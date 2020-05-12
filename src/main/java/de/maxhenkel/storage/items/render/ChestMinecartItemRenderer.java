package de.maxhenkel.storage.items.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.storage.entity.StorageOverhaulChestMinecartEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class ChestMinecartItemRenderer extends ItemStackTileEntityRenderer {

    private Minecraft minecraft;
    private MinecartRenderer renderer;
    private Supplier<EntityType<StorageOverhaulChestMinecartEntity>> entitySupplier;
    private StorageOverhaulChestMinecartEntity entity;

    public ChestMinecartItemRenderer(Supplier<EntityType<StorageOverhaulChestMinecartEntity>> entity) {
        this.entitySupplier = entity;
        minecraft = Minecraft.getInstance();
        renderer = new MinecartRenderer(minecraft.getRenderManager());
    }

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (entity == null) {
            entity = entitySupplier.get().create(minecraft.world);
        }
        renderer.render(entity, 0F, 1F, matrixStackIn, bufferIn, combinedLightIn);
    }
}
