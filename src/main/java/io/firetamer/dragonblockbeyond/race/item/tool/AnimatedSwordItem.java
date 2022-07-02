package io.firetamer.dragonblockbeyond.race.item.tool;

import io.firetamer.dragonblockbeyond.race.client.renderer.GeoToolRenderer;
import io.firetamer.dragonblockbeyond.race.item.animated.AnimatedItemProperties;
import io.firetamer.dragonblockbeyond.race.item.animated.IAnimatedItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.client.IItemRenderProperties;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AnimatedSwordItem extends SwordItem implements IAnimatable, IAnimatedItem {
    protected final AnimationFactory factory = new AnimationFactory(this);
    protected final List<Supplier<MobEffectInstance>> effectSuppliers;
    protected final AnimatedItemProperties animatedToolProperties;

    public AnimatedSwordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, AnimatedItemProperties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        effectSuppliers = properties.getSwordEffectSuppliers();
        animatedToolProperties = properties;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        for (Supplier<MobEffectInstance> effectInstance : effectSuppliers) {
            target.addEffect(effectInstance.get());
        }

        return super.hurtEnemy(stack, target, attacker);
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
