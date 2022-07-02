package io.firetamer.dragonblockbeyond.race.client.event;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, value = Dist.CLIENT)
public class RaceRenderEvents {
    private static float partialTick;

    public static float getPlayerRenderPartialTick() {
        return partialTick;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        partialTick = event.getPartialTick();
    }
}
