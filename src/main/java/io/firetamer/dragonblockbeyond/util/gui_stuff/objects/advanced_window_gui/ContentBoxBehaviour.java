package io.firetamer.dragonblockbeyond.util.gui_stuff.objects.advanced_window_gui;

import io.firetamer.dragonblockbeyond.util.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.gui_stuff.GuiDrawingContext;
import io.firetamer.dragonblockbeyond.util.library_candidates.FireLibColor;

public class ContentBoxBehaviour {

    public enum ContentBoxPosition {
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

    public float widthPercentOfParent;
    public float heightPercentOfParent;
    public int color1;
    public int color2;
    public boolean isColor2Used;
    private ContentBoxBehaviour.ContentBoxPosition position;

    public ContentBoxBehaviour(ContentBoxBehaviour.Properties properties) {
        this.widthPercentOfParent = properties.widthPercentage;
        this.heightPercentOfParent = properties.heightPercentage;
        this.color1 = properties.color1;
        this.color2 = properties.color2;
        this.isColor2Used = properties.isColor2Used;
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    public static class Properties {
        protected float widthPercentage = 1.0F;
        protected float heightPercentage = 1.0F;
        protected int color1 = 0xFFFFFFFF;
        protected int color2 = 0xFFFFFFFF;
        protected boolean isColor2Used;

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
            color1 = color1In.getRGBA();
            isColor2Used = false;
            return this;
        }

        /**
         * Takes two FireLibColor variables to create a gradient background
         */
        public ContentBoxBehaviour.Properties backgroundColor(FireLibColor color1In, FireLibColor color2In) {
            color1 = color1In.getRGBA();
            color2 = color2In.getRGBA();
            isColor2Used = true;
            return this;
        }

        /**
         * Takes one int variables to create a solid background
         */
        public ContentBoxBehaviour.Properties backgroundColor(int color1In) {
            color1 = color1In;
            isColor2Used = false;
            return this;
        }

        /**
         * Takes two int variables to create a gradient background
         */
        public ContentBoxBehaviour.Properties backgroundColor(int color1In, int color2In) {
            color1 = color1In;
            color2 = color2In;
            isColor2Used = true;
            return this;
        }
    }



}
