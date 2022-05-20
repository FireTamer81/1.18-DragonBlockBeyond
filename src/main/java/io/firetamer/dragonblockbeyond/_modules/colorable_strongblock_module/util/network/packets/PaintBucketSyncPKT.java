package io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.util.network.packets;

import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.items.PaintBucketItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PaintBucketSyncPKT {
    private final int color;
    private final boolean isRGBSelected;

    public PaintBucketSyncPKT(int color, boolean isRGBSelected) {
        this.color = color;
        this.isRGBSelected = isRGBSelected;
    }

    public static void encode(PaintBucketSyncPKT message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.color);
        buffer.writeBoolean(message.isRGBSelected);
    }

    public static PaintBucketSyncPKT decode(FriendlyByteBuf buffer) {
        return new PaintBucketSyncPKT(buffer.readInt(), buffer.readBoolean());
    }

    public static class Handler {
        public static void handle(PaintBucketSyncPKT message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                ItemStack stack = context.get().getSender().getMainHandItem();
                if (stack.getItem() instanceof PaintBucketItem) {
                    stack.getOrCreateTag().putInt("color", message.color);
                    stack.getOrCreateTag().putBoolean("isRGBSelected", message.isRGBSelected);
                }
            });
            context.get().setPacketHandled(true);
        }
    }
}
