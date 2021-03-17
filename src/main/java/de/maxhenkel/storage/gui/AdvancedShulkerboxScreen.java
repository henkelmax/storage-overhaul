package de.maxhenkel.storage.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AdvancedShulkerboxScreen extends ScreenBase<AdvancedShulkerboxContainer> {

    public static final ResourceLocation DEFAULT_IMAGE = new ResourceLocation("textures/gui/container/shulker_box.png");

    private PlayerInventory playerInventory;

    public AdvancedShulkerboxScreen(AdvancedShulkerboxContainer shulkerboxContainer, PlayerInventory playerInventory, ITextComponent name) {
        super(DEFAULT_IMAGE, shulkerboxContainer, playerInventory, name);
        this.playerInventory = playerInventory;
        imageWidth = 176;
        imageHeight = 166;
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        font.draw(matrixStack, getTitle(), 8F, 6F, FONT_COLOR);
        font.draw(matrixStack, playerInventory.getDisplayName(), 8F, (float) (imageHeight - 96 + 3), FONT_COLOR);
    }

}