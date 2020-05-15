package de.maxhenkel.storage.entity;

import de.maxhenkel.storage.Main;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModEntities {

    public static EntityType<ModChestMinecartEntity> CHEST_MINECART;

    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        CHEST_MINECART = EntityType.Builder.<ModChestMinecartEntity>create(
                (entityType, world) -> new ModChestMinecartEntity(world), EntityClassification.MISC)
                .size(0.98F, 0.7F)
                .build(Main.MODID + ":chest_minecart");
        CHEST_MINECART.setRegistryName(new ResourceLocation(Main.MODID, "chest_minecart"));
        event.getRegistry().register(CHEST_MINECART);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CHEST_MINECART, MinecartRenderer::new);
    }

}
