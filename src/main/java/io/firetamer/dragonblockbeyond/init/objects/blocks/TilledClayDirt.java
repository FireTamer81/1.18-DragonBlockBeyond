package io.firetamer.dragonblockbeyond.init.objects.blocks;

import io.firetamer.dragonblockbeyond.init.CommonObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class TilledClayDirt extends FarmBlock {
	public TilledClayDirt(Properties properties) {
		super(properties);
	}

	private boolean isUnderAjisaBush(LevelReader reader, BlockPos pos) {
		BlockState plant = reader.getBlockState(pos.above());
		BlockState state = reader.getBlockState(pos);
		
		return plant.getBlock() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(reader, pos, Direction.UP, (net.minecraftforge.common.IPlantable)plant.getBlock());
	}
	
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		BlockState blockstate = reader.getBlockState(pos.above());
		return !blockstate.getMaterial().isSolid()
				|| blockstate.getBlock() instanceof FenceGateBlock
				|| blockstate.getBlock() instanceof MovingPistonBlock
				/*|| blockstate.getBlock() instanceof AjisaBush*/;
	}
	
	public static void turnToClayDirt(BlockState state, Level level, BlockPos pos) {
		level.setBlockAndUpdate(pos, pushEntitiesUp(state, CommonObjects.CLAY_DIRT.get().defaultBlockState(), level, pos));
	}
	

	
}
