package io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks;

import io.firetamer.dragonblockbeyond._modules.strongblock_module.util.WarenaiBlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class WarenaiGlassStairsBlock extends WarenaiBlockStairs {

    public WarenaiGlassStairsBlock(Supplier<BlockState> state) {
        super(state, Properties.copy(Blocks.GLASS));
    }

    @Override
    public float[] getBeaconColorMultiplier(BlockState state, LevelReader world, BlockPos pos, BlockPos beaconPos) {
        return WarenaiBlockUtils.getBeaconColorMultiplier(state, world, pos, beaconPos);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return WarenaiBlockUtils.stairSkipRendering(state, adjacentBlockState, side);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }
}
