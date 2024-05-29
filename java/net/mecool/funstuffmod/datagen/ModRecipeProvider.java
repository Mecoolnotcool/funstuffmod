package net.mecool.funstuffmod.datagen;

import net.mecool.funstuffmod.funstuffmod;
import net.mecool.funstuffmod.block.ModBlocks;
import net.mecool.funstuffmod.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> URANIUM_SMELTABLES = List.of(ModBlocks.URANIUM_ORE_BLOCK.get());

    public ModRecipeProvider(PackOutput pOutput){
        super(pOutput);
    }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreBlasting(pWriter, URANIUM_SMELTABLES, RecipeCategory.MISC, ModItems.URANIUM.get(), 1f,100, "uranium" );
        oreSmelting(pWriter, URANIUM_SMELTABLES, RecipeCategory.MISC, ModItems.URANIUM.get(), 1f,200, "uranium" );

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SALT_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.SALT.get())
                .unlockedBy(getHasName(ModItems.SALT.get()), has(ModItems.SALT.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SALT.get(), 9)
                .requires(ModBlocks.SALT_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SALT_BLOCK.get()), has(ModBlocks.SALT_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.URANIUM_PICKAXE.get())
                .pattern("UUU")
                .pattern("UDU")
                .pattern("UUU")
                .define('U', ModItems.URANIUM.get())
                .define('D', Items.DIAMOND_PICKAXE)
                .unlockedBy(getHasName(ModItems.URANIUM.get()), has(ModItems.URANIUM.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.URANIUM_SCYTHE.get())
                .pattern("UUU")
                .pattern("UDU")
                .pattern("UUU")
                .define('U', ModItems.URANIUM.get())
                .define('D', Items.DIAMOND_HOE)
                .unlockedBy(getHasName(ModItems.URANIUM.get()), has(ModItems.URANIUM.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.METAL_DETECTOR.get())
                .pattern("U")
                .pattern("I")
                .pattern("S")
                .define('U', ModItems.URANIUM.get())
                .define('I', Items.IRON_INGOT)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.URANIUM.get()), has(ModItems.URANIUM.get()))
                .save(pWriter);



    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  funstuffmod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
