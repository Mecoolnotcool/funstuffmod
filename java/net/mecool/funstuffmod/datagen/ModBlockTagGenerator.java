package net.mecool.funstuffmod.datagen;

import net.mecool.funstuffmod.funstuffmod;
import net.mecool.funstuffmod.block.ModBlocks;
import net.mecool.funstuffmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, funstuffmod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Blocks.METAL_DETECTOR_VALUABLES)
                .add(ModBlocks.URANIUM_ORE_BLOCK.get()).addTag(Tags.Blocks.ORES);


        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SALT_BLOCK.get(),
                        ModBlocks.SOUND_BLOCK.get(),
                        ModBlocks.URANIUM_ORE_BLOCK.get()
                );
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.URANIUM_ORE_BLOCK.get());



    }
}