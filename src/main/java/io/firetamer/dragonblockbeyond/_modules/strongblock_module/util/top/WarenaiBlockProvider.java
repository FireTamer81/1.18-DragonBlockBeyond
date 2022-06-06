package io.firetamer.dragonblockbeyond._modules.strongblock_module.util.top;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.client.gui.screen.ColorSelectScreen;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.tiles.StrongBlockTile;
import io.firetamer.dragonblockbeyond.util.DBBColor;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WarenaiBlockProvider implements IProbeInfoProvider {
    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo info, Player player, Level world, BlockState state, IProbeHitData hitData) {
        BlockEntity tileEntity = world.getBlockEntity(hitData.getPos());
        if (tileEntity instanceof StrongBlockTile) {
            if (mode == ProbeMode.NORMAL) {
                info.text("#" + Integer.toHexString(((StrongBlockTile) tileEntity).getColor()).substring(2));
            }

            if (mode == ProbeMode.EXTENDED) {
                DBBColor color = new DBBColor(((StrongBlockTile) tileEntity).getColor());
                MutableComponent red = new TranslatableComponent("gui.dragonblockbeyond.red").append(": " + color.getRed());
                MutableComponent green = new TranslatableComponent("gui.dragonblockbeyond.green").append(": " +
                                                                                                 color.getGreen());
                MutableComponent blue = new TranslatableComponent("gui.dragonblockbeyond.blue").append(": " + color.getBlue());
                info.text(red.append(", ").append(green).append(", ").append(blue));
                float[] hsb = DBBColor.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue());
                MutableComponent hue = new TranslatableComponent("gui.dragonblockbeyond.hue").append(": " +
                                                                                             Math.round(hsb[0] *
                                                                                                        ColorSelectScreen.MAX_VALUE_HUE));
                MutableComponent saturation = new TranslatableComponent("gui.dragonblockbeyond.saturation").append(": " +
                                                                                                           Math.round(
                                                                                                                   hsb[1] *
                                                                                                                   ColorSelectScreen.MAX_VALUE_SB));
                MutableComponent brightness = new TranslatableComponent("gui.dragonblockbeyond.brightness").append(": " +
                                                                                                           Math.round(
                                                                                                                   hsb[2] *
                                                                                                                   ColorSelectScreen.MAX_VALUE_SB));
                info.text(hue.append("\u00B0, ").append(saturation).append("%, ").append(brightness).append("%"));
            }
        }
    }

    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(DragonBlockBeyond.MOD_ID, "blocks");
    }
}
