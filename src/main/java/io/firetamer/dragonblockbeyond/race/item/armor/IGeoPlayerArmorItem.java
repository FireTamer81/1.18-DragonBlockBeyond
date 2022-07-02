package io.firetamer.dragonblockbeyond.race.item.armor;

import io.firetamer.dragonblockbeyond.race.client.animatable.IGeoArmorProvider;
import net.minecraft.client.CameraType;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

import java.util.List;

public interface IGeoPlayerArmorItem {
    @Nullable
    <T extends IAnimatable> GeoModel getArmorModel(EquipmentSlot slot, ItemStack itemStack, CameraType cameraType, AbstractClientPlayer player, T animatable);

    /**
     * @return A list of root bones to gather sub-bones to merge with the main model.
     * Empty = all top level bones.
     * Null = default back to the {@link IGeoArmorProvider}.
     * @see IGeoArmorProvider#getRootArmorBones(EquipmentSlot, ItemStack, CameraType, AbstractClientPlayer, IAnimatable)
     */
    @Nullable
    default <T extends IAnimatable> List<String> getRootArmorBones(EquipmentSlot slot, ItemStack itemStack, CameraType cameraType, AbstractClientPlayer player, T animatable) {
        return null;
    }

    @Nullable
    <T extends IAnimatable> ResourceLocation getTextureLocation(EquipmentSlot slot, ItemStack itemStack, CameraType cameraType, AbstractClientPlayer player, T animatable);
}
