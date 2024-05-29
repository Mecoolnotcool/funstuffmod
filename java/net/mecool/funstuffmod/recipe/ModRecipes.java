package net.mecool.funstuffmod.recipe;

import net.mecool.funstuffmod.funstuffmod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, funstuffmod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<RefineryRecipe>> REFINING_SERIALIZER =
            SERIALIZERS.register("refining", () -> RefineryRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}