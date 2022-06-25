package io.firetamer.dragonblockbeyond.handlers;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.BorderTextureObject;
import net.minecraft.resources.ResourceLocation;

public class TextureHandler {
    private static final ResourceLocation BORDER_MAP_1 = new ResourceLocation(DragonBlockBeyond.MOD_ID, "textures/gui/gui_panel_border_styles_map1.png");
    public static BorderTextureObject BORDER_1;
    public static BorderTextureObject BORDER_2;

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

        BORDER_2 = new BorderTextureObject(BORDER_MAP_1,
                33, 10, 0, 10, 0, 0,
                44, 12, 2, 8, 0, 2,
                57, 10, 0, 10, 0, 0,
                57, 8, 11, 12, 0, 0,
                57, 10, 24, 10, 0, 0,
                44, 12, 24, 8, 0, 0,
                33, 10, 24, 10, 0, 0,
                35, 8, 11, 12, 2, 0);
    }
}
