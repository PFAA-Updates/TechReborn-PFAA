package techreborn.compat.nei.recipes;

import java.awt.Rectangle;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import reborncore.common.util.ItemUtils;
import techreborn.api.recipe.IBaseRecipeType;
import techreborn.client.gui.GuiVacuumFreezer;
import techreborn.lib.Reference;

public class VacuumFreezerRecipeHandler extends GenericRecipeHander implements INeiBaseRecipe {

    @Override
    public void addPositionedStacks(List<PositionedStack> input, List<PositionedStack> outputs,
        IBaseRecipeType recipeType) {
        int offset = 4;
        Object iStack = recipeType.useOreDic() ? ItemUtils.getStackWithAllOre(
            recipeType.getInputs()
                .get(0))
            : recipeType.getInputs()
                .get(0);
        PositionedStack pStack = new PositionedStack(iStack, 56 - offset, 34 - offset, false);
        input.add(pStack);

        PositionedStack pStack3 = new PositionedStack(recipeType.getOutput(0), 116 - offset, 35 - offset, false);
        outputs.add(pStack3);
    }

    @Override
    public String getRecipeName() {
        return Reference.vacuumFreezerRecipe;
    }

    @Override
    public String getGuiTexture() {
        return "techreborn:textures/gui/vacuum_freezer.png";
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiVacuumFreezer.class;
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
