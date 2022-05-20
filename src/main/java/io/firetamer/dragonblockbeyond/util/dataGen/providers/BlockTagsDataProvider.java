package io.firetamer.dragonblockbeyond.util.dataGen.providers;

import io.firetamer.dragonblockbeyond.DragonBlockBeyond;
import io.firetamer.dragonblockbeyond._modules.colorable_strongblock_module.ColorableStrongBlockModule;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagsDataProvider extends BlockTagsProvider {
    public BlockTagsDataProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DragonBlockBeyond.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ColorableStrongBlockModule.WARENAI_FULL_BLOCK.get());

        /**
        this.tag(BlockTags.IMPERMEABLE)
            .add(ColorableBlockTest.RGB_GLASS.get(),
                    ColorableBlockTest.RGB_GLASS_SLAB.get(),
                    ColorableBlockTest.RGB_GLASS_STAIRS.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
            .add(ColorableBlockTest.RGB_PLANKS.get(),
                    ColorableBlockTest.RGB_PLANKS_SLAB.get(),
                    ColorableBlockTest.RGB_PLANKS_STAIRS.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(ColorableBlockTest.RGB_CONCRETE.get(),
                 ColorableBlockTest.RGB_CONCRETE_SLAB.get(),
                 ColorableBlockTest.RGB_CONCRETE_STAIRS.get(),
                 ColorableBlockTest.RGB_TERRACOTTA.get(),
                 ColorableBlockTest.RGB_TERRACOTTA_SLAB.get(),
                 ColorableBlockTest.RGB_TERRACOTTA_STAIRS.get(),
                 ColorableBlockTest.RGB_ANTIBLOCK.get(),
                 ColorableBlockTest.RGB_PRISMARINE.get(),
                 ColorableBlockTest.RGB_PRISMARINE_SLAB.get(),
                 ColorableBlockTest.RGB_PRISMARINE_STAIRS.get(),
                 ColorableBlockTest.RGB_PRISMARINE_BRICKS.get(),
                 ColorableBlockTest.RGB_PRISMARINE_BRICK_SLAB.get(),
                 ColorableBlockTest.RGB_PRISMARINE_BRICK_STAIRS.get(),
                 ColorableBlockTest.RGB_DARK_PRISMARINE.get(),
                 ColorableBlockTest.RGB_DARK_PRISMARINE_SLAB.get(),
                 ColorableBlockTest.RGB_DARK_PRISMARINE_STAIRS.get());
        **/
    }
}
