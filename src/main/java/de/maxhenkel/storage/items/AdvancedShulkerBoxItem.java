package de.maxhenkel.storage.items;

import de.maxhenkel.storage.gui.ShulkerBoxItemInventory;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        playerIn.openContainer(new ShulkerBoxItemInventory(playerIn, stack));
        return ActionResult.resultSuccess(stack);
    }

}
