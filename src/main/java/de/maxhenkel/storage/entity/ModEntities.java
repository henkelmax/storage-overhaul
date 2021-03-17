package de.maxhenkel.storage.entity;

import de.maxhenkel.corelib.CommonRegistry;
import de.maxhenkel.storage.Main;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModEntities {

    public static EntityType<ModChestMinecartEntity> CHEST_MINECART;

    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        CHEST_MINECART = CommonRegistry.registerEntity(Main.MODID, "chest_minecart", EntityClassification.MISC, ModChestMinecartEntity.class, builder -> {
            builder.sized(0.98F, 0.7F);
        });
        event.getRegistry().register(CHEST_MINECART);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CHEST_MINECART, MinecartRenderer::new);
    }

}
