package io.firetamer.dragonblockbeyond.network.packets;

import dev._100media.capabilitysyncer.network.IPacket;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.items.PaintBucketItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

public class PaintBucketSyncPKT implements IPacket {
    private int color;
    private boolean isRGBSelected;

    public PaintBucketSyncPKT(int color, boolean isRGBSelected) {
        this.color = color;
        this.isRGBSelected = isRGBSelected;
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(color);
        buffer.writeBoolean(isRGBSelected);
    }

    public static PaintBucketSyncPKT read(FriendlyByteBuf buffer) {
        return new PaintBucketSyncPKT(buffer.readInt(), buffer.readBoolean());
    }

    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            ItemStack stack = context.getSender().getMainHandItem();
            if (stack.getItem() instanceof PaintBucketItem) {
                stack.getOrCreateTag().putInt("color", color);
                stack.getOrCreateTag().putBoolean("isRGBSelected", isRGBSelected);
            }
        });
        context.setPacketHandled(true);
    }
    public static void register(SimpleChannel channel, int id) {
        IPacket.register(channel, id, NetworkDirection.PLAY_TO_SERVER, PaintBucketSyncPKT.class, PaintBucketSyncPKT::read);
    }
}
