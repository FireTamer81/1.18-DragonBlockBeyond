package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.advanced_window_gui;

import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GuiDrawingContext;

public interface WidgetEventListener {
    /**
     * Draws the button content using the GuiDrawingContext class
     */
    void draw(GuiDrawingContext drawContext, int mouseX, int mouseY);

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     *
     * @return Is this button the one being hovered over in the radial menu
     */
    boolean isHovered();

    /**
     * Sets the Hovered state of the button
     */
    void setHovered(boolean newHoveredState);

    /**
     * Method to execute on the click of the button
     */
    void click();

}
