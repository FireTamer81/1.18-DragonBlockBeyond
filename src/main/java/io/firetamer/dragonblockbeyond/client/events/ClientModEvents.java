package io.firetamer.dragonblockbeyond.client.events;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.init.RaceInit;
import io.firetamer.dragonblockbeyond.race.client.animatable.SimpleAnimatable;
import io.firetamer.dragonblockbeyond.race.client.model.SimpleGeoPlayerModel;
import io.firetamer.dragonblockbeyond.race.client.renderer.GeoPlayerRenderer;
import io.firetamer.dragonblockbeyond.race.client.renderer.RaceRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value= Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        RaceRenderers.registerPlayerRaceRenderer(RaceInit.SAIYAN.get(),(context)->
                new GeoPlayerRenderer<>(context,
                        new SimpleGeoPlayerModel<>(DragonBlockBeyond.MOD_ID,"saiyan", new SimpleGeoPlayerModel.Properties<>().armBones("RightArm","LeftArm").legABones("RightLeg").legBBones("LeftLeg3")), new SimpleAnimatable()));
    }
}
