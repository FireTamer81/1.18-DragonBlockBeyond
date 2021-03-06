package io.firetamer.dragonblockbeyond.race.item.tool;

import io.firetamer.dragonblockbeyond.race.client.renderer.GeoToolRenderer;
import io.firetamer.dragonblockbeyond.race.item.animated.AnimatedItemProperties;
import io.firetamer.dragonblockbeyond.race.item.animated.IAnimatedItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.client.IItemRenderProperties;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AnimatedShovelItem extends ShovelItem implements IAnimatable, IAnimatedItem {
    protected final AnimationFactory factory = new AnimationFactory(this);
    protected final List<Supplier<MobEffectInstance>> effectSuppliers;
    protected final AnimatedItemProperties animatedToolProperties;


    public AnimatedShovelItem(Tier tier, float attackDamageModifier, float attackSpeedModifier, AnimatedItemProperties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        effectSuppliers = properties.getShovelEffectSuppliers();
        animatedToolProperties = properties;
    }

    @Override
    public void registerControllers(AnimationData data) {}

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public AnimatedItemProperties getAnimatedToolProperties() {
        return animatedToolProperties;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
                            @Override
                            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                                return new GeoToolRenderer<>();
                            }
                        }

        );
    }
}
