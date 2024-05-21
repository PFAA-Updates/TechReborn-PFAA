package techreborn.compat.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import cofh.thermalexpansion.item.TEItems;
import cofh.thermalexpansion.util.crafting.PulverizerManager;
import cofh.thermalexpansion.util.crafting.SawmillManager;
import cofh.thermalexpansion.util.crafting.SmelterManager;
import cofh.thermalexpansion.util.crafting.TransposerManager;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import reborncore.common.util.OreUtil;
import techreborn.api.recipe.RecipeHandler;
import techreborn.api.recipe.machines.CentrifugeRecipe;
import techreborn.blocks.BlockOre;
import techreborn.compat.ICompatModule;
import techreborn.items.ItemCells;
import techreborn.items.ItemDusts;
import techreborn.items.ItemDustsSmall;
import techreborn.items.ItemGems;
import techreborn.items.ItemIngots;
import techreborn.items.ItemPlates;

public class RecipesThermalExpansion implements ICompatModule {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        addPulverizerRecipes();
        addInductionSemlterRecipes();
        addCentrifugeRecipes();
        addFluidTransposerRecipes();
        addSawMillRecipes();
        // TODO remove basic machine frame recipe
        // TODO replace iron in recipe to steel
        // TODO add industrial blast furnace recipes for Enderium and Fluxed Electrum
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {}

    @Override
    public void serverStarting(FMLServerStartingEvent event) {

    }

