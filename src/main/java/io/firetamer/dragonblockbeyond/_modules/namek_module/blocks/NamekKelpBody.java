package io.firetamer.dragonblockbeyond._modules.namek_module.blocks;

import io.firetamer.dragonblockbeyond._modules.namek_module.NamekModule;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.KelpPlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NamekKelpBody extends KelpPlantBlock {

    public NamekKelpBody(Properties properties) {
        super(properties);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return NamekModule.NAMEK_FLUID_SOURCE.get().getSource(false);
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) NamekModule.NAMEK_KELP_HEAD.get();
    }

}
