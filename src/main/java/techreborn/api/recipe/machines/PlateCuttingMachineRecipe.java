package techreborn.api.recipe.machines;

import net.minecraft.item.ItemStack;

import techreborn.api.recipe.BaseRecipe;
import techreborn.lib.Reference;

public class PlateCuttingMachineRecipe extends BaseRecipe {

    private boolean useOreDictionary = true;

    public PlateCuttingMachineRecipe(ItemStack input1, ItemStack output1, int tickTime, int euPerTick) {
        super(Reference.plateCuttingMachineRecipe, tickTime, euPerTick);
        if (input1 != null) inputs.add(input1);
        if (output1 != null) addOutput(output1);
    }

    public PlateCuttingMachineRecipe(ItemStack input1, ItemStack output1, int tickTime, int euPerTick,
        boolean useOreDict) {
        this(input1, output1, tickTime, euPerTick);
        this.useOreDictionary = useOreDict;
    }

    @Override
    public String getUserFreindlyName() {
        return "Plate Cutting Machine";
    }

    @Override
    public boolean useOreDic() {
        return this.useOreDictionary;
    }
}
