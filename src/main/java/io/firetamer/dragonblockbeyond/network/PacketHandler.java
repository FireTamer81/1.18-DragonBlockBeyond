package io.firetamer.dragonblockbeyond.network;

import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.race.capability.RaceHolderAttacher;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1.6";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(DragonBlockBeyond.MOD_ID, "main"), () ->
                    PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int nextId = 0;

    public static void register() {
        Stream.<BiConsumer<SimpleChannel,Integer>>builder()
//                .add(PaintBucketSyncPKT::register)
                .add((channel, id) -> SimpleEntityCapabilityStatusPacket.register(RaceHolderAttacher.RACE_HOLDER_RL, RaceHolderAttacher::getRaceHolderUnwrap, channel, id))
                .build().forEach(consumer -> consumer.accept(INSTANCE, getNextId()));
    }

    private static int getNextId() {
        return nextId++;
    }
    public static void sendToServer(Object message) {
        INSTANCE.sendToServer(message);
    }
}
