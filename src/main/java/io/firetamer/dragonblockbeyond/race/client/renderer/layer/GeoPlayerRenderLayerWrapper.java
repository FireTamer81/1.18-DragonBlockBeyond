package io.firetamer.dragonblockbeyond.race.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import software.bernie.geckolib3.core.IAnimatable;

public class GeoPlayerRenderLayerWrapper<T extends IAnimatable> extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    protected final GeoPlayerLayerRenderer<T> geoPlayerRenderLayer;

    public GeoPlayerRenderLayerWrapper(GeoPlayerLayerRenderer<T> geoPlayerRenderLayer, RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent) {
        super(renderLayerParent);
        this.geoPlayerRenderLayer = geoPlayerRenderLayer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick,
            float ageInTicks, float netHeadYaw, float headPitch) {
        T animatable = this.geoPlayerRenderLayer.getRenderer().getAnimatable();
        this.geoPlayerRenderLayer.renderPre(poseStack, buffer, packedLight, player, animatable, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
    }
}
