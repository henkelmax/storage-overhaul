package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.storage.ChestTier;
import de.maxhenkel.storage.Tools;
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
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;

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
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);

        compound.putInt("Tier", getTier().getTier());

        if (!checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, getItems());
        }

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);

        tier = ChestTier.byTier(compound.getInt("Tier"));

        barrelContents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        if (!checkLootAndRead(compound)) {
            ItemStackHelper.loadAllItems(compound, barrelContents);
        }
    }

    @Override
    public int getSizeInventory() {
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
    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (numPlayersUsing < 0) {
                numPlayersUsing = 0;
            }

            numPlayersUsing++;
            BlockState blockstate = getBlockState();
            if (!blockstate.get(BarrelBlock.PROPERTY_OPEN)) {
                playSound(blockstate, SoundEvents.BLOCK_BARREL_OPEN);
                setOpenProperty(blockstate, true);
            }
            scheduleTick();
        }

    }

    private void scheduleTick() {
        world.getPendingBlockTicks().scheduleTick(getPos(), getBlockState().getBlock(), 5);
    }

    public void barrelTick() {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        numPlayersUsing = ChestTileEntity.calculatePlayersUsing(world, this, x, y, z);
        if (numPlayersUsing > 0) {
            scheduleTick();
        } else {
            BlockState blockstate = getBlockState();
            if (!(blockstate.getBlock() instanceof ModBarrelBlock)) {
                remove();
                return;
            }

            boolean flag = blockstate.get(BarrelBlock.PROPERTY_OPEN);
            if (flag) {
                playSound(blockstate, SoundEvents.BLOCK_BARREL_CLOSE);
                setOpenProperty(blockstate, false);
            }
        }

    }

    @Override
    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            numPlayersUsing--;
        }
    }

    private void setOpenProperty(BlockState blockState, boolean open) {
        world.setBlockState(getPos(), blockState.with(BarrelBlock.PROPERTY_OPEN, open), 3);
    }

    private void playSound(BlockState blockState, SoundEvent soundEvent) {
        Vec3i vec3i = blockState.get(BarrelBlock.PROPERTY_FACING).getDirectionVec();
        double x = (double) this.pos.getX() + 0.5D + (double) vec3i.getX() / 2D;
        double y = (double) this.pos.getY() + 0.5D + (double) vec3i.getY() / 2D;
        double z = (double) this.pos.getZ() + 0.5D + (double) vec3i.getZ() / 2D;
        world.playSound(null, x, y, z, soundEvent, SoundCategory.BLOCKS, 0.5F, Tools.getVariatedPitch(world));
    }

    @Override
    protected ITextComponent getDefaultName() {
        return getBlockState().getBlock().getNameTextComponent();
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

}
