package io.firetamer.dragonblockbeyond._modules.machines_module;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.FabricatorBlock;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.FabricatorBlockTile;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.FabricatorBlockTileRenderer;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.container.FabricatorContainerMenu;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui.screen.FabricatorScreen_TEST_3;
import io.firetamer.dragonblockbeyond.handlers.RegistryHandler;
import io.firetamer.dragonblockbeyond.util.library_candidates.ModuleBase;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MachinesModule extends ModuleBase {


    public static void init(){}



    /******************************************************************************************************************/
    //Fabricator Machine
    /******************************************************************************************************************/


    public static final RegistryObject<Block> FABRICATOR_BLOCK = registerBlock("fabricator", () ->
            new FabricatorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)), RegistryHandler.BLOCKS, RegistryHandler.ITEMS);

    public static final RegistryObject<BlockEntityType<FabricatorBlockTile>> FABRICATOR_TILE = RegistryHandler.TILES.register("fabricator_tile",
            () -> BlockEntityType.Builder.of(FabricatorBlockTile::new, FABRICATOR_BLOCK.get()).build(null));

    public static final RegistryObject<MenuType<FabricatorContainerMenu>> FABRICATOR_MENU_TYPE =
            registerMenuType(FabricatorContainerMenu::new, "fabricator_menu", RegistryHandler.CONTAINER_MENUS);



    /******************************************************************************************************************/
    //EventHandler
    /******************************************************************************************************************/


    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(FABRICATOR_TILE.get(), FabricatorBlockTileRenderer::new);
    }

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event) {
        MenuScreens.register(FABRICATOR_MENU_TYPE.get(), FabricatorScreen_TEST_3::new);
    }

}
