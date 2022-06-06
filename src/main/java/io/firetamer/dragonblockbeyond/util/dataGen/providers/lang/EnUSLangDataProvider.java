package io.firetamer.dragonblockbeyond.util.dataGen.providers.lang;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.StrongBlockModule;
import io.firetamer.dragonblockbeyond.common_registration.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class EnUSLangDataProvider extends LanguageProvider {

    public EnUSLangDataProvider(DataGenerator gen) {
        super(gen, DragonBlockBeyond.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ItemInit.TEST_ITEM.get(), "Test Item");
        //add(BlockInit.TEST_BLOCK.get(), "Test Block");

        add(StrongBlockModule.WARENAI_FULL_BLOCK.get(), "Warenai Full Block");

        addItemGroup("dbb_items_tab", "DBB Items");
        addItemGroup("dbb_blocks_tab", "DBB Blocks");

        addGui("useRGB", "Use RGB");
        addGui("useHSB", "Use HSB");
        addGui("red", "Red");
        addGui("green", "Green");
        addGui("blue", "Blue");
        addGui("hue", "Hue");
        addGui("saturation", "Saturation");
        addGui("brightness", "Brightness");
    }



    private void addItemGroup(String key, String name) { add("itemGroup." + key, name); }

    private void addGui(String suffix, String text) {
        add("gui." + DragonBlockBeyond.MOD_ID + "." + suffix, text);
    }

    //@SuppressWarnings("unused")
    //private void add(Fluid fluid, String name) { add(new FluidStack(fluid, 2).getTranslationKey(), name); }
}
