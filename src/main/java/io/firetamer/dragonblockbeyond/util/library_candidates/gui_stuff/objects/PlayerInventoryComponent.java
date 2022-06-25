package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.util.library_candidates.DBBColor;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.base_objects.BasePanelComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class PlayerInventoryComponent extends BasePanelComponent {
    //TODO make this a texture object like the panel border styles for when I go to make multiple versions.
    public static final ResourceLocation PLAYER_INVENTORY_TEXTUREMAP = new ResourceLocation(DragonBlockBeyond.MOD_ID, "textures/gui/player_inventory_style1.png");

    public void init(int xOffset, int yOffset, int panelWidth, int panelHeight, int screenWidth, int screenHeight, Minecraft instanceIn, BorderTextureObject borderStyleIn, DBBColor panelColor1In, DBBColor panelColor2In) {

        if(panelWidth < 170) { panelWidth = 170; }
        if(panelHeight < 92) { panelHeight = 92; }

        int xOrigin = ((screenWidth - panelWidth) / 2) + xOffset;
        int yOrigin = ((screenHeight - panelHeight) / 2) + yOffset;

        super.init(xOrigin, yOrigin, panelWidth, panelHeight, 170, 92, instanceIn, true, false, borderStyleIn, panelColor1In, panelColor2In);
    }

    @Override
    public void renderBackground(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        super.renderBackground(poseStack, mouseX, mouseY, delta);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, PLAYER_INVENTORY_TEXTUREMAP);

        //GUIHelper.blit(poseStack, topLeftCornerPosX, topLeftCornerPosY, topLeftCornerTextureData[0], topLeftCornerTextureData[2], topLeftCornerTextureData[1], topLeftCornerTextureData[3], zOffset);
        GUIHelper.blit(poseStack, this.panelOriginX + 4, this.panelOriginY + 8, 0, 0, 162, 76, 0);
    }
}
