package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.radial_menu.buttons;

import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GuiDrawingContext;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public interface IRadialMenuButton {

    /**
     * Draws the button content using the GuiDrawingContext class
     */
    void draw(GuiDrawingContext drawContext);

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

    /**
     * Determines whether the radial should be closed when this button is clicked.
     */
    boolean shouldCloseRadialOnClick();

    /**
     * Gets the context items for this category.
     *
     * @return a list of context items.
     */
    default List<IRadialMenuButton> getContextItems() {
        return new ArrayList<>();
    }

    /**
     * Gets if the context menu should be skipped if there is only one context item.
     * This changes the default tooltip and right click behavior.
     *
     * @return true if the menu should be skipped.
     */
    default boolean skipMenuIfSingleContextItem() {
        return true;
    }

    /**
     *
     * @return the text to be drawn in the interior of the radial (Usually the name of the button)
     */
    Component getRadialCenterTextForHoveredButton();

    /**
     * @return The color to be applied to the radial's center text
     */
    int getCenterTextColor();
}
