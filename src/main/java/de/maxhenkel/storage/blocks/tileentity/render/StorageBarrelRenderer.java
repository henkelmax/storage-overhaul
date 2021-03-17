package de.maxhenkel.storage.blocks.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.storage.blocks.StorageBarrelBlock;
import de.maxhenkel.storage.blocks.tileentity.StorageBarrelTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StorageBarrelRenderer extends TileEntityRenderer<StorageBarrelTileEntity> {

    private final ItemRenderer itemRenderer;
    private Minecraft minecraft;

    public StorageBarrelRenderer(TileEntityRendererDispatcher renderer) {
        super(renderer);
        minecraft = Minecraft.getInstance();
        itemRenderer = minecraft.getItemRenderer();
    }

    @Override
    public void render(StorageBarrelTileEntity barrel, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = Direction.UP;
        if (barrel.hasLevel()) {
            BlockState blockstate = barrel.getLevel().getBlockState(barrel.getBlockPos());
            if (blockstate.getBlock() instanceof StorageBarrelBlock) {
                direction = blockstate.getValue(StorageBarrelBlock.PROPERTY_FACING);
                combinedLightIn = minecraft.levelRenderer.getLightColor(barrel.getLevel(), barrel.getBlockPos().relative(direction));
            }
        }


        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.mulPose(direction.getRotation());
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        matrixStackIn.translate(0D, 0D, -0.5D + 1D / 16D);

        ItemStack itemstack = barrel.getBarrelContent();
        if (!itemstack.isEmpty()) {
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            itemRenderer.renderStatic(itemstack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
        }

        matrixStackIn.popPose();
    }
}