package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.storage.Config;
import de.maxhenkel.storage.Tools;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StorageBarrelTileEntity extends TileEntity implements IItemHandler, INameable {

    private ItemStack barrelContent = ItemStack.EMPTY;
    private ITextComponent customName;

    private Map<UUID, Long> clicks;

    public StorageBarrelTileEntity() {
        super(ModTileEntities.STORAGE_BARREL);
        clicks = new HashMap<>();
    }

    public boolean onInsert(PlayerEntity player) {
        boolean flag = false;
        if (world.getGameTime() - clicks.getOrDefault(player.getUniqueID(), 0L) <= 4) {
            flag = true;
        }
        clicks.put(player.getUniqueID(), world.getGameTime());
        return flag;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.getPlayers(player -> player.getDistanceSq(getPos().getX(), getPos().getY(), getPos().getZ()) <= 128D * 128D).forEach(this::syncContents);
        }
    }

    public void syncContents(ServerPlayerEntity player) {
        player.connection.sendPacket(getUpdatePacket());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);

        compound.put("Item", Tools.writeOverstackedItem(new CompoundNBT(), barrelContent));

        if (this.customName != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(customName));
        }

        return compound;
    }

    @Override
    public void func_230337_a_(BlockState blockState, CompoundNBT compound) {
        super.func_230337_a_(blockState, compound);
        barrelContent = Tools.readOverstackedItem(compound.getCompound("Item"));


        if (compound.contains("CustomName")) {
            customName = ITextComponent.Serializer.func_240644_b_(compound.getString("CustomName"));
        }
    }

    public ItemStack getBarrelContent() {
        return barrelContent;
    }

    public void setBarrelContent(ItemStack barrelContent) {
        this.barrelContent = barrelContent;
        markDirty();
    }

    public void addCount(int amount) {
        if (barrelContent.isEmpty()) {
            return;
        }
        barrelContent.grow(amount);
        markDirty();
    }

    public void removeCount(int amount) {
        if (barrelContent.isEmpty()) {
            return;
        }
        barrelContent.shrink(amount);
        markDirty();
    }

    public boolean isEmpty() {
        return barrelContent.isEmpty();
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return barrelContent;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        ItemStack ret = stack.copy();
        int freeSpace = Config.SERVER.STORAGE_BARREL_SIZE.get() - getBarrelContent().getCount();
        int amount = Math.min(stack.getCount(), freeSpace);
        if (Tools.isStackable(barrelContent, stack)) {
            if (!simulate) {
                addCount(amount);
            }
            ret.shrink(amount);
        } else if (isEmpty() && !stack.isEmpty()) {
            if (!simulate) {
                ItemStack insert = stack.copy();
                insert.setCount(amount);
                setBarrelContent(insert);
            }
            ret.shrink(amount);
        }
        if (ret.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return ret;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack content = getBarrelContent().copy();
        int count = Math.min(content.getCount(), amount);
        if (!simulate) {
            removeCount(count);
        }
        content.setCount(count);
        if (content.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return content;
    }

    @Override
    public int getSlotLimit(int slot) {
        return Config.SERVER.STORAGE_BARREL_SIZE.get();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }

    public void setCustomName(ITextComponent customName) {
        this.customName = customName;
    }

    @Override
    public ITextComponent getName() {
        return customName != null ? customName : new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        func_230337_a_(getBlockState(), pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (!removed && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> this).cast();
        }
        return super.getCapability(cap, side);
    }
}
