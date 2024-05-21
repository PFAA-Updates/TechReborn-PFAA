package techreborn.compat.nei.recipes;

import java.awt.Rectangle;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import reborncore.common.util.ItemUtils;
import techreborn.api.recipe.IBaseRecipeType;
import techreborn.client.gui.GuiAssemblingMachine;
import techreborn.lib.Reference;

public class AssemblingMachineRecipeHandler extends GenericRecipeHander implements INeiBaseRecipe {

    @Override
    public void addPositionedStacks(List<PositionedStack> input, List<PositionedStack> outputs,
        IBaseRecipeType recipeType) {
        int offset = 4;
        if (recipeType.getInputs()
            .size() > 0) {
            Object iStack = recipeType.useOreDic() ? ItemUtils.getStackWithAllOre(
                recipeType.getInputs()
                    .get(0))
                : recipeType.getInputs()
                    .get(0);
            PositionedStack pStack = new PositionedStack(iStack, 47 - offset, 17 - offset, false);
            input.add(pStack);
        }
        if (recipeType.getInputs()
            .size() > 1) {
            Object iStack = recipeType.useOreDic() ? ItemUtils.getStackWithAllOre(
                recipeType.getInputs()
                    .get(1))
                : recipeType.getInputs()
                    .get(1);
            PositionedStack pStack2 = new PositionedStack(iStack, 65 - offset, 17 - offset, false);
            input.add(pStack2);
        }

        if (recipeType.getOutputsSize() > 0) {
            PositionedStack pStack3 = new PositionedStack(recipeType.getOutput(0), 116 - offset, 35 - offset, false);
            outputs.add(pStack3);
        }

    }

    @Override
    public String getRecipeName() {
        return Reference.assemblingMachineRecipe;
    }

    @Override
    public String getGuiTexture() {
        return "techreborn:textures/gui/assembling_machine.png";
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiAssemblingMachine.class;
    }

    @Override
    public INeiBaseRecipe getNeiBaseRecipe() {
        return this;
    }

    @Override
    public void loadTransferRects() {
        this.transferRects.add(
            new TemplateRecipeHandler.RecipeTransferRect(
                new Rectangle(80, 20, 20, 20),
                getNeiBaseRecipe().getRecipeName(),
                new Object[0]));
    }
}
