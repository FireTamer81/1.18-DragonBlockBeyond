package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.util.library_candidates.DBBColor;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.texture_objects.BorderTextureObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

public class AdvancedPanelComponent2 extends Screen implements GuiEventListener {
    protected Minecraft minecraft;
    protected boolean visible;
    protected boolean canToggleVisibility;

    protected int panelOriginX;
    protected int panelOriginY;
    protected int panelWidth;
    protected int panelHeight;

    private boolean isDragging;
    private double lastMouseX;
    private double lastMouseY;

    BorderTextureObject borderStyle;
    DBBColor panelColor1; //Required (To not apply a color, just pass the color with 255 for the alpha value)
    DBBColor panelColor2; //Optional (Passing this parameter as null simply makes the panel fill the interior with a solid color)

    public AdvancedPanelComponent2() {
        super(new TextComponent(""));
    }

    public void init(int originPosXIn, int originPosYIn, int panelWidthIn, int panelHeightIn, Minecraft instanceIn, boolean initialVisibility, boolean canToggleVisibility,
                     BorderTextureObject borderStyleIn, DBBColor panelColor1In, DBBColor panelColor2In) {
        this.minecraft = instanceIn;
        this.panelOriginX = originPosXIn;
        this.panelOriginY = originPosYIn;
        this.panelWidth = panelWidthIn;
        this.panelHeight = panelHeightIn;

        this.visible = initialVisibility;
        this.canToggleVisibility = canToggleVisibility;

        this.borderStyle = borderStyleIn;
        this.panelColor1 = panelColor1In;
        this.panelColor2 = panelColor2In;
    }



    /******************************************************************************************************************/
    //Re-sizing/movement methods
    /******************************************************************************************************************/


    public void updatePos(int newXOrigin, int newYOrigin) {
        this.panelOriginX = newXOrigin;
        this.panelOriginY = newYOrigin;
    }

    @Override
    public boolean mouseClicked(double mousePosX, double mousePosY, int button) {
        super.mouseClicked(mousePosX, mousePosY, button);

        final double minX = this.panelOriginX;
        final double minY = this.panelOriginY;
        final double maxX = minX + this.panelWidth;
        final double maxY = minY + this.panelHeight;

        if (mousePosX >= minX && mousePosX <= maxX && mousePosY >= minY && mousePosY <= maxY) {
            this.isDragging = true;
            this.lastMouseX = mousePosX;
            this.lastMouseY = mousePosY;

            //This Works!!!!!!!!
            System.out.println("Heyo, it only activates when within the GUI boundary");

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
        this.isDragging = false;

        return super.mouseReleased(p_94722_, p_94723_, p_94724_);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int lastButtonClicked, double p_231045_6_, double p_231045_8_) {
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

        return super.mouseDragged(mouseX, mouseY, lastButtonClicked, p_231045_6_, p_231045_8_);
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
