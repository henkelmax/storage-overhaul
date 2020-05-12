package de.maxhenkel.storage.integration.waila;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITaggableList;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class HUDHandlerChestMinecart implements IEntityComponentProvider {

    static final ResourceLocation OBJECT_NAME_TAG = new ResourceLocation("waila", "object_name");
    static final ResourceLocation CONFIG_SHOW_REGISTRY = new ResourceLocation("waila", "show_registry");
    static final ResourceLocation REGISTRY_NAME_TAG = new ResourceLocation("waila", "registry_name");

    static final HUDHandlerChestMinecart INSTANCE = new HUDHandlerChestMinecart();

    @Override
    public void appendHead(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        ((ITaggableList<ResourceLocation, ITextComponent>) tooltip).setTag(OBJECT_NAME_TAG, new StringTextComponent(String.format(Waila.CONFIG.get().getFormatting().getEntityName(), accessor.getEntity().getDisplayName().getFormattedText())));
        if (config.get(CONFIG_SHOW_REGISTRY))
            ((ITaggableList<ResourceLocation, ITextComponent>) tooltip).setTag(REGISTRY_NAME_TAG, new StringTextComponent(accessor.getEntity().getType().getRegistryName().toString()).setStyle(new Style().setColor(TextFormatting.GRAY)));
    }

    @Override
    public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        //if (!(accessor.getEntity() instanceof StorageOverhaulChestMinecartEntity)) {
        //    return;
        //}
        //StorageOverhaulChestMinecartEntity chestMinecart = (StorageOverhaulChestMinecartEntity) accessor.getEntity();
    }

    @Override
    public void appendTail(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        tooltip.add(new StringTextComponent(String.format(Waila.CONFIG.get().getFormatting().getModName(), ModIdentification.getModInfo(accessor.getEntity()).getName())));
    }
}