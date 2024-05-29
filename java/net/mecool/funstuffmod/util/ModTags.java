package net.mecool.funstuffmod.util;

import net.mecool.funstuffmod.funstuffmod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> METAL_DETECTOR_VALUABLES = tag("metal_detector_valuables");
        public static final TagKey<Block> NEEDS_URANIUM_TOOL = tag("needs_uranium_tool");



        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(funstuffmod.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> REFINE_STATION_FUEL = tag("refine_station_fuel");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(funstuffmod.MOD_ID, name));
        }
    }
}