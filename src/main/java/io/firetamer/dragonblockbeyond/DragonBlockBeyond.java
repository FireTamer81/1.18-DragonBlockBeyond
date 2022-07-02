package io.firetamer.dragonblockbeyond;

import com.mojang.logging.LogUtils;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.StrongBlockModule;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.dispenser.DispensePaintbucketBehaviour;
import io.firetamer.dragonblockbeyond.network.PacketHandler;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.util.top.TOPMain;
import io.firetamer.dragonblockbeyond.common_registration.RaceInit;
import io.firetamer.dragonblockbeyond.handlers.CreativeTabHandler;
import io.firetamer.dragonblockbeyond.handlers.RegistryHandler;
import io.firetamer.dragonblockbeyond.handlers.TextureHandler;
import io.firetamer.dragonblockbeyond.race.capability.RaceHolderAttacher;
import io.firetamer.dragonblockbeyond.race.client.ClientGeckolibIntegration;
import io.firetamer.dragonblockbeyond.race.event.NMCommonForgeEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
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

        RegistryHandler.init(modEventBus);

        TextureHandler.init();
        NMCommonForgeEvents.register();

        RaceHolderAttacher.register();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.register(this);
        if (FMLLoader.getDist() == Dist.CLIENT && ModList.get().isLoaded("geckolib3"))
            ClientGeckolibIntegration.registerClientReloadListener();
    }







    public void setup(final FMLCommonSetupEvent event) {
        PacketHandler.register();

        event.enqueueWork(() -> DispenserBlock.registerBehavior(StrongBlockModule.PAINT_BUCKET.get(),
                new DispensePaintbucketBehaviour()));
    }

    public void enqueueIMC(final InterModEnqueueEvent event) {
        if (ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", TOPMain::new);
        }
    }
}
