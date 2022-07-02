package io.firetamer.dragonblockbeyond._modules.strongblock_module;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.blocks.*;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.client.colorhandlers.PaintBucketItemColor;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.client.colorhandlers.WarenaiBlockColor;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.client.colorhandlers.WarenaiBlockItemColor;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.client.gui.screen.ColorSelectScreen;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.items.PaintBucketItem;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.items.WarenaiBlockItem;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.tiles.StrongBlockTile;
import io.firetamer.dragonblockbeyond.common_registration.BlockInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static io.firetamer.dragonblockbeyond.common_registration.ItemInit.ITEMS;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StrongBlockModule extends BlockInit {
    public static void init() {
    }





    private static <T extends Block> RegistryObject<T> registerWarenaiBlock(String name, Supplier<T> block) {
        return registerBlock(name, block, b->()-> new WarenaiBlockItem(b.get()));
    }

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return CONTAINER_MENUS.register(name, () -> IForgeMenuType.create(factory));
    }



    /******************************************************************************************************************/
    //Blocks
    /******************************************************************************************************************/




    public static final RegistryObject<WarenaiBlock> WARENAI_FULL_BLOCK = registerWarenaiBlock("warenai_full_block", () ->
            new WarenaiBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)));

    public static final RegistryObject<WarenaiBlockStairs> WARENAI_STAIRS_BLOCK = registerWarenaiBlock("warenai_stairs", () ->
            new WarenaiBlockStairs(() -> WARENAI_FULL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.HEAVY_METAL)));

    public static final RegistryObject<WarenaiSlab> WARENAI_SLAB_BLOCK = registerWarenaiBlock("warenai_slab", () ->
            new WarenaiSlab(BlockBehaviour.Properties.of(Material.HEAVY_METAL)));

    public static final RegistryObject<WarenaiBlockFence> WARENAI_FENCE_BLOCK = registerWarenaiBlock("warenai_fence", () ->
            new WarenaiBlockFence(BlockBehaviour.Properties.of(Material.HEAVY_METAL)));

    //TODO Add a WallBlock and GlassPane block (Be Wary, for some reason my first attempt at doing a WallBlock gave the infamous "Rendering Overlay" error that never explains what's wrong)
    //public static final RegistryObject<WarenaiBlockWall> WARENAI_WALL_BLOCK = registerBlock("warenai_wall", () ->
    //        new WarenaiBlockWall(BlockBehaviour.Properties.of(Material.HEAVY_METAL)));

    public static final RegistryObject<WarenaiGlassBlock> WARENAI_GLASS = registerWarenaiBlock("warenai_glass", WarenaiGlassBlock::new);
    public static final RegistryObject<WarenaiGlassSlabBlock> WARENAI_GLASS_SLAB = registerWarenaiBlock("warenai_glass_slab", WarenaiGlassSlabBlock::new);
    public static final RegistryObject<WarenaiGlassStairsBlock> WARENAI_GLASS_STAIRS = registerWarenaiBlock("warenai_glass_stairs",
            () -> new WarenaiGlassStairsBlock(() -> WARENAI_GLASS.get()
                    .defaultBlockState()));




    /******************************************************************************************************************/
    //Tile Entities
    /******************************************************************************************************************/


    public static final RegistryObject<BlockEntityType<StrongBlockTile>> STRONG_BLOCK_TILE = TILES.register("strong_block_tile",
            () -> BlockEntityType.Builder.of(StrongBlockTile::new,
                    BLOCKS.getEntries().stream().map(RegistryObject::get).toArray(Block[]::new)).build(null));




    /******************************************************************************************************************/
    //Items
    /******************************************************************************************************************/


    public static final RegistryObject<Item> PAINT_BUCKET = ITEMS.register("paint_bucket", PaintBucketItem::new);


    /******************************************************************************************************************/
    //Container Menus
    /******************************************************************************************************************/




    /******************************************************************************************************************/
    //Recipes
    /******************************************************************************************************************/





    /******************************************************************************************************************/
    //Events / Util
    /******************************************************************************************************************/





    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(StrongBlockModule.WARENAI_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(StrongBlockModule.WARENAI_GLASS_STAIRS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(StrongBlockModule.WARENAI_GLASS_SLAB.get(), RenderType.translucent());

        //ItemBlockRenderTypes.setRenderLayer();
    }

    @SubscribeEvent
    public static void registerColorHandlers(ColorHandlerEvent.Item event) {
        event.getBlockColors()
                .register(new WarenaiBlockColor(),
                        StrongBlockModule.BLOCKS.getEntries().stream().map(RegistryObject::get).toArray(Block[]::new));
        event.getItemColors()
                .register(new WarenaiBlockItemColor(),
                        StrongBlockModule.BLOCKS.getEntries().stream().map(RegistryObject::get).toArray(Block[]::new));

        event.getItemColors()
                .register(new PaintBucketItemColor(),
                        StrongBlockModule.PAINT_BUCKET.get());
    }

    public static void openColorSelectScreen(int color, boolean isRGBSelected) {
        Minecraft.getInstance().setScreen(new ColorSelectScreen(color, isRGBSelected));
    }

    public static boolean hasShiftDown() {
        return Screen.hasShiftDown();
    }
}
