package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.corelib.sound.SoundUtils;
import de.maxhenkel.storage.ChestTier;
import de.maxhenkel.storage.blocks.ModBarrelBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class ModBarrelTileEntity extends LockableLootTileEntity {

    @Nullable
    private NonNullList<ItemStack> barrelContents;
    private int numPlayersUsing;

    @Nullable
    private ChestTier tier;

    public ModBarrelTileEntity(ChestTier tier) {
        super(ModTileEntities.BARREL);
        this.tier = tier;
    }

    public ChestTier getTier() {
        return tier;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        if (barrelContents == null) {
            barrelContents = NonNullList.withSize(getTier().numSlots(), ItemStack.EMPTY);
        }
        return barrelContents;
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);

        compound.putInt("Tier", getTier().getTier());

        if (!trySaveLootTable(compound)) {
            ItemStackHelper.saveAllItems(compound, getItems());
        }

        return compound;
    }

    @Override
    public void load(BlockState blockState, CompoundNBT compound) {
        super.load(blockState, compound);

        tier = ChestTier.byTier(compound.getInt("Tier"));

        barrelContents = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!tryLoadLootTable(compound)) {
            ItemStackHelper.loadAllItems(compound, barrelContents);
        }
    }

    @Override
    public int getContainerSize() {
        return getTier().numSlots();
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        barrelContents = itemsIn;
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return getTier().getContainer(id, player, this);
    }

    @Override
    public void startOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (numPlayersUsing < 0) {
                numPlayersUsing = 0;
            }

            numPlayersUsing++;
            BlockState blockstate = getBlockState();
            if (!blockstate.getValue(BarrelBlock.OPEN)) {
                playSound(blockstate, SoundEvents.BARREL_OPEN);
                setOpenProperty(blockstate, true);
            }
            scheduleTick();
        }

    }

    private void scheduleTick() {
        level.getBlockTicks().scheduleTick(getBlockPos(), getBlockState().getBlock(), 5);
    }

    public void barrelTick() {
        int x = worldPosition.getX();
        int y = worldPosition.getY();
        int z = worldPosition.getZ();
        numPlayersUsing = ChestTileEntity.getOpenCount(level, this, x, y, z);
        if (numPlayersUsing > 0) {
            scheduleTick();
        } else {
            BlockState blockstate = getBlockState();
            if (!(blockstate.getBlock() instanceof ModBarrelBlock)) {
                setRemoved();
                return;
            }

            boolean flag = blockstate.getValue(BarrelBlock.OPEN);
            if (flag) {
                playSound(blockstate, SoundEvents.BARREL_CLOSE);
                setOpenProperty(blockstate, false);
            }
        }

    }

    @Override
    public void stopOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            numPlayersUsing--;
        }
    }

    private void setOpenProperty(BlockState blockState, boolean open) {
        level.setBlock(getBlockPos(), blockState.setValue(BarrelBlock.OPEN, open), 3);
    }

    private void playSound(BlockState blockState, SoundEvent soundEvent) {
        Vector3i vec3i = blockState.getValue(BarrelBlock.FACING).getNormal();
        double x = (double) this.worldPosition.getX() + 0.5D + (double) vec3i.getX() / 2D;
        double y = (double) this.worldPosition.getY() + 0.5D + (double) vec3i.getY() / 2D;
        double z = (double) this.worldPosition.getZ() + 0.5D + (double) vec3i.getZ() / 2D;
        level.playSound(null, x, y, z, soundEvent, SoundCategory.BLOCKS, 0.5F, SoundUtils.getVariatedPitch(level));
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent(getBlockState().getBlock().getDescriptionId());
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        load(getBlockState(), pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

}
