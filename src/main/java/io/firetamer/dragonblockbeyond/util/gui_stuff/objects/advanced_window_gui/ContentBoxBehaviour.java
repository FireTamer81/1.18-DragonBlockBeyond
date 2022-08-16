package io.firetamer.dragonblockbeyond.util.gui_stuff.objects.advanced_window_gui;

import io.firetamer.dragonblockbeyond.util.gui_stuff.objects.texture_objects.BorderTextureObject;
import io.firetamer.dragonblockbeyond.util.library_candidates.FireLibColor;

import java.util.ArrayList;
import java.util.List;

public class ContentBoxBehaviour {

    public enum InteriorContentBoxPosition {
        TOP_LEFT,
        TOP_MIDDLE,
        TOP_RIGHT,
        MIDDLE_LEFT,
        CENTER,
        MIDDLE_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_MIDDLE,
        BOTTOM_RIGHT
    }

    public enum ExteriorContentBoxPosition {
        TOP_LEFT,
        TOP_MIDDLE,
        TOP_RIGHT,
        RIGHT_TOP,
        RIGHT_MIDDLE,
        RIGHT_BOTTOM,
        BOTTOM_RIGHT,
        BOTTOM_MIDDLE,
        BOTTOM_LEFT,
        LEFT_BOTTOM,
        LEFT_MIDDLE,
        LEFT_TOP
    }

    public float widthPercentOfParent;
    public float heightPercentOfParent;
    public FireLibColor color1;
    public FireLibColor color2;
    public boolean isColor2Used;
    public InteriorContentBoxPosition interiorPosition;
    public ExteriorContentBoxPosition exteriorPosition;
    public boolean shouldDrawContentBoxBackground;
    public boolean shouldDrawBorder;
    public BorderTextureObject borderFont;
    public int xOffsetAbsolute;
    public int yOffsetAbsolute;
    public float xOffsetDynamic;
    public float yOffsetDynamic;
    public boolean shouldUseDynamicOffset;

    public List<ContentBox> interiorDynamicItems;
    public List<ContentBox> exteriorPanels;

