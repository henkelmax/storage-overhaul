package de.maxhenkel.storage.gui;

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
        passEvents = false;
        xSize = 230;

        inventoryRows = container.getNumRows();
        ySize = 114 + inventoryRows * 18;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        font.drawString(title.getFormattedText(), 8F, 6F, FONT_COLOR);
        font.drawString(playerInventory.getDisplayName().getFormattedText(), 35F, (float) (ySize - 96 + 2), FONT_COLOR);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        minecraft.getTextureManager().bindTexture(TEXTURE);
        int invStart = inventoryRows * 18 + 17;
        blit(guiLeft, guiTop, 0, 0, xSize, invStart, 512, 512);
        blit(guiLeft, guiTop + invStart, 0, 179, xSize, 96, 512, 512);
    }

}