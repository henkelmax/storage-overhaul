package de.maxhenkel.storage.items;

import de.maxhenkel.storage.entity.ModChestMinecartEntity;
import de.maxhenkel.storage.entity.ModEntities;
import de.maxhenkel.storage.items.render.ChestMinecartItemRenderer;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ModMinecartItem extends Item {

    private final IDispenseItemBehavior MINECART_DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior behaviourDefaultDispenseItem = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            World world = source.getLevel();
            double x = source.x() + (double) direction.getStepX() * 1.125D;
            double y = Math.floor(source.y()) + (double) direction.getStepY();
            double z = source.z() + (double) direction.getStepZ() * 1.125D;
            BlockPos blockpos = source.getPos().relative(direction);
            BlockState blockstate = world.getBlockState(blockpos);
            RailShape railshape = blockstate.getBlock() instanceof AbstractRailBlock ? ((AbstractRailBlock) blockstate.getBlock()).getRailDirection(blockstate, world, blockpos, null) : RailShape.NORTH_SOUTH;
            double varY;
            if (blockstate.getBlock().is(BlockTags.RAILS)) {
                if (railshape.isAscending()) {
                    varY = 0.6D;
                } else {
                    varY = 0.1D;
                }
            } else {
                if (!blockstate.isAir(world, blockpos) || !world.getBlockState(blockpos.below()).getBlock().is(BlockTags.RAILS)) {
                    return this.behaviourDefaultDispenseItem.dispense(source, stack);
                }

                BlockState blockstate1 = world.getBlockState(blockpos.below());
                RailShape railshape1 = blockstate1.getBlock() instanceof AbstractRailBlock ? ((AbstractRailBlock) blockstate1.getBlock()).getRailDirection(blockstate1, world, blockpos.below(), null) : RailShape.NORTH_SOUTH;
                if (direction != Direction.DOWN && railshape1.isAscending()) {
                    varY = -0.4D;
                } else {
                    varY = -0.9D;
                }
            }
            ModChestMinecartEntity cart = create(world);
            cart.setPos(x, y + varY, z);
            if (stack.hasCustomHoverName()) {
                cart.setCustomName(stack.getDisplayName());
            }

            world.addFreshEntity(cart);
            stack.shrink(1);
            return stack;
        }

        @Override
        protected void playSound(IBlockSource source) {
            source.getLevel().levelEvent(1000, source.getPos(), 0);
        }
    };

    private Supplier<Block> block;

    private static class CallableProvider {
        Supplier<Block> block;

        public CallableProvider(Supplier<Block> block) {
            this.block = block;
        }

        public Callable getCallable() {
            return () -> new ChestMinecartItemRenderer(block);
        }
    }

    public ModMinecartItem(Supplier<Block> block) {
        super(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_TRANSPORTATION).setISTER(() -> new CallableProvider(block).getCallable()));
        this.block = block;
        DispenserBlock.registerBehavior(this, MINECART_DISPENSER_BEHAVIOR);
    }

    public ModChestMinecartEntity create(World world) {
        ModChestMinecartEntity cart = ModEntities.CHEST_MINECART.create(world);
        cart.setBlock(block.get());
        return cart;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = world.getBlockState(blockpos);
        if (!blockstate.getBlock().is(BlockTags.RAILS)) {
            return ActionResultType.FAIL;
        } else {
            ItemStack itemstack = context.getItemInHand();
            if (!world.isClientSide) {
                RailShape railshape = blockstate.getBlock() instanceof AbstractRailBlock ? ((AbstractRailBlock) blockstate.getBlock()).getRailDirection(blockstate, world, blockpos, null) : RailShape.NORTH_SOUTH;
                double height = 0.0D;
                if (railshape.isAscending()) {
                    height = 0.5D;
                }

                ModChestMinecartEntity cart = create(world);
                cart.setPos((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.0625D + height, (double) blockpos.getZ() + 0.5D);
                if (itemstack.hasCustomHoverName()) {
                    cart.setCustomName(itemstack.getDisplayName());
                }

                world.addFreshEntity(cart);
            }

            itemstack.shrink(1);
            return ActionResultType.SUCCESS;
        }
    }

}
