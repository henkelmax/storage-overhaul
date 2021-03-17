package de.maxhenkel.storage.blocks;

import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.storage.ChestTier;
import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.blocks.tileentity.ModChestTileEntity;
import de.maxhenkel.storage.blocks.tileentity.ModTileEntities;
import de.maxhenkel.storage.items.render.ChestItemRenderer;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.BiPredicate;

public class ModChestBlock extends ContainerBlock implements IWaterLoggable, IItemBlock {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final EnumProperty<ChestType> TYPE = BlockStateProperties.CHEST_TYPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SHAPE_NORTH = Block.box(1D, 0D, 0D, 15D, 14D, 15D);
    protected static final VoxelShape SHAPE_SOUTH = Block.box(1D, 0D, 1D, 15D, 14D, 16D);
    protected static final VoxelShape SHAPE_WEST = Block.box(0D, 0D, 1D, 15D, 14D, 15D);
    protected static final VoxelShape SHAPE_EAST = Block.box(1D, 0D, 1D, 16D, 14D, 15D);
    protected static final VoxelShape SHAPE_SINGLE = Block.box(1D, 0D, 1D, 15D, 14D, 15D);

    private static final TileEntityMerger.ICallback<ModChestTileEntity, Optional<INamedContainerProvider>> CALLBACK = new TileEntityMerger.ICallback<ModChestTileEntity, Optional<INamedContainerProvider>>() {
        @Override
        public Optional<INamedContainerProvider> acceptDouble(final ModChestTileEntity iinventory1, ModChestTileEntity iinventory2) {
            final IInventory iinventory = new DoubleSidedInventory(iinventory1, iinventory2);
            return Optional.of(new INamedContainerProvider() {
                @Nullable
                public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
                    if (iinventory1.canOpen(player) && iinventory2.canOpen(player)) {
                        iinventory1.unpackLootTable(playerInventory.player);
                        iinventory2.unpackLootTable(playerInventory.player);
                        return iinventory1.getTier().getContainer(id, playerInventory, iinventory);
                    } else {
                        return null;
                    }
                }

                public ITextComponent getDisplayName() {
                    if (iinventory1.hasCustomName()) {
                        return iinventory1.getDisplayName();
                    } else {
                        return iinventory2.hasCustomName() ? iinventory2.getDisplayName() : new TranslationTextComponent("container.storage_overhaul.generic_large", iinventory1.getDisplayName());
                    }
                }
            });
        }

        @Override
        public Optional<INamedContainerProvider> acceptSingle(ModChestTileEntity tileEntity) {
            return Optional.of(tileEntity);
        }

