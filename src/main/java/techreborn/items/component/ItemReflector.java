package techreborn.items.component;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import techreborn.client.TechRebornCreativeTab;

public class ItemReflector extends Item implements IReactorComponent {

    public ItemReflector(int maxDamage) {
        setCreativeTab(TechRebornCreativeTab.instance);
        setUnlocalizedName("techreborn.reflector");
        setMaxDamage(maxDamage);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected String getIconString() {
        return "techreborn:component/reflector";
    }

    @Override
    public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {}

    @Override
    public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY,
        int pulseX, int pulseY, boolean heatrun) {
        if (!heatrun) ((IReactorComponent) pulsingStack.getItem())
            .acceptUraniumPulse(reactor, pulsingStack, yourStack, pulseX, pulseY, youX, youY, heatrun);
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
        return -1;
    }

}
