package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.corelib.sound.SoundUtils;
import de.maxhenkel.storage.blocks.AdvancedShulkerBoxBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ShulkerBoxContainer;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class AdvancedShulkerBoxTileEnitity extends LockableLootTileEntity implements ISidedInventory, ITickableTileEntity {

    private static final int[] SLOTS = IntStream.range(0, 27).toArray();
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private int openCount;
    private ShulkerBoxTileEntity.AnimationStatus animationStatus = ShulkerBoxTileEntity.AnimationStatus.CLOSED;
    private float progress;
    private float progressOld;
    private INBT enchantments;

    @Nullable
    private DyeColor color;

    public AdvancedShulkerBoxTileEnitity(DyeColor colorIn) {
        super(ModTileEntities.SHULKER_BOX);
        this.color = colorIn;
    }

    @Override
    public void tick() {
        progressOld = progress;
        switch (animationStatus) {
            case CLOSED:
                progress = 0F;
                break;
            case OPENING:
                progress += 0.25F;
                if (progress >= 1F) {
                    animationStatus = ShulkerBoxTileEntity.AnimationStatus.OPENED;
                    progress = 1F;
                    updateNeighbors();
                }
                break;
            case CLOSING:
                progress -= 0.25F;
                if (progress <= 0F) {
                    animationStatus = ShulkerBoxTileEntity.AnimationStatus.CLOSED;
                    progress = 0F;
                    updateNeighbors();
                }
                break;
            case OPENED:
                progress = 1F;
        }
    }

    public boolean canOpen() {
        if (animationStatus == ShulkerBoxTileEntity.AnimationStatus.CLOSED) {
            Direction direction = getBlockState().getValue(AdvancedShulkerBoxBlock.FACING);
            AxisAlignedBB axisalignedbb = VoxelShapes.block().bounds().expandTowards(0.5F * (float) direction.getStepX(), 0.5F * (float) direction.getStepY(), 0.5F * (float) direction.getStepZ()).contract(direction.getStepX(), direction.getStepY(), direction.getStepZ());
            return level.noCollision(axisalignedbb.move(worldPosition.relative(direction)));
        } else {
            return true;
        }
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            openCount = type;
            if (type == 0) {
                animationStatus = ShulkerBoxTileEntity.AnimationStatus.CLOSING;
                updateNeighbors();
            }

            if (type == 1) {
                animationStatus = ShulkerBoxTileEntity.AnimationStatus.OPENING;
                updateNeighbors();
            }

            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    private void updateNeighbors() {
        getBlockState().updateNeighbourShapes(getLevel(), getBlockPos(), 3);
    }

    @Override
    public void startOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (openCount < 0) {
                openCount = 0;
            }

            openCount++;
            level.blockEvent(worldPosition, getBlockState().getBlock(), 1, openCount);
            if (openCount == 1) {
                level.playSound(null, worldPosition, getOpenSound(), SoundCategory.BLOCKS, 0.5F, SoundUtils.getVariatedPitch(level));
            }
        }

    }

    public static SoundEvent getOpenSound() {
        return SoundEvents.SHULKER_OPEN;
    }

    public static SoundEvent getCloseSound() {
        return SoundEvents.SHULKER_CLOSE;
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            openCount--;
            level.blockEvent(worldPosition, getBlockState().getBlock(), 1, openCount);
            if (openCount <= 0) {
                level.playSound(null, worldPosition, getCloseSound(), SoundCategory.BLOCKS, 0.5F, SoundUtils.getVariatedPitch(level));
            }
        }

    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent(getBlockState().getBlock().getDescriptionId());
    }

    @Override
    public void load(BlockState blockState, CompoundNBT compound) {
        super.load(blockState, compound);
        loadFromNbt(compound);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        return saveToNbt(compound);
    }

    public void loadFromNbt(CompoundNBT compound) {
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!tryLoadLootTable(compound) && compound.contains("Items", 9)) {
            ItemStackHelper.loadAllItems(compound, items);
        }
        INBT enchantmentsNbt = compound.get("Enchantments");
        if (enchantmentsNbt != null) {
            enchantments = enchantmentsNbt.copy();
        }
    }

    public CompoundNBT saveToNbt(CompoundNBT compound) {
        if (!trySaveLootTable(compound)) {
            ItemStackHelper.saveAllItems(compound, items, false);
        }
        if (enchantments != null) {
            compound.put("Enchantments", enchantments);
        }
        return compound;
    }

    public void readFromItemStackNbt(CompoundNBT nbtIn) {
        INBT nbt = nbtIn.get("Enchantments");
        if (nbt != null) {
            this.enchantments = nbt.copy();
        }
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        items = itemsIn;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return !(Block.byItem(itemStackIn.getItem()) instanceof ShulkerBoxBlock);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    public float getProgress(float partialTicks) {
        return MathHelper.lerp(partialTicks, progressOld, progress);
    }

    public DyeColor getColor() {
        if (color == null) {
            color = ((AdvancedShulkerBoxBlock) getBlockState().getBlock()).getColor();
        }
        return color;
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new ShulkerBoxContainer(id, player, this);
    }

    @Override
    protected IItemHandler createUnSidedHandler() {
        return new SidedInvWrapper(this, Direction.UP);
    }
}