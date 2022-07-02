package io.firetamer.dragonblockbeyond.race.client.util;

import io.firetamer.dragonblockbeyond.race.capability.RaceHolderAttacher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ClientUtil {
    public static ResourceLocation getVariantTextureLocation(String namespace, @Nullable Player entity, String baseTextureName) {
        String variant = entity == null ? null : RaceHolderAttacher.getCurrentVariantUnwrap(entity);
        return new ResourceLocation(namespace, "textures/entity/" + (variant == null ? "" : variant + "_") + baseTextureName + ".png");
    }


}
