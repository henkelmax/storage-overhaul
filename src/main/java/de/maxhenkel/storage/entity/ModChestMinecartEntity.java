package de.maxhenkel.storage.entity;

import de.maxhenkel.storage.ChestTier;
import de.maxhenkel.storage.ModDataSerializers;
import de.maxhenkel.storage.blocks.ModBlocks;
import de.maxhenkel.storage.blocks.ModChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class ModChestMinecartEntity extends AbstractMinecartEntity implements IInventory {

    private static final DataParameter<Block> BLOCK = EntityDataManager.defineId(ModChestMinecartEntity.class, ModDataSerializers.BLOCK);

    protected NonNullList<ItemStack> inventoryContents;
    private boolean dropContentsWhenDead = true;
    private BlockState cachedBlock;

    public ModChestMinecartEntity(World world) {
        super(ModEntities.CHEST_MINECART, world);

    }

    public NonNullList<ItemStack> getInventoryContents() {
        if (inventoryContents == null) {
            inventoryContents = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        }
        return inventoryContents;
    }

    public BlockState getBlock() {
        if (cachedBlock == null) {
            cachedBlock = entityData.get(BLOCK).defaultBlockState();
        }

        return cachedBlock;
    }

    public void setBlock(Block block) {
        entityData.set(BLOCK, block);
    }

    public ChestTier getChestTier() {
        Block block = getBlock().getBlock();
        if (block instanceof ModChestBlock) {
            return ((ModChestBlock) block).getTier();
        }
        return ChestTier.BASE_TIER;
    }

    public ItemStack getItem() {
        return new ItemStack(getBlock().getBlock());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(BLOCK, ModBlocks.OAK_CHEST);
    }

    @Override
    public BlockState getDefaultDisplayBlockState() {
        return getBlock();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void startOpen(PlayerEntity player) {
        level.playSound(null, getX(), getY(), getZ(), SoundEvents.CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        level.playSound(null, getX(), getY(), getZ(), SoundEvents.CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public int getContainerSize() {
        return getChestTier().numSlots();
    }

    @Override
    public AbstractMinecartEntity.Type getMinecartType() {
        return AbstractMinecartEntity.Type.CHEST;
    }

    @Override
    public int getDefaultDisplayOffset() {
        return 8;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return getItem().copy();
    }


    @Override
    protected ITextComponent getTypeName() {
        return new TranslationTextComponent("entity.storage_overhaul.chest_minecart_generic", getItem().getDisplayName());
    }

    @Override
    public void destroy(DamageSource source) {
        super.destroy(source);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            InventoryHelper.dropContents(level, this, this);
            spawnAtLocation(getItem());
        }
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : getInventoryContents()) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return getInventoryContents().get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ItemStackHelper.removeItem(getInventoryContents(), index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack itemstack = getInventoryContents().get(index);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            getInventoryContents().set(index, ItemStack.EMPTY);
            return itemstack;
        }
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        getInventoryContents().set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }

    }

    @Override
    public boolean setSlot(int inventorySlot, ItemStack itemStackIn) {
        if (inventorySlot >= 0 && inventorySlot < this.getContainerSize()) {
            setItem(inventorySlot, itemStackIn);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        if (removed) {
            return false;
        } else {
            return !(player.distanceToSqr(this) > 64.0D);
        }
    }

    @Nullable
    @Override
    public Entity changeDimension(ServerWorld world) {
        this.dropContentsWhenDead = false;
        return super.changeDimension(world);
    }

    @Override
    public void remove(boolean keepData) {
        if (!level.isClientSide && dropContentsWhenDead) {
            InventoryHelper.dropContents(level, this, this);
        }

        super.remove(keepData);
        if (!keepData) itemHandler.invalidate();
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        ItemStackHelper.saveAllItems(compound, getInventoryContents());
        compound.putString("Block", getBlock().getBlock().getRegistryName().toString());
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        setBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(compound.getString("Block"))));
        inventoryContents = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inventoryContents);
    }

    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        if (super.interact(player, hand).consumesAction()) return ActionResultType.SUCCESS;
        player.openMenu(new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent(getBlock().getBlock().getDescriptionId());
            }

            @Nullable
            @Override
            public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
                return getChestTier().getContainer(id, playerInventory, ModChestMinecartEntity.this);
            }
        });
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void applyNaturalSlowdown() {
        float motion = 0.98F;
        int i = 15 - Container.getRedstoneSignalFromContainer(this);
        motion += (float) i * 0.001F;

        setDeltaMovement(getDeltaMovement().multiply(motion, 0D, motion));
    }

    @Override
    public void clearContent() {
        getInventoryContents().clear();
    }

    private LazyOptional<?> itemHandler = LazyOptional.of(() -> new InvWrapper(this));

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (this.isAlive() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        return super.getCapability(capability, facing);
    }

    static {
        MinecraftForge.EVENT_BUS.register(new Object() {
            @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
            public void onTravelToDimension(EntityTravelToDimensionEvent event) {
                if (event.getEntity() instanceof ModChestMinecartEntity) {
                    if (event.isCanceled()) {
                        ((ModChestMinecartEntity) event.getEntity()).dropContentsWhenDead = true;
                    }
                }
            }
        });
    }

}
