package techreborn.api.recipe.machines;

import net.minecraft.item.ItemStack;

import techreborn.api.recipe.BaseRecipe;
import techreborn.lib.Reference;

public class ImplosionCompressorRecipe extends BaseRecipe {

    private boolean useOreDictionary = true;

    public ImplosionCompressorRecipe(ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2,
        int tickTime, int euPerTick) {
        super(Reference.implosionCompressorRecipe, tickTime, euPerTick);
        if (input1 != null) inputs.add(input1);
        if (input2 != null) inputs.add(input2);
        if (output1 != null) addOutput(output1);
        if (output2 != null) addOutput(output2);
    }

    public ImplosionCompressorRecipe(ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2,
        int tickTime, int euPerTick, boolean useOreDict) {
        this(input1, input2, output1, output2, tickTime, euPerTick);
        this.useOreDictionary = useOreDict;
    }

    @Override
    public String getUserFreindlyName() {
        return "Implosion Compressor";
    }

    @Override
    public boolean useOreDic() {
        return this.useOreDictionary;
    }
}
