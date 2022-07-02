package io.firetamer.dragonblockbeyond.race.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

// Expose default implementations of common IGeoRenderer methods
public class GeoRenderingUtil {
    private static final IGeoRenderer<?> DEFAULT_GEO_RENDERER = new IGeoRenderer<Object>() {
        @Override
        public GeoModelProvider<?> getGeoModelProvider() {
            return null;
        }

        @Override
        public ResourceLocation getTextureLocation(Object instance) {
            return null;
        }
    };

    public static void renderRecursively(GeoBone bone, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int overlay, float red, float green, float blue, float alpha) {
        DEFAULT_GEO_RENDERER.renderRecursively(bone, poseStack, vertexConsumer, packedLight, overlay, red, green, blue, alpha);
    }

    public static void renderCube(GeoCube cube, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int overlay, float red, float green, float blue, float alpha) {
        DEFAULT_GEO_RENDERER.renderCube(cube, poseStack, vertexConsumer, packedLight, overlay, red, green, blue, alpha);
    }
}
