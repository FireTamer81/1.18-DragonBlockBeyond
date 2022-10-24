package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.V2_window_gui;

import io.firetamer.dragonblockbeyond.util.library_candidates.FireLibColor;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.texture_objects.BorderTextureObject;

public class V2_ContentBoxBehaviour {

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

    public float percentWidthOfParent;
    public float percentHeightOfParent;
    public FireLibColor color1;
    public FireLibColor color2;
    public boolean isColor2Used;
    public FireLibColor hoverColor;
    public V2_ContentBoxBehaviour.InteriorContentBoxPosition interiorPosition;
    public V2_ContentBoxBehaviour.ExteriorContentBoxPosition exteriorPosition;
    public boolean isUsingInteriorPositioning;
    public boolean shouldDrawContentBoxBackground;
    public boolean shouldDrawBorder;
    public BorderTextureObject borderFont;
    public int xOffsetAbsolute;
    public int yOffsetAbsolute;
    public float xOffsetDynamic;
    public float yOffsetDynamic;
    public boolean shouldUseDynamicOffset;


    /* -------------------------------------------------------------------------------------------------------------- */


    /**
     * This constructor is for basic content boxes
     */
    public V2_ContentBoxBehaviour(V2_ContentBoxBehaviour.BasicBoxProperties properties) {
        this.percentWidthOfParent = properties.widthPercentage;
        this.percentHeightOfParent = properties.heightPercentage;
        this.color1 = properties.color1;
        this.color2 = properties.color2;
        this.isColor2Used = properties.isColor2Used;
        this.hoverColor = new FireLibColor(255, 255, 255, 0);
        this.interiorPosition = properties.interiorPosition;
        this.exteriorPosition = properties.exteriorPosition;
        this.isUsingInteriorPositioning = properties.isUsingInteriorPositioning;
        this.shouldDrawContentBoxBackground = properties.shouldDrawContentBoxBackground;
        this.shouldDrawBorder = properties.shouldDrawBorder;
        this.borderFont = properties.borderFont;
        this.xOffsetAbsolute = properties.xOffsetAbsolute;
        this.yOffsetAbsolute = properties.yOffsetAbsolute;
        this.xOffsetDynamic = properties.xOffsetDynamic;
        this.yOffsetDynamic = properties.yOffsetDynamic;
        this.shouldUseDynamicOffset = properties.shouldUseDynamicOffset;
    }

    /**
     * This constructor is for basic buttons
     */
    public V2_ContentBoxBehaviour(V2_ContentBoxBehaviour.BasicButtonProperties properties) {
        this.percentWidthOfParent = properties.widthPercentage;
        this.percentHeightOfParent = properties.heightPercentage;
        this.color1 = properties.color1;
        this.color2 = properties.color2;
        this.isColor2Used = properties.isColor2Used;
        this.hoverColor = properties.hoverColor;
        this.interiorPosition = properties.interiorPosition;
        this.exteriorPosition = properties.exteriorPosition;
        this.isUsingInteriorPositioning = properties.isUsingInteriorPositioning;
        this.shouldDrawContentBoxBackground = properties.shouldDrawContentBoxBackground;
        this.shouldDrawBorder = properties.shouldDrawBorder;
        this.borderFont = properties.borderFont;
        this.xOffsetAbsolute = properties.xOffsetAbsolute;
        this.yOffsetAbsolute = properties.yOffsetAbsolute;
        this.xOffsetDynamic = properties.xOffsetDynamic;
        this.yOffsetDynamic = properties.yOffsetDynamic;
        this.shouldUseDynamicOffset = properties.shouldUseDynamicOffset;
    }


    /* -------------------------------------------------------------------------------------------------------------- */


