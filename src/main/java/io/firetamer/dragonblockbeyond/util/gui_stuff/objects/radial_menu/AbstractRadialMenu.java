package io.firetamer.dragonblockbeyond.util.gui_stuff.objects.radial_menu;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.util.gui_stuff.GuiDrawingContext;
import io.firetamer.dragonblockbeyond.util.gui_stuff.objects.radial_menu.buttons.IRadialMenuButton;
import io.firetamer.dragonblockbeyond.util.gui_stuff.objects.radial_menu.buttons.IRadialMenuCategoryButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DragonBlockBeyond.MOD_ID)
public abstract class AbstractRadialMenu extends Screen {

    private enum MenuState {
        INITIALIZING,
        OPENING,
        NORMAL,
        CLOSING,
        CLOSED
    }

    private static final float PRECISION = 2.5f / 360.0f;
    private static final double TWO_PI = 2.0 * Math.PI;
    private AbstractRadialMenu.MenuState state = AbstractRadialMenu.MenuState.INITIALIZING;
    public double startAnimation;
    public float animationProgress;
    public float radiusInterior;
    public float radiusExterior;
    public float itemRadius;
    public float animationTop;

    private List<IRadialMenuButton> items;
    private List<IRadialMenuButton> visibleItems;
    private int maxPages;
    public static int currentPage;

    private Component radialHeaderText;
    private Component radialFooterText;

