package io.firetamer.dragonblockbeyond.race.client.renderer.layer;

import io.firetamer.dragonblockbeyond.race.Race;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import software.bernie.geckolib3.core.IAnimatable;

import java.util.function.Predicate;

/**
 * It is recommended to call {@link #registerFirstPersonLayer} from a listener of {@link EntityRenderersEvent.AddLayers}.
 * Separate from {@link FirstPersonRenderLayerManager} to prevent classloading Geckolib classes when Geckolib is not present.
 */
public class GeoFirstPersonRenderLayerManager {
    public static <T extends IAnimatable> void registerFirstPersonLayer(Race race, GeoPlayerLayerRenderer<T> geoPlayerRenderLayer) {
        FirstPersonRenderLayerManager.registerFirstPersonLayer(race, parent -> new GeoPlayerRenderLayerWrapper<>(geoPlayerRenderLayer, parent));
    }

    public static <T extends IAnimatable> void registerFirstPersonLayer(Predicate<LocalPlayer> shouldRender, GeoPlayerLayerRenderer<T> geoPlayerRenderLayer) {
        FirstPersonRenderLayerManager.registerFirstPersonLayer(shouldRender, parent -> new GeoPlayerRenderLayerWrapper<>(geoPlayerRenderLayer, parent));
    }
}
