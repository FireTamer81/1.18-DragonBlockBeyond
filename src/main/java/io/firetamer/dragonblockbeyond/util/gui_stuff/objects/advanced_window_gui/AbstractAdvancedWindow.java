package io.firetamer.dragonblockbeyond.util.gui_stuff.objects.advanced_window_gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.util.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.gui_stuff.GuiDrawingContext;
import io.firetamer.dragonblockbeyond.util.gui_stuff.objects.radial_menu.AbstractRadialMenu;
import io.firetamer.dragonblockbeyond.util.library_candidates.FireLibColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DragonBlockBeyond.MOD_ID)
public abstract class AbstractAdvancedWindow extends Screen {
    private List<IContentBox> items;

    public AbstractAdvancedWindow(List<IContentBox> itemsIn) {
        super(new TextComponent(""));

        this.items = itemsIn;

        Minecraft mc = Minecraft.getInstance();
        MinecraftForge.EVENT_BUS.register(this);
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    /**
     * Abstract method that determines if the Advanced Window GUI pauses the world (when playing single-player)
     */
    @Override
    public abstract boolean isPauseScreen();


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    /** GUI Behaviour Methods **/
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    @Override
    public void onClose() { super.onClose(); }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    /** Util Methods **/
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    @NotNull
    private GuiDrawingContext createDrawingContext(PoseStack poseStack, int width, int height, float x, float y, float z) {
        return new GuiDrawingContext(poseStack, width, height, x, y, z, font, itemRenderer);
    }

    private void iterateSubMenus(Consumer<IContentBox> consumer) {
        int numItems = items.size();

        for (int i = 0; i < numItems; i++) {
            IContentBox item = items.get(i);
            consumer.accept(item);
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    /** Rendering Methods **/
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    private void drawContentBoxes(PoseStack poseStack, int x, int y, float z, int width, int height) {
        iterateSubMenus(item -> {
            item.draw(createDrawingContext(poseStack, width, height, x, y, z));
        });
    }

    private void drawBackground(PoseStack poseStack, float x, float y, float z) {
        GUIHelper.fillAreaWithColor(poseStack, 0, 0, width, height, 0, new FireLibColor(50, 50, 50, 100).getRGBA());
    }

    private void draw(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        int x = 0;
        int y = 0;
        float z = 0;

        poseStack.pushPose();
        poseStack.translate(0, 0, 0);

        drawBackground(poseStack, x, y, z);

        poseStack.popPose();

        //if (isReady()) {content...} for animations
        drawContentBoxes(poseStack, x, y, z, width, height);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);

        draw(poseStack, partialTicks, mouseX, mouseY);
    }

    /**
     * Renders the mouse
     */
    @SubscribeEvent
    public static void overlayEvent(RenderGameOverlayEvent.PreLayer event) {
        if (event.getOverlay() != ForgeIngameGui.CROSSHAIR_ELEMENT)
            return;

        if (Minecraft.getInstance().screen instanceof AbstractRadialMenu) {
            event.setCanceled(true);
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

}
