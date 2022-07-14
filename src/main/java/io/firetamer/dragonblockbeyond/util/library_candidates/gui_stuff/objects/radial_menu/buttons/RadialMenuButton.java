package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.radial_menu.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GuiDrawingContext;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.texture_objects.SimpleSpriteObject;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class RadialMenuButton implements IRadialMenuButton {
    private ItemStack stack;
    private BlockItem blockItem;
    private SimpleSpriteObject spriteIcon;
    private Component centerText;
    private int centerTextColor;
    private Component buttonText;
    private int buttonTextColor;

    private boolean isHovered;


    /**
     * Only one of the three render objects can be passed (itemstack, blockitem, or sprite texture).
     * For the TextComponents, keep them short and sweet or they might overlap the button edges or the radial.
     * The buttontextIn parameter can also be paired with one fo the three other renders to have a word or phrase under the render.
     *
     * @param itemStackIn Renders the passed itemstack in the center of the button.
     * @param blockItemIn Renders the passed blockitem in the center of the button.
     * @param spriteIconIn Renders the passed sprite texture in the center of the button
     * @param radialCenterText Renders the passed text in the center of the radial (best used to title the hovered button)
     * @param radialCenterTextColor Changes the color of the center text, if null the color will be a default of white
     * @param buttonTextIn If this is the only value, then the text will be in the center of the button. If paired with 1 of the 3 other renders (itemstack, blockitem, or sprite texture), it will render under that.
     * @param buttonTextColor Changes the color of the button text from the default white.
     */
    public RadialMenuButton(@Nullable ItemStack itemStackIn, @Nullable BlockItem blockItemIn, @Nullable SimpleSpriteObject spriteIconIn,
                            @Nullable Component radialCenterText, @Nullable ChatFormatting radialCenterTextColor,
                            @Nullable Component buttonTextIn, @Nullable ChatFormatting buttonTextColor) {

        if ((itemStackIn != null && blockItemIn != null) || (blockItemIn != null && spriteIconIn != null) || (spriteIconIn != null && itemStackIn != null)) {
            this.stack = null;
            this.blockItem = null;
            this.spriteIcon = null;
        } else {
            if(itemStackIn != null) {
                this.stack = itemStackIn;
            } else if (blockItemIn != null) {
                this.blockItem = blockItemIn;
            } else if (spriteIconIn != null) {
                this.spriteIcon = spriteIconIn;
            }
        }

        this.centerText = radialCenterText;
        this.centerTextColor = radialCenterTextColor == null ? ChatFormatting.WHITE.getColor() : radialCenterTextColor.getColor();

        this.buttonText = buttonTextIn;
        this.buttonTextColor = buttonTextColor == null ? ChatFormatting.WHITE.getColor() : buttonTextColor.getColor();
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    @Override
    public void draw(GuiDrawingContext drawContext) {
        /* If buttonText != null but the other button renders do, render the passed text in the center of the button */
        if (this.buttonText != null && (this.stack == null && this.blockItem == null && this.spriteIcon == null)) {
            float y = drawContext.originY;
            MultiLineLabel lines = MultiLineLabel.create(drawContext.fontRenderer, this.buttonText, 60);

            if (lines.getLineCount() > 1) {
                y = y - drawContext.fontRenderer.lineHeight / 1.5f;
            } else {
                y = y - drawContext.fontRenderer.lineHeight / 2.0f;
            }

            lines.renderCentered(drawContext.poseStack, (int) drawContext.originX, (int) y, drawContext.fontRenderer.lineHeight, this.buttonTextColor);
        }


        /* If itemStack != null but the other button renders do, render the passed itemStack in the center of the button */
        if (this.stack != null && this.buttonText == null) {
            PoseStack viewModelPose = RenderSystem.getModelViewStack();
            viewModelPose.pushPose();
            viewModelPose.mulPoseMatrix(drawContext.poseStack.last().pose());
            viewModelPose.translate(-8, -8, drawContext.originZ);
            RenderSystem.applyModelViewMatrix();
            drawContext.itemRenderer.renderAndDecorateItem(stack, (int) drawContext.originX, (int) drawContext.originY);
            drawContext.itemRenderer.renderGuiItemDecorations(drawContext.fontRenderer, stack,  (int) drawContext.originX, (int) drawContext.originY, "");
            viewModelPose.popPose();
            RenderSystem.applyModelViewMatrix();
        }


        /* If blockItem != null but the other button renders do, render the passed blockItem in the center of the button */
        if (this.blockItem != null && this.buttonText == null) {
            ItemStack itemStack = this.blockItem.getDefaultInstance();

            PoseStack viewModelPose = RenderSystem.getModelViewStack();
            viewModelPose.pushPose();
            viewModelPose.mulPoseMatrix(drawContext.poseStack.last().pose());
            viewModelPose.translate(-8, -8, drawContext.originZ);
            RenderSystem.applyModelViewMatrix();
            drawContext.itemRenderer.renderAndDecorateItem(itemStack, (int) drawContext.originX, (int) drawContext.originY);
            drawContext.itemRenderer.renderGuiItemDecorations(drawContext.fontRenderer, itemStack,  (int) drawContext.originX, (int) drawContext.originY, "");
            viewModelPose.popPose();
            RenderSystem.applyModelViewMatrix();
        }


        /* If spriteTexture != null but the other button renders do, render the passed spriteTexture in the center of the button */
        if (this.spriteIcon != null && this.buttonText == null) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, this.spriteIcon.getTextureResource());

            RenderSystem.enableDepthTest();

            int textureOffsetX = 0;
            int textureOffsetY = 0;
            if(this.spriteIcon.getTextureWidth() == 16) { textureOffsetX = 8; } else { textureOffsetX = 16; }
            if(this.spriteIcon.getTextureHeight() == 16) { textureOffsetY = 8; } else { textureOffsetY = 16; }

            GuiComponent.blit(drawContext.poseStack, (int)drawContext.originX - textureOffsetX, (int)drawContext.originY - textureOffsetY,
                    this.spriteIcon.getTextureXOrigin(), this.spriteIcon.getTextureYOrigin(),
                    this.spriteIcon.getTextureWidth(), this.spriteIcon.getTextureHeight(),
                    this.spriteIcon.getTextureWidth(), this.spriteIcon.getTextureHeight());
        }


        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


        /* If itemStack and buttonText != null, render the passed itemStack in the center of the button with the text below it */
        if (this.stack != null && this.buttonText != null) {
            PoseStack viewModelPose = RenderSystem.getModelViewStack();
            viewModelPose.pushPose();
            viewModelPose.mulPoseMatrix(drawContext.poseStack.last().pose());
            viewModelPose.translate(-8, -8 - drawContext.fontRenderer.lineHeight, drawContext.originZ);
            RenderSystem.applyModelViewMatrix();
            drawContext.itemRenderer.renderAndDecorateItem(stack, (int) drawContext.originX, (int) drawContext.originY);
            drawContext.itemRenderer.renderGuiItemDecorations(drawContext.fontRenderer, stack,  (int) drawContext.originX, (int) drawContext.originY, "");
            viewModelPose.popPose();
            RenderSystem.applyModelViewMatrix();

            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

            float y = drawContext.originY;
            MultiLineLabel lines = MultiLineLabel.create(drawContext.fontRenderer, this.buttonText, 60);

            if (lines.getLineCount() > 1) {
                y = y - drawContext.fontRenderer.lineHeight / 1.5f;
            } else {
                y = y - drawContext.fontRenderer.lineHeight / 2.0f;
            }

            lines.renderCentered(drawContext.poseStack, (int) drawContext.originX, (int) y + 8, drawContext.fontRenderer.lineHeight, this.buttonTextColor);
        }


        /* If blockItem and buttonText != null, render the passed blockItem in the center of the button with the text below it */
        if (this.blockItem != null && this.buttonText != null) {
            ItemStack itemStack = this.blockItem.getDefaultInstance();

            PoseStack viewModelPose = RenderSystem.getModelViewStack();
            viewModelPose.pushPose();
            viewModelPose.mulPoseMatrix(drawContext.poseStack.last().pose());
            viewModelPose.translate(-8, -8 - drawContext.fontRenderer.lineHeight, drawContext.originZ);
            RenderSystem.applyModelViewMatrix();
            drawContext.itemRenderer.renderAndDecorateItem(itemStack, (int) drawContext.originX, (int) drawContext.originY);
            drawContext.itemRenderer.renderGuiItemDecorations(drawContext.fontRenderer, itemStack,  (int) drawContext.originX, (int) drawContext.originY, "");
            viewModelPose.popPose();
            RenderSystem.applyModelViewMatrix();

            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

            float y = drawContext.originY;
            MultiLineLabel lines = MultiLineLabel.create(drawContext.fontRenderer, this.buttonText, 60);

            if (lines.getLineCount() > 1) {
                y = y - drawContext.fontRenderer.lineHeight / 1.5f;
            } else {
                y = y - drawContext.fontRenderer.lineHeight / 2.0f;
            }

            lines.renderCentered(drawContext.poseStack, (int) drawContext.originX, (int) y + 8, drawContext.fontRenderer.lineHeight, this.buttonTextColor);
        }


        /* If spriteTexture and buttonText != null, render the passed spriteTexture in the center of the button with the text below it */
        if (this.spriteIcon != null && this.buttonText != null) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, this.spriteIcon.getTextureResource());

            RenderSystem.enableDepthTest();

            int textureOffsetX = 0;
            int textureOffsetY = 0;
            if(this.spriteIcon.getTextureWidth() == 16) { textureOffsetX = 8; } else { textureOffsetX = 16; }
            if(this.spriteIcon.getTextureHeight() == 16) { textureOffsetY = 8 - drawContext.fontRenderer.lineHeight; } else { textureOffsetY = 16 - drawContext.fontRenderer.lineHeight; }

            GuiComponent.blit(drawContext.poseStack, (int)drawContext.originX - textureOffsetX, (int)drawContext.originY - textureOffsetY,
                    this.spriteIcon.getTextureXOrigin(), this.spriteIcon.getTextureYOrigin(),
                    this.spriteIcon.getTextureWidth(), this.spriteIcon.getTextureHeight(),
                    this.spriteIcon.getTextureWidth(), this.spriteIcon.getTextureHeight());

            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

            float y = drawContext.originY;
            MultiLineLabel lines = MultiLineLabel.create(drawContext.fontRenderer, this.buttonText, 60);

            if (lines.getLineCount() > 1) {
                y = y - drawContext.fontRenderer.lineHeight / 1.5f;
            } else {
                y = y - drawContext.fontRenderer.lineHeight / 2.0f;
            }

            lines.renderCentered(drawContext.poseStack, (int) drawContext.originX, (int) y + 8, drawContext.fontRenderer.lineHeight, this.buttonTextColor);
        }


    }

    @Override
    public boolean isHovered() { return isHovered; }

    @Override
    public void setHovered(boolean newHoveredState) { this.isHovered = newHoveredState; }

    @Override
    public void click() {}

    @Override
    public boolean shouldCloseRadialOnClick() { return true; }

    @Override
    public Component getRadialCenterTextForHoveredButton() { return centerText; }

    @Override
    public int getCenterTextColor() { return centerTextColor; }
}
