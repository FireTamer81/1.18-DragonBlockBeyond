package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.advanced_window_gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GuiDrawingContext;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.advanced_window_gui.base.BasicButton;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.advanced_window_gui.base.ContentBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DragonBlockBeyond.MOD_ID)
public abstract class AbstractAdvancedWindow extends Screen {
    private List<ContentBox> elements;
    private List<BasicButton> buttons;

    public AbstractAdvancedWindow(List<ContentBox> contentBoxList, List<BasicButton> buttonsIn) {
        super(new TextComponent(""));

        this.elements = contentBoxList;
        this.buttons = buttonsIn;

        Minecraft mc = Minecraft.getInstance();
        MinecraftForge.EVENT_BUS.register(this);
    }


    /** Abstract Methods **/
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    @Override
    public abstract boolean isPauseScreen();

    public abstract int getBackgroundColor();


    /** GUI Behaviour Methods **/
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    @Override
    public void onClose() { super.onClose(); }


    /** Util Methods **/
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    @NotNull
    private GuiDrawingContext createDrawingContext(PoseStack poseStack, int width, int height, float x, float y, float z) {
        return new GuiDrawingContext(poseStack, width, height, x, y, z, font, itemRenderer);
    }

    private void iterateContentBoxes(Consumer<ContentBox> consumer) {
        int numItems = elements.size();

        for (int i = 0; i < numItems; i++) {
            ContentBox item = elements.get(i);
            consumer.accept(item);
        }
    }
    private void iterateButtons(Consumer<BasicButton> consumer) {
        int numItems = buttons.size();

        for (int i = 0; i < numItems; i++) {
            BasicButton item = buttons.get(i);
            consumer.accept(item);
        }
    }


    /** Rendering Methods **/
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    private void drawButtons(PoseStack poseStack, int x, int y, float z, int width, int height, int mouseX, int mouseY) {
        iterateButtons((item) -> {
            item.draw(createDrawingContext(poseStack, width, height, x, y, z), mouseX, mouseY);
        });
    }
    private void drawContentBoxes(PoseStack poseStack, int x, int y, float z, int width, int height, int mouseX, int mouseY) {
        iterateContentBoxes(item -> {
            item.draw(createDrawingContext(poseStack, width, height, x, y, z), mouseX, mouseY);
        });
    }
    private void drawBackground(PoseStack poseStack, float x, float y, float z) {
        GUIHelper.fillAreaWithColor(poseStack, 0, 0, width, height, 0, getBackgroundColor());
    }

    private void draw(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        processMouse(mouseX, mouseY);

        int x = 0;
        int y = 0;
        float z = 0;

        poseStack.pushPose();
        poseStack.translate(0, 0, 0);

        drawBackground(poseStack, x, y, z);

        poseStack.popPose();

        drawContentBoxes(poseStack, x, y, z, width, height, mouseX, mouseY);
        drawButtons(poseStack, x, y, z, width, height, mouseX, mouseY);
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
        if (event.getOverlay() != ForgeIngameGui.CROSSHAIR_ELEMENT) return;

        if (Minecraft.getInstance().screen instanceof AbstractAdvancedWindow) {
            event.setCanceled(true);
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    @SubscribeEvent
    public void onMouseScroll(ScreenEvent.MouseScrollEvent.Pre event) {
        if (Minecraft.getInstance().screen instanceof AbstractAdvancedWindow) {
            if (!isScrollInverted()) {
                if (event.getScrollDelta() < 0) {
                    //nextPage();
                } else if (event.getScrollDelta() > 0) {
                    //prevPage();
                }
            } else {
                if (event.getScrollDelta() > 0) {
                    //nextPage();
                } else if (event.getScrollDelta() < 0) {
                    //prevPage();
                }
            }
        }
    }

    public boolean isScrollInverted() { return false; }

    private void processMouse(int mouseX, int mouseY) {
        iterateButtons((item) -> {
            if (item.isWithinHoverArea(mouseX, mouseY)) {
                item.setHovered(true);
            } else {
                item.setHovered(false);
            }
        });


        /*
        if (shouldClipMouseToRadial()) {
            Window mainWindow = minecraft.getWindow();

            int windowWidth = mainWindow.getScreenWidth();
            int windowHeight = mainWindow.getScreenHeight();

            double[] xPos = new double[1];
            double[] yPos = new double[1];

            GLFW.glfwGetCursorPos(mainWindow.getWindow(), xPos, yPos);

            double scaledX = xPos[0] - (windowWidth / 2.0f);
            double scaledY = yPos[0] - (windowHeight / 2.0f);

            double distance = Math.sqrt(scaledX * scaledX + scaledY * scaledY);
            double radius = radiusExterior * (windowWidth / (float) width) * 0.975;

            if (distance > radius) {
                double fixedX = scaledX * radius / distance;
                double fixedY = scaledY * radius / distance;

                GLFW.glfwSetCursorPos(mainWindow.getWindow(), (int) (windowWidth / 2 + fixedX), (int) (windowHeight / 2 + fixedY));
            }
        }
        */
    }

    private void processClick(boolean triggeredByMouse, int button) {
        if (getHoveredItem() != null) {
            BasicButton item = getHoveredItem();

            item.click();
        }
    }

    @SubscribeEvent
    public void onMouseRelease(ScreenEvent.MouseReleasedEvent.Pre event) {
        processClick(true, event.getButton());
    }

    /**
     * Returns the hovered button.
     */
    public BasicButton getHoveredItem() {
        for (BasicButton item : buttons) {
            if (item.isHovered())
                return item;
        }
        return null;
    }
}
