package de.maxhenkel.storage.items.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.storage.entity.ModChestMinecartEntity;
import de.maxhenkel.storage.entity.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class ChestMinecartItemRenderer extends ItemStackTileEntityRenderer {

    private Minecraft minecraft;
    private MinecartRenderer renderer;
    private Supplier<Block> block;
    private ModChestMinecartEntity entity;

    public ChestMinecartItemRenderer(Supplier<Block> block) {
        this.block = block;
        minecraft = Minecraft.getInstance();
    }

    @Override
    public void func_239207_a_(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (renderer == null) {
            renderer = new MinecartRenderer(minecraft.getRenderManager());
        }
        if (entity == null) {
            entity = ModEntities.CHEST_MINECART.create(minecraft.world);
            entity.setBlock(block.get());
        }
        renderer.render(entity, 0F, 1F, matrixStackIn, bufferIn, combinedLightIn);
    }
}
