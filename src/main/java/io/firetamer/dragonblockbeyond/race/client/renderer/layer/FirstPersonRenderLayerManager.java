package io.firetamer.dragonblockbeyond.race.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.race.Race;
import io.firetamer.dragonblockbeyond.race.capability.RaceHolderAttacher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * It is recommended to call {@link #registerFirstPersonLayer} from a listener of {@link EntityRenderersEvent.AddLayers}.
 */
@Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, value = Dist.CLIENT)
public class FirstPersonRenderLayerManager {
    private static final Object LOCK = new Object();
    private static final List<LayerEntry> FIRST_PERSON_LAYERS = new ArrayList<>();

    public static void registerFirstPersonLayer(Race race, PlayerRenderLayerFactory factory) {
        registerFirstPersonLayer(race, createRenderLayer(factory));
    }

    public static void registerFirstPersonLayer(Race race, RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayer) {
        registerFirstPersonLayer(player -> RaceHolderAttacher.isRace(player, race), renderLayer);
    }

    public static void registerFirstPersonLayer(Predicate<LocalPlayer> shouldRender, PlayerRenderLayerFactory factory) {
        registerFirstPersonLayer(shouldRender, createRenderLayer(factory));
    }

    public static void registerFirstPersonLayer(Predicate<LocalPlayer> shouldRender, RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayer) {
        synchronized (LOCK) {
            FIRST_PERSON_LAYERS.add(new LayerEntry(shouldRender, renderLayer));
        }
    }

    @SuppressWarnings("unchecked")
    @NotNull
    private static RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> createRenderLayer(PlayerRenderLayerFactory factory) {
        return factory.createRenderLayer((RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>) Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default"));
    }

    @SubscribeEvent
    public static void onRenderWorldLast(RenderLevelLastEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || !mc.options.getCameraType().isFirstPerson() || player.isSpectator())
            return;

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource bufferSource = mc.renderBuffers().bufferSource();
        float partialTick = event.getPartialTick();
        int packedLight = mc.getEntityRenderDispatcher().getPackedLightCoords(player, partialTick);
        float lerpedYBodyRot = Mth.rotLerp(partialTick, player.yBodyRotO, player.yBodyRot);
        float lerpedYHeadRot = Mth.rotLerp(partialTick, player.yHeadRotO, player.yHeadRot);
        float netYRot = lerpedYHeadRot - lerpedYBodyRot;
        float lerpedXRot = Mth.lerp(partialTick, player.xRotO, player.getXRot());

        Vec3 cameraVec = mc.gameRenderer.getMainCamera().getPosition();
        double lerpedX = Mth.lerp(partialTick, player.xOld, player.getX());
        double lerpedY = Mth.lerp(partialTick, player.yOld, player.getY());
        double lerpedZ = Mth.lerp(partialTick, player.zOld, player.getZ());
        PlayerRenderer playerRenderer = (PlayerRenderer) mc.getEntityRenderDispatcher().getRenderer(player);
        Vec3 renderOffset = playerRenderer.getRenderOffset(player, partialTick).subtract(cameraVec);

        float bob = playerRenderer.getBob(player, partialTick);
        poseStack.translate(lerpedX + renderOffset.x(), lerpedY + renderOffset.y(), lerpedZ + renderOffset.z());

        poseStack.pushPose();

        playerRenderer.setupRotations(player, poseStack, bob, lerpedYBodyRot, partialTick);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        playerRenderer.scale(player, poseStack, partialTick);
        poseStack.translate(0.0D, -1.501F, 0.0D);

        FIRST_PERSON_LAYERS.forEach(layerEntry -> {
            if (layerEntry.shouldRender().test(player))
                layerEntry.renderLayer().render(poseStack, bufferSource, packedLight, player, 0, 0, partialTick, bob, netYRot, lerpedXRot);
        });

        poseStack.popPose();
    }

    private FirstPersonRenderLayerManager() {}

    @FunctionalInterface
    public interface PlayerRenderLayerFactory {
        @NotNull
        RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> createRenderLayer(@NotNull RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent);
    }

    private record LayerEntry(Predicate<LocalPlayer> shouldRender, RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayer) {}
}
