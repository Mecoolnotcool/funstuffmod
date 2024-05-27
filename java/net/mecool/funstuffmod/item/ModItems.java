package net.mecool.funstuffmod.item;

import net.mecool.funstuffmod.block.ModBlocks;
import net.mecool.funstuffmod.funstuffmod;
import net.mecool.funstuffmod.item.custom.FuelItem;
import net.mecool.funstuffmod.item.custom.MetalDetectorItem;
import net.mecool.funstuffmod.item.custom.ModArmorItem;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, funstuffmod.MOD_ID);

    public static final RegistryObject<Item> SALT = ITEMS.register("salt",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CONCENTRATED_SUGAR = ITEMS.register("concentrated_sugar",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> URANIUM = ITEMS.register("uranium",
            () -> new FuelItem(new Item.Properties(), 4000));

    public static final RegistryObject<Item> CURED_MEAT = ITEMS.register("cured_meat",
            () -> new Item(new Item.Properties().food(ModFoods.CURED_MEAT)));

    public static final RegistryObject<Item> WEED = ITEMS.register("weed",
            () -> new Item(new Item.Properties().food(ModFoods.WEED)));

    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(250)));

    public static final RegistryObject<Item> URANIUM_PICKAXE = ITEMS.register("uranium_pickaxe",
            () -> new PickaxeItem(ModToolTiers.URANIUM, 1, 1, new Item.Properties()));
    public static final RegistryObject<Item> URANIUM_SCYTHE = ITEMS.register("uranium_scythe",
            () -> new HoeItem(ModToolTiers.URANIUM, 35, 20, new Item.Properties()));
    public static final RegistryObject<Item> URANIUM_AXE = ITEMS.register("uranium_axe",
            () -> new AxeItem(ModToolTiers.URANIUM, 35, 20, new Item.Properties()));

    public static final RegistryObject<Item> WEED_SEEDS = ITEMS.register("weed_seeds",
            () -> new ItemNameBlockItem(ModBlocks.WEED_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> URANIUM_HELMET = ITEMS.register("uranium_helmet",
            () -> new ModArmorItem(ModArmorMaterials.URANIUM, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> URANIUM_CHESTPLATE = ITEMS.register("uranium_chestplate",
            () -> new ArmorItem(ModArmorMaterials.URANIUM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> URANIUM_LEGGINGS = ITEMS.register("uranium_leggings",
            () -> new ArmorItem(ModArmorMaterials.URANIUM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> URANIUM_BOOTS = ITEMS.register("uranium_boots",
            () -> new ArmorItem(ModArmorMaterials.URANIUM, ArmorItem.Type.BOOTS, new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}