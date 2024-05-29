package net.mecool.funstuffmod.block.entity;

import net.mecool.funstuffmod.funstuffmod;
import net.mecool.funstuffmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, funstuffmod.MOD_ID);

    public static final RegistryObject<BlockEntityType<RefineryStationBlockEntity>> REFINERY_BE =
            BLOCK_ENTITIES.register("refinery_be", () ->
                    BlockEntityType.Builder.of(RefineryStationBlockEntity::new,
                            ModBlocks.REFINERY_STATION.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
