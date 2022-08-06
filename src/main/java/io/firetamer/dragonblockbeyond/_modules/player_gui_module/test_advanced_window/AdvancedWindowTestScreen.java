package io.firetamer.dragonblockbeyond._modules.player_gui_module.test_advanced_window;

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



    public static final ContentBox testExteriorPanel = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(0, 0, 255))
                    .heightPercentageOfParent(0.5f)
                    .widthPercentageOfParent(0.5f)
                    .exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_TOP),

            new ContentBoxBehaviour.Children()
    );

    public static final IContentBox testContentBox = new ContentBox(
            new ContentBoxBehaviour.Properties()
                    .backgroundColor(new FireLibColor(0, 255, 255), new FireLibColor(0, 255, 0))
                    .heightPercentageOfParent(0.6f)
                    .widthPercentageOfParent(0.2f)
                    .position(ContentBoxBehaviour.InteriorContentBoxPosition.CENTER),
                    //.xOffset(-40).yOffset(-40),

            new ContentBoxBehaviour.Children()
                    .addExteriorPanels(testExteriorPanel)
    );




    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    private static List<IContentBox> getTopLevelItems() {
        List<IContentBox> items = new ArrayList<>();

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        items.add(testContentBox);

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        return items;
    }
}
