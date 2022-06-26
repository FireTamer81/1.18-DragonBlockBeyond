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
public abstract class BasicPanelComponent extends GuiComponent implements Widget, GuiEventListener {
    protected Minecraft minecraft;
    protected boolean visible;
    protected boolean canToggleVisibility;
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
