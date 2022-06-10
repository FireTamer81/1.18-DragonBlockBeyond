package io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.machines_module.fabricator.container.FabricatorContainerMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class FabricatorScreen extends AbstractContainerScreen<FabricatorContainerMenu> {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID, "textures/gui/fabricator_menu.png");
    private static final ResourceLocation INV_TEXTURE = new ResourceLocation(DragonBlockBeyond.MOD_ID, "textures/gui/fabricator_inventory.png");
    protected int imageWidth = 176;
    protected int imageHeight = 218;

    public FabricatorScreen(FabricatorContainerMenu menuIn, Inventory playerInventory, Component title) {
        super(menuIn, playerInventory, title);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        int x = (width - this.imageWidth) / 2;
        int y = (height - this.imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    private void renderMachineInv(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, INV_TEXTURE);
        int x = ((width - this.imageWidth) / 2) - 88;
        int y = (height - this.imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, 88, 256);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        renderMachineInv(pPoseStack, delta, mouseX, mouseY);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
        //this.font.draw(p_97808_, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        //this.font.draw(p_97808_, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }
}
