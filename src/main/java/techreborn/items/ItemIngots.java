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

public class ItemIngots extends Item {
	public static ItemStack getIngotByName(String name, int count) {
		int meta = RecipeUtils.getArrayPos(types, name);
		if (meta == -1)
			throw new IllegalArgumentException("The ingot " + name + " could not be found.");
		return new ItemStack(ModItems.ingots, count, meta);
	}

	public static ItemStack getIngotByName(String name) {
		return getIngotByName(name, 1);
	}

	public static final String[] types = new String[] { "aluminium", "brass", "chromium", "copper", "electrum", "invar",
			"iridium", "lead", "nickel", "osmium", "platinum", "plutonium", "silver", "steel", "thorium", "titanium",
			"tungsten", "hotTungstensteel", "tungstensteel", "zinc" };

	private IIcon[] textures;

	public ItemIngots() {
		setCreativeTab(TechRebornCreativeTabMisc.instance);
		setHasSubtypes(true);
		setUnlocalizedName("techreborn.ingot");
	}

	@Override
	@SideOnly(Side.CLIENT)
	// Registers Textures For All Dusts
	public void registerIcons(IIconRegister iconRegister) {
		textures = new IIcon[types.length];

		for (int i = 0; i < types.length; ++i) {
			textures[i] = iconRegister.registerIcon("techreborn:" + "ingot/" + types[i] + "Ingot");
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
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		int meta = par1ItemStack.getItemDamage();
		return meta == RecipeUtils.getArrayPos(types, "plutonium") || meta == RecipeUtils.getArrayPos(types, "thorium");
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
