package io.firetamer.dragonblockbeyond._modules.strongblock_module.util.top;

import java.util.function.Function;

import mcjty.theoneprobe.api.ITheOneProbe;

public class TOPMain implements Function<ITheOneProbe, Void> {
    private static ITheOneProbe probe;

    @Override
    public Void apply(ITheOneProbe theOneProbe) {
        probe = theOneProbe;
        probe.registerProvider(new WarenaiBlockProvider());
        return null;
    }
}
