package de.maxhenkel.storage.blocks;

import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.blocks.tileentity.AdvancedShulkerBoxTileEnitity;
import de.maxhenkel.storage.items.AdvancedShulkerBoxItem;
import de.maxhenkel.storage.items.render.AdvancedShulkerBoxItemRenderer;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.*;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Callable;

public class AdvancedShulkerBoxBlock extends ContainerBlock implements IItemBlock {

    public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

    private DyeColor color;

    public AdvancedShulkerBoxBlock(String name, DyeColor color) {
        super(Block.Properties.of(Material.SHULKER_SHELL, color.getMaterialColor()).strength(0F, 2F).dynamicShape().noOcclusion());
        this.color = color;
        setRegistryName(Main.MODID, name);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new AdvancedShulkerBoxTileEnitity(color);
    }

    private Callable renderer = () -> new AdvancedShulkerBoxItemRenderer(color);

    @Override
    public Item toItem() {
        return new AdvancedShulkerBoxItem(this, new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_DECORATIONS).setISTER(() -> renderer)).setRegistryName(getRegistryName());
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        } else if (player.isSpectator()) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof AdvancedShulkerBoxTileEnitity) {
                AdvancedShulkerBoxTileEnitity box = (AdvancedShulkerBoxTileEnitity) tileentity;

                if (box.canOpen()) {
                    player.openMenu(box);
                    player.awardStat(Stats.OPEN_SHULKER_BOX);
                }

                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof AdvancedShulkerBoxTileEnitity) {
            AdvancedShulkerBoxTileEnitity box = (AdvancedShulkerBoxTileEnitity) tileentity;
            if (player.isCreative() && !world.isClientSide && !box.isEmpty()) {
                LootContext.Builder builder = new LootContext.Builder((ServerWorld) world)
                        .withRandom(world.random)
                        .withParameter(LootParameters.ORIGIN, new Vector3d(pos.getX(), pos.getY(), pos.getZ()))
                        .withParameter(LootParameters.BLOCK_STATE, state)
                        .withParameter(LootParameters.BLOCK_ENTITY, box)
                        .withParameter(LootParameters.TOOL, ItemStack.EMPTY);
                List<ItemStack> drops = getDrops(state, builder);
                drops.forEach((itemStack) -> {
                    popResource(world, pos, itemStack);
                });
            } else {
                box.unpackLootTable(player);
            }
        }
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        TileEntity tileentity = builder.getParameter(LootParameters.BLOCK_ENTITY);
        if (tileentity instanceof AdvancedShulkerBoxTileEnitity) {
            AdvancedShulkerBoxTileEnitity box = (AdvancedShulkerBoxTileEnitity) tileentity;
            builder = builder.withDynamicDrop(CONTENTS, (lootContext, stackConsumer) -> {
                for (int i = 0; i < box.getContainerSize(); i++) {
                    stackConsumer.accept(box.getItem(i));
                }
            });
        }
        return super.getDrops(state, builder);
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof AdvancedShulkerBoxTileEnitity) {
            if (stack.hasCustomHoverName()) {
                ((AdvancedShulkerBoxTileEnitity) tileentity).setCustomName(stack.getDisplayName());
            }
            CompoundNBT tag = stack.getTag();
            if (tag != null) {
                ((AdvancedShulkerBoxTileEnitity) tileentity).readFromItemStackNbt(tag);
            }
        }
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof AdvancedShulkerBoxTileEnitity) {
                worldIn.updateNeighbourForOutputSignal(pos, state.getBlock());
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        CompoundNBT compoundnbt = stack.getTagElement("BlockEntityTag");
        if (compoundnbt != null) {
            if (compoundnbt.contains("LootTable", 8)) {
                tooltip.add(new StringTextComponent("???????"));
            }

            if (compoundnbt.contains("Items", 9)) {
                NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(compoundnbt, nonnulllist);
                int shownCount = 0;
                int itemCount = 0;

                for (ItemStack itemstack : nonnulllist) {
                    if (!itemstack.isEmpty()) {
                        itemCount++;
                        if (shownCount <= 4) {
                            shownCount++;
                            IFormattableTextComponent itextcomponent = itemstack.getDisplayName().copy();
                            itextcomponent.append(" x").append(String.valueOf(itemstack.getCount()));
                            tooltip.add(itextcomponent);
                        }
                    }
                }

                if (itemCount - shownCount > 0) {
                    tooltip.add((new TranslationTextComponent("container.shulkerBox.more", itemCount - shownCount)).withStyle(TextFormatting.ITALIC));
                }
            }
        }

    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.getRedstoneSignalFromContainer((IInventory) worldIn.getBlockEntity(pos));
    }

    @Override
    public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getCloneItemStack(worldIn, pos, state);
        AdvancedShulkerBoxTileEnitity tileEntity = (AdvancedShulkerBoxTileEnitity) worldIn.getBlockEntity(pos);
        CompoundNBT compoundnbt = tileEntity.saveToNbt(new CompoundNBT());
        if (!compoundnbt.isEmpty()) {
            itemstack.addTagElement("BlockEntityTag", compoundnbt);
        }

        return itemstack;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Nullable
    public DyeColor getColor() {
        return color;
    }
}
