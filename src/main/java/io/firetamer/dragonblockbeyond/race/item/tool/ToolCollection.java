package io.firetamer.dragonblockbeyond.race.item.tool;

import io.firetamer.dragonblockbeyond.race.item.animated.AnimatedItemProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ToolCollection {
    private final RegistryObject<AxeItem> axe;
    private final RegistryObject<HoeItem> hoe;
    private final RegistryObject<PickaxeItem> pickaxe;
    private final RegistryObject<ShovelItem> shovel;
    private final RegistryObject<SwordItem> sword;

    public ToolCollection(RegistryObject<AxeItem> axe, RegistryObject<HoeItem> hoe, RegistryObject<PickaxeItem> pickaxe, RegistryObject<ShovelItem> shovel, RegistryObject<SwordItem> sword) {
        this.axe = axe;
        this.hoe = hoe;
        this.pickaxe = pickaxe;
        this.shovel = shovel;
        this.sword = sword;
    }

    public static ToolCollection registerCollection(DeferredRegister<Item> register, String toolType, Tier itemTier, Supplier<AnimatedItemProperties> propsSupplier) {
        return registerCollection(register, toolType, itemTier, new Builder(propsSupplier));
    }

    // public static ToolCollection registerCollectionDefaultRenderer(DeferredRegister<Item> register, String toolType, Tier itemTier, Supplier<AnimatedItemProperties> propsSupplier) {
    //     return registerCollection(register, toolType, itemTier, new Builder(propsSupplier).defaultRenderer());
    // }

    public static ToolCollection registerCollection(DeferredRegister<Item> register, String toolType, Tier itemTier, Builder builder) {
        return new ToolCollection(
                register.register(toolType + "_axe", () -> builder.axeFactory.create(itemTier, 5, -3.0F, builder.axeProperties)),
                register.register(toolType + "_hoe", () -> builder.hoeFactory.create(itemTier, -1, -2.0F, builder.hoeProperties)),
                register.register(toolType + "_pickaxe", () -> builder.pickaxeFactory.create(itemTier, 1, -2.5F, builder.pickaxeProperties)),
                register.register(toolType + "_shovel", () -> builder.shovelFactory.create(itemTier, 1.5F, -3F, builder.shovelProperties)),
                register.register(toolType + "_sword", () -> builder.swordFactory.create(itemTier, 2, -2.4F, builder.swordProperties))
        );
    }

    public AxeItem getAxe() {
        return axe.get();
    }

    public HoeItem getHoe() {
        return hoe.get();
    }

    public PickaxeItem getPickaxe() {
        return pickaxe.get();
    }

    public ShovelItem getShovel() {
        return shovel.get();
    }

    public SwordItem getSword() {
        return sword.get();
    }

    public List<TieredItem> getTools() {
        return List.of(getAxe(), getHoe(), getPickaxe(), getShovel(), getSword());
    }

    public static class Builder {
        protected AnimatedItemProperties axeProperties;
        protected AnimatedItemProperties hoeProperties;
        protected AnimatedItemProperties pickaxeProperties;
        protected AnimatedItemProperties shovelProperties;
        protected AnimatedItemProperties swordProperties;
        protected FloatToolItemFactory<AxeItem> axeFactory = AnimatedAxeItem::new;
        protected IntToolItemFactory<HoeItem> hoeFactory = AnimatedHoeItem::new;
        protected IntToolItemFactory<PickaxeItem> pickaxeFactory = AnimatedPickaxeItem::new;
        protected FloatToolItemFactory<ShovelItem> shovelFactory = AnimatedShovelItem::new;
        protected IntToolItemFactory<SwordItem> swordFactory = AnimatedSwordItem::new;

        public Builder(Supplier<AnimatedItemProperties> propsSupplier) {
            this.axeProperties = propsSupplier.get();
            this.hoeProperties = propsSupplier.get();
            this.pickaxeProperties = propsSupplier.get();
            this.shovelProperties = propsSupplier.get();
            this.swordProperties = propsSupplier.get();
        }

        public AnimatedItemProperties getAxeProperties() {
            return axeProperties;
        }

        public AnimatedItemProperties getHoeProperties() {
            return hoeProperties;
        }

        public AnimatedItemProperties getPickaxeProperties() {
            return pickaxeProperties;
        }

        public AnimatedItemProperties getShovelProperties() {
            return shovelProperties;
        }

        public AnimatedItemProperties getSwordProperties() {
            return swordProperties;
        }

        public FloatToolItemFactory<AxeItem> getAxeFactory() {
            return axeFactory;
        }

        public IntToolItemFactory<HoeItem> getHoeFactory() {
            return hoeFactory;
        }

        public IntToolItemFactory<PickaxeItem> getPickaxeFactory() {
            return pickaxeFactory;
        }

        public FloatToolItemFactory<ShovelItem> getShovelFactory() {
            return shovelFactory;
        }

        public IntToolItemFactory<SwordItem> getSwordFactory() {
            return swordFactory;
        }

        public Builder axeProperties(AnimatedItemProperties axeProperties) {
            this.axeProperties = axeProperties;
            return this;
        }

        public Builder hoeProperties(AnimatedItemProperties hoeProperties) {
            this.hoeProperties = hoeProperties;
            return this;
        }

        public Builder pickaxeProperties(AnimatedItemProperties pickaxeProperties) {
            this.pickaxeProperties = pickaxeProperties;
            return this;
        }

        public Builder shovelProperties(AnimatedItemProperties shovelProperties) {
            this.shovelProperties = shovelProperties;
            return this;
        }

        public Builder swordProperties(AnimatedItemProperties swordProperties) {
            this.swordProperties = swordProperties;
            return this;
        }

        public Builder axeFactory(FloatToolItemFactory<AxeItem> axeFactory) {
            this.axeFactory = axeFactory;
            return this;
        }

        public Builder hoeFactory(IntToolItemFactory<HoeItem> hoeFactory) {
            this.hoeFactory = hoeFactory;
            return this;
        }

        public Builder pickaxeFactory(IntToolItemFactory<PickaxeItem> pickaxeFactory) {
            this.pickaxeFactory = pickaxeFactory;
            return this;
        }

        public Builder shovelFactory(FloatToolItemFactory<ShovelItem> shovelFactory) {
            this.shovelFactory = shovelFactory;
            return this;
        }

        public Builder swordFactory(IntToolItemFactory<SwordItem> swordFactory) {
            this.swordFactory = swordFactory;
            return this;
        }

        // public Builder renderer(Supplier<Callable<BlockEntityWithoutLevelRenderer>> renderer) {
        //     this.axeProperties.setISTER(renderer);
        //     this.hoeProperties.setISTER(renderer);
        //     this.pickaxeProperties.setISTER(renderer);
        //     this.shovelProperties.setISTER(renderer);
        //     this.swordProperties.setISTER(renderer);
        //     return this;
        // }
        //
        // public Builder defaultRenderer() {
        //     return renderer(() -> GeoToolRenderer::new);
        // }
    }

    public interface FloatToolItemFactory<T extends TieredItem> {
        T create(Tier tier, float attackDamageModifier, float attackSpeedModifier, AnimatedItemProperties properties);
    }

    public interface IntToolItemFactory<T extends TieredItem> {
        T create(Tier tier, int attackDamageModifier, float attackSpeedModifier, AnimatedItemProperties properties);
    }
}
