package io.firetamer.dragonblockbeyond.handlers;

import io.firetamer.dragonblockbeyond._modules.fabricator_temp_module.FabricatorTempModule;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.StrongBlockModule;
import io.firetamer.dragonblockbeyond.common_registration.BlockInit;
import io.firetamer.dragonblockbeyond.common_registration.ItemInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;


/**
 * Since I couldn't figure out a work around to get annotations to work (thanks Mojang), this is my best alternative
 * All that is done in the main mod file is instantiate this class and call the init method, keeping the main mod file cleaner.
 * So, now all that needs to be done is call the deferred registers from each class that has them (see the commented example)
 */

public class RegistryHandler {
    IEventBus modBus;

    public RegistryHandler(IEventBus busIn) {
        this.modBus = busIn;
    }

    public void init() {
        DeferredRegister<?>[] registers = {
            ItemInit.ITEMS,

            BlockInit.BLOCKS,
            BlockInit.ITEMS,

            StrongBlockModule.BLOCKS,
            StrongBlockModule.ITEMS,
            StrongBlockModule.TILES,
            StrongBlockModule.CONTAINER_MENUS,
            StrongBlockModule.RECIPE_SERIALIZERS,

            FabricatorTempModule.BLOCKS,
            FabricatorTempModule.ITEMS,
            FabricatorTempModule.TILES,
            FabricatorTempModule.CONTAINER_MENUS,
            FabricatorTempModule.RECIPE_SERIALIZERS
        };

        for (DeferredRegister<?> register : registers) {
            register.register(modBus);
        }
    }
}
