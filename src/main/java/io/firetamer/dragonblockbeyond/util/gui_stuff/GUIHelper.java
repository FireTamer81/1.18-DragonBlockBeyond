package io.firetamer.dragonblockbeyond.util.gui_stuff;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import io.firetamer.dragonblockbeyond.util.library_candidates.FireLibColor;
import io.firetamer.dragonblockbeyond.util.gui_stuff.objects.texture_objects.BorderTextureObject;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class GUIHelper {


    /**
     * A method that draws a bordered area. The position and size values are used for the interior square, with the border drawn just outside the given area.
     * @param poseStack Any old PoseStack
     * @param panelTopLeftX This is the topLeftX value for the panel, or more accurately the area inside the borders.
     * @param panelTopLeftY The Y value for the above X value
     * @param panelWidth This determined how wide the panel interior is
     * @param panelHeight This determines out tall the panel interior is
     * @param borderTexture This is an object that holds all of the UV data for the border textures. Instantiated in the TextureHandler class on mod load.
     * @param zOffset This is a weird value that in most cases only needs ot be "0". I believe it forcefully pushes what's drawn in front or behind other objects that would have been drawn before it. (Could be used for overlapping panels based on which was last interacted with)
     */
    public static void drawBorderedArea(PoseStack poseStack, int panelTopLeftX, int panelTopLeftY, int panelWidth, int panelHeight, BorderTextureObject borderTexture, int zOffset) {
        /**
         * int0 = XOrigin
         * int1 = Width
         * int2 = YOrigin
         * int3 = Height
         * int4 = xOffset
         * int5 = yOffset
         */
        int[] topLeftCornerTextureData = borderTexture.getTopLeftCornerTextureData();
        int[] topBarTextureData = borderTexture.getTopBarTextureData();
        int[] topRightCornerTextureData = borderTexture.getTopRightCornerTextureData();
        int[] rightBarTextureData = borderTexture.getRightBarTextureData();
        int[] bottomRightCornerTextureData = borderTexture.getBottomRightCornerTextureData();
        int[] bottomBarTextureData = borderTexture.getBottomBarTextureData();
        int[] bottomLeftCornerTextureData = borderTexture.getBottomLeftCornerTextureData();
        int[] leftBarTextureData = borderTexture.getLeftBarTextureData();

        /**************************************************************************************************************/

        int topLeftCornerPosX = panelTopLeftX - topLeftCornerTextureData[1] + topLeftCornerTextureData[4];
        int topLeftCornerPosY = panelTopLeftY - topLeftCornerTextureData[3] + topLeftCornerTextureData[5];

        int topRightCornerPosX = panelTopLeftX  + panelWidth + topRightCornerTextureData[4];
        int topRightCornerPosY = panelTopLeftY - topRightCornerTextureData[3] + topRightCornerTextureData[5];

        int bottomRightCornerPosX = panelTopLeftX + panelWidth + bottomRightCornerTextureData[4];
        int bottomRightCornerPosY = panelTopLeftY + panelHeight + bottomRightCornerTextureData[5];

        int bottomLeftCornerPosX = panelTopLeftX - bottomLeftCornerTextureData[1] + bottomLeftCornerTextureData[4];
        int bottomLeftCornerPosY = panelTopLeftY + panelHeight + bottomLeftCornerTextureData[5];

        int topBarPosX = topLeftCornerPosX + topLeftCornerTextureData[1] + topBarTextureData[4];
        int topBarPosY = topLeftCornerPosY + topBarTextureData[5];

        int rightBarPosX = topRightCornerPosX + rightBarTextureData[4];
        int rightBarPosY = topRightCornerPosY + topRightCornerTextureData[3] + rightBarTextureData[5];

        int bottomBarPosX = bottomLeftCornerPosX + bottomLeftCornerTextureData[1] + bottomBarTextureData[4];
        int bottomBarPosY = bottomLeftCornerPosY + bottomBarTextureData[5];

        int leftBarPosX = topLeftCornerPosX + leftBarTextureData[4];
        int leftBarPosY = topLeftCornerPosY + topLeftCornerTextureData[3] + leftBarTextureData[5];



        /**************************************************************************************************************/

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, borderTexture.getTextureResource());

        //TopLeftCorner
        blit(poseStack, topLeftCornerPosX, topLeftCornerPosY, topLeftCornerTextureData[0], topLeftCornerTextureData[2],
                topLeftCornerTextureData[1], topLeftCornerTextureData[3], zOffset);

        //TopRightCorner
        blit(poseStack, topRightCornerPosX, topRightCornerPosY, topRightCornerTextureData[0], topRightCornerTextureData[2],
                topRightCornerTextureData[1], topRightCornerTextureData[3], zOffset);

        //BottomRightCorner
        blit(poseStack, bottomRightCornerPosX, bottomRightCornerPosY, bottomRightCornerTextureData[0], bottomRightCornerTextureData[2],
                bottomRightCornerTextureData[1], bottomRightCornerTextureData[3], zOffset);

        //BottomLeftCorner
        blit(poseStack, bottomLeftCornerPosX, bottomLeftCornerPosY, bottomLeftCornerTextureData[0], bottomLeftCornerTextureData[2],
                bottomLeftCornerTextureData[1], bottomLeftCornerTextureData[3], zOffset);

        //TopBar
        drawHorizontalBarTiledTexture(poseStack, topBarPosX, topBarPosY, panelWidth, zOffset, topBarTextureData[0], topBarTextureData[2],
                topBarTextureData[1], topBarTextureData[3]);

        //RightBar
        drawVerticalBarTiledTexture(poseStack, rightBarPosX, rightBarPosY, panelHeight, zOffset, rightBarTextureData[0],
                rightBarTextureData[2], rightBarTextureData[1], rightBarTextureData[3]);

        //BottomBar
        drawHorizontalBarTiledTexture(poseStack, bottomBarPosX, bottomBarPosY, panelWidth, zOffset, bottomBarTextureData[0],
                bottomBarTextureData[2], bottomBarTextureData[1], bottomBarTextureData[3]);

        //LeftBar
        drawVerticalBarTiledTexture(poseStack, leftBarPosX, leftBarPosY, panelHeight, zOffset, leftBarTextureData[0],
                leftBarTextureData[2], leftBarTextureData[1], leftBarTextureData[3]);
    }

    /**
     * A method that draws a "panel" with a border around it. The sizes given are for the fill area, and the border is placed just outside of the color filled area.
     * So, depending on the "border style" used, the panel will be slightly larger than the size given (this is for later on when I want panels to be able to be "maximized" to fill the entire screen)
     * @param poseStack Any old PoseStack
     * @param panelTopLeftX This is the topLeftX value for the panel, or more accurately the area inside the borders.
     * @param panelTopLeftY The Y value for the above X value
     * @param panelWidth This determined how wide the panel interior is
     * @param panelHeight This determines out tall the panel interior is
     * @param borderTexture This is an object that holds all of the UV data for the border textures. Instantiated in the TextureHandler class on mod load.
     * @param fillColor1 This color value is required, but if you want the panel transparent you can just pass a color with 255 for the alpha value
     * @param fillColor2 This color is optional, and placing null in it's parameter slot will simply make the method dra a solid color.
     * @param zOffset This is a weird value that in most cases only needs ot be "0". I believe it forcefully pushes what's drawn in front or behind other objects that would have been drawn before it. (Could be used for overlapping panels based on which was last interacted with)
     */
    public static void drawBorderedFilledPanel(PoseStack poseStack, int panelTopLeftX, int panelTopLeftY, int panelWidth, int panelHeight, BorderTextureObject borderTexture, FireLibColor fillColor1, FireLibColor fillColor2, int zOffset) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, borderTexture.getTextureResource());

        //Interior Fill
        if(fillColor2 == null) {
            fillAreaWithColor(poseStack, panelTopLeftX, panelTopLeftY, panelTopLeftX + panelWidth, panelTopLeftY + panelHeight, 0, fillColor1.getRGBA());
        } else {
            fillAreaWithColorGradient(poseStack, panelTopLeftX, panelTopLeftY, panelTopLeftX + panelWidth, panelTopLeftY + panelHeight, zOffset, fillColor1.getRGBA(), fillColor2.getRGBA());
        }

        drawBorderedArea(poseStack, panelTopLeftX, panelTopLeftY, panelTopLeftX + panelWidth, panelTopLeftY + panelHeight, borderTexture, zOffset);
    }

    private static void drawHorizontalBarTiledTexture(PoseStack poseStack, int barOriginX, int barOriginY, int barWidth, int zOffset, int barTextureOriginX, int barTextureOriginY, int barTextureWidth, int barTextureHeight) {
        // Calculate the amount of tiles
        //final int tileCount = (int) Math.ceil((float) barWidth / barTextureWidth);
        int tileCount = barWidth / barTextureWidth;
        int remainderWidth = barWidth % barTextureWidth;

        int lastBarPosX = 0;
        int i = 0;

        while(i < tileCount) {
            int barPosX = barOriginX + (barTextureWidth * i);

            blit(poseStack, barPosX, barOriginY, barTextureOriginX, barTextureOriginY, barTextureWidth, barTextureHeight, zOffset);

            i++;

            lastBarPosX = barPosX + barTextureWidth;
        }

        blit(poseStack, lastBarPosX, barOriginY, barTextureOriginX, barTextureOriginY, remainderWidth, barTextureHeight, zOffset);
    }

    private static void drawVerticalBarTiledTexture(PoseStack poseStack, int barOriginX, int barOriginY, int barHeight, int zOffset, int barTextureOriginX, int barTextureOriginY, int barTextureWidth, int barTextureHeight) {
        // Calculate the amount of tiles
        //final int tileCount = (int) Math.ceil((float) barHeight / barTextureHeight);
        int tileCount = barHeight / barTextureHeight;
        int remainderHeight = barHeight % barTextureHeight;

        int lastBarHeightPosY = 0;
        int i = 0;

        while(i < tileCount) {
            int barPosY = barOriginY + (barTextureHeight * i);

            blit(poseStack, barOriginX, barPosY, barTextureOriginX, barTextureOriginY, barTextureWidth, barTextureHeight, zOffset);

            i++;
            lastBarHeightPosY = barPosY + barTextureHeight;
        }

        blit(poseStack, barOriginX, lastBarHeightPosY, barTextureOriginX, barTextureOriginY, barTextureWidth, remainderHeight, zOffset);
    }




    //TODO I might be able to use this method to make a background that doesn't render the world outside the GUI (Character Creation Screen and such)
    //I would do this by making a 256x256 texture (only size that works with this method), having everything horizontal so it can be stretched without looking funky,
    //And then stretching it using this method to fit the entire screen.
    /**
     * A method that renders background large textures, scaling them down or tilling them in order to fit the size requirements.
     * @apiNote this method shouldn't be used for small textures as due to the tilling, FPS will be reduced significantly
     * @param matrix the matrix
     * @param resource the resources to renderer
     * @param texSideWidth the side width of the texture
     * @param texSideHeight the side height of the texture
     * @param left the top x coordinate to start rendering from
     * @param top the top y coordinate to start rendering from
     * @param width the target width
     * @param height the target height
     * @param textureWidth the texture width
     * @param textureHeight the texture height
     */
    public static void renderBackgroundTexture(PoseStack matrix, ResourceLocation resource, int texSideWidth, int texSideHeight, int left, int top, int width, int height, int textureWidth, int textureHeight) {
        final var sideWidth = Math.min(texSideWidth, width / 2);
        final var sideHeight = Math.min(texSideHeight, height / 2);

        // Adjustment for small odd-height and odd-width GUIs
        final var leftWidth = sideWidth < texSideWidth ? sideWidth + (width % 2) : sideWidth;
        final var topHeight = sideHeight < texSideHeight ? sideHeight + (height % 2) : sideHeight;

        // Calculate texture centre
        final int texCenterWidth = textureWidth - texSideWidth * 2, texCenterHeight = textureHeight - texSideHeight * 2;
        final int centerWidth = width - leftWidth - sideWidth, centerHeight = height - topHeight - sideHeight;

        // Calculate the corner positions
        final var leftEdgeEnd = left + leftWidth;
        final var rightEdgeStart = leftEdgeEnd + centerWidth;
        final var topEdgeEnd = top + topHeight;
        final var bottomEdgeStart = topEdgeEnd + centerHeight;
        RenderSystem.setShaderTexture(0, resource);

        // Top Left Corner
        Screen.blit(matrix, left, top, 0, 0, leftWidth, topHeight, textureWidth, textureHeight);
        // Bottom Left Corner
        Screen.blit(matrix, left, bottomEdgeStart, 0, textureHeight - sideHeight, leftWidth, sideHeight, textureWidth, textureHeight);

        // Middle
        if (centerWidth > 0) {
            // Top Middle
            blitTiled(matrix, leftEdgeEnd, top, centerWidth, topHeight, texSideWidth, 0, texCenterWidth, texSideHeight, textureWidth, textureHeight);
            if (centerHeight > 0) {
                // Center
                blitTiled(matrix, leftEdgeEnd, topEdgeEnd, centerWidth, centerHeight, texSideWidth, texSideHeight, texCenterWidth, texCenterHeight, textureWidth, textureHeight);
            }
            // Bottom Middle
            blitTiled(matrix, leftEdgeEnd, bottomEdgeStart, centerWidth, sideHeight, texSideWidth, textureHeight - sideHeight, texCenterWidth, texSideHeight, textureWidth, textureHeight);
        }

        if (centerHeight > 0) {
            // Left Middle
            blitTiled(matrix, left, topEdgeEnd, leftWidth, centerHeight, 0, texSideHeight, texSideWidth, texCenterHeight, textureWidth, textureHeight);
            // Right Middle
            blitTiled(matrix, rightEdgeStart, topEdgeEnd, sideWidth, centerHeight, textureWidth - sideWidth, texSideHeight, texSideWidth, texCenterHeight, textureWidth, textureHeight);
        }

        // Top Right Corner
        Screen.blit(matrix, rightEdgeStart, top, textureWidth - sideWidth, 0, sideWidth, topHeight, textureWidth, textureHeight);
        // Bottom Right Corner
        GuiComponent.blit(matrix, rightEdgeStart, bottomEdgeStart, textureWidth - sideWidth, textureHeight - sideHeight, sideWidth, sideHeight, textureWidth, textureHeight);
    }

    /**
     * Renders a tiled texture.
     */
    public static void blitTiled(PoseStack matrix, int x, int y, int width, int height, int texX, int texY, int texDrawWidth, int texDrawHeight, int textureWidth, int textureHeight) {
        // Calculate the amount of tiles
        final int xTiles = (int) Math.ceil((float) width / texDrawWidth), yTiles = (int) Math.ceil((float) height / texDrawHeight);

        var drawWidth = width;
        var drawHeight = height;
        for (var tileX = 0; tileX < xTiles; tileX++) {
            for (var tileY = 0; tileY < yTiles; tileY++) {
                final var renderWidth = Math.min(drawWidth, texDrawWidth);
                final var renderHeight = Math.min(drawHeight, texDrawHeight);
                Screen.blit(matrix, x + texDrawWidth * tileX, y + texDrawHeight * tileY, texX, texY, renderWidth, renderHeight, textureWidth, textureHeight);
                // We rendered a tile
                drawHeight -= texDrawHeight;
            }
            drawWidth -= texDrawWidth;
            drawHeight = height;
        }
    }




    /******************************************************************************************************************/
    //More Accessible Copy Methods
    /******************************************************************************************************************/

    public static void fillAreaWithColor(PoseStack poseStack, int originX, int originY, int endX, int endY, int zOffset, int color) {
        if (originX < endX) {
            int i = originX;
            originX = endX;
            endX = i;
        }

        if (originY < endY) {
            int j = originY;
            originY = endY;
            endY = j;
        }

        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        float f3 = (float)(color >> 24 & 255) / 255.0F;

        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(poseStack.last().pose(), (float)originX, (float)endY, zOffset).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(poseStack.last().pose(), (float)endX, (float)endY, zOffset).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(poseStack.last().pose(), (float)endX, (float)originY, zOffset).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(poseStack.last().pose(), (float)originX, (float)originY, zOffset).color(f, f1, f2, f3).endVertex();
        bufferbuilder.end();

        BufferUploader.end(bufferbuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void fillAreaWithColorGradient(PoseStack poseStack, int originX, int originY, int endX, int endY, int zOffset, int color1, int color2) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        /**************************************************************************************************************/
        float f = (float)(color1 >> 24 & 255) / 255.0F;
        float f1 = (float)(color1 >> 16 & 255) / 255.0F;
        float f2 = (float)(color1 >> 8 & 255) / 255.0F;
        float f3 = (float)(color1 & 255) / 255.0F;

        float f4 = (float)(color2 >> 24 & 255) / 255.0F;
        float f5 = (float)(color2 >> 16 & 255) / 255.0F;
        float f6 = (float)(color2 >> 8 & 255) / 255.0F;
        float f7 = (float)(color2 & 255) / 255.0F;

        Matrix4f matrix4f = poseStack.last().pose();

        bufferBuilder.vertex(matrix4f, (float)endX, (float)originY, (float)zOffset).color(f1, f2, f3, f).endVertex();
        bufferBuilder.vertex(matrix4f, (float)originX, (float)originY, (float)zOffset).color(f1, f2, f3, f).endVertex();
        bufferBuilder.vertex(matrix4f, (float)originX, (float)endY, (float)zOffset).color(f5, f6, f7, f4).endVertex();
        bufferBuilder.vertex(matrix4f, (float)endX, (float)endY, (float)zOffset).color(f5, f6, f7, f4).endVertex();

        /**************************************************************************************************************/

        tesselator.end();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }







    public static void blit(PoseStack poseStack, int p_93230_, int p_93231_, int p_93232_, int p_93233_, int p_93234_, int p_93235_, int zOffset) {
        blit(poseStack, p_93230_, p_93231_, zOffset, (float)p_93232_, (float)p_93233_, p_93234_, p_93235_, 256, 256);
    }

    public static void blit(PoseStack poseStack, int p_93145_, int p_93146_, int p_93147_, float p_93148_, float p_93149_, int p_93150_, int p_93151_, int p_93152_, int p_93153_) {
        innerBlit(poseStack, p_93145_, p_93145_ + p_93150_, p_93146_, p_93146_ + p_93151_, p_93147_, p_93150_, p_93151_, p_93148_, p_93149_, p_93152_, p_93153_);
    }

    private static void innerBlit(PoseStack poseStack, int p_93189_, int p_93190_, int p_93191_, int p_93192_, int p_93193_, int p_93194_, int p_93195_, float p_93196_, float p_93197_, int p_93198_, int p_93199_) {
        innerBlit(poseStack.last().pose(), p_93189_, p_93190_, p_93191_, p_93192_, p_93193_, (p_93196_ + 0.0F) / (float)p_93198_, (p_93196_ + (float)p_93194_) / (float)p_93198_, (p_93197_ + 0.0F) / (float)p_93199_, (p_93197_ + (float)p_93195_) / (float)p_93199_);
    }

    private static void innerBlit(Matrix4f matrix4f, int p_93114_, int p_93115_, int p_93116_, int p_93117_, int p_93118_, float p_93119_, float p_93120_, float p_93121_, float p_93122_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, (float)p_93114_, (float)p_93117_, (float)p_93118_).uv(p_93119_, p_93122_).endVertex();
        bufferbuilder.vertex(matrix4f, (float)p_93115_, (float)p_93117_, (float)p_93118_).uv(p_93120_, p_93122_).endVertex();
        bufferbuilder.vertex(matrix4f, (float)p_93115_, (float)p_93116_, (float)p_93118_).uv(p_93120_, p_93121_).endVertex();
        bufferbuilder.vertex(matrix4f, (float)p_93114_, (float)p_93116_, (float)p_93118_).uv(p_93119_, p_93121_).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }
}
