package techreborn.items.component;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.IBoxable;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.IC2Potion;
import ic2.core.item.armor.ItemArmorHazmat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import techreborn.client.TechRebornCreativeTab;

public class ItemFuelRod extends Item implements IReactorComponent, IBoxable {

	private final int cellCount;
	private final ItemStack depleted;
	private final float energy;
	private final float heat;
	private final int maxDurability;
	private final String name;
	private final int radiation;

	public ItemFuelRod(String name, int cellCount, int maxDurability, float energy, int radiation, float heat,
			ItemStack depleted) {
		this.cellCount = cellCount;
		this.depleted = depleted;
		this.energy = energy;
		this.heat = heat;
		this.maxDurability = maxDurability;
		this.name = name;
		this.radiation = radiation;
		setCreativeTab(TechRebornCreativeTab.instance);
		setMaxDamage(10000);
		setMaxStackSize(1);
		setNoRepair();
		setUnlocalizedName("techreborn." + name);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> p_77624_3_, boolean p_77624_4_) {
		p_77624_3_.add("Time left: " + (this.maxDurability - this.getDurabilityOfStack(p_77624_1_)) + " seconds");
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected String getIconString() {
		return "techreborn:component/" + this.name;
	}

	@Override
	public void onUpdate(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_,
			boolean p_77663_5_) {
		if (this.radiation > 0 && p_77663_3_ instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) p_77663_3_;
			if (!ItemArmorHazmat.hasCompleteHazmat(entity)) {
				PotionEffect effect = null;
				entity.addPotionEffect(new PotionEffect(IC2Potion.radiation.id,
						radiation * 180 * p_77663_1_.stackSize
								+ Math.max(0, ((effect = entity.getActivePotionEffect(IC2Potion.radiation)) == null ? 0
										: effect.getDuration())),
						Math.max(0, 5 * radiation / 7)));
			}
		}
	}

	@Override
	public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
		if (!reactor.produceEnergy()) {
			return;
		}
		for (int iteration = 0; iteration < this.cellCount; iteration++) {
			int pulses = 1 + this.cellCount / 2;
			if (!heatrun) {
				for (int i = 0; i < pulses; i++) {
					acceptUraniumPulse(reactor, yourStack, yourStack, x, y, x, y, heatrun);
				}
				checkPulsable(reactor, x - 1, y, yourStack, x, y, heatrun);
				checkPulsable(reactor, x + 1, y, yourStack, x, y, heatrun);
				checkPulsable(reactor, x, y - 1, yourStack, x, y, heatrun);
				checkPulsable(reactor, x, y + 1, yourStack, x, y, heatrun);
			} else {
				pulses += this.checkPulsable(reactor, x - 1, y, yourStack, x, y, heatrun)
						+ this.checkPulsable(reactor, x + 1, y, yourStack, x, y, heatrun)
						+ this.checkPulsable(reactor, x, y - 1, yourStack, x, y, heatrun)
						+ this.checkPulsable(reactor, x, y + 1, yourStack, x, y, heatrun);
				int heat = (pulses * pulses + pulses) * 2;
				ArrayList<ItemStackCoord> heatAcceptors = new ArrayList<>();
				this.checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
				this.checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
				this.checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
				this.checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);
				heat = Math.round(heat * this.heat);
				while (heatAcceptors.size() > 0 && heat > 0) {
					int dHeat = heat / heatAcceptors.size();
					heat -= dHeat;
					dHeat = ((IReactorComponent) heatAcceptors.get(0).stack.getItem()).alterHeat(reactor,
							heatAcceptors.get(0).stack, heatAcceptors.get(0).x, heatAcceptors.get(0).y, dHeat);
					heat += dHeat;
					heatAcceptors.remove(0);
				}
				if (heat > 0) {
					reactor.addHeat(heat);
				}
			}
		}
		if (getDurabilityOfStack(yourStack) > this.maxDurability) {
			reactor.setItemAt(x, y, depleted.copy());
		} else if (heatrun) {

		}
	}

	@Override
	public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY,
			int pulseX, int pulseY, boolean heatrun) {
		if (!heatrun) {
			reactor.addOutput(this.energy);
		}
		return true;
	}

	@Override
	public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
		return false;
	}

	@Override
	public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
		return 0;
	}

	@Override
	public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
		return 0;
	}

	@Override
	public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
		return heat;
	}

	@Override
	public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
		return 2 * this.cellCount;
	}

	private void checkHeatAcceptor(IReactor reactor, int x, int y, ArrayList<ItemStackCoord> list) {
		ItemStack stack = reactor.getItemAt(x, y);
		if (stack != null && stack.getItem() instanceof IReactorComponent
				&& ((IReactorComponent) stack.getItem()).canStoreHeat(reactor, stack, x, y)) {
			list.add(new ItemStackCoord(stack, x, y));
		}
	}

	private int checkPulsable(IReactor reactor, int youX, int youY, ItemStack pulsingStack, int pulseX, int pulseY,
			boolean heatrun) {
		ItemStack stack = reactor.getItemAt(youX, youY);
		return (stack != null && stack.getItem() instanceof IReactorComponent && ((IReactorComponent) stack.getItem())
				.acceptUraniumPulse(reactor, stack, pulsingStack, youX, youY, pulseX, pulseY, heatrun)) ? 1 : 0;
	}

	private void damageItemStack(ItemStack stack, int damage) {

	}

	private int getDurabilityOfStack(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		return nbt.getInteger("durability");
	}

	private void setDurabilityOfStack(ItemStack stack, int damage) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		nbt.setInteger("durability", damage);
		if (this.maxDurability > 0) {
			double p = damage / this.maxDurability;
			int newDamage = (int) (stack.getMaxDamage() * p);
			if (newDamage > stack.getMaxDamage()) {
				newDamage = stack.getMaxDamage() - 1;
			}
			stack.setItemDamage(newDamage);
		}
	}

	protected class ItemStackCoord {

		public ItemStack stack;
		public int x;
		public int y;

		public ItemStackCoord(ItemStack stack, int x, int y) {
			this.stack = stack;
			this.x = x;
			this.y = y;
		}

	}

	@Override
	public boolean canBeStoredInToolbox(ItemStack itemstack) {
		return true;
	}

}