    protected AbstractRadialMenu(List<IRadialMenuButton> items, Component headerTextIn, Component footerTextIn) {
        super(new TextComponent("")); //The super method that adds a screen title is useless, but need to be there.
        this.changeButtons(items);

        Minecraft minecraft = Minecraft.getInstance();
        this.startAnimation = minecraft.level.getGameTime() + (double) minecraft.getFrameTime();
        MinecraftForge.EVENT_BUS.register(this);

        this.radialHeaderText = headerTextIn;
        this.radialFooterText = footerTextIn;
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    /**
     * For instance, if the "Activation" is a keybind, always returning true means the keybind can be release and the menu will stay open.
     * Otherwise, you can use a return statement like --- return KeyBindsUtil.isKeyDown(KeyBindHandler.openRadialSkillMenu); --- to open and close the menu based on whether the keybind is held down
     */
    public abstract boolean shouldMenuStayOpen();

    /**
     * Determines whether the title of the currently hovered button should be drawn in the center of the radial.
     * This specific method turns it off for all buttons, but each button class will have a similar method for just their instance.
     */
    public abstract boolean shouldDrawHoveredButtonTitle();

    /**
     * Prevent the mouse from leaving the Radials area (Has weird issue with the left side of the radial.)
     */
    public abstract boolean shouldClipMouseToRadial();

    /**
     * Does the radial menu allow click outside the circle. (If ShouldClipMouseToCircle() = true, this value can be either or.)
     */
    public abstract boolean allowClickOutsideBounds();

    /**
     * Gets the radial size for use in render methods. 1.0f is a good size for most applications
     */
    public abstract float getRadialSize(); //I will use 2.5f for my menu

    /**
     * Gets the max amount of items per Radial page. Larger values only work well with buttons rendering itemstacks or sprites
     */
    public abstract int getMaxItemsPerPage();

    /**
     * Gets the int code for the radial color (supports transparency)
     */
    public abstract int getRadialColor();

    /**
     * Gets the color that will be applied to Radial buttons that are being hovered by the mouse
     */
    public abstract int getRadialButtonHoverColor();

    /**
     * Determines the length of the open animation.
     *
     * @return the length of the open animation.
     */
    public abstract float getOpenAnimationLength();

    /**
     * Determines whether the world pauses when this menu is open (only counts in singleplayer non-LAN worlds)
     */
    @Override
    public abstract boolean isPauseScreen();


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    /**
     * Override to draw a header above the radial.
     *
     * @param width     the width.
     * @param y         the y point to draw from.
     * @param radiusOut the outer radius.
     */
    public void drawHeader(PoseStack poseStack, float width, float y, float radiusOut) {
        font.drawShadow(poseStack, this.radialHeaderText, (width - font.width(this.radialHeaderText)) / 2.0f, y, 0xFFFFFFFF);
    }

    /**
     * Override to change the footer.
     *
     * @param width     the width.
     * @param y         the y point to draw from.
     * @param radiusOut the outer radius.
     */
    public void drawFooter(PoseStack poseStack, float width, float y, float radiusOut) {
        font.drawShadow(poseStack, this.radialFooterText, (width - font.width(this.radialFooterText)) / 2.0f, y, 0xFFFFFFFF);
    }

    /**
     * Override to draw extras on the screen.
     *
     * @param radiusOut the outer radius.
     */
    public void drawExtras(PoseStack poseStack, float radiusOut) { }

    /**
     * Returns whether the radial is closed or not.
     *
     * @return true if closed.
     */
    public boolean isClosed() { return state == AbstractRadialMenu.MenuState.CLOSED; }

    /**
     * Returns whether the radial is ready to do mouse and button stuff (basically, when the animations are done).
     *
     * @return true if ready.
     */
    public boolean isReady() { return state == AbstractRadialMenu.MenuState.NORMAL; }

    @Override
    public void onClose() { super.onClose(); }

    @Override
    public void tick() {
        super.tick();
        if (state == AbstractRadialMenu.MenuState.INITIALIZING) {
            startAnimation = minecraft.level.getGameTime() + (double) minecraft.getFrameTime();
            state = AbstractRadialMenu.MenuState.OPENING;
            animationProgress = 0;
        }

        if (isClosed()) {
            Minecraft.getInstance().setScreen(null);
        }

        if (!isReady()) {
            return;
        }

        if (!shouldMenuStayOpen()) {
            processClick(false, 0);
        }
    }

    /**
     * Closes the radial.
     */
    public void close() {
        state = AbstractRadialMenu.MenuState.CLOSING;
        startAnimation = minecraft.level.getGameTime() + (double) minecraft.getFrameTime();
        animationProgress = 1.0f;
        setHovered(-1);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        draw(poseStack, partialTicks, mouseX, mouseY);
    }

    private void updateAnimationState(float partialTicks) {
        float openAnimation = 0;

        switch (state) {
            case OPENING:
                openAnimation = (float) ((minecraft.level.getGameTime() + partialTicks - startAnimation) / getOpenAnimationLength());
                if (openAnimation >= 1.0 || visibleItems.size() == 0) {
                    openAnimation = 1;
                    state = AbstractRadialMenu.MenuState.NORMAL;
                }
                break;
            case CLOSING:
                openAnimation = 1 - (float) ((minecraft.level.getGameTime() + partialTicks - startAnimation) / getOpenAnimationLength());
                if (openAnimation <= 0 || visibleItems.size() == 0) {
                    openAnimation = 0;
                    state = AbstractRadialMenu.MenuState.CLOSED;
                }
                break;
        }

        animationProgress = openAnimation;
    }

    @NotNull
    private GuiDrawingContext createDrawingContext(PoseStack poseStack, int width, int height, float x, float y, float z) {
        return new GuiDrawingContext(poseStack, width, height, x, y, z, font, itemRenderer);
    }

    private void iterateVisible(TriConsumer<IRadialMenuButton, Float, Float> consumer) {
        int numItems = visibleItems.size();
        for (int i = 0; i < numItems; i++) {
            float s = (float) getAngleFor(i - 0.5, numItems);
            float e = (float) getAngleFor(i + 0.5, numItems);

            IRadialMenuButton item = visibleItems.get(i);
            consumer.accept(item, s, e);
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    private void drawBackground(PoseStack poseStack, float x, float y, float z, float radiusIn, float radiusOut) {
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        if (!visibleItems.isEmpty()) {
            iterateVisible((item, s, e) -> {
                int color = item.isHovered() ? getRadialButtonHoverColor() : getRadialColor();
                drawPieArc(buffer, x, y, z, radiusIn, radiusOut, s, e, color);
            });
        } else {
            float s = (float) getAngleFor(0 - 0.5, 1);
            float e = (float) getAngleFor(0 + 0.5, 1);
            drawPieArc(buffer, x, y, z, radiusIn, radiusOut, s, e, getRadialColor());
        }

        tessellator.end();

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private void drawButtons(PoseStack poseStack, int x, int y, float z, int width, int height) {
        iterateVisible((item, s, e) -> {
            float middle = (s + e) * 0.5f;
            float posX = x + itemRadius * (float) Math.cos(middle);
            float posY = y + itemRadius * (float) Math.sin(middle);

            item.draw(createDrawingContext(poseStack, width, height, posX, posY, z));
        });
    }

    private double getAngleFor(double i, int numItems) {
        if (numItems == 0)
            return 0;
        double angle = ((i / numItems) + 0.25) * TWO_PI + Math.PI;
        return angle;
    }

    private void drawPieArc(BufferBuilder buffer, float x, float y, float z, float radiusIn, float radiusOut, float startAngle, float endAngle, int color) {
        float angle = endAngle - startAngle;
        int sections = Math.max(1, Mth.ceil(angle / PRECISION));

        angle = endAngle - startAngle;

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        int a = (color >> 24) & 0xFF;

        for (int i = 0; i < sections; i++) {
            float angle1 = startAngle + (i / (float) sections) * angle;
            float angle2 = startAngle + ((i + 1) / (float) sections) * angle;

            float pos1InX = x + radiusIn * (float) Math.cos(angle1);
            float pos1InY = y + radiusIn * (float) Math.sin(angle1);
            float pos1OutX = x + radiusOut * (float) Math.cos(angle1);
            float pos1OutY = y + radiusOut * (float) Math.sin(angle1);
            float pos2OutX = x + radiusOut * (float) Math.cos(angle2);
            float pos2OutY = y + radiusOut * (float) Math.sin(angle2);
            float pos2InX = x + radiusIn * (float) Math.cos(angle2);
            float pos2InY = y + radiusIn * (float) Math.sin(angle2);

            buffer.vertex(pos1OutX, pos1OutY, z).color(r, g, b, a).endVertex();
            buffer.vertex(pos1InX, pos1InY, z).color(r, g, b, a).endVertex();
            buffer.vertex(pos2InX, pos2InY, z).color(r, g, b, a).endVertex();
            buffer.vertex(pos2OutX, pos2OutY, z).color(r, g, b, a).endVertex();
        }
    }

    private void draw(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        updateAnimationState(partialTicks);

        if (isClosed())
            return;

        if (isReady())
            processMouse(mouseX, mouseY);

        boolean animated = state == AbstractRadialMenu.MenuState.OPENING || state == AbstractRadialMenu.MenuState.CLOSING;
        radiusInterior = animated ? Math.max(0.1f, 30 * animationProgress) : 30;
        radiusInterior = radiusInterior * getRadialSize();
        radiusExterior = radiusInterior * 2f;
        itemRadius = (radiusInterior + radiusExterior) * 0.5f;
        animationTop = animated ? (1 - animationProgress) * height / 2.0f : 0;

        int x = width / 2;
        int y = height / 2;
        float z = 0;

        poseStack.pushPose();
        poseStack.translate(0, animationTop, 0);

        drawBackground(poseStack, x, y, z, radiusInterior, radiusExterior);

        poseStack.popPose();

        if (isReady()) {
            drawButtons(poseStack, x, y, z, width, height);

            Component currentCenterText = null;
            int centerTextColor = 0xFFFFFFFF;
            for (IRadialMenuButton item : visibleItems) {
                if (item.isHovered()) {
                    if (item.getRadialCenterTextForHoveredButton() != null) {
                        currentCenterText = item.getRadialCenterTextForHoveredButton();
                        centerTextColor = item.getCenterTextColor();
                    }
                    break;
                }
            }

            if (currentCenterText != null && shouldDrawHoveredButtonTitle()) {
                String text = currentCenterText.getString();
                font.drawShadow(poseStack, text, (width - font.width(text)) / 2.0f, (height - font.lineHeight) / 2.0f, centerTextColor);
            }

            drawFooter(poseStack, width, height / 2.0f + radiusExterior * 1.05f, radiusExterior);
            drawHeader(poseStack, width, height / 2.0f - radiusExterior * 1.05f - font.lineHeight, radiusExterior);
            drawExtras(poseStack, radiusExterior);
        }
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    @SubscribeEvent
    public void onMouseScroll(ScreenEvent.MouseScrollEvent.Pre event) {
        if (Minecraft.getInstance().screen instanceof AbstractRadialMenu) {
            if (!isScrollInverted()) {
                if (event.getScrollDelta() < 0) {
                    nextPage();
                } else if (event.getScrollDelta() > 0) {
                    prevPage();
                }
            } else {
                if (event.getScrollDelta() > 0) {
                    nextPage();
                } else if (event.getScrollDelta() < 0) {
                    prevPage();
                }
            }
        }
    }

    public boolean isScrollInverted() { return false; }

    private void processMouse(int mouseX, int mouseY) {

        if (!isReady())
            return;

        int numItems = visibleItems.size();

        int x = width / 2;
        int y = height / 2;
        double a = Math.atan2(mouseY - y, mouseX - x);
        double d = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2));
        if (numItems > 0) {
            double s0 = getAngleFor(0 - 0.5, numItems);
            double s1 = getAngleFor(numItems - 0.5, numItems);
            while (a < s0) {
                a += TWO_PI;
            }
            while (a >= s1) {
                a -= TWO_PI;
            }
        }

        int hovered = -1;
        for (int i = 0; i < numItems; i++) {
            float s = (float) getAngleFor(i - 0.5, numItems);
            float e = (float) getAngleFor(i + 0.5, numItems);

            if (a >= s && a < e && d >= radiusInterior && (d < radiusExterior || shouldClipMouseToRadial() || allowClickOutsideBounds())) {
                hovered = i;
                break;
            }
        }

        setHovered(hovered);

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
    }

    /**
     * Override to change what it does when clicked outside the radial.
     */
    public void onClickOutside() { close(); }

    private void processClick(boolean triggeredByMouse, int button) {
        if (state == AbstractRadialMenu.MenuState.NORMAL) {
            if (getHoveredItem() != null) {
                if (getHoveredItem() instanceof IRadialMenuCategoryButton) {
                    IRadialMenuCategoryButton category = (IRadialMenuCategoryButton) getHoveredItem();

                    if (button == 1) {
                        List<IRadialMenuButton> items = category.getCategoryObjects();
                        if (!items.isEmpty()) {
                            if (items.size() == 1 && category.skipMenuIfSingleContextItem()) {
                                items.get(0).click();
                                if (items.get(0).shouldCloseRadialOnClick())
                                    close();
                            } else
                                changeButtons(items);
                        }
                    } else {
                        List<IRadialMenuButton> items = category.getCategoryObjects();
                        if (items.isEmpty() && category.shouldRadialCloseIfCategoryIsEmpty())
                            close();
                        else
                            changeButtons(items);

                    }
                } else {
                    IRadialMenuButton item = getHoveredItem();

                    if (button == 1) {
                        List<IRadialMenuButton> items = item.getContextItems();
                        if (!items.isEmpty()) {
                            if (items.size() == 1 && item.skipMenuIfSingleContextItem()) {
                                items.get(0).click();
                                if (items.get(0).shouldCloseRadialOnClick())
                                    close();
                            } else
                                changeButtons(items);
                        }
                    } else {
                        item.click();
                        if (item.shouldCloseRadialOnClick())
                            close();
                    }
                }
            } else {
                onClickOutside();
            }
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        processClick(true, state);
        return super.mouseReleased(mouseX, mouseY, state);
    }

    /**
     * Returns the hovered button.
     */
    public IRadialMenuButton getHoveredItem() {
        for (IRadialMenuButton item : visibleItems) {
            if (item.isHovered())
                return item;
        }
        return null;
    }

    /**
     * Sets the hovered button
     */
    private void setHovered(int buttonId) {
        for (int i = 0; i < visibleItems.size(); i++) {
            visibleItems.get(i).setHovered(i == buttonId);
        }
    }

    //TODO See if this method can render custom icons for the computer mouse. Might be a neat future addition to have a custom mouse thingy.
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


    private List<IRadialMenuButton> getButtonsForPage(int page) {
        return items.stream()
                .skip((page - 1) * getMaxItemsPerPage())
                .limit(getMaxItemsPerPage())
                .collect(Collectors.toList());
    }

    private void prevPage() {
        if (currentPage > 1) {
            currentPage--;
            visibleItems = getButtonsForPage(currentPage);
        }
    }

    private void nextPage() {
        if (currentPage < maxPages) {
            currentPage++;
            visibleItems = getButtonsForPage(currentPage);
        }
    }

    private void changeButtons(List<IRadialMenuButton> items) {
        this.items = items;
        maxPages = (int) Math.ceil(items.size() / (double) getMaxItemsPerPage());
        currentPage = 1;
        this.visibleItems = getButtonsForPage(currentPage);
    }
}
