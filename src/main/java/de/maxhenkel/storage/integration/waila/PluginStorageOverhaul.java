package de.maxhenkel.storage.integration.waila;

import de.maxhenkel.storage.entity.StorageOverhaulChestMinecartEntity;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin
public class PluginStorageOverhaul implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.registerComponentProvider(HUDHandlerChestMinecart.INSTANCE, TooltipPosition.HEAD, StorageOverhaulChestMinecartEntity.class);
        registrar.registerComponentProvider(HUDHandlerChestMinecart.INSTANCE, TooltipPosition.BODY, StorageOverhaulChestMinecartEntity.class);
        registrar.registerComponentProvider(HUDHandlerChestMinecart.INSTANCE, TooltipPosition.TAIL, StorageOverhaulChestMinecartEntity.class);
    }

}