    public static class BasicBoxProperties {
        private float widthPercentage = 0.7F;
        private float heightPercentage = 0.7F;
        private FireLibColor color1 = new FireLibColor(150, 150, 255);
        private FireLibColor color2 = new FireLibColor(255, 150, 150);
        private boolean isColor2Used = false;
        private V2_ContentBoxBehaviour.InteriorContentBoxPosition interiorPosition = V2_ContentBoxBehaviour.InteriorContentBoxPosition.TOP_LEFT;
        private V2_ContentBoxBehaviour.ExteriorContentBoxPosition exteriorPosition = V2_ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_TOP;
        private boolean isUsingInteriorPositioning;
        private boolean shouldDrawContentBoxBackground = true;
        private boolean shouldDrawBorder = false;
        private BorderTextureObject borderFont = null;
        private int xOffsetAbsolute = 0;
        private int yOffsetAbsolute = 0;
        private float xOffsetDynamic = 0.0f;
        private float yOffsetDynamic = 0.0f;
        private boolean shouldUseDynamicOffset = false;


        /**
         * Takes two floats between 0.0 and 1.0. Any values over 1.0 will be converted to 1.0
         * @param widthPercentageIn Percentage of the parent elements width this content box should be.
         */
        public V2_ContentBoxBehaviour.BasicBoxProperties widthPercentageOfParent(float widthPercentageIn) {
            widthPercentage = widthPercentageIn;
            return this;
        }

        /**
         * Takes two floats between 0.0 and 1.0. Any values over 1.0 will be converted to 1.0
         * @param heightPercentageIn Percentage of the parent elements width this content box should be.
         */
        public V2_ContentBoxBehaviour.BasicBoxProperties heightPercentageOfParent(float heightPercentageIn) {
            heightPercentage = heightPercentageIn;
            return this;
        }

        /**
         * Takes one FireLibColor variables to create a solid background
         */
        public V2_ContentBoxBehaviour.BasicBoxProperties backgroundColor(FireLibColor color1In) {
            color1 = color1In;
            isColor2Used = false;
            return this;
        }

        /**
         * Takes two FireLibColor variables to create a gradient background
         */
        public V2_ContentBoxBehaviour.BasicBoxProperties backgroundColor(FireLibColor color1In, FireLibColor color2In) {
            color1 = color1In;
            color2 = color2In;
            isColor2Used = true;
            return this;
        }

        /**
         * Takes the ContentBoxBehaviour.InteriorContentBoxPosition Enum to determine where the Content Box will be placed within it's parent
         */
        public V2_ContentBoxBehaviour.BasicBoxProperties position(V2_ContentBoxBehaviour.InteriorContentBoxPosition positionIn) {
            interiorPosition = positionIn;
            isUsingInteriorPositioning = true;
            return this;
        }

        /**
         * Takes the ContentBoxBehaviour.ExteriorContentBoxPosition Enum to determine where the Content Box will be placed outside it's parent
         */
        public V2_ContentBoxBehaviour.BasicBoxProperties exteriorPosition(V2_ContentBoxBehaviour.ExteriorContentBoxPosition positionIn) {
            exteriorPosition = positionIn;
            isUsingInteriorPositioning = false;
            return this;
        }

