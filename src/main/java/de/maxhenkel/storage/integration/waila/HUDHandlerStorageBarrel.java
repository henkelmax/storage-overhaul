package de.maxhenkel.storage.integration.waila;

import de.maxhenkel.corelib.item.ItemUtils;
import de.maxhenkel.storage.blocks.tileentity.StorageBarrelTileEntity;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.*;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class HUDHandlerStorageBarrel implements IComponentProvider, IServerDataProvider<TileEntity> {

    static final HUDHandlerStorageBarrel INSTANCE = new HUDHandlerStorageBarrel();

    @Override
    public void appendHead(List<ITextComponent> t, IDataAccessor accessor, IPluginConfig config) {
        ITaggableList<ResourceLocation, ITextComponent> tooltip = (ITaggableList<ResourceLocation, ITextComponent>) t;
        tooltip.setTag(PluginStorageOverhaul.OBJECT_NAME_TAG, new StringTextComponent(String.format(Waila.CONFIG.get().getFormatting().getBlockName(), ((StorageBarrelTileEntity) accessor.getTileEntity()).getDisplayName().getString())));
        if (config.get(PluginStorageOverhaul.CONFIG_SHOW_REGISTRY)) {
            tooltip.setTag(PluginStorageOverhaul.REGISTRY_NAME_TAG, new StringTextComponent(accessor.getBlock().getRegistryName().toString()).func_240699_a_(TextFormatting.GRAY));
        }
    }

    @Override
    public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("Content")) {
            ItemStack stack = ItemUtils.readOverstackedItem(accessor.getServerData().getCompound("Content"));

            tooltip.add(new StringTextComponent(stack.getCount() + "x ").func_230529_a_((stack.getDisplayName())));
        }
    }

    @Override
    public void appendTail(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
        tooltip.clear();
        tooltip.add(new StringTextComponent(String.format(Waila.CONFIG.get().getFormatting().getModName(), ModIdentification.getModInfo(accessor.getBlock()).getName())));
    }

    @Override
    public void appendServerData(CompoundNBT data, ServerPlayerEntity serverPlayerEntity, World world, TileEntity tileEntity) {
        StorageBarrelTileEntity barrel = (StorageBarrelTileEntity) tileEntity;
        if (!barrel.getBarrelContent().isEmpty()) {
            data.put("Content", ItemUtils.writeOverstackedItem(new CompoundNBT(), barrel.getBarrelContent()));
        }
    }

}