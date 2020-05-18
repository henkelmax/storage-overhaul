package de.maxhenkel.storage.blocks;

import com.google.common.collect.Maps;
import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.Tools;
import de.maxhenkel.storage.blocks.tileentity.StorageBarrelTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.*;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class StorageBarrelBlock extends ContainerBlock implements IItemBlock {

    public static final DirectionProperty PROPERTY_FACING = BlockStateProperties.FACING;

    protected StorageBarrelBlock(String name) {
        super(Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation(Main.MODID, name));
        setDefaultState(stateContainer.getBaseState().with(PROPERTY_FACING, Direction.NORTH));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(getRegistryName());
    }

    @SubscribeEvent
    public void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        BlockState state = event.getWorld().getBlockState(event.getPos());
        if (state.getBlock() != this) {
            return;
        }

        if (event.getFace().equals(state.get(PROPERTY_FACING))) {
            if (event.getPlayer().isCreative()) {
                event.setCanceled(true);
                onFrontClicked(state, event.getWorld(), event.getPos(), event.getPlayer());
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!hit.getFace().equals(state.get(PROPERTY_FACING))) {
            return ActionResultType.PASS;
        }

        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }

        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (!(tileentity instanceof StorageBarrelTileEntity)) {
            return ActionResultType.SUCCESS;
        }

        StorageBarrelTileEntity barrel = (StorageBarrelTileEntity) tileentity;

        if (barrel.onInsert(player)) {
            boolean inserted = false;
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                ItemStack rest = barrel.insertItem(0, stack, false);
                if (!ItemStack.areItemStacksEqual(rest, stack)) {
                    player.inventory.setInventorySlotContents(i, rest);
                    inserted = true;
                }
            }

            if (inserted) {
                playInsertSound(worldIn, player);
            }
            return ActionResultType.SUCCESS;
        }

        ItemStack held = player.getHeldItem(handIn);

        ItemStack remaining = barrel.insertItem(0, held, false);

        if (ItemStack.areItemStacksEqual(remaining, held)) {
            return ActionResultType.SUCCESS;
        }

        held.setCount(remaining.getCount());
        playInsertSound(worldIn, player);

        return ActionResultType.SUCCESS;
    }

    public void playInsertSound(World world, PlayerEntity player) {
        world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, Tools.getVariatedPitch(world));
    }

    public void playExtractSound(World world, PlayerEntity player) {
        world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 1.3F);
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        super.onBlockClicked(state, worldIn, pos, player);
        if (!player.isCreative()) {
            onFrontClicked(state, worldIn, pos, player);
        }
    }

    public void onFrontClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if (worldIn.isRemote) {
            return;
        }
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (!(tileentity instanceof StorageBarrelTileEntity)) {
            return;
        }

        StorageBarrelTileEntity barrel = (StorageBarrelTileEntity) tileentity;

        if (barrel.isEmpty()) {
            return;
        }

        ItemStack barrelContent = barrel.getBarrelContent();

        int amount = Math.min(player.isSneaking() ? barrelContent.getMaxStackSize() : 1, barrelContent.getCount());

        ItemStack heldItem = player.getHeldItemMainhand();

        if (heldItem.isEmpty()) {
            ItemStack newItem = barrelContent.copy();
            newItem.setCount(amount);
            barrel.removeCount(amount);
            player.inventory.add(player.inventory.currentItem, newItem);
            playExtractSound(worldIn, player);
            return;
        } else if (Tools.isStackable(heldItem, barrelContent)) {
            int space = Math.max(heldItem.getMaxStackSize() - heldItem.getCount(), 0);

            if (space > 0) {
                amount = Math.min(space, amount);
                ItemStack newItem = heldItem.copy();
                newItem.setCount(amount);
                barrel.removeCount(amount);
                player.inventory.add(player.inventory.currentItem, newItem);
                playExtractSound(worldIn, player);
                return;
            }
        }

        ItemStack newItem = barrelContent.copy();
        newItem.setCount(amount);
        barrel.removeCount(amount);

        player.inventory.addItemStackToInventory(newItem);

        if (!newItem.isEmpty()) {
            spawnAsEntity(worldIn, pos.offset(state.get(PROPERTY_FACING)), newItem);
        }

        playExtractSound(worldIn, player);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof IInventory) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof StorageBarrelTileEntity) {
                ((StorageBarrelTileEntity) tileentity).setCustomName(stack.getDisplayName());
            }
        }

    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return calcRedstone((StorageBarrelTileEntity) worldIn.getTileEntity(pos));
    }

    public int calcRedstone(StorageBarrelTileEntity barrel) {
        if (barrel.isEmpty()) {
            return 0;
        }
        float percentage = (float) barrel.getBarrelContent().getCount() / (float) barrel.getSlotLimit(0);
        return MathHelper.floor(percentage * 14F) + 1;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(PROPERTY_FACING, rot.rotate(state.get(PROPERTY_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(PROPERTY_FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PROPERTY_FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(PROPERTY_FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new StorageBarrelTileEntity();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.get(PROPERTY_FACING));
    }

    private static final Map<Direction, VoxelShape> SHAPES;

    static {
        Map<Direction, VoxelShape> shapes = new HashMap<>();
        shapes.put(Direction.NORTH,
                Tools.combine(
                        Block.makeCuboidShape(0D, 0D, 1D, 16D, 16D, 16D),
                        Block.makeCuboidShape(0D, 0D, 0D, 1D, 16D, 1D),
                        Block.makeCuboidShape(0D, 15D, 0D, 16D, 16D, 1D),
                        Block.makeCuboidShape(15D, 0D, 0D, 16D, 16D, 1D),
                        Block.makeCuboidShape(0D, 0D, 0D, 16D, 1D, 1D)
                ));

        shapes.put(Direction.SOUTH,
                Tools.combine(
                        Block.makeCuboidShape(0D, 0D, 0D, 16D, 16D, 15D),
                        Block.makeCuboidShape(0D, 0D, 15D, 1D, 16D, 16D),
                        Block.makeCuboidShape(0D, 15D, 15D, 16D, 16D, 16D),
                        Block.makeCuboidShape(15D, 0D, 15D, 16D, 16D, 16D),
                        Block.makeCuboidShape(0D, 0D, 15D, 16D, 1D, 16D)
                ));

        shapes.put(Direction.EAST,
                Tools.combine(
                        Block.makeCuboidShape(0D, 0D, 0D, 15D, 16D, 16D),
                        Block.makeCuboidShape(15D, 0D, 0D, 16D, 16D, 1D),
                        Block.makeCuboidShape(15D, 15D, 0D, 16D, 16D, 16D),
                        Block.makeCuboidShape(15D, 16D, 15D, 16D, 0D, 16D),
                        Block.makeCuboidShape(15D, 0D, 0D, 16D, 1D, 16D)
                ));

        shapes.put(Direction.WEST,
                Tools.combine(
                        Block.makeCuboidShape(1D, 0D, 0D, 16D, 16D, 16D),
                        Block.makeCuboidShape(0D, 0D, 0D, 1D, 16D, 1D),
                        Block.makeCuboidShape(0D, 15D, 0D, 1D, 16D, 16D),
                        Block.makeCuboidShape(0D, 16D, 15D, 1D, 0D, 16D),
                        Block.makeCuboidShape(0D, 0D, 0D, 1D, 1D, 16D)
                ));

        shapes.put(Direction.UP,
                Tools.combine(
                        Block.makeCuboidShape(0D, 0D, 0D, 16D, 15D, 16D),
                        Block.makeCuboidShape(0D, 15D, 0D, 1D, 16D, 16D),
                        Block.makeCuboidShape(0D, 15D, 15D, 16D, 16D, 16D),
                        Block.makeCuboidShape(16D, 16D, 16D, 15D, 15D, 0D),
                        Block.makeCuboidShape(0D, 16D, 0D, 16D, 15D, 1D)
                ));

        shapes.put(Direction.DOWN,
                Tools.combine(
                        Block.makeCuboidShape(0D, 1D, 0D, 16D, 16D, 16D),
                        Block.makeCuboidShape(0D, 0D, 0D, 1D, 1D, 16D),
                        Block.makeCuboidShape(0D, 0D, 15D, 16D, 1D, 16D),
                        Block.makeCuboidShape(16D, 1D, 16D, 15D, 0D, 0D),
                        Block.makeCuboidShape(0D, 0D, 0D, 16D, 1D, 1D)
                ));

        SHAPES = Maps.newEnumMap(shapes);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        if (((BlockRayTraceResult) target).getFace().equals(state.get(PROPERTY_FACING))) {
            StorageBarrelTileEntity barrel = (StorageBarrelTileEntity) world.getTileEntity(pos);
            ItemStack stack = barrel.getBarrelContent().copy();
            if (!stack.isEmpty()) {
                stack.setCount(1);
                return stack;
            }
        }
        return super.getPickBlock(state, target, world, pos, player);
    }
}
