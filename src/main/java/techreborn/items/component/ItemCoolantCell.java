package techreborn.items.component;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import techreborn.client.TechRebornCreativeTab;

public class ItemCoolantCell extends Item implements IReactorComponent {

	private int heatStorage;
	private String name;

	public ItemCoolantCell(int maxHeat, String name) {
		this.heatStorage = maxHeat;
		this.name = "coolant" + name;
		setMaxStackSize(1);
		setMaxDamage(100);
		setNoRepair();
		setCreativeTab(TechRebornCreativeTab.instance);
		setUnlocalizedName("techreborn." + this.name);
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected String getIconString() {
		return "techreborn:component/" + this.name;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> p_77624_3_, boolean p_77624_4_) {
		p_77624_3_.add("Stored Heat: " + getHeatOfStack(p_77624_1_));
	}

	@Override
	public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
	}

	@Override
	public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY,
			int pulseX, int pulseY, boolean heatrun) {
		return false;
	}

	@Override
	public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
		return true;
	}

	@Override
	public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
		return heatStorage;
	}

	@Override
	public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
		return getHeatOfStack(yourStack);
	}

	@Override
	public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
		int i = this.getHeatOfStack(yourStack) + heat;
		if (i > this.heatStorage) {
			reactor.setItemAt(x, y, null);
			heat = this.heatStorage - i + 1;
		} else {
			if (i < 0) {
				heat = i;
				i = 0;
			} else {
				heat = 0;
			}
			this.setHeatForStack(yourStack, i);
		}
		return heat;
	}

	@Override
	public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
		return 1 + heatStorage / 30000;
	}

	private void setHeatForStack(ItemStack stack, int heat) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		nbt.setInteger("heat", heat);
		if (this.heatStorage > 0) {
			double d = heat / this.heatStorage;
			int i = (int) (stack.getItemDamage() * d);
			if (i >= stack.getItemDamage())
				i = stack.getItemDamage() - 1;
			stack.setItemDamage(i);
		}
	}

	private int getHeatOfStack(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		return nbt.getInteger("heat");
	}

}
