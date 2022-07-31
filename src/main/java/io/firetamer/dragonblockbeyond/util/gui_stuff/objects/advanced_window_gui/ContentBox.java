package io.firetamer.dragonblockbeyond.util.gui_stuff.objects.advanced_window_gui;

import io.firetamer.dragonblockbeyond.util.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.gui_stuff.GuiDrawingContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;

import javax.annotation.Nullable;

public class ContentBox extends ContentBoxBehaviour implements IContentBox {

    public ContentBox(ContentBoxBehaviour.Properties properties) {
        super(properties);
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    @Override
    public void draw(GuiDrawingContext drawContext) {

        if (isColor2Used) {
            GUIHelper.fillAreaWithColorGradient(drawContext.poseStack, (int) drawContext.originX, (int) drawContext.originY,
                    (int) (drawContext.width * widthPercentOfParent), (int) (drawContext.height * heightPercentOfParent), (int) drawContext.originZ,
                    color1, color2);
        } else {
            GUIHelper.fillAreaWithColor(drawContext.poseStack, (int) drawContext.originX, (int) drawContext.originY,
                    (int) (drawContext.width * widthPercentOfParent), (int) (drawContext.height * heightPercentOfParent), (int) drawContext.originZ,
                    color1);
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
}
