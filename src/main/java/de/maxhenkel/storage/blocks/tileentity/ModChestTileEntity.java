package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.storage.blocks.ModChestBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.text.ITextComponent;

public class ModChestTileEntity extends ChestTileEntity {

    private WoodType woodType;

    public ModChestTileEntity(WoodType woodType) {
        super(ModTileEntities.CHEST);
        this.woodType = woodType;
    }

    public WoodType getWoodType() {
        if (woodType == null) {
            woodType = ((ModChestBlock) getBlockState().getBlock()).getWoodType();
        }
        return woodType;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return getBlockState().getBlock().getNameTextComponent();
    }
}
