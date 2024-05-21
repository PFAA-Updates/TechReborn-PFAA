package techreborn.api.recipe.machines;

import net.minecraft.item.ItemStack;

import techreborn.api.recipe.BaseRecipe;
import techreborn.lib.Reference;

public class CentrifugeRecipe extends BaseRecipe {

    private boolean useOreDictionary = true;

    public CentrifugeRecipe(ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2, ItemStack output3,
        ItemStack output4, int tickTime, int euPerTick) {
        super(Reference.centrifugeRecipe, tickTime, euPerTick);
        if (input1 != null) inputs.add(input1);
        if (input2 != null) inputs.add(input2);
        if (output1 != null) addOutput(output1);
        if (output2 != null) addOutput(output2);
        if (output3 != null) addOutput(output3);
        if (output4 != null) addOutput(output4);
    }

    public CentrifugeRecipe(ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2, ItemStack output3,
        ItemStack output4, int tickTime, int euPerTick, boolean useOreDict) {
        this(input1, input2, output1, output2, output3, output4, tickTime, euPerTick);
        this.useOreDictionary = useOreDict;
    }

    @Override
    public String getUserFreindlyName() {
        return "Centrifuge";
    }

    @Override
    public boolean useOreDic() {
        return this.useOreDictionary;
    }
}
