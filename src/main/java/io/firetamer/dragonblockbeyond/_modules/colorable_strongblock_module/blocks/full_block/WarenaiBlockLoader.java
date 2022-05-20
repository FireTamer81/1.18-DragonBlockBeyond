package io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.blocks.full_block;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class WarenaiBlockLoader implements IModelLoader<WarenaiBlockGeometry> {

    @Override
    public WarenaiBlockGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new WarenaiBlockGeometry();
    }

    @Override
    public void onResourceManagerReload(ResourceManager p_10758_) { }
}
