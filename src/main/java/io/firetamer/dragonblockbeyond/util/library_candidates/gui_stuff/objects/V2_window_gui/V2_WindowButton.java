package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.V2_window_gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GuiDrawingContext;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.texture_objects.SimpleSpriteObject;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class V2_WindowButton extends V2_ContentBox {
    //TODO These three things can be moved to the Behaviour class after I figure out rendering text better.
    private SimpleSpriteObject spriteIcon;
    private Component buttonText;
    private int buttonTextColor;

    private boolean isHovered;

    public V2_WindowButton(BasicButtonProperties properties) {
        super(properties);
    }

    public boolean isWithinHoverArea(int mouseX, int mouseY) {
        return mouseX >= this.topLeftXPos
                && mouseX <= this.topLeftXPos + this.contentBoxWidth
                && mouseY >= this.topLeftYPos
                && mouseY <= this.topLeftYPos + this.contentBoxHeight;
    }

    public boolean isHovered() { return isHovered; }
    public void setHoverState(boolean newState) { this.isHovered = newState; }

    public void draw(GuiDrawingContext drawContext) {
        super.draw(drawContext);

        if (isHovered()) {
            GUIHelper.fillAreaWithColor(drawContext.poseStack, topLeftXPos, topLeftYPos,
                    endX, endY, (int) drawContext.originZ,
                    hoverColor.getRGBA());
        }
    }

    /**
     * This is to be overwridden when the button is made if you want it to do anything
     **/
    public void click() {}
}
