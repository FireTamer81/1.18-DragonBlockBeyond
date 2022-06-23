package io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.container.FabricatorContainerMenu;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui.widget.StateSwitchingActionButton;
import io.firetamer.dragonblockbeyond.handlers.TextureHandler;
import io.firetamer.dragonblockbeyond.util.DBBColor;
import io.firetamer.dragonblockbeyond.util.gui_stuff.GUIHelper;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FabricatorScreen extends AbstractContainerScreen<FabricatorContainerMenu> {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID, "textures/gui/fabricator_menu.png");
    private static final ResourceLocation EXTRAS_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID, "textures/gui/fabricator_menu_extras.png");
    private static final ResourceLocation PANEL_PIECES_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID, "textures/gui/dynamic_panel_test.png");
    private static final ResourceLocation PANEL_TEST = new ResourceLocation(DragonBlockBeyond.MOD_ID, "textures/gui/border1.png");

    protected int imageWidth = 176;
    protected int imageHeight = 218;
    protected StateSwitchingActionButton testButton;
    protected boolean isTestButtonActive = false;

    //private final RecipeScreenComponent recipeScreenComponent = new RecipeScreenComponent();


    public FabricatorScreen(FabricatorContainerMenu menuIn, Inventory playerInventory, Component title) {
        super(menuIn, playerInventory, title);

        //minecraft.level.getBlockEntity()
    }

    @Override
    protected void init() {
        super.init();
        //this.recipeScreenComponent.init(232, 194, this.minecraft);
        //this.recipeScreenComponent.initVisuals();
        this.createScreenControlButtons();
    }

    protected void createScreenControlButtons() {
        int x = (width - this.imageWidth) / 2;
        int y = (height - this.imageHeight) / 2;

        this.testButton = new StateSwitchingActionButton(x + 124, y + 5, 45, 18, isTestButtonActive, (onPress) -> {
            //this.recipeComponent.toggleVisibility();
        });

        this.initButtonTextures();
    }

    protected void initButtonTextures() {
        this.testButton.initVisuals(166, 0, 45, 18, EXTRAS_TEXTURE);
    }

    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {
        if (this.testButton.mouseClicked(p_97748_, p_97749_, p_97750_)) {
            this.testButton.setStateTriggered(!this.testButton.isStateTriggered());
            return true;
        } else {
            return super.mouseClicked(p_97748_, p_97749_, p_97750_);
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        int x = (width - this.imageWidth) / 2;
        int y = (height - this.imageHeight) / 2;

        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        this.testButton.render(poseStack, mouseX, mouseY, delta);

        //GUIHelper.renderBackgroundTexture(pPoseStack, PANEL_TEST, 4, 4, leftPos, topPos, imageWidth, imageHeight, 256, 256, 0);
        DBBColor testColor = new DBBColor(255, 0, 0, 150);
        DBBColor testColor2 = new DBBColor(0, 0, 255, 150);
        GUIHelper.drawBorderedFilledPanel(poseStack, x, y, this.imageWidth, this.imageHeight, TextureHandler.BORDER_1, testColor, testColor2, 0);

        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float pPartialTick, int pMouseX, int pMouseY) {
        /*
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        int x = (width - this.imageWidth) / 2;
        int y = (height - this.imageHeight) / 2;

        this.blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
        */
    }

    @Override
    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
        //this.font.draw(p_97808_, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        //this.font.draw(p_97808_, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }
}
