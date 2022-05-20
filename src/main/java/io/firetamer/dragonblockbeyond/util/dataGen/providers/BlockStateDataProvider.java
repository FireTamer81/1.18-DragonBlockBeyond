package io.firetamer.dragonblockbeyond.util.dataGen.providers;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.ColorableStrongBlockModule;
import io.firetamer.dragonblockbeyond.common_registration.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateDataProvider extends BlockStateProvider {

    public BlockStateDataProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, DragonBlockBeyond.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(BlockInit.TEST_BLOCK.get());

        //Actually generates an item model... I think
        //blocks(ColorableStrongBlockModule.WARENAI_FULL_BLOCK.get());

        /*
        ColorableStrongBlockModule.BLOCKS.getEntries()
                .stream()
                //.filter((block) -> block.get() != ColorableStrongBlockModule.RGB_GLASS_PANE.get())
                .forEach((block) -> blockItems(block.get()));
        */




        /**
        simpleBlock(ColorableBlockTest.RGB_CARPET.get(),
                models().singleTexture(ColorableBlockTest.RGB_CARPET.getId().getPath(),
                        modLoc(ModelProvider.BLOCK_FOLDER + "/thin_block"),
                        "all",
                        modLoc(ModelProvider.BLOCK_FOLDER + "/wool")));

        paneBlock(ColorableBlockTest.RGB_GLASS_PANE.get(),
                models().withExistingParent(ColorableBlockTest.RGB_GLASS_PANE.getId().toString() + "_post",
                        modLoc(ModelProvider.BLOCK_FOLDER + "/template_glass_pane_post"))
                        .texture("pane", modLoc(ModelProvider.BLOCK_FOLDER + "/glass"))
                        .texture("edge", modLoc(ModelProvider.BLOCK_FOLDER + "/glass_pane_top")),
                models().withExistingParent(ColorableBlockTest.RGB_GLASS_PANE.getId().toString() + "_side",
                        modLoc(ModelProvider.BLOCK_FOLDER + "/template_glass_pane_side"))
                        .texture("pane", modLoc(ModelProvider.BLOCK_FOLDER + "/glass"))
                        .texture("edge", modLoc(ModelProvider.BLOCK_FOLDER + "/glass_pane_top")),
                models().withExistingParent(ColorableBlockTest.RGB_GLASS_PANE.getId().toString() + "_side_alt",
                        modLoc(ModelProvider.BLOCK_FOLDER + "/template_glass_pane_side_alt"))
                        .texture("pane", modLoc(ModelProvider.BLOCK_FOLDER + "/glass"))
                        .texture("edge", modLoc(ModelProvider.BLOCK_FOLDER + "/glass_pane_top")),
                models().withExistingParent(ColorableBlockTest.RGB_GLASS_PANE.getId().toString() + "_noside",
                        modLoc(ModelProvider.BLOCK_FOLDER + "/template_glass_pane_noside"))
                        .texture("pane", modLoc(ModelProvider.BLOCK_FOLDER + "/glass")),
                models().withExistingParent(ColorableBlockTest.RGB_GLASS_PANE.getId().toString() + "_noside_alt",
                        modLoc(ModelProvider.BLOCK_FOLDER + "/template_glass_pane_noside_alt"))
                        .texture("pane", modLoc(ModelProvider.BLOCK_FOLDER + "/glass")));


        getVariantBuilder(ColorableBlockTest.RGB_REDSTONE_LAMP.get()).forAllStates(state -> {
            return state.getValue(RedstoneLampBlock.LIT)
                    ? ConfiguredModel.builder()
                    .modelFile(models().singleTexture(ColorableBlockTest.RGB_REDSTONE_LAMP.getId()
                                    .getPath() +
                                    "_on",
                            modLoc(ModelProvider.BLOCK_FOLDER +
                                    "/example_blocks"),
                            "all",
                            modLoc(ModelProvider.BLOCK_FOLDER +
                                    "/redstone_lamp_on")))
                    .build()
                    : ConfiguredModel.builder()
                    .modelFile(models().singleTexture(ColorableBlockTest.RGB_REDSTONE_LAMP.getId().getPath(),
                            modLoc(ModelProvider.BLOCK_FOLDER + "/example_blocks"),
                            "all",
                            modLoc(ModelProvider.BLOCK_FOLDER +
                                    "/redstone_lamp")))
                    .build();
        });


        blocks(ColorableBlockTest.RGB_CONCRETE.get());
        slabBlocks(ColorableBlockTest.RGB_CONCRETE_SLAB.get());
        stairBlocks(ColorableBlockTest.RGB_DARK_PRISMARINE_STAIRS.get());

        ColorableBlockTest.BLOCKS.getEntries()
                .stream()
                .filter((block) -> block.get() != ColorableBlockTest.RGB_GLASS_PANE.get())
                .forEach((block) -> blockItems(block.get()));
        **/
    }






    private void blocks(Block block) {
        String path = block.getRegistryName().getPath();
        String loc = ModelProvider.BLOCK_FOLDER + "/" + path;
        simpleBlock(block,
                models().singleTexture(path, modLoc(ModelProvider.BLOCK_FOLDER + "/blocks"), "all", modLoc(loc)));
    }

    private void slabBlocks(Block block) {
        String path = block.getRegistryName().getPath();
        String loc = ModelProvider.BLOCK_FOLDER + "/" + path.replace("_slab", "");
        ModelFile slabBottom = models().withExistingParent(path, modLoc(ModelProvider.BLOCK_FOLDER + "/slab"))
                .texture("all", loc);
        ModelFile slabTop = models().withExistingParent(path + "_top", modLoc(ModelProvider.BLOCK_FOLDER + "/slab_top"))
                .texture("all", loc);
        ModelFile slabDouble = models().getExistingFile(modLoc(loc));
        slabBlock((SlabBlock) block, slabBottom, slabTop, slabDouble);
        ConfiguredModel.builder().modelFile(slabBottom).build();
    }

    private void stairBlocks(Block block) {
        String path = block.getRegistryName().getPath();
        String loc = ModelProvider.BLOCK_FOLDER + "/" + path.replace("_stairs", "");
        ModelFile stairs = models().withExistingParent(path, modLoc(ModelProvider.BLOCK_FOLDER + "/stairs"))
                .texture("all", loc);
        ModelFile stairsInner = models().withExistingParent(path + "_inner",
                modLoc(ModelProvider.BLOCK_FOLDER + "/inner_stairs"))
                .texture("all", loc);
        ModelFile stairsOuter = models().withExistingParent(path + "_outer",
                modLoc(ModelProvider.BLOCK_FOLDER + "/outer_stairs"))
                .texture("all", loc);
        stairsBlock((StairBlock) block, stairs, stairsInner, stairsOuter);
        ConfiguredModel.builder().modelFile(stairs).build();
    }

    private void blockItems(Block block) {
        String path = block.getRegistryName().getPath();
        simpleBlockItem(block, models().getExistingFile(modLoc(ModelProvider.BLOCK_FOLDER + "/" + path)));
    }
}
