package io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.container.FabricatorContainerMenu;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui.widget.StateSwitchingActionButton;
import io.firetamer.dragonblockbeyond.handlers.TextureHandler;
import io.firetamer.dragonblockbeyond.util.library_candidates.DBBColor;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.AdvancedPanelComponent2;
import net.minecraft.client.CameraType;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FabricatorScreen_TEST_3 extends AbstractContainerScreen<FabricatorContainerMenu> {
    private static final ResourceLocation EXTRAS_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID, "textures/gui/fabricator_menu.png");
    private final AdvancedPanelComponent2 testPanelComponent = new AdvancedPanelComponent2();

    protected int imageWidth = 176;
    protected int imageHeight = 218;
    protected StateSwitchingActionButton testButton;
    protected boolean isTestButtonActive = false;

    private float xMouse;
    private float yMouse;


    public FabricatorScreen_TEST_3(FabricatorContainerMenu menuIn, Inventory playerInventory, Component title) {
        super(menuIn, playerInventory, title);

        //minecraft.level.getBlockEntity()
    }


    @Override
    protected void init() {
        super.init();

        DBBColor interiorPanelColor1 = new DBBColor(100, 100, 100, 180);

        this.testPanelComponent.init(20, 64, 100, 100, minecraft,
                true, true, TextureHandler.BORDER_1, interiorPanelColor1, null);

        this.children.add(this.testPanelComponent);
        this.setInitialFocus(this.testPanelComponent);

        minecraft.options.setCameraType(CameraType.THIRD_PERSON_FRONT);

        this.createScreenControlButtons();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    protected void createScreenControlButtons() {
        int x = (width - this.imageWidth) / 2;
        int y = (height - this.imageHeight) / 2;

        this.testButton = new StateSwitchingActionButton(x + 124, y + 5, 45, 18, isTestButtonActive, (onPress) -> {
            //this.testPanelComponent.toggleVisibility();
        });

        this.initButtonTextures();
    }

    protected void initButtonTextures() {
        this.testButton.initVisuals(0, 0, 45, 18, EXTRAS_TEXTURE);
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

        this.testPanelComponent.render(poseStack, mouseX, mouseY, delta);

        super.render(poseStack, mouseX, mouseY, delta);
        this.testButton.render(poseStack, mouseX, mouseY, delta);

        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float pPartialTick, int pMouseX, int pMouseY) { }

    @Override
    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
        //this.font.draw(p_97808_, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        //this.font.draw(p_97808_, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }
}
