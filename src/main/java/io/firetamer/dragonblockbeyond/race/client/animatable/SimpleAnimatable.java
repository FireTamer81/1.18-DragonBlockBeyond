package io.firetamer.dragonblockbeyond.race.client.animatable;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class SimpleAnimatable implements IAnimatable, IHasGeoRenderer {
    private final AnimationFactory animationFactory = new AnimationFactory(this);
    private IGeoRenderer<?> geoRenderer;

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", getTransitionLengthTicks(), this::animationEvent));
    }

    protected float getTransitionLengthTicks() {
        return 0;
    }

    protected <E extends IAnimatable> PlayState animationEvent(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

    @Override
    public void setGeoRenderer(IGeoRenderer<?> renderer) {
        this.geoRenderer = renderer;
    }

    @Nullable
    @Override
    public IGeoRenderer<?> getGeoRenderer() {
        return geoRenderer;
    }
}
