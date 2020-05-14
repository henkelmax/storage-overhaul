package de.maxhenkel.storage.blocks;

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
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.ChestContainer;
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
import net.minecraft.tileentity.*;
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

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final EnumProperty<ChestType> TYPE = BlockStateProperties.CHEST_TYPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(1D, 0D, 0D, 15D, 14D, 15D);
    protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(1D, 0D, 1D, 15D, 14D, 16D);
    protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(0D, 0D, 1D, 15D, 14D, 15D);
    protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(1D, 0D, 1D, 16D, 14D, 15D);
    protected static final VoxelShape SHAPE_SINGLE = Block.makeCuboidShape(1D, 0D, 1D, 15D, 14D, 15D);

    private static final TileEntityMerger.ICallback<ModChestTileEntity, Optional<INamedContainerProvider>> CALLBACK = new TileEntityMerger.ICallback<ModChestTileEntity, Optional<INamedContainerProvider>>() {
        @Override
        public Optional<INamedContainerProvider> func_225539_a_(final ModChestTileEntity iinventory1, ModChestTileEntity iinventory2) {
            final IInventory iinventory = new DoubleSidedInventory(iinventory1, iinventory2);
            return Optional.of(new INamedContainerProvider() {
                @Nullable
                public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
                    if (iinventory1.canOpen(player) && iinventory2.canOpen(player)) {
                        iinventory1.fillWithLoot(playerInventory.player);
                        iinventory2.fillWithLoot(playerInventory.player);
                        return ChestContainer.createGeneric9X6(id, playerInventory, iinventory);
                    } else {
                        return null;
                    }
                }

                public ITextComponent getDisplayName() {
                    if (iinventory1.hasCustomName()) {
                        return iinventory1.getDisplayName();
                    } else {
                        return iinventory2.hasCustomName() ? iinventory2.getDisplayName() : new TranslationTextComponent("container.storage_overhaul.large_" + ((ModChestTileEntity) iinventory1).getWoodType().getName() + "_chest");
                    }
                }
            });
        }

        public Optional<INamedContainerProvider> func_225538_a_(ModChestTileEntity tileEntity) {
            return Optional.of(tileEntity);
        }

        public Optional<INamedContainerProvider> func_225537_b_() {
            return Optional.empty();
        }
    };

    private WoodType woodType;

    protected ModChestBlock(String name, WoodType woodType) {
        super(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD));
        this.woodType = woodType;
        setRegistryName(new ResourceLocation(Main.MODID, name));
        setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH).with(TYPE, ChestType.SINGLE).with(WATERLOGGED, false));
    }

    public WoodType getWoodType() {
        return woodType;
    }

    private Callable renderer = () -> new ChestItemRenderer(woodType);

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ItemGroup.DECORATIONS).setISTER(() -> renderer)).setRegistryName(getRegistryName());
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new ModChestTileEntity(woodType);
    }

    @Override
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        return getMergerCallback(state, worldIn, pos, false).apply(CALLBACK).orElse(null);
    }

    private static final TileEntityMerger.ICallback<ModChestTileEntity, Optional<IInventory>> MERGER = new TileEntityMerger.ICallback<ModChestTileEntity, Optional<IInventory>>() {
        public Optional<IInventory> func_225539_a_(ModChestTileEntity chest1, ModChestTileEntity chest2) {
            return Optional.of(new DoubleSidedInventory(chest1, chest2));
        }

        public Optional<IInventory> func_225538_a_(ModChestTileEntity chest) {
            return Optional.of(chest);
        }

        public Optional<IInventory> func_225537_b_() {
            return Optional.empty();
        }
    };

    public static TileEntityMerger.Type getType(BlockState blockState) {
        ChestType chesttype = blockState.get(TYPE);
        if (chesttype == ChestType.SINGLE) {
            return TileEntityMerger.Type.SINGLE;
        } else {
            return chesttype == ChestType.RIGHT ? TileEntityMerger.Type.FIRST : TileEntityMerger.Type.SECOND;
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        if (facingState.getBlock() == this && facing.getAxis().isHorizontal()) {
            ChestType chesttype = facingState.get(TYPE);
            if (stateIn.get(TYPE) == ChestType.SINGLE && chesttype != ChestType.SINGLE && stateIn.get(FACING) == facingState.get(FACING) && getDirectionToAttached(facingState) == facing.getOpposite()) {
                return stateIn.with(TYPE, chesttype.opposite());
            }
        } else if (getDirectionToAttached(stateIn) == facing) {
            return stateIn.with(TYPE, ChestType.SINGLE);
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(TYPE) == ChestType.SINGLE) {
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
        Direction direction = state.get(FACING);
        return state.get(TYPE) == ChestType.LEFT ? direction.rotateY() : direction.rotateYCCW();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        ChestType chesttype = ChestType.SINGLE;
        Direction direction = context.getPlacementHorizontalFacing().getOpposite();
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = context.func_225518_g_();
        Direction direction1 = context.getFace();
        if (direction1.getAxis().isHorizontal() && flag) {
            Direction direction2 = this.getDirectionToAttach(context, direction1.getOpposite());
            if (direction2 != null && direction2.getAxis() != direction1.getAxis()) {
                direction = direction2;
                chesttype = direction2.rotateYCCW() == direction1.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
            }
        }

        if (chesttype == ChestType.SINGLE && !flag) {
            if (direction == this.getDirectionToAttach(context, direction.rotateY())) {
                chesttype = ChestType.LEFT;
            } else if (direction == this.getDirectionToAttach(context, direction.rotateYCCW())) {
                chesttype = ChestType.RIGHT;
            }
        }

        return this.getDefaultState().with(FACING, direction).with(TYPE, chesttype).with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }


    @Nullable
    private Direction getDirectionToAttach(BlockItemUseContext p_196312_1_, Direction p_196312_2_) {
        BlockState blockstate = p_196312_1_.getWorld().getBlockState(p_196312_1_.getPos().offset(p_196312_2_));
        return blockstate.getBlock() == this && blockstate.get(TYPE) == ChestType.SINGLE ? blockstate.get(FACING) : null;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof ChestTileEntity) {
                ((ChestTileEntity) tileentity).setCustomName(stack.getDisplayName());
            }
        }

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
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }
        INamedContainerProvider inamedcontainerprovider = this.getContainer(state, worldIn, pos);
        if (inamedcontainerprovider != null) {
            player.openContainer(inamedcontainerprovider);
            player.addStat(this.getOpenStat());
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

        return TileEntityMerger.func_226924_a_(ModTileEntities.CHEST, ModChestBlock::getType, ModChestBlock::getDirectionToAttached, FACING, blockState, world, blockPos, bipredicate);
    }

    @OnlyIn(Dist.CLIENT)
    public static TileEntityMerger.ICallback<ModChestTileEntity, Float2FloatFunction> lidAngleCallback(final IChestLid lid) {
        return new TileEntityMerger.ICallback<ModChestTileEntity, Float2FloatFunction>() {
            public Float2FloatFunction func_225539_a_(ModChestTileEntity chest1, ModChestTileEntity chest2) {
                return (partialTicks) -> Math.max(chest1.getLidAngle(partialTicks), chest2.getLidAngle(partialTicks));
            }

            public Float2FloatFunction func_225538_a_(ModChestTileEntity chest) {
                return chest::getLidAngle;
            }

            public Float2FloatFunction func_225537_b_() {
                return lid::getLidAngle;
            }
        };
    }

    public static boolean isBlocked(IWorld world, BlockPos blockPos) {
        return isBelowSolidBlock(world, blockPos) || isCatSittingOn(world, blockPos);
    }

    private static boolean isBelowSolidBlock(IBlockReader reader, BlockPos pos) {
        BlockPos blockpos = pos.up();
        return reader.getBlockState(blockpos).isNormalCube(reader, blockpos);
    }

    private static boolean isCatSittingOn(IWorld world, BlockPos pos) {
        List<CatEntity> list = world.getEntitiesWithinAABB(CatEntity.class, new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));
        if (!list.isEmpty()) {
            for (CatEntity catentity : list) {
                if (catentity.isSitting()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstoneFromInventory(getInventory(this, blockState, worldIn, pos, false));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, WATERLOGGED);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

}
