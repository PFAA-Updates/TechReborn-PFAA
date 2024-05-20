package techreborn.client.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import techreborn.client.container.ContainerLesu;
import techreborn.tiles.lesu.TileLesu;

public class GuiLesu extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation(
            "techreborn", "textures/gui/aesu.png");

    TileLesu aesu;

    ContainerLesu containerLesu;

    public GuiLesu(EntityPlayer player,
                   TileLesu tileaesu) {
        super(new ContainerLesu(tileaesu, player));
        this.xSize = 176;
        this.ySize = 165;
        aesu = tileaesu;
        this.containerLesu = (ContainerLesu) this.inventorySlots;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks,
                                                   int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }

    @Override
	protected void drawGuiContainerForegroundLayer(int mouseX,
                                                   int mouseY) {
        this.fontRendererObj.drawString(StatCollector.translateToLocal("tile.techreborn.lesu.name"), 40, 10, Color.WHITE.getRGB());
        this.fontRendererObj.drawString(GetEUString(containerLesu.euOut) + "/t", 10, 20, Color.WHITE.getRGB());
        this.fontRendererObj.drawString(GetEUString(containerLesu.storedEu), 10, 30, Color.WHITE.getRGB());
        this.fontRendererObj.drawString(GetEUString(containerLesu.euChange) + " change", 10, 40, Color.WHITE.getRGB());
        this.fontRendererObj.drawString(containerLesu.connectedBlocks + " blocks", 10, 50, Color.WHITE.getRGB());
        this.fontRendererObj.drawString(GetEUString(containerLesu.euStorage) + " max", 10, 60, Color.WHITE.getRGB());
    }

    private String GetEUString(double euValue) {
        if (euValue >= 1000000) {
            double tenX = Math.round(euValue / 100000);
            return Double.toString(tenX / 10.0).concat(" m EU");
        } else if (euValue >= 1000) {
            double tenX = Math.round(euValue / 100);
            return Double.toString(tenX / 10.0).concat(" k EU");
        } else {
            return Double.toString(Math.floor(euValue)).concat(" EU");
        }
    }
}
