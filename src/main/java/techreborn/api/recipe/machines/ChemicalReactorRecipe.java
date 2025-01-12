package techreborn.api.recipe.machines;

import net.minecraft.item.ItemStack;

import techreborn.api.recipe.BaseRecipe;
import techreborn.lib.Reference;

public class ChemicalReactorRecipe extends BaseRecipe {

    private boolean useOreDictionary = true;

    public ChemicalReactorRecipe(ItemStack input1, ItemStack input2, ItemStack output1, int tickTime, int euPerTick) {
        super(Reference.chemicalReactorRecipe, tickTime, euPerTick);
        if (input1 != null) inputs.add(input1);
        if (input2 != null) inputs.add(input2);
        if (output1 != null) addOutput(output1);
    }

    public ChemicalReactorRecipe(ItemStack input1, ItemStack input2, ItemStack output1, int tickTime, int euPerTick,
        boolean useOreDict) {
        this(input1, input2, output1, tickTime, euPerTick);
        this.useOreDictionary = useOreDict;
    }

    @Override
    public String getUserFreindlyName() {
        return "Chemical Reactor";
    }

    @Override
    public boolean useOreDic() {
        return this.useOreDictionary;
    }
}
