package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.V2_window_gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.handlers.TextureHandler;
import io.firetamer.dragonblockbeyond.util.library_candidates.FireLibColor;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GuiDrawingContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DragonBlockBeyond.MOD_ID)
public abstract class V2_AbstractWindow extends Screen {
    Minecraft mc;
    private List<V2_WindowButton> buttons;

    protected V2_AbstractWindow() {
        super(new TextComponent(""));

        MinecraftForge.EVENT_BUS.register(this);
        mc = Minecraft.getInstance();
    }


    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


    @Override
    public abstract boolean isPauseScreen();

    public abstract int getBackgroundColor();

    public abstract void onOpen();

    @Override
    public void onClose() { super.onClose(); };


    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    /**
     * This method is where the "structure" of the GUI is defined (What elements of children of others and the properties of elements).
     * When making your implementation of this class (extending it to make a new GUI), Override this method and create your own "structure".
     */
    public static WindowNode getWindowStructure() {
        //ContentBox Stuff
        V2_ContentBoxBehaviour.BasicBoxProperties rootBox =
                new V2_ContentBoxBehaviour.BasicBoxProperties()
                        .position(V2_ContentBoxBehaviour.InteriorContentBoxPosition.TOP_RIGHT)
                        .borderFont(TextureHandler.BORDER_1)
                        .backgroundColor(new FireLibColor(255, 0, 0))
                        .widthPercentageOfParent(0.5f)
                        .heightPercentageOfParent(0.5f);
        V2_ContentBoxBehaviour.BasicBoxProperties simpleBox =
                new V2_ContentBoxBehaviour.BasicBoxProperties()
                        .position(V2_ContentBoxBehaviour.InteriorContentBoxPosition.TOP_RIGHT)
                        .backgroundColor(new FireLibColor(0, 200, 100))
                        .widthPercentageOfParent(0.5f)
                        .heightPercentageOfParent(0.5f);
        V2_ContentBoxBehaviour.BasicBoxProperties interiorTopRight_Border1_Red_50w_50h =
                new V2_ContentBoxBehaviour.BasicBoxProperties()
                        .position(V2_ContentBoxBehaviour.InteriorContentBoxPosition.TOP_RIGHT)
                        .borderFont(TextureHandler.BORDER_1)
                        .backgroundColor(new FireLibColor(255, 0, 0))
                        .widthPercentageOfParent(0.5f)
                        .heightPercentageOfParent(0.5f);
        V2_ContentBoxBehaviour.BasicBoxProperties exteriorBottomLeft_Border2_Blue_50w_50h =
                new V2_ContentBoxBehaviour.BasicBoxProperties()
                        .exteriorPosition(V2_ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_BOTTOM)
                        .borderFont(TextureHandler.BORDER_2)
                        .backgroundColor(new FireLibColor(0, 0, 255))
                        .widthPercentageOfParent(0.5f)
                        .heightPercentageOfParent(0.5f);

        //Button Stuff
        V2_ContentBoxBehaviour.BasicButtonProperties basicButtonProperties = new V2_ContentBoxBehaviour.BasicButtonProperties()
                .exteriorPosition(V2_ContentBoxBehaviour.ExteriorContentBoxPosition.LEFT_TOP)
                .borderFont(TextureHandler.BORDER_1)
                .backgroundColor(new FireLibColor(0, 0, 255))
                .hoverColor(new FireLibColor(255, 0, 0))
                .widthPercentageOfParent(0.5f)
                .heightPercentageOfParent(0.3f);
        V2_WindowButton basicButton = new V2_WindowButton(basicButtonProperties) {
            @Override
            public void click() {
                final var player = Minecraft.getInstance().player;
                final var level = Minecraft.getInstance().level;
                level.playSound(player, player, SoundEvents.UI_BUTTON_CLICK, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
                player.displayClientMessage(new TextComponent("Text Button Works"), false);
            }
        };


        /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


        WindowNode root = new WindowNode(createContentBoxNodeData("root", createContentBox(rootBox))); {
            WindowNode leaf1 = root.addChild(createContentBoxNodeData("leaf1", createContentBox(exteriorBottomLeft_Border2_Blue_50w_50h)));
            WindowNode leaf2 = root.addChild(createContentBoxNodeData("leaf2", createContentBox(interiorTopRight_Border1_Red_50w_50h))); {
                WindowNode leaf1A = leaf2.addChild(createContentBoxNodeData("leaf1A", createContentBox(simpleBox)));
            }
            WindowNode button1 = root.addChild(createButtonNodeData("button1", basicButton));
        }


        /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


        return root;
    }

    public static WindowNodeData createContentBoxNodeData(String name, V2_ContentBox contentBoxObject) {
        return new WindowNodeData(name, contentBoxObject);
    }
    public static WindowNodeData createButtonNodeData(String name, V2_WindowButton buttonObject) {
        return new WindowNodeData(name, buttonObject);
    }

    public static V2_ContentBox createContentBox(V2_ContentBoxBehaviour.BasicBoxProperties properties) {
        return new V2_ContentBox(properties);
    }
    public static V2_WindowButton createBasicButton(V2_ContentBoxBehaviour.BasicButtonProperties properties) {
        return new V2_WindowButton(properties);
    }


    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


    @NotNull
    private GuiDrawingContext createDrawingContext(PoseStack poseStack, int width, int height, float x, float y, float z) {
        return new GuiDrawingContext(poseStack, width, height, x, y, z, font, itemRenderer);
    }

    private void drawContentBoxElements(PoseStack poseStack, int windowOriginX, int windowOriginY, float windowOriginZ, int windowWidth, int windowHeight) {
        WindowNode windowRoot = getWindowStructure();

        for(WindowNode node : windowRoot) {
            if (node.isRoot()) {
                if (!node.data.isObjectButton()) {
                    V2_ContentBox contentBox = node.data.getContentBoxObject();
                    contentBox.draw(createDrawingContext(poseStack, windowWidth, windowHeight, windowOriginX, windowOriginY, windowOriginZ));
                }
            } else {
                if (!node.data.isObjectButton()) {
                    V2_ContentBox parentContentBox = node.parent.data.getContentBoxObject();
                    V2_ContentBox contentBox = node.data.getContentBoxObject();

                    contentBox.draw(createDrawingContext(poseStack, parentContentBox.contentBoxWidth, parentContentBox.contentBoxHeight,
                            parentContentBox.topLeftXPos, parentContentBox.topLeftYPos, windowOriginZ));
                }
            }
        }
    }
    private void drawButtonElements(PoseStack poseStack, int windowOriginX, int windowOriginY, float windowOriginZ, int windowWidth, int windowHeight) {
        WindowNode windowRoot = getWindowStructure();

        for(WindowNode node : windowRoot) {
            if (node.isRoot()) {
                if (node.data.isObjectButton()) {
                    V2_WindowButton buttonNode = (V2_WindowButton)node.data.getContentBoxObject();
                    buttonNode.draw(createDrawingContext(poseStack, windowWidth, windowHeight, windowOriginX, windowOriginY, windowOriginZ));
                    //buttons.add(buttonNode);
                }
            } else {
                if (node.data.isObjectButton()) {
                    /** This will need some editing so a buttons parent isn't limited to basic ContentBoxes... I think **/
                    V2_ContentBox parentContentBox = node.parent.data.getContentBoxObject();
                    V2_WindowButton buttonNode = (V2_WindowButton)node.data.getContentBoxObject();

                    buttonNode.draw(createDrawingContext(poseStack, parentContentBox.contentBoxWidth, parentContentBox.contentBoxHeight,
                            parentContentBox.topLeftXPos, parentContentBox.topLeftYPos, windowOriginZ));
                    //buttons.add(buttonNode);
                }
            }
        }
    }

    private void drawBackground(PoseStack poseStack) {
        GUIHelper.fillAreaWithColor(poseStack, 0, 0, width, height, 0, getBackgroundColor());
    }

    private void draw(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        processMouse(mouseX, mouseY);

        int topLeftX = 0;
        int topLeftY = 0;
        int originZ = 0;
        int windowWidth = this.width;
        int windowHeight = this.height;

        poseStack.pushPose();
        poseStack.translate(0, 0, 0);

        drawBackground(poseStack);

        poseStack.popPose();

        drawContentBoxElements(poseStack, topLeftX, topLeftY, originZ, windowWidth, windowHeight);
        drawButtonElements(poseStack, topLeftX, topLeftY, originZ, windowWidth, windowHeight);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);

        draw(poseStack, partialTicks, mouseX, mouseY);
    }


    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


    private void processMouse(int mouseX, int mouseY) {
        WindowNode windowRoot = getWindowStructure();

        for(WindowNode node : windowRoot) {
            if (node.data.isObjectButton()) {
                V2_WindowButton buttonObject = (V2_WindowButton)node.data.getContentBoxObject();

                if (buttonObject.isWithinHoverArea(mouseX, mouseY)) {
                    buttonObject.setHoverState(true);
                } else {
                    buttonObject.setHoverState(false);
                }
            }
        }

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
            V2_WindowButton item = getHoveredItem();

            item.click();
        }
    }

    @SubscribeEvent
    public void onMouseRelease(ScreenEvent.MouseButtonReleased.Pre event) {
        processClick(true, event.getButton());
    }

    /**
     * Returns the hovered button.
     */
    public V2_WindowButton getHoveredItem() {
        WindowNode windowRoot = getWindowStructure();

        for(WindowNode node : windowRoot) {
            if (node.data.isObjectButton()) {
                V2_WindowButton buttonObject = (V2_WindowButton)node.data.getContentBoxObject();

                if (buttonObject.isHovered()) {
                    return buttonObject;
                }
            }
        }

        return null;
    }
}
