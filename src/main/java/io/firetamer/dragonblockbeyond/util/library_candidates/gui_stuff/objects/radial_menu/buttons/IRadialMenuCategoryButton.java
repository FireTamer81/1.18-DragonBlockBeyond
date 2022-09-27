package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.radial_menu.buttons;

import java.util.List;

public interface IRadialMenuCategoryButton extends IRadialMenuButton {

    /**
     * Returns a list of objects under this category button (which opens a new radial containing said objects on click)
     */
    List<IRadialMenuButton> getCategoryObjects();

    /**
     * Adds an item to the category
     */
    void addItem(IRadialMenuButton newObject);

    /**
     * Determines whether the radial should automatically close if the clicked category does not have any objects within it.
     */
    boolean shouldRadialCloseIfCategoryIsEmpty();

    /**
     * Default empty override
     */
    @Override
    default void click() {}

    /**
     * Default override.
     */
    @Override
    default boolean shouldCloseRadialOnClick() {
        return false;
    }
}