        @Override
        public Optional<INamedContainerProvider> acceptNone() {
            return Optional.empty();
        }
    };

    private static final TileEntityMerger.ICallback<ModChestTileEntity, Optional<IInventory>> MERGER = new TileEntityMerger.ICallback<ModChestTileEntity, Optional<IInventory>>() {
        @Override
        public Optional<IInventory> acceptDouble(ModChestTileEntity chest1, ModChestTileEntity chest2) {
            return Optional.of(new DoubleSidedInventory(chest1, chest2));
        }

        @Override
        public Optional<IInventory> acceptSingle(ModChestTileEntity chest) {
            return Optional.of(chest);
        }

        @Override
        public Optional<IInventory> acceptNone() {
            return Optional.empty();
        }
    };

    private WoodType woodType;
    private ChestTier tier;

    protected ModChestBlock(String name, WoodType woodType, ChestTier tier) {
        super(Block.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD));
        this.woodType = woodType;
        this.tier = tier;
        setRegistryName(new ResourceLocation(Main.MODID, name));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, ChestType.SINGLE).setValue(WATERLOGGED, false));
    }

    public WoodType getWoodType() {
        return woodType;
    }

    public ChestTier getTier() {
        return tier;
    }

    private Callable renderer = () -> new ChestItemRenderer(woodType, tier);

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS).setISTER(() -> renderer)).setRegistryName(getRegistryName());
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new ModChestTileEntity(woodType, tier);
    }

    @Override
    public INamedContainerProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
        return getMergerCallback(state, worldIn, pos, false).apply(CALLBACK).orElse(null);
    }

    public static TileEntityMerger.Type getType(BlockState blockState) {
        ChestType chesttype = blockState.getValue(TYPE);
        if (chesttype == ChestType.SINGLE) {
            return TileEntityMerger.Type.SINGLE;
        } else {
            return chesttype == ChestType.RIGHT ? TileEntityMerger.Type.FIRST : TileEntityMerger.Type.SECOND;
        }
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        if (facingState.getBlock() == this && facing.getAxis().isHorizontal()) {
            ChestType chesttype = facingState.getValue(TYPE);
            if (stateIn.getValue(TYPE) == ChestType.SINGLE && chesttype != ChestType.SINGLE && stateIn.getValue(FACING) == facingState.getValue(FACING) && getDirectionToAttached(facingState) == facing.getOpposite()) {
                return stateIn.setValue(TYPE, chesttype.getOpposite());
            }
        } else if (getDirectionToAttached(stateIn) == facing) {
            return stateIn.setValue(TYPE, ChestType.SINGLE);
        }

        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.getValue(TYPE) == ChestType.SINGLE) {
            return SHAPE_SINGLE;
        } else {
            switch (getDirectionToAttached(state)) {
                case NORTH:
                default:
                    return SHAPE_NORTH;
                case SOUTH:
                    return SHAPE_SOUTH;
                case WEST:
                    return SHAPE_WEST;
                case EAST:
                    return SHAPE_EAST;
            }
        }
    }

    public static Direction getDirectionToAttached(BlockState state) {
        Direction direction = state.getValue(FACING);
        return state.getValue(TYPE) == ChestType.LEFT ? direction.getClockWise() : direction.getCounterClockWise();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        ChestType chesttype = ChestType.SINGLE;
        Direction direction = context.getHorizontalDirection().getOpposite();
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = context.isSecondaryUseActive();
        Direction direction1 = context.getClickedFace();
        if (direction1.getAxis().isHorizontal() && flag) {
            Direction direction2 = getDirectionToAttach(context, direction1.getOpposite());
            if (direction2 != null && direction2.getAxis() != direction1.getAxis()) {
                direction = direction2;
                chesttype = direction2.getCounterClockWise() == direction1.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
            }
        }

        if (chesttype == ChestType.SINGLE && !flag) {
            if (direction == getDirectionToAttach(context, direction.getClockWise())) {
                chesttype = ChestType.LEFT;
            } else if (direction == getDirectionToAttach(context, direction.getCounterClockWise())) {
                chesttype = ChestType.RIGHT;
            }
        }

        return defaultBlockState().setValue(FACING, direction).setValue(TYPE, chesttype).setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }


    @Nullable
    private Direction getDirectionToAttach(BlockItemUseContext context, Direction direction) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().relative(direction));
        return blockstate.getBlock() == this && blockstate.getValue(TYPE) == ChestType.SINGLE ? blockstate.getValue(FACING) : null;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof ModChestTileEntity) {
                ((ModChestTileEntity) tileentity).setCustomName(stack.getDisplayName());
            }
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
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        }
        INamedContainerProvider inamedcontainerprovider = getMenuProvider(state, worldIn, pos);
        if (inamedcontainerprovider != null) {
            player.openMenu(inamedcontainerprovider);
            player.awardStat(getOpenStat());
        }

        return ActionResultType.SUCCESS;
    }

    protected Stat<ResourceLocation> getOpenStat() {
        return Stats.CUSTOM.get(Stats.OPEN_CHEST);
    }

    @Nullable
    public static IInventory getInventory(ModChestBlock chestBlock, BlockState blockState, World world, BlockPos blockPos, boolean p_226916_4_) {
        return chestBlock.getMergerCallback(blockState, world, blockPos, p_226916_4_).apply(MERGER).orElse(null);
    }

    public TileEntityMerger.ICallbackWrapper<? extends ModChestTileEntity> getMergerCallback(BlockState blockState, World world, BlockPos blockPos, boolean b) {
        BiPredicate<IWorld, BlockPos> bipredicate;
        if (b) {
            bipredicate = (world1, pos) -> false;
        } else {
            bipredicate = ModChestBlock::isBlocked;
        }

        return TileEntityMerger.combineWithNeigbour(ModTileEntities.CHEST, ModChestBlock::getType, ModChestBlock::getDirectionToAttached, FACING, blockState, world, blockPos, bipredicate);
    }

    @OnlyIn(Dist.CLIENT)
    public static TileEntityMerger.ICallback<ModChestTileEntity, Float2FloatFunction> lidAngleCallback(final IChestLid lid) {
        return new TileEntityMerger.ICallback<ModChestTileEntity, Float2FloatFunction>() {
            @Override
            public Float2FloatFunction acceptDouble(ModChestTileEntity chest1, ModChestTileEntity chest2) {
                return (partialTicks) -> Math.max(chest1.getOpenNess(partialTicks), chest2.getOpenNess(partialTicks));
            }

            @Override
            public Float2FloatFunction acceptSingle(ModChestTileEntity chest) {
                return chest::getOpenNess;
            }

            @Override
            public Float2FloatFunction acceptNone() {
                return lid::getOpenNess;
            }
        };
    }

    public static boolean isBlocked(IWorld world, BlockPos blockPos) {
        return isBelowSolidBlock(world, blockPos) || isCatSittingOn(world, blockPos);
    }

    private static boolean isBelowSolidBlock(IBlockReader reader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        return reader.getBlockState(blockpos).isRedstoneConductor(reader, blockpos);
    }

    private static boolean isCatSittingOn(IWorld world, BlockPos pos) {
        List<CatEntity> list = world.getEntitiesOfClass(CatEntity.class, new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));
        if (!list.isEmpty()) {
            for (CatEntity catentity : list) {
                if (catentity.isOrderedToSit()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.getRedstoneSignalFromContainer(getInventory(this, blockState, worldIn, pos, false));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}
