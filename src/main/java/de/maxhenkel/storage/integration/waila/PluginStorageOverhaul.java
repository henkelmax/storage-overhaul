package de.maxhenkel.storage.integration.waila;

import de.maxhenkel.storage.blocks.tileentity.StorageBarrelTileEntity;
import de.maxhenkel.storage.entity.ModChestMinecartEntity;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.util.ResourceLocation;

@WailaPlugin
public class PluginStorageOverhaul implements IWailaPlugin {

    static final ResourceLocation OBJECT_NAME_TAG = new ResourceLocation("waila", "object_name");
    static final ResourceLocation CONFIG_SHOW_REGISTRY = new ResourceLocation("waila", "show_registry");
    static final ResourceLocation REGISTRY_NAME_TAG = new ResourceLocation("waila", "registry_name");

    @Override
    public void register(IRegistrar registrar) {
        registrar.registerComponentProvider(HUDHandlerChestMinecart.INSTANCE, TooltipPosition.HEAD, ModChestMinecartEntity.class);
        registrar.registerComponentProvider(HUDHandlerChestMinecart.INSTANCE, TooltipPosition.TAIL, ModChestMinecartEntity.class);

        registrar.registerComponentProvider(HUDHandlerStorageBarrel.INSTANCE, TooltipPosition.HEAD, StorageBarrelTileEntity.class);
        registrar.registerComponentProvider(HUDHandlerStorageBarrel.INSTANCE, TooltipPosition.BODY, StorageBarrelTileEntity.class);
        registrar.registerComponentProvider(HUDHandlerStorageBarrel.INSTANCE, TooltipPosition.TAIL, StorageBarrelTileEntity.class);
        registrar.registerBlockDataProvider(HUDHandlerStorageBarrel.INSTANCE, StorageBarrelTileEntity.class);
    }

}
