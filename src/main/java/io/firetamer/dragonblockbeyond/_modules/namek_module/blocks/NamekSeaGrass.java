package io.firetamer.dragonblockbeyond._modules.namek_module.blocks;

import io.firetamer.dragonblockbeyond._modules.namek_module.NamekModule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class NamekSeaGrass extends SeagrassBlock {

    public NamekSeaGrass(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return fluidstate.getType() == NamekModule.NAMEK_FLUID_FLOWING.get() || fluidstate.getType() == NamekModule.NAMEK_FLUID_SOURCE.get() &&
                fluidstate.getAmount() == 8 ? super.getStateForPlacement(context) : null;
    }

    @Override
    public FluidState getFluidState(BlockState p_154537_) {
        return NamekModule.NAMEK_FLUID_SOURCE.get().getSource(false);
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, Random random, BlockPos blockPos, BlockState blockState) {
        BlockState blockstateBottom = Blocks.TALL_SEAGRASS.defaultBlockState();
        BlockState blockstateTop = blockstateBottom.setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
        BlockPos blockpos2 = blockPos.above();

        if (serverLevel.getBlockState(blockpos2).is(NamekModule.NAMEK_FLUID_BLOCK.get())) {
            serverLevel.setBlock(blockPos, blockstateBottom, 2);
            serverLevel.setBlock(blockpos2, blockstateTop, 2);
        }
    }
}
