package io.firetamer.dragonblockbeyond.handlers;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.namek_module.NamekModule;
import io.firetamer.dragonblockbeyond.init.CommonObjects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Random;

public class EventHandler {

    @Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class ModBusClientEvents {

        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {

            KeyBindHandler.init();


            /*  RenderTypes  */
            ItemBlockRenderTypes.setRenderLayer(NamekModule.NAMEK_TREE_SAPLING.get(), RenderType.cutoutMipped());

            ItemBlockRenderTypes.setRenderLayer(NamekModule.SHORT_NAMEK_GRASS.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(NamekModule.TALL_NAMEK_GRASS.get(), RenderType.cutoutMipped());

            ItemBlockRenderTypes.setRenderLayer(NamekModule.NAMEK_FLUID_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(NamekModule.NAMEK_FLUID_FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(NamekModule.NAMEK_FLUID_BLOCK.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(NamekModule.NAMEK_SEAGRASS.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(NamekModule.TALL_NAMEK_SEAGRASS.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(NamekModule.NAMEK_KELP_BODY.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(NamekModule.NAMEK_KELP_HEAD.get(), RenderType.cutoutMipped());
        }
    }

    @Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class ForgeBusClientEvents {


        //TODO get this BoneMealEvent working (Grow plants in Namek Water when using Bonemeal)
        /**
         * The purpose fo this event is to recreate the underwater bonemeal effect, but for the custom NamekFluid.
         * I still haven't figured out how to get it to work though.
         */
        @SubscribeEvent
        public static void namekFluidBonemealUse(BonemealEvent event) {
            LocalPlayer player = Minecraft.getInstance().player;
            Level worldIn = player.getCommandSenderWorld();
            HitResult lookingAt = Minecraft.getInstance().hitResult;
            Random rand = new Random();

            if (lookingAt != null && lookingAt.getType() == HitResult.Type.BLOCK) {
                double x = lookingAt.getLocation().x();
                double y = lookingAt.getLocation().y();
                double z = lookingAt.getLocation().z();

                BlockPos blockpos_above_1 = new BlockPos(x, y, z).above();

                //player.displayClientMessage(new TextComponent("Clicked on full block, fluid state above block is: " + fluidstate), true);

                Task:
                for (int i = 0; i < 128; ++i) {
                    BlockPos blockpos1 = blockpos_above_1;

                    for (int j = 0; j < i / 16; ++j) {
                        blockpos1 = blockpos1.offset(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

                        if (worldIn.getBlockState(blockpos1).isCollisionShapeFullBlock(worldIn, blockpos1)) {
                            continue Task;
                        }
                    }

                    BlockPos blockpos1_above = blockpos1.above();
                    BlockState blockstate1 = worldIn.getBlockState(blockpos1);
                    BlockState blockstate2 = worldIn.getBlockState(blockpos1_above);

                    if (blockstate1.equals(NamekModule.NAMEK_FLUID_BLOCK) && blockstate2.equals(NamekModule.NAMEK_FLUID_BLOCK)) {
                        BlockState blockstate3;

                        if (rand.nextInt(4) == 0) {
                            blockstate3 = NamekModule.NAMEK_SEAGRASS.get().defaultBlockState();
                        } else {
                            blockstate3 = NamekModule.NAMEK_SEAGRASS.get().defaultBlockState();
                        }

                        worldIn.setBlockAndUpdate(blockpos1, blockstate3);
                    }
                }
            }
        }

        /**
         * Creates green fog when in Namek Water to reduce visibility
         */
        @SubscribeEvent
        public static void greenFogInNamekWater(EntityViewRenderEvent.FogColors event) {
            LocalPlayer player = Minecraft.getInstance().player;
            double eyeHeight = player.getEyeY() - 1 / 9d;
            FluidState fluidstate = player.level.getFluidState(new BlockPos(player.getX(), eyeHeight, player.getZ()));

            if (fluidstate.getType() == NamekModule.NAMEK_FLUID_FLOWING.get()
                    || fluidstate.getType() == NamekModule.NAMEK_FLUID_SOURCE.get()) {
                event.setBlue(0.09F);
                event.setGreen(0.45F); // As it stands, what I have done is match the ratios shown in the fluid color
                // to the fog color. 255 for the fluid color being 1.0F for the Fog DBBColor.
                event.setRed(0);
            }
        }

        /**
         * Prevents the vanilla water overlay (which gives the view a blue tint instead of green)
         */
        @SubscribeEvent
        public static void cancelVanillaWaterOverlay(RenderBlockOverlayEvent event) {
            @SuppressWarnings("resource")
            LocalPlayer player = Minecraft.getInstance().player;
            double eyeHeight = player.getEyeY() - 1 / 9d;
            FluidState fluidstate = player.level.getFluidState(new BlockPos(player.getX(), eyeHeight, player.getZ()));

            if (fluidstate.getType() == NamekModule.NAMEK_FLUID_FLOWING.get()
                    || fluidstate.getType() == NamekModule.NAMEK_FLUID_SOURCE.get()) {
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }

            }
        }
    }
}
