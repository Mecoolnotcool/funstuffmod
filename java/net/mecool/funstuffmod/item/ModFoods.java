package net.mecool.funstuffmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static  final FoodProperties CURED_MEAT = new FoodProperties.Builder().nutrition(6).meat()
            .saturationMod(0.6f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100), 0.5f).build();
    public static  final FoodProperties WEED = new FoodProperties.Builder().nutrition(8).alwaysEat()
            .saturationMod(10f).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1000), 0.8f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1000), 1f).effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 1000), 0.1f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1000), 1f).build();
}
