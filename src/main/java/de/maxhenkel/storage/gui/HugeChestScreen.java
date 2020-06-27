package de.maxhenkel.storage.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.maxhenkel.storage.Main;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class HugeChestScreen extends ContainerScreen<HugeChestContainer> {

    protected static final int FONT_COLOR = 4210752;
    public static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/container/huge_chest.png");

    private final PlayerInventory playerInventory;
    private final int inventoryRows;

    public HugeChestScreen(PlayerInventory playerInventory, HugeChestContainer container, ITextComponent name) {
        super(container, playerInventory, name);
        this.playerInventory = playerInventory;
        //passEvents = false;
        xSize = 338;

        inventoryRows = container.getNumRows();
        ySize = 114 + inventoryRows * 18;
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        field_230712_o_.func_238421_b_(matrixStack, field_230704_d_.getString(), 8F, 6F, FONT_COLOR);
        field_230712_o_.func_238421_b_(matrixStack, playerInventory.getDisplayName().getString(), 89F, (float) (ySize - 96 + 2), FONT_COLOR);
    }

    @Override
    protected void func_230450_a_(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        func_230446_a_(matrixStack);
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        field_230706_i_.getTextureManager().bindTexture(TEXTURE);

        int invStart = inventoryRows * 18 + 17;
        func_238463_a_(matrixStack, guiLeft, guiTop, 0, 0, xSize, invStart, 512, 512);
        func_238463_a_(matrixStack, guiLeft, guiTop + invStart, 0, 179, xSize, 96, 512, 512);
    }

}