package io.firetamer.dragonblockbeyond.race.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.race.capability.RaceHolderAttacher;
import io.firetamer.dragonblockbeyond.race.client.ArmRenderingUtil;
import io.firetamer.dragonblockbeyond.race.client.ClientGeckolibIntegration;
import io.firetamer.dragonblockbeyond.race.client.renderer.RaceRenderers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, value = Dist.CLIENT)
public class NMRenderEvents {
    private static boolean renderingPlayerPre = false;



    @SuppressWarnings("unchecked")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderHand(RenderHandEvent event) {
        AbstractClientPlayer player = Minecraft.getInstance().player;
        boolean isMainHand = event.getHand() == InteractionHand.MAIN_HAND;
        if (player == null || !isMainHand || player.isScoping() || !event.getItemStack().isEmpty() || player.isInvisible())
            return;

        RaceHolderAttacher.getCurrentRace(player).ifPresent(currentRace -> {

            event.setCanceled(true);

            PoseStack poseStack = event.getPoseStack();
            MultiBufferSource buffer = event.getMultiBufferSource();
            int packedLight = event.getPackedLight();
            float equippedProgress = event.getEquipProgress();
            float swingProgress = event.getSwingProgress();
            HumanoidArm side = isMainHand ? player.getMainArm() : player.getMainArm().getOpposite();
            EntityRenderer<?> renderer = RaceRenderers.getPlayerRaceRenderer(player, currentRace);

            if (ModList.get().isLoaded("geckolib3") && ClientGeckolibIntegration.isGeoPlayerRenderer(renderer)) {
                ClientGeckolibIntegration.renderGeoArm(player, poseStack, buffer, packedLight, equippedProgress, swingProgress, side, renderer);
            } else if (renderer instanceof LivingEntityRenderer) {
                ArmRenderingUtil.renderArm(player, poseStack, buffer, packedLight, equippedProgress, swingProgress, side, (LivingEntityRenderer<? super AbstractClientPlayer, ?>) renderer);
            }
        });
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        if (renderingPlayerPre || (event.getPlayer().isSpectator()))
            return;

        renderingPlayerPre = true;
        float partialTick = event.getPartialTick();
        Player player = event.getPlayer();
        PoseStack poseStack = event.getPoseStack();

        RaceHolderAttacher.getCurrentRace(player).ifPresent(currentRace -> {
            event.setCanceled(true);

            EntityRenderer<Player> raceRenderer = RaceRenderers.getPlayerRaceRenderer(player, currentRace);

            render(player, poseStack, partialTick, raceRenderer, event.getMultiBufferSource(), event.getPackedLight());
        });

        renderingPlayerPre = false;
    }


    private static <T extends LivingEntity> void render(T entity, PoseStack poseStack, float partialTick, EntityRenderer<T> raceRenderer,
            MultiBufferSource multiBufferSource, int packedLight) {
        poseStack.pushPose();

        float yRot = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
        raceRenderer.render(entity, yRot, partialTick, poseStack, multiBufferSource, packedLight);

        poseStack.popPose();
    }
}
