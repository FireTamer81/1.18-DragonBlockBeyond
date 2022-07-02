package io.firetamer.dragonblockbeyond.race.item.animated;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class AnimatedItemProperties extends Item.Properties {
    private List<Supplier<MobEffectInstance>> axeEffectSuppliers = new ArrayList<>();
    private List<Supplier<MobEffectInstance>> swordEffectSuppliers = new ArrayList<>();
    private List<Supplier<MobEffectInstance>> hoeEffectSuppliers = new ArrayList<>();
    private List<Supplier<MobEffectInstance>> pickaxeEffectSuppliers = new ArrayList<>();
    private List<Supplier<MobEffectInstance>> shovelEffectSuppliers = new ArrayList<>();
    private boolean textureAnimated = false;
    private boolean animated = false;
    private int frames = 0;
    private int ticksPerFrame = 0;

    public List<Supplier<MobEffectInstance>> getSwordEffectSuppliers() {
        return swordEffectSuppliers;
    }

    @SafeVarargs
    public final AnimatedItemProperties swordEffectSuppliers(Supplier<MobEffectInstance>... effectInstance) {
        this.swordEffectSuppliers = Arrays.asList(effectInstance);
        return this;
    }

    public List<Supplier<MobEffectInstance>> getAxeEffectSuppliers() {
        return axeEffectSuppliers;
    }

    @SafeVarargs
    public final AnimatedItemProperties axeEffectSuppliers(Supplier<MobEffectInstance>... effectInstance) {
        this.axeEffectSuppliers = Arrays.asList(effectInstance);
        return this;
    }

    public List<Supplier<MobEffectInstance>> getShovelEffectSuppliers() {
        return shovelEffectSuppliers;
    }

    @SafeVarargs
    public final AnimatedItemProperties shovelEffectSuppliers(Supplier<MobEffectInstance>... effectInstance) {
        this.shovelEffectSuppliers = Arrays.asList(effectInstance);
        return this;
    }

    public List<Supplier<MobEffectInstance>> getPickaxeEffectSuppliers() {
        return pickaxeEffectSuppliers;
    }

    @SafeVarargs
    public final AnimatedItemProperties pickaxeEffectSuppliers(Supplier<MobEffectInstance>... effectInstance) {
        this.pickaxeEffectSuppliers = Arrays.asList(effectInstance);
        return this;
    }

    public List<Supplier<MobEffectInstance>> getHoeEffectSuppliers() {
        return hoeEffectSuppliers;
    }

    @SafeVarargs
    public final AnimatedItemProperties hoeEffectSuppliers(Supplier<MobEffectInstance>... effectInstance) {
        this.hoeEffectSuppliers = Arrays.asList(effectInstance);
        return this;
    }

    @Override
    public AnimatedItemProperties food(FoodProperties food) {
        super.food(food);
        return this;
    }

    @Override
    public AnimatedItemProperties stacksTo(int maxStackSize) {
        super.stacksTo(maxStackSize);
        return this;
    }

    @Override
    public AnimatedItemProperties defaultDurability(int maxDamage) {
        super.defaultDurability(maxDamage);
        return this;
    }

    @Override
    public AnimatedItemProperties durability(int maxDamage) {
        super.durability(maxDamage);
        return this;
    }

    @Override
    public AnimatedItemProperties craftRemainder(Item craftingRemainingItem) {
        super.craftRemainder(craftingRemainingItem);
        return this;
    }

    @Override
    public AnimatedItemProperties tab(CreativeModeTab category) {
        super.tab(category);
        return this;
    }

    @Override
    public AnimatedItemProperties rarity(Rarity rarity) {
        super.rarity(rarity);
        return this;
    }

    @Override
    public AnimatedItemProperties fireResistant() {
        super.fireResistant();
        return this;
    }

    @Override
    public AnimatedItemProperties setNoRepair() {
        super.setNoRepair();
        return this;
    }

    public AnimatedItemProperties textureAnimated(int frames, int ticksPerFrame) {
        this.textureAnimated = true;
        this.frames = frames;
        this.ticksPerFrame = ticksPerFrame;
        return this;
    }

    public boolean isTextureAnimated() {
        return textureAnimated;
    }

    public int getFrames() {
        return frames;
    }

    public int getTicksPerFrame() {
        return ticksPerFrame;
    }

    public boolean isAnimated() {
        return animated;
    }

    public AnimatedItemProperties animated() {
        this.animated = true;
        return this;
    }
}
