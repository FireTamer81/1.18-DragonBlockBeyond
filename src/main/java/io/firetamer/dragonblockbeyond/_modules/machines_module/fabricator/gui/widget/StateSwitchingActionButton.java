package io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class StateSwitchingActionButton extends Button {
    private int topLeftPosX, topLeftPosY, width, height;
    private int texTopLeftX, texTopLeftY, texWidth, texHeight;
    private ResourceLocation buttonTexture;
    private boolean isStateTriggered;

    public StateSwitchingActionButton(int topLeftPosXIn, int topLeftPosYIn, int widthIn, int heightIn, boolean initialTriggerState, OnPress onPress) {
        super(topLeftPosXIn, topLeftPosYIn, widthIn, heightIn, TextComponent.EMPTY, onPress);

        this.topLeftPosX = topLeftPosXIn;
        this.topLeftPosY = topLeftPosYIn;
        this.width = widthIn;
        this.height = heightIn;
        this.isStateTriggered = initialTriggerState;
    }

    public void initVisuals(int texTopLeftXIn, int texTopLeftYIn, int texWidthIn, int texHeightIn, ResourceLocation buttonTextureIn) {
        this.texTopLeftX = texTopLeftXIn;
        this.texTopLeftY = texTopLeftYIn;
        this.texWidth = texWidthIn;
        this.texHeight = texHeightIn;
        this.buttonTexture = buttonTextureIn;
    }

    public void setStateTriggered(boolean newState) { this.isStateTriggered = newState; }

    public boolean isStateTriggered() { return this.isStateTriggered; }

    public void setPosition(int xIn, int yIn) {
        this.topLeftPosX = xIn;
        this.topLeftPosY = yIn;
    }

    public void renderButton(PoseStack stack, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.buttonTexture);

        int x = this.texTopLeftX;
        int y = this.texTopLeftY;

        if (this.isStateTriggered) {
            x += this.texWidth;
        }
        if (this.isHoveredOrFocused()) {
            y += this.texHeight;
        }

        blit(stack, this.topLeftPosX, this.topLeftPosY, x, y, this.width, this.height);
        RenderSystem.enableDepthTest();
    }
}
