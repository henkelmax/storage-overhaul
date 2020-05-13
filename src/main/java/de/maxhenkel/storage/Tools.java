package de.maxhenkel.storage;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;

public class Tools {

    public static float getVariatedPitch(World world) {
        return world.rand.nextFloat() * 0.1F + 0.9F;
    }

    public static VoxelShape combine(VoxelShape... shapes) {
        if (shapes.length <= 0) {
            return VoxelShapes.empty();
        }
        VoxelShape combined = shapes[0];

        for (int i = 1; i < shapes.length; i++) {
            combined = VoxelShapes.or(combined, shapes[i]);
        }

        return combined;
    }

    public static boolean isStackable(ItemStack stack1, ItemStack stack2) {
        return stack1.isItemEqual(stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

    public static CompoundNBT writeOverstackedItem(CompoundNBT compound, ItemStack stack) {
        stack.write(compound);
        compound.remove("Count");
        compound.putInt("Count", stack.getCount());
        return compound;
    }

    public static ItemStack readOverstackedItem(CompoundNBT compound) {
        CompoundNBT data= compound.copy();
        int count = data.getInt("Count");
        data.remove("Count");
        data.putByte("Count", (byte) 1);
        ItemStack stack = ItemStack.read(data);
        stack.setCount(count);
        return stack;
    }


}
