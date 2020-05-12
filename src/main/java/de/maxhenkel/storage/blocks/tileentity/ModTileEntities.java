package de.maxhenkel.storage.blocks.tileentity;

import de.maxhenkel.storage.Main;
import de.maxhenkel.storage.blocks.ModBlocks;
import de.maxhenkel.storage.blocks.tileentity.render.AdvancedShulkerBoxRenderer;
import de.maxhenkel.storage.blocks.tileentity.render.ModChestRenderer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModTileEntities {

    public static TileEntityType<ModChestTileEntity> CHEST;

    public static TileEntityType<ModBarrelTileEntity> BARREL;

    public static TileEntityType<AdvancedShulkerBoxTileEnitity> SHULKER_BOX;

    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        CHEST = TileEntityType.Builder.create(() -> new ModChestTileEntity(null),
                ModBlocks.OAK_CHEST,
                ModBlocks.SPRUCE_CHEST,
                ModBlocks.BIRCH_CHEST,
                ModBlocks.ACACIA_CHEST,
                ModBlocks.JUNGLE_CHEST,
                ModBlocks.DARK_OAK_CHEST
        ).build(null);
        CHEST.setRegistryName(new ResourceLocation(Main.MODID, "chest"));
        event.getRegistry().register(CHEST);

        BARREL = TileEntityType.Builder.create(ModBarrelTileEntity::new,
                ModBlocks.OAK_BARREL,
                ModBlocks.SPRUCE_BARREL,
                ModBlocks.BIRCH_BARREL,
                ModBlocks.ACACIA_BARREL,
                ModBlocks.JUNGLE_BARREL,
                ModBlocks.DARK_OAK_BARREL
        ).build(null);
        BARREL.setRegistryName(new ResourceLocation(Main.MODID, "barrel"));
        event.getRegistry().register(BARREL);

        SHULKER_BOX = TileEntityType.Builder.create(() -> new AdvancedShulkerBoxTileEnitity(SHULKER_BOX, null),
                ModBlocks.WHITE_SHULKER_BOX,
                ModBlocks.ORANGE_SHULKER_BOX,
                ModBlocks.MAGENTA_SHULKER_BOX,
                ModBlocks.LIGHT_BLUE_SHULKER_BOX,
                ModBlocks.YELLOW_SHULKER_BOX,
                ModBlocks.LIME_SHULKER_BOX,
                ModBlocks.PINK_SHULKER_BOX,
                ModBlocks.GRAY_SHULKER_BOX,
                ModBlocks.LIGHT_GRAY_SHULKER_BOX,
                ModBlocks.CYAN_SHULKER_BOX,
                ModBlocks.PURPLE_SHULKER_BOX,
                ModBlocks.BLUE_SHULKER_BOX,
                ModBlocks.BROWN_SHULKER_BOX,
                ModBlocks.GREEN_SHULKER_BOX,
                ModBlocks.RED_SHULKER_BOX,
                ModBlocks.BLACK_SHULKER_BOX
        ).build(null);
        SHULKER_BOX.setRegistryName(new ResourceLocation(Main.MODID, "shulker_box"));
        event.getRegistry().register(SHULKER_BOX);

    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.CHEST, ModChestRenderer::new);

        ClientRegistry.bindTileEntityRenderer(ModTileEntities.SHULKER_BOX, AdvancedShulkerBoxRenderer::new);
    }
}
