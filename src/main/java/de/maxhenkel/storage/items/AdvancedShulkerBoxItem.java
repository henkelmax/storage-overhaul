package de.maxhenkel.storage.items;

import de.maxhenkel.storage.gui.AdvancedShulkerboxContainer;
import de.maxhenkel.storage.gui.ShulkerBoxItemInventory;
import de.maxhenkel.storage.util.ShulkerBoxInventoryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AdvancedShulkerBoxItem extends BlockItem {

    public AdvancedShulkerBoxItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
        DispenserBlock.registerDispenseBehavior(this, new ShulkerBoxDispenseBehavior());
    }

    @Override
    public ActionResultType tryPlace(BlockItemUseContext context) {
        if (!context.getPlayer().isSneaking()) {
            return ActionResultType.PASS;
        }
        return super.tryPlace(context);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack stack = player.getHeldItem(handIn);
        if (player instanceof ServerPlayerEntity) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                @Override
                public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    return new AdvancedShulkerboxContainer(id, playerInventory, new ShulkerBoxItemInventory(player, stack));
                }

                @Override
                public ITextComponent getDisplayName() {
                    return stack.getDisplayName();
                }
            });
        }
        return ActionResult.resultSuccess(stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                    return LazyOptional.of(() -> new ShulkerBoxInventoryHandler(stack)).cast();
                }
                return LazyOptional.empty();
            }
        };
    }
}
