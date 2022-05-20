package io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.blocks.full_block.WarenaiBlockLoader;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.blocks.full_block.WarenaiBlockModel;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.client.colorhandlers.PaintBucketItemColor;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.client.colorhandlers.WarenaiBlockColor;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.client.colorhandlers.WarenaiBlockItemColor;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.client.gui.screen.ColorSelectScreen;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.blocks.full_block.WarenaiBlock;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.items.PaintBucketItem;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.tiles.StrongBlockTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorableStrongBlockModule {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DragonBlockBeyond.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DragonBlockBeyond.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, DragonBlockBeyond.MOD_ID);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }



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
    //Blocks
    /******************************************************************************************************************/

    public static final RegistryObject<Block> WARENAI_FULL_BLOCK = registerBlock("warenai_full_block", WarenaiBlock::new);






    /******************************************************************************************************************/
    //Events / Util
    /******************************************************************************************************************/

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event) {
        //ItemBlockRenderTypes.setRenderLayer(ColorableStrongBlockModule.RGB_GLASS.get(), RenderType.translucent());
        //ItemBlockRenderTypes.setRenderLayer(ColorableStrongBlockModule.RGB_GLASS_STAIRS.get(), RenderType.translucent());
        //ItemBlockRenderTypes.setRenderLayer(ColorableStrongBlockModule.RGB_GLASS_SLAB.get(), RenderType.translucent());
        //ItemBlockRenderTypes.setRenderLayer(ColorableStrongBlockModule.RGB_GLASS_PANE.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(WARENAI_FULL_BLOCK.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(DragonBlockBeyond.MOD_ID, "warenai_full_block_loader"), new WarenaiBlockLoader());
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onTextureStitchEvent(TextureStitchEvent.Pre event) { //getSmoothWarenaiBlockResourceLocations()
        if (event.getAtlas().location() == TextureAtlas.LOCATION_BLOCKS) {
            event.addSprite(WarenaiBlockModel.UNDERLAY_TEXTURE);
            event.addSprite(WarenaiBlockModel.POLISHED_UNDERLAY_TEXTURE);
            event.addSprite(WarenaiBlockModel.LARGE_BRICK_UNDERLAY_TEXTURE);
            event.addSprite(WarenaiBlockModel.POLISHED_LARGE_BRICK_UNDERLAY_TEXTURE);
            event.addSprite(WarenaiBlockModel.SMALL_BRICK_UNDERLAY_TEXTURE);
            event.addSprite(WarenaiBlockModel.POLISHED_SMALL_BRICK_UNDERLAY_TEXTURE);

            event.addSprite(WarenaiBlockModel.SCUFFED_OVERLAY_TEXTURE);
            event.addSprite(WarenaiBlockModel.CRACKED1_OVERLAY_TEXTURE);
            event.addSprite(WarenaiBlockModel.CRACKED2_OVERLAY_TEXTURE);
            event.addSprite(WarenaiBlockModel.CRACKED3_OVERLAY_TEXTURE);
            event.addSprite(WarenaiBlockModel.CRACKED4_OVERLAY_TEXTURE);
        }
    }

    @SubscribeEvent
    public static void registerColorHandlers(ColorHandlerEvent.Item event) {
        event.getBlockColors()
                .register(new WarenaiBlockColor(),
                        ColorableStrongBlockModule.BLOCKS.getEntries().stream().map(RegistryObject::get).toArray(Block[]::new));
        event.getItemColors()
                .register(new WarenaiBlockItemColor(),
                        ColorableStrongBlockModule.BLOCKS.getEntries().stream().map(RegistryObject::get).toArray(Block[]::new));

        event.getItemColors()
                .register(new PaintBucketItemColor(),
                        ColorableStrongBlockModule.PAINT_BUCKET.get());
    }

    public static void openColorSelectScreen(int color, boolean isRGBSelected) {
        Minecraft.getInstance().setScreen(new ColorSelectScreen(color, isRGBSelected));
    }

    public static boolean hasShiftDown() {
        return Screen.hasShiftDown();
    }
}
