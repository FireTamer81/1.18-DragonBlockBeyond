package io.firetamer.dragonblockbeyond.util.guiHelpers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class GUIElementCreator {

    //TODO finish the method below to use a set of individual textures to draw a sizeable panel filled with an rgba color
    //Right now I need to do the math stuff to make variables for the blit methods that draw the correct pieces at the correct places.
    //I also need to figure out how I want the texture map to be laid out since the math will be predetermined.
    //Then I need ot document how it need to be laid out.
    //After, I might start adding this stuff ot the library mod (If I can figure that stuff out)
    //Also, the "texturePadding" int is the space between texture pieces, so they can be easier identified when just looking at the texture if
    //the user of this method so wants it that way.

    /*
    TopLeftCorner
    TopRightCorner
    BottomLeftCorner
    BottomRightCorner

    TopHorizontalBar
    RightVerticalBar
    BottomHorizontalBar
    LeftVerticalBar
     */

    public static void drawBordered_FilledPanel(PoseStack poseStack, int panelTopLeftX, int panelTopLeftY, int panelWidth, int panelHeight,
                                          int textureSetTopLeftX, int textureSetTopLeftY, ResourceLocation textureResource, int texturePadding,
                                          int topLeftCornerWidth, int topLeftCornerHeight, int topLeftCornerOffsetX, int topLeftCornerOffsetY,
                                          int topRightCornerWidth, int topRightCornerHeight, int topRightCornerOffsetX, int topRightCornerOffsetY,
                                          int bottomLeftCornerWidth, int bottomLeftCornerHeight, int bottomLeftCornerOffsetX, int bottomLeftCornerOffsetY,
                                          int bottomRightCornerWidth, int bottomRightCornerHeight, int bottomRightCornerOffsetX, int bottomRightCornerOffsetY) {


        int topLeftCornerTextureX = textureSetTopLeftX;
        int topRightCornerTextureX = topLeftCornerTextureX + topLeftCornerWidth + texturePadding;
        int bottomLeftCornerTextureX = topRightCornerTextureX + topRightCornerWidth + texturePadding;
        int bottomRightCornerTextureX = bottomLeftCornerTextureX + bottomLeftCornerWidth + texturePadding;

        int topLeftCornerPosX = panelTopLeftX + topLeftCornerOffsetX;
        int topRightCornerPosX = panelTopLeftX + panelWidth - topRightCornerWidth + topRightCornerOffsetX;
        int bottomLeftCornerPosX = panelTopLeftX + bottomLeftCornerOffsetX;
        int bottomRightCornerPosX = panelTopLeftX + panelWidth - topRightCornerWidth + bottomRightCornerOffsetX;

        int topLeftCornerPosY = panelTopLeftY + topLeftCornerOffsetY;
        int topRightCornerPosY = panelTopLeftY + topRightCornerOffsetY;
        int bottomLeftCornerPosY = panelTopLeftY + panelHeight - bottomLeftCornerHeight + bottomLeftCornerOffsetY;
        int bottomRightCornerPosY = panelTopLeftY + panelHeight - bottomRightCornerHeight + bottomRightCornerOffsetY;

        /**************************************************************************************************************/

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, textureResource);

        //TopLeftCorner
        GUIHelper.blit(poseStack, topLeftCornerPosX, topLeftCornerPosY, topLeftCornerTextureX, textureSetTopLeftY, topLeftCornerWidth, topLeftCornerHeight, 0);

        //TopRightCorner
        GUIHelper.blit(poseStack, topRightCornerPosX, topRightCornerPosY, topRightCornerTextureX, textureSetTopLeftY, topRightCornerWidth, topRightCornerHeight, 0);

        //BottomLeftCorner
        GUIHelper.blit(poseStack, bottomLeftCornerPosX, bottomLeftCornerPosY, bottomLeftCornerTextureX, textureSetTopLeftY, bottomLeftCornerWidth, bottomLeftCornerHeight, 0);

        //BottomRightCorner
        GUIHelper.blit(poseStack, bottomRightCornerPosX, bottomRightCornerPosY, bottomRightCornerTextureX, textureSetTopLeftY, bottomRightCornerWidth, bottomRightCornerHeight, 0);


    }
}
