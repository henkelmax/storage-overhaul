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

    public HugeChestScreen(HugeChestContainer container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
        this.playerInventory = playerInventory;
        xSize = 338;

        inventoryRows = container.getNumRows();
        ySize = 114 + inventoryRows * 18;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        font.func_243248_b(matrixStack, getTitle(), 8F, 6F, FONT_COLOR);
        font.func_243248_b(matrixStack, playerInventory.getDisplayName(), 89F, (float) (ySize - 96 + 2), FONT_COLOR);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float f) {
        super.render(matrixStack, x, y, f);
        renderHoveredTooltip(matrixStack, x, y);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        renderBackground(matrixStack);
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        minecraft.getTextureManager().bindTexture(TEXTURE);

        int invStart = inventoryRows * 18 + 17;
        blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, invStart, 512, 512);
        blit(matrixStack, guiLeft, guiTop + invStart, 0, 179, xSize, 96, 512, 512);
    }

}