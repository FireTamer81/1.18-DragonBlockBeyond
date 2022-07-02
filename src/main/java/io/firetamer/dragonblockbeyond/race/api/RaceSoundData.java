package io.firetamer.dragonblockbeyond.race.api;

import io.firetamer.dragonblockbeyond.race.provider.*;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public record RaceSoundData(SoundEventProvider deathSound, HurtSoundEventProvider hurtSound, StepSoundEventProvider stepSound,
                            AttackSoundEventProvider attackSound, SoundEventProvider ambientSound, int ambientSoundInterval, EatSoundEventProvider eatSound) {
    public static final RaceSoundData DEFAULT = builder().build();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private SoundEventProvider deathSound = SoundEventProvider.DEFAULT;
        private HurtSoundEventProvider hurtSound = HurtSoundEventProvider.DEFAULT;
        private StepSoundEventProvider stepSound = StepSoundEventProvider.DEFAULT;
        private AttackSoundEventProvider attackSound = AttackSoundEventProvider.DEFAULT;
        private SoundEventProvider ambientSound = SoundEventProvider.DEFAULT;
        private int ambientSoundInterval = 0;
        private EatSoundEventProvider eatSound = EatSoundEventProvider.DEFAULT;

        public Builder deathSound(@NotNull Supplier<SoundEvent> deathSound) {
            this.deathSound = (entity, random) -> deathSound.get();
            return this;
        }

        public Builder deathSound(@NotNull SoundEventProvider deathSound) {
            this.deathSound = deathSound;
            return this;
        }

        public Builder hurtSound(@NotNull Supplier<SoundEvent> hurtSound) {
            this.hurtSound = (entity, random, damageSource) -> hurtSound.get();
            return this;
        }

        public Builder hurtSound(@NotNull HurtSoundEventProvider hurtSound) {
            this.hurtSound = hurtSound;
            return this;
        }

        public Builder stepSound(@NotNull Supplier<SoundEvent> stepSound) {
            this.stepSound = (entity, random, pos, state) -> stepSound.get();
            return this;
        }

        public Builder stepSound(@NotNull StepSoundEventProvider stepSound) {
            this.stepSound = stepSound;
            return this;
        }

        public Builder attackSound(@NotNull Supplier<SoundEvent> attackSound) {
            this.attackSound = (player, random, target) -> attackSound.get();
            return this;
        }

        public Builder attackSound(@NotNull AttackSoundEventProvider attackSound) {
            this.attackSound = attackSound;
            return this;
        }

        public Builder ambientSound(@NotNull Supplier<SoundEvent> ambientSound) {
            this.ambientSound = (entity, random) -> ambientSound.get();
            return this;
        }

        public Builder ambientSound(@NotNull SoundEventProvider ambientSound) {
            this.ambientSound = ambientSound;
            return this;
        }

        public Builder ambientSoundInterval(int ambientSoundInterval) {
            if (ambientSoundInterval < 0)
                throw new IllegalArgumentException("ambientSoundInterval must be >= 0");

            this.ambientSoundInterval = ambientSoundInterval;
            return this;
        }

        public Builder eatSound(@NotNull Supplier<SoundEvent> eatSound) {
            this.eatSound = (entity, random, itemStack) -> eatSound.get();
            return this;
        }

        public Builder eatSound(@NotNull EatSoundEventProvider eatSound) {
            this.eatSound = eatSound;
            return this;
        }

        public RaceSoundData build() {
            return new RaceSoundData(deathSound, hurtSound, stepSound, attackSound, ambientSound, ambientSoundInterval, eatSound);
        }
    }
}
