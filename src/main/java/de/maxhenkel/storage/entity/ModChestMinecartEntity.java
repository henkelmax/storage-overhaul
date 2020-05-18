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
import net.minecraft.world.dimension.DimensionType;
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

    private static final DataParameter<Block> BLOCK = EntityDataManager.createKey(ModChestMinecartEntity.class, ModDataSerializers.BLOCK);

    protected NonNullList<ItemStack> inventoryContents;
    private boolean dropContentsWhenDead = true;
    private BlockState cachedBlock;

    public ModChestMinecartEntity(World world) {
        super(ModEntities.CHEST_MINECART, world);

    }

    public NonNullList<ItemStack> getInventoryContents() {
        if (inventoryContents == null) {
            inventoryContents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        }
        return inventoryContents;
    }

    public BlockState getBlock() {
        if (cachedBlock == null) {
            cachedBlock = dataManager.get(BLOCK).getDefaultState();
        }

        return cachedBlock;
    }

    public void setBlock(Block block) {
        dataManager.set(BLOCK, block);
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
    protected void registerData() {
        super.registerData();
        dataManager.register(BLOCK, ModBlocks.OAK_CHEST);
    }

    @Override
    public BlockState getDefaultDisplayTile() {
        return getBlock();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void openInventory(PlayerEntity player) {
        world.playSound(null, getPosX(), getPosY(), getPosZ(), SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        world.playSound(null, getPosX(), getPosY(), getPosZ(), SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public int getSizeInventory() {
        return getChestTier().numSlots();
    }

    @Override
    public AbstractMinecartEntity.Type getMinecartType() {
        return AbstractMinecartEntity.Type.CHEST;
    }

    @Override
    public int getDefaultDisplayTileOffset() {
        return 8;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return getItem().copy();
    }


    @Override
    protected ITextComponent getProfessionName() {
        return new TranslationTextComponent("entity.storage_overhaul.chest_minecart_generic", getItem().getDisplayName());
    }

    @Override
    public void killMinecart(DamageSource source) {
        super.killMinecart(source);
        if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            InventoryHelper.dropInventoryItems(world, this, this);
            entityDropItem(getItem());
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
    public ItemStack getStackInSlot(int index) {
        return getInventoryContents().get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(getInventoryContents(), index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack itemstack = getInventoryContents().get(index);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            getInventoryContents().set(index, ItemStack.EMPTY);
            return itemstack;
        }
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        getInventoryContents().set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }

    }

    @Override
    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
        if (inventorySlot >= 0 && inventorySlot < this.getSizeInventory()) {
            setInventorySlotContents(inventorySlot, itemStackIn);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (removed) {
            return false;
        } else {
            return !(player.getDistanceSq(this) > 64.0D);
        }
    }

    @Nullable
    @Override
    public Entity changeDimension(DimensionType destination, net.minecraftforge.common.util.ITeleporter teleporter) {
        dropContentsWhenDead = false;
        return super.changeDimension(destination, teleporter);
    }

    @Override
    public void remove(boolean keepData) {
        if (!world.isRemote && dropContentsWhenDead) {
            InventoryHelper.dropInventoryItems(world, this, this);
        }

        super.remove(keepData);
        if (!keepData) itemHandler.invalidate();
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        ItemStackHelper.saveAllItems(compound, getInventoryContents());
        compound.putString("Block", getBlock().getBlock().getRegistryName().toString());
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(compound.getString("Block"))));
        inventoryContents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inventoryContents);
    }

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        if (super.processInitialInteract(player, hand)) return true;
        player.openContainer(new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return getBlock().getBlock().getNameTextComponent();
            }

            @Nullable
            @Override
            public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
                return getChestTier().getContainer(id, playerInventory, ModChestMinecartEntity.this);
            }
        });
        return true;
    }

    @Override
    protected void applyDrag() {
        float motion = 0.98F;
        int i = 15 - Container.calcRedstoneFromInventory(this);
        motion += (float) i * 0.001F;

        setMotion(getMotion().mul(motion, 0D, motion));
    }

    @Override
    public void clear() {
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
