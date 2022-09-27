package io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.V2_window_gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond.handlers.TextureHandler;
import io.firetamer.dragonblockbeyond.util.library_candidates.FireLibColor;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GUIHelper;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.GuiDrawingContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DragonBlockBeyond.MOD_ID)
public abstract class V2_AbstractWindow extends Screen {
    Minecraft mc;

    protected V2_AbstractWindow() {
        super(new TextComponent(""));

        MinecraftForge.EVENT_BUS.register(this);
        mc = Minecraft.getInstance();
    }


    //* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ *//


    @Override
    public abstract boolean isPauseScreen();

    public abstract int getBackgroundColor();

    public abstract void onOpen();

    @Override
    public void onClose() { super.onClose(); };


    //* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ *//

    /**
     * This method is where the "structure" of the GUI is defined (What elements of children of others and the properties of elements).
     * When making your implementation of this class (extending it to make a new GUI), Override this method and create your own "structure".
     */
    public static WindowNode getWindowStructure() {
        WindowNode root = new WindowNode(createNodeData("root", createContentBox(SampleProperties.topRightPosition_Border1_Red_50w_50h))); {
            WindowNode leaf1 = root.addChild(createNodeData("leaf1", createContentBox(SampleProperties.basicBoxProperties)));
            WindowNode leaf2 = root.addChild(createNodeData("leaf2", createContentBox(SampleProperties.topRightPosition_Border1_Red_50w_50h))); {
                WindowNode leaf1A = leaf2.addChild(createNodeData("leaf1A", createContentBox(SampleProperties.basicBoxProperties)));
            }
        }

        return root;
    }

    public static WindowNodeData createNodeData(String name, V2_ContentBox contentBoxObject) {
        WindowNodeData data = new WindowNodeData(name, contentBoxObject);

        return data;
    }
    public static V2_ContentBox createContentBox(V2_ContentBoxBehaviour.BasicBoxProperties properties) {
        V2_ContentBox newBox = new V2_ContentBox(properties);

        return newBox;
    }

    private class SampleProperties {
        public static V2_ContentBoxBehaviour.BasicBoxProperties basicBoxProperties = new V2_ContentBoxBehaviour.BasicBoxProperties();

        public static V2_ContentBoxBehaviour.BasicBoxProperties topRightPosition_Border1_Red_50w_50h =
                new V2_ContentBoxBehaviour.BasicBoxProperties()
                        //.exteriorPosition(V2_ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_TOP)
                        .position(V2_ContentBoxBehaviour.InteriorContentBoxPosition.TOP_RIGHT)
                        .borderFont(TextureHandler.BORDER_1)
                        .backgroundColor(new FireLibColor(255, 0, 0))
                        .widthPercentageOfParent(0.5f)
                        .heightPercentageOfParent(0.5f);
    }


    //* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ *//


    @NotNull
    private GuiDrawingContext createDrawingContext(PoseStack poseStack, int width, int height, float x, float y, float z) {
        return new GuiDrawingContext(poseStack, width, height, x, y, z, font, itemRenderer);
    }

    private void drawElements(PoseStack poseStack, int windowOriginX, int windowOriginY, float windowOriginZ,
                              int windowWidth, int windowHeight, int mouseX, int mouseY) {
        WindowNode windowRoot = getWindowStructure();

        for(WindowNode node : windowRoot) {
            if (node.isRoot()) {
                if (node.data.isObjectButton()) {
                    /** Stuff will go here soon **/
                } else {
                    V2_ContentBox contentBox = node.data.getObject();
                    contentBox.draw(createDrawingContext(poseStack, windowWidth, windowHeight, windowOriginX, windowOriginY, windowOriginZ));
                }
            } else {
                if (node.data.isObjectButton()) {
                    /** Stuff will go here soon. **/
                } else {
                    V2_ContentBox parentContentBox = node.parent.data.getObject();
                    V2_ContentBox contentBox = node.data.getObject();

                    contentBox.draw(createDrawingContext(poseStack, parentContentBox.contentBoxWidth, parentContentBox.contentBoxHeight,
                            parentContentBox.topLeftXPos, parentContentBox.topLeftYPos, windowOriginZ));
                }
            }
        }
    }

    private void drawBackground(PoseStack poseStack, float x, float y, float z) {
        GUIHelper.fillAreaWithColor(poseStack, 0, 0, width, height, 0, getBackgroundColor());
    }

    private void draw(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        int topLeftX = 0;
        int topLeftY = 0;
        int originZ = 0;
        int windowWidth = this.width;
        int windowHeight = this.height;

        poseStack.pushPose();
        poseStack.translate(0, 0, 0);

        drawBackground(poseStack, topLeftX, topLeftY, originZ);

        poseStack.popPose();

        drawElements(poseStack, topLeftX, topLeftY, originZ, windowWidth, windowHeight, mouseX, mouseY);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);

        draw(poseStack, partialTicks, mouseX, mouseY);
    }
}
