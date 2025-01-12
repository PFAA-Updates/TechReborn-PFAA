package techreborn.compat.nei.recipes;

import java.awt.Rectangle;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import reborncore.common.util.ItemUtils;
import techreborn.api.recipe.IBaseRecipeType;
import techreborn.client.gui.GuiAlloySmelter;
import techreborn.lib.Reference;

public class AlloySmelterRecipeHandler extends GenericRecipeHander implements INeiBaseRecipe {

    @Override
    public void addPositionedStacks(List<PositionedStack> input, List<PositionedStack> outputs,
        IBaseRecipeType recipeType) {
        int offset = 4;
        Object iStack = recipeType.useOreDic() ? ItemUtils.getStackWithAllOre(
            recipeType.getInputs()
                .get(0))
            : recipeType.getInputs()
                .get(0);
        PositionedStack pStack = new PositionedStack(iStack, 47 - offset, 17 - offset, false);
        input.add(pStack);

        if (recipeType.getInputs()
            .size() > 1) {
            Object iStack2 = recipeType.useOreDic() ? ItemUtils.getStackWithAllOre(
                recipeType.getInputs()
                    .get(1))
                : recipeType.getInputs()
                    .get(1);
            PositionedStack pStack2 = new PositionedStack(iStack2, 65 - offset, 17 - offset, false);
            input.add(pStack2);
        }

        PositionedStack pStack3 = new PositionedStack(recipeType.getOutput(0), 116 - offset, 35 - offset, false);
        outputs.add(pStack3);
    }

    @Override
    public String getRecipeName() {
        return Reference.alloySmelteRecipe;
    }

    @Override
    public String getGuiTexture() {
        return "techreborn:textures/gui/electric_alloy_furnace.png";
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiAlloySmelter.class;
    }

    @Override
    public INeiBaseRecipe getNeiBaseRecipe() {
        return this;
    }

    @Override
    public void loadTransferRects() {
        this.transferRects.add(
            new TemplateRecipeHandler.RecipeTransferRect(
                new Rectangle(75, 20, 25, 20),
                getNeiBaseRecipe().getRecipeName(),
                new Object[0]));
    }
}
