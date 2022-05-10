package io.firetamer.dragonblockbeyond;

import com.matyrobbrt.lib.ClientSetup;
import com.matyrobbrt.lib.ModSetup;
import com.matyrobbrt.lib.registry.annotation.AnnotationProcessor;
import com.mojang.logging.LogUtils;
import io.firetamer.dragonblockbeyond.api.extensions.ApiExtensions;
import io.firetamer.dragonblockbeyond.api.registry.DBBAnnotationProcessor;
import io.firetamer.dragonblockbeyond.client.DBBClientSetup;
import io.firetamer.dragonblockbeyond.item_groups.DBBBlocksItemGroup;
import io.firetamer.dragonblockbeyond.item_groups.DBBItemGroup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.function.Supplier;

@Mod("dragonblockbeyond")
public class DragonBlockBeyond extends ModSetup {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static String MOD_ID = "dragonblockbeyond";
    public static final DBBAnnotationProcessor ANNOTATION_PROCESSOR = new DBBAnnotationProcessor(MOD_ID);

    public static final CreativeModeTab Item_GROUP = new DBBItemGroup("dbb_items_group");
    public static final CreativeModeTab BLOCKS_GROUP = new DBBBlocksItemGroup("dbb_blocks_group");

    public DragonBlockBeyond() {
        super(MOD_ID);

        //GeckoLibMod.DISABLE_IN_DEV = true;
        //GeckoLib.initialize();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ANNOTATION_PROCESSOR.setAutoBlockItemTab(block -> BLOCKS_GROUP);
        ApiExtensions.registerAnnotationExtensions();

        //modEventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }



    @Override
    public AnnotationProcessor annotationProcessor() {
        return ANNOTATION_PROCESSOR;
    }

    @Override
    public Optional<Supplier<ClientSetup>> clientSetup() {
        return Optional.of(() -> new DBBClientSetup(modBus));
    }

}
