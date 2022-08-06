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
    private List<IContentBox> interiorItems;
    private List<ContentBox> exteriorItems;

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

    private void iterateInteriorElements(Consumer<IContentBox> consumer) {
        int numItems = interiorItems.size();

        for (int i = 0; i < numItems; i++) {
            IContentBox item = interiorItems.get(i);
            consumer.accept(item);
        }
    }

    private void iterateExteriorElements(Consumer<ContentBox> consumer) {
        int numItems = exteriorItems.size();

        for (int i = 0; i < numItems; i++) {
            ContentBox item = exteriorItems.get(i);
            consumer.accept(item);
        }
    }

    public ExteriorContentBoxPosition getExteriorPositionValue(ContentBox itemIn) {
        return itemIn.exteriorPosition;
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    public void drawInteriorPanels(PoseStack poseStack, int x, int y, float z, int width, int height) {
        iterateInteriorElements(item -> {
            item.draw(createDrawingContext(poseStack, width, height, x, y, z));
        });
    }

    public void drawExteriorPanels(PoseStack poseStack, int x, int y, float z, int width, int height) {
        iterateExteriorElements(item -> {
            ExteriorContentBoxPosition panelPosition = getExteriorPositionValue(item);

            if (panelPosition == ExteriorContentBoxPosition.RIGHT_TOP) {
                item.draw(createDrawingContext(poseStack, width, height, x, y, z));
            }




            //item.draw(createDrawingContext(poseStack, width, height, x, y, z));
        });
    }

    @Override
    public void draw(GuiDrawingContext drawContext) {
        int contentBoxWidth = (int) (drawContext.parentWidth * widthPercentOfParent);
        int contentBoxHeight = (int) (drawContext.parentHeight * heightPercentOfParent);
        int topLeftXPos = getXPosForPositionFromContext(position, drawContext, contentBoxWidth);
        int topLeftYPos = getYPosForPositionFromContext(position, drawContext, contentBoxHeight);

        if (shouldDrawContentBoxBackground) {
            if (isColor2Used) {
                GUIHelper.fillAreaWithColorGradient(drawContext.poseStack, topLeftXPos + xOffset, topLeftYPos + yOffset,
                        topLeftXPos + contentBoxWidth + xOffset, topLeftYPos + contentBoxHeight + yOffset, (int) drawContext.originZ,
                        color1, color2);
            } else {
                GUIHelper.fillAreaWithColor(drawContext.poseStack, topLeftXPos + xOffset, topLeftYPos + yOffset,
                        topLeftXPos + contentBoxWidth + xOffset, topLeftYPos + contentBoxHeight + yOffset, (int) drawContext.originZ,
                        color1);
            }
        }

        drawExteriorPanels(drawContext.poseStack, 50, 50, drawContext.originZ,
                150, 150);
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
}
