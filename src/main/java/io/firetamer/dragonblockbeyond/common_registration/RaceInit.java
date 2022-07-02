package io.firetamer.dragonblockbeyond.common_registration;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.race.Race;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class RaceInit {
    public static final DeferredRegister<Race> RACES = DeferredRegister.create(new ResourceLocation(DragonBlockBeyond.MOD_ID, "races"), DragonBlockBeyond.MOD_ID);
    private static final Supplier<IForgeRegistry<Race>> SUPPLIER = RACES.makeRegistry(Race.class, () -> new RegistryBuilder<Race>().hasTags());



    public static final RegistryObject<Race> SAIYAN = RACES.register("saiyan", ()->new Race(new Race.Properties<>()));







    public static IForgeRegistry<Race> getRegistry() {
        return SUPPLIER.get();
    }
}
