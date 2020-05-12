package de.maxhenkel.storage.gui;

import de.maxhenkel.storage.Main;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;

public class Containers {

    public static ContainerType<AdvancedShulkerboxContainer> SHULKERBOX_CONTAINER;

    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        ScreenManager.IScreenFactory factory = (ScreenManager.IScreenFactory<AdvancedShulkerboxContainer, AdvancedShulkerboxScreen>) (container, playerInventory, name) -> new AdvancedShulkerboxScreen(playerInventory, container, name);
        ScreenManager.registerFactory(SHULKERBOX_CONTAINER, factory);
    }

    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        SHULKERBOX_CONTAINER = new ContainerType<>(AdvancedShulkerboxContainer::new);
        SHULKERBOX_CONTAINER.setRegistryName(new ResourceLocation(Main.MODID, "shulkerbox"));
        event.getRegistry().register(SHULKERBOX_CONTAINER);
    }
}
