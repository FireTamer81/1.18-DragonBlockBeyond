package io.firetamer.dragonblockbeyond.race.client.animatable;

import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public interface IHasGeoRenderer {
    IGeoRenderer<?> getGeoRenderer();

    void setGeoRenderer(IGeoRenderer<?> geoRenderer);
}
