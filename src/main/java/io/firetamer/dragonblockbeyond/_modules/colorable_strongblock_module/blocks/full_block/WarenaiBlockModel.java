package io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.blocks.full_block;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.blocks.properties.WarenaiBlockConditionEnum;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.util.ModelHelper;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.blocks.properties.WarenaiBlockPatternEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation")
public class WarenaiBlockModel implements IDynamicBakedModel {

	public WarenaiBlockModel() {}

	public static final ResourceLocation UNDERLAY_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID,
			"block/warenai_full_block");
	public static final ResourceLocation POLISHED_UNDERLAY_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID,
			"block/warenai_block_polished");
	public static final ResourceLocation LARGE_BRICK_UNDERLAY_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID,
			"block/warenai_block_large_bricks");
	public static final ResourceLocation POLISHED_LARGE_BRICK_UNDERLAY_TEXTURE = new ResourceLocation(
			DragonBlockBeyond.MOD_ID, "block/warenai_large_bricks_polished");
	public static final ResourceLocation SMALL_BRICK_UNDERLAY_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID,
			"block/warenai_block_small_bricks");
	public static final ResourceLocation POLISHED_SMALL_BRICK_UNDERLAY_TEXTURE = new ResourceLocation(
			DragonBlockBeyond.MOD_ID, "block/warenai_small_bricks_polished");

	public static final ResourceLocation SCUFFED_OVERLAY_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID,
			"block/scuffed_texture");
	public static final ResourceLocation CRACKED1_OVERLAY_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID,
			"block/cracked1_texture");
	public static final ResourceLocation CRACKED2_OVERLAY_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID,
			"block/cracked2_texture");
	public static final ResourceLocation CRACKED3_OVERLAY_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID,
			"block/cracked3_texture");
	public static final ResourceLocation CRACKED4_OVERLAY_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID,
			"block/cracked4_texture");

	@Nonnull
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {

		TextureAtlasSprite underlayTexture = getTextureSprite(UNDERLAY_TEXTURE);
		TextureAtlasSprite polishedUnderlayTexture = getTextureSprite(POLISHED_UNDERLAY_TEXTURE);

		if (state.getValue(WarenaiBlock.BLOCK_PATTERN) == WarenaiBlockPatternEnum.LARGE_BRICK) {
			underlayTexture = getTextureSprite(LARGE_BRICK_UNDERLAY_TEXTURE);
			polishedUnderlayTexture = getTextureSprite(POLISHED_LARGE_BRICK_UNDERLAY_TEXTURE);
		} else if (state.getValue(WarenaiBlock.BLOCK_PATTERN) == WarenaiBlockPatternEnum.SMALL_BRICK) {
			underlayTexture = getTextureSprite(SMALL_BRICK_UNDERLAY_TEXTURE);
			polishedUnderlayTexture = getTextureSprite(POLISHED_SMALL_BRICK_UNDERLAY_TEXTURE);
		}

		TextureAtlasSprite scuffed_OverlayTexture = getTextureSprite(SCUFFED_OVERLAY_TEXTURE);
		TextureAtlasSprite c1_OverlayTexture = getTextureSprite(CRACKED1_OVERLAY_TEXTURE);
		TextureAtlasSprite c2_OverlayTexture = getTextureSprite(CRACKED2_OVERLAY_TEXTURE);
		TextureAtlasSprite c3_OverlayTexture = getTextureSprite(CRACKED3_OVERLAY_TEXTURE);
		TextureAtlasSprite c4_OverlayTexture = getTextureSprite(CRACKED4_OVERLAY_TEXTURE);

		List<BakedQuad> allQuads = new ArrayList<>();

		double l = 0;
		double r = 1;

		boolean renderNorth = true;
		boolean renderEast = true;
		boolean renderSouth = true;
		boolean renderWest = true;
		boolean renderUp = true;
		boolean renderDown = true;

		if (state.getValue(WarenaiBlock.BLOCK_CONDITION) == WarenaiBlockConditionEnum.POLISHED) {
			allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, polishedUnderlayTexture, 0,
					renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
		} else if (state.getValue(WarenaiBlock.BLOCK_CONDITION) == WarenaiBlockConditionEnum.NORMAL) {
			allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, underlayTexture, 0,
					renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
		} else if (state.getValue(WarenaiBlock.BLOCK_CONDITION) == WarenaiBlockConditionEnum.SCUFFED) {
			allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, underlayTexture, 0,
					renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
			allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, scuffed_OverlayTexture, 0,
					renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
		} else if (state.getValue(WarenaiBlock.BLOCK_CONDITION) == WarenaiBlockConditionEnum.CRACKED1) {
			allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, underlayTexture, 0,
					renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
			allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, c1_OverlayTexture, 0,
					renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
		} else if (state.getValue(WarenaiBlock.BLOCK_CONDITION) == WarenaiBlockConditionEnum.CRACKED2) {
			allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, underlayTexture, 0,
					renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
			allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, c2_OverlayTexture, 0,
					renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
		} else if (state.getValue(WarenaiBlock.BLOCK_CONDITION) == WarenaiBlockConditionEnum.CRACKED3) {
			allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, underlayTexture, 0,
					renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
			allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, c3_OverlayTexture, 0,
					renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
		} else if (state.getValue(WarenaiBlock.BLOCK_CONDITION) == WarenaiBlockConditionEnum.CRACKED4) {
			allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, underlayTexture, 0,
					renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
			allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, c4_OverlayTexture, 0,
					renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
		}

		return allQuads;
	}

	private TextureAtlasSprite getTextureSprite(ResourceLocation textureResource) {
		return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(textureResource);
	}




	/**
	 * Not much done with these methods
	 */
	@Override
	public boolean useAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() { return false; }

	@Override
	public boolean usesBlockLight() {
		return false;
	}

	@Override
	public boolean isCustomRenderer() { return false; }

	@Override
	public TextureAtlasSprite getParticleIcon() { return getTextureSprite(UNDERLAY_TEXTURE); }

	@Override
	public ItemOverrides getOverrides() { return null; }
}
