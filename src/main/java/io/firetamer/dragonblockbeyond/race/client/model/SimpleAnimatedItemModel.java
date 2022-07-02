package io.firetamer.dragonblockbeyond.race.client.model;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.race.item.animated.AnimatedItemProperties;
import io.firetamer.dragonblockbeyond.race.item.animated.IAnimatedItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleAnimatedItemModel<T extends Item & IAnimatable & IAnimatedItem> extends AnimatedGeoModel<T> {
    protected static final ResourceLocation NONE_ANIMATION = new ResourceLocation(DragonBlockBeyond.MOD_ID, "animations/none.animation.json");
    protected final Map<ResourceLocation, ResourceLocation> geoCache = new HashMap<>();
    protected final Map<ResourceLocation, ResourceLocation> animationCache = new HashMap<>();
    protected final Map<ResourceLocation, ResourceLocation> textureCache = new HashMap<>();
    protected final Map<ResourceLocation, List<ResourceLocation>> framesCache = new HashMap<>();

    @Override
    public ResourceLocation getModelLocation(T object) {
        return geoCache.computeIfAbsent(object.getRegistryName(), k -> new ResourceLocation(k.getNamespace(), "geo/" + k.getPath() + ".geo.json"));
    }

    @Override
    public ResourceLocation getTextureLocation(T object) {
        if (object.getAnimatedToolProperties().isTextureAnimated()) {
            AnimatedItemProperties properties = object.getAnimatedToolProperties();
            int frameCount = properties.getFrames();
            List<ResourceLocation> frames = framesCache.computeIfAbsent(object.getRegistryName(), k -> {
                List<ResourceLocation> list = new ArrayList<>();

                for (int frame = 0; frame < frameCount; frame++) {
                    list.add(new ResourceLocation(k.getNamespace(), "textures/item/" + k.getPath() + "/" + frame + ".png"));
                }

                return list;
            });

            LocalPlayer player = Minecraft.getInstance().player;
            int frame = player == null ? 0 : (player.tickCount % (properties.getFrames() * properties.getTicksPerFrame())) / properties.getTicksPerFrame();
            return frames.get(frame);
        }

        return textureCache.computeIfAbsent(object.getRegistryName(), k -> new ResourceLocation(k.getNamespace(), "textures/item/" + k.getPath() + ".png"));
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T object) {
        return object.getAnimatedToolProperties().isAnimated()
                ? animationCache.computeIfAbsent(object.getRegistryName(), k -> new ResourceLocation(k.getNamespace(), "animations/" + k.getPath() + ".animation.json"))
                : NONE_ANIMATION;
    }
}