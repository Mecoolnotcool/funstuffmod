package net.mecool.funstuffmod.block;

import net.mecool.funstuffmod.block.custom.GemPolishingStationBlock;
import net.mecool.funstuffmod.block.custom.SoundBlock;
import net.mecool.funstuffmod.block.custom.WeedCropBlock;
import net.mecool.funstuffmod.funstuffmod;
import net.mecool.funstuffmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, funstuffmod.MOD_ID);

    public static final RegistryObject<Block> SALT_BLOCK = registerBlock("salt_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CALCITE)));

    public static final RegistryObject<Block> SOUND_BLOCK = registerBlock("sound_block",
            () -> new SoundBlock(BlockBehaviour.Properties.copy(Blocks.CALCITE)));


    public static final RegistryObject<Block> URANIUM_ORE_BLOCK = registerBlock("uranium_ore_block",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK)
                    .strength(0.5f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));

    public static final RegistryObject<Block> WEED_CROP = BLOCKS.register("weed_crop",
            () -> new WeedCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));

    public static final RegistryObject<Block> GEM_POLISHING_STATION = registerBlock("gem_polishing_station",
            () -> new GemPolishingStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
