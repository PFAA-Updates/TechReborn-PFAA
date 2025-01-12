package techreborn.utils;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeUtils {

    public static int getArrayPos(String[] types, String target) {
        for (int index = 0; index < types.length; index++) {
            if (types[index].equalsIgnoreCase(target)) return index;
        }
        return -1;
    }

    /**
     * Credits to KingLemming
     * (https://github.com/CoFH/CoFHLib/blob/1.7.10/src/main/java/cofh/lib/util/helpers/ItemHelper.java#L272-L307)
     */
    public static ItemStack findMatchingRecipe(InventoryCrafting inv, World world) {
        ItemStack[] dmgItems = new ItemStack[2];
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if (inv.getStackInSlot(i) != null) {
                if (dmgItems[0] == null) {
                    dmgItems[0] = inv.getStackInSlot(i);
                } else {
                    dmgItems[1] = inv.getStackInSlot(i);
                    break;
                }
            }
        }
        if (dmgItems[0] == null || dmgItems[1] == null) {
            return null;
        } else if (dmgItems[1] != null && dmgItems[0].getItem() == dmgItems[1].getItem()
            && dmgItems[0].stackSize == 1
            && dmgItems[1].stackSize == 1
            && dmgItems[0].getItem()
                .isRepairable()) {
                    Item item = dmgItems[0].getItem();
                    int i = item.getMaxDamage() - dmgItems[0].getItemDamageForDisplay();
                    int j = item.getMaxDamage() - dmgItems[1].getItemDamageForDisplay();
                    int k = i + j + item.getMaxDamage() * 5 / 100;
                    int l = Math.max(0, item.getMaxDamage() - k);
                    return new ItemStack(dmgItems[0].getItem(), 1, l);
                } else {
                    return findMatchingRecipeSimple(inv, world);
                }
    }

    public static ItemStack findMatchingRecipeSimple(InventoryCrafting inv, World world) {
        IRecipe recipe;
        for (int i = 0; i < CraftingManager.getInstance()
            .getRecipeList()
            .size(); i++) {
            recipe = (IRecipe) CraftingManager.getInstance()
                .getRecipeList()
                .get(i);
            if (recipe.matches(inv, world)) {
                return recipe.getCraftingResult(inv);
            }
        }
        return null;
    }
}
