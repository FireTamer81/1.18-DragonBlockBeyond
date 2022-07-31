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
        super(getItems());
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    private static List<IContentBox> getItems() {
        List<IContentBox> items = new ArrayList<>();

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        items.add(testContentBox);

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        return items;
    }

    private static final IContentBox testContentBox = new ContentBox(new ContentBoxBehaviour.Properties()
            .backgroundColor(new FireLibColor(0, 255, 255), new FireLibColor(0, 255, 0))
            .heightPercentageOfParent(0.6f)
            .widthPercentageOfParent(0.2f)
    );


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
