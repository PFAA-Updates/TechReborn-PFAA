package techreborn.api.recipe.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import techreborn.api.recipe.BaseRecipe;
import techreborn.lib.Reference;
import techreborn.tiles.TileVacuumFreezer;

public class VacuumFreezerRecipe extends BaseRecipe {

    private boolean useOreDictionary = true;
    private boolean useNBT = true;

    public VacuumFreezerRecipe(ItemStack input, ItemStack output, int tickTime, int euPerTick) {
        super(Reference.vacuumFreezerRecipe, tickTime, euPerTick);
        if (input != null) inputs.add(input);
        if (output != null) addOutput(output);
    }

    public VacuumFreezerRecipe(ItemStack input, ItemStack output, int tickTime, int euPerTick, boolean useOreDict) {
        this(input, output, tickTime, euPerTick);
        this.useOreDictionary = useOreDict;
    }

    public VacuumFreezerRecipe(ItemStack input, ItemStack output, int tickTime, int euPerTick, boolean useOreDict, boolean useNBT) {
        this(input, output, tickTime, euPerTick, useOreDict);
        this.useNBT = useNBT;
    }

    @Override
    public String getUserFreindlyName() {
        return "Vacuum Freezer";
    }

    @Override
    public boolean canCraft(TileEntity tile) {
        if (tile instanceof TileVacuumFreezer) {
            if (((TileVacuumFreezer) tile).multiBlockStatus == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean useOreDic() {
        return this.useOreDictionary;
    }

    public boolean useNBT() {
        return this.useNBT;
    }
}
