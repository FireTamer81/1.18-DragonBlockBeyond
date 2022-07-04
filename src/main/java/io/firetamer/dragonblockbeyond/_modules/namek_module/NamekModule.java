package io.firetamer.dragonblockbeyond._modules.namek_module;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.namek_module.blocks.NamekGrassBlock;
import io.firetamer.dragonblockbeyond._modules.namek_module.blocks.NamekGrassPlant;
import io.firetamer.dragonblockbeyond._modules.namek_module.blocks.NamekLog;
import io.firetamer.dragonblockbeyond._modules.namek_module.blocks.NamekTreeSapling;
import io.firetamer.dragonblockbeyond._modules.namek_module.features.NamekTreeGrower;
import io.firetamer.dragonblockbeyond.handlers.RegistryHandler;
import io.firetamer.dragonblockbeyond.handlers.TextureHandler;
import io.firetamer.dragonblockbeyond.util.library_candidates.ModuleBase;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NamekModule extends ModuleBase {
    public static void init(){}

    public static final RegistryObject<Block> NAMEK_GRASS_BLOCK = registerBlock("namek_grass_block",
            () -> new NamekGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<Block> TALL_NAMEK_GRASS = registerBlock("tall_namek_grass",
            () -> new NamekGrassPlant(BlockBehaviour.Properties.copy(Blocks.GRASS)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<Block> SHORT_NAMEK_GRASS = registerBlock("short_namek_grass",
            () -> new NamekGrassPlant(BlockBehaviour.Properties.copy(Blocks.GRASS)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    /*
    @AutoBlockItem
	@RegisterBlock("ajisa_bush")
	public static final Block AJISA_BUSH = new AjisaBush(AbstractBlock.Properties.of(Material.LEAVES).strength(0.2f)
			.sound(SoundType.GRASS).noOcclusion().randomTicks());

	@RegisterItem("ajisa_flowers")
	public static final Item AJISA_FLOWERS = new Item(new Item.Properties());

	@AutoBlockItem
	@RegisterBlock("namek_seagrass")
	public static final Block NAMEK_SEAGRASS = new NamekSeaGrass(AbstractBlock.Properties
			.of(Material.REPLACEABLE_WATER_PLANT).noCollission().instabreak().sound(SoundType.WET_GRASS));

	@AutoBlockItem
	@RegisterBlock("namek_tall_seagrass")
	public static final Block NAMEK_TALL_SEAGRASS = new NamekSeaGrassTall(AbstractBlock.Properties
			.of(Material.REPLACEABLE_WATER_PLANT).noCollission().instabreak().sound(SoundType.WET_GRASS));

	@AutoBlockItem
	@RegisterBlock("namek_kelp_top")
	public static final Block NAMEK_KELP_TOP = new NamekKelpTop(AbstractBlock.Properties
			.of(Material.REPLACEABLE_WATER_PLANT).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS));

	@AutoBlockItem
	@RegisterBlock("namek_kelp_stem")
	public static final Block NAMEK_KELP_STEM = new NamekKelpStem(AbstractBlock.Properties
			.of(Material.REPLACEABLE_WATER_PLANT).noCollission().instabreak().randomTicks().sound(SoundType.WET_GRASS));

	@RegisterItem("namek_kelp_buds")
	public static final Item NAMEK_KELP_BUDS = new Item(new Item.Properties());
     */


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    public static final RegistryObject<RotatedPillarBlock> NAMEK_LOG = registerBlock("namek_log",
            () -> new NamekLog(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<RotatedPillarBlock> STRIPPED_NAMEK_LOG = registerBlock("stripped_namek_log",
            () -> new NamekLog(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<RotatedPillarBlock> NAMEK_WOOD = registerBlock("namek_wood",
            () -> new NamekLog(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<RotatedPillarBlock> STRIPPED_NAMEK_WOOD = registerBlock("stripped_namek_wood",
            () -> new NamekLog(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<Block> NAMEK_PLANKS = registerBlock("namek_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<Block> NAMEK_LEAVES = registerBlock("namek_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<Block> NAMEK_TREE_SAPLING = registerBlock("namek_tree_sapling",
            () -> new NamekTreeSapling(new NamekTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    public static final RegistryObject<FlowingFluid> NAMEK_FLUID = registerFluid("namek_fluid",
            () -> new ForgeFlowingFluid.Source(NamekModule.NAMEK_FLUID_PROPERTIES), RegistryHandler.FLUIDS);

    public static final RegistryObject<FlowingFluid> NAMEK_FLOWING = registerFluid("namek_flowing",
            () -> new ForgeFlowingFluid.Flowing(NamekModule.NAMEK_FLUID_PROPERTIES), RegistryHandler.FLUIDS);

    public static final ForgeFlowingFluid.Properties NAMEK_FLUID_PROPERTIES =
            new ForgeFlowingFluid.Properties(() -> NAMEK_FLUID.get(), () -> NAMEK_FLOWING.get(),
            FluidAttributes.builder(TextureHandler.STILL_RL, TextureHandler.FLOWING_RL)
                    .color(0xFF197302)
                    .density(15)
                    .viscosity(5)
                    .temperature(10)
                    .luminosity(0)
                    .overlay(TextureHandler.OVERLAY_RL))
                    .block(() -> NamekModule.NAMEK_FLUID_BLOCK.get())
                    .bucket(() -> NamekModule.NAMEK_FLUID_BUCKET.get());

    //TODO Make the fluid "erode" vanilla kelp and turn vanilla water to namek water on contact.
    public static final RegistryObject<LiquidBlock> NAMEK_FLUID_BLOCK = registerBlockNoItem("namek_fluid_block",
            () -> new LiquidBlock(() -> NamekModule.NAMEK_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(100f).noDrops()), RegistryHandler.BLOCKS);

    public static final RegistryObject<Item> NAMEK_FLUID_BUCKET = registerItem("namek_fluid_bucket",
            () -> new BucketItem(NamekModule.NAMEK_FLUID, new Item.Properties().stacksTo(1)), RegistryHandler.ITEMS);




    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    //Events
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(NAMEK_TREE_SAPLING.get(), RenderType.cutoutMipped());

        ItemBlockRenderTypes.setRenderLayer(SHORT_NAMEK_GRASS.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(TALL_NAMEK_GRASS.get(), RenderType.cutoutMipped());

        ItemBlockRenderTypes.setRenderLayer(NAMEK_FLUID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(NAMEK_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(NAMEK_FLUID_BLOCK.get(), RenderType.translucent());

        //ItemBlockRenderTypes.setRenderLayer(NAMEK_SEAGRASS.get(), RenderType.cutoutMipped());
        //ItemBlockRenderTypes.setRenderLayer(NAMEK_TALL_SEAGRASS.get(), RenderType.cutoutMipped());
        //ItemBlockRenderTypes.setRenderLayer(NAMEK_KELP_STEM.get(), RenderType.cutoutMipped());
        //ItemBlockRenderTypes.setRenderLayer(NAMEK_KELP_TOP.get(), RenderType.cutoutMipped());
    }

    /*
    @SubscribeEvent
    public static void greenFogInNamekWater(EntityViewRenderEvent.FogColors event) {
        PlayerEntity player = Minecraft.getInstance().player;
        double eyeHeight = player.getEyeY() - 1 / 9d;
        FluidState fluidstate = player.level.getFluidState(new BlockPos(player.getX(), eyeHeight, player.getZ()));

        if (fluidstate.getType() == NamekModule.NAMEK_FLUID_FLOWING.get()
                || fluidstate.getType() == NamekModule.NAMEK_FLUID_SOURCE.get()) {
            event.setBlue(0.09F);
            event.setGreen(0.45F); // As it stands, what I have done is match the ratios shown in the fluid color
            // to the fog color. 255 for the fluid color being 1.0F for the Fog DBBColor.
            event.setRed(0);
        }
    }

    @SubscribeEvent
    public static void cancelVanillaWaterOverlay(RenderBlockOverlayEvent event) {
        @SuppressWarnings("resource")
        PlayerEntity player = Minecraft.getInstance().player;
        double eyeHeight = player.getEyeY() - 1 / 9d;
        FluidState fluidstate = player.level.getFluidState(new BlockPos(player.getX(), eyeHeight, player.getZ()));

        if (fluidstate.getType() == NamekModule.NAMEK_FLUID_FLOWING.get()
                || fluidstate.getType() == NamekModule.NAMEK_FLUID_SOURCE.get()) {
            if (event.isCancelable()) {
                event.setCanceled(true);
            }
        }
    }

    // The only weird thing about this event I have done is that you cannot just
    // click any side of the Namek Dirt/Grass and it function,
    // it has to be the top unlike the vanilla functionality.
    @SubscribeEvent
    public static void onHoeUse(UseHoeEvent event) {
        RayTraceResult lookingAt = Minecraft.getInstance().hitResult;
        if (lookingAt != null && lookingAt.getType() == RayTraceResult.Type.BLOCK) {
            double x = lookingAt.getLocation().x();
            double y = lookingAt.getLocation().y();
            double z = lookingAt.getLocation().z();

            BlockPos pos = new BlockPos(x, y, z).below();

            PlayerEntity player = event.getPlayer();
            World world = player.getCommandSenderWorld();
            BlockState state = world.getBlockState(pos);

            if (state == BlockInit.CLAY_DIRT.defaultBlockState()) {
                world.playSound(player, pos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1, 1);
                world.setBlockAndUpdate(pos, NamekModule.TILLED_NAMEK_DIRT.defaultBlockState());
            }

            if (state == NamekModule.NAMEK_GRASS_BLOCK.defaultBlockState()) {
                world.playSound(player, pos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockAndUpdate(pos, NamekModule.TILLED_NAMEK_DIRT.defaultBlockState());
            }
        }
    }

    // This is being used to recreate the vanilla underwater grow effect, but for my
    // Namek Fluid and Sea Grasses
    // Remember to check "BonemealEvent" from forge and "BoneMealItem" form
    // minecraft to get this working.
    @SubscribeEvent
    public static void namekFluidBonemealUse(BonemealEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        World worldIn = player.getCommandSenderWorld();
        RayTraceResult lookingAt = Minecraft.getInstance().hitResult;
        Random rand = new Random();
        double eyeHeight = player.getEyeY() - 1 / 9d;
        FluidState fluidstate = player.level.getFluidState(new BlockPos(player.getX(), eyeHeight, player.getZ()));

        if (fluidstate.getType() == NamekModule.NAMEK_FLUID_FLOWING.get()
                || fluidstate.getType() == NamekModule.NAMEK_FLUID_SOURCE.get()) {
            if (lookingAt != null && lookingAt.getType() == RayTraceResult.Type.BLOCK) {
                double x = lookingAt.getLocation().x();
                double y = lookingAt.getLocation().y();
                double z = lookingAt.getLocation().z();

                BlockPos blockpos_above_1 = new BlockPos(x, y, z).above();

                // player.displayClientMessage(new StringTextComponent("Hey, Bonemeal + Namek
                // water + solid block shoudl do something"), false);
                // Here starts the more experimental stuff... Basically, don't delete anything
                // above here.
                Task: for (int i = 0; i < 128; ++i) {
                    BlockPos blockpos1 = blockpos_above_1;

                    for (int j = 0; j < i / 16; ++j) {
                        blockpos1 = blockpos1.offset(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2,
                                rand.nextInt(3) - 1);

                        if (worldIn.getBlockState(blockpos1).isCollisionShapeFullBlock(worldIn, blockpos1)) {
                            continue Task;
                        }
                    }

                    BlockPos blockpos1_above = blockpos1.above();
                    BlockState blockstate1 = worldIn.getBlockState(blockpos1);
                    BlockState blockstate2 = worldIn.getBlockState(blockpos1_above);

                    if (blockstate1.equals(NamekModule.NAMEK_FLUID_BLOCK)
                            && blockstate2.equals(NamekModule.NAMEK_FLUID_BLOCK)) {
                        BlockState blockstate3;

                        if (rand.nextInt(4) == 0) {
                            blockstate3 = NamekModule.TALL_NAMEK_GRASS.defaultBlockState();
                        } else {
                            blockstate3 = NamekModule.SHORT_NAMEK_GRASS.defaultBlockState();
                        }

                        worldIn.setBlockAndUpdate(blockpos1, blockstate3);
                    }
                }
            }
        }
    }
    */
}
