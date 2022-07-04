package io.firetamer.dragonblockbeyond._modules.namek_module.features;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class NamekTreeGrower extends AbstractTreeGrower {
    public NamekTreeGrower() {}

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random p_204307_, boolean p_204308_) {
        return Features.NAMEK_TREE;

        /*
        if (p_204329_.nextInt(10) == 0) {
            return p_204330_ ? TreeFeatures.FANCY_OAK_BEES_005 : TreeFeatures.FANCY_OAK;
        } else {
            return p_204330_ ? TreeFeatures.OAK_BEES_005 : TreeFeatures.OAK;
        }
         */
    }
}
