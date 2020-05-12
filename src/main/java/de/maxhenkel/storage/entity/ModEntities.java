package de.maxhenkel.storage.entity;

import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.blocks.ModBlocks;
import de.maxhenkel.storage.blocks.StorageOverhaulChestBlock;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModEntities {

    public static EntityType<StorageOverhaulChestMinecartEntity> OAK_CHEST_MINECART;
    public static EntityType<StorageOverhaulChestMinecartEntity> SPRUCE_CHEST_MINECART;
    public static EntityType<StorageOverhaulChestMinecartEntity> BIRCH_CHEST_MINECART;
    public static EntityType<StorageOverhaulChestMinecartEntity> ACACIA_CHEST_MINECART;
    public static EntityType<StorageOverhaulChestMinecartEntity> JUNGLE_CHEST_MINECART;
    public static EntityType<StorageOverhaulChestMinecartEntity> DARK_OAK_CHEST_MINECART;

    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        OAK_CHEST_MINECART = registerMinecart(event, "oak_chest_minecart", ModBlocks.OAK_CHEST);
        SPRUCE_CHEST_MINECART = registerMinecart(event, "spruce_chest_minecart", ModBlocks.SPRUCE_CHEST);
        BIRCH_CHEST_MINECART = registerMinecart(event, "birch_chest_minecart", ModBlocks.BIRCH_CHEST);
        ACACIA_CHEST_MINECART = registerMinecart(event, "acacia_chest_minecart", ModBlocks.ACACIA_CHEST);
        JUNGLE_CHEST_MINECART = registerMinecart(event, "jungle_chest_minecart", ModBlocks.JUNGLE_CHEST);
        DARK_OAK_CHEST_MINECART = registerMinecart(event, "dark_oak_chest_minecart", ModBlocks.DARK_OAK_CHEST);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.OAK_CHEST_MINECART, MinecartRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SPRUCE_CHEST_MINECART, MinecartRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BIRCH_CHEST_MINECART, MinecartRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ACACIA_CHEST_MINECART, MinecartRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.JUNGLE_CHEST_MINECART, MinecartRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.DARK_OAK_CHEST_MINECART, MinecartRenderer::new);
    }

    private static EntityType<StorageOverhaulChestMinecartEntity> registerMinecart(RegistryEvent.Register<EntityType<?>> event, String name, StorageOverhaulChestBlock block) {
        EntityType<StorageOverhaulChestMinecartEntity> type = EntityType.Builder.<StorageOverhaulChestMinecartEntity>create(
                (entityType, world) -> new StorageOverhaulChestMinecartEntity(entityType, world, block), EntityClassification.MISC)
                .size(0.98F, 0.7F)
                .build(Main.MODID + ":" + name);
        type.setRegistryName(new ResourceLocation(Main.MODID, name));
        event.getRegistry().register(type);
        return type;
    }

}
