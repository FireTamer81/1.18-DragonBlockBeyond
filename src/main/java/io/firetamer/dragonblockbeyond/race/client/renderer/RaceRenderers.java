package io.firetamer.dragonblockbeyond.race.client.renderer;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.mojang.datafixers.util.Pair;
import io.firetamer.dragonblockbeyond.race.Race;
import io.firetamer.dragonblockbeyond.race.client.animatable.IHasGeoRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class RaceRenderers {
    private static final Multimap<ResourceLocation, Pair<Class<? extends LivingEntity>, EntityRendererProvider<?>>> FACTORIES = MultimapBuilder.hashKeys().arrayListValues().build();
    private static final Multimap<ResourceLocation, Pair<Class<? extends LivingEntity>, EntityRenderer<?>>> RACE_RENDERERS = MultimapBuilder.hashKeys().arrayListValues().build();

    static {
        if (ModList.get().isLoaded("geckolib3")) {
            addModelFetcher();
        }
    }

    @SuppressWarnings("unchecked")
    private static void addModelFetcher() {
        AnimationController.addModelFetcher(animatable -> {
            if (animatable instanceof IHasGeoRenderer hasGeoRenderer) {
                IGeoRenderer<?> geoRenderer = hasGeoRenderer.getGeoRenderer();
                return geoRenderer == null ? null : (IAnimatableModel<Object>) geoRenderer.getGeoModelProvider();
            }
            return null;
        });
    }

    /**
     * Registers a player race renderer for the provided race with the given entity renderer provider.
     * All entities raceed as this race that are a subclass of {@link Player} will use the entity renderer from the provider.
     * <p>
     * If multiple entries are registered for a single race, the entries will be checked in the order they were registered for a match.
     *
     * @param race the race
     * @param provider the entity renderer provider
     * @see #registerRaceRenderer(Race, Class, EntityRendererProvider)
     */
    public static synchronized void registerPlayerRaceRenderer(Race race, EntityRendererProvider<AbstractClientPlayer> provider) {
        registerRaceRenderer(race, AbstractClientPlayer.class, provider);
    }



    /**
     * Registers a race renderer for the provided race and entity class with the given entity renderer provider.
     * All entities raceed as this race that are a subclass of {@code entityClass} will use the entity renderer from the provider.
     * <p>
     * If multiple entries are registered for a single race, the entries will be checked in the order they were registered for a match.
     *
     * @param race the race
     * @param entityClass the entity class to filter any raceed entities through before they are rendered with this renderer
     * @param provider the entity renderer provider
     * @param <T> the type of the entity to filter
     * @see #registerPlayerRaceRenderer(Race, EntityRendererProvider)
     */
    private static synchronized <T extends LivingEntity> void registerRaceRenderer(Race race, Class<T> entityClass, EntityRendererProvider<T> provider) {
        FACTORIES.put(race.getRegistryName(), Pair.of(entityClass, provider));
    }

    @Nullable
    private static EntityRenderer<?> getRaceRendererInternal(@Nullable LivingEntity entity, Race race) {
        ResourceLocation key = race.getRegistryName();

        if (!RACE_RENDERERS.containsKey(key)) {
            var providers = FACTORIES.get(key);
            if (providers.isEmpty()) {

                    return null;

            }

            Minecraft mc = Minecraft.getInstance();
            EntityRendererProvider.Context context = new EntityRendererProvider.Context(mc.getEntityRenderDispatcher(), mc.getItemRenderer(), mc.getResourceManager(), mc.getEntityModels(), mc.font);

            providers.forEach(pair -> RACE_RENDERERS.put(key, Pair.of(pair.getFirst(), pair.getSecond().create(context))));
        }

        for (var pair : RACE_RENDERERS.get(key)) {
            if (pair.getFirst() == null || (entity != null && pair.getFirst().isInstance(entity))) {
                return pair.getSecond();
            }
        }

        return null;
    }



    @SuppressWarnings("unchecked")
    @NotNull
    public static <T extends Entity> EntityRenderer<T> getRaceRenderer(@Nullable LivingEntity entity, Race race) {
        EntityRenderer<T> raceRenderer = (EntityRenderer<T>) getRaceRendererInternal(entity, race);

        if (raceRenderer == null)
            throw new IllegalStateException("Entity " + entity + " with race " + race.getRegistryName() + " did not have a registered race renderer!");

        return raceRenderer;
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public static EntityRenderer<Player> getPlayerRaceRenderer(@NotNull Player player, Race race) {
        EntityRenderer<Player> raceRenderer = (EntityRenderer<Player>) getRaceRendererInternal(player, race);

        if (raceRenderer == null)
            throw new IllegalStateException(race.getRegistryName() + " did not have a registered race renderer!");

        return raceRenderer;
    }
}
