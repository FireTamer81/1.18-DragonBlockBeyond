package io.firetamer.dragonblockbeyond.race.api;

import net.minecraft.world.entity.player.Player;

public interface IAmbientSoundHolder {
    static IAmbientSoundHolder get(Player entity) {
        return (IAmbientSoundHolder) entity;
    }

    int getAmbientSoundTime();

    void setAmbientSoundTime(int ambientSoundTime);
}
