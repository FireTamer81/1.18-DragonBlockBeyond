package io.firetamer.dragonblockbeyond.handlers;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.util.gui_stuff.objects.BorderTextureObject;
import net.minecraft.resources.ResourceLocation;

public class TextureHandler {
    private static final ResourceLocation BORDER_MAP_1 = new ResourceLocation(DragonBlockBeyond.MOD_ID, "textures/gui/dynamic_panel_test.png");
    public static BorderTextureObject BORDER_1;

    public TextureHandler() {}

    public void init() {
        createGUIBorderTextureData();
    }


    private void createGUIBorderTextureData() {
        BORDER_1 = new BorderTextureObject(BORDER_MAP_1,
                0, 8, 0, 8, 0, 0,
                9, 12, 0, 8, 0, 0,
                22, 8, 0, 8, 0, 0,
                22, 8, 9, 12, 0, 0,
                22, 8, 22, 8, 0, 0,
                9, 12, 22, 8, 0, 0,
                0, 8, 22, 8, 0, 0,
                0, 8, 9, 12, 0, 0);


    }
}
