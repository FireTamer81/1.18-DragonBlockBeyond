package io.firetamer.dragonblockbeyond.handlers;

import io.firetamer.dragonblockbeyond._modules.machines_module.MachinesModule;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.StrongBlockModule;
import io.firetamer.dragonblockbeyond.common_registration.BlockInit;
import io.firetamer.dragonblockbeyond.common_registration.ItemInit;
import io.firetamer.dragonblockbeyond.common_registration.RaceInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.stream.Stream;


/**
 * Since I couldn't figure out a work around to get annotations to work (thanks Mojang), this is my best alternative
 * All that is done in the main mod file is instantiate this class and call the init method, keeping the main mod file cleaner.
 * So, now all that needs to be done is call the deferred registers from each class that has them (see the commented example)
 */

public class RegistryHandler {

    public static void init(IEventBus modBus) {
        BlockInit.init();
        Stream.of(
                ItemInit.ITEMS,
                BlockInit.BLOCKS,
                BlockInit.CONTAINER_MENUS,
                BlockInit.RECIPE_SERIALIZERS,
                BlockInit.TILES,
                RaceInit.RACES
        ).forEach(registry -> registry.register(modBus));
    }
}
