package io.firetamer.dragonblockbeyond.util.dataGen;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.util.dataGen.providers.*;
import io.firetamer.dragonblockbeyond.util.dataGen.providers.lang.EnUSLangDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new EnUSLangDataProvider(generator));
            generator.addProvider(new BlockStateDataProvider(generator, fileHelper));
            generator.addProvider(new ItemModelDataProvider(generator, fileHelper));
        }
        if (event.includeServer()) {
            //generator.addProvider(new LootTableDataProvider(generator));
            generator.addProvider(new BlockTagsDataProvider(generator, fileHelper));
        }
    }
}
