package io.firetamer.dragonblockbeyond.handlers;

import com.mojang.blaze3d.platform.InputConstants;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.player_gui_module.test_advanced_window.AdvancedWindowTestScreen;
import io.firetamer.dragonblockbeyond._modules.player_gui_module.test_radial_screen.RadialTestScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Keybinds are client side only. From what I understand, packets are required to do server side stuff with keybinds
 */
public class KeyBindHandler {
    private KeyBindHandler() {}

    public static KeyMapping openRadialSkillMenu;
    public static KeyMapping openPlayerMenu;

    public static void init() {
        /* Single Press Keybinds */
        openPlayerMenu = registerKey("open_player_gui_key", InputConstants.KEY_NUMPAD2, KeyMapping.CATEGORY_INTERFACE);

        /* Continuous Press Keybinds */
        openRadialSkillMenu = registerKey("open_skill_gui_key", InputConstants.KEY_NUMPAD1, KeyMapping.CATEGORY_INTERFACE);

    }


    private static KeyMapping registerKey(String name, int keycode, String category) {
        final var key = new KeyMapping("key." + DragonBlockBeyond.MOD_ID + "." + name, keycode, category);

        ClientRegistry.registerKeyBinding(key);
        return key;
    }


    /**
     * This is for defining what a key does on press
     * This isn't in the event handler because this will likely have a lot of if statements added overtime and the content is only for keybinds
     */
    @Mod.EventBusSubscriber(modid = DragonBlockBeyond.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class ForgeBusClientEvents {

        @SubscribeEvent
        public static void clientTick(TickEvent.ClientTickEvent event) {
            final var player = Minecraft.getInstance().player;

            //keybind.isDown() should be a continuous action rather than a single action no matter how long the keybind is pressed
            if (KeyBindHandler.openRadialSkillMenu.consumeClick()) {
                Minecraft.getInstance().setScreen(new RadialTestScreen());
            }

            if (KeyBindHandler.openPlayerMenu.consumeClick()) {
                Minecraft.getInstance().setScreen(new AdvancedWindowTestScreen());
            }
        }
    }
}
