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


    public static final ContentBox testExteriorPanel = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(255, 0, 0))
                    .heightPercentageOfParent(0.5f)
                    .widthPercentageOfParent(0.5f)
                    //.position(ContentBoxBehaviour.InteriorContentBoxPosition.TOP_LEFT)
                    .exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_MIDDLE)
                    .borderFont(TextureHandler.BORDER_1)
            ,

            new ContentBoxBehaviour.Children()
    );

    public static final ContentBox testInteriorPanel2 = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(255, 0, 0))
                    .heightPercentageOfParent(0.2f)
                    .widthPercentageOfParent(0.2f)
                    .position(ContentBoxBehaviour.InteriorContentBoxPosition.TOP_RIGHT)
                    //.exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_TOP)
            ,

            new ContentBoxBehaviour.Children()
    );

    public static final ContentBox testInteriorPanel3 = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(0, 0, 255))
                    .heightPercentageOfParent(0.2f)
                    .widthPercentageOfParent(0.2f)
                    .position(ContentBoxBehaviour.InteriorContentBoxPosition.CENTER)
                    //.exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_MIDDLE)
            ,

            new ContentBoxBehaviour.Children()
    );

    public static final ContentBox testInteriorPanel4 = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(255, 0, 0))
                    .heightPercentageOfParent(0.2f)
                    .widthPercentageOfParent(0.2f)
                    .position(ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_LEFT)
                    //.exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_BOTTOM)
            ,

            new ContentBoxBehaviour.Children()
    );

    public static final ContentBox testInteriorPanel5 = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(255, 0, 0))
                    .heightPercentageOfParent(0.2f)
                    .widthPercentageOfParent(0.2f)
                    .position(ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_RIGHT)
                    //.exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_LEFT)
            ,

            new ContentBoxBehaviour.Children()
    );

    public static final ContentBox testContentBox = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(0, 255, 255), new FireLibColor(0, 255, 0))
                    .heightPercentageOfParent(0.4f)
                    .widthPercentageOfParent(0.2f)
                    .position(ContentBoxBehaviour.InteriorContentBoxPosition.CENTER)
                    .borderFont(TextureHandler.BORDER_2)
                    //.noBackground()
            ,

            new ContentBoxBehaviour.Children()
                    //.addExteriorPanels(testExteriorPanel)
                    //.addInteriorItem(testInteriorPanel2)
                    //.addInteriorItem(testInteriorPanel3)
                    //.addInteriorItem(testInteriorPanel4)
                    //.addInteriorItem(testInteriorPanel5)
    );
}
