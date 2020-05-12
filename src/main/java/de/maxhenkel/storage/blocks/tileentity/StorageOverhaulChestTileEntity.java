package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.storage.blocks.tileentity.render.ChestAtlases;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

public class StorageOverhaulChestTileEntity extends ChestTileEntity {

    private ChestAtlases.ChestMaterial material;

    public StorageOverhaulChestTileEntity(TileEntityType type, ChestAtlases.ChestMaterial material) {
        super(type);
        this.material = material;
    }

    public ChestAtlases.ChestMaterial getMaterial() {
        return material;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return getBlockState().getBlock().getNameTextComponent();
    }
}
