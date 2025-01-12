package techreborn.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import techreborn.client.container.ContainerChargeBench;
import techreborn.tiles.TileChargeBench;

public class GuiChargeBench extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation("techreborn", "textures/gui/chargebench.png");

    TileChargeBench chargebench;

    public GuiChargeBench(EntityPlayer player, TileChargeBench tile) {
        super(new ContainerChargeBench(tile, player));
        this.xSize = 176;
        this.ySize = 167;
        chargebench = tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager()
            .bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        int j = 0;

        j = chargebench.getEnergyScaled(12);
        if (j > 0) {
            this.drawTexturedModalRect(k + 10, l + 32 + 12 - j, 176, 12 - j, 14, j + 2);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = StatCollector.translateToLocal("tile.techreborn.chargebench.name");
        this.fontRendererObj
            .drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
        this.fontRendererObj
            .drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }
}
