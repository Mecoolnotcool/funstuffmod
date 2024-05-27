package net.mecool.funstuffmod.item;

import net.mecool.funstuffmod.funstuffmod;
import net.mecool.funstuffmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, funstuffmod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FUN_STUFF_TAB = CREATIVE_MODE_TABS.register("fun_stuff_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SALT.get()))
                    .title(Component.translatable("creativetab.fun_stuff_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.SALT.get());
                        pOutput.accept(ModItems.CONCENTRATED_SUGAR.get());
                        pOutput.accept(ModItems.URANIUM.get());
                        pOutput.accept(ModItems.CURED_MEAT.get());
                        pOutput.accept(ModItems.WEED.get());
                        pOutput.accept(ModItems.WEED_SEEDS.get());

                        pOutput.accept(ModItems.METAL_DETECTOR.get());
                        pOutput.accept(ModItems.URANIUM_PICKAXE.get());
                        pOutput.accept(ModItems.URANIUM_SCYTHE.get());

                        pOutput.accept(ModItems.URANIUM_HELMET.get());
                        pOutput.accept(ModItems.URANIUM_CHESTPLATE.get());
                        pOutput.accept(ModItems.URANIUM_LEGGINGS.get());
                        pOutput.accept(ModItems.URANIUM_BOOTS.get());

                        pOutput.accept(ModBlocks.SALT_BLOCK.get());
                        pOutput.accept(ModBlocks.URANIUM_ORE_BLOCK.get());
                        pOutput.accept(ModBlocks.SOUND_BLOCK.get());



                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}