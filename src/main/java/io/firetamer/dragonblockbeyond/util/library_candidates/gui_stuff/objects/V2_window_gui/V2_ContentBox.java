package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.V2_window_gui;

import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GuiDrawingContext;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.advanced_window_gui.base.ContentBoxBehaviour;

public class V2_ContentBox extends V2_ContentBoxBehaviour {
    public int topLeftXPos = 0;
    public int topLeftYPos = 0;
    public int contentBoxWidth = 0;
    public int contentBoxHeight = 0;

    public V2_ContentBox(BasicBoxProperties properties) {
        super(properties);
    }

    public void draw(GuiDrawingContext drawContext) {
        contentBoxWidth = (int) (drawContext.parentWidth * percentWidthOfParent);
        contentBoxHeight = (int) (drawContext.parentHeight * percentHeightOfParent);

        topLeftXPos = getXPosForInteriorChildren(interiorPosition, drawContext, contentBoxWidth) + getXOffset(drawContext);
        topLeftYPos = getYPosForInteriorChildren(interiorPosition, drawContext, contentBoxHeight) + getYOffset(drawContext);
        int endX = topLeftXPos + contentBoxWidth;
        int endY = topLeftYPos + contentBoxHeight;

        if (shouldDrawContentBoxBackground) {
            if (shouldDrawBorder) {
                GUIHelper.drawBorderedArea(drawContext.poseStack, topLeftXPos, topLeftYPos,
                        contentBoxWidth, contentBoxHeight, borderFont, (int) drawContext.originZ);
            }

            if (isColor2Used) {
                GUIHelper.fillAreaWithColorGradient(drawContext.poseStack, topLeftXPos, topLeftYPos,
                        endX, endY, (int) drawContext.originZ,
                        color1.getRGBA(), color2.getRGBA());
            } else {
                GUIHelper.fillAreaWithColor(drawContext.poseStack, topLeftXPos, topLeftYPos,
                        endX, endY, (int) drawContext.originZ,
                        color1.getRGBA());
            }
        }
    }









    private int getXOffset(GuiDrawingContext parentDrawContext) {
        int xOffset;

        if (shouldUseDynamicOffset) {
            xOffset = (int) (parentDrawContext.parentWidth * xOffsetDynamic);
        } else {
            xOffset = xOffsetAbsolute;
        }

        return xOffset;
    }
    private int getYOffset(GuiDrawingContext parentDrawContext) {
        int yOffset;

        if (shouldUseDynamicOffset) {
            yOffset = (int) (parentDrawContext.parentHeight * yOffsetDynamic);
        } else {
            yOffset = yOffsetAbsolute;
        }

        return yOffset;
    }

    /**
     * int0 = XOrigin,
     * int1 = Width,
     * int2 = YOrigin,
     * int3 = Height,
     * int4 = xOffset,
     * int5 = yOffset
     */
    private int getLeftMargin() {
        int leftMarginOffset = 0;

        int[] topLeftCornerTextureData = borderFont.getTopLeftCornerTextureData();
        int[] leftBarTextureData = borderFont.getLeftBarTextureData();

        if (topLeftCornerTextureData[1] > leftBarTextureData[1]) {
            leftMarginOffset = topLeftCornerTextureData[1];
        }
        else if (topLeftCornerTextureData[1] < leftBarTextureData[1]) {
            leftMarginOffset = leftBarTextureData[1];
        }
        else {
            leftMarginOffset = topLeftCornerTextureData[1];
        }

        return leftMarginOffset;
    }
    private int getTopMargin() {
        int topMarginOffset = 0;

        int[] topLeftCornerTextureData = borderFont.getTopLeftCornerTextureData();
        int[] topBarTextureData = borderFont.getTopBarTextureData();

        if (topLeftCornerTextureData[3] > topBarTextureData[3]) {
            topMarginOffset = topLeftCornerTextureData[3];
        }
        else if (topLeftCornerTextureData[3] < topBarTextureData[3]) {
            topMarginOffset = topBarTextureData[3];
        }
        else {
            topMarginOffset = topLeftCornerTextureData[3];
        }

        return topMarginOffset;
    }
    private int getRightMargin() {
        int rightMarginOffset = 0;

        int[] bottomRightCornerTextureData = borderFont.getBottomRightCornerTextureData();
        int[] rightBarTextureData = borderFont.getRightBarTextureData();

        if (bottomRightCornerTextureData[1] > rightBarTextureData[1]) {
            rightMarginOffset = bottomRightCornerTextureData[1];
        }
        else if (bottomRightCornerTextureData[1] < rightBarTextureData[1]) {
            rightMarginOffset = rightBarTextureData[1];
        }
        else {
            rightMarginOffset = bottomRightCornerTextureData[1];
        }

        return rightMarginOffset;
    }
    private int getBottomMargin() {
        int bottomMarginOffset = 0;

        int[] bottomRightCornerTextureData = borderFont.getBottomRightCornerTextureData();
        int[] bottomBarTextureData = borderFont.getBottomBarTextureData();

        if (bottomRightCornerTextureData[3] > bottomBarTextureData[3]) {
            bottomMarginOffset = bottomRightCornerTextureData[3];
        }
        else if (bottomRightCornerTextureData[3] < bottomBarTextureData[3]) {
            bottomMarginOffset = bottomBarTextureData[3];
        }
        else {
            bottomMarginOffset = bottomRightCornerTextureData[3];
        }

        return bottomMarginOffset;
    }

