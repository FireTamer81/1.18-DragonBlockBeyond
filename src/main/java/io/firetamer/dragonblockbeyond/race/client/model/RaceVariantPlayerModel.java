package io.firetamer.dragonblockbeyond.race.client.model;

import io.firetamer.dragonblockbeyond.race.client.util.ClientUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;

public class RaceVariantPlayerModel<T extends IAnimatable> extends SimpleGeoPlayerModel<T> {
    private final String namespace;
    private final String name;

    public RaceVariantPlayerModel(String namespace, String name) {
        super(namespace, name);
        this.namespace = namespace;
        this.name = name;
    }

    public RaceVariantPlayerModel(String namespace, String name, Properties<?> properties) {
        super(namespace, name, properties);
        this.namespace = namespace;
        this.name = name;
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable, @Nullable AbstractClientPlayer player) {
        return ClientUtil.getVariantTextureLocation(this.namespace, player, this.name);
    }
}
