package de.maxhenkel.storage.blocks;

import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.storage.ChestTier;
import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.blocks.tileentity.ModBarrelTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class ModBarrelBlock extends ContainerBlock implements IItemBlock {

    public static final DirectionProperty PROPERTY_FACING = BlockStateProperties.FACING;
    public static final BooleanProperty PROPERTY_OPEN = BlockStateProperties.OPEN;

    private ChestTier tier;

    protected ModBarrelBlock(String name, ChestTier tier) {
        super(Block.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD));
        this.tier = tier;

        setRegistryName(new ResourceLocation(Main.MODID, name));
        registerDefaultState(stateDefinition.any().setValue(PROPERTY_FACING, Direction.NORTH).setValue(PROPERTY_OPEN, false));
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof ModBarrelTileEntity) {
            ((ModBarrelTileEntity) tileentity).barrelTick();
        }
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName(getRegistryName());
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof ModBarrelTileEntity) {
                player.openMenu((ModBarrelTileEntity) tileentity);
                player.awardStat(Stats.OPEN_BARREL);
            }

            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof IInventory) {
                InventoryHelper.dropContents(worldIn, pos, (IInventory) tileentity);
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
            if (tileentity instanceof ModBarrelTileEntity) {
                ((ModBarrelTileEntity) tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.getRedstoneSignalFromBlockEntity(worldIn.getBlockEntity(pos));
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
        builder.add(PROPERTY_FACING, PROPERTY_OPEN);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(PROPERTY_FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new ModBarrelTileEntity(tier);
    }
}
