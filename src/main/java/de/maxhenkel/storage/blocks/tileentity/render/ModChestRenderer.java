package de.maxhenkel.storage.blocks.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.maxhenkel.storage.blocks.ModBlocks;
import de.maxhenkel.storage.blocks.ModChestBlock;
import de.maxhenkel.storage.blocks.tileentity.ModChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModChestRenderer extends ChestTileEntityRenderer<ModChestTileEntity> {

    private final ModelRenderer singleLid;
    private final ModelRenderer singleBottom;
    private final ModelRenderer singleLatch;
    private final ModelRenderer rightLid;
    private final ModelRenderer rightBottom;
    private final ModelRenderer rightLatch;
    private final ModelRenderer leftLid;
    private final ModelRenderer leftBottom;
    private final ModelRenderer leftLatch;

    public ModChestRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
        singleBottom = new ModelRenderer(64, 64, 0, 19);
        singleBottom.addBox(1F, 0F, 1F, 14F, 10F, 14F, 0F);
        singleLid = new ModelRenderer(64, 64, 0, 0);
        singleLid.addBox(1F, 0F, 0F, 14F, 5F, 14F, 0F);
        singleLid.rotationPointY = 9F;
        singleLid.rotationPointZ = 1F;
        singleLatch = new ModelRenderer(64, 64, 0, 0);
        singleLatch.addBox(7F, -1F, 15F, 2F, 4F, 1F, 0F);
        singleLatch.rotationPointY = 8F;
        rightBottom = new ModelRenderer(64, 64, 0, 19);
        rightBottom.addBox(1F, 0F, 1F, 15F, 10F, 14F, 0F);
        rightLid = new ModelRenderer(64, 64, 0, 0);
        rightLid.addBox(1F, 0F, 0F, 15F, 5F, 14F, 0F);
        rightLid.rotationPointY = 9F;
        rightLid.rotationPointZ = 1F;
        rightLatch = new ModelRenderer(64, 64, 0, 0);
        rightLatch.addBox(15F, -1F, 15F, 1F, 4F, 1F, 0F);
        rightLatch.rotationPointY = 8F;
        leftBottom = new ModelRenderer(64, 64, 0, 19);
        leftBottom.addBox(0F, 0F, 1F, 15F, 10F, 14F, 0F);
        leftLid = new ModelRenderer(64, 64, 0, 0);
        leftLid.addBox(0F, 0F, 0F, 15F, 5F, 14F, 0F);
        leftLid.rotationPointY = 9F;
        leftLid.rotationPointZ = 1F;
        leftLatch = new ModelRenderer(64, 64, 0, 0);
        leftLatch.addBox(0F, -1F, 15F, 1F, 4F, 1F, 0F);
        leftLatch.rotationPointY = 8F;
    }

    @Override
    public void render(ModChestTileEntity chest, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockstate = chest.hasWorld() ? chest.getBlockState() : ModBlocks.OAK_CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.has(ChestBlock.TYPE) ? blockstate.get(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockstate.getBlock();
        if (!(block instanceof ModChestBlock)) {
            return;
        }
        ModChestBlock chestBlock = (ModChestBlock) block;
        matrixStackIn.push();
        float rotation = blockstate.get(ChestBlock.FACING).getHorizontalAngle();
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-rotation));
        matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
        TileEntityMerger.ICallbackWrapper<? extends ModChestTileEntity> callback;
        if (chest.hasWorld()) {
            callback = chestBlock.getMergerCallback(blockstate, chest.getWorld(), chest.getPos(), true);
        } else {
            callback = TileEntityMerger.ICallback::func_225537_b_;
        }

        float lidAngle = callback.apply(ModChestBlock.lidAngleCallback(chest)).get(partialTicks);
        lidAngle = 1F - lidAngle;
        lidAngle = 1F - lidAngle * lidAngle * lidAngle;
        int i = callback.apply(new DualBrightnessCallback<>()).applyAsInt(combinedLightIn);
        Material material = this.getMaterial(chest, chesttype);
        IVertexBuilder ivertexbuilder = material.getBuffer(bufferIn, RenderType::getEntityCutout);
        if (chesttype == ChestType.LEFT) {
            renderModels(matrixStackIn, ivertexbuilder, leftLid, leftLatch, leftBottom, lidAngle, i, combinedOverlayIn);
        } else if (chesttype == ChestType.RIGHT) {
            renderModels(matrixStackIn, ivertexbuilder, rightLid, rightLatch, rightBottom, lidAngle, i, combinedOverlayIn);
        } else {
            renderModels(matrixStackIn, ivertexbuilder, singleLid, singleLatch, singleBottom, lidAngle, i, combinedOverlayIn);
        }

        matrixStackIn.pop();
    }

    private void renderModels(MatrixStack matrixStackIn, IVertexBuilder bufferIn, ModelRenderer chestLid, ModelRenderer chestLatch, ModelRenderer chestBottom, float lidAngle, int combinedLightIn, int combinedOverlayIn) {
        chestLid.rotateAngleX = -(lidAngle * ((float) Math.PI / 2F));
        chestLatch.rotateAngleX = chestLid.rotateAngleX;
        chestLid.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        chestLatch.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        chestBottom.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

    @Override
    protected Material getMaterial(ModChestTileEntity tileEntity, ChestType chestType) {
        return ModAtlases.getChestMaterial(tileEntity.getWoodType(), chestType);
    }
}
