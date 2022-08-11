package io.firetamer.dragonblockbeyond.util.gui_stuff.objects.advanced_window_gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.util.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.gui_stuff.GuiDrawingContext;
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
        /*
        int numItems = interiorItems.size();

        for (int i = 0; i < numItems; i++) {
            IContentBox item = interiorItems.get(i);
            consumer.accept(item);
        }
        */

        for (ContentBox item : interiorItems) {
            consumer.accept(item);
        }
    }

    private void iterateExteriorElements(Consumer<ContentBox> consumer) {
        /*
        int numItems = exteriorItems.size();

        for (int i = 0; i < numItems; i++) {
            ContentBox item = exteriorItems.get(i);
            consumer.accept(item);
        }
        */

        for (ContentBox item : exteriorItems) {
            consumer.accept(item);
        }
    }

    public int getXPosForPositionFromContext(InteriorContentBoxPosition interiorPositionIn, GuiDrawingContext context, int contentBoxWidth) {
        if (interiorPositionIn == InteriorContentBoxPosition.TOP_LEFT || interiorPositionIn == InteriorContentBoxPosition.MIDDLE_LEFT || interiorPositionIn == InteriorContentBoxPosition.BOTTOM_LEFT)
            return (int) context.originX;

        if (interiorPositionIn == InteriorContentBoxPosition.TOP_MIDDLE || interiorPositionIn == InteriorContentBoxPosition.CENTER || interiorPositionIn == InteriorContentBoxPosition.BOTTOM_MIDDLE)
            return (int) (context.originX + (context.parentWidth / 2) - (contentBoxWidth / 2));

        if (interiorPositionIn == InteriorContentBoxPosition.TOP_RIGHT || interiorPositionIn == InteriorContentBoxPosition.MIDDLE_RIGHT || interiorPositionIn == InteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) context.originX + context.parentWidth - contentBoxWidth;

        else { return 0; }
    }

    public int getYPosForPositionFromContext(InteriorContentBoxPosition interiorPositionIn, GuiDrawingContext context, int contentBoxHeight) {
        if (interiorPositionIn == InteriorContentBoxPosition.TOP_LEFT || interiorPositionIn == InteriorContentBoxPosition.TOP_MIDDLE || interiorPositionIn == InteriorContentBoxPosition.TOP_RIGHT)
            return (int) context.originY;

        if (interiorPositionIn == InteriorContentBoxPosition.MIDDLE_LEFT || interiorPositionIn == InteriorContentBoxPosition.CENTER || interiorPositionIn == InteriorContentBoxPosition.MIDDLE_RIGHT)
            return (int) (context.originY + (context.parentHeight / 2) - (contentBoxHeight / 2));

        if (interiorPositionIn == InteriorContentBoxPosition.BOTTOM_LEFT || interiorPositionIn == InteriorContentBoxPosition.BOTTOM_MIDDLE || interiorPositionIn == InteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) context.originY + context.parentHeight - contentBoxHeight;

        else { return 0; }
    }

    public int getXPosForPositionFromContext(ExteriorContentBoxPosition exteriorPositionIn, GuiDrawingContext parentContext, int contentBoxWidth) {
        if (exteriorPositionIn == ExteriorContentBoxPosition.LEFT_BOTTOM || exteriorPositionIn == ExteriorContentBoxPosition.LEFT_MIDDLE || exteriorPositionIn == ExteriorContentBoxPosition.LEFT_TOP)
            return (int) parentContext.originX - contentBoxWidth;

        else if (exteriorPositionIn == ExteriorContentBoxPosition.TOP_LEFT || exteriorPositionIn == ExteriorContentBoxPosition.BOTTOM_LEFT)
            return (int) parentContext.originX;

        else if (exteriorPositionIn == ExteriorContentBoxPosition.TOP_MIDDLE || exteriorPositionIn == ExteriorContentBoxPosition.BOTTOM_MIDDLE)
            return (int) (parentContext.originX + (parentContext.parentWidth / 2) - (contentBoxWidth / 2));

        else if (exteriorPositionIn == ExteriorContentBoxPosition.TOP_RIGHT || exteriorPositionIn == ExteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) parentContext.originX + parentContext.parentWidth - contentBoxWidth;

        else if (exteriorPositionIn == ExteriorContentBoxPosition.RIGHT_BOTTOM || exteriorPositionIn == ExteriorContentBoxPosition.RIGHT_MIDDLE || exteriorPositionIn == ExteriorContentBoxPosition.RIGHT_TOP)
            return (int) parentContext.originX + parentContext.parentWidth;

        else { return 0; }
    }

    public int getYPosForPositionFromContext(ExteriorContentBoxPosition exteriorPositionIn, GuiDrawingContext parentContext, int contentBoxHeight) {
        if (exteriorPositionIn == ExteriorContentBoxPosition.TOP_LEFT || exteriorPositionIn == ExteriorContentBoxPosition.TOP_MIDDLE || exteriorPositionIn == ExteriorContentBoxPosition.TOP_RIGHT)
            return (int) parentContext.originY - contentBoxHeight;

        else if (exteriorPositionIn == ExteriorContentBoxPosition.LEFT_TOP || exteriorPositionIn == ExteriorContentBoxPosition.RIGHT_TOP)
            return (int) parentContext.originY;

        else if (exteriorPositionIn == ExteriorContentBoxPosition.LEFT_MIDDLE || exteriorPositionIn == ExteriorContentBoxPosition.RIGHT_MIDDLE)
            return (int) (parentContext.originY + (parentContext.parentHeight / 2) - (contentBoxHeight / 2));

        else if (exteriorPositionIn == ExteriorContentBoxPosition.LEFT_BOTTOM || exteriorPositionIn == ExteriorContentBoxPosition.RIGHT_BOTTOM)
            return (int) parentContext.originY + parentContext.parentHeight - contentBoxHeight;

        else if (exteriorPositionIn == ExteriorContentBoxPosition.BOTTOM_LEFT || exteriorPositionIn == ExteriorContentBoxPosition.BOTTOM_MIDDLE || exteriorPositionIn == ExteriorContentBoxPosition.BOTTOM_RIGHT)
            return (int) parentContext.originY + parentContext.parentHeight;

        else { return 0; }
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
        int topLeftXPos = getXPosForPositionFromContext(interiorPosition, drawContext, contentBoxWidth);
        int topLeftYPos = getYPosForPositionFromContext(interiorPosition, drawContext, contentBoxHeight);

        if (shouldDrawContentBoxBackground) {
            if (isColor2Used) {
                GUIHelper.fillAreaWithColorGradient(drawContext.poseStack, topLeftXPos + xOffset, topLeftYPos + yOffset,
                        topLeftXPos + contentBoxWidth + xOffset, topLeftYPos + contentBoxHeight + yOffset, (int) drawContext.originZ,
                        color1.getRGBA(), color2.getRGBA());
            } else {
                GUIHelper.fillAreaWithColor(drawContext.poseStack, topLeftXPos + xOffset, topLeftYPos + yOffset,
                        topLeftXPos + contentBoxWidth + xOffset, topLeftYPos + contentBoxHeight + yOffset, (int) drawContext.originZ,
                        color1.getRGBA());
            }
        }
    }

    public void drawExteriorChildren(PoseStack poseStack, int xIn, int yIn, float zIn, int widthIn, int heightIn) {
        iterateExteriorElements(item -> {
            item.drawExteriorChildren(createDrawingContext(poseStack, widthIn, heightIn, xIn, yIn, zIn));
        });
    }

    public void drawExteriorChildren(GuiDrawingContext drawContext) {
        int contentBoxWidth = (int) (drawContext.parentWidth * widthPercentOfParent);
        int contentBoxHeight = (int) (drawContext.parentHeight * heightPercentOfParent);
        int topLeftXPos = getXPosForPositionFromContext(exteriorPosition, drawContext, contentBoxWidth);
        int topLeftYPos = getYPosForPositionFromContext(exteriorPosition, drawContext, contentBoxHeight);

        if (shouldDrawContentBoxBackground) {
            if (isColor2Used) {
                GUIHelper.fillAreaWithColorGradient(drawContext.poseStack, topLeftXPos + xOffset, topLeftYPos + yOffset,
                        topLeftXPos + contentBoxWidth + xOffset, topLeftYPos + contentBoxHeight + yOffset, (int) drawContext.originZ,
                        color1.getRGBA(), color2.getRGBA());
            } else {
                GUIHelper.fillAreaWithColor(drawContext.poseStack, topLeftXPos + xOffset, topLeftYPos + yOffset,
                        topLeftXPos + contentBoxWidth + xOffset, topLeftYPos + contentBoxHeight + yOffset, (int) drawContext.originZ,
                        color1.getRGBA());
            }
        }
    }

    @Override
    public void draw(GuiDrawingContext drawContext) {
        int contentBoxWidth = (int) (drawContext.parentWidth * widthPercentOfParent);
        int contentBoxHeight = (int) (drawContext.parentHeight * heightPercentOfParent);

        int topLeftXPos = getXPosForPositionFromContext(interiorPosition, drawContext, contentBoxWidth) + xOffset;
        int topLeftYPos = getYPosForPositionFromContext(interiorPosition, drawContext, contentBoxHeight) + yOffset;
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

        drawExteriorChildren(drawContext.poseStack, topLeftXPos, topLeftYPos, drawContext.originZ, contentBoxWidth, contentBoxHeight);
        drawInteriorChildren(drawContext.poseStack, topLeftXPos, topLeftYPos, drawContext.originZ, contentBoxWidth, contentBoxHeight);
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
}
