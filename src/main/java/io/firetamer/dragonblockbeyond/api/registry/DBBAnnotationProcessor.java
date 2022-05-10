package io.firetamer.dragonblockbeyond.api.registry;

import com.matyrobbrt.lib.registry.annotation.AnnotationProcessor;
import net.minecraftforge.eventbus.api.IEventBus;


public class DBBAnnotationProcessor extends AnnotationProcessor {

    public DBBAnnotationProcessor(String modid) {
        super(modid);
    }

    @Override
    public void register(IEventBus modBus) {
        super.register(modBus);
    }
}
