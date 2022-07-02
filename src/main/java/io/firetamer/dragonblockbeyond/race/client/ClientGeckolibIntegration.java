package io.firetamer.dragonblockbeyond.race.client;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.race.client.event.RaceRenderEvents;
import io.firetamer.dragonblockbeyond.race.client.renderer.GeoModelCache;
import io.firetamer.dragonblockbeyond.race.client.renderer.GeoPlayerRenderer;
import io.firetamer.dragonblockbeyond.race.client.renderer.IGeoArmProvider;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.core.IAnimatable;

public class ClientGeckolibIntegration {
    public static void registerClientReloadListener() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(ClientGeckolibIntegration::onRegisterClientReloadListeners);
    }

    private static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event) {
        // Clear the geo model cache on client reload
        event.registerReloadListener((ResourceManagerReloadListener) resourceManager -> GeoModelCache.clear());
    }

    @SuppressWarnings("unchecked")
    public static <T extends IAnimatable> void renderGeoArm(AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float equippedProgress,
            float swingProgress, HumanoidArm side, EntityRenderer<?> renderer) {
        if (!isGeoPlayerRenderer(renderer))
            return;
        GeoPlayerRenderer<T> geoRenderer = (GeoPlayerRenderer<T>) renderer;
        renderGeoArm(player, poseStack, bufferSource, packedLight, equippedProgress, swingProgress, side, geoRenderer.getAnimatable(), geoRenderer);
    }

    @SuppressWarnings("unchecked")
    public static <T extends IAnimatable> void renderGeoArm(AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float equippedProgress,
            float swingProgress, HumanoidArm side, T animatable, GeoPlayerRenderer<T> renderer) {
        if (renderer.getGeoModelProvider() instanceof IGeoArmProvider) {
            ((IGeoArmProvider<T>) renderer.getGeoModelProvider()).renderGeoArm(player, poseStack, bufferSource, packedLight, equippedProgress, swingProgress,
                    RaceRenderEvents.getPlayerRenderPartialTick(), side, animatable, renderer);
        }
    }

    public static boolean isGeoPlayerRenderer(EntityRenderer<?> renderer) {
        return renderer instanceof GeoPlayerRenderer;
    }
}
