package io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks;

import io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.properties.WarenaiBlockConditionEnum;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.properties.WarenaiBlockPatternEnum;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.properties.WarenaiBlockStateProperties;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.util.WarenaiBlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.HitResult;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class WarenaiBlockWall extends WallBlock implements EntityBlock, SimpleWaterloggedBlock {
    public static final EnumProperty<WarenaiBlockConditionEnum> BLOCK_CONDITION = WarenaiBlockStateProperties.BLOCK_CONDITION;
    public static final EnumProperty<WarenaiBlockPatternEnum> BLOCK_PATTERN = WarenaiBlockStateProperties.BLOCK_PATTERN;


    public WarenaiBlockWall(Properties p_57964_) {
        super(p_57964_);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BLOCK_CONDITION, WarenaiBlockConditionEnum.NORMAL)
                .setValue(BLOCK_PATTERN, WarenaiBlockPatternEnum.SMOOTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BLOCK_CONDITION, BLOCK_PATTERN, UP, NORTH_WALL, EAST_WALL, WEST_WALL, SOUTH_WALL, WATERLOGGED);
    }


    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return WarenaiBlockUtils.newBlockEntity(pos, state);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        WarenaiBlockUtils.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return WarenaiBlockUtils.getCloneItemStack(state, target, world, pos, player);
    }
}
