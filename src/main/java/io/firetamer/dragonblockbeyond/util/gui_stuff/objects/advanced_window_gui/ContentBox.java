package io.firetamer.dragonblockbeyond.util.gui_stuff.objects.advanced_window_gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.util.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.gui_stuff.GuiDrawingContext;
import io.firetamer.dragonblockbeyond.util.gui_stuff.objects.radial_menu.AbstractRadialMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class ContentBox extends ContentBoxBehaviour implements IContentBox {

    private final List<ContentBox> interiorItems;
    private final List<ContentBox> exteriorItems;

    protected ItemRenderer itemRenderer;
    protected Font font;

    public ContentBox(ContentBoxBehaviour.Properties properties, ContentBoxBehaviour.Children children) {
        super(properties, children);

        Minecraft mc = Minecraft.getInstance();

        this.font = mc.font;
        this.itemRenderer = mc.getItemRenderer();

        this.interiorItems = interiorDynamicItems;
        this.exteriorItems = exteriorPanels;
    }

    @NotNull
    private GuiDrawingContext createDrawingContext(PoseStack poseStack, int width, int height, float x, float y, float z) {
        return new GuiDrawingContext(poseStack, width, height, x, y, z, font, itemRenderer);
    }

    private void iterateInteriorElements(Consumer<ContentBox> consumer) {
        for (ContentBox item : interiorItems) {
            consumer.accept(item);
        }
    }

    private void iterateExteriorElements(Consumer<ContentBox> consumer) {
        for (ContentBox item : exteriorItems) {
            consumer.accept(item);
        }
    }

    private int getXPosForInteriorChildren(InteriorContentBoxPosition interiorPositionIn, GuiDrawingContext context, int contentBoxWidth) {
        int leftMarginOffset = 0;
        int rightMarginOffset = 0;

        if (shouldDrawBorder) {
            leftMarginOffset = getLeftMargin();
            rightMarginOffset = getRightMargin();
        }

        if (interiorPositionIn == InteriorContentBoxPosition.TOP_LEFT || interiorPositionIn == InteriorContentBoxPosition.MIDDLE_LEFT || interiorPositionIn == InteriorContentBoxPosition.BOTTOM_LEFT)
            return (int) context.originX + leftMarginOffset;

        if (interiorPositionIn == InteriorContentBoxPosition.TOP_MIDDLE || interiorPositionIn == InteriorContentBoxPosition.CENTER || interiorPositionIn == InteriorContentBoxPosition.BOTTOM_MIDDLE)
            return (int) (context.originX + (context.parentWidth / 2) - (contentBoxWidth / 2));

        if (interiorPositionIn == InteriorContentBoxPosition.TOP_RIGHT || interiorPositionIn == InteriorContentBoxPosition.MIDDLE_RIGHT || interiorPositionIn == InteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) context.originX + context.parentWidth - contentBoxWidth - rightMarginOffset;

        else { return 0; }
    }

    private int getYPosForInteriorChildren(InteriorContentBoxPosition interiorPositionIn, GuiDrawingContext context, int contentBoxHeight) {
        int topMarginOffset = 0;
        int bottomMarginOffset = 0;

        if (shouldDrawBorder) {
            topMarginOffset = getTopMargin();
            bottomMarginOffset = getBottomMargin();
        }

        if (interiorPositionIn == InteriorContentBoxPosition.TOP_LEFT || interiorPositionIn == InteriorContentBoxPosition.TOP_MIDDLE || interiorPositionIn == InteriorContentBoxPosition.TOP_RIGHT)
            return (int) context.originY + topMarginOffset;

        if (interiorPositionIn == InteriorContentBoxPosition.MIDDLE_LEFT || interiorPositionIn == InteriorContentBoxPosition.CENTER || interiorPositionIn == InteriorContentBoxPosition.MIDDLE_RIGHT)
            return (int) (context.originY + (context.parentHeight / 2) - (contentBoxHeight / 2));

        if (interiorPositionIn == InteriorContentBoxPosition.BOTTOM_LEFT || interiorPositionIn == InteriorContentBoxPosition.BOTTOM_MIDDLE || interiorPositionIn == InteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) context.originY + context.parentHeight - contentBoxHeight - bottomMarginOffset;

        else { return 0; }
    }

    private int getXPosForExteriorChildren(ExteriorContentBoxPosition exteriorPositionIn, GuiDrawingContext parentContext, int contentBoxWidth,
                                           int parentLeftMarginOffset, int parentRightMarginOffset) {
        int leftMarginOffset = 0;
        int rightMarginOffset = 0;

        if (shouldDrawBorder) {
            leftMarginOffset = getLeftMargin();
            rightMarginOffset = getRightMargin();
        }

        if (exteriorPositionIn == ExteriorContentBoxPosition.LEFT_BOTTOM || exteriorPositionIn == ExteriorContentBoxPosition.LEFT_MIDDLE || exteriorPositionIn == ExteriorContentBoxPosition.LEFT_TOP)
            return (int) parentContext.originX - contentBoxWidth - leftMarginOffset - parentLeftMarginOffset;

        else if (exteriorPositionIn == ExteriorContentBoxPosition.TOP_LEFT || exteriorPositionIn == ExteriorContentBoxPosition.BOTTOM_LEFT)
            return (int) parentContext.originX;

        else if (exteriorPositionIn == ExteriorContentBoxPosition.TOP_MIDDLE || exteriorPositionIn == ExteriorContentBoxPosition.BOTTOM_MIDDLE)
            return (int) (parentContext.originX + (parentContext.parentWidth / 2) - (contentBoxWidth / 2));

        else if (exteriorPositionIn == ExteriorContentBoxPosition.TOP_RIGHT || exteriorPositionIn == ExteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) parentContext.originX + parentContext.parentWidth - contentBoxWidth;

        else if (exteriorPositionIn == ExteriorContentBoxPosition.RIGHT_BOTTOM || exteriorPositionIn == ExteriorContentBoxPosition.RIGHT_MIDDLE || exteriorPositionIn == ExteriorContentBoxPosition.RIGHT_TOP)
            return (int) parentContext.originX + parentContext.parentWidth + rightMarginOffset + parentRightMarginOffset;

        else { return 0; }
    }

    private int getYPosForExteriorChildren(ExteriorContentBoxPosition exteriorPositionIn, GuiDrawingContext parentContext, int contentBoxHeight,
                                           int parentTopMarginOffset, int parentBottomMarginOffset) {
        int topMarginOffset = 0;
        int bottomMarginOffset = 0;

        if (shouldDrawBorder) {
            topMarginOffset = getTopMargin();
            bottomMarginOffset = getBottomMargin();
        }

        if (exteriorPositionIn == ExteriorContentBoxPosition.TOP_LEFT || exteriorPositionIn == ExteriorContentBoxPosition.TOP_MIDDLE || exteriorPositionIn == ExteriorContentBoxPosition.TOP_RIGHT)
            return (int) parentContext.originY - contentBoxHeight - topMarginOffset - parentTopMarginOffset;

        else if (exteriorPositionIn == ExteriorContentBoxPosition.LEFT_TOP || exteriorPositionIn == ExteriorContentBoxPosition.RIGHT_TOP)
            return (int) parentContext.originY;

        else if (exteriorPositionIn == ExteriorContentBoxPosition.LEFT_MIDDLE || exteriorPositionIn == ExteriorContentBoxPosition.RIGHT_MIDDLE)
            return (int) (parentContext.originY + (parentContext.parentHeight / 2) - (contentBoxHeight / 2));

        else if (exteriorPositionIn == ExteriorContentBoxPosition.LEFT_BOTTOM || exteriorPositionIn == ExteriorContentBoxPosition.RIGHT_BOTTOM)
            return (int) parentContext.originY + parentContext.parentHeight - contentBoxHeight;

        else if (exteriorPositionIn == ExteriorContentBoxPosition.BOTTOM_LEFT || exteriorPositionIn == ExteriorContentBoxPosition.BOTTOM_MIDDLE || exteriorPositionIn == ExteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) parentContext.originY + parentContext.parentHeight + bottomMarginOffset + parentBottomMarginOffset;

        else { return 0; }
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


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    public void drawInteriorChildren(PoseStack poseStack, int xIn, int yIn, float zIn, int widthIn, int heightIn) {
        iterateInteriorElements(item -> {
            item.drawInteriorChildren(createDrawingContext(poseStack, widthIn, heightIn, xIn, yIn, zIn));
        });
    }

    public void drawInteriorChildren(GuiDrawingContext drawContext) {
        int contentBoxWidth = (int) (drawContext.parentWidth * widthPercentOfParent);
        int contentBoxHeight = (int) (drawContext.parentHeight * heightPercentOfParent);
        int topLeftXPos = getXPosForInteriorChildren(interiorPosition, drawContext, contentBoxWidth) + getXOffset(drawContext);
        int topLeftYPos = getYPosForInteriorChildren(interiorPosition, drawContext, contentBoxHeight) + getYOffset(drawContext);

        if (shouldDrawContentBoxBackground) {
            if (shouldDrawBorder) {
                GUIHelper.drawBorderedArea(drawContext.poseStack, topLeftXPos, topLeftYPos,
                        contentBoxWidth, contentBoxHeight, borderFont, (int) drawContext.originZ);
            }

            if (isColor2Used) {
                GUIHelper.fillAreaWithColorGradient(drawContext.poseStack, topLeftXPos, topLeftYPos,
                        topLeftXPos + contentBoxWidth, topLeftYPos + contentBoxHeight, (int) drawContext.originZ,
                        color1.getRGBA(), color2.getRGBA());
            } else {
                GUIHelper.fillAreaWithColor(drawContext.poseStack, topLeftXPos, topLeftYPos,
                        topLeftXPos + contentBoxWidth, topLeftYPos + contentBoxHeight, (int) drawContext.originZ,
                        color1.getRGBA());
            }
        }

        drawInteriorChildren(drawContext.poseStack, topLeftXPos, topLeftYPos, drawContext.originZ, contentBoxWidth, contentBoxHeight);

        int leftMarginOffset = 0;
        int rightMarginOffset = 0;
        int topMarginOffset = 0;
        int bottomMarginOffset = 0;

        if (shouldDrawBorder) {
            leftMarginOffset = getLeftMargin();
            rightMarginOffset = getRightMargin();
            topMarginOffset = getTopMargin();
            bottomMarginOffset = getBottomMargin();
        }

        drawExteriorChildren(drawContext.poseStack, topLeftXPos, topLeftYPos, drawContext.originZ, contentBoxWidth, contentBoxHeight,
                leftMarginOffset, rightMarginOffset, topMarginOffset, bottomMarginOffset);
    }

    public void drawExteriorChildren(PoseStack poseStack, int xIn, int yIn, float zIn, int widthIn, int heightIn, int parentLeftMarginOffset, int parentRightMarginOffset, int parentTopMarginOffset, int parentBottomMarginOffset) {
        iterateExteriorElements(item ->
                item.drawExteriorChildren(createDrawingContext(poseStack, widthIn, heightIn, xIn, yIn, zIn),
                        parentLeftMarginOffset, parentRightMarginOffset, parentTopMarginOffset, parentBottomMarginOffset));
    }

    public void drawExteriorChildren(GuiDrawingContext drawContext, int parentLeftMarginOffset, int parentRightMarginOffset, int parentTopMarginOffset, int parentBottomMarginOffset) {
        int contentBoxWidth = (int) (drawContext.parentWidth * widthPercentOfParent);
        int contentBoxHeight = (int) (drawContext.parentHeight * heightPercentOfParent);
        int topLeftXPos = getXPosForExteriorChildren(exteriorPosition, drawContext, contentBoxWidth,
                parentLeftMarginOffset, parentRightMarginOffset) + getXOffset(drawContext);
        int topLeftYPos = getYPosForExteriorChildren(exteriorPosition, drawContext, contentBoxHeight,
                parentTopMarginOffset, parentBottomMarginOffset) + getYOffset(drawContext);

        if (shouldDrawContentBoxBackground) {
            if (shouldDrawBorder) {
                GUIHelper.drawBorderedArea(drawContext.poseStack, topLeftXPos, topLeftYPos,
                        contentBoxWidth, contentBoxHeight, borderFont, (int) drawContext.originZ);
            }

            if (isColor2Used) {
                GUIHelper.fillAreaWithColorGradient(drawContext.poseStack, topLeftXPos, topLeftYPos,
                        topLeftXPos + contentBoxWidth, topLeftYPos + contentBoxHeight, (int) drawContext.originZ,
                        color1.getRGBA(), color2.getRGBA());
            } else {
                GUIHelper.fillAreaWithColor(drawContext.poseStack, topLeftXPos, topLeftYPos,
                        topLeftXPos + contentBoxWidth, topLeftYPos + contentBoxHeight, (int) drawContext.originZ,
                        color1.getRGBA());
            }
        }

        drawInteriorChildren(drawContext.poseStack, topLeftXPos, topLeftYPos, drawContext.originZ, contentBoxWidth, contentBoxHeight);

        int leftMarginOffset = 0;
        int rightMarginOffset = 0;
        int topMarginOffset = 0;
        int bottomMarginOffset = 0;

        if (shouldDrawBorder) {
            leftMarginOffset = getLeftMargin();
            rightMarginOffset = getRightMargin();
            topMarginOffset = getTopMargin();
            bottomMarginOffset = getBottomMargin();
        }

        drawExteriorChildren(drawContext.poseStack, topLeftXPos, topLeftYPos, drawContext.originZ, contentBoxWidth, contentBoxHeight,
                leftMarginOffset, rightMarginOffset, topMarginOffset, bottomMarginOffset);
    }

    @Override
    public void draw(GuiDrawingContext drawContext) {
        int contentBoxWidth = (int) (drawContext.parentWidth * widthPercentOfParent);
        int contentBoxHeight = (int) (drawContext.parentHeight * heightPercentOfParent);

        int topLeftXPos = getXPosForInteriorChildren(interiorPosition, drawContext, contentBoxWidth) + getXOffset(drawContext);
        int topLeftYPos = getYPosForInteriorChildren(interiorPosition, drawContext, contentBoxHeight) + getYOffset(drawContext);
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

        drawInteriorChildren(drawContext.poseStack, topLeftXPos, topLeftYPos, drawContext.originZ, contentBoxWidth, contentBoxHeight);

        int leftMarginOffset = 0;
        int rightMarginOffset = 0;
        int topMarginOffset = 0;
        int bottomMarginOffset = 0;

        if (shouldDrawBorder) {
            leftMarginOffset = getLeftMargin();
            rightMarginOffset = getRightMargin();
            topMarginOffset = getTopMargin();
            bottomMarginOffset = getBottomMargin();
        }

        drawExteriorChildren(drawContext.poseStack, topLeftXPos, topLeftYPos, drawContext.originZ, contentBoxWidth, contentBoxHeight,
                leftMarginOffset, rightMarginOffset, topMarginOffset, bottomMarginOffset);
    }
}
