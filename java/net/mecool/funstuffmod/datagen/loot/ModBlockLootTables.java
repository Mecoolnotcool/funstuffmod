package net.mecool.funstuffmod.datagen.loot;

import net.mecool.funstuffmod.block.ModBlocks;
import net.mecool.funstuffmod.block.custom.WeedCropBlock;
import net.mecool.funstuffmod.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.SALT_BLOCK.get());
        this.dropSelf(ModBlocks.SOUND_BLOCK.get());



        this.add(ModBlocks.URANIUM_ORE_BLOCK.get(),
                block -> createCopperLikeOreDrops(ModBlocks.URANIUM_ORE_BLOCK.get(), ModItems.URANIUM.get()));

        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.WEED_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WeedCropBlock.AGE, 5));

        this.add(ModBlocks.WEED_CROP.get(), createCropDrops(ModBlocks.WEED_CROP.get(), ModItems.WEED.get(),
                ModItems.WEED_SEEDS.get(), lootitemcondition$builder));

        this.dropSelf(ModBlocks.REFINERY_STATION.get());
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}