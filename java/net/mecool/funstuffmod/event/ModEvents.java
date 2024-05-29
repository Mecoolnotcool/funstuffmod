package net.mecool.funstuffmod.event;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.mecool.funstuffmod.funstuffmod;
import net.mecool.funstuffmod.item.ModItems;

import net.mecool.funstuffmod.villager.ModVillagers;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = funstuffmod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ModItems.WEED_SEEDS.get(), 2),
                    10, 1, 0.02f));
        }

        if(event.getType() == VillagerProfession.ARMORER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 64),
                    new ItemStack(ModItems.URANIUM_CHESTPLATE.get(), 1),
                    1, 1, 1f));
        }

        if(event.getType() == ModVillagers.FUN_STUFF_DEALER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ModItems.WEED_SEEDS.get(), 10),
                    10, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.WEED.get(), 20),
                    new ItemStack(Items.EMERALD, 10),
                    10, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.WEED_SEEDS.get(), 20),
                    new ItemStack(Items.EMERALD, 5),
                    10, 1, 0.02f));

        }
    }



    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 10),
                new ItemStack(ModItems.WEED.get(), 10),
                3, 2, 0.2f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 64),
                new ItemStack(ModItems.URANIUM_SCYTHE.get(), 1),
                1, 12, 0.15f));
    }
}