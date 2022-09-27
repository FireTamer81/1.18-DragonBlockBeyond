package io.firetamer.dragonblockbeyond._modules.player_gui_module.test_advanced_window;

import io.firetamer.dragonblockbeyond.util.library_candidates.FireLibColor;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.V2_window_gui.V2_AbstractWindow;

public class V2_WindowTestScreen extends V2_AbstractWindow {

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public int getBackgroundColor() {
        return new FireLibColor(50, 50, 50, 100).getRGBA();
    }

    @Override
    public void onOpen() {}
}
