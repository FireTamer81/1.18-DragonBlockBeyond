package io.firetamer.dragonblockbeyond._modules.strongblock_module.util;

import io.firetamer.dragonblockbeyond._modules.strongblock_module.StrongBlockModule;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.WarenaiBlock;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.WarenaiGlassBlock;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.WarenaiGlassSlabBlock;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.WarenaiGlassStairsBlock;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.tiles.StrongBlockTile;
import io.firetamer.dragonblockbeyond.util.DBBColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.HitResult;

public final class WarenaiBlockUtils {
    public static BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return StrongBlockModule.STRONG_BLOCK_TILE.get().create(pos, state);
    }

    public static void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if (stack.hasTag() && tileEntity instanceof StrongBlockTile) {
            ((StrongBlockTile) tileEntity).setColor(stack.getTag().getInt("color"));
        }
    }

    public static ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        ItemStack stack = new ItemStack(state.getBlock().asItem());
        BlockEntity tileEntity = world.getBlockEntity(pos);

        if (tileEntity instanceof StrongBlockTile) {
            CompoundTag tag = new CompoundTag();
            tag.putInt("color", ((StrongBlockTile) tileEntity).getColor());
            stack.setTag(tag);
        }

        return stack;
    }

    public static float[] getBeaconColorMultiplier(BlockState state, LevelReader world, BlockPos pos, BlockPos beaconPos) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof StrongBlockTile) {
            return new DBBColor(((StrongBlockTile) tileEntity).getColor()).getRGBColorComponents();
        } else {
            return null;
        }
    }



    /******************************************************************************************************************/
    //Should Skip Rendering Methods
    /******************************************************************************************************************/


    public static boolean blockSkipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.getBlock() instanceof WarenaiBlock) {
            return true;
        } else if (adjacentBlockState.getBlock() instanceof WarenaiGlassSlabBlock) {
            if (adjacentBlockState.getValue(SlabBlock.TYPE) == SlabType.DOUBLE) {
                return true;
            } else if (side == Direction.UP) {
                return adjacentBlockState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM;
            } else if (side == Direction.DOWN) {
                return adjacentBlockState.getValue(SlabBlock.TYPE) == SlabType.TOP;
            } else {
                return false;
            }
        } else if (adjacentBlockState.getBlock() instanceof WarenaiGlassStairsBlock) {
            if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.OUTER_LEFT ||
                adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.OUTER_RIGHT) {
                return false;
            } else if (side == adjacentBlockState.getValue(StairBlock.FACING).getOpposite()) {
                return true;
            } else if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.INNER_LEFT) {
                return side == adjacentBlockState.getValue(StairBlock.FACING).getClockWise();
            } else if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.INNER_RIGHT) {
                return side == adjacentBlockState.getValue(StairBlock.FACING).getCounterClockWise();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }



    public static boolean slabSkipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.getBlock() instanceof WarenaiGlassBlock) {
            return true;
        } else if (adjacentBlockState.getBlock() instanceof WarenaiGlassSlabBlock) {
            return slabSkipRenderingAdjacentGlassSlab(state, adjacentBlockState, side);
        } else if (adjacentBlockState.getBlock() instanceof WarenaiGlassStairsBlock) {
            return slabSkipRenderingAdjacentGlassStairs(state, adjacentBlockState, side);
        } else {
            return false;
        }
    }

    public static boolean slabSkipRenderingAdjacentGlassSlab(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.getValue(SlabBlock.TYPE) == SlabType.DOUBLE) {
            return true;
        }

        switch (side) {
            case UP:
                return (adjacentBlockState.getValue(SlabBlock.TYPE) != SlabType.TOP && adjacentBlockState.getValue(SlabBlock.TYPE) != SlabType.DOUBLE);
            case DOWN:
                return (state.getValue(SlabBlock.TYPE) != adjacentBlockState.getValue(SlabBlock.TYPE));
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                return (state.getValue(SlabBlock.TYPE) == adjacentBlockState.getValue(SlabBlock.TYPE));
        }

        return false;
    }

    public static boolean slabSkipRenderingAdjacentGlassStairs(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (side == Direction.UP && adjacentBlockState.getValue(StairBlock.HALF) == Half.BOTTOM) {
            return true;
        }

        if (side == Direction.DOWN && adjacentBlockState.getValue(StairBlock.HALF) == Half.TOP) {
            return true;
        }

        if (adjacentBlockState.getValue(StairBlock.FACING) == side.getOpposite()) {
            return true;
        }

        if (side.get2DDataValue() != -1) {
            if (state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM &&
                adjacentBlockState.getValue(StairBlock.HALF) == Half.BOTTOM) {
                return true;
            } else if (state.getValue(SlabBlock.TYPE) == SlabType.TOP &&
                       adjacentBlockState.getValue(StairBlock.HALF) == Half.TOP) {
                return true;
            }
        }

        return false;
    }



    public static boolean stairSkipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.getBlock() instanceof WarenaiGlassBlock) {
            return true;
        } else if (adjacentBlockState.getBlock() instanceof WarenaiGlassSlabBlock) {
            return stairSkipRenderingAdjacentGlassSlab(state, adjacentBlockState, side);
        } else if (adjacentBlockState.getBlock() instanceof WarenaiGlassStairsBlock) {
            return stairSkipRenderingAdjacentGlassStairs(state, adjacentBlockState, side);
        } else {
            return false;
        }
    }

    public static boolean stairSkipRenderingAdjacentGlassSlab(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (side == Direction.UP && adjacentBlockState.getValue(SlabBlock.TYPE) != SlabType.TOP) {
            return true;
        }

        if (side == Direction.DOWN && adjacentBlockState.getValue(SlabBlock.TYPE) != SlabType.BOTTOM) {
            return true;
        }

        if (adjacentBlockState.getValue(SlabBlock.TYPE) == SlabType.DOUBLE) {
            return true;
        }

        if (side == state.getValue(StairBlock.FACING).getOpposite()) {
            if (adjacentBlockState.getValue(SlabBlock.TYPE) == SlabType.TOP &&
                state.getValue(StairBlock.HALF) == Half.TOP) {
                return true;
            } else if (adjacentBlockState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM &&
                       state.getValue(StairBlock.HALF) == Half.BOTTOM) {
                return true;
            }
        }

        if (side == state.getValue(StairBlock.FACING).getClockWise() &&
            state.getValue(StairBlock.SHAPE) == StairsShape.OUTER_LEFT) {
            if (adjacentBlockState.getValue(SlabBlock.TYPE) == SlabType.TOP &&
                state.getValue(StairBlock.HALF) == Half.TOP) {
                return true;
            } else if (adjacentBlockState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM &&
                       state.getValue(StairBlock.HALF) == Half.BOTTOM) {
                return true;
            }
        }

        if (side == state.getValue(StairBlock.FACING).getCounterClockWise() &&
            state.getValue(StairBlock.SHAPE) == StairsShape.OUTER_RIGHT) {
            if (adjacentBlockState.getValue(SlabBlock.TYPE) == SlabType.TOP &&
                state.getValue(StairBlock.HALF) == Half.TOP) {
                return true;
            } else if (adjacentBlockState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM &&
                       state.getValue(StairBlock.HALF) == Half.BOTTOM) {
                return true;
            }
        }

        return false;
    }

    public static boolean stairSkipRenderingAdjacentGlassStairs(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (side == Direction.UP) {
            if (adjacentBlockState.getValue(StairBlock.HALF) == Half.BOTTOM) {
                return true;
            } else if (state.getValue(StairBlock.HALF) != adjacentBlockState.getValue(StairBlock.HALF)) {
                if (state.getValue(StairBlock.FACING) == adjacentBlockState.getValue(StairBlock.FACING) &&
                    state.getValue(StairBlock.SHAPE) == adjacentBlockState.getValue(StairBlock.SHAPE)) {
                    return true;
                } else {
                    switch (state.getValue(StairBlock.SHAPE)) {
                        case STRAIGHT:
                            if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.INNER_LEFT &&
                                (adjacentBlockState.getValue(StairBlock.FACING) == state.getValue(StairBlock.FACING) ||
                                 adjacentBlockState.getValue(StairBlock.FACING) ==
                                 state.getValue(StairBlock.FACING).getClockWise())) {
                                return true;
                            } else if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.INNER_RIGHT &&
                                       (adjacentBlockState.getValue(StairBlock.FACING) ==
                                        state.getValue(StairBlock.FACING) ||
                                        adjacentBlockState.getValue(StairBlock.FACING) ==
                                        state.getValue(StairBlock.FACING).getCounterClockWise())) {
                                return true;
                            }
                            break;
                        case INNER_LEFT:
                            if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.INNER_RIGHT &&
                                adjacentBlockState.getValue(StairBlock.FACING) ==
                                state.getValue(StairBlock.FACING).getCounterClockWise()) {
                                return true;
                            }
                            break;
                        case INNER_RIGHT:
                            if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.INNER_LEFT &&
                                adjacentBlockState.getValue(StairBlock.FACING) ==
                                state.getValue(StairBlock.FACING).getClockWise()) {
                                return true;
                            }
                            break;
                        case OUTER_LEFT:
                            if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.OUTER_RIGHT &&
                                adjacentBlockState.getValue(StairBlock.FACING) ==
                                state.getValue(StairBlock.FACING).getCounterClockWise()) {
                                return true;
                            } else if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.STRAIGHT &&
                                       (adjacentBlockState.getValue(StairBlock.FACING) ==
                                        state.getValue(StairBlock.FACING) ||
                                        adjacentBlockState.getValue(StairBlock.FACING) ==
                                        state.getValue(StairBlock.FACING).getCounterClockWise())) {
                                return true;
                            }
                            break;
                        case OUTER_RIGHT:
                            if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.OUTER_LEFT &&
                                adjacentBlockState.getValue(StairBlock.FACING) ==
                                state.getValue(StairBlock.FACING).getClockWise()) {
                                return true;
                            } else if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.STRAIGHT &&
                                       (adjacentBlockState.getValue(StairBlock.FACING) ==
                                        state.getValue(StairBlock.FACING) ||
                                        adjacentBlockState.getValue(StairBlock.FACING) ==
                                        state.getValue(StairBlock.FACING).getClockWise())) {
                                return true;
                            }
                            break;
                    }
                }
            }
        }

        if (side == Direction.DOWN) {
            if (adjacentBlockState.getValue(StairBlock.HALF) == Half.TOP) {
                return true;
            } else {
                switch (state.getValue(StairBlock.SHAPE)) {
                    case STRAIGHT:
                        if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.INNER_LEFT &&
                            (adjacentBlockState.getValue(StairBlock.FACING) == state.getValue(StairBlock.FACING) ||
                             adjacentBlockState.getValue(StairBlock.FACING) ==
                             state.getValue(StairBlock.FACING).getClockWise())) {
                            return true;
                        } else if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.INNER_RIGHT &&
                                   (adjacentBlockState.getValue(StairBlock.FACING) ==
                                    state.getValue(StairBlock.FACING) ||
                                    adjacentBlockState.getValue(StairBlock.FACING) ==
                                    state.getValue(StairBlock.FACING).getCounterClockWise())) {
                            return true;
                        }
                        break;
                    case INNER_LEFT:
                        if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.INNER_RIGHT &&
                            adjacentBlockState.getValue(StairBlock.FACING) ==
                            state.getValue(StairBlock.FACING).getCounterClockWise()) {
                            return true;
                        }
                        break;
                    case INNER_RIGHT:
                        if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.INNER_LEFT &&
                            adjacentBlockState.getValue(StairBlock.FACING) ==
                            state.getValue(StairBlock.FACING).getClockWise()) {
                            return true;
                        }
                        break;
                    case OUTER_LEFT:
                        if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.OUTER_RIGHT &&
                            adjacentBlockState.getValue(StairBlock.FACING) ==
                            state.getValue(StairBlock.FACING).getCounterClockWise()) {
                            return true;
                        } else if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.STRAIGHT &&
                                   (adjacentBlockState.getValue(StairBlock.FACING) ==
                                    state.getValue(StairBlock.FACING) ||
                                    adjacentBlockState.getValue(StairBlock.FACING) ==
                                    state.getValue(StairBlock.FACING).getCounterClockWise())) {
                            return true;
                        }
                        break;
                    case OUTER_RIGHT:
                        if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.OUTER_LEFT &&
                            adjacentBlockState.getValue(StairBlock.FACING) ==
                            state.getValue(StairBlock.FACING).getClockWise()) {
                            return true;
                        } else if (adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.STRAIGHT &&
                                   (adjacentBlockState.getValue(StairBlock.FACING) ==
                                    state.getValue(StairBlock.FACING) ||
                                    adjacentBlockState.getValue(StairBlock.FACING) ==
                                    state.getValue(StairBlock.FACING).getClockWise())) {
                            return true;
                        }
                        break;
                }
            }
        }

        if (adjacentBlockState.getValue(StairBlock.FACING) == side.getOpposite()) {
            return true;
        }

        if (side == state.getValue(StairBlock.FACING)) {
            if (state.getValue(StairBlock.HALF) == adjacentBlockState.getValue(StairBlock.HALF) &&
                state.getValue(StairBlock.SHAPE) != StairsShape.STRAIGHT) {
                if (adjacentBlockState.getValue(StairBlock.FACING) ==
                    state.getValue(StairBlock.FACING).getCounterClockWise() &&
                    adjacentBlockState.getValue(StairBlock.SHAPE) != StairsShape.OUTER_RIGHT) {
                    return true;
                } else if (adjacentBlockState.getValue(StairBlock.FACING) ==
                           state.getValue(StairBlock.FACING).getClockWise() &&
                           adjacentBlockState.getValue(StairBlock.SHAPE) != StairsShape.OUTER_LEFT) {
                    return true;
                }
            }
        }

        if (side == state.getValue(StairBlock.FACING).getOpposite()) {
            if (state.getValue(StairBlock.HALF) == adjacentBlockState.getValue(StairBlock.HALF)) {
                if (adjacentBlockState.getValue(StairBlock.FACING) ==
                    state.getValue(StairBlock.FACING).getCounterClockWise() &&
                    adjacentBlockState.getValue(StairBlock.SHAPE) != StairsShape.OUTER_RIGHT) {
                    return true;
                } else if (adjacentBlockState.getValue(StairBlock.FACING) ==
                           state.getValue(StairBlock.FACING).getClockWise() &&
                           adjacentBlockState.getValue(StairBlock.SHAPE) != StairsShape.OUTER_LEFT) {
                    return true;
                }
            }
        }

        if (side == state.getValue(StairBlock.FACING).getCounterClockWise()) {
            if (state.getValue(StairBlock.HALF) == adjacentBlockState.getValue(StairBlock.HALF)) {
                if (adjacentBlockState.getValue(StairBlock.FACING) == side &&
                    state.getValue(StairBlock.SHAPE) != StairsShape.INNER_LEFT &&
                    adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.INNER_RIGHT) {
                    return true;
                } else if (adjacentBlockState.getValue(StairBlock.FACING) == state.getValue(StairBlock.FACING) &&
                           adjacentBlockState.getValue(StairBlock.SHAPE) != StairsShape.OUTER_LEFT) {
                    return true;
                } else if (adjacentBlockState.getValue(StairBlock.FACING) ==
                           state.getValue(StairBlock.FACING).getOpposite() &&
                           state.getValue(StairBlock.SHAPE) == StairsShape.OUTER_RIGHT) {
                    return true;
                }
            }
        }

        if (side == state.getValue(StairBlock.FACING).getClockWise()) {
            if (state.getValue(StairBlock.HALF) == adjacentBlockState.getValue(StairBlock.HALF)) {
                if (adjacentBlockState.getValue(StairBlock.FACING) == side &&
                    state.getValue(StairBlock.SHAPE) != StairsShape.INNER_RIGHT &&
                    adjacentBlockState.getValue(StairBlock.SHAPE) == StairsShape.INNER_LEFT) {
                    return true;
                } else if (adjacentBlockState.getValue(StairBlock.FACING) == state.getValue(StairBlock.FACING) &&
                           adjacentBlockState.getValue(StairBlock.SHAPE) != StairsShape.OUTER_RIGHT) {
                    return true;
                } else if (adjacentBlockState.getValue(StairBlock.FACING) ==
                           state.getValue(StairBlock.FACING).getOpposite() &&
                           state.getValue(StairBlock.SHAPE) == StairsShape.OUTER_LEFT) {
                    return true;
                }
            }
        }

        return false;
    }
}
