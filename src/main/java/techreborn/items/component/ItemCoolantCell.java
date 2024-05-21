package techreborn.items.component;

import java.util.List;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import techreborn.client.TechRebornCreativeTab;

public class ItemCoolantCell extends Item implements IReactorComponent {

	private int heatStorage;

	public ItemCoolantCell(int maxHeat, String name) {
		this.heatStorage = maxHeat;
		name = "coolant" + name;
        setCreativeTab(TechRebornCreativeTab.instance);
        setMaxDamage(100);
		setMaxStackSize(1);
		setNoRepair();
		setTextureName("techreborn:component/" + name);
		setUnlocalizedName("techreborn." + name);
	}

	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> p_77624_3_, boolean p_77624_4_) {
        int heat = getHeatOfStack(p_77624_1_) * 10 / this.heatStorage;
        EnumChatFormatting color = switch (heat) {
            case 0 -> EnumChatFormatting.BLUE;
            case 1, 2 -> EnumChatFormatting.GREEN;
            case 3, 4, 5, 6 -> EnumChatFormatting.YELLOW;
            case 7, 8 -> EnumChatFormatting.RED;
            default -> EnumChatFormatting.DARK_RED;
        };
        p_77624_3_.add(EnumChatFormatting.WHITE + "Stored Heat: " + color + getHeatOfStack(p_77624_1_));
        if (!getControlTagOfStack(p_77624_1_)) {
            p_77624_3_.add(StatCollector.translateToLocal("ic2.reactoritem.heatwarning.line1"));
            p_77624_3_.add(StatCollector.translateToLocal("ic2.reactoritem.heatwarning.line2"));
        }
	}

	@Override
	public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {}

	@Override
	public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY,
			int pulseX, int pulseY, boolean heatrun) {
		return false;
	}

	@Override
	public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
		return !reactor.isFluidCooled() || !getControlTagOfStack(yourStack);
	}

	@Override
	public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
		return this.heatStorage;
	}

	@Override
	public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
		return getHeatOfStack(yourStack);
	}

	@Override
	public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int additionalHeat) {
		int currentHeat = getHeatOfStack(yourStack);
		if(currentHeat == 0 && getControlTagOfStack(yourStack)) {
		    setControlTagOfStack(yourStack, false);
		}
		int newHeat = currentHeat + additionalHeat;
		if (newHeat > this.heatStorage) {
			reactor.setItemAt(x, y, null);
			additionalHeat = this.heatStorage - newHeat + 1;
		} else {
			if (newHeat < 0) {
			    additionalHeat = newHeat;
				newHeat = 0;
			} else {
			    additionalHeat = 0;
			}
			if(newHeat > 0 && !getControlTagOfStack(yourStack) && !reactor.isFluidCooled()) {
			    setControlTagOfStack(yourStack, true);
			}
			this.setHeatForStack(yourStack, newHeat);
		}
		return additionalHeat;
	}

	@Override
	public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
		return 1.0f + this.heatStorage / 30000.0f;
	}

	private void setHeatForStack(ItemStack stack, int heat) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		nbt.setInteger("heat", heat);
		if (this.heatStorage > 0) {
			double heatRatio = (double) heat / (double) this.heatStorage;
			int damage = (int) (stack.getMaxDamage() * heatRatio);
			if (damage >= stack.getMaxDamage())
				damage = stack.getMaxDamage() - 1;
			stack.setItemDamage(damage);
		}
	}

	private static int getHeatOfStack(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			stack.setTagCompound(new NBTTagCompound());
			return 0;
		}
		return nbt.getInteger("heat");
	}

    private static boolean getControlTagOfStack(ItemStack stack) {
        return StackUtil.getOrCreateNbtData(stack).getBoolean("tag");
    }

    private static void setControlTagOfStack(ItemStack stack, boolean tag) {
        StackUtil.getOrCreateNbtData(stack).setBoolean("tag", tag);
    }

}
