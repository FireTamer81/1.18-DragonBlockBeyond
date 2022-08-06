package io.firetamer.dragonblockbeyond.util.gui_stuff.objects.advanced_window_gui;

import io.firetamer.dragonblockbeyond.util.gui_stuff.GuiDrawingContext;
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
    public int color1;
    public int color2;
    public boolean isColor2Used;
    public InteriorContentBoxPosition position;
    public ExteriorContentBoxPosition exteriorPosition = ExteriorContentBoxPosition.RIGHT_TOP;
    public int xOffset;
    public int yOffset;
    public boolean shouldDrawContentBoxBackground;

    public List<IContentBox> interiorDynamicItems;
    public List<ContentBox> exteriorPanels;

    public ContentBoxBehaviour(ContentBoxBehaviour.Properties properties, ContentBoxBehaviour.Children children) {
        this.widthPercentOfParent = properties.widthPercentage;
        this.heightPercentOfParent = properties.heightPercentage;
        this.color1 = properties.color1;
        this.color2 = properties.color2;
        this.isColor2Used = properties.isColor2Used;
        this.position = properties.interiorPosition;
        this.exteriorPosition = properties.exteriorPosition;
        this.xOffset = properties.xOffset;
        this.yOffset = properties.yOffset;
        this.shouldDrawContentBoxBackground = properties.shouldDrawContentBoxBackground;

        this.interiorDynamicItems = children.interiorDynamicItems;
        this.exteriorPanels = children.exteriorPanels;
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    public int getXPosForPositionFromContext(InteriorContentBoxPosition position, GuiDrawingContext context, int contentBoxWidth) {
        if (position == InteriorContentBoxPosition.TOP_LEFT || position == InteriorContentBoxPosition.MIDDLE_LEFT || position == InteriorContentBoxPosition.BOTTOM_LEFT) {
            return 0;
        } else if (position == InteriorContentBoxPosition.TOP_MIDDLE || position == InteriorContentBoxPosition.CENTER || position == InteriorContentBoxPosition.BOTTOM_MIDDLE) {
            return (context.parentWidth - contentBoxWidth) / 2;
        } else {
            return context.parentWidth - contentBoxWidth;
        }
    }

    public int getYPosForPositionFromContext(InteriorContentBoxPosition position, GuiDrawingContext context, int contentBoxHeight) {
        if (position == InteriorContentBoxPosition.TOP_LEFT || position == InteriorContentBoxPosition.TOP_MIDDLE || position == InteriorContentBoxPosition.TOP_RIGHT) {
            return 0;
        } else if (position == InteriorContentBoxPosition.MIDDLE_LEFT || position == InteriorContentBoxPosition.CENTER || position == InteriorContentBoxPosition.MIDDLE_RIGHT) {
            return (context.parentHeight - contentBoxHeight) / 2;
        } else {
            return context.parentHeight - contentBoxHeight;
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    public static class Properties {
        protected float widthPercentage = 1.0F;
        protected float heightPercentage = 1.0F;
        protected int color1 = 0xFFFFFFFF;
        protected int color2 = 0xFFFFFFFF;
        protected boolean isColor2Used = false;
        protected InteriorContentBoxPosition interiorPosition = InteriorContentBoxPosition.TOP_LEFT;
        protected ExteriorContentBoxPosition exteriorPosition = ExteriorContentBoxPosition.RIGHT_TOP;
        protected int xOffset = 0;
        protected int yOffset = 0;
        protected boolean shouldDrawContentBoxBackground = true;


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
         * After the original position enum is assigned, this adds an additional offset. It's purpose is for fine tuning of content's position
         */
        public ContentBoxBehaviour.Properties xOffset(int xOffsetIn) {
            xOffset = xOffsetIn;
            return this;
        }

        /**
         * After the original position enum is assigned, this adds an additional offset. It's purpose is for fine tuning of content's position
         */
        public ContentBoxBehaviour.Properties yOffset(int yOffsetIn) {
            yOffset = yOffsetIn;
            return this;
        }

        /**
         * This method makes it where a content box will no longer draw the background, no matter if a single color or gradient is defined.
         */
        public ContentBoxBehaviour.Properties noBackground() {
            shouldDrawContentBoxBackground = false;
            return this;
        }
    }

    public static class Children {
        protected List<IContentBox> interiorDynamicItems = new ArrayList<>();
        protected List<ContentBox> exteriorPanels = new ArrayList<>();

        /**
         * Adds a IContentBox object to the interiorDynamicItems List, which are then added to the interior of the content box in question.
         * These objects are dynamically placed based on the order they were added and their own properties
         */
        public ContentBoxBehaviour.Children addInteriorItem(IContentBox newItemIn) {
            interiorDynamicItems.add(newItemIn);

            return this;
        }

        /**
         * Adds a IContentBox object to the exteriorPanels list, which are placed outside the parent content box in question.
         * These objects are placed outside of the parent content box based on the objects properties.
         */
        public ContentBoxBehaviour.Children addExteriorPanels(ContentBox newPanelIn) {
            exteriorPanels.add(newPanelIn);

            return this;
        }
    }
}
