package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.advanced_window_gui.base;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.util.library_candidates.FireLibColor;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GuiDrawingContext;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.advanced_window_gui.WidgetEventListener;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.texture_objects.SimpleSpriteObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BasicButton extends ContentBox implements WidgetEventListener {
    //TODO These three things can be moved to the Behaviour class after I figure out rendering text better.
    private SimpleSpriteObject spriteIcon;
    private Component buttonText;
    private int buttonTextColor;

    public BasicButton(BasicButtonProperties properties, ExteriorChildren children) {
        super(properties, children);
    }

    @NotNull
    private GuiDrawingContext createDrawingContext(PoseStack poseStack, int width, int height, float x, float y, float z) {
        return new GuiDrawingContext(poseStack, width, height, x, y, z, font, itemRenderer);
    }

    private void iterateExteriorElements(Consumer<ContentBox> consumer) {
        for (ContentBox item : exteriorChildren) {
            consumer.accept(item);
        }
    }
    private void iterateExteriorButtons(Consumer<BasicButton> consumer) {
        int numItems = exteriorButtons.size();

        for (int i = 0; i < numItems; i++) {
            BasicButton item = exteriorButtons.get(i);
            consumer.accept(item);
        }
    }

    private int getXPosForInteriorChildren(ContentBoxBehaviour.InteriorContentBoxPosition interiorPositionIn, GuiDrawingContext context, int contentBoxWidth) {
        int leftMarginOffset = 0;
        int rightMarginOffset = 0;

        if (shouldDrawBorder) {
            leftMarginOffset = getLeftMargin();
            rightMarginOffset = getRightMargin();
        }

        if (interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.TOP_LEFT || interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.MIDDLE_LEFT || interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_LEFT)
            return (int) context.originX + leftMarginOffset;

        if (interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.TOP_MIDDLE || interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.CENTER || interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_MIDDLE)
            return (int) (context.originX + (context.parentWidth / 2) - (contentBoxWidth / 2));

        if (interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.TOP_RIGHT || interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.MIDDLE_RIGHT || interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) context.originX + context.parentWidth - contentBoxWidth - rightMarginOffset;

        else { return 0; }
    }
    private int getYPosForInteriorChildren(ContentBoxBehaviour.InteriorContentBoxPosition interiorPositionIn, GuiDrawingContext context, int contentBoxHeight) {
        int topMarginOffset = 0;
        int bottomMarginOffset = 0;

        if (shouldDrawBorder) {
            topMarginOffset = getTopMargin();
            bottomMarginOffset = getBottomMargin();
        }

        if (interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.TOP_LEFT || interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.TOP_MIDDLE || interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.TOP_RIGHT)
            return (int) context.originY + topMarginOffset;

        if (interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.MIDDLE_LEFT || interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.CENTER || interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.MIDDLE_RIGHT)
            return (int) (context.originY + (context.parentHeight / 2) - (contentBoxHeight / 2));

        if (interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_LEFT || interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_MIDDLE || interiorPositionIn == ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) context.originY + context.parentHeight - contentBoxHeight - bottomMarginOffset;

        else { return 0; }
    }

    private int getXPosForExteriorChildren(ContentBoxBehaviour.ExteriorContentBoxPosition exteriorPositionIn, GuiDrawingContext parentContext, int contentBoxWidth, int parentLeftMarginOffset, int parentRightMarginOffset) {
        int leftMarginOffset = 0;
        int rightMarginOffset = 0;

        if (shouldDrawBorder) {
            leftMarginOffset = getLeftMargin();
            rightMarginOffset = getRightMargin();
        }

        if (exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_BOTTOM || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_MIDDLE || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_TOP)
            return (int) parentContext.originX - contentBoxWidth - leftMarginOffset - parentLeftMarginOffset;

        else if (exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_LEFT || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_LEFT)
            return (int) parentContext.originX;

        else if (exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_MIDDLE || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_MIDDLE)
            return (int) (parentContext.originX + (parentContext.parentWidth / 2) - (contentBoxWidth / 2));

        else if (exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_RIGHT || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) parentContext.originX + parentContext.parentWidth - contentBoxWidth;

        else if (exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_BOTTOM || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_MIDDLE || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_TOP)
            return (int) parentContext.originX + parentContext.parentWidth + rightMarginOffset + parentRightMarginOffset;

        else { return 0; }
    }
    private int getYPosForExteriorChildren(ContentBoxBehaviour.ExteriorContentBoxPosition exteriorPositionIn, GuiDrawingContext parentContext, int contentBoxHeight, int parentTopMarginOffset, int parentBottomMarginOffset) {
        int topMarginOffset = 0;
        int bottomMarginOffset = 0;

        if (shouldDrawBorder) {
            topMarginOffset = getTopMargin();
            bottomMarginOffset = getBottomMargin();
        }

        if (exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_LEFT || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_MIDDLE || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_RIGHT)
            return (int) parentContext.originY - contentBoxHeight - topMarginOffset - parentTopMarginOffset;

        else if (exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_TOP || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_TOP)
            return (int) parentContext.originY;

        else if (exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_MIDDLE || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_MIDDLE)
            return (int) (parentContext.originY + (parentContext.parentHeight / 2) - (contentBoxHeight / 2));

        else if (exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_BOTTOM || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_BOTTOM)
            return (int) parentContext.originY + parentContext.parentHeight - contentBoxHeight;

        else if (exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_LEFT || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_MIDDLE || exteriorPositionIn == ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_RIGHT)
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

    public boolean isWithinHoverArea(int mouseX, int mouseY) {
        return mouseX >= this.boxTopLeftX
                && mouseX <= this.boxEndX
                && mouseY >= this.boxTopLeftY
                && mouseY <= this.boxEndY;
    }


    /* -------------------------------------------------------------------------------------------------------------- */


    public static void drawCenteredString(PoseStack poseStack, Font font, Component buttonText, int xOrigin, int yOrigin, FireLibColor color) {
        FormattedCharSequence formattedcharsequence = buttonText.getVisualOrderText();
        font.drawShadow(poseStack, formattedcharsequence, (float)(xOrigin - font.width(formattedcharsequence) / 2), (float)yOrigin, color.getRGBA());
    }

    public static void drawString(PoseStack poseStack, Font font, Component buttonText, int xOrigin, int yOrigin, FireLibColor color) {
        FormattedCharSequence formattedcharsequence = buttonText.getVisualOrderText();
        font.drawShadow(poseStack, formattedcharsequence, (float)xOrigin, (float)yOrigin, color.getRGBA());
    }

    /**
     Watch this method and test thoroughly... because I have no idea how it works
     **/
    public void blitOutlineBlack(int p_93102_, int p_93103_, BiConsumer<Integer, Integer> p_93104_) {
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        p_93104_.accept(p_93102_ + 1, p_93103_);
        p_93104_.accept(p_93102_ - 1, p_93103_);
        p_93104_.accept(p_93102_, p_93103_ + 1);
        p_93104_.accept(p_93102_, p_93103_ - 1);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        p_93104_.accept(p_93102_, p_93103_);
    }


    /* -------------------------------------------------------------------------------------------------------------- */


    public void drawExteriorChildren(PoseStack poseStack, int mouseX, int mouseY, int xIn, int yIn, float zIn, int widthIn, int heightIn, int parentLeftMarginOffset, int parentRightMarginOffset, int parentTopMarginOffset, int parentBottomMarginOffset) {
        iterateExteriorElements(item ->
                item.drawExteriorContentBoxes(createDrawingContext(poseStack, widthIn, heightIn, xIn, yIn, zIn), mouseX, mouseY,
                        parentLeftMarginOffset, parentRightMarginOffset, parentTopMarginOffset, parentBottomMarginOffset)
        );

        iterateExteriorButtons((item) ->
                item.drawExteriorButtons(createDrawingContext(poseStack, widthIn, heightIn, xIn, yIn, zIn), mouseX, mouseY,
                        parentLeftMarginOffset, parentRightMarginOffset, parentTopMarginOffset, parentBottomMarginOffset)
        );
    }
    public void drawExteriorContentBoxes(GuiDrawingContext drawContext, int mouseX, int mouseY, int parentLeftMarginOffset, int parentRightMarginOffset, int parentTopMarginOffset, int parentBottomMarginOffset) {
        int contentBoxWidth = (int) (drawContext.parentWidth * widthPercentOfParent);
        int contentBoxHeight = (int) (drawContext.parentHeight * heightPercentOfParent);

        int topLeftXPos = getXPosForExteriorChildren(exteriorPosition, drawContext, contentBoxWidth, parentLeftMarginOffset, parentRightMarginOffset) + getXOffset(drawContext);
        int topLeftYPos = getYPosForExteriorChildren(exteriorPosition, drawContext, contentBoxHeight, parentTopMarginOffset, parentBottomMarginOffset) + getYOffset(drawContext);
        int endX = topLeftXPos + contentBoxWidth;
        int endY = topLeftYPos + contentBoxHeight;

        this.boxTopLeftX = topLeftXPos;
        this.boxTopLeftY = topLeftYPos;
        this.boxEndX = endX;
        this.boxEndY = endY;

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

        //drawInteriorChildren(drawContext.poseStack, topLeftXPos, topLeftYPos, drawContext.originZ, contentBoxWidth, contentBoxHeight);

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

        this.drawExteriorChildren(drawContext.poseStack, mouseX, mouseY, topLeftXPos, topLeftYPos, drawContext.originZ, contentBoxWidth, contentBoxHeight,
                leftMarginOffset, rightMarginOffset, topMarginOffset, bottomMarginOffset);
    }
    public void drawExteriorButtons(GuiDrawingContext drawContext, int mouseX, int mouseY, int parentLeftMarginOffset, int parentRightMarginOffset, int parentTopMarginOffset, int parentBottomMarginOffset) {
        processMouse(mouseX, mouseY);

        int contentBoxWidth = (int) (drawContext.parentWidth * widthPercentOfParent);
        int contentBoxHeight = (int) (drawContext.parentHeight * heightPercentOfParent);

        int topLeftXPos = getXPosForExteriorChildren(exteriorPosition, drawContext, contentBoxWidth, parentLeftMarginOffset, parentRightMarginOffset) + getXOffset(drawContext);
        int topLeftYPos = getYPosForExteriorChildren(exteriorPosition, drawContext, contentBoxHeight, parentTopMarginOffset, parentBottomMarginOffset) + getYOffset(drawContext);
        int endX = topLeftXPos + contentBoxWidth;
        int endY = topLeftYPos + contentBoxHeight;

        this.boxTopLeftX = topLeftXPos;
        this.boxTopLeftY = topLeftYPos;
        this.boxEndX = endX;
        this.boxEndY = endY;

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

        if (isHovered()) {
            GUIHelper.fillAreaWithColor(drawContext.poseStack, topLeftXPos, topLeftYPos,
                    endX, endY, (int) drawContext.originZ,
                    hoverColor.getRGBA());
        }

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

        drawExteriorChildren(drawContext.poseStack, mouseX, mouseY, topLeftXPos, topLeftYPos, drawContext.originZ, contentBoxWidth, contentBoxHeight,
                leftMarginOffset, rightMarginOffset, topMarginOffset, bottomMarginOffset);
    }

    @Override
    public void draw(GuiDrawingContext drawContext, int mouseX, int mouseY) {
        processMouse(mouseX, mouseY);

        int contentBoxWidth = (int) (drawContext.parentWidth * widthPercentOfParent);
        int contentBoxHeight = (int) (drawContext.parentHeight * heightPercentOfParent);

        int topLeftXPos = getXPosForInteriorChildren(interiorPosition, drawContext, contentBoxWidth) + getXOffset(drawContext);
        int topLeftYPos = getYPosForInteriorChildren(interiorPosition, drawContext, contentBoxHeight) + getYOffset(drawContext);
        int endX = topLeftXPos + contentBoxWidth;
        int endY = topLeftYPos + contentBoxHeight;

        this.boxTopLeftX = topLeftXPos;
        this.boxTopLeftY = topLeftYPos;
        this.boxEndX = endX;
        this.boxEndY = endY;

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

        if (isHovered()) {
            GUIHelper.fillAreaWithColor(drawContext.poseStack, topLeftXPos, topLeftYPos,
                    endX, endY, (int) drawContext.originZ,
                    hoverColor.getRGBA());
        }

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

        this.drawExteriorChildren(drawContext.poseStack, mouseX, mouseY, topLeftXPos, topLeftYPos, drawContext.originZ, contentBoxWidth, contentBoxHeight,
                leftMarginOffset, rightMarginOffset, topMarginOffset, bottomMarginOffset);
    }


    /* -------------------------------------------------------------------------------------------------------------- */


    private void processMouse(int mouseX, int mouseY) {
        iterateExteriorButtons((item) -> {
            if (item.isWithinHoverArea(mouseX, mouseY)) {
                item.setHovered(true);
            } else {
                item.setHovered(false);
            }
        });
    }

    private void processClick(boolean triggeredByMouse, int button) {
        if (getHoveredItem() != null) {
            BasicButton item = getHoveredItem();

            item.click();
        }
    }

    @SubscribeEvent
    public void onMouseRelease(ScreenEvent.MouseButtonReleased.Pre event) {
        processClick(true, event.getButton());
    }

    public BasicButton getHoveredItem() {
        for (BasicButton item : exteriorButtons) {
            if (item.isHovered())
                return item;
        }
        return null;
    }

    @Override
    public boolean isHovered() { return isHovered; }

    @Override
    public void setHovered(boolean hovered) { isHovered = hovered; }

    @Override
    public void click() {}
}
