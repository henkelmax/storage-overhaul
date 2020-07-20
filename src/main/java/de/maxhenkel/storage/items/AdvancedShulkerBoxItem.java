package de.maxhenkel.storage.items;

import de.maxhenkel.storage.gui.AdvancedShulkerboxContainer;
import de.maxhenkel.storage.gui.ShulkerBoxItemInventory;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class AdvancedShulkerBoxItem extends BlockItem {

    public AdvancedShulkerBoxItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
        DispenserBlock.registerDispenseBehavior(this, new ShulkerBoxDispenseBehavior());
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getPlayer().isSneaking()) {
            context.getPlayer().openContainer(new ShulkerBoxItemInventory(context.getPlayer(), context.getItem()));
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
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

}
