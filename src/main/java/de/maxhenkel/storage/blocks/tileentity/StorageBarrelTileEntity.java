package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.corelib.entity.EntityUtils;
import de.maxhenkel.corelib.item.ItemUtils;
import de.maxhenkel.storage.Main;
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
        if (level.getGameTime() - clicks.getOrDefault(player.getUUID(), 0L) <= 4) {
            flag = true;
        }
        clicks.put(player.getUUID(), level.getGameTime());
        return flag;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level instanceof ServerWorld) {
            EntityUtils.forEachPlayerAround((ServerWorld) level, getBlockPos(), 128D, this::syncContents);
        }
    }

    public void syncContents(ServerPlayerEntity player) {
        player.connection.send(getUpdatePacket());
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);

        compound.put("Item", ItemUtils.writeOverstackedItem(new CompoundNBT(), barrelContent));

        if (this.customName != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(customName));
        }

        return compound;
    }

    @Override
    public void load(BlockState blockState, CompoundNBT compound) {
        super.load(blockState, compound);
        barrelContent = ItemUtils.readOverstackedItem(compound.getCompound("Item"));


        if (compound.contains("CustomName")) {
            customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }
    }

    public ItemStack getBarrelContent() {
        return barrelContent;
    }

    public void setBarrelContent(ItemStack barrelContent) {
        this.barrelContent = barrelContent;
        setChanged();
    }

    public void addCount(int amount) {
        if (barrelContent.isEmpty()) {
            return;
        }
        barrelContent.grow(amount);
        setChanged();
    }

    public void removeCount(int amount) {
        if (barrelContent.isEmpty()) {
            return;
        }
        barrelContent.shrink(amount);
        setChanged();
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
        int freeSpace = Main.SERVER_CONFIG.storageBarrelSize.get() - getBarrelContent().getCount();
        int amount = Math.min(stack.getCount(), freeSpace);
        if (ItemUtils.isStackable(barrelContent, stack)) {
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
        return Main.SERVER_CONFIG.storageBarrelSize.get();
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
        return customName != null ? customName : new TranslationTextComponent(getBlockState().getBlock().getDescriptionId());
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
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

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (!remove && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> this).cast();
        }
        return super.getCapability(cap, side);
    }
}
