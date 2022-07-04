package io.firetamer.dragonblockbeyond.handlers;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.machines_module.MachinesModule;
import io.firetamer.dragonblockbeyond._modules.namek_module.NamekModule;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.StrongBlockModule;
import io.firetamer.dragonblockbeyond.init.CommonObjects;
import io.firetamer.dragonblockbeyond.init.RaceInit;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Stream;


public class RegistryHandler {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DragonBlockBeyond.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DragonBlockBeyond.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, DragonBlockBeyond.MOD_ID);
    public static final DeferredRegister<MenuType<?>> CONTAINER_MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, DragonBlockBeyond.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, DragonBlockBeyond.MOD_ID);

    public static void init(IEventBus modBus) {
        CommonObjects.init();
        StrongBlockModule.init();
        MachinesModule.init();
        NamekModule.init();

        Stream.of(
                ITEMS,
                BLOCKS,
                CONTAINER_MENUS,
                TILES,
                FLUIDS,

                RaceInit.RACES
        ).forEach(registry -> registry.register(modBus));
    }
}
