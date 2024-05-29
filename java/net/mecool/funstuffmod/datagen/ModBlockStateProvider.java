package net.mecool.funstuffmod.datagen;

import net.mecool.funstuffmod.block.custom.WeedCropBlock;
import net.mecool.funstuffmod.funstuffmod;
import net.mecool.funstuffmod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, funstuffmod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.SALT_BLOCK);
        blockWithItem(ModBlocks.SOUND_BLOCK);
        blockWithItem(ModBlocks.URANIUM_ORE_BLOCK);

        makeWeedCrop((CropBlock) ModBlocks.WEED_CROP.get(), "weed_stage", "weed_stage");

        simpleBlock(ModBlocks.REFINERY_STATION.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/refinery_station")));
    }

    public void makeWeedCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> weedStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] weedStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((WeedCropBlock) block).getAgeProperty()),
                new ResourceLocation(funstuffmod.MOD_ID, "block/" + textureName + state.getValue(((WeedCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

}