    private int getXPosForInteriorChildren(V2_ContentBoxBehaviour.InteriorContentBoxPosition interiorPositionIn, GuiDrawingContext context, int contentBoxWidth) {
        int leftMarginOffset = 0;
        int rightMarginOffset = 0;

        if (shouldDrawBorder) {
            leftMarginOffset = getLeftMargin();
            rightMarginOffset = getRightMargin();
        }

        if (interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.TOP_LEFT || interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.MIDDLE_LEFT || interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_LEFT)
            return (int) context.originX + leftMarginOffset;

        if (interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.TOP_MIDDLE || interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.CENTER || interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_MIDDLE)
            return (int) (context.originX + (context.parentWidth / 2) - (contentBoxWidth / 2));

        if (interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.TOP_RIGHT || interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.MIDDLE_RIGHT || interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) context.originX + context.parentWidth - contentBoxWidth - rightMarginOffset;

        else { return 0; }
    }
    private int getYPosForInteriorChildren(V2_ContentBoxBehaviour.InteriorContentBoxPosition interiorPositionIn, GuiDrawingContext context, int contentBoxHeight) {
        int topMarginOffset = 0;
        int bottomMarginOffset = 0;

        if (shouldDrawBorder) {
            topMarginOffset = getTopMargin();
            bottomMarginOffset = getBottomMargin();
        }

        if (interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.TOP_LEFT || interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.TOP_MIDDLE || interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.TOP_RIGHT)
            return (int) context.originY + topMarginOffset;

        if (interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.MIDDLE_LEFT || interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.CENTER || interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.MIDDLE_RIGHT)
            return (int) (context.originY + (context.parentHeight / 2) - (contentBoxHeight / 2));

        if (interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_LEFT || interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_MIDDLE || interiorPositionIn == V2_ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) context.originY + context.parentHeight - contentBoxHeight - bottomMarginOffset;

        else { return 0; }
    }

    private int getXPosForExteriorChildren(V2_ContentBoxBehaviour.ExteriorContentBoxPosition exteriorPositionIn, GuiDrawingContext parentContext, int contentBoxWidth, int parentLeftMarginOffset, int parentRightMarginOffset) {
        int leftMarginOffset = 0;
        int rightMarginOffset = 0;

        if (shouldDrawBorder) {
            leftMarginOffset = getLeftMargin();
            rightMarginOffset = getRightMargin();
        }

        if (exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_BOTTOM || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_MIDDLE || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_TOP)
            return (int) parentContext.originX - contentBoxWidth - leftMarginOffset - parentLeftMarginOffset;

        else if (exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_LEFT || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_LEFT)
            return (int) parentContext.originX;

        else if (exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_MIDDLE || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_MIDDLE)
            return (int) (parentContext.originX + (parentContext.parentWidth / 2) - (contentBoxWidth / 2));

        else if (exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_RIGHT || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) parentContext.originX + parentContext.parentWidth - contentBoxWidth;

        else if (exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_BOTTOM || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_MIDDLE || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_TOP)
            return (int) parentContext.originX + parentContext.parentWidth + rightMarginOffset + parentRightMarginOffset;

        else { return 0; }
    }
    private int getYPosForExteriorChildren(V2_ContentBoxBehaviour.ExteriorContentBoxPosition exteriorPositionIn, GuiDrawingContext parentContext, int contentBoxHeight, int parentTopMarginOffset, int parentBottomMarginOffset) {
        int topMarginOffset = 0;
        int bottomMarginOffset = 0;

        if (shouldDrawBorder) {
            topMarginOffset = getTopMargin();
            bottomMarginOffset = getBottomMargin();
        }

        if (exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_LEFT || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_MIDDLE || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_RIGHT)
            return (int) parentContext.originY - contentBoxHeight - topMarginOffset - parentTopMarginOffset;

        else if (exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_TOP || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_TOP)
            return (int) parentContext.originY;

        else if (exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_MIDDLE || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_MIDDLE)
            return (int) (parentContext.originY + (parentContext.parentHeight / 2) - (contentBoxHeight / 2));

        else if (exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_BOTTOM || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_BOTTOM)
            return (int) parentContext.originY + parentContext.parentHeight - contentBoxHeight;

        else if (exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_LEFT || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_MIDDLE || exteriorPositionIn == V2_ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) parentContext.originY + parentContext.parentHeight + bottomMarginOffset + parentBottomMarginOffset;

        else { return 0; }
    }

}
