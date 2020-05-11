package de.maxhenkel.storage.blocks.tileentity.render;

import de.maxhenkel.storage.Main;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.state.properties.ChestType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChestAtlases {
    private static final ResourceLocation CHEST_OAK_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/oak");
    private static final ResourceLocation CHEST_OAK_LEFT_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/oak_left");
    private static final ResourceLocation CHEST_OAK_RIGHT_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/oak_right");

    private static final ResourceLocation CHEST_SPRUCE_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/spruce");
    private static final ResourceLocation CHEST_SPRUCE_LEFT_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/spruce_left");
    private static final ResourceLocation CHEST_SPRUCE_RIGHT_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/spruce_right");

    private static final ResourceLocation CHEST_BIRCH_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/birch");
    private static final ResourceLocation CHEST_BIRCH_LEFT_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/birch_left");
    private static final ResourceLocation CHEST_BIRCH_RIGHT_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/birch_right");

    private static final ResourceLocation CHEST_ACACIA_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/acacia");
    private static final ResourceLocation CHEST_ACACIA_LEFT_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/acacia_left");
    private static final ResourceLocation CHEST_ACACIA_RIGHT_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/acacia_right");

    private static final ResourceLocation CHEST_JUNGLE_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/jungle");
    private static final ResourceLocation CHEST_JUNGLE_LEFT_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/jungle_left");
    private static final ResourceLocation CHEST_JUNGLE_RIGHT_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/jungle_right");

    private static final ResourceLocation CHEST_DARK_OAK_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/dark_oak");
    private static final ResourceLocation CHEST_DARK_OAK_LEFT_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/dark_oak_left");
    private static final ResourceLocation CHEST_DARK_OAK_RIGHT_LOCATION = new ResourceLocation(Main.MODID, "entity/chest/dark_oak_right");

    private static final Material CHEST_OAK_MATERIAL = getChestMaterial(CHEST_OAK_LOCATION);
    private static final Material CHEST_OAK_LEFT_MATERIAL = getChestMaterial(CHEST_OAK_LEFT_LOCATION);
    private static final Material CHEST_OAK_RIGHT_MATERIAL = getChestMaterial(CHEST_OAK_RIGHT_LOCATION);

    private static final Material CHEST_SPRUCE_MATERIAL = getChestMaterial(CHEST_SPRUCE_LOCATION);
    private static final Material CHEST_SPRUCE_LEFT_MATERIAL = getChestMaterial(CHEST_SPRUCE_LEFT_LOCATION);
    private static final Material CHEST_SPRUCE_RIGHT_MATERIAL = getChestMaterial(CHEST_SPRUCE_RIGHT_LOCATION);

    private static final Material CHEST_BIRCH_MATERIAL = getChestMaterial(CHEST_BIRCH_LOCATION);
    private static final Material CHEST_BIRCH_LEFT_MATERIAL = getChestMaterial(CHEST_BIRCH_LEFT_LOCATION);
    private static final Material CHEST_BIRCH_RIGHT_MATERIAL = getChestMaterial(CHEST_BIRCH_RIGHT_LOCATION);

    private static final Material CHEST_ACACIA_MATERIAL = getChestMaterial(CHEST_ACACIA_LOCATION);
    private static final Material CHEST_ACACIA_LEFT_MATERIAL = getChestMaterial(CHEST_ACACIA_LEFT_LOCATION);
    private static final Material CHEST_ACACIA_RIGHT_MATERIAL = getChestMaterial(CHEST_ACACIA_RIGHT_LOCATION);

    private static final Material CHEST_JUNGLE_MATERIAL = getChestMaterial(CHEST_JUNGLE_LOCATION);
    private static final Material CHEST_JUNGLE_LEFT_MATERIAL = getChestMaterial(CHEST_JUNGLE_LEFT_LOCATION);
    private static final Material CHEST_JUNGLE_RIGHT_MATERIAL = getChestMaterial(CHEST_JUNGLE_RIGHT_LOCATION);

    private static final Material CHEST_DARK_OAK_MATERIAL = getChestMaterial(CHEST_DARK_OAK_LOCATION);
    private static final Material CHEST_DARK_OAK_LEFT_MATERIAL = getChestMaterial(CHEST_DARK_OAK_LEFT_LOCATION);
    private static final Material CHEST_DARK_OAK_RIGHT_MATERIAL = getChestMaterial(CHEST_DARK_OAK_RIGHT_LOCATION);

    public static final ChestMaterial CHEST_OAK = new ChestMaterial(CHEST_OAK_MATERIAL, CHEST_OAK_LEFT_MATERIAL, CHEST_OAK_RIGHT_MATERIAL);
    public static final ChestMaterial CHEST_SPRUCE = new ChestMaterial(CHEST_SPRUCE_MATERIAL, CHEST_SPRUCE_LEFT_MATERIAL, CHEST_SPRUCE_RIGHT_MATERIAL);
    public static final ChestMaterial CHEST_BIRCH = new ChestMaterial(CHEST_BIRCH_MATERIAL, CHEST_BIRCH_LEFT_MATERIAL, CHEST_BIRCH_RIGHT_MATERIAL);
    public static final ChestMaterial CHEST_ACACIA = new ChestMaterial(CHEST_ACACIA_MATERIAL, CHEST_ACACIA_LEFT_MATERIAL, CHEST_ACACIA_RIGHT_MATERIAL);
    public static final ChestMaterial CHEST_JUNGLE = new ChestMaterial(CHEST_JUNGLE_MATERIAL, CHEST_JUNGLE_LEFT_MATERIAL, CHEST_JUNGLE_RIGHT_MATERIAL);
    public static final ChestMaterial CHEST_DARK_OAK = new ChestMaterial(CHEST_DARK_OAK_MATERIAL, CHEST_DARK_OAK_LEFT_MATERIAL, CHEST_DARK_OAK_RIGHT_MATERIAL);

    private static Material getChestMaterial(ResourceLocation location) {
        return new Material(Atlases.CHEST_ATLAS, location);
    }

    public static Material getChestMaterial(ChestMaterial chestMaterial, ChestType chestType) {
        switch (chestType) {
            case LEFT:
                return chestMaterial.left;
            case RIGHT:
                return chestMaterial.right;
            case SINGLE:
            default:
                return chestMaterial.single;
        }
    }

    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Pre event) {
        if (!event.getMap().getTextureLocation().equals(Atlases.CHEST_ATLAS)) {
            return;
        }

        event.addSprite(CHEST_OAK_LOCATION);
        event.addSprite(CHEST_OAK_LEFT_LOCATION);
        event.addSprite(CHEST_OAK_RIGHT_LOCATION);

        event.addSprite(CHEST_SPRUCE_LOCATION);
        event.addSprite(CHEST_SPRUCE_LEFT_LOCATION);
        event.addSprite(CHEST_SPRUCE_RIGHT_LOCATION);

        event.addSprite(CHEST_BIRCH_LOCATION);
        event.addSprite(CHEST_BIRCH_LEFT_LOCATION);
        event.addSprite(CHEST_BIRCH_RIGHT_LOCATION);

        event.addSprite(CHEST_ACACIA_LOCATION);
        event.addSprite(CHEST_ACACIA_LEFT_LOCATION);
        event.addSprite(CHEST_ACACIA_RIGHT_LOCATION);

        event.addSprite(CHEST_JUNGLE_LOCATION);
        event.addSprite(CHEST_JUNGLE_LEFT_LOCATION);
        event.addSprite(CHEST_JUNGLE_RIGHT_LOCATION);

        event.addSprite(CHEST_DARK_OAK_LOCATION);
        event.addSprite(CHEST_DARK_OAK_LEFT_LOCATION);
        event.addSprite(CHEST_DARK_OAK_RIGHT_LOCATION);
    }

    public static class ChestMaterial {
        private Material single, left, right;

        public ChestMaterial(Material single, Material left, Material right) {
            this.single = single;
            this.left = left;
            this.right = right;
        }
    }
}