package techreborn.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import techreborn.client.TechRebornCreativeTabMisc;
import techreborn.init.ModItems;
import techreborn.utils.RecipeUtils;

public class ItemPlates extends ItemTR {

	public static ItemStack getPlateByName(String name, int count) {
		int meta = RecipeUtils.getArrayPos(types, name);
		if(meta == -1) throw new IllegalArgumentException("The plate " + name + " could not be found!");
		return new ItemStack(ModItems.plate, count, meta);
	}

	public static ItemStack getPlateByName(String name) {
		return getPlateByName(name, 1);
	}

	public static final String[] types = new String[] { "aluminium", "brass", "bronze", "chromium", "copper",
			"electrum", "gold", "invar", "iron", "lead", "magnalium", "nickel", "osmium", "platinum", "refinedIron",
			"silicon", "silver", "steel", "tin", "titanium", "tungsten", "tungstensteel", "wood", "zinc" };

	private IIcon[] textures;

	public ItemPlates() {
		setUnlocalizedName("techreborn.plate");
		setHasSubtypes(true);
		setCreativeTab(TechRebornCreativeTabMisc.instance);
	}

	@SideOnly(Side.CLIENT)
	@Override
	// Registers Textures For All Dusts
	public void registerIcons(IIconRegister iconRegister) {
		textures = new IIcon[types.length];

		for (int i = 0; i < types.length; ++i) {
			textures[i] = iconRegister.registerIcon("techreborn:" + "plate/" + types[i] + "Plate");
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
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
