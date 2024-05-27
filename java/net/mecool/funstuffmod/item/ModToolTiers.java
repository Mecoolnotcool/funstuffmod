package net.mecool.funstuffmod.item;

import net.mecool.funstuffmod.funstuffmod;
import net.mecool.funstuffmod.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier URANIUM = TierSortingRegistry.registerTier(
            new ForgeTier(5, 2000, 200f,5f, 25,
                    ModTags.Blocks.NEEDS_URANIUM_TOOL, () -> Ingredient.of(ModItems.URANIUM.get())),
            new ResourceLocation(funstuffmod.MOD_ID, "uranium"), List.of(Tiers.NETHERITE), List.of());

}
