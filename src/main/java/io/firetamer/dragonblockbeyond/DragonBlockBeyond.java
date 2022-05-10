package io.firetamer.dragonblockbeyond;

import com.mojang.logging.LogUtils;
import io.firetamer.dragonblockbeyond.item_groups.DBBBlocksItemGroup;
import io.firetamer.dragonblockbeyond.item_groups.DBBItemGroup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod("dragonblockbeyond")
public class DragonBlockBeyond {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static String MOD_ID = "dragonblockbeyond";

    public static final CreativeModeTab Item_GROUP = new DBBItemGroup("dbb_items_group");
    public static final CreativeModeTab BLOCKS_GROUP = new DBBBlocksItemGroup("dbb_blocks_group");

    public DragonBlockBeyond() {

        //GeckoLibMod.DISABLE_IN_DEV = true;
        //GeckoLib.initialize();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }



    
    public void setup(FMLCommonSetupEvent e) { }
}
