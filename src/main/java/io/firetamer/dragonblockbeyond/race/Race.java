package io.firetamer.dragonblockbeyond.race;


import io.firetamer.dragonblockbeyond.init.RaceInit;
import io.firetamer.dragonblockbeyond.race.api.RaceSoundData;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class Race extends ForgeRegistryEntry<Race> {
    private final EntityDimensions dimensions;
    protected final float eyeHeight;
    private final float maxHealth;
    private final int swingDuration;
    private final double cameraDistance;

    private final List<String> variants;
    private final List<Class<? extends LivingEntity>> enabledEntityClasses;
    private final Consumer<LivingEntity> raceedToConsumer;
    private final Consumer<LivingEntity> deraceConsumer;
    private final RaceSoundData raceSoundData;
    private String descriptionId;
    private Component description;
    private ResourceLocation skin;

    public Race(Properties<?> properties) {
        this.dimensions = properties.dimensions;
        this.eyeHeight = properties.eyeHeight;
        this.maxHealth = properties.maxHealth;
        this.swingDuration = properties.swingDuration;
        this.cameraDistance = properties.cameraDistance;
        this.variants = properties.variants;
        this.raceedToConsumer = properties.raceedToConsumer;
        this.enabledEntityClasses = properties.enabledEntityClasses;
        this.deraceConsumer = properties.deraceConsumer;
        this.raceSoundData = properties.raceSoundData;
    }

    public EntityDimensions getDimensions(LivingEntity entity) {
        return getDimensions();
    }

    protected EntityDimensions getDimensions() {
        return this.dimensions;
    }

    public float getEyeHeight(LivingEntity entity) {
        return getEyeHeight();
    }

    protected float getEyeHeight() {
        return this.eyeHeight == 0.0F ? 0.85F * this.dimensions.height : this.eyeHeight;
    }

    public float getMaxHealth(LivingEntity entity) {
        return getMaxHealth();
    }

    protected float getMaxHealth() {
        return this.maxHealth;
    }

    public int getSwingDuration(LivingEntity entity) {
        return getSwingDuration();
    }

    protected int getSwingDuration() {
        return this.swingDuration;
    }

    public double getCameraDistance(Player player) {
        return getCameraDistance();
    }

    protected double getCameraDistance() {
        return this.cameraDistance;
    }

    public boolean isEnabled(LivingEntity entity) {
        if (entity instanceof Player)
            return true;

        for (Class<? extends LivingEntity> entityClass : getEnabledEntityClasses()) {
            if (entityClass.isInstance(entity))
                return true;
        }

        return false;
    }

    protected List<Class<? extends LivingEntity>> getEnabledEntityClasses() {
        return this.enabledEntityClasses;
    }



    public List<String> getVariants() {
        return this.variants;
    }

    @NotNull
    public RaceSoundData getRaceSoundData() {
        return this.raceSoundData;
    }

    public void onRaceedTo(LivingEntity entity) {
        setMaxRaceHealth(entity);
        this.raceedToConsumer.accept(entity);
    }

    public void onDerace(LivingEntity entity) {
        restoreMaxHealth(entity);
        this.deraceConsumer.accept(entity);
    }

    public void summonRaceParticles(LivingEntity entity, ServerLevel level) {
        level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, entity.getX(), entity.getY(), entity.getZ(), 3, 0.0D, 0.0D, 0, 1);
    }

    public void playRaceedToSound(LivingEntity entity, ServerLevel level) {
        level.playSound(null, entity, SoundEvents.GENERIC_EXPLODE, entity instanceof Player ? SoundSource.PLAYERS : SoundSource.BLOCKS, 4.0F, 1.0F);
    }

    public void setMaxRaceHealth(LivingEntity entity) {
        float maxHealth = this.getMaxHealth(entity);
        if (maxHealth != 0) {
            setMaxHealth(entity, maxHealth);
        }
    }

    @SuppressWarnings("unchecked")
    private void restoreMaxHealth(LivingEntity entity) {
        float maxHealth = this.getMaxHealth(entity);
        if (maxHealth != 0) {
            EntityType<? extends LivingEntity> type = (EntityType<? extends LivingEntity>) entity.getType();
            setMaxHealth(entity, DefaultAttributes.getSupplier(type).getBaseValue(Attributes.MAX_HEALTH));
        }
    }

    private void setMaxHealth(LivingEntity entity, double newBaseValue) {
        AttributeInstance maxHealthAttribute = entity.getAttribute(Attributes.MAX_HEALTH);
        double oldBaseValue = maxHealthAttribute.getBaseValue();
        double oldPercentage = entity.getHealth() / oldBaseValue;

        maxHealthAttribute.setBaseValue(newBaseValue);

        // Scale the old health relative to the new max health
        entity.setHealth((float) (oldPercentage * newBaseValue));
    }

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("race", this.getRegistryName());
        }

        return this.descriptionId;
    }

    public Component getDescription() {
        if (this.description == null) {
            this.description = new TranslatableComponent(this.getDescriptionId());
        }

        return this.description;
    }

    protected ResourceLocation getSkin() {
        if (skin == null) {
            skin = new ResourceLocation(getRegistryName().getNamespace(), "textures/skin/" + getRegistryName().getPath() + ".png");
        }

        return skin;
    }

    public ResourceLocation getSkin(Player player) {
        return getSkin();
    }

    public boolean is(TagKey<Race> tag) {
        return RaceInit.getRegistry().tags().getReverseTag(this).orElseThrow().containsTag(tag);
    }

    public static class Properties<T extends Properties<T>> {
        private EntityDimensions dimensions = EntityDimensions.scalable(0.6F, 1.8F);
        private float eyeHeight = 0.0F;
        private float maxHealth = 0.0F;
        private int swingDuration = -1;
        private double cameraDistance = 4.0D;

        private List<String> variants = List.of();
        private List<Class<? extends LivingEntity>> enabledEntityClasses = List.of();
        private Consumer<LivingEntity> raceedToConsumer = p -> {};
        private Consumer<LivingEntity> deraceConsumer = p -> {};
        private RaceSoundData raceSoundData = RaceSoundData.DEFAULT;

        @SuppressWarnings("unchecked")
        protected T getSelf() {
            return (T) this;
        }

        public T dimensions(float width, float height) {
            this.dimensions = EntityDimensions.scalable(width, height);
            return getSelf();
        }

        public T eyeHeight(float eyeHeight) {
            this.eyeHeight = eyeHeight;
            return getSelf();
        }

        public T maxHealth(float maxHealth) {
            this.maxHealth = maxHealth;
            return getSelf();
        }

        public T swingDuration(int swingDuration) {
            this.swingDuration = swingDuration;
            return getSelf();
        }

        public T cameraDistance(double cameraDistance) {
            this.cameraDistance = cameraDistance;
            return getSelf();
        }


        public T variants(String... variants) {
            for (String variant : variants) {
                if (variant.indexOf(' ') != -1)
                    throw new IllegalStateException("Variant name " + variant + " contains spaces");
            }

            this.variants = List.of(variants);
            return getSelf();
        }

        public T enableForAllLiving() {
            this.enabledEntityClasses = List.of(LivingEntity.class);
            return getSelf();
        }

        @SafeVarargs
        public final T enableForEntityClasses(Class<? extends LivingEntity>... classes) {
            this.enabledEntityClasses = List.of(classes);
            return getSelf();
        }

        public T raceedTo(Consumer<LivingEntity> raceedToConsumer) {
            this.raceedToConsumer = raceedToConsumer;
            return getSelf();
        }

        public T derace(Consumer<LivingEntity> deraceConsumer) {
            this.deraceConsumer = deraceConsumer;
            return getSelf();
        }

        public T raceSoundData(@NotNull RaceSoundData raceSoundData) {
            this.raceSoundData = raceSoundData;
            return getSelf();
        }
    }
}