    public ContentBoxBehaviour(ContentBoxBehaviour.Properties properties, ContentBoxBehaviour.Children children) {
        this.widthPercentOfParent = properties.widthPercentage;
        this.heightPercentOfParent = properties.heightPercentage;
        this.color1 = properties.color1;
        this.color2 = properties.color2;
        this.isColor2Used = properties.isColor2Used;
        this.interiorPosition = properties.interiorPosition;
        this.exteriorPosition = properties.exteriorPosition;
        this.shouldDrawContentBoxBackground = properties.shouldDrawContentBoxBackground;
        this.shouldDrawBorder = properties.shouldDrawBorder;
        this.borderFont = properties.borderFont;
        this.xOffsetAbsolute = properties.xOffsetAbsolute;
        this.yOffsetAbsolute = properties.yOffsetAbsolute;
        this.xOffsetDynamic = properties.xOffsetDynamic;
        this.yOffsetDynamic = properties.yOffsetDynamic;
        this.shouldUseDynamicOffset = properties.shouldUseDynamicOffset;

        this.interiorDynamicItems = children.interiorDynamicItems;
        this.exteriorPanels = children.exteriorPanels;
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    public static class Properties {
        protected float widthPercentage = 1.0F;
        protected float heightPercentage = 1.0F;
        protected FireLibColor color1 = null;
        protected FireLibColor color2 = null;
        protected boolean isColor2Used = false;
        protected InteriorContentBoxPosition interiorPosition = InteriorContentBoxPosition.TOP_LEFT;
        protected ExteriorContentBoxPosition exteriorPosition = ExteriorContentBoxPosition.RIGHT_TOP;
        protected boolean shouldDrawContentBoxBackground = true;
        protected boolean shouldDrawBorder = false;
        protected BorderTextureObject borderFont = null;
        protected int xOffsetAbsolute = 0;
        protected int yOffsetAbsolute = 0;
        protected float xOffsetDynamic = 0.0f;
        protected float yOffsetDynamic = 0.0f;
        protected boolean shouldUseDynamicOffset = false;



        /**
         * Takes two floats between 0.0 and 1.0. Any values over 1.0 will be converted to 1.0
         * @param widthPercentageIn Percentage of the parent elements width this content box should be.
         */
        public ContentBoxBehaviour.Properties widthPercentageOfParent(float widthPercentageIn) {
            widthPercentage = widthPercentageIn;
            return this;
        }

        /**
         * Takes two floats between 0.0 and 1.0. Any values over 1.0 will be converted to 1.0
         * @param heightPercentageIn Percentage of the parent elements width this content box should be.
         */
        public ContentBoxBehaviour.Properties heightPercentageOfParent(float heightPercentageIn) {
            heightPercentage = heightPercentageIn;
            return this;
        }

        /**
         * Takes one FireLibColor variables to create a solid background
         */
        public ContentBoxBehaviour.Properties backgroundColor(FireLibColor color1In) {
            color1 = color1In;
            isColor2Used = false;
            return this;
        }

        /**
         * Takes two FireLibColor variables to create a gradient background
         */
        public ContentBoxBehaviour.Properties backgroundColor(FireLibColor color1In, FireLibColor color2In) {
            color1 = color1In;
            color2 = color2In;
            isColor2Used = true;
            return this;
        }

        /**
         * Takes the ContentBoxBehaviour.InteriorContentBoxPosition Enum to determine where the Content Box will be placed within it's parent
         */
        public ContentBoxBehaviour.Properties position(InteriorContentBoxPosition positionIn) {
            interiorPosition = positionIn;

            return this;
        }

        /**
         * Takes the ContentBoxBehaviour.ExteriorContentBoxPosition Enum to determine where the Content Box will be placed outside it's parent
         */
        public ContentBoxBehaviour.Properties exteriorPosition(ExteriorContentBoxPosition positionIn) {
            exteriorPosition = positionIn;

            return this;
        }

        /**
         * This method offsets the contentBox by pixels.
         * It is most useful for offsetting elements where gui scale changes don't matter
         */
        public ContentBoxBehaviour.Properties absolutePositionOffset(int xOffsetIn, int yOffsetIn) {
            xOffsetAbsolute = xOffsetIn;
            yOffsetAbsolute = yOffsetIn;

            shouldUseDynamicOffset = false;

            return this;
        }

        /**
         * This method offsets the content box based on a percentage of the parent element.
         * This is most useful for offsetting interior child elements.
         * For instance, if you have 2 child element with a position of "TOP_LEFT".
         * One element can remain without an offset, and the 2nd can be offset by either the height or width
         * of the first, so it can appear paired to the 1st without actually being so.
         */
        public ContentBoxBehaviour.Properties dynamicPositionOffset(float xOffsetIn, float yOffsetIn) {
            xOffsetDynamic = xOffsetIn;
            yOffsetDynamic = yOffsetIn;

            shouldUseDynamicOffset = true;

            return this;
        }

        /**
         * This method makes the content box draw a border around itself using a BorderTextureObject.
         * One problem has presented itself with borders. If you have a very small content box,
         * whichever axis that is too small (height/width) will have the sidebars of the border
         * placed at the top or side of the screen.
         */
        public ContentBoxBehaviour.Properties borderFont(BorderTextureObject borderFontIn) {
            borderFont = borderFontIn;

            shouldDrawBorder = true;
            return this;
        }

        /**
         * This method makes it where a content box will no longer draw the background, no matter if a single color or gradient is defined. Borders will also not be drawn.
         */
        public ContentBoxBehaviour.Properties noBackground() {
            shouldDrawContentBoxBackground = false;
            return this;
        }
    }

    public static class Children {
        protected List<ContentBox> interiorDynamicItems = new ArrayList<>();
        protected List<ContentBox> exteriorPanels = new ArrayList<>();

        /**
         * Adds a IContentBox object to the interiorDynamicItems List, which are then added to the interior of the content box in question.
         * These objects are dynamically placed based on the order they were added and their own properties
         */
        public ContentBoxBehaviour.Children addInteriorChild(ContentBox newItemIn) {
            interiorDynamicItems.add(newItemIn);

            return this;
        }

        /**
         * Adds a IContentBox object to the exteriorPanels list, which are placed outside the parent content box in question.
         * These objects are placed outside of the parent content box based on the objects properties.
         */
        public ContentBoxBehaviour.Children addExteriorChild(ContentBox newPanelIn) {
            exteriorPanels.add(newPanelIn);

            return this;
        }
    }
}
