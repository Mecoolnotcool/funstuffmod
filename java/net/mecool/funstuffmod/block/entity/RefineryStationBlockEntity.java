package net.mecool.funstuffmod.block.entity;


import net.mecool.funstuffmod.item.ModItems;
import net.mecool.funstuffmod.recipe.RefineryRecipe;
import net.mecool.funstuffmod.screen.RefineryStationMenu;
import net.mecool.funstuffmod.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RefineryStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }


//        @Override
//        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
//            return switch (slot) {
//                case 0 -> stack.getItem() == ModItems.URANIUM.get();
//                case 1 -> stack.getItem() == ModItems.REFINED_URANIUM.get();
//                case 2 -> stack.getItem() == Items.COAL;
//                default -> super.isItemValid(slot, stack);
//            };
//        }
    };


    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int INPUT_SLOT_ENERGY = 2;


    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(20, 256) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    };
    private static final int ENERGY_REQ = 5;


    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;


    public RefineryStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REFINERY_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> RefineryStationBlockEntity.this.progress;
                    case 1 -> RefineryStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }


            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> RefineryStationBlockEntity.this.progress = pValue;
                    case 1 -> RefineryStationBlockEntity.this.maxProgress = pValue;
                }
            }


            @Override
            public int getCount() {
                return 2;
            }
        };
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }


        return super.getCapability(cap, side);
    }


    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }


    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }


    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("block.funstuffmod.gem_polishing_station");
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new RefineryStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("refining_station.progress", progress);
        pTag.putInt("refining_station.energy", ENERGY_STORAGE.getEnergyStored());


        super.saveAdditional(pTag);
    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("refining_station.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("refining_station.energy"));
    }


    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {


        if (ENERGY_STORAGE.getEnergyStored() <= 0 && this.itemHandler.getStackInSlot(INPUT_SLOT_ENERGY).getItem() == Items.COAL) {
            ENERGY_STORAGE.receiveEnergy(20, false);
            this.itemHandler.extractItem(INPUT_SLOT_ENERGY, 1, false);
            setChanged(pLevel, pPos, pState);
        }
        if(hasRecipe()  ) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);
            if(!level.isClientSide() && hasEnoughEnergy()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
            if(hasProgressFinished()) {
                ExtractEnergy();
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }





    private void ExtractEnergy() {
        ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }


    private boolean hasEnoughEnergy() {
        return ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }


    private void resetProgress() {
        progress = 0;
    }


    private void craftItem() {
        Optional<RefineryRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }


    private boolean hasRecipe() {
        Optional<RefineryRecipe> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<RefineryRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(RefineryRecipe.Type.INSTANCE, inventory, level);
    }


    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }


    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }


    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }


    private void increaseCraftingProgress() {
        if(hasEnoughEnergy()) {
            progress++;
        }
    }


    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }




    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }


    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }
}