    static void addPulverizerRecipes() {
        PulverizerManager.addRecipe(4000, new ItemStack(Items.wheat), ItemDusts.getDustByName("flour"));
        PulverizerManager.addRecipe(4000, new ItemStack(Items.ender_pearl), ItemDusts.getDustByName("enderPearl"));
        PulverizerManager.addRecipe(4000, new ItemStack(Items.ender_eye), ItemDusts.getDustByName("enderEye", 2));
        PulverizerManager.addRecipe(4000, new ItemStack(Items.emerald), ItemDusts.getDustByName("emerald"));
        PulverizerManager.addRecipe(4000, new ItemStack(Items.diamond), ItemDusts.getDustByName("diamond"));
        PulverizerManager.addRecipe(4000, ItemGems.getGemByName("ruby"), ItemDusts.getDustByName("ruby"));
        PulverizerManager.addRecipe(4000, ItemGems.getGemByName("sapphire"), ItemDusts.getDustByName("sapphire"));
        PulverizerManager.addRecipe(4000, ItemGems.getGemByName("peridot"), ItemDusts.getDustByName("peridot"));
        PulverizerManager.addRecipe(4000, ItemGems.getGemByName("olivine"), ItemDusts.getDustByName("olivine"));
        PulverizerManager.addRecipe(4000, ItemGems.getGemByName("redGarnet"), ItemDusts.getDustByName("redGarnet"));
        PulverizerManager
            .addRecipe(4000, ItemGems.getGemByName("yellowGarnet"), ItemDusts.getDustByName("yellowGarnet"));

        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("aluminium"), ItemDusts.getDustByName("aluminium"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("titanium"), ItemDusts.getDustByName("titanium"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("chromium"), ItemDusts.getDustByName("chromium"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("electrum"), ItemDusts.getDustByName("electrum"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("tungsten"), ItemDusts.getDustByName("tungsten"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("lead"), ItemDusts.getDustByName("lead"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("zinc"), ItemDusts.getDustByName("zinc"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("brass"), ItemDusts.getDustByName("brass"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("steel"), ItemDusts.getDustByName("steel"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("platinum"), ItemDusts.getDustByName("platinum"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("nickel"), ItemDusts.getDustByName("nickel"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("invar"), ItemDusts.getDustByName("invar"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("osmium"), ItemDusts.getDustByName("osmium"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("iron"), ItemDusts.getDustByName("iron"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("gold"), ItemDusts.getDustByName("gold"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("copper"), ItemDusts.getDustByName("copper"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("tin"), ItemDusts.getDustByName("tin"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("bronze"), ItemDusts.getDustByName("bronze"));
        PulverizerManager.addRecipe(2400, ItemPlates.getPlateByName("silver"), ItemDusts.getDustByName("silver"));

        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("aluminium"), ItemDusts.getDustByName("aluminium"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("titanium"), ItemDusts.getDustByName("titanium"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("chromium"), ItemDusts.getDustByName("chromium"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("electrum"), ItemDusts.getDustByName("electrum"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("tungsten"), ItemDusts.getDustByName("tungsten"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("lead"), ItemDusts.getDustByName("lead"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("zinc"), ItemDusts.getDustByName("zinc"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("brass"), ItemDusts.getDustByName("brass"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("steel"), ItemDusts.getDustByName("steel"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("platinum"), ItemDusts.getDustByName("platinum"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("nickel"), ItemDusts.getDustByName("nickel"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("invar"), ItemDusts.getDustByName("invar"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("osmium"), ItemDusts.getDustByName("osmium"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("thorium"), ItemDusts.getDustByName("thorium"));
        PulverizerManager.addRecipe(2400, ItemIngots.getIngotByName("plutonium"), ItemDusts.getDustByName("plutonium"));

        PulverizerManager.addRecipe(
            4000,
            BlockOre.getOreByName("pyrite"),
            ItemDusts.getDustByName("pyrite", 5),
            ItemDusts.getDustByName("iron"),
            10);
        PulverizerManager.addRecipe(
            4000,
            BlockOre.getOreByName("sodalite"),
            ItemDusts.getDustByName("sodalite", 12),
            ItemDusts.getDustByName("aluminium"),
            10);
        PulverizerManager.addRecipe(
            4000,
            BlockOre.getOreByName("galena"),
            ItemDusts.getDustByName("galena", 2),
            ItemDusts.getDustByName("sulfur"),
            50);
        PulverizerManager.addRecipe(
            4000,
            BlockOre.getOreByName("cinnabar"),
            ItemDusts.getDustByName("cinnabar", 3),
            new ItemStack(Items.redstone),
            10);
        PulverizerManager.addRecipe(
            4000,
            BlockOre.getOreByName("tungsten"),
            ItemDusts.getDustByName("tungsten", 2),
            ItemDusts.getDustByName("manganese"),
            10);
        PulverizerManager.addRecipe(
            4000,
            BlockOre.getOreByName("sphalerite"),
            ItemDusts.getDustByName("sphalerite", 4),
            ItemDusts.getDustByName("zinc"),
            10);
        PulverizerManager.addRecipe(
            4000,
            BlockOre.getOreByName("bauxite"),
            ItemDusts.getDustByName("bauxite", 4),
            ItemDusts.getDustByName("aluminium"),
            10);
        PulverizerManager.addRecipe(
            4000,
            BlockOre.getOreByName("ruby"),
            ItemDusts.getDustByName("ruby", 2),
            ItemDusts.getDustByName("redGarnet"),
            10);
        PulverizerManager.addRecipe(
            4000,
            BlockOre.getOreByName("sapphire"),
            ItemDusts.getDustByName("sapphire", 2),
            ItemDusts.getDustByName("peridot"),
            10);
        PulverizerManager.addRecipe(
            4000,
            BlockOre.getOreByName("olivine"),
            ItemDusts.getDustByName("olivine", 2),
            ItemDusts.getDustByName("emerald"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreUranium"),
            ItemDusts.getDustByName("uranium", 2),
            ItemDusts.getDustByName("plutonium"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreSilver"),
            ItemDusts.getDustByName("silver", 2),
            ItemDusts.getDustByName("lead"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreLead"),
            ItemDusts.getDustByName("lead", 2),
            ItemDusts.getDustByName("silver"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("orePeridot"),
            ItemDusts.getDustByName("peridot", 2),
            ItemDusts.getDustByName("sapphire"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreElectrotine"),
            OreUtil.getStackFromName("dustElectrotine", 10),
            ItemDusts.getDustByName("diamond"),
            10,
            true);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreApatite"),
            OreUtil.getStackFromName("gemApatite", 8),
            ItemDusts.getDustByName("tricalciumPhosphate"),
            10);

        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreNetherLapis"),
            new ItemStack(Items.dye, 12, 4),
            ItemDusts.getDustByName("lazurite"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreNetherUranium"),
            ItemDusts.getDustByName("uranium", 4),
            ItemDusts.getDustByName("plutonium"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreNetherSilver"),
            ItemDusts.getDustByName("silver", 3),
            ItemDusts.getDustByName("lead"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreNetherLead"),
            ItemDusts.getDustByName("lead", 4),
            ItemDusts.getDustByName("silver"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreNetherIron"),
            ItemDusts.getDustByName("iron", 3),
            ItemDusts.getDustByName("nickel"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreNetherRuby"),
            ItemDusts.getDustByName("ruby", 3),
            ItemDusts.getDustByName("redGarnet"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreNetherPeridot"),
            ItemDusts.getDustByName("peridot", 3),
            ItemDusts.getDustByName("sapphire"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreNetherSapphire"),
            ItemDusts.getDustByName("sapphire", 3),
            ItemDusts.getDustByName("peridot"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreNetherEmerald"),
            ItemDusts.getDustByName("emerald", 3),
            ItemDusts.getDustByName("olivine"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreNetherDiamond"),
            ItemDusts.getDustByName("diamond", 3),
            ItemDusts.getDustByName("coal"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreNetherElectrotine"),
            OreUtil.getStackFromName("dustElectrotine", 15),
            ItemDusts.getDustByName("diamond"),
            10);
        PulverizerManager.addRecipe(
            4000,
            OreUtil.getStackFromName("oreNetherCoal"),
            ItemDusts.getDustByName("coal", 3),
            ItemDusts.getDustByName("thorium"),
            10);

        PulverizerManager.addRecipe(
            4000,
            new ItemStack(Blocks.end_stone),
            ItemDusts.getDustByName("endstone"),
            ItemDusts.getDustByName("endstone"),
            10);
        PulverizerManager.addRecipe(
            4000,
            new ItemStack(Blocks.enchanting_table),
            ItemDusts.getDustByName("diamond", 2),
            ItemDusts.getDustByName("obsidian"),
            95);
        PulverizerManager.addRecipe(4000, new ItemStack(Blocks.obsidian), ItemDusts.getDustByName("obsidian"));
        PulverizerManager.addRecipe(4000, new ItemStack(Blocks.clay), ItemDusts.getDustByName("clay", 2));
        PulverizerManager.addRecipe(
            4000,
            new ItemStack(Items.flint),
            ItemDustsSmall.getSmallDustByName("flint", 2),
            ItemDustsSmall.getSmallDustByName("flint"),
            10);
        PulverizerManager.addRecipe(
            4000,
            new ItemStack(Blocks.lever),
            new ItemStack(Blocks.sand),
            ItemDustsSmall.getSmallDustByName("sawDust", 2),
            95);
        PulverizerManager
            .addRecipe(4000, new ItemStack(Blocks.iron_bars, 2), ItemDustsSmall.getSmallDustByName("iron", 3));
        if (Loader.isModLoaded("IC2")) {
            PulverizerManager
                .addRecipe(4000, ic2.api.item.IC2Items.getItem("Uran238"), ItemDusts.getDustByName("uranium"));
            PulverizerManager.addRecipe(
                4000,
                ic2.api.item.IC2Items.getItem("basaltBlock"),
                ItemDusts.getDustByName("basalt"),
                ItemDusts.getDustByName("basalt"),
                10);
            PulverizerManager.addRecipe(
                4000,
                OreUtil.getStackFromName("gemApatite"),
                new ItemStack(
                    ic2.api.item.IC2Items.getItem("fertilizer")
                        .getItem(),
                    4),
                ItemDusts.getDustByName("tricalciumPhosphate"),
                50);
            PulverizerManager.addRecipe(
                4000,
                new ItemStack(
                    ic2.api.item.IC2Items.getItem("ironFence")
                        .getItem(),
                    2),
                ItemDustsSmall.getSmallDustByName("iron", 3));
            PulverizerManager
                .addRecipe(4000, ic2.api.item.IC2Items.getItem("tinCan"), ItemDustsSmall.getSmallDustByName("tin", 5));
            PulverizerManager
                .addRecipe(4000, ic2.api.item.IC2Items.getItem("cell"), ItemDustsSmall.getSmallDustByName("tin", 4));

            PulverizerManager.addRecipe(
                4000,
                BlockOre.getOreByName("iridium"),
                new ItemStack(
                    ic2.api.item.IC2Items.getItem("iridiumOre")
                        .getItem(),
                    2),
                ItemDusts.getDustByName("platinum"),
                10);
            PulverizerManager.addRecipe(
                4000,
                BlockOre.getOreByName("sheldonite"),
                ItemDusts.getDustByName("platinum", 2),
                ic2.api.item.IC2Items.getItem("iridiumOre"),
                10,
                true);
            PulverizerManager.addRecipe(
                4000,
                BlockOre.getOreByName("galena"),
                ItemDusts.getDustByName("galena", 2),
                ic2.api.item.IC2Items.getItem("sulfurDust"),
                50,
                true);
        }
        if (Loader.isModLoaded("ExtrabiomesXL")) {
            Item redRock = GameRegistry.findItem("ExtrabiomesXL", "terrain_blocks1");
            PulverizerManager.addRecipe(
                4000,
                new ItemStack(redRock, 1, 0),
                ItemDusts.getDustByName("redrock"),
                ItemDusts.getDustByName("redrock"),
                10);
            PulverizerManager.addRecipe(
                4000,
                new ItemStack(redRock, 1, 1),
                ItemDusts.getDustByName("redrock"),
                ItemDusts.getDustByName("redrock"),
                10);
            PulverizerManager.addRecipe(
                4000,
                new ItemStack(redRock, 1, 2),
                ItemDusts.getDustByName("redrock"),
                ItemDusts.getDustByName("redrock"),
                10);
        }
        if (Loader.isModLoaded("ProjRed|Exploration")) {
            Item stone = GameRegistry.findItem("ProjRed|Exploration", "projectred.exploration.stone");
            PulverizerManager.addRecipe(
                4000,
                new ItemStack(stone, 1, 0),
                ItemDusts.getDustByName("marble"),
                ItemDusts.getDustByName("marble"),
                10);
            PulverizerManager.addRecipe(
                4000,
                new ItemStack(stone, 1, 1),
                ItemDusts.getDustByName("marble"),
                ItemDusts.getDustByName("marble"),
                10);
            PulverizerManager.addRecipe(
                4000,
                new ItemStack(stone, 1, 2),
                ItemDusts.getDustByName("basalt"),
                ItemDusts.getDustByName("basalt"),
                10);
            PulverizerManager.addRecipe(
                4000,
                new ItemStack(stone, 1, 3),
                ItemDusts.getDustByName("basalt"),
                ItemDusts.getDustByName("basalt"),
                10);
            PulverizerManager.addRecipe(
                4000,
                new ItemStack(stone, 1, 4),
                ItemDusts.getDustByName("basalt"),
                ItemDusts.getDustByName("basalt"),
                10);
        }
    }

    static void addCentrifugeRecipes() {
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("darkAshes", 2),
                null,
                ItemDusts.getDustByName("ashes", 2),
                TEItems.slag,
                null,
                null,
                240,
                5,
                false));
    }

    static void addInductionSemlterRecipes() {
        SmelterManager.addRecipe(
            3200,
            BlockOre.getOreByName("tungsten"),
            new ItemStack(Blocks.sand),
            ItemDusts.getDustByName("tungsten", 2),
            TEItems.slagRich,
            5,
            true);
        SmelterManager.addRecipe(
            4000,
            BlockOre.getOreByName("tungsten"),
            TEItems.slagRich,
            ItemDusts.getDustByName("tungsten", 3),
            TEItems.slag,
            75,
            true);
    }

    static void addFluidTransposerRecipes() {
        if (Loader.isModLoaded("IC2")) {
            TransposerManager.addFillRecipe(
                1600,
                ic2.api.item.IC2Items.getItem("cell"),
                ItemCells.getCellByName("diesel"),
                FluidRegistry.getFluidStack("fuel", 1000),
                true);
            TransposerManager.addFillRecipe(
                1600,
                ic2.api.item.IC2Items.getItem("cell"),
                ItemCells.getCellByName("diesel"),
                FluidRegistry.getFluidStack("diesel", 1000),
                true);
            TransposerManager.addFillRecipe(
                1600,
                ic2.api.item.IC2Items.getItem("cell"),
                ItemCells.getCellByName("ethanol"),
                FluidRegistry.getFluidStack("biofuel", 1000),
                true);
            TransposerManager.addFillRecipe(
                1600,
                ic2.api.item.IC2Items.getItem("cell"),
                ItemCells.getCellByName("ethanol"),
                FluidRegistry.getFluidStack("bioethanol", 1000),
                true);
            TransposerManager.addFillRecipe(
                1600,
                ic2.api.item.IC2Items.getItem("cell"),
                ItemCells.getCellByName("ethanol"),
                FluidRegistry.getFluidStack("ethanol", 1000),
                true);
        }
    }

    static void addSawMillRecipes() {
        SawmillManager.addRecipe(800, new ItemStack(Items.stick), ItemDustsSmall.getSmallDustByName("sawDust", 2));
        SawmillManager.addRecipe(
            800,
            new ItemStack(Blocks.redstone_torch),
            ItemDustsSmall.getSmallDustByName("sawDust", 2),
            new ItemStack(Items.redstone),
            95);
        SawmillManager
            .addRecipe(800, new ItemStack(Blocks.wooden_slab), ItemDustsSmall.getSmallDustByName("sawDust", 2));
        if (Loader.isModLoaded("IC2")) {
            SawmillManager.addRecipe(
                1200,
                OreUtil.getStackFromName("woodrubber"),
                new ItemStack(Blocks.planks, 4, 3),
                ic2.api.item.IC2Items.getItem("resin"),
                33);
        }
    }

}
