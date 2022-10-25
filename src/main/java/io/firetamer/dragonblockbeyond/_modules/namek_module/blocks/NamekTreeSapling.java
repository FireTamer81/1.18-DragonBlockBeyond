package io.firetamer.dragonblockbeyond._modules.namek_module.blocks;

import io.firetamer.dragonblockbeyond._modules.namek_module.NamekModule;
import io.firetamer.dragonblockbeyond.init.CommonObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class NamekTreeSapling extends SaplingBlock {
    public NamekTreeSapling(AbstractTreeGrower treeGrower, Properties properties) {
        super(treeGrower, properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.is(NamekModule.NAMEK_GRASS_BLOCK.get())
                || blockState.is(CommonObjects.CLAY_DIRT.get());
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.get("namek");
    }


}
