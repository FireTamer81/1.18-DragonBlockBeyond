package io.firetamer.dragonblockbeyond._modules.strongblock_module.util.network;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.util.network.packets.PaintBucketSyncPKT;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(DragonBlockBeyond.MOD_ID, "main"), () ->
                    PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);


    public static void register() {
        int index = 0;

        //Paint Bucket Sync
        INSTANCE.registerMessage(index++,
                PaintBucketSyncPKT.class,
                PaintBucketSyncPKT::encode,
                PaintBucketSyncPKT::decode,
                PaintBucketSyncPKT.Handler::handle);


    }


    public static void sendToServer(Object message) {
        INSTANCE.sendToServer(message);
    }
}
