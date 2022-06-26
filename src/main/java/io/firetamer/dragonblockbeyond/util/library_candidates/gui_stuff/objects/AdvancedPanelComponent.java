package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.util.library_candidates.DBBColor;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.texture_objects.BorderTextureObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AdvancedPanelComponent extends GuiComponent implements Widget, GuiEventListener {
    protected Minecraft minecraft;
    protected boolean visible;
    protected boolean canToggleVisibility;

    private boolean isDragging;
    private double lastMouseX;
    private double lastMouseY;

    protected int panelOriginX;
    protected int panelOriginY;
    protected int panelWidth;
    protected int panelHeight;

    protected int minPanelWidth;
    protected int minPanelHeight;

    BorderTextureObject borderStyle;
    DBBColor panelColor1; //Required (To not apply a color, just pass the color with 255 for the alpha value)
    DBBColor panelColor2; //Optional (Passing this parameter as null simply makes the panel fill the interior with a solid color)

    public void init(int originPosXIn, int originPosYIn, int panelWidthIn, int panelHeightIn, int minPanelWidthIn, int minPanelHeightIn, Minecraft instanceIn, boolean initialVisibility, boolean canToggleVisibility,
                     BorderTextureObject borderStyleIn, DBBColor panelColor1In, DBBColor panelColor2In) {
        this.minecraft = instanceIn;
        this.panelOriginX = originPosXIn;
        this.panelOriginY = originPosYIn;
        this.panelWidth = panelWidthIn;
        this.panelHeight = panelHeightIn;
        this.minPanelWidth = minPanelWidthIn;
        this.minPanelHeight = minPanelHeightIn;

        this.visible = initialVisibility;
        this.canToggleVisibility = canToggleVisibility;

        this.borderStyle = borderStyleIn;
        this.panelColor1 = panelColor1In;
        this.panelColor2 = panelColor2In;
    }



    /******************************************************************************************************************/
    //Re-sizing/movement methods
    /******************************************************************************************************************/


    @Override
    public boolean mouseClicked(double xDouble, double yDouble, int button) {
        GuiEventListener.super.mouseClicked(xDouble, yDouble, button);

        double minX = this.panelOriginX;
        double minY = this.panelOriginY;
        double maxX = minX + this.panelWidth;
        double maxY = minY + this.panelHeight;

        if (xDouble >= minX && xDouble <= maxX && yDouble >= minY && yDouble <= maxY) {
            this.isDragging = true;
            this.lastMouseX = xDouble;
            this.lastMouseY = yDouble;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean mouseReleased(double p_94753_, double p_94754_, int p_94755_) {
        this.isDragging = false;

        return GuiEventListener.super.mouseReleased(p_94753_, p_94754_, p_94755_);
    }





    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int lastButtonClicked, double p_94743_, double p_94744_) {
        if (this.isDragging) {
            double tempXChange;
            double tempYChange;

            if (mouseX > lastMouseX) {
                tempXChange = mouseX - lastMouseX;
                this.panelOriginX = (int) (this.panelOriginX + tempXChange);
            } else {
                tempXChange = lastMouseX - mouseX;
                this.panelOriginX = (int) (this.panelOriginX - tempXChange);
            }

            if (mouseY > lastMouseY) {
                tempYChange = mouseY - lastMouseY;
                this.panelOriginY = (int) (this.panelOriginY + tempYChange);
            } else {
                tempYChange = lastMouseY - mouseY;
                this.panelOriginY = (int) (this.panelOriginY - tempYChange);
            }

            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;

            //this.initButtons();

            System.out.println(mouseX + ", " + mouseY);
        }

        return GuiEventListener.super.mouseDragged(mouseX, mouseY, lastButtonClicked, p_94743_, p_94744_);
    }

    @Override
    public boolean mouseScrolled(double p_94734_, double p_94735_, double p_94736_) {
        return GuiEventListener.super.mouseScrolled(p_94734_, p_94735_, p_94736_);
    }

    @Override
    public boolean isMouseOver(double p_94748_, double p_94749_) {
        return GuiEventListener.super.isMouseOver(p_94748_, p_94749_);
    }




    /******************************************************************************************************************/
    //Render Methods
    /******************************************************************************************************************/

    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        if(this.isVisible()) {
            this.renderBackground(poseStack, mouseX, mouseY, delta);
        }
    }

    public void renderBackground(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        if (panelWidth < minPanelWidth) {
            panelWidth = minPanelWidth;
        }
        if (panelHeight < minPanelHeight) {
           panelHeight = minPanelHeight;
        }

        GUIHelper.drawBorderedFilledPanel(poseStack, panelOriginX, panelOriginY, panelWidth, panelHeight, borderStyle, panelColor1, panelColor2, 0);
    }




    /******************************************************************************************************************/
    //Visibility Methods
    /******************************************************************************************************************/

    public boolean allowVisibilityToggle() { return this.canToggleVisibility; }

    public void toggleVisibility() {
        if(allowVisibilityToggle()) {
            this.setVisibility(!this.isVisible());
        }
    }

    public boolean isVisible() { return this.visible; }

    protected void setVisibility(boolean newVisibilityState) {
        this.visible = newVisibilityState;
    }
}
