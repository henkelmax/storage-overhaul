package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.storage.Tools;
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
            Direction direction = getBlockState().get(AdvancedShulkerBoxBlock.FACING);
            AxisAlignedBB axisalignedbb = VoxelShapes.fullCube().getBoundingBox().expand(0.5F * (float) direction.getXOffset(), 0.5F * (float) direction.getYOffset(), 0.5F * (float) direction.getZOffset()).contract(direction.getXOffset(), direction.getYOffset(), direction.getZOffset());
            return world.hasNoCollisions(axisalignedbb.offset(pos.offset(direction)));
        } else {
            return true;
        }
    }

    @Override
    public int getSizeInventory() {
        return items.size();
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
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
            return super.receiveClientEvent(id, type);
        }
    }

    private void updateNeighbors() {
        getBlockState().func_235734_a_(getWorld(), getPos(), 3);
    }

    @Override
    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (openCount < 0) {
                openCount = 0;
            }

            openCount++;
            world.addBlockEvent(pos, getBlockState().getBlock(), 1, openCount);
            if (openCount == 1) {
                world.playSound(null, pos, getOpenSound(), SoundCategory.BLOCKS, 0.5F, Tools.getVariatedPitch(world));
            }
        }

    }

    public static SoundEvent getOpenSound() {
        return SoundEvents.ENTITY_SHULKER_OPEN; //BLOCK_SHULKER_BOX_OPEN;
    }

    public static SoundEvent getCloseSound() {
        return SoundEvents.ENTITY_SHULKER_CLOSE; //BLOCK_SHULKER_BOX_CLOSE;
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            openCount--;
            world.addBlockEvent(pos, getBlockState().getBlock(), 1, openCount);
            if (openCount <= 0) {
                world.playSound(null, pos, getCloseSound(), SoundCategory.BLOCKS, 0.5F, Tools.getVariatedPitch(world));
            }
        }

    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
    }

    @Override
    public void func_230337_a_(BlockState blockState, CompoundNBT compound) {
        super.func_230337_a_(blockState, compound);
        loadFromNbt(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        return saveToNbt(compound);
    }

    public void loadFromNbt(CompoundNBT compound) {
        items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        if (!checkLootAndRead(compound) && compound.contains("Items", 9)) {
            ItemStackHelper.loadAllItems(compound, items);
        }

    }

    public CompoundNBT saveToNbt(CompoundNBT compound) {
        if (!checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, items, false);
        }

        return compound;
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
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return !(Block.getBlockFromItem(itemStackIn.getItem()) instanceof ShulkerBoxBlock);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
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