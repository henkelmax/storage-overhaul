package de.maxhenkel.storage.blocks;

import de.maxhenkel.corelib.block.DirectionalVoxelShape;
import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.corelib.item.ItemUtils;
import de.maxhenkel.corelib.sound.SoundUtils;
import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.blocks.tileentity.StorageBarrelTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

public class StorageBarrelBlock extends ContainerBlock implements IItemBlock {

    private static final DirectionalVoxelShape SHAPES = new DirectionalVoxelShape.Builder()
            .direction(Direction.NORTH,
                    Block.box(0D, 0D, 1D, 16D, 16D, 16D),
                    Block.box(0D, 0D, 0D, 1D, 16D, 1D),
                    Block.box(0D, 15D, 0D, 16D, 16D, 1D),
                    Block.box(15D, 0D, 0D, 16D, 16D, 1D),
                    Block.box(0D, 0D, 0D, 16D, 1D, 1D)
            )
            .direction(Direction.SOUTH,
                    Block.box(0D, 0D, 0D, 16D, 16D, 15D),
                    Block.box(0D, 0D, 15D, 1D, 16D, 16D),
                    Block.box(0D, 15D, 15D, 16D, 16D, 16D),
                    Block.box(15D, 0D, 15D, 16D, 16D, 16D),
                    Block.box(0D, 0D, 15D, 16D, 1D, 16D)
            )
            .direction(Direction.EAST,
                    Block.box(0D, 0D, 0D, 15D, 16D, 16D),
                    Block.box(15D, 0D, 0D, 16D, 16D, 1D),
                    Block.box(15D, 15D, 0D, 16D, 16D, 16D),
                    Block.box(15D, 16D, 15D, 16D, 0D, 16D),
                    Block.box(15D, 0D, 0D, 16D, 1D, 16D)
            )
            .direction(Direction.WEST,
                    Block.box(1D, 0D, 0D, 16D, 16D, 16D),
                    Block.box(0D, 0D, 0D, 1D, 16D, 1D),
                    Block.box(0D, 15D, 0D, 1D, 16D, 16D),
                    Block.box(0D, 16D, 15D, 1D, 0D, 16D),
                    Block.box(0D, 0D, 0D, 1D, 1D, 16D)
            )
            .direction(Direction.UP,
                    Block.box(0D, 0D, 0D, 16D, 15D, 16D),
                    Block.box(0D, 15D, 0D, 1D, 16D, 16D),
                    Block.box(0D, 15D, 15D, 16D, 16D, 16D),
                    Block.box(16D, 16D, 16D, 15D, 15D, 0D),
                    Block.box(0D, 16D, 0D, 16D, 15D, 1D)
            )
            .direction(Direction.DOWN,
                    Block.box(0D, 1D, 0D, 16D, 16D, 16D),
                    Block.box(0D, 0D, 0D, 1D, 1D, 16D),
                    Block.box(0D, 0D, 15D, 16D, 1D, 16D),
                    Block.box(16D, 1D, 16D, 15D, 0D, 0D),
                    Block.box(0D, 0D, 0D, 16D, 1D, 1D)
            ).build();

    public static final DirectionProperty PROPERTY_FACING = BlockStateProperties.FACING;

    protected StorageBarrelBlock(String name) {
        super(Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation(Main.MODID, name));
        registerDefaultState(stateDefinition.any().setValue(PROPERTY_FACING, Direction.NORTH));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName(getRegistryName());
    }

    @SubscribeEvent
    public void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        BlockState state = event.getWorld().getBlockState(event.getPos());
        if (state.getBlock() != this) {
            return;
        }

