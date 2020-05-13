package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.storage.Tools;
import de.maxhenkel.storage.blocks.ModBarrelBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;

public class ModBarrelTileEntity extends LockableLootTileEntity {

    private NonNullList<ItemStack> barrelContents = NonNullList.withSize(27, ItemStack.EMPTY);
    private int numPlayersUsing;

    public ModBarrelTileEntity() {
        super(ModTileEntities.BARREL);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (!checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, barrelContents);
        }

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        barrelContents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        if (!checkLootAndRead(compound)) {
            ItemStackHelper.loadAllItems(compound, barrelContents);
        }

    }

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return barrelContents;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        barrelContents = itemsIn;
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return ChestContainer.createGeneric9X3(id, player, this);
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
        double d0 = (double) this.pos.getX() + 0.5D + (double) vec3i.getX() / 2.0D;
        double d1 = (double) this.pos.getY() + 0.5D + (double) vec3i.getY() / 2.0D;
        double d2 = (double) this.pos.getZ() + 0.5D + (double) vec3i.getZ() / 2.0D;
        world.playSound(null, d0, d1, d2, soundEvent, SoundCategory.BLOCKS, 0.5F, Tools.getVariatedPitch(world));
    }

    @Override
    protected ITextComponent getDefaultName() {
        return getBlockState().getBlock().getNameTextComponent();
    }

}
