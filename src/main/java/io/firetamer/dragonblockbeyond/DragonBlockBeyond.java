package io.firetamer.dragonblockbeyond;

import com.mojang.logging.LogUtils;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.ColorableStrongBlockModule;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.dispenser.DispensePaintbucketBehaviour;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.util.network.PacketHandler;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.util.top.TOPMain;
import io.firetamer.dragonblockbeyond.handlers.CreativeTabHandler;
import io.firetamer.dragonblockbeyond.handlers.RegistryHandler;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod("dragonblockbeyond")
public class DragonBlockBeyond {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "dragonblockbeyond";

    public static final CreativeTabHandler.DBBItemsTab DBB_ITEMS_TAB = new CreativeTabHandler.DBBItemsTab(CreativeModeTab.TABS.length, "dbb_items_tab");
    public static final CreativeTabHandler.DBBBlocksTab DBB_BLOCKS_TAB = new CreativeTabHandler.DBBBlocksTab(CreativeModeTab.TABS.length, "dbb_blocks_tab");

    public DragonBlockBeyond() {
        //GeckoLibMod.DISABLE_IN_DEV = true;
        //GeckoLib.initialize();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        RegistryHandler registryHandler = new RegistryHandler(modEventBus);
        registryHandler.init();

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::enqueueIMC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(final FMLCommonSetupEvent event) {
        PacketHandler.register();

        event.enqueueWork(() -> DispenserBlock.registerBehavior(ColorableStrongBlockModule.PAINT_BUCKET.get(),
                new DispensePaintbucketBehaviour()));
    }

    public void enqueueIMC(final InterModEnqueueEvent event) {
        if (ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", TOPMain::new);
        }
    }










}
