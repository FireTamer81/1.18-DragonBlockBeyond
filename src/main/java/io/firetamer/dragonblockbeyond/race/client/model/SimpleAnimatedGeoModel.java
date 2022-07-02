package io.firetamer.dragonblockbeyond.race.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SimpleAnimatedGeoModel<T extends IAnimatable> extends AnimatedGeoModel<T> {
    protected ResourceLocation animationLocation;
    protected ResourceLocation geoLocation;
    protected ResourceLocation textureLocation;

    public SimpleAnimatedGeoModel(String namespace, String textureType, String name) {
        this(namespace, textureType, name + ".geo.json", name + ".animation.json", name + ".png");
    }

    public SimpleAnimatedGeoModel(String namespace, String textureType, String geoFilename, String animationFilename, String textureFilename) {
        this.geoLocation = new ResourceLocation(namespace, "geo/" + geoFilename);
        this.animationLocation = new ResourceLocation(namespace, "animations/" + animationFilename);
        this.textureLocation = new ResourceLocation(namespace, "textures/" + textureType + "/" + textureFilename);
    }

    @Override
    public ResourceLocation getModelLocation(T entity) {
        return geoLocation;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T entity) {
        return animationLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return textureLocation;
    }
}
