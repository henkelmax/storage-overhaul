package de.maxhenkel.storage.blocks.tileentity.render;

import de.maxhenkel.storage.blocks.tileentity.ModChestTileEntity;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;

public class ModChestRenderer extends ChestTileEntityRenderer<ModChestTileEntity> {

    public ModChestRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    protected Material getMaterial(ModChestTileEntity tileEntity, ChestType chestType) {
        return ChestAtlases.getChestMaterial(tileEntity.getMaterial(), chestType);
    }
}
