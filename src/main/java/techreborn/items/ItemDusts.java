package techreborn.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import techreborn.client.TechRebornCreativeTabMisc;
import techreborn.init.ModItems;
import techreborn.utils.RecipeUtils;

public class ItemDusts extends ItemTR {

	public static ItemStack getDustByName(String name, int count) {
		if (name.equalsIgnoreCase("glowstone"))
			return new ItemStack(Items.glowstone_dust);
		if (name.equalsIgnoreCase("redstone"))
			return new ItemStack(Items.redstone);
		if (name.equalsIgnoreCase("gunpowder"))
			return new ItemStack(Items.gunpowder);
		int meta = RecipeUtils.getArrayPos(types, name);
		if (meta == -1)
			throw new IllegalArgumentException("The dust " + name + " could not be found.");
		return new ItemStack(ModItems.dusts, count, meta);
	}

	public static ItemStack getDustByName(String name) {
		return getDustByName(name, 1);
	}

	public static final String[] types = new String[] { "almandine", "aluminium", "andradite", "ashes", "basalt",
			"bauxite", "brass", "bronze", "calcite", "charcoal", "chromium", "cinnabar", "clay", "coal", "copper",
			"darkAshes", "diamond", "electrum", "emerald", "enderEye", "enderPearl", "endstone", "flint", "flour",
			"galena", "gold", "grossular", "invar", "iron", "lazurite", "lead", "magnesium", "manganese", "marble",
			"netherrack", "nickel", "obsidian", "olivine", "osmium", "peridot", "platinum", "plutonium", "pyrite",
			"pyrope", "redGarnet", "redrock", "ruby", "saltpeter", "sapphire", "sawDust", "silver", "sodalite",
			"spessartine", "sphalerite", "steel", "sulfur", "thorium", "tin", "titanium", "tricalciumPhosphate",
			"tungsten", "uranium", "uvarovite", "yellowGarnet", "zinc" };

	private IIcon[] textures;

	public ItemDusts() {
		setUnlocalizedName("techreborn.dust");
		setHasSubtypes(true);
		setCreativeTab(TechRebornCreativeTabMisc.instance);
	}

	@Override
	@SideOnly(Side.CLIENT)
	// Registers Textures For All Dusts
	public void registerIcons(IIconRegister iconRegister) {
		textures = new IIcon[types.length];

		for (int i = 0; i < types.length; ++i) {
			textures[i] = iconRegister.registerIcon("techreborn:" + "dust/" + types[i] + "Dust");
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
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		int meta = par1ItemStack.getItemDamage();
		return meta == RecipeUtils.getArrayPos(types, "plutonium") || meta == RecipeUtils.getArrayPos(types, "thorium")
				|| meta == RecipeUtils.getArrayPos(types, "uranium");
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
