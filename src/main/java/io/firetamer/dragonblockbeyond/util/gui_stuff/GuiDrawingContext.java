package io.firetamer.dragonblockbeyond.util.gui_stuff;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;

/**
 * The purpose of this class is to have one place to store data related to drawing Gui Elements.
 * This is done by making a variable of this class and referencing that and the variables inside when needed.
 * In the future I might be able to add onto this class to include set/get methods and also make a way fo updating the values for movable elements.
 */
public class GuiDrawingContext {
    public final PoseStack poseStack;
    public final int parentWidth;
    public final int parentHeight;
    public final float originX;
    public final float originY;
    public final float originZ; //For controlling which elements are in front of or behind others
    public final Font fontRenderer;
    public final ItemRenderer itemRenderer;

    public GuiDrawingContext(PoseStack poseStackIn, int widthIn, int heightIn, float originXIn, float originYIn, float originZIn, Font fontRendererIn, ItemRenderer itemRendererIn) {
        this.poseStack = poseStackIn;
        this.parentWidth = widthIn;
        this.parentHeight = heightIn;
        this.originX = originXIn;
        this.originY = originYIn;
        this.originZ = originZIn;
        this.fontRenderer = fontRendererIn;
        this.itemRenderer = itemRendererIn;
    }

    public GuiDrawingContext of(float xIn, float yIn) {
        return new GuiDrawingContext(poseStack, parentWidth, parentHeight, xIn, yIn, originZ, fontRenderer, itemRenderer);
    }
}
