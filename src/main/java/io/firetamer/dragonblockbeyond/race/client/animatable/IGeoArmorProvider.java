package io.firetamer.dragonblockbeyond.race.client.animatable;

import io.firetamer.dragonblockbeyond.race.item.armor.IGeoPlayerArmorItem;
import net.minecraft.client.CameraType;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

import java.util.List;

public interface IGeoArmorProvider<T extends IAnimatable> {
    static <T extends IAnimatable> IGeoArmorProvider<T> getDefaultInstance() {
        return new IGeoArmorProvider<T>() {};
    }

    @Nullable
    default GeoModel getArmorModel(EquipmentSlot slot, ItemStack itemStack, CameraType cameraType, AbstractClientPlayer player, T animatable) {
        if (itemStack.getItem() instanceof IGeoPlayerArmorItem geoPlayerArmorItem)
            return geoPlayerArmorItem.getArmorModel(slot, itemStack, cameraType, player, animatable);

        return null;
    }

    /**
     * @return A list of root bones to gather sub-bones to merge with the main model. Empty = all top level bones.
     */
    @NotNull
    default List<String> getRootArmorBones(EquipmentSlot slot, ItemStack itemStack, CameraType cameraType, AbstractClientPlayer player, T animatable) {
        if (itemStack.getItem() instanceof IGeoPlayerArmorItem geoPlayerArmorItem) {
            List<String> rootArmorBones = geoPlayerArmorItem.getRootArmorBones(slot, itemStack, cameraType, player, animatable);
            if (rootArmorBones != null)
                return rootArmorBones;
        }

        return switch (slot) {
            case HEAD -> List.of("helmet");
            case CHEST -> List.of("chestplate");
            case LEGS -> List.of("leggings");
            case FEET -> List.of("boots");
            default -> throw new IllegalArgumentException("Unknown slot type: " + slot);
        };
    }

    @Nullable
    default ResourceLocation getTextureLocation(EquipmentSlot slot, ItemStack itemStack, CameraType cameraType, AbstractClientPlayer player, T animatable) {
        if (itemStack.getItem() instanceof IGeoPlayerArmorItem)
            return ((IGeoPlayerArmorItem) itemStack.getItem()).getTextureLocation(slot, itemStack, cameraType, player, animatable);

        return null;
    }
}
