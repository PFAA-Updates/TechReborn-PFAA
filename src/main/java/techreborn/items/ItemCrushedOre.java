package techreborn.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import techreborn.client.TechRebornCreativeTabMisc;
import techreborn.init.ModItems;
import techreborn.utils.RecipeUtils;

public class ItemCrushedOre extends Item {

    @SuppressWarnings("deprecation")
    public static ItemStack getCrushedOreByName(String name, int count) {
        int meta = RecipeUtils.getArrayPos(types, name);
        if (meta == -1) throw new IllegalArgumentException("The crushed ore " + name + " could not be found.");
        return new ItemStack(ModItems.crushedOre, count, meta);
    }

    public static ItemStack getCrushedOreByName(String name) {
        return getCrushedOreByName(name, 1);
    }

    public static final String[] types = new String[] { "Bauxite", "Cinnabar", "Iridium", "Platinum", "Pyrite",
        "Sphalerite", "Tungsten", "Galena" };

    private IIcon[] textures;

    public ItemCrushedOre() {
        setCreativeTab(TechRebornCreativeTabMisc.instance);
        setHasSubtypes(true);
        setUnlocalizedName("techreborn.crushedore");
    }

    @Override
    @SideOnly(Side.CLIENT)
    // Registers Textures For All Dusts
    public void registerIcons(IIconRegister iconRegister) {
        textures = new IIcon[types.length];

        for (int i = 0; i < types.length; ++i) {
            textures[i] = iconRegister.registerIcon("techreborn:" + "crushedOre/crushed" + types[i] + "Ore");
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

}
