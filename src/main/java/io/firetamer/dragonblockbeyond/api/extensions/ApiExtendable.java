package io.firetamer.dragonblockbeyond.api.extensions;

/**
 * Implement this interface on types which can be API extended.
 * @see ApiExtensions
 */
public interface ApiExtendable {

    /**
     * @return the name of the extension type
     */
    default String getExtensionTypeName() {
        return ApiExtendable.class.getName().replaceFirst("L", "");
    }

}
