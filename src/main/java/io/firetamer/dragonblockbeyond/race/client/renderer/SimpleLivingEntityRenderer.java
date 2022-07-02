package io.firetamer.dragonblockbeyond.race.client.renderer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class SimpleLivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {
    protected final ResourceLocation textureLocation;

    public SimpleLivingEntityRenderer(EntityRendererProvider.Context context, M model, float shadowRadius, ResourceLocation textureLocation) {
        super(context, model, shadowRadius);
        this.textureLocation = textureLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return this.textureLocation;
    }

    @Override
    protected boolean shouldShowName(T entity) {
        return super.shouldShowName(entity) && (entity.shouldShowName() || (entity.hasCustomName() && (entity == this.entityRenderDispatcher.crosshairPickEntity)));
    }
}