        /**
         * This method offsets the contentBox by pixels.
         * It is most useful for offsetting elements where gui scale changes don't matter
         */
        public V2_ContentBoxBehaviour.BasicBoxProperties absolutePositionOffset(int xOffsetIn, int yOffsetIn) {
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
        public V2_ContentBoxBehaviour.BasicBoxProperties dynamicPositionOffset(float xOffsetIn, float yOffsetIn) {
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
        public V2_ContentBoxBehaviour.BasicBoxProperties borderFont(BorderTextureObject borderFontIn) {
            borderFont = borderFontIn;

            shouldDrawBorder = true;
            return this;
        }

        /**
         * This method makes it where a content box will no longer draw the background, no matter if a single color or gradient is defined. Borders will also not be drawn.
         */
        public V2_ContentBoxBehaviour.BasicBoxProperties noBackground() {
            shouldDrawContentBoxBackground = false;
            return this;
        }
    }

    public static class BasicButtonProperties {
        private float widthPercentage = 1.0F;
        private float heightPercentage = 1.0F;
        private FireLibColor color1 = new FireLibColor(255, 255, 255);
        private FireLibColor color2 = new FireLibColor(255, 255, 255);
        private boolean isColor2Used = false;
        private FireLibColor hoverColor = new FireLibColor(100, 100, 100, 100);
        private V2_ContentBoxBehaviour.InteriorContentBoxPosition interiorPosition = V2_ContentBoxBehaviour.InteriorContentBoxPosition.TOP_LEFT;
        private V2_ContentBoxBehaviour.ExteriorContentBoxPosition exteriorPosition = V2_ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_TOP;
        private boolean isUsingInteriorPositioning;
        private boolean shouldDrawContentBoxBackground = true;
        private boolean shouldDrawBorder = false;
        private BorderTextureObject borderFont = null;
        private int xOffsetAbsolute = 0;
        private int yOffsetAbsolute = 0;
        private float xOffsetDynamic = 0.0f;
        private float yOffsetDynamic = 0.0f;
        private boolean shouldUseDynamicOffset = false;


        /**
         * Takes two floats between 0.0 and 1.0. Any values over 1.0 will be converted to 1.0
         * @param widthPercentageIn Percentage of the parent elements width this content box should be.
         */
        public V2_ContentBoxBehaviour.BasicButtonProperties widthPercentageOfParent(float widthPercentageIn) {
            widthPercentage = widthPercentageIn;
            return this;
        }

        /**
         * Takes two floats between 0.0 and 1.0. Any values over 1.0 will be converted to 1.0
         * @param heightPercentageIn Percentage of the parent elements width this content box should be.
         */
        public V2_ContentBoxBehaviour.BasicButtonProperties heightPercentageOfParent(float heightPercentageIn) {
            heightPercentage = heightPercentageIn;
            return this;
        }

        /**
         * Takes one FireLibColor variables to create a solid background
         */
        public V2_ContentBoxBehaviour.BasicButtonProperties backgroundColor(FireLibColor color1In) {
            color1 = color1In;
            isColor2Used = false;
            return this;
        }

        /**
         * Takes two FireLibColor variables to create a gradient background
         */
        public V2_ContentBoxBehaviour.BasicButtonProperties backgroundColor(FireLibColor color1In, FireLibColor color2In) {
            color1 = color1In;
            color2 = color2In;
            isColor2Used = true;
            return this;
        }


        /**
         * Takes one FireLibColor variables to define a new color when the button is hovered
         */
        public V2_ContentBoxBehaviour.BasicButtonProperties hoverColor(FireLibColor colorIn) {
            hoverColor = colorIn;
            return this;
        }

        /**
         * Takes the ContentBoxBehaviour.InteriorContentBoxPosition Enum to determine where the Content Box will be placed within it's parent.
         */
        public V2_ContentBoxBehaviour.BasicButtonProperties position(V2_ContentBoxBehaviour.InteriorContentBoxPosition positionIn) {
            interiorPosition = positionIn;
            isUsingInteriorPositioning = true;
            return this;
        }

        /**
         * Takes the ContentBoxBehaviour.ExteriorContentBoxPosition Enum to determine where the Content Box will be placed outside it's parent.
         */
        public V2_ContentBoxBehaviour.BasicButtonProperties exteriorPosition(V2_ContentBoxBehaviour.ExteriorContentBoxPosition positionIn) {
            exteriorPosition = positionIn;
            isUsingInteriorPositioning = false;
            return this;
        }

        /**
         * This method offsets the contentBox by pixels.
         * It is most useful for offsetting elements where gui scale changes don't matter
         */
        public V2_ContentBoxBehaviour.BasicButtonProperties absolutePositionOffset(int xOffsetIn, int yOffsetIn) {
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
        public V2_ContentBoxBehaviour.BasicButtonProperties dynamicPositionOffset(float xOffsetIn, float yOffsetIn) {
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
        public V2_ContentBoxBehaviour.BasicButtonProperties borderFont(BorderTextureObject borderFontIn) {
            borderFont = borderFontIn;

            shouldDrawBorder = true;
            return this;
        }

        /**
         * This method makes it where a content box will no longer draw the background, no matter if a single color or gradient is defined. Borders will also not be drawn.
         */
        public V2_ContentBoxBehaviour.BasicButtonProperties noBackground() {
            shouldDrawContentBoxBackground = false;
            return this;
        }
    }
}
