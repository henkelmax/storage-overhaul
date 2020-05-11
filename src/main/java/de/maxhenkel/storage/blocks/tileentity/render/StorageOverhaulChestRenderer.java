package de.maxhenkel.storage.blocks.tileentity.render;

import de.maxhenkel.storage.blocks.tileentity.StorageOverhaulChestTileEntity;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;

public class StorageOverhaulChestRenderer extends ChestTileEntityRenderer<StorageOverhaulChestTileEntity> {

    public StorageOverhaulChestRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    protected Material getMaterial(StorageOverhaulChestTileEntity tileEntity, ChestType chestType) {
        return ChestAtlases.getChestMaterial(tileEntity.getMaterial(), chestType);
    }
}
