package io.firetamer.dragonblockbeyond._modules.namek_module;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.namek_module.blocks.*;
import io.firetamer.dragonblockbeyond._modules.namek_module.features.NamekTreeGrower;
import io.firetamer.dragonblockbeyond.handlers.RegistryHandler;
import io.firetamer.dragonblockbeyond.handlers.TextureHandler;
import io.firetamer.dragonblockbeyond.util.library_candidates.ModuleBase;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NamekModule extends ModuleBase {
    public static void init(){}


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    //TODO Redo the Item Texture for this one. It looks like shit
    public static final RegistryObject<Item> AJISA_FLOWERS = registerItem("ajisa_flowers",
            () -> new Item(new Item.Properties()), RegistryHandler.ITEMS);

    public static final RegistryObject<Item> NAMEK_KELP_BUDS = registerItem("namek_kelp_buds",
            () -> new Item(new Item.Properties()), RegistryHandler.ITEMS);


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    public static final RegistryObject<Block> NAMEK_GRASS_BLOCK = registerBlock("namek_grass_block",
            () -> new NamekGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<Block> TALL_NAMEK_GRASS = registerBlock("tall_namek_grass",
            () -> new NamekGrassPlant(BlockBehaviour.Properties.copy(Blocks.GRASS)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<Block> SHORT_NAMEK_GRASS = registerBlock("short_namek_grass",
            () -> new NamekGrassPlant(BlockBehaviour.Properties.copy(Blocks.GRASS)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<Block> NAMEK_SEAGRASS = registerBlock("namek_seagrass",
            () -> new NamekSeaGrass(BlockBehaviour.Properties.copy(Blocks.SEAGRASS)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<TallNamekSeaGrass> TALL_NAMEK_SEAGRASS = registerBlock("tall_namek_seagrass",
            () -> new TallNamekSeaGrass(BlockBehaviour.Properties.copy(Blocks.TALL_SEAGRASS)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    //TODO Make both of these blocks drop te head block, which should also have a 2d handmade item model similar to vanilla kelp.
    public static final RegistryObject<Block> NAMEK_KELP_BODY = registerBlock("namek_kelp_stem",
            () -> new NamekKelpBody(BlockBehaviour.Properties.of(Material.WATER_PLANT).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS)),
            RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<Block> NAMEK_KELP_HEAD = registerBlock("namek_kelp_top",
            () -> new NamekKelpHead(BlockBehaviour.Properties.of(Material.WATER_PLANT).noCollission().instabreak().sound(SoundType.WET_GRASS)),
            RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<Block> AJISA_BUSH = registerBlock("ajisa_bush",
            () -> new AjisaBush(BlockBehaviour.Properties.of(Material.BAMBOO).strength(0.2f).sound(SoundType.GRASS).noOcclusion().randomTicks()),
            RegistryHandler.BLOCKS, RegistryHandler.ITEMS);



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

    public static final TagKey<Fluid> NAMEK_WATER_KEY = createFluidKey(DragonBlockBeyond.MOD_ID, "namek_water");

    public static final RegistryObject<FlowingFluid> NAMEK_FLUID_SOURCE = registerFluid("namek_fluid",
            () -> new ForgeFlowingFluid.Source(NamekModule.NAMEK_FLUID_PROPERTIES), RegistryHandler.FLUIDS);

    public static final RegistryObject<FlowingFluid> NAMEK_FLUID_FLOWING = registerFluid("namek_flowing",
            () -> new ForgeFlowingFluid.Flowing(NamekModule.NAMEK_FLUID_PROPERTIES), RegistryHandler.FLUIDS);

    public static final ForgeFlowingFluid.Properties NAMEK_FLUID_PROPERTIES =
            new ForgeFlowingFluid.Properties(() -> NAMEK_FLUID_SOURCE.get(), () -> NAMEK_FLUID_FLOWING.get(),
            FluidAttributes.builder(TextureHandler.STILL_RL, TextureHandler.FLOWING_RL)
                    .color(0xFF197302)
                    .density(15)
                    .viscosity(5)
                    .temperature(10)
                    .luminosity(0)
                    .overlay(TextureHandler.OVERLAY_RL))
                    .block(() -> NamekModule.NAMEK_FLUID_BLOCK.get())
                    .bucket(() -> NamekModule.NAMEK_FLUID_BUCKET.get());

    //TODO Make the fluid "erode" vanilla kelp and either turn vanilla water to namek water (if there is more namek water) or turn into vanilla water (if there is more of it) on contact.
    public static final RegistryObject<LiquidBlock> NAMEK_FLUID_BLOCK = registerBlockNoItem("namek_fluid_block",
            () -> new LiquidBlock(() -> NamekModule.NAMEK_FLUID_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(100f).noDrops()), RegistryHandler.BLOCKS);

    public static final RegistryObject<Item> NAMEK_FLUID_BUCKET = registerItem("namek_fluid_bucket",
            () -> new BucketItem(NamekModule.NAMEK_FLUID_SOURCE, new Item.Properties().stacksTo(1)), RegistryHandler.ITEMS);
}
