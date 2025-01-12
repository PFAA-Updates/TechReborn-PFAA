package techreborn.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import techreborn.Core;
import techreborn.client.GuiHandler;
import techreborn.client.TechRebornCreativeTab;
import techreborn.init.ModItems;
import techreborn.utils.RecipeUtils;

public class ItemParts extends Item {

    @SuppressWarnings("deprecation")
    public static ItemStack getPartByName(String name, int count) {
        int meta = RecipeUtils.getArrayPos(types, name);
        if (meta == -1) throw new IllegalArgumentException("The part " + name + " could not be found.");
        return new ItemStack(ModItems.parts, count, meta);
    }

    public static ItemStack getPartByName(String name) {
        return getPartByName(name, 1);
    }

    public static final String[] types = new String[] { "advancedCircuitParts", "basicCircuitBoard",
        "advancedCircuitBoard", "processorCircuitBoard", "energyFlowCircuit", "dataControlCircuit", "dataOrb",
        "dataStorageCircuit", "diamondGrindingHead", "diamondSawBlade", "tungstenGrindingHead",
        "cupronickelHeatingCoil", "nichromeHeatingCoil", "kanthalHeatingCoil", "ductTape", "lazuriteChunk",
        "iridiumAlloyIngot", "superConductor", "destructoPack", "massHoleDevice", "computerMonitor", "machineParts",
        "conveyorModule", "copperCredit", "silverCredit", "goldCredit", "diamondCredit" };

    private IIcon[] textures;

    public ItemParts() {
        setCreativeTab(TechRebornCreativeTab.instance);
        setHasSubtypes(true);
        setUnlocalizedName("techreborn.part");
    }

    @Override
    @SideOnly(Side.CLIENT)
    // Registers Textures For All Dusts
    public void registerIcons(IIconRegister iconRegister) {
        textures = new IIcon[types.length];

        for (int i = 0; i < types.length; ++i) {
            textures[i] = iconRegister.registerIcon("techreborn:" + "component/" + types[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    // Adds Texture what match's meta data
    public IIcon getIconFromDamage(int meta) {
        if (meta < 0 || meta >= textures.length) {
            meta = 0;
        }

        return textures[meta];
    }

    @Override
    // gets Unlocalized Name depending on meta data
    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= types.length) {
            meta = 0;
        }

        return super.getUnlocalizedName() + "." + types[meta];
    }

    // Adds Dusts SubItems To Creative Tab
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
        for (int meta = 0; meta < types.length; ++meta) {
            list.add(new ItemStack(item, 1, meta));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (itemStack.getItemDamage() == RecipeUtils.getArrayPos(types, "destructoPack")) player.openGui(
            Core.INSTANCE,
            GuiHandler.destructoPackID,
            world,
            (int) player.posX,
            (int) player.posY,
            (int) player.posY);
        return itemStack;
    }
}
