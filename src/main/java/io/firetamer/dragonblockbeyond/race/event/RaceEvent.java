package io.firetamer.dragonblockbeyond.race.event;

import io.firetamer.dragonblockbeyond.race.Race;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.Nullable;

/**
 * Fired on {@link MinecraftForge#EVENT_BUS the forge event bus} whenever a living entity races from one thing to another, including deraces.
 * <p>
 * This event is {@link Cancelable cancelable}. If canceled, the living entity will not race.
 */
@Cancelable
public class RaceEvent extends LivingEvent {
    @Nullable
    private final Race current;
    @Nullable
    private final String currentVariant;
    @Nullable
    private Race target;
    @Nullable
    private String targetVariant;

    public RaceEvent(LivingEntity entity, @Nullable Race current, @Nullable String currentVariant, @Nullable Race target, @Nullable String targetVariant) {
        super(entity);
        this.current = current;
        this.currentVariant = currentVariant;
        this.target = target;
        this.targetVariant = targetVariant;
    }

    /**
     * @return the current living entity race before raceing into {@link #getTargetRace() the target race}.
     * Null means no race.
     */
    @Nullable
    public Race getCurrentRace() {
        return this.current;
    }

    /**
     * @return the current variant before raceing into {@link #getTargetRace() the target race} with {@link #getTargetVariant() the target variant}.
     * Null means no variant.
     */
    @Nullable
    public String getCurrentVariant() {
        return this.currentVariant;
    }

    /**
     * Sets the target race that the living entity will race into. Null means no race.
     */
    public void setTarget(@Nullable Race target) {
        this.target = target;
    }

    /**
     * @return the target race that the living entity will race into.
     * Null means no race.
     */
    @Nullable
    public Race getTargetRace() {
        return this.target;
    }

    /**
     * Sets the target variant that the living entity will race into.
     * Null means no variant.
     * <p>
     * If {@link #getTargetRace() the target race} is null, the target variant will be ignored and set to null in the race holder.
     */
    public void setTargetVariant(@Nullable String targetVariant) {
        this.targetVariant = targetVariant;
    }

    /**
     * @return the target variant that the living entity will race into.
     * Null means no variant.
     * <p>
     * If {@link #getTargetRace() the target race} is null, the target variant will be ignored and set to null in the race holder.
     */
    @Nullable
    public String getTargetVariant() {
        return this.targetVariant;
    }
}
