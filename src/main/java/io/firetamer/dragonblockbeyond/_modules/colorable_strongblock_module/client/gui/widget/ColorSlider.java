package io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.client.gui.widget;

import java.util.Locale;
import java.util.function.Function;

import com.mojang.blaze3d.vertex.PoseStack;

import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.client.gui.ScreenUtils;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.client.gui.screen.ColorSelectScreen;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.util.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;

public class ColorSlider extends AbstractSliderButton {
    private SliderType type;

    private double minValue;
    private double maxValue;

    private Component displayText;

    public ColorSlider(int x, int y, int width, int height, Component displayText, double minValue, double maxValue,
                       double currentValue, SliderType type) {
        super(x, y, width, height, displayText, (currentValue - minValue) / (maxValue - minValue));
        this.type = type;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = (currentValue - this.minValue) / (this.maxValue - this.minValue);
        this.displayText = displayText;
        setMessage(new TextComponent("").append(displayText)
                                        .append(Integer.toString((int) Math.round(this.value * (maxValue - minValue) +
                                                                                  minValue))));
    }

    @Override
    protected void updateMessage() {
        setMessage(new TextComponent("").append(this.displayText).append(Integer.toString(getValueInt())));
    }

    @Override
    protected void applyValue() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen instanceof ColorSelectScreen screen) {
            screen.hexBox.setValue("#" +
                                   Integer.toHexString(new Color(screen.redSlider.getValueInt(),
                                                                 screen.greenSlider.getValueInt(),
                                                                 screen.blueSlider.getValueInt()).getRGB())
                                          .substring(2)
                                          .toUpperCase(Locale.ENGLISH));
        }
    }

    public int getValueInt() {
        return (int) Math.round(this.value * (maxValue - minValue) + minValue);
    }

    public double getValue() {
        return this.value * (maxValue - minValue) + minValue;
    }

    public void setValueInt(int value) {
        this.value = (value - this.minValue) / (this.maxValue - this.minValue);
        this.updateMessage();
    }

    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            Minecraft minecraft = Minecraft.getInstance();
            fill(matrixStack, this.x, this.y, this.x + this.width, this.y + this.height, 0xFF000000);
            if (minecraft.screen instanceof ColorSelectScreen) {
                ColorSelectScreen screen = (ColorSelectScreen) minecraft.screen;
                switch (type) {
                    case RED:
                        renderRedBackground(matrixStack, screen);
                        break;
                    case GREEN:
                        renderGreenBackground(matrixStack, screen);
                        break;
                    case BLUE:
                        renderBlueBackground(matrixStack, screen);
                        break;
                    case HUE:
                        renderHueBackground(matrixStack, screen);
                        break;
                    case SATURATION:
                        renderSaturationBackground(matrixStack, screen);
                        break;
                    case BRIGHTNESS:
                        renderBrightnessBackground(matrixStack, screen);
                        break;
                }
            }

            this.renderBg(matrixStack, minecraft, mouseX, mouseY);

            drawCenteredString(matrixStack,
                               minecraft.font,
                               this.getMessage(),
                               this.x + this.width / 2,
                               this.y - (this.height) / 10 * 7,
                               getFGColor());
        }
    }

    private void renderRedBackground(PoseStack matrixStack, ColorSelectScreen screen) {
        int leftColor = new Color(0x00, screen.greenSlider.getValueInt(), screen.blueSlider.getValueInt()).getRGB();
        int rightColor = new Color(0xFF, screen.greenSlider.getValueInt(), screen.blueSlider.getValueInt()).getRGB();
        ScreenUtils.fillGradient(matrixStack,
                                 this.x + 1,
                                 this.y + 1,
                                 this.x + this.width - 1,
                                 this.y + this.height - 1,
                                 leftColor,
                                 rightColor,
                                 this.getBlitOffset());
    }

    private void renderGreenBackground(PoseStack matrixStack, ColorSelectScreen screen) {
        int leftColor = new Color(screen.redSlider.getValueInt(), 0x00, screen.blueSlider.getValueInt()).getRGB();
        int rightColor = new Color(screen.redSlider.getValueInt(), 0xFF, screen.blueSlider.getValueInt()).getRGB();
        ScreenUtils.fillGradient(matrixStack,
                                 this.x + 1,
                                 this.y + 1,
                                 this.x + this.width - 1,
                                 this.y + this.height - 1,
                                 leftColor,
                                 rightColor,
                                 this.getBlitOffset());
    }

    private void renderBlueBackground(PoseStack matrixStack, ColorSelectScreen screen) {
        int leftColor = new Color(screen.redSlider.getValueInt(), screen.greenSlider.getValueInt(), 0x00).getRGB();
        int rightColor = new Color(screen.redSlider.getValueInt(), screen.greenSlider.getValueInt(), 0xFF).getRGB();
        ScreenUtils.fillGradient(matrixStack,
                                 this.x + 1,
                                 this.y + 1,
                                 this.x + this.width - 1,
                                 this.y + this.height - 1,
                                 leftColor,
                                 rightColor,
                                 this.getBlitOffset());
    }

    private void renderHueBackground(PoseStack matrixStack, ColorSelectScreen screen) {
        Function<Integer, Integer> lerp = (pct) -> (int) Math.floor(Mth.lerp(pct / 100f,
                                                                             this.x + 1,
                                                                             this.x + this.width - 1));
        Function<Integer, Integer> color = (pct) -> Color.HSBtoRGB((float) ((pct / 100f)),
                                                                   (float) (screen.saturationSlider.getValueInt() /
                                                                            ColorSelectScreen.MAX_VALUE_SB),
                                                                   (float) (screen.brightnessSlider.getValueInt() /
                                                                            ColorSelectScreen.MAX_VALUE_SB));
        ScreenUtils.fillGradient(matrixStack,
                                 lerp.apply(0),
                                 this.y + 1,
                                 lerp.apply(17),
                                 this.y + this.height - 1,
                                 color.apply(0),
                                 color.apply(17),
                                 this.getBlitOffset());
        ScreenUtils.fillGradient(matrixStack,
                                 lerp.apply(17),
                                 this.y + 1,
                                 lerp.apply(34),
                                 this.y + this.height - 1,
                                 color.apply(17),
                                 color.apply(34),
                                 this.getBlitOffset());
        ScreenUtils.fillGradient(matrixStack,
                                 lerp.apply(34),
                                 this.y + 1,
                                 lerp.apply(50),
                                 this.y + this.height - 1,
                                 color.apply(34),
                                 color.apply(50),
                                 this.getBlitOffset());
        ScreenUtils.fillGradient(matrixStack,
                                 lerp.apply(50),
                                 this.y + 1,
                                 lerp.apply(66),
                                 this.y + this.height - 1,
                                 color.apply(50),
                                 color.apply(66),
                                 this.getBlitOffset());
        ScreenUtils.fillGradient(matrixStack,
                                 lerp.apply(66),
                                 this.y + 1,
                                 lerp.apply(82),
                                 this.y + this.height - 1,
                                 color.apply(66),
                                 color.apply(82),
                                 this.getBlitOffset());
        ScreenUtils.fillGradient(matrixStack,
                                 lerp.apply(82),
                                 this.y + 1,
                                 lerp.apply(100),
                                 this.y + this.height - 1,
                                 color.apply(82),
                                 color.apply(100),
                                 this.getBlitOffset());
    }

    private void renderSaturationBackground(PoseStack matrixStack, ColorSelectScreen screen) {
        int leftColor = Color.HSBtoRGB((float) (screen.hueSlider.getValue() / ColorSelectScreen.MAX_VALUE_HUE),
                                       0.0f,
                                       (float) (screen.brightnessSlider.getValue() / ColorSelectScreen.MAX_VALUE_SB));
        int rightColor = Color.HSBtoRGB((float) (screen.hueSlider.getValue() / ColorSelectScreen.MAX_VALUE_HUE),
                                        1.0f,
                                        (float) (screen.brightnessSlider.getValue() / ColorSelectScreen.MAX_VALUE_SB));
        ScreenUtils.fillGradient(matrixStack,
                                 this.x + 1,
                                 this.y + 1,
                                 this.x + this.width - 1,
                                 this.y + this.height - 1,
                                 leftColor,
                                 rightColor,
                                 this.getBlitOffset());
    }

    private void renderBrightnessBackground(PoseStack matrixStack, ColorSelectScreen screen) {
        int leftColor = Color.HSBtoRGB((float) (screen.hueSlider.getValue() / ColorSelectScreen.MAX_VALUE_HUE),
                                       (float) (screen.saturationSlider.getValue() / ColorSelectScreen.MAX_VALUE_SB),
                                       0.0f);
        int rightColor = Color.HSBtoRGB((float) (screen.hueSlider.getValue() / ColorSelectScreen.MAX_VALUE_HUE),
                                        (float) (screen.saturationSlider.getValue() / ColorSelectScreen.MAX_VALUE_SB),
                                        1.0f);
        ScreenUtils.fillGradient(matrixStack,
                                 this.x + 1,
                                 this.y + 1,
                                 this.x + this.width - 1,
                                 this.y + this.height - 1,
                                 leftColor,
                                 rightColor,
                                 this.getBlitOffset());
    }
}
