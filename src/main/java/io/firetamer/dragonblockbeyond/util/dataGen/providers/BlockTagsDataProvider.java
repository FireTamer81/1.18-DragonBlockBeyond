package io.firetamer.dragonblockbeyond.util.dataGen.providers;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.namek_module.NamekModule;
import io.firetamer.dragonblockbeyond._modules.strongblock_module.StrongBlockModule;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagsDataProvider extends BlockTagsProvider {
    public BlockTagsDataProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DragonBlockBeyond.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(StrongBlockModule.WARENAI_FULL_BLOCK.get())
                .add(StrongBlockModule.WARENAI_STAIRS_BLOCK.get())
                .add(StrongBlockModule.WARENAI_SLAB_BLOCK.get())
                .add(StrongBlockModule.WARENAI_FENCE_BLOCK.get())
                .add(StrongBlockModule.WARENAI_GLASS.get())
                .add(StrongBlockModule.WARENAI_GLASS_SLAB.get())
                .add(StrongBlockModule.WARENAI_GLASS_STAIRS.get());

        this.tag(BlockTags.FENCES)
                .add(StrongBlockModule.WARENAI_FENCE_BLOCK.get());

        this.tag(BlockTags.LOGS)
                .add(NamekModule.NAMEK_LOG.get());

        //this.tag(BlockTags.WALLS).add(StrongBlockModule.WARENAI_WALL_BLOCK.get());
    }
}
