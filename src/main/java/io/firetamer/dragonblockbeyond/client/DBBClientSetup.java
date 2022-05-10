package io.firetamer.dragonblockbeyond.client;

import com.matyrobbrt.lib.ClientSetup;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.IEventBus;


public class DBBClientSetup extends ClientSetup {

	private static final Minecraft minecraft = Minecraft.getInstance();

	public DBBClientSetup(IEventBus modBus) {
		super(modBus);
	}
}
