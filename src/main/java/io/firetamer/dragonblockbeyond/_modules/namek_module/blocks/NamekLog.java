package io.firetamer.dragonblockbeyond._modules.namek_module.blocks;

import io.firetamer.dragonblockbeyond._modules.namek_module.NamekModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

@SuppressWarnings("deprecated")
public class NamekLog extends RotatedPillarBlock {
    public NamekLog(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack, ToolAction toolAction) {

        if(stack.getItem() instanceof AxeItem) {
            if(state.is(NamekModule.NAMEK_LOG.get())) {
                return NamekModule.STRIPPED_NAMEK_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            if(state.is(NamekModule.NAMEK_WOOD.get())) {
                return NamekModule.STRIPPED_NAMEK_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }

        return super.getToolModifiedState(state, world, pos, player, stack, toolAction);
    }
}
