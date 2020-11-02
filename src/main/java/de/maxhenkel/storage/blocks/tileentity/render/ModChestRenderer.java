package de.maxhenkel.storage.blocks.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.maxhenkel.storage.ChestTier;
import de.maxhenkel.storage.blocks.ModBlocks;
import de.maxhenkel.storage.blocks.ModChestBlock;
import de.maxhenkel.storage.blocks.tileentity.ModChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModChestRenderer extends ChestTileEntityRenderer<ModChestTileEntity> {

    private final ModelRenderer singleLid;
    private final ModelRenderer singleBottom;
    private final ModelRenderer singleLatchBase;
    private final ModelRenderer singleLatchTier1;
    private final ModelRenderer singleLatchTier2;
    private final ModelRenderer singleLatchTier3;
    private final ModelRenderer rightLid;
    private final ModelRenderer rightBottom;
    private final ModelRenderer rightLatchBase;
    private final ModelRenderer rightLatchTier1;
    private final ModelRenderer rightLatchTier2;
    private final ModelRenderer rightLatchTier3;
    private final ModelRenderer leftLid;
    private final ModelRenderer leftBottom;
    private final ModelRenderer leftLatchBase;
    private final ModelRenderer leftLatchTier1;
    private final ModelRenderer leftLatchTier2;
    private final ModelRenderer leftLatchTier3;

    public ModChestRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
        singleBottom = new ModelRenderer(64, 64, 0, 19);
        singleBottom.addBox(1F, 0F, 1F, 14F, 10F, 14F, 0F);
        singleLid = new ModelRenderer(64, 64, 0, 0);
        singleLid.addBox(1F, 0F, 0F, 14F, 5F, 14F, 0F);
        singleLid.rotationPointY = 9F;
        singleLid.rotationPointZ = 1F;
        singleLatchBase = new ModelRenderer(64, 64, 0, 0);
        singleLatchBase.addBox(7F, -1F, 15F, 2F, 4F, 1F, 0F);
        singleLatchBase.rotationPointY = 8F;
        singleLatchTier1 = new ModelRenderer(64, 64, 8, 0);
        singleLatchTier1.addBox(7F, -1F, 15F, 2F, 4F, 1F, 0F);
        singleLatchTier1.rotationPointY = 8F;
        singleLatchTier2 = new ModelRenderer(64, 64, 0, 8);
        singleLatchTier2.addBox(7F, -1F, 15F, 2F, 4F, 1F, 0F);
        singleLatchTier2.rotationPointY = 8F;
        singleLatchTier3 = new ModelRenderer(64, 64, 8, 8);
        singleLatchTier3.addBox(7F, -1F, 15F, 2F, 4F, 1F, 0F);
        singleLatchTier3.rotationPointY = 8F;
        rightBottom = new ModelRenderer(64, 64, 0, 19);
        rightBottom.addBox(1F, 0F, 1F, 15F, 10F, 14F, 0F);
        rightLid = new ModelRenderer(64, 64, 0, 0);
        rightLid.addBox(1F, 0F, 0F, 15F, 5F, 14F, 0F);
        rightLid.rotationPointY = 9F;
        rightLid.rotationPointZ = 1F;
        rightLatchBase = new ModelRenderer(64, 64, 0, 0);
        rightLatchBase.addBox(15F, -1F, 15F, 1F, 4F, 1F, 0F);
        rightLatchBase.rotationPointY = 8F;
        rightLatchTier1 = new ModelRenderer(64, 64, 8, 0);
        rightLatchTier1.addBox(15F, -1F, 15F, 1F, 4F, 1F, 0F);
        rightLatchTier1.rotationPointY = 8F;
        rightLatchTier2 = new ModelRenderer(64, 64, 0, 8);
        rightLatchTier2.addBox(15F, -1F, 15F, 1F, 4F, 1F, 0F);
        rightLatchTier2.rotationPointY = 8F;
        rightLatchTier3 = new ModelRenderer(64, 64, 8, 8);
        rightLatchTier3.addBox(15F, -1F, 15F, 1F, 4F, 1F, 0F);
        rightLatchTier3.rotationPointY = 8F;
        leftBottom = new ModelRenderer(64, 64, 0, 19);
        leftBottom.addBox(0F, 0F, 1F, 15F, 10F, 14F, 0F);
        leftLid = new ModelRenderer(64, 64, 0, 0);
        leftLid.addBox(0F, 0F, 0F, 15F, 5F, 14F, 0F);
        leftLid.rotationPointY = 9F;
        leftLid.rotationPointZ = 1F;
        leftLatchBase = new ModelRenderer(64, 64, 0, 0);
        leftLatchBase.addBox(0F, -1F, 15F, 1F, 4F, 1F, 0F);
        leftLatchBase.rotationPointY = 8F;
        leftLatchTier1 = new ModelRenderer(64, 64, 8, 0);
        leftLatchTier1.addBox(0F, -1F, 15F, 1F, 4F, 1F, 0F);
        leftLatchTier1.rotationPointY = 8F;
        leftLatchTier2 = new ModelRenderer(64, 64, 0, 8);
        leftLatchTier2.addBox(0F, -1F, 15F, 1F, 4F, 1F, 0F);
        leftLatchTier2.rotationPointY = 8F;
        leftLatchTier3 = new ModelRenderer(64, 64, 8, 8);
        leftLatchTier3.addBox(0F, -1F, 15F, 1F, 4F, 1F, 0F);
        leftLatchTier3.rotationPointY = 8F;
    }

    @Override
    public void render(ModChestTileEntity chest, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockstate = chest.hasWorld() ? chest.getBlockState() : ModBlocks.OAK_CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.hasProperty(ChestBlock.TYPE) ? blockstate.get(ChestBlock.TYPE) : ChestType.SINGLE;
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
        RenderMaterial material = getMaterial(chest, chesttype);
        IVertexBuilder ivertexbuilder = material.getBuffer(bufferIn, RenderType::getEntityCutout);
        if (chesttype == ChestType.LEFT) {
            renderModels(matrixStackIn, ivertexbuilder, leftLid, getDoubleLatchLeft(chest.getTier()), leftBottom, lidAngle, i, combinedOverlayIn);
        } else if (chesttype == ChestType.RIGHT) {
            renderModels(matrixStackIn, ivertexbuilder, rightLid, getDoubleLatchRight(chest.getTier()), rightBottom, lidAngle, i, combinedOverlayIn);
        } else {
            renderModels(matrixStackIn, ivertexbuilder, singleLid, getSingleLatch(chest.getTier()), singleBottom, lidAngle, i, combinedOverlayIn);
        }

        matrixStackIn.pop();
    }

    private ModelRenderer getSingleLatch(ChestTier tier) {
        switch (tier) {
            case BASE_TIER:
                return singleLatchBase;
            case TIER_1:
                return singleLatchTier1;
            case TIER_2:
                return singleLatchTier2;
            case TIER_3:
                return singleLatchTier3;
        }
        return singleLatchBase;
    }

    private ModelRenderer getDoubleLatchRight(ChestTier tier) {
        switch (tier) {
            case BASE_TIER:
                return rightLatchBase;
            case TIER_1:
                return rightLatchTier1;
            case TIER_2:
                return rightLatchTier2;
            case TIER_3:
                return rightLatchTier3;
        }
        return rightLatchBase;
    }

    private ModelRenderer getDoubleLatchLeft(ChestTier tier) {
        switch (tier) {
            case BASE_TIER:
                return leftLatchBase;
            case TIER_1:
                return leftLatchTier1;
            case TIER_2:
                return leftLatchTier2;
            case TIER_3:
                return leftLatchTier3;
        }
        return leftLatchBase;
    }

    private void renderModels(MatrixStack matrixStackIn, IVertexBuilder bufferIn, ModelRenderer chestLid, ModelRenderer chestLatch, ModelRenderer chestBottom, float lidAngle, int combinedLightIn, int combinedOverlayIn) {
        chestLid.rotateAngleX = -(lidAngle * ((float) Math.PI / 2F));
        chestLatch.rotateAngleX = chestLid.rotateAngleX;
        chestLid.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        chestLatch.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        chestBottom.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

    @Override
    protected RenderMaterial getMaterial(ModChestTileEntity tileEntity, ChestType chestType) {
        return ModAtlases.getChestMaterial(tileEntity.getWoodType(), chestType);
    }
}
