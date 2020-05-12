package de.maxhenkel.storage.entity;

import de.maxhenkel.storage.blocks.ModChestBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.ContainerMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.IPacket;
import net.minecraft.util.*;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ModChestMinecartEntity extends ContainerMinecartEntity {

    private ModChestBlock chest;

    public ModChestMinecartEntity(EntityType<? extends ContainerMinecartEntity> entityType, World world, ModChestBlock chest) {
        super(entityType, world);
        this.chest = chest;
    }

    @Override
    public void killMinecart(DamageSource source) {
        super.killMinecart(source);
        if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            entityDropItem(chest);
        }
    }

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        player.openContainer(this);
        return true;
    }

    @Override
    public BlockState getDefaultDisplayTile() {
        return chest.getDefaultState().with(ChestBlock.FACING, Direction.NORTH);
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
}
