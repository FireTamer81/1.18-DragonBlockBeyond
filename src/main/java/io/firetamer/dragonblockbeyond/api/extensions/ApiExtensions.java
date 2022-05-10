package io.firetamer.dragonblockbeyond.api.extensions;

import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.objectweb.asm.Type;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;

/**
 * API Extensions are a way of different mod parts (internal and the API) communicating
 * with each other. This is only used by Machina in order to link the main mod and the API
 */
@SuppressWarnings("unchecked")
public class ApiExtensions {

    private static volatile Map<Class<?>, Object> EXTENSIONS = Collections.synchronizedMap(new HashMap<>());

    /**
     * Register and extension
     *
     * @param extensionType  the class which represents the extension type
     * @param implementation the extension implementation
     * @param <EXTENSION>    the extension type
     * @param <IMPL>         the extension implementation
     */
    public static synchronized <EXTENSION extends ApiExtendable, IMPL extends EXTENSION> void registerExtension(
            Class<EXTENSION> extensionType, IMPL implementation) {
        if (!EXTENSIONS.containsKey(extensionType)) {
            EXTENSIONS.put(extensionType, implementation);
        } else {
            throw new RuntimeException("Tried to register an ApiExtension for a class which already has that!");
        }
    }

    /**
     * Retrieves the currently registered extension for a type. <br>
     * Can be null if an implementation is not registered for that type!
     *
     * @param extensionType the class which represents the extension type
     * @param <EXTENSION>   the extension type
     * @return the extension implementation
     */
    @Nullable
    public static <EXTENSION extends ApiExtendable> EXTENSION getExtension(Class<EXTENSION> extensionType) {
        return (EXTENSION) EXTENSIONS.get(extensionType);
    }

    /**
     * Executes a void function using the implementation of the provided {@code extensionType}
     * @param extensionType the class which represents the extension type
     * @param callback the function to execute
     * @param <EXTENSION> the extension type
     * @param <X> an exception that can be thrown by the {@code extensionType}
     * @throws X an exception that can be thrown by the {@code extensionType}
     */
    public synchronized static <EXTENSION, X extends Exception> void useExtension(Class<EXTENSION> extensionType,
                                                                                  ExtensionConsumer<EXTENSION, X> callback) throws X {
        withExtension(extensionType, extension -> {
            callback.useExtension(extension);
            return null;
        });
    }

    /**
     * Executes a function using the implementation of the provided {@code extensionType}
     * @param extensionType the class which represents the extension type
     * @param callback the function to execute
     * @param <EXTENSION> the extension type
     * @param <R> the return type of the {@code extensionType}
     * @param <X> an exception that can be thrown by the {@code extensionType}
     * @throws X an exception that can be thrown by the {@code extensionType}
     * @return the return of the executed {@code callback}
     */
    public static synchronized <R, EXTENSION, X extends Exception> R withExtension(Class<EXTENSION> extensionType,
                                                                                   ExtensionCallback<R, EXTENSION, X> callback) throws X {
        return callback.withExtension((EXTENSION) EXTENSIONS.get(extensionType));
    }

    private static boolean annotationsProcessed = false;

    public static void registerAnnotationExtensions() {
        if (annotationsProcessed) {
            return;
        }
        annotationsProcessed = true;
        ModList.get().getAllScanData().stream().map(ModFileScanData::getAnnotations).flatMap(Collection::stream)
                .filter(a -> a.getAnnotationType().equals(Type.getType(ApiExtension.class))
                        && a.getTargetType() == ElementType.FIELD)
                .forEach(data -> {
                    try {
                        final Class<?> clazz = Class.forName(data.getClassType().getClassName(), false,
                                ApiExtensions.class.getClassLoader());
                        final Field field = clazz.getDeclaredField(data.getMemberName());
                        field.setAccessible(true);
                        Class<? extends ApiExtendable> extensionType = field.getAnnotation(ApiExtension.class).value();
                        EXTENSIONS.put(extensionType, field.get(null));
                    } catch (ClassNotFoundException | NoSuchFieldException | SecurityException
                            | IllegalArgumentException | IllegalAccessException e) {
                        throw new RuntimeException("Could not register Api Extension", e);
                    }
                });
    }
}
