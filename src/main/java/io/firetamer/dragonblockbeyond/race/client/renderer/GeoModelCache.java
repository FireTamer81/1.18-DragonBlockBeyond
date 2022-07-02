package io.firetamer.dragonblockbeyond.race.client.renderer;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.file.GeoModelLoader;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

import java.util.concurrent.TimeUnit;

public class GeoModelCache {
    private static final GeoModelLoader geoModelLoader = new GeoModelLoader();
    private static final Cache<ResourceLocation, GeoModel> cache = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).build();

    /**
     * Useful for querying a geo model that won't be affected by the animations of an entity.
     *
     * @param location the geo model location; see {@link GeoModelProvider#getModelLocation(Object)} for what should be input
     * @return a cached geo model
     */
    public static GeoModel getModel(ResourceLocation location) {
        return cache.asMap().computeIfAbsent(location, l -> geoModelLoader.loadModel(Minecraft.getInstance().getResourceManager(), location));
    }

    public static void clear() {
        cache.invalidateAll();
    }
}
