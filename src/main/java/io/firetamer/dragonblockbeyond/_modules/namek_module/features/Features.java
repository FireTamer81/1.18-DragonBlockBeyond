package io.firetamer.dragonblockbeyond._modules.namek_module.features;

import io.firetamer.dragonblockbeyond._modules.namek_module.NamekModule;
import io.firetamer.dragonblockbeyond.init.CommonObjects;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class Features {

    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> NAMEK_TREE = FeatureUtils.register("namek_tree", Feature.TREE, createNamekTree().build());



    private static TreeConfiguration.TreeConfigurationBuilder createNamekTree() {
        return createStraightBlobTree(NamekModule.NAMEK_LOG.get(), NamekModule.NAMEK_LEAVES.get(), CommonObjects.CLAY_DIRT.get(),
                12, 6, 0, 2, 1, 0, 0);
    }





    private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block block1, Block block2, Block dirtBlock, int trunkMaxHeight, int trunkMinHeight, int p_195151_, int leafRadius, int leafBlobDifferenceLimit, int leafBlobLowerSize, int leafBlockUpperSize) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(block1),
                new StraightTrunkPlacer(trunkMaxHeight, trunkMinHeight, p_195151_), BlockStateProvider.simple(block2),
                new BlobFoliagePlacer(ConstantInt.of(leafRadius), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(leafBlobDifferenceLimit, leafBlobLowerSize, leafBlockUpperSize))
                .dirt(BlockStateProvider.simple(dirtBlock));
    }
}
