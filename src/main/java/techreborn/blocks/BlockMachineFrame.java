package techreborn.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import techreborn.client.TechRebornCreativeTab;
import techreborn.init.ModBlocks;
import techreborn.utils.RecipeUtils;

public class BlockMachineFrame extends Block {

	public static ItemStack getFrameByName(String name, int count) {
		int meta = RecipeUtils.getArrayPos(types, name);
		if (meta == -1)
			throw new IllegalArgumentException("The machine hull " + name + " could not be found.");
		return new ItemStack(ModBlocks.machineframe, count, meta);
	}

	public static final String[] types = new String[] { "aluminium", "iron", "bronze", "brass", "steel", "titanium" };

	private IIcon[] textures;

	public BlockMachineFrame(Material material) {
		super(material);
		setBlockName("techreborn.machineFrame");
		setCreativeTab(TechRebornCreativeTab.instance);
		setHardness(1f);
		ModBlocks.blocksToCut.add(this);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
		for (int meta = 0; meta < types.length; meta++) {
			list.add(new ItemStack(item, 1, meta));
		}
	}

	@Override
	public int damageDropped(int metaData) {
		return metaData;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.textures = new IIcon[types.length];

		for (int i = 0; i < types.length; i++) {
			textures[i] = iconRegister.registerIcon("techreborn:machine/" + types[i] + "_machine_block");
		}
	}

	@Override
	public IIcon getIcon(int side, int metaData) {
		metaData = MathHelper.clamp_int(metaData, 0, types.length - 1);

		if (ForgeDirection.getOrientation(side) == ForgeDirection.UP
				|| ForgeDirection.getOrientation(side) == ForgeDirection.DOWN) {
			return textures[metaData];
		} else {
			return textures[metaData];
		}
	}

}
