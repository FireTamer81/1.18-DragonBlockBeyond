package io.firetamer.dragonblockbeyond.util.dataGen.providers;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.StrongBlockModule;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.*;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.properties.WarenaiBlockConditionEnum;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.properties.WarenaiBlockPatternEnum;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;



//TODO Make datagen methods for fence, wall, and other such blocks that are currently being handmade (Because MultiPartBuilders are a pain in the ass)



public class BlockStateDataProvider extends BlockStateProvider {

    public BlockStateDataProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, DragonBlockBeyond.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //simpleBlock(BlockInit.TEST_BLOCK.get());

        warenaiBlock(StrongBlockModule.WARENAI_FULL_BLOCK.get());
        warenaiBlockStair(StrongBlockModule.WARENAI_STAIRS_BLOCK.get());
        warenaiBlockSlab(StrongBlockModule.WARENAI_SLAB_BLOCK.get());
        warenaiReinforcedGlassBlock(StrongBlockModule.WARENAI_GLASS.get());
        warenaiReinforcedGlassSlab(StrongBlockModule.WARENAI_GLASS_SLAB.get());
        warenaiReinforcedGlassStairs(StrongBlockModule.WARENAI_GLASS_STAIRS.get());

        StrongBlockModule.BLOCKS.getEntries()
                .stream()
                .filter((block) ->
                        block.get() != StrongBlockModule.WARENAI_FENCE_BLOCK.get()
                        //&& block.get() != StrongBlockModule.WARENAI_WALL_BLOCK.get()
                )
                .forEach((block) -> blockItems(block.get()));





        /**
        simpleBlock(ColorableBlockTest.RGB_CARPET.get(),
                models().singleTexture(ColorableBlockTest.RGB_CARPET.getId().getPath(),
                        modLoc(ModelProvider.BLOCK_FOLDER + "/thin_block"),
                        "all",
                        modLoc(ModelProvider.BLOCK_FOLDER + "/wool")));

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





    private void blockItems(Block block) {
        String path = block.getRegistryName().getPath();
        simpleBlockItem(block, models().getExistingFile(modLoc(ModelProvider.BLOCK_FOLDER + "/" + path)));
    }






    /******************************************************************************************************************/
    //Warenai Block DataGen Methods
    /******************************************************************************************************************/

