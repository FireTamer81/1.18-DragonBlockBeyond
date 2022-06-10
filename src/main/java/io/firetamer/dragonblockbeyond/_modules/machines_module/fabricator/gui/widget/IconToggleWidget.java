package io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.util.DBBColor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class IconToggleWidget extends AbstractWidget {
    protected ResourceLocation iconTextureResource;
    protected boolean isStateTriggered;
    protected int texTopLeftX;
    protected int texTopLeftY;
    protected int texWidth;
    protected int texHeight;
    protected DBBColor backgroundColor;
    protected DBBColor hoverColor;
    protected int buttonPosTopLeftX;
    protected int buttonPosTopLeftY;

    public IconToggleWidget(int topLeftX, int topLeftY, int width, int height, boolean initialTriggerState, DBBColor backgroundColorIn, DBBColor hoverColorIn) {
        super(topLeftX, topLeftY, width, height, TextComponent.EMPTY);
        this.isStateTriggered = initialTriggerState;
        this.backgroundColor = backgroundColorIn;
        this.hoverColor = hoverColorIn;

        this.buttonPosTopLeftX = topLeftX;
        this.buttonPosTopLeftY = topLeftY;
    }

    public void initButtonTexture(int textureTopLeftX, int textureTopLeftY, int textureWidth, int textureHeight, ResourceLocation resourceLocationIn) {
        this.texTopLeftX = textureTopLeftX;
        this.texTopLeftY = textureTopLeftY;
        this.texWidth = textureWidth;
        this.texHeight = textureHeight;
        this.iconTextureResource = resourceLocationIn;
    }

    public void setStateTriggered(boolean newState) { this.isStateTriggered = newState; }

    public boolean isStateTriggered() { return this.isStateTriggered; }

    public void setPosition(int newXPos, int newYPos) {
        this.x = newXPos;
        this.y = newYPos;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) { this.defaultButtonNarrationText(p_169152_); }

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.iconTextureResource);
        RenderSystem.disableDepthTest();



        int i = this.texTopLeftX;
        int j = this.texTopLeftY;

        if (this.isStateTriggered) {
            i += this.texWidth;
        }

        if (this.isHoveredOrFocused()) {
            j += this.texHeight;
        }

        this.blit(stack, this.x, this.y, i, j, this.width, this.height);
        RenderSystem.enableDepthTest();
    }
}
