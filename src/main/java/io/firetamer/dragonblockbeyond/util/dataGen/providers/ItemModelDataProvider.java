package io.firetamer.dragonblockbeyond.util.dataGen.providers;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.StrongBlockModule;
import io.firetamer.dragonblockbeyond.common_registration.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelDataProvider extends ItemModelProvider {

    public ItemModelDataProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DragonBlockBeyond.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //makeSimpleBlockItem(BlockInit.TEST_BLOCK.get());

        simpleItem(ItemInit.TEST_ITEM.get());

        item2Layers(StrongBlockModule.PAINT_BUCKET.get());

        /**
        singleTexture(ColorableBlockTest.RGB_GLASS_PANE.getId().getPath(),
                mcLoc(ITEM_FOLDER + "/generated"),
                "layer0",
                modLoc(BLOCK_FOLDER + "/glass"));
        **/
    }





    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture(
                        "layer0", new ResourceLocation(DragonBlockBeyond.MOD_ID, "item/" + item.getRegistryName().getPath()));
    }

    protected void makeSimpleBlockItem(Block block) {
        getBuilder(block.asItem().getRegistryName().toString())
                .parent(getExistingFile(new ResourceLocation(DragonBlockBeyond.MOD_ID, "block/" + block.asItem().getRegistryName().getPath())));
    }

    private void item2Layers(Item item) {
        String path = item.getRegistryName().getPath();
        String loc = ITEM_FOLDER + "/" + path;
        singleTexture(path, mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc(loc)).texture("layer1",
                modLoc(loc + "_color"));
    }
}
