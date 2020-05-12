package de.maxhenkel.storage.items.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.storage.blocks.tileentity.ModChestTileEntity;
import de.maxhenkel.storage.blocks.tileentity.render.ModChestRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class ChestItemRenderer extends ItemStackTileEntityRenderer {

    private ModChestRenderer renderer;
    private ModChestTileEntity tileEntity;

    public ChestItemRenderer(ModChestTileEntity tileEntity) {
        this.tileEntity = tileEntity;
        renderer = new ModChestRenderer(TileEntityRendererDispatcher.instance);
    }

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        renderer.render(tileEntity, 1F, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