        if (event.getFace().equals(state.getValue(PROPERTY_FACING))) {
            if (event.getPlayer().isCreative()) {
                event.setCanceled(true);
                onFrontClicked(state, event.getWorld(), event.getPos(), event.getPlayer());
            }
        }
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!hit.getDirection().equals(state.getValue(PROPERTY_FACING))) {
            return ActionResultType.PASS;
        }

        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        }

        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if (!(tileentity instanceof StorageBarrelTileEntity)) {
            return ActionResultType.SUCCESS;
        }

        StorageBarrelTileEntity barrel = (StorageBarrelTileEntity) tileentity;

        if (barrel.onInsert(player)) {
            boolean inserted = false;
            for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                ItemStack stack = player.inventory.getItem(i);
                ItemStack rest = barrel.insertItem(0, stack, false);
                if (!ItemStack.matches(rest, stack)) {
                    player.inventory.setItem(i, rest);
                    inserted = true;
                }
            }

            if (inserted) {
                playInsertSound(worldIn, player);
            }
            return ActionResultType.SUCCESS;
        }

        ItemStack held = player.getItemInHand(handIn);

        ItemStack remaining = barrel.insertItem(0, held, false);

        if (ItemStack.matches(remaining, held)) {
            return ActionResultType.SUCCESS;
        }

        held.setCount(remaining.getCount());
        playInsertSound(worldIn, player);

        return ActionResultType.SUCCESS;
    }

    public void playInsertSound(World world, PlayerEntity player) {
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, SoundUtils.getVariatedPitch(world));
    }

    public void playExtractSound(World world, PlayerEntity player) {
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.random.nextFloat() * 0.1F + 1.3F);
    }

    @Override
    public void attack(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        super.attack(state, worldIn, pos, player);
        if (!player.isCreative()) {
            onFrontClicked(state, worldIn, pos, player);
        }
    }

    public void onFrontClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if (worldIn.isClientSide) {
            return;
        }
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if (!(tileentity instanceof StorageBarrelTileEntity)) {
            return;
        }

        StorageBarrelTileEntity barrel = (StorageBarrelTileEntity) tileentity;

        if (barrel.isEmpty()) {
            return;
        }

        ItemStack barrelContent = barrel.getBarrelContent();

        int amount = Math.min(player.isShiftKeyDown() ? barrelContent.getMaxStackSize() : 1, barrelContent.getCount());

        ItemStack heldItem = player.getMainHandItem();

        if (heldItem.isEmpty()) {
            ItemStack newItem = barrelContent.copy();
            newItem.setCount(amount);
            barrel.removeCount(amount);
            player.inventory.add(player.inventory.selected, newItem);
            playExtractSound(worldIn, player);
            return;
        } else if (ItemUtils.isStackable(heldItem, barrelContent)) {
            int space = Math.max(heldItem.getMaxStackSize() - heldItem.getCount(), 0);

            if (space > 0) {
                amount = Math.min(space, amount);
                ItemStack newItem = heldItem.copy();
                newItem.setCount(amount);
                barrel.removeCount(amount);
                player.inventory.add(player.inventory.selected, newItem);
                playExtractSound(worldIn, player);
                return;
            }
        }

        ItemStack newItem = barrelContent.copy();
        newItem.setCount(amount);
        barrel.removeCount(amount);

        player.inventory.add(newItem);

        if (!newItem.isEmpty()) {
            popResource(worldIn, pos.relative(state.getValue(PROPERTY_FACING)), newItem);
        }

        playExtractSound(worldIn, player);
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof StorageBarrelTileEntity) {
                ItemStack content = ((StorageBarrelTileEntity) tileentity).getBarrelContent();
                while (!content.isEmpty()) {
                    ItemStack split = content.split(content.getMaxStackSize());
                    InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), split);
                }
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof StorageBarrelTileEntity) {
                ((StorageBarrelTileEntity) tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
        return calcRedstone((StorageBarrelTileEntity) worldIn.getBlockEntity(pos));
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
        return state.setValue(PROPERTY_FACING, rot.rotate(state.getValue(PROPERTY_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(PROPERTY_FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PROPERTY_FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(PROPERTY_FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new StorageBarrelTileEntity();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.getValue(PROPERTY_FACING));
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        if (((BlockRayTraceResult) target).getDirection().equals(state.getValue(PROPERTY_FACING))) {
            StorageBarrelTileEntity barrel = (StorageBarrelTileEntity) world.getBlockEntity(pos);
            ItemStack stack = barrel.getBarrelContent().copy();
            if (!stack.isEmpty()) {
                stack.setCount(1);
                return stack;
            }
        }
        return super.getPickBlock(state, target, world, pos, player);
    }

}
