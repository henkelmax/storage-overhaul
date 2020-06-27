package de.maxhenkel.storage.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AdvancedShulkerboxScreen extends ContainerScreen<AdvancedShulkerboxContainer> {

    protected static final int FONT_COLOR = 4210752;
    public static final ResourceLocation DEFAULT_IMAGE = new ResourceLocation("textures/gui/container/shulker_box.png");

    private PlayerInventory playerInventory;

    public AdvancedShulkerboxScreen(PlayerInventory playerInventory, AdvancedShulkerboxContainer shulkerboxContainer, ITextComponent name) {
        super(shulkerboxContainer, playerInventory, name);

        this.playerInventory = playerInventory;
        xSize = 176;
        ySize = 166;
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        field_230712_o_.func_238421_b_(matrixStack, field_230704_d_.getString(), 8F, 6F, FONT_COLOR);
        field_230712_o_.func_238421_b_(matrixStack, playerInventory.getDisplayName().getString(), 8F, (float) (ySize - 96 + 3), FONT_COLOR);
    }

    @Override
    protected void func_230450_a_(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        func_230446_a_(matrixStack);
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        field_230706_i_.getTextureManager().bindTexture(DEFAULT_IMAGE);

        func_238474_b_(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}