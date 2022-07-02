package io.firetamer.dragonblockbeyond.race.client.renderer;

import io.firetamer.dragonblockbeyond.race.client.model.SimpleAnimatedItemModel;
import io.firetamer.dragonblockbeyond.race.item.animated.IAnimatedItem;
import net.minecraft.world.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GeoToolRenderer<T extends Item & IAnimatable & IAnimatedItem> extends GeoItemRenderer<T> {
    public GeoToolRenderer() {
        super(new SimpleAnimatedItemModel<>());
    }
}