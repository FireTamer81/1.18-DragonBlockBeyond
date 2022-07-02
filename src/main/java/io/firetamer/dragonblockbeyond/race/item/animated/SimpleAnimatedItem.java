package io.firetamer.dragonblockbeyond.race.item.animated;

import net.minecraft.world.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SimpleAnimatedItem extends Item implements IAnimatable, IAnimatedItem {
    protected final AnimationFactory factory = new AnimationFactory(this);
    protected final AnimatedItemProperties animatedItemProperties;

    public SimpleAnimatedItem(AnimatedItemProperties pProperties) {
        super(pProperties);
        this.animatedItemProperties = pProperties;
    }

    @Override
    public void registerControllers(AnimationData data) {}

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public AnimatedItemProperties getAnimatedToolProperties() {
        return animatedItemProperties;
    }
}
