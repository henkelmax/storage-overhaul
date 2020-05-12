package de.maxhenkel.storage.blocks.tileentity.render;

import de.maxhenkel.storage.blocks.tileentity.ModChestTileEntity;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModChestRenderer extends ChestTileEntityRenderer<ModChestTileEntity> {

    public ModChestRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    protected Material getMaterial(ModChestTileEntity tileEntity, ChestType chestType) {
        return ModAtlases.getChestMaterial(tileEntity.getWoodType(), chestType);
    }
}
