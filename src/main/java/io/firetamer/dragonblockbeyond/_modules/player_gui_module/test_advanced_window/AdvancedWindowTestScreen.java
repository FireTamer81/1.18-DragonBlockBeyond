package io.firetamer.dragonblockbeyond._modules.player_gui_module.test_advanced_window;

import io.firetamer.dragonblockbeyond.handlers.TextureHandler;
import io.firetamer.dragonblockbeyond.util.gui_stuff.objects.advanced_window_gui.AbstractAdvancedWindow;
import io.firetamer.dragonblockbeyond.util.gui_stuff.objects.advanced_window_gui.ContentBox;
import io.firetamer.dragonblockbeyond.util.gui_stuff.objects.advanced_window_gui.ContentBoxBehaviour;
import io.firetamer.dragonblockbeyond.util.gui_stuff.objects.advanced_window_gui.IContentBox;
import io.firetamer.dragonblockbeyond.util.library_candidates.FireLibColor;

import java.util.ArrayList;
import java.util.List;

public class AdvancedWindowTestScreen extends AbstractAdvancedWindow {


    public AdvancedWindowTestScreen() {
        super(getTopLevelItems());
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public int getBackgroundColor() {
        return new FireLibColor(50, 50, 50, 100).getRGBA();
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    private static List<IContentBox> getTopLevelItems() {
        List<IContentBox> items = new ArrayList<>();

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        items.add(testContentBox);

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        return items;
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    public static final ContentBox testPanel1A = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(255, 0, 255))
                    .heightPercentageOfParent(0.4f)
                    .widthPercentageOfParent(0.6f)
                    //.position(ContentBoxBehaviour.InteriorContentBoxPosition.TOP_MIDDLE)
                    .exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_MIDDLE)
                    .borderFont(TextureHandler.BORDER_2)
            ,

            new ContentBoxBehaviour.Children()
    );

    public static final ContentBox testPanel = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(255, 0, 0))
                    .heightPercentageOfParent(0.2f)
                    .widthPercentageOfParent(0.8f)
                    .position(ContentBoxBehaviour.InteriorContentBoxPosition.TOP_MIDDLE)
                    //.exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_LEFT)
                    .borderFont(TextureHandler.BORDER_1)
            ,

            new ContentBoxBehaviour.Children()
                    .addExteriorChild(testPanel1A)
    );

    public static final ContentBox testPanel2A = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(255, 0, 255))
                    .heightPercentageOfParent(0.4f)
                    .widthPercentageOfParent(0.6f)
                    //.position(ContentBoxBehaviour.InteriorContentBoxPosition.TOP_MIDDLE)
                    .exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_MIDDLE)
                    .borderFont(TextureHandler.BORDER_2)
            ,

            new ContentBoxBehaviour.Children()
    );

    public static final ContentBox testPanel2 = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(255, 0, 0))
                    .heightPercentageOfParent(0.2f)
                    .widthPercentageOfParent(0.8f)
                    //.position(ContentBoxBehaviour.InteriorContentBoxPosition.TOP_MIDDLE)
                    .exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_TOP)
                    .borderFont(TextureHandler.BORDER_1)
            ,

            new ContentBoxBehaviour.Children()
                    .addExteriorChild(testPanel2A)
    );





    public static final ContentBox testContentBox = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(0, 255, 255), new FireLibColor(0, 255, 0))
                    .heightPercentageOfParent(0.5f)
                    .widthPercentageOfParent(0.4f)
                    .position(ContentBoxBehaviour.InteriorContentBoxPosition.MIDDLE_LEFT)
                    //.absolutePositionOffset(-40, -40)
                    //.dynamicPositionOffset(0.2f, -0.3f)
                    .borderFont(TextureHandler.BORDER_2)
                    //.noBackground()
            ,

            new ContentBoxBehaviour.Children()
                    .addInteriorChild(testPanel)
                    .addExteriorChild(testPanel2)
    );
}
