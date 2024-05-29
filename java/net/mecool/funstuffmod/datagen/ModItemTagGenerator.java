package net.mecool.funstuffmod.datagen;

import net.mecool.funstuffmod.funstuffmod;
import net.mecool.funstuffmod.item.ModItems;
import net.mecool.funstuffmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, funstuffmod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
    this.tag(ItemTags.TRIMMABLE_ARMOR)
            .add(ModItems.URANIUM_HELMET.get(),ModItems.URANIUM_CHESTPLATE.get(),ModItems.URANIUM_LEGGINGS.get(),ModItems.URANIUM_BOOTS.get());

    this.tag(ModTags.Items.REFINE_STATION_FUEL).add(Items.COAL,ModItems.URANIUM.get(),Items.LAVA_BUCKET,Items.CHARCOAL);

    };


}