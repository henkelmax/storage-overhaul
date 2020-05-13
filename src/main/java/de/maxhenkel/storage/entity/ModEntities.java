package de.maxhenkel.storage.entity;

import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.blocks.ModBlocks;
import de.maxhenkel.storage.blocks.ModChestBlock;
import de.maxhenkel.storage.items.ModItems;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModEntities {

    public static EntityType<ModChestMinecartEntity> OAK_CHEST_MINECART;
    public static EntityType<ModChestMinecartEntity> SPRUCE_CHEST_MINECART;
    public static EntityType<ModChestMinecartEntity> BIRCH_CHEST_MINECART;
    public static EntityType<ModChestMinecartEntity> ACACIA_CHEST_MINECART;
    public static EntityType<ModChestMinecartEntity> JUNGLE_CHEST_MINECART;
    public static EntityType<ModChestMinecartEntity> DARK_OAK_CHEST_MINECART;

    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        OAK_CHEST_MINECART = registerMinecart(event, "oak_chest_minecart", ModBlocks.OAK_CHEST, ModItems.OAK_MINECART);
        SPRUCE_CHEST_MINECART = registerMinecart(event, "spruce_chest_minecart", ModBlocks.SPRUCE_CHEST, ModItems.SPRUCE_MINECART);
        BIRCH_CHEST_MINECART = registerMinecart(event, "birch_chest_minecart", ModBlocks.BIRCH_CHEST, ModItems.BIRCH_MINECART);
        ACACIA_CHEST_MINECART = registerMinecart(event, "acacia_chest_minecart", ModBlocks.ACACIA_CHEST, ModItems.ACACIA_MINECART);
        JUNGLE_CHEST_MINECART = registerMinecart(event, "jungle_chest_minecart", ModBlocks.JUNGLE_CHEST, ModItems.JUNGLE_MINECART);
        DARK_OAK_CHEST_MINECART = registerMinecart(event, "dark_oak_chest_minecart", ModBlocks.DARK_OAK_CHEST, ModItems.DARK_OAK_MINECART);
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

    private static EntityType<ModChestMinecartEntity> registerMinecart(RegistryEvent.Register<EntityType<?>> event, String name, ModChestBlock block, Item item) {
        EntityType<ModChestMinecartEntity> type = EntityType.Builder.<ModChestMinecartEntity>create(
                (entityType, world) -> new ModChestMinecartEntity(entityType, world, block, item), EntityClassification.MISC)
                .size(0.98F, 0.7F)
                .build(Main.MODID + ":" + name);
        type.setRegistryName(new ResourceLocation(Main.MODID, name));
        event.getRegistry().register(type);
        return type;
    }

}
