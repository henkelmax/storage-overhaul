package de.maxhenkel.storage.entity;

import de.maxhenkel.storage.ModDataSerializers;
import de.maxhenkel.storage.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.ContainerMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ModChestMinecartEntity extends ContainerMinecartEntity {

    private static final DataParameter<Block> BLOCK = EntityDataManager.createKey(ModChestMinecartEntity.class, ModDataSerializers.BLOCK);

    private BlockState cachedBlock;

    public ModChestMinecartEntity(World world) {
        super(ModEntities.CHEST_MINECART, world);
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

    public ItemStack getItem() {
        return new ItemStack(getBlock().getBlock());
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(BLOCK, ModBlocks.OAK_CHEST);
    }

    @Override
    public void killMinecart(DamageSource source) {
        super.killMinecart(source);
        if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            entityDropItem(getItem());
        }
    }

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        player.openContainer(this);
        return true;
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
        super.openInventory(player);
        world.playSound(null, getPosX(), getPosY(), getPosZ(), SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        super.closeInventory(player);
        world.playSound(null, getPosX(), getPosY(), getPosZ(), SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public int getSizeInventory() {
        return 27;
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
    public Container createContainer(int id, PlayerInventory playerInventoryIn) {
        return ChestContainer.createGeneric9X3(id, playerInventoryIn, this);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return getItem().copy();
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setBlock(Registry.BLOCK.getOrDefault(new ResourceLocation(compound.getString("Block"))));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("Block", getBlock().getBlock().getRegistryName().toString());
    }

    @Override
    protected ITextComponent getProfessionName() {
        return new TranslationTextComponent("entity.storage_overhaul.chest_minecart_generic", getItem().getDisplayName());
    }
}
