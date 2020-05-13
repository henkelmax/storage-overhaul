package de.maxhenkel.storage.items.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.storage.blocks.tileentity.AdvancedShulkerBoxTileEnitity;
import de.maxhenkel.storage.blocks.tileentity.render.AdvancedShulkerBoxRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;

public class AdvancedShulkerBoxItemRenderer extends ItemStackTileEntityRenderer {

    private AdvancedShulkerBoxRenderer renderer;
    private AdvancedShulkerBoxTileEnitity tileEntity;


    public AdvancedShulkerBoxItemRenderer(DyeColor color) {
        tileEntity = new AdvancedShulkerBoxTileEnitity(color);
        renderer = new AdvancedShulkerBoxRenderer(TileEntityRendererDispatcher.instance);
    }

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        renderer.render(tileEntity, 1F, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