    private void warenaiBlock(WarenaiBlock block) {
        String path = block.getRegistryName().getPath();
        String loc = ModelProvider.BLOCK_FOLDER + "/" + path;

        warenaiBlockInternal(block,
                models().singleTexture(path + "_large_bricks_polished", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_polished")),
                models().singleTexture(path + "_small_bricks_polished", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_polished")),
                models().singleTexture(path + "_polished", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block_polished")),
                models().singleTexture(path + "_large_bricks", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks")),
                models().singleTexture(path + "_small_bricks", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks")),
                models().singleTexture(path, modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block")),
                models().singleTexture(path + "_large_bricks_scuffed", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_scuffed")),
                models().singleTexture(path + "_small_bricks_scuffed", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_scuffed")),
                models().singleTexture(path + "_scuffed", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block_scuffed")),
                models().singleTexture(path + "_large_bricks_cracked1", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked1")),
                models().singleTexture(path + "_small_bricks_cracked1", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked1")),
                models().singleTexture(path + "_cracked1", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked1")),
                models().singleTexture(path + "_large_bricks_cracked2", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked2")),
                models().singleTexture(path + "_small_bricks_cracked2", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked2")),
                models().singleTexture(path + "_cracked2", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked2")),
                models().singleTexture(path + "_large_bricks_cracked3", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked3")),
                models().singleTexture(path + "_small_bricks_cracked3", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked3")),
                models().singleTexture(path + "_cracked3", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked3")),
                models().singleTexture(path + "_large_bricks_cracked4", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked4")),
                models().singleTexture(path + "_small_bricks_cracked4", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked4")),
                models().singleTexture(path + "_cracked4", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked4"))
        );
    }

    private void warenaiBlockInternal(WarenaiBlock block,
                                      ModelFile polishedLargeBrick, ModelFile polishedSmallBrick, ModelFile polishedNormal,
                                      ModelFile largeBrick, ModelFile smallBrick, ModelFile normal,
                                      ModelFile scuffedLargeBrick, ModelFile scuffedSmallBrick, ModelFile scuffedNormal,
                                      ModelFile cracked1LargeBrick, ModelFile cracked1SmallBrick, ModelFile cracked1Normal,
                                      ModelFile cracked2LargeBrick, ModelFile cracked2SmallBrick, ModelFile cracked2Normal,
                                      ModelFile cracked3LargeBrick, ModelFile cracked3SmallBrick, ModelFile cracked3Normal,
                                      ModelFile cracked4LargeBrick, ModelFile cracked4SmallBrick, ModelFile cracked4Normal) {

        getVariantBuilder(block).forAllStatesExcept(state -> {
            WarenaiBlockPatternEnum patternEnum = state.getValue(WarenaiBlock.BLOCK_PATTERN);
            WarenaiBlockConditionEnum conditionEnum = state.getValue(WarenaiBlock.BLOCK_CONDITION);

            return ConfiguredModel.builder()
                    .modelFile(warenaiBlockStateTest(
                            patternEnum, conditionEnum,
                            polishedNormal, normal, scuffedNormal, cracked1Normal, cracked2Normal, cracked3Normal, cracked4Normal,
                            polishedSmallBrick, smallBrick, scuffedSmallBrick, cracked1SmallBrick, cracked2SmallBrick, cracked3SmallBrick, cracked4SmallBrick,
                            polishedLargeBrick, largeBrick, scuffedLargeBrick, cracked1LargeBrick, cracked2LargeBrick, cracked3LargeBrick, cracked4LargeBrick
                    )).build();
        });
    }

    public ModelFile warenaiBlockStateTest(WarenaiBlockPatternEnum patternEnumIn, WarenaiBlockConditionEnum conditionEnumIn,
                                           ModelFile polishedNormal, ModelFile normal, ModelFile scuffedNormal, ModelFile cracked1Normal, ModelFile cracked2Normal, ModelFile cracked3Normal, ModelFile cracked4Normal,
                                           ModelFile polishedSmallBrick, ModelFile smallBrick, ModelFile scuffedSmallBrick, ModelFile cracked1SmallBrick, ModelFile cracked2SmallBrick, ModelFile cracked3SmallBrick, ModelFile cracked4SmallBrick,
                                           ModelFile polishedLargeBrick, ModelFile largeBrick, ModelFile scuffedLargeBrick, ModelFile cracked1LargeBrick, ModelFile cracked2LargeBrick, ModelFile cracked3LargeBrick, ModelFile cracked4LargeBrick) {

        if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)       { return polishedNormal; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)         { return normal; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)        { return scuffedNormal; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)       { return cracked1Normal; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)       { return cracked2Normal; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)       { return cracked3Normal; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)       { return cracked4Normal; }

        else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return polishedSmallBrick; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return smallBrick; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return scuffedSmallBrick; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return cracked1SmallBrick; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return cracked2SmallBrick; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return cracked3SmallBrick; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)  { return cracked4SmallBrick; }

        else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return polishedLargeBrick; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return largeBrick; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return scuffedLargeBrick; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return cracked1LargeBrick; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return cracked2LargeBrick; }
        else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return cracked3LargeBrick; }
        else {
            return cracked4LargeBrick;
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    private void warenaiBlockStair(WarenaiBlockStairs block) {
        String path = block.getRegistryName().getPath();
        String loc = ModelProvider.BLOCK_FOLDER + "/" + path;

        warenaiBlockStairInternal(block,
                models().singleTexture(path + "_large_bricks_polished", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_polished")),
                models().singleTexture(path + "_large_bricks_polished_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_polished")),
                models().singleTexture(path + "_large_bricks_polished_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_polished")),

                models().singleTexture(path + "_small_bricks_polished", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_polished")),
                models().singleTexture(path + "_small_bricks_polished_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_polished")),
                models().singleTexture(path + "_small_bricks_polished_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_polished")),

                models().singleTexture(path + "_polished", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_polished")),
                models().singleTexture(path + "_polished_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_polished")),
                models().singleTexture(path + "_polished_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_polished")),

                models().singleTexture(path + "_large_bricks", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks")),
                models().singleTexture(path + "_large_bricks_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks")),
                models().singleTexture(path + "_large_bricks_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks")),

                models().singleTexture(path + "_small_bricks", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks")),
                models().singleTexture(path + "_small_bricks_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks")),
                models().singleTexture(path + "_small_bricks_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks")),

                models().singleTexture(path + "", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block")),
                models().singleTexture(path + "_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block")),
                models().singleTexture(path + "_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block")),

                models().singleTexture(path + "_large_bricks_scuffed", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_scuffed")),
                models().singleTexture(path + "_large_bricks_scuffed_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_scuffed")),
                models().singleTexture(path + "_large_bricks_scuffed_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_scuffed")),

                models().singleTexture(path + "_small_bricks_scuffed", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_scuffed")),
                models().singleTexture(path + "_small_bricks_scuffed_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_scuffed")),
                models().singleTexture(path + "_small_bricks_scuffed_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_scuffed")),

                models().singleTexture(path + "_scuffed", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_scuffed")),
                models().singleTexture(path + "_scuffed_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_scuffed")),
                models().singleTexture(path + "_scuffed_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_scuffed")),

                models().singleTexture(path + "_large_bricks_cracked1", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked1")),
                models().singleTexture(path + "_large_bricks_cracked1_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked1")),
                models().singleTexture(path + "_large_bricks_cracked1_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked1")),

                models().singleTexture(path + "_small_bricks_cracked1", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked1")),
                models().singleTexture(path + "_small_bricks_cracked1_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked1")),
                models().singleTexture(path + "_small_bricks_cracked1_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked1")),

                models().singleTexture(path + "_cracked1", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked1")),
                models().singleTexture(path + "_cracked1_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked1")),
                models().singleTexture(path + "_cracked1_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked1")),

                models().singleTexture(path + "_large_bricks_cracked2", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked2")),
                models().singleTexture(path + "_large_bricks_cracked2_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked2")),
                models().singleTexture(path + "_large_bricks_cracked2_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked2")),

                models().singleTexture(path + "_small_bricks_cracked2", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked2")),
                models().singleTexture(path + "_small_bricks_cracked2_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked2")),
                models().singleTexture(path + "_small_bricks_cracked2_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked2")),

                models().singleTexture(path + "_cracked2", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked2")),
                models().singleTexture(path + "_cracked2_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked2")),
                models().singleTexture(path + "_cracked2_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked2")),

                models().singleTexture(path + "_large_bricks_cracked3", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked3")),
                models().singleTexture(path + "_large_bricks_cracked3_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked3")),
                models().singleTexture(path + "_large_bricks_cracked3_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked3")),

                models().singleTexture(path + "_small_bricks_cracked3", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked3")),
                models().singleTexture(path + "_small_bricks_cracked3_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked3")),
                models().singleTexture(path + "_small_bricks_cracked3_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked3")),

                models().singleTexture(path + "_cracked3", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked3")),
                models().singleTexture(path + "_cracked3_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked3")),
                models().singleTexture(path + "_cracked3_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked3")),

                models().singleTexture(path + "_large_bricks_cracked4", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked4")),
                models().singleTexture(path + "_large_bricks_cracked4_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked4")),
                models().singleTexture(path + "_large_bricks_cracked4_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked4")),

                models().singleTexture(path + "_small_bricks_cracked4", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked4")),
                models().singleTexture(path + "_small_bricks_cracked4_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked4")),
                models().singleTexture(path + "_small_bricks_cracked4_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked4")),

                models().singleTexture(path + "_block_cracked4", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked4")),
                models().singleTexture(path + "_block_cracked4_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked4")),
                models().singleTexture(path + "_block_cracked4_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked4"))
        );
    }

    private void warenaiBlockStairInternal(WarenaiBlockStairs block, 
                                           ModelFile largeBrickPolished, ModelFile largeBrickPolishedInner, ModelFile largeBrickPolishedOuter,
                                           ModelFile smallBrickPolished, ModelFile smallBrickPolishedInner, ModelFile smallBrickPolishedOuter,
                                           ModelFile normalPolished, ModelFile normalPolishedInner, ModelFile normalPolishedOuter,
                                           ModelFile largeBrick, ModelFile largeBrickInner, ModelFile largeBrickOuter,
                                           ModelFile smallBrick, ModelFile smallBrickInner, ModelFile smallBrickOuter,
                                           ModelFile normal, ModelFile normalInner, ModelFile normalOuter,
                                           ModelFile largeBrickScuffed, ModelFile largeBrickScuffedInner, ModelFile largeBrickScuffedOuter,
                                           ModelFile smallBrickScuffed, ModelFile smallBrickScuffedInner, ModelFile smallBrickScuffedOuter,
                                           ModelFile normalScuffed, ModelFile normalScuffedInner, ModelFile normalScuffedOuter,
                                           ModelFile largeBrickCracked1, ModelFile largeBrickCracked1Inner, ModelFile largeBrickCracked1Outer,
                                           ModelFile smallBrickCracked1, ModelFile smallBrickCracked1Inner, ModelFile smallBrickCracked1Outer,
                                           ModelFile normalCracked1, ModelFile normalCracked1Inner, ModelFile normalCracked1Outer,
                                           ModelFile largeBrickCracked2, ModelFile largeBrickCracked2Inner, ModelFile largeBrickCracked2Outer,
                                           ModelFile smallBrickCracked2, ModelFile smallBrickCracked2Inner, ModelFile smallBrickCracked2Outer,
                                           ModelFile normalCracked2, ModelFile normalCracked2Inner, ModelFile normalCracked2Outer,
                                           ModelFile largeBrickCracked3, ModelFile largeBrickCracked3Inner, ModelFile largeBrickCracked3Outer,
                                           ModelFile smallBrickCracked3, ModelFile smallBrickCracked3Inner, ModelFile smallBrickCracked3Outer,
                                           ModelFile normalCracked3, ModelFile normalCracked3Inner, ModelFile normalCracked3Outer,
                                           ModelFile largeBrickCracked4, ModelFile largeBrickCracked4Inner, ModelFile largeBrickCracked4Outer,
                                           ModelFile smallBrickCracked4, ModelFile smallBrickCracked4Inner, ModelFile smallBrickCracked4Outer,
                                           ModelFile normalCracked4, ModelFile normalCracked4Inner, ModelFile normalCracked4Outer) {

        getVariantBuilder(block).forAllStatesExcept(state -> {
            WarenaiBlockPatternEnum patternEnum = state.getValue(WarenaiBlockStairs.BLOCK_PATTERN);
            WarenaiBlockConditionEnum conditionEnum = state.getValue(WarenaiBlockStairs.BLOCK_CONDITION);
            Direction directionEnum = state.getValue(WarenaiBlockStairs.FACING);
            Half halfEnum = state.getValue(WarenaiBlockStairs.HALF);
            StairsShape shapeEnum = state.getValue(WarenaiBlockStairs.SHAPE);

            int yRot = (int) directionEnum.getClockWise().toYRot(); // Stairs model is rotated 90 degrees clockwise for some reason
            if (shapeEnum == StairsShape.INNER_LEFT || shapeEnum == StairsShape.OUTER_LEFT) {
                yRot += 270; // Left facing stairs are rotated 90 degrees clockwise
            }
            if (shapeEnum != StairsShape.STRAIGHT && halfEnum == Half.TOP) {
                yRot += 90; // Top stairs are rotated 90 degrees clockwise
            }
            yRot %= 360;
            boolean uvlock = yRot != 0 || halfEnum == Half.TOP; // Don't set uvlock for states that have no rotation


            return ConfiguredModel.builder()
                    .modelFile(warenaiBlockStairStateTest(
                            patternEnum, conditionEnum, shapeEnum,
                            largeBrickPolished, largeBrickPolishedInner, largeBrickPolishedOuter,
                            smallBrickPolished, smallBrickPolishedInner, smallBrickPolishedOuter,
                            normalPolished, normalPolishedInner, normalPolishedOuter,
                            largeBrick, largeBrickInner, largeBrickOuter,
                            smallBrick, smallBrickInner, smallBrickOuter,
                            normal, normalInner, normalOuter,
                            largeBrickScuffed, largeBrickScuffedInner, largeBrickScuffedOuter,
                            smallBrickScuffed, smallBrickScuffedInner, smallBrickScuffedOuter,
                            normalScuffed, normalScuffedInner, normalScuffedOuter,
                            largeBrickCracked1, largeBrickCracked1Inner, largeBrickCracked1Outer,
                            smallBrickCracked1, smallBrickCracked1Inner, smallBrickCracked1Outer,
                            normalCracked1, normalCracked1Inner, normalCracked1Outer,
                            largeBrickCracked2, largeBrickCracked2Inner, largeBrickCracked2Outer,
                            smallBrickCracked2, smallBrickCracked2Inner, smallBrickCracked2Outer,
                            normalCracked2, normalCracked2Inner, normalCracked2Outer,
                            largeBrickCracked3, largeBrickCracked3Inner, largeBrickCracked3Outer,
                            smallBrickCracked3, smallBrickCracked3Inner, smallBrickCracked3Outer,
                            normalCracked3, normalCracked3Inner, normalCracked3Outer,
                            largeBrickCracked4, largeBrickCracked4Inner, largeBrickCracked4Outer,
                            smallBrickCracked4, smallBrickCracked4Inner, smallBrickCracked4Outer,
                            normalCracked4, normalCracked4Inner, normalCracked4Outer
                    ))
                    .rotationX(halfEnum == Half.BOTTOM ? 0 : 180)
                    .rotationY(yRot)
                    .uvLock(uvlock)
                    .build();
        });
    }


    private ModelFile warenaiBlockStairStateTest(WarenaiBlockPatternEnum patternEnumIn, WarenaiBlockConditionEnum conditionEnumIn, StairsShape shapeEnumIn,
                                            ModelFile largeBrickPolished, ModelFile largeBrickPolishedInner, ModelFile largeBrickPolishedOuter,
                                            ModelFile smallBrickPolished, ModelFile smallBrickPolishedInner, ModelFile smallBrickPolishedOuter,
                                            ModelFile normalPolished, ModelFile normalPolishedInner, ModelFile normalPolishedOuter,
                                            ModelFile largeBrick, ModelFile largeBrickInner, ModelFile largeBrickOuter,
                                            ModelFile smallBrick, ModelFile smallBrickInner, ModelFile smallBrickOuter,
                                            ModelFile normal, ModelFile normalInner, ModelFile normalOuter,
                                            ModelFile largeBrickScuffed, ModelFile largeBrickScuffedInner, ModelFile largeBrickScuffedOuter,
                                            ModelFile smallBrickScuffed, ModelFile smallBrickScuffedInner, ModelFile smallBrickScuffedOuter,
                                            ModelFile normalScuffed, ModelFile normalScuffedInner, ModelFile normalScuffedOuter,
                                            ModelFile largeBrickCracked1, ModelFile largeBrickCracked1Inner, ModelFile largeBrickCracked1Outer,
                                            ModelFile smallBrickCracked1, ModelFile smallBrickCracked1Inner, ModelFile smallBrickCracked1Outer,
                                            ModelFile normalCracked1, ModelFile normalCracked1Inner, ModelFile normalCracked1Outer,
                                            ModelFile largeBrickCracked2, ModelFile largeBrickCracked2Inner, ModelFile largeBrickCracked2Outer,
                                            ModelFile smallBrickCracked2, ModelFile smallBrickCracked2Inner, ModelFile smallBrickCracked2Outer,
                                            ModelFile normalCracked2, ModelFile normalCracked2Inner, ModelFile normalCracked2Outer,
                                            ModelFile largeBrickCracked3, ModelFile largeBrickCracked3Inner, ModelFile largeBrickCracked3Outer,
                                            ModelFile smallBrickCracked3, ModelFile smallBrickCracked3Inner, ModelFile smallBrickCracked3Outer,
                                            ModelFile normalCracked3, ModelFile normalCracked3Inner, ModelFile normalCracked3Outer,
                                            ModelFile largeBrickCracked4, ModelFile largeBrickCracked4Inner, ModelFile largeBrickCracked4Outer,
                                            ModelFile smallBrickCracked4, ModelFile smallBrickCracked4Inner, ModelFile smallBrickCracked4Outer,
                                            ModelFile normalCracked4, ModelFile normalCracked4Inner, ModelFile normalCracked4Outer) {

        if (shapeEnumIn == StairsShape.OUTER_LEFT || shapeEnumIn == StairsShape.OUTER_RIGHT) {
            if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)            { return normalPolishedOuter; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)         { return normalOuter; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)        { return normalScuffedOuter; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)       { return normalCracked1Outer; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)       { return normalCracked2Outer; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)       { return normalCracked3Outer; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)       { return normalCracked4Outer; }

            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return smallBrickPolishedOuter; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return smallBrickOuter; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return smallBrickScuffedOuter; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return smallBrickCracked1Outer; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return smallBrickCracked2Outer; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return smallBrickCracked3Outer; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)  { return smallBrickCracked4Outer; }

            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return largeBrickPolishedOuter; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return largeBrickOuter; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return largeBrickScuffedOuter; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return largeBrickCracked1Outer; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return largeBrickCracked2Outer; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return largeBrickCracked3Outer; }
            else {
                return largeBrickCracked4Outer;
            }

        } else if (shapeEnumIn == StairsShape.INNER_LEFT || shapeEnumIn == StairsShape.INNER_RIGHT) {
            if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)            { return normalPolishedInner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)         { return normalInner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)        { return normalScuffedInner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)       { return normalCracked1Inner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)       { return normalCracked2Inner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)       { return normalCracked3Inner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)       { return normalCracked4Inner; }

            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return smallBrickPolishedInner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return smallBrickInner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return smallBrickScuffedInner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return smallBrickCracked1Inner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return smallBrickCracked2Inner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return smallBrickCracked3Inner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)  { return smallBrickCracked4Inner; }

            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return largeBrickPolishedInner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return largeBrickInner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return largeBrickScuffedInner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return largeBrickCracked1Inner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return largeBrickCracked2Inner; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return largeBrickCracked3Inner; }
            else {
                return largeBrickCracked4Inner;
            }

        } else {
            if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)            { return normalPolished; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)         { return normal; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)        { return normalScuffed; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)       { return normalCracked1; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)       { return normalCracked2; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)       { return normalCracked3; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)       { return normalCracked4; }

            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return smallBrickPolished; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return smallBrick; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return smallBrickScuffed; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return smallBrickCracked1; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return smallBrickCracked2; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return smallBrickCracked3; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)  { return smallBrickCracked4; }

            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return largeBrickPolished; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return largeBrick; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return largeBrickScuffed; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return largeBrickCracked1; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return largeBrickCracked2; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return largeBrickCracked3; }
            else {
                return largeBrickCracked4;
            }
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    private void warenaiBlockSlab(WarenaiSlab block) {
        String path = block.getRegistryName().getPath();
        String loc = ModelProvider.BLOCK_FOLDER + "/" + path.replace("_slab", "");

        warenaiBlockSlabInternal(block,
                models().singleTexture(path + "_large_bricks_polished", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_polished")),
                models().singleTexture(path + "_large_bricks_polished_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_polished")),
                models().singleTexture(path + "_large_bricks_polished_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_polished")),

                models().singleTexture(path + "_small_bricks_polished", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_polished")),
                models().singleTexture(path + "_small_bricks_polished_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_polished")),
                models().singleTexture(path + "_small_bricks_polished_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_polished")),

                models().singleTexture(path + "_polished", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_block_polished")),
                models().singleTexture(path + "_polished_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_block_polished")),
                models().singleTexture(path + "_polished_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block_polished")),

                models().singleTexture(path + "_large_bricks", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks")),
                models().singleTexture(path + "_large_bricks_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks")),
                models().singleTexture(path + "_large_bricks_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks")),

                models().singleTexture(path + "_small_bricks", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks")),
                models().singleTexture(path + "_small_bricks_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks")),
                models().singleTexture(path + "_small_bricks_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks")),

                models().singleTexture(path + "", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_block")),
                models().singleTexture(path + "_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_block")),
                models().singleTexture(path + "_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block")),

                models().singleTexture(path + "_large_bricks_scuffed", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_scuffed")),
                models().singleTexture(path + "_large_bricks_scuffed_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_scuffed")),
                models().singleTexture(path + "_large_bricks_scuffed_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_scuffed")),

                models().singleTexture(path + "_small_bricks_scuffed", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_scuffed")),
                models().singleTexture(path + "_small_bricks_scuffed_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_scuffed")),
                models().singleTexture(path + "_small_bricks_scuffed_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_scuffed")),

                models().singleTexture(path + "_scuffed", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_block_scuffed")),
                models().singleTexture(path + "_scuffed_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_block_scuffed")),
                models().singleTexture(path + "_scuffed_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block_scuffed")),

                models().singleTexture(path + "_large_bricks_cracked1", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked1")),
                models().singleTexture(path + "_large_bricks_cracked1_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked1")),
                models().singleTexture(path + "_large_bricks_cracked1_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked1")),

                models().singleTexture(path + "_small_bricks_cracked1", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked1")),
                models().singleTexture(path + "_small_bricks_cracked1_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked1")),
                models().singleTexture(path + "_small_bricks_cracked1_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked1")),

                models().singleTexture(path + "_cracked1", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked1")),
                models().singleTexture(path + "_cracked1_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked1")),
                models().singleTexture(path + "_cracked1_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked1")),

                models().singleTexture(path + "_large_bricks_cracked2", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked2")),
                models().singleTexture(path + "_large_bricks_cracked2_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked2")),
                models().singleTexture(path + "_large_bricks_cracked2_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked2")),

                models().singleTexture(path + "_small_bricks_cracked2", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked2")),
                models().singleTexture(path + "_small_bricks_cracked2_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked2")),
                models().singleTexture(path + "_small_bricks_cracked2_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked2")),

                models().singleTexture(path + "_cracked2", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked2")),
                models().singleTexture(path + "_cracked2_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked2")),
                models().singleTexture(path + "_cracked2_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked2")),

                models().singleTexture(path + "_large_bricks_cracked3", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked3")),
                models().singleTexture(path + "_large_bricks_cracked3_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked3")),
                models().singleTexture(path + "_large_bricks_cracked3_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked3")),

                models().singleTexture(path + "_small_bricks_cracked3", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked3")),
                models().singleTexture(path + "_small_bricks_cracked3_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked3")),
                models().singleTexture(path + "_small_bricks_cracked3_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked3")),

                models().singleTexture(path + "_cracked3", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked3")),
                models().singleTexture(path + "_cracked3_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked3")),
                models().singleTexture(path + "_cracked3_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked3")),

                models().singleTexture(path + "_large_bricks_cracked4", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked4")),
                models().singleTexture(path + "_large_bricks_cracked4_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked4")),
                models().singleTexture(path + "_large_bricks_cracked4_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_large_bricks_cracked4")),

                models().singleTexture(path + "_small_bricks_cracked4", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked4")),
                models().singleTexture(path + "_small_bricks_cracked4_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked4")),
                models().singleTexture(path + "_small_bricks_cracked4_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_small_bricks_cracked4")),

                models().singleTexture(path + "_cracked4", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked4")),
                models().singleTexture(path + "_cracked4_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked4")),
                models().singleTexture(path + "_cracked4_double", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/" + "warenai_block_cracked4"))
        );
    }

    private void warenaiBlockSlabInternal(WarenaiSlab block,
                                          ModelFile largeBrickPolishedBottom, ModelFile largeBrickPolishedTop, ModelFile largeBrickPolishedDouble,
                                          ModelFile smallBrickPolishedBottom, ModelFile smallBrickPolishedTop, ModelFile smallBrickPolishedDouble,
                                          ModelFile normalPolishedBottom, ModelFile normalPolishedTop, ModelFile normalPolishedDouble,
                                          ModelFile largeBrickBottom, ModelFile largeBrickTop, ModelFile largeBrickDouble,
                                          ModelFile smallBrickBottom, ModelFile smallBrickTop, ModelFile smallBrickDouble,
                                          ModelFile normalBottom, ModelFile normalTop, ModelFile normalDouble,
                                          ModelFile largeBrickScuffedBottom, ModelFile largeBrickScuffedTop, ModelFile largeBrickScuffedDouble,
                                          ModelFile smallBrickScuffedBottom, ModelFile smallBrickScuffedTop, ModelFile smallBrickScuffedDouble,
                                          ModelFile normalScuffedBottom, ModelFile normalScuffedTop, ModelFile normalScuffedDouble,
                                          ModelFile largeBrickCracked1Bottom, ModelFile largeBrickCracked1Top, ModelFile largeBrickCracked1Double,
                                          ModelFile smallBrickCracked1Bottom, ModelFile smallBrickCracked1Top, ModelFile smallBrickCracked1Double,
                                          ModelFile normalCracked1Bottom, ModelFile normalCracked1Top, ModelFile normalCracked1Double,
                                          ModelFile largeBrickCracked2Bottom, ModelFile largeBrickCracked2Top, ModelFile largeBrickCracked2Double,
                                          ModelFile smallBrickCracked2Bottom, ModelFile smallBrickCracked2Top, ModelFile smallBrickCracked2Double,
                                          ModelFile normalCracked2Bottom, ModelFile normalCracked2Top, ModelFile normalCracked2Double,
                                          ModelFile largeBrickCracked3Bottom, ModelFile largeBrickCracked3Top, ModelFile largeBrickCracked3Double,
                                          ModelFile smallBrickCracked3Bottom, ModelFile smallBrickCracked3Top, ModelFile smallBrickCracked3Double,
                                          ModelFile normalCracked3Bottom, ModelFile normalCracked3Top, ModelFile normalCracked3Double,
                                          ModelFile largeBrickCracked4Bottom, ModelFile largeBrickCracked4Top, ModelFile largeBrickCracked4Double,
                                          ModelFile smallBrickCracked4Bottom, ModelFile smallBrickCracked4Top, ModelFile smallBrickCracked4Double,
                                          ModelFile normalCracked4Bottom, ModelFile normalCracked4Top, ModelFile normalCracked4Double) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            WarenaiBlockPatternEnum patternEnum = state.getValue(WarenaiSlab.BLOCK_PATTERN);
            WarenaiBlockConditionEnum conditionEnum = state.getValue(WarenaiSlab.BLOCK_CONDITION);
            SlabType slabTypeEnum = state.getValue(WarenaiSlab.TYPE);

            return ConfiguredModel.builder()
                    .modelFile(warenaiBlockSlabStateTest(
                            patternEnum, conditionEnum, slabTypeEnum,
                            largeBrickPolishedBottom, largeBrickPolishedTop, largeBrickPolishedDouble,
                            smallBrickPolishedBottom, smallBrickPolishedTop, smallBrickPolishedDouble,
                            normalPolishedBottom, normalPolishedTop, normalPolishedDouble,
                            largeBrickBottom, largeBrickTop, largeBrickDouble,
                            smallBrickBottom, smallBrickTop, smallBrickDouble,
                            normalBottom, normalTop, normalDouble,
                            largeBrickScuffedBottom, largeBrickScuffedTop, largeBrickScuffedDouble,
                            smallBrickScuffedBottom, smallBrickScuffedTop, smallBrickScuffedDouble,
                            normalScuffedBottom, normalScuffedTop, normalScuffedDouble,
                            largeBrickCracked1Bottom, largeBrickCracked1Top, largeBrickCracked1Double,
                            smallBrickCracked1Bottom, smallBrickCracked1Top, smallBrickCracked1Double,
                            normalCracked1Bottom, normalCracked1Top, normalCracked1Double,
                            largeBrickCracked2Bottom, largeBrickCracked2Top, largeBrickCracked2Double,
                            smallBrickCracked2Bottom, smallBrickCracked2Top, smallBrickCracked2Double,
                            normalCracked2Bottom, normalCracked2Top, normalCracked2Double,
                            largeBrickCracked3Bottom, largeBrickCracked3Top, largeBrickCracked3Double,
                            smallBrickCracked3Bottom, smallBrickCracked3Top, smallBrickCracked3Double,
                            normalCracked3Bottom, normalCracked3Top, normalCracked3Double,
                            largeBrickCracked4Bottom, largeBrickCracked4Top, largeBrickCracked4Double,
                            smallBrickCracked4Bottom, smallBrickCracked4Top, smallBrickCracked4Double,
                            normalCracked4Bottom, normalCracked4Top, normalCracked4Double
                    )).build();
        });
    }

    private ModelFile warenaiBlockSlabStateTest(WarenaiBlockPatternEnum patternEnumIn, WarenaiBlockConditionEnum conditionEnumIn, SlabType slabTypeEnumIn,
                                                ModelFile largeBrickPolishedBottom, ModelFile largeBrickPolishedTop, ModelFile largeBrickPolishedDouble,
                                                ModelFile smallBrickPolishedBottom, ModelFile smallBrickPolishedTop, ModelFile smallBrickPolishedDouble,
                                                ModelFile normalPolishedBottom, ModelFile normalPolishedTop, ModelFile normalPolishedDouble,
                                                ModelFile largeBrickBottom, ModelFile largeBrickTop, ModelFile largeBrickDouble,
                                                ModelFile smallBrickBottom, ModelFile smallBrickTop, ModelFile smallBrickDouble,
                                                ModelFile normalBottom, ModelFile normalTop, ModelFile normalDouble,
                                                ModelFile largeBrickScuffedBottom, ModelFile largeBrickScuffedTop, ModelFile largeBrickScuffedDouble,
                                                ModelFile smallBrickScuffedBottom, ModelFile smallBrickScuffedTop, ModelFile smallBrickScuffedDouble,
                                                ModelFile normalScuffedBottom, ModelFile normalScuffedTop, ModelFile normalScuffedDouble,
                                                ModelFile largeBrickCracked1Bottom, ModelFile largeBrickCracked1Top, ModelFile largeBrickCracked1Double,
                                                ModelFile smallBrickCracked1Bottom, ModelFile smallBrickCracked1Top, ModelFile smallBrickCracked1Double,
                                                ModelFile normalCracked1Bottom, ModelFile normalCracked1Top, ModelFile normalCracked1Double,
                                                ModelFile largeBrickCracked2Bottom, ModelFile largeBrickCracked2Top, ModelFile largeBrickCracked2Double,
                                                ModelFile smallBrickCracked2Bottom, ModelFile smallBrickCracked2Top, ModelFile smallBrickCracked2Double,
                                                ModelFile normalCracked2Bottom, ModelFile normalCracked2Top, ModelFile normalCracked2Double,
                                                ModelFile largeBrickCracked3Bottom, ModelFile largeBrickCracked3Top, ModelFile largeBrickCracked3Double,
                                                ModelFile smallBrickCracked3Bottom, ModelFile smallBrickCracked3Top, ModelFile smallBrickCracked3Double,
                                                ModelFile normalCracked3Bottom, ModelFile normalCracked3Top, ModelFile normalCracked3Double,
                                                ModelFile largeBrickCracked4Bottom, ModelFile largeBrickCracked4Top, ModelFile largeBrickCracked4Double,
                                                ModelFile smallBrickCracked4Bottom, ModelFile smallBrickCracked4Top, ModelFile smallBrickCracked4Double,
                                                ModelFile normalCracked4Bottom, ModelFile normalCracked4Top, ModelFile normalCracked4Double) {

        if (slabTypeEnumIn == SlabType.BOTTOM) {
            if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)            { return normalPolishedBottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)         { return normalBottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)        { return normalScuffedBottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)       { return normalCracked1Bottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)       { return normalCracked2Bottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)       { return normalCracked3Bottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)       { return normalCracked4Bottom; }

            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return smallBrickPolishedBottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return smallBrickBottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return smallBrickScuffedBottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return smallBrickCracked1Bottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return smallBrickCracked2Bottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return smallBrickCracked3Bottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)  { return smallBrickCracked4Bottom; }

            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return largeBrickPolishedBottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return largeBrickBottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return largeBrickScuffedBottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return largeBrickCracked1Bottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return largeBrickCracked2Bottom; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return largeBrickCracked3Bottom; }
            else {
                return largeBrickCracked4Bottom;
            }

        } else if (slabTypeEnumIn == SlabType.TOP) {
            if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)            { return normalPolishedTop; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)         { return normalTop; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)        { return normalScuffedTop; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)       { return normalCracked1Top; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)       { return normalCracked2Top; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)       { return normalCracked3Top; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)       { return normalCracked4Top; }

            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return smallBrickPolishedTop; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return smallBrickTop; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return smallBrickScuffedTop; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return smallBrickCracked1Top; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return smallBrickCracked2Top; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return smallBrickCracked3Top; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)  { return smallBrickCracked4Top; }

            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return largeBrickPolishedTop; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return largeBrickTop; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return largeBrickScuffedTop; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return largeBrickCracked1Top; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return largeBrickCracked2Top; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return largeBrickCracked3Top; }
            else {
                return largeBrickCracked4Top;
            }

        } else {
            if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)            { return normalPolishedDouble; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)         { return normalDouble; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)        { return normalScuffedDouble; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)       { return normalCracked1Double; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)       { return normalCracked2Double; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)       { return normalCracked3Double; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMOOTH && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)       { return normalCracked4Double; }

            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return smallBrickPolishedDouble; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return smallBrickDouble; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return smallBrickScuffedDouble; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return smallBrickCracked1Double; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return smallBrickCracked2Double; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return smallBrickCracked3Double; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.SMALL_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED4)  { return smallBrickCracked4Double; }

            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.POLISHED)  { return largeBrickPolishedDouble; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.NORMAL)    { return largeBrickDouble; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.SCUFFED)   { return largeBrickScuffedDouble; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED1)  { return largeBrickCracked1Double; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED2)  { return largeBrickCracked2Double; }
            else if (patternEnumIn == WarenaiBlockPatternEnum.LARGE_BRICK && conditionEnumIn == WarenaiBlockConditionEnum.CRACKED3)  { return largeBrickCracked3Double; }
            else {
                return largeBrickCracked4Double;
            }
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    private void warenaiReinforcedGlassBlock(WarenaiGlassBlock block) {
        String path = block.getRegistryName().getPath();
        String loc = ModelProvider.BLOCK_FOLDER + "/" + path;

        simpleBlock(block, models().singleTexture(path, modLoc(ModelProvider.BLOCK_FOLDER + "/templates/blocks"), "all", modLoc("block/warenai_blocks/warenai_glass")));
    }


    private void warenaiReinforcedGlassStairs(WarenaiGlassStairsBlock block) {
        String path = block.getRegistryName().getPath();
        String loc = ModelProvider.BLOCK_FOLDER + "/" + path.replace("_stairs", "");

        ModelFile stairs = models().withExistingParent(path, modLoc(ModelProvider.BLOCK_FOLDER + "/templates/stairs")).texture("all", "block/warenai_blocks/warenai_glass");
        ModelFile stairsInner = models().withExistingParent(path + "_inner", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/inner_stairs")).texture("all", "block/warenai_blocks/warenai_glass");
        ModelFile stairsOuter = models().withExistingParent(path + "_outer", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/outer_stairs")).texture("all", "block/warenai_blocks/warenai_glass");

        stairsBlock((StairBlock) block, stairs, stairsInner, stairsOuter);
        ConfiguredModel.builder().modelFile(stairs).build();    }


    private void warenaiReinforcedGlassSlab(WarenaiGlassSlabBlock block) {
        String path = block.getRegistryName().getPath();
        String loc = ModelProvider.BLOCK_FOLDER + "/" + path.replace("_slab", "");

        ModelFile slabBottom = models().withExistingParent(path, modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab")).texture("all", "block/warenai_blocks/warenai_glass");
        ModelFile slabTop = models().withExistingParent(path + "_top", modLoc(ModelProvider.BLOCK_FOLDER + "/templates/slab_top")).texture("all", "block/warenai_blocks/warenai_glass");
        ModelFile slabDouble = models().getExistingFile(modLoc(loc));

        slabBlock((SlabBlock) block, slabBottom, slabTop, slabDouble);
        ConfiguredModel.builder().modelFile(slabBottom).build();
    }



    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /*
    paneBlock(BlockRegistry.RGB_GLASS_PANE.get(),
                  models().withExistingParent(BlockRegistry.RGB_GLASS_PANE.getId().toString() + "_post",
                                              modLoc(ModelProvider.BLOCK_FOLDER + "/template_glass_pane_post"))
                          .texture("pane", modLoc(ModelProvider.BLOCK_FOLDER + "/glass"))
                          .texture("edge", modLoc(ModelProvider.BLOCK_FOLDER + "/glass_pane_top")),
                  models().withExistingParent(BlockRegistry.RGB_GLASS_PANE.getId().toString() + "_side",
                                              modLoc(ModelProvider.BLOCK_FOLDER + "/template_glass_pane_side"))
                          .texture("pane", modLoc(ModelProvider.BLOCK_FOLDER + "/glass"))
                          .texture("edge", modLoc(ModelProvider.BLOCK_FOLDER + "/glass_pane_top")),
                  models().withExistingParent(BlockRegistry.RGB_GLASS_PANE.getId().toString() + "_side_alt",
                                              modLoc(ModelProvider.BLOCK_FOLDER + "/template_glass_pane_side_alt"))
                          .texture("pane", modLoc(ModelProvider.BLOCK_FOLDER + "/glass"))
                          .texture("edge", modLoc(ModelProvider.BLOCK_FOLDER + "/glass_pane_top")),
                  models().withExistingParent(BlockRegistry.RGB_GLASS_PANE.getId().toString() + "_noside",
                                              modLoc(ModelProvider.BLOCK_FOLDER + "/template_glass_pane_noside"))
                          .texture("pane", modLoc(ModelProvider.BLOCK_FOLDER + "/glass")),
                  models().withExistingParent(BlockRegistry.RGB_GLASS_PANE.getId().toString() + "_noside_alt",
                                              modLoc(ModelProvider.BLOCK_FOLDER + "/template_glass_pane_noside_alt"))
                          .texture("pane", modLoc(ModelProvider.BLOCK_FOLDER + "/glass")));
     */

    private void warenaiReinforcedGlassPane() {}



}
