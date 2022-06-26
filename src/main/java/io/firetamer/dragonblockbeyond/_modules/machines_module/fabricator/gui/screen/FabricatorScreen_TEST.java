package io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.container.FabricatorContainerMenu;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui.widget.StateSwitchingActionButton;
import io.firetamer.dragonblockbeyond.handlers.TextureHandler;
import io.firetamer.dragonblockbeyond.util.library_candidates.DBBColor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FabricatorScreen_TEST extends AbstractContainerScreen<FabricatorContainerMenu> {
    private static final ResourceLocation EXTRAS_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID, "textures/gui/fabricator_menu.png");
    //private final PlayerInventoryComponent playerInventoryPanelComponent = new PlayerInventoryComponent();

    protected int imageWidth = 176;
    protected int imageHeight = 218;
    protected StateSwitchingActionButton testButton;
    protected boolean isTestButtonActive = false;

    //private final RecipeScreenComponent recipeScreenComponent = new RecipeScreenComponent();


    public FabricatorScreen_TEST(FabricatorContainerMenu menuIn, Inventory playerInventory, Component title) {
        super(menuIn, playerInventory, title);

        //minecraft.level.getBlockEntity()
    }

    @Override
    protected void init() {
        super.init();

        //DBBColor interiorPanelColor1 = new DBBColor(100, 100, 100, 180);
        //this.playerInventoryPanelComponent.init(0, 64, 170, 92, width, height, minecraft, TextureHandler.BORDER_1, interiorPanelColor1, null);
        //this.setInitialFocus(this.playerInventoryPanelComponent);

        this.createScreenControlButtons();
    }

    protected void createScreenControlButtons() {
        int x = (width - this.imageWidth) / 2;
        int y = (height - this.imageHeight) / 2;

        this.testButton = new StateSwitchingActionButton(x + 124, y + 5, 45, 18, isTestButtonActive, (onPress) -> {
            //this.playerInventoryPanelComponent.toggleVisibility();
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

        //this.playerInventoryPanelComponent.render(poseStack, mouseX, mouseY, delta);

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