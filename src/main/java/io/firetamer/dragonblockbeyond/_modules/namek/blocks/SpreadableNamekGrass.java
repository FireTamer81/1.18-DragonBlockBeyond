package io.firetamer.dragonblockbeyond._modules.namek.blocks;

import io.firetamer.dragonblockbeyond.init.CommonObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;

import java.util.Random;

public class SpreadableNamekGrass extends Block implements BonemealableBlock {

    public SpreadableNamekGrass(Properties p_49795_) {
        super(p_49795_);
    }

    private static boolean canBeNamekGrass(BlockState state, LevelReader reader, BlockPos blockPos) {
        BlockPos abovePos = blockPos.above();
        BlockState aboveState = reader.getBlockState(abovePos);

        if (aboveState.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int light = LayerLightEngine.getLightBlockInto(reader, state, blockPos, aboveState, abovePos, Direction.UP, aboveState.getLightBlock(reader, abovePos));
            return light < reader.getMaxLightLevel();
        }
    }

    private static boolean canPropagate(BlockState state, LevelReader reader, BlockPos pos) {
        BlockPos abovePos = pos.above();

        //TODO make a fluid tag class, add NamekWater as an entry, add add it to this method like water is
        return canBeNamekGrass(state, reader, pos) && !reader.getFluidState(abovePos).is(FluidTags.WATER);
    }

    public void randomTick(BlockState state, ServerLevel server, BlockPos pos,  Random rand) {
        if (!canBeNamekGrass(state, server, pos)) {
            if (!server.isAreaLoaded(pos, 1)) return; //No updates when area is not loaded

            server.setBlockAndUpdate(pos, CommonObjects.CLAY_DIRT.get().defaultBlockState());
        } else {
            if (!server.isAreaLoaded(pos, 3)) return;

            if (server.getMaxLocalRawBrightness(pos.above()) >= 9) {
                BlockState blockState = this.defaultBlockState();

                //I think is what cycles through the 4 adjacent blocks and randomly picks which should be turned to grass
                for(int i = 0; i < 4; ++i) {
                    BlockPos adjacentPos = pos.offset(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                    if (server.getBlockState(adjacentPos).is(CommonObjects.CLAY_DIRT.get()) && canPropagate(blockState, server, adjacentPos)) {
                        server.setBlockAndUpdate(adjacentPos, this.defaultBlockState());
                    }
                }
            }
        }
    }



    @Override
    public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos pos, BlockState state, boolean p_50900_) {
        return blockGetter.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, Random p_50902_, BlockPos p_50903_, BlockState p_50904_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_50893_, Random p_50894_, BlockPos p_50895_, BlockState p_50896_) {

    }
}
