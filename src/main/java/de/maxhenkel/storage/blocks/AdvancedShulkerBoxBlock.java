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
        super(Block.Properties.create(Material.SHULKER, color.getMapColor()).hardnessAndResistance(0F, 2F).variableOpacity().notSolid());
        this.color = color;
        setRegistryName(Main.MODID, name);
        setDefaultState(stateContainer.getBaseState().with(FACING, Direction.UP));
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new AdvancedShulkerBoxTileEnitity(color);
    }

    private Callable renderer = () -> new AdvancedShulkerBoxItemRenderer(color);

    @Override
    public Item toItem() {
        return new AdvancedShulkerBoxItem(this, new Item.Properties().maxStackSize(1).group(ItemGroup.DECORATIONS).setISTER(() -> renderer)).setRegistryName(getRegistryName());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else if (player.isSpectator()) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof AdvancedShulkerBoxTileEnitity) {
                AdvancedShulkerBoxTileEnitity box = (AdvancedShulkerBoxTileEnitity) tileentity;

                if (box.canOpen()) {
                    player.openContainer(box);
                    player.addStat(Stats.OPEN_SHULKER_BOX);
                }

                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(FACING, context.getFace());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof AdvancedShulkerBoxTileEnitity) {
            AdvancedShulkerBoxTileEnitity box = (AdvancedShulkerBoxTileEnitity) tileentity;
            if (player.isCreative() && !world.isRemote && !box.isEmpty()) {
                LootContext.Builder builder = new LootContext.Builder((ServerWorld) world)
                        .withRandom(world.rand)
                        .withParameter(LootParameters.field_237457_g_, new Vector3d(pos.getX(), pos.getY(), pos.getZ()))
                        .withParameter(LootParameters.BLOCK_STATE, state)
                        .withParameter(LootParameters.BLOCK_ENTITY, box)
                        .withParameter(LootParameters.TOOL, ItemStack.EMPTY);
                List<ItemStack> drops = getDrops(state, builder);
                drops.forEach((itemStack) -> {
                    spawnAsEntity(world, pos, itemStack);
                });
            } else {
                box.fillWithLoot(player);
            }
        }
        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        TileEntity tileentity = builder.get(LootParameters.BLOCK_ENTITY);
        if (tileentity instanceof AdvancedShulkerBoxTileEnitity) {
            AdvancedShulkerBoxTileEnitity box = (AdvancedShulkerBoxTileEnitity) tileentity;
            builder = builder.withDynamicDrop(CONTENTS, (lootContext, stackConsumer) -> {
                for (int i = 0; i < box.getSizeInventory(); i++) {
                    stackConsumer.accept(box.getStackInSlot(i));
                }
            });
        }
        return super.getDrops(state, builder);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof AdvancedShulkerBoxTileEnitity) {
                ((AdvancedShulkerBoxTileEnitity) tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof AdvancedShulkerBoxTileEnitity) {
                worldIn.updateComparatorOutputLevel(pos, state.getBlock());
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        CompoundNBT compoundnbt = stack.getChildTag("BlockEntityTag");
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
                            IFormattableTextComponent itextcomponent = itemstack.getDisplayName().deepCopy();
                            itextcomponent.appendString(" x").appendString(String.valueOf(itemstack.getCount()));
                            tooltip.add(itextcomponent);
                        }
                    }
                }

                if (itemCount - shownCount > 0) {
                    tooltip.add((new TranslationTextComponent("container.shulkerBox.more", itemCount - shownCount)).mergeStyle(TextFormatting.ITALIC));
                }
            }
        }

    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstoneFromInventory((IInventory) worldIn.getTileEntity(pos));
    }

    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getItem(worldIn, pos, state);
        AdvancedShulkerBoxTileEnitity tileEntity = (AdvancedShulkerBoxTileEnitity) worldIn.getTileEntity(pos);
        CompoundNBT compoundnbt = tileEntity.saveToNbt(new CompoundNBT());
        if (!compoundnbt.isEmpty()) {
            itemstack.setTagInfo("BlockEntityTag", compoundnbt);
        }

        return itemstack;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Nullable
    public DyeColor getColor() {
        return color;
    }
}
