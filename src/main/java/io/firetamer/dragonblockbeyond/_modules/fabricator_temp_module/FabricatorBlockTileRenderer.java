package io.firetamer.dragonblockbeyond._modules.fabricator_temp_module;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class FabricatorBlockTileRenderer implements BlockEntityRenderer<FabricatorBlockTile> {
    private final BlockEntityRendererProvider.Context context;
    private final Minecraft mc = Minecraft.getInstance();

    public FabricatorBlockTileRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldRenderOffScreen(FabricatorBlockTile p_112306_) {
        return true;
    }

    @Override
    public void render(FabricatorBlockTile tile, float partialTicks, PoseStack stack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        int lightLevel = getLightLevel(Objects.requireNonNull(tile.getLevel()), tile.getBlockPos().above());

        //if(tile.getItem().equals(ItemStack.EMPTY) || tile.getItem().getItem().equals(Items.AIR)) { return; }
        //renderItem(tile.getItem(), new float[] {0.5f, 1f, 0.5f}, Vector3f.YP.rotationDegrees(180f), stack, buffer, partialTicks, combinedOverlay, lightLevel, 0.8f);

        ItemStack item = Blocks.ACACIA_PLANKS.asItem().getDefaultInstance();
        renderItem(item, new float[] {0.5f, 1f, 0.5f}, Vector3f.YP.rotationDegrees(180f), stack, buffer, partialTicks, combinedOverlay, lightLevel, 0.8f);
    }

    private void renderItem(ItemStack stack, float[] translation, Quaternion rotation, PoseStack matrixStack, MultiBufferSource buffer, float partialTicks, int combinedOverlay, int lightLevel, float scale) {
        matrixStack.pushPose();

        if(stack.getItem() instanceof BlockItem) {
            matrixStack.translate(0.5, 1.0, 0.5);  // matrixStack.translate(translation[0], translation[1], translation[2]
        } else {
            matrixStack.translate(0.5, 1.05, 0.5);  // matrixStack.translate(translation[0], translation[1], translation[2]
        }

        // how is mulPose rotation
        matrixStack.mulPose(rotation);
        matrixStack.scale(scale, scale, scale);

        mc.getItemRenderer().renderStatic(stack, TransformType.GROUND, lightLevel, combinedOverlay, matrixStack, buffer, 1);

        matrixStack.popPose();
    }

    private int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }





    private void renderItem(BlockEntity te, ItemStack itemStack, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        matrixStack.pushPose();

        matrixStack.translate(0.5f, 2f, 0.5f);
        matrixStack.scale(0.75f, 0.75f, 0.75f);

        renderer.renderStatic(itemStack, TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer, 1);

        matrixStack.popPose();
    }
}
