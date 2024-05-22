package techreborn.compat.recipes;

import static net.minecraftforge.fluids.FluidRegistry.WATER;

import java.lang.reflect.Field;
import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.apache.commons.lang3.tuple.Pair;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IC2Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.Recipes;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import reborncore.common.util.CraftingHelper;
import reborncore.common.util.OreUtil;
import reborncore.common.util.RecipeRemover;
import techreborn.Core;
import techreborn.api.RollingMachineRecipe;
import techreborn.api.reactor.FusionReactorRecipe;
import techreborn.api.reactor.FusionReactorRecipeHelper;
import techreborn.api.recipe.RecipeHandler;
import techreborn.api.recipe.machines.AlloySmelterRecipe;
import techreborn.api.recipe.machines.AssemblingMachineRecipe;
import techreborn.api.recipe.machines.BlastFurnaceRecipe;
import techreborn.api.recipe.machines.CentrifugeRecipe;
import techreborn.api.recipe.machines.ChemicalReactorRecipe;
import techreborn.api.recipe.machines.GrinderRecipe;
import techreborn.api.recipe.machines.ImplosionCompressorRecipe;
import techreborn.api.recipe.machines.IndustrialElectrolyzerRecipe;
import techreborn.api.recipe.machines.IndustrialSawmillRecipe;
import techreborn.api.recipe.machines.VacuumFreezerRecipe;
import techreborn.blocks.BlockMachineFrame;
import techreborn.blocks.BlockOre;
import techreborn.blocks.BlockStorage2;
import techreborn.compat.CompatManager;
import techreborn.compat.ICompatModule;
import techreborn.config.ConfigTechReborn;
import techreborn.init.ModBlocks;
import techreborn.init.ModFluids;
import techreborn.init.ModItems;
import techreborn.items.ItemCells;
import techreborn.items.ItemCrushedOre;
import techreborn.items.ItemDusts;
import techreborn.items.ItemDustsSmall;
import techreborn.items.ItemGems;
import techreborn.items.ItemIngots;
import techreborn.items.ItemNuggets;
import techreborn.items.ItemParts;
import techreborn.items.ItemPlates;
import techreborn.items.ItemPurifiedCrushedOre;

public class RecipesIC2 implements ICompatModule {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        addIC2OreDict();
        removeIc2Recipes();
        addShappedIc2Recipes();
        addTRMaceratorRecipes();
        addTROreWashingRecipes();
        addTRThermalCentrifugeRecipes();
        addMetalFormerRecipes();
        addTRRecipes();
        addCannerRecipes();

        RecipeHandler.addRecipe(
            new AlloySmelterRecipe(
                ItemIngots.getIngotByName("copper", 3),
                IC2Items.getItem("tinIngot"),
                new ItemStack(
                    IC2Items.getItem("bronzeIngot")
                        .getItem(),
                    2,
                    2),
                100,
                16));
        FusionReactorRecipeHelper.registerRecipe(
            new FusionReactorRecipe(
                ItemCells.getCellByName("tungsten"),
                ItemCells.getCellByName("lithium"),
                IC2Items.getItem("iridiumOre"),
                100000000,
                -32768,
                512));
        RecipeHandler.addRecipe(
            new VacuumFreezerRecipe(
                any(IC2Items.getItem("reactorCoolantSimple")),
                new ItemStack(
                    IC2Items.getItem("reactorCoolantSimple")
                        .getItem(),
                    1,
                    1),
                40,
                128));
        RecipeHandler.addRecipe(
            new VacuumFreezerRecipe(
                any(IC2Items.getItem("reactorCoolantTriple")),
                new ItemStack(
                    IC2Items.getItem("reactorCoolantTriple")
                        .getItem(),
                    1,
                    1),
                120,
                128));
        RecipeHandler.addRecipe(
            new VacuumFreezerRecipe(
                any(IC2Items.getItem("reactorCoolantSix")),
                new ItemStack(
                    IC2Items.getItem("reactorCoolantSix")
                        .getItem(),
                    1,
                    1),
                350,
                128));
        // TODO Distillation Tower: 16 Oil + 17 Empty -> 16 Diesel + 16 Sulfuric Acid + Glyceryl @ 128EU/t * 16000t
        // TODO Distillation Tower: 16 Biomass -> 8 Bio Diesel + 8 Empty @ 32EU/t * 400t
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        addScrapboxDrops();
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {

    }

    static void addIC2OreDict() {
        OreDictionary.registerOre("machineBasic", IC2Items.getItem("machine"));
        OreDictionary.registerOre("machineAdvanced", IC2Items.getItem("advancedMachine"));
        OreDictionary.registerOre("battery10k", IC2Items.getItem("reBattery"));
        OreDictionary.registerOre("glassReinforced", IC2Items.getItem("reinforcedGlass"));
    }

    static ItemStack any(ItemStack stack) {
        Items.apple.setDamage(stack.copy(), OreDictionary.WILDCARD_VALUE);
        return stack;
    }

    static void addTRRecipes() {
        // General
        CraftingHelper.addShapelessOreRecipe(
            BlockStorage2.getStorageBlockByName("tungstensteel", 1),
            IC2Items.getItem("reinforcedStone"),
            "plateTungstensteel");
        CraftingHelper.addShapelessOreRecipe(
            BlockStorage2.getStorageBlockByName("iridium_reinforced_stone", 1),
            IC2Items.getItem("reinforcedStone"),
            "ingotIridium");
        CraftingHelper.addShapelessOreRecipe(
            ItemDusts.getDustByName("saltpeter", 10),
            ItemCells.getCellByName("potassium"),
            ItemCells.getCellByName("potassium"),
            ItemCells.getCellByName("nitrogen"),
            ItemCells.getCellByName("nitrogen"),
            IC2Items.getItem("airCell"),
            IC2Items.getItem("airCell"),
            IC2Items.getItem("airCell"));
        CraftingHelper.addShapelessOreRecipe(
            new ItemStack(
                IC2Items.getItem("fertilizer")
                    .getItem(),
                4),
            IC2Items.getItem("fertilizer"),
            "dustTricalciumPhosphate");
        CraftingHelper.addShapelessOreRecipe(
            new ItemStack(
                IC2Items.getItem("fertilizer")
                    .getItem(),
                3),
            IC2Items.getItem("fertilizer"),
            "dustSulfur",
            ItemCells.getCellByName("calcium"));
        CraftingHelper.addShapelessOreRecipe(
            new ItemStack(
                IC2Items.getItem("fertilizer")
                    .getItem(),
                3),
            IC2Items.getItem("fertilizer"),
            ItemCells.getCellByName("sulfur"),
            ItemCells.getCellByName("calcium"));
        CraftingHelper.addShapelessOreRecipe(
            new ItemStack(
                IC2Items.getItem("fertilizer")
                    .getItem(),
                2),
            IC2Items.getItem("fertilizer"),
            new ItemStack(Items.dye, 1, 15));
        CraftingHelper.addShapelessOreRecipe(
            new ItemStack(
                IC2Items.getItem("fertilizer")
                    .getItem(),
                2),
            IC2Items.getItem("fertilizer"),
            "dustAsh",
            "dustAsh",
            "dustAsh");
        CraftingHelper.addShapelessOreRecipe(
            new ItemStack(
                IC2Items.getItem("fertilizer")
                    .getItem(),
                2),
            IC2Items.getItem("fertilizer"),
            "dustDarkAsh");
        CraftingHelper.addShapelessOreRecipe(
            new ItemStack(
                IC2Items.getItem("fertilizer")
                    .getItem(),
                2),
            IC2Items.getItem("scrap"),
            IC2Items.getItem("scrap"),
            IC2Items.getItem("fertilizer"));
        CraftingHelper.addShapelessOreRecipe(
            IC2Items.getItem("carbonFiber"),
            ItemCells.getCellByName("carbon"),
            ItemCells.getCellByName("carbon"),
            ItemCells.getCellByName("carbon"),
            ItemCells.getCellByName("carbon"),
            ItemCells.getCellByName("carbon"),
            ItemCells.getCellByName("carbon"),
            ItemCells.getCellByName("carbon"),
            ItemCells.getCellByName("carbon"),
            ItemCells.getCellByName("carbon"));

        ItemStack coinCu = ItemParts.getPartByName("copperCredit");
        ItemStack coinFe = IC2Items.getItem("coin")
            .copy();
        ItemStack coinAg = ItemParts.getPartByName("silverCredit");
        ItemStack coinAu = ItemParts.getPartByName("goldCredit");
        ItemStack coinD = ItemParts.getPartByName("diamondCredit");
        CraftingHelper.addShapelessOreRecipe(
            coinFe.copy(),
            coinCu.copy(),
            coinCu.copy(),
            coinCu.copy(),
            coinCu.copy(),
            coinCu.copy(),
            coinCu.copy(),
            coinCu.copy(),
            coinCu.copy());
        CraftingHelper.addShapelessOreRecipe(
            coinAg.copy(),
            coinFe.copy(),
            coinFe.copy(),
            coinFe.copy(),
            coinFe.copy(),
            coinFe.copy(),
            coinFe.copy(),
            coinFe.copy(),
            coinFe.copy());
        CraftingHelper.addShapelessOreRecipe(
            coinAu.copy(),
            coinAg.copy(),
            coinAg.copy(),
            coinAg.copy(),
            coinAg.copy(),
            coinAg.copy(),
            coinAg.copy(),
            coinAg.copy(),
            coinAg.copy());
        CraftingHelper.addShapelessOreRecipe(
            coinD.copy(),
            coinAu.copy(),
            coinAu.copy(),
            coinAu.copy(),
            coinAu.copy(),
            coinAu.copy(),
            coinAu.copy(),
            coinAu.copy(),
            coinAu.copy());
        coinCu.stackSize = 8;
        CraftingHelper.addShapelessOreRecipe(coinCu, coinFe.copy());
        coinFe.stackSize = 8;
        CraftingHelper.addShapelessOreRecipe(coinFe, coinAg.copy());
        coinAg.stackSize = 8;
        CraftingHelper.addShapelessOreRecipe(coinAg, coinAu.copy());
        coinAu.stackSize = 8;
        CraftingHelper.addShapelessOreRecipe(coinAu, coinD);

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("machineParts", 16),
            "CSC",
            "SCS",
            "CSC",
            'S',
            "ingotSteel",
            'C',
            "circuitBasic");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.cloakingDevice),
            "CIC",
            "IOI",
            "CIC",
            'C',
            "ingotChromium",
            'I',
            IC2Items.getItem("iridiumPlate"),
            'O',
            "battery100M");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.omniTool),
            "LS ",
            "SP ",
            "  P",
            'L',
            new ItemStack(ModItems.lapotronicOrb, 1, OreDictionary.WILDCARD_VALUE),
            'S',
            ItemParts.getPartByName("superconductor"),
            'P',
            IC2Items.getItem("iridiumPlate"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.coolantNaK60k),
            "TST",
            "PCP",
            "TST",
            'T',
            "ingotTin",
            'S',
            ItemCells.getCellByName("sodium"),
            'P',
            ItemCells.getCellByName("potassium"),
            'C',
            new ItemStack(
                IC2Items.getItem("reactorCoolantSimple")
                    .getItem(),
                1,
                1));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.neutronReflector),
            "RRR",
            "RPR",
            "RRR",
            'R',
            new ItemStack(
                IC2Items.getItem("reactorReflectorThick")
                    .getItem(),
                1,
                1),
            'P',
            IC2Items.getItem("iridiumPlate"));

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("energyFlowCircuit", 4),
            "ATA",
            "LIL",
            "ATA",
            'A',
            "circuitAdvanced",
            'T',
            "plateTungsten",
            'L',
            "battery10M",
            'I',
            IC2Items.getItem("iridiumPlate"));

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("superconductor", 4),
            "CCC",
            "TIT",
            "EEE",
            'E',
            "circuitMaster",
            'C',
            ModItems.coolantHe60k,
            'T',
            "plateTungsten",
            'I',
            IC2Items.getItem("iridiumPlate"));

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("superconductor", 4),
            "CCC",
            "TIT",
            "EEE",
            'E',
            "circuitMaster",
            'C',
            ModItems.coolantNaK60k,
            'T',
            "plateTungsten",
            'I',
            IC2Items.getItem("iridiumPlate"));

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("superconductor", 4),
            "CCC",
            "TIT",
            "EEE",
            'E',
            "circuitMaster",
            'C',
            IC2Items.getItem("reactorCoolantSix"),
            'T',
            "plateTungsten",
            'I',
            IC2Items.getItem("iridiumPlate"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.lapotronicOrb),
            "LLL",
            "LPL",
            "LLL",
            'L',
            "battery10M",
            'P',
            IC2Items.getItem("iridiumPlate"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.industrialSawmill),
            "PAP",
            "SSS",
            "ACA",
            'P',
            IC2Items.getItem("pump"),
            'A',
            "circuitAdvanced",
            'S',
            ItemParts.getPartByName("diamondSawBlade"),
            'C',
            "machineAdvanced");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.ComputerCube),
            "DME",
            "MAM",
            "EMD",
            'E',
            "circuitMaster",
            'D',
            ItemParts.getPartByName("dataOrb"),
            'M',
            ItemParts.getPartByName("computerMonitor"),
            'A',
            "machineAdvanced");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.Idsu),
            "PAP",
            "ACA",
            "PAP",
            'P',
            IC2Items.getItem("iridiumPlate"),
            'C',
            "chestEnder",
            'A',
            new ItemStack(ModBlocks.Aesu));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.MatterFabricator),
            "ETE",
            "AOA",
            "ETE",
            'E',
            "circuitMaster",
            'T',
            IC2Items.getItem("teleporter"),
            'A',
            "machineElite",
            'O',
            "battery10M");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.thermalGenerator),
            "III",
            "IHI",
            "CGC",
            'I',
            "plateInvar",
            'H',
            "glassReinforced",
            'C',
            "circuitBasic",
            'G',
            new ItemStack(ModBlocks.heatGenerator));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.thermalGenerator),
            "AAA",
            "AHA",
            "CGC",
            'A',
            "plateAluminium",
            'H',
            "glassReinforced",
            'C',
            "circuitBasic",
            'G',
            new ItemStack(ModBlocks.heatGenerator));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.heatGenerator),
            "III",
            "IHI",
            "CGC",
            'I',
            "plateIron",
            'H',
            new ItemStack(Blocks.iron_bars),
            'C',
            "circuitBasic",
            'G',
            IC2Items.getItem("geothermalGenerator"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.Gasturbine),
            "IAI",
            "WGW",
            "IAI",
            'I',
            "plateInvar",
            'A',
            "circuitAdvanced",
            'W',
            IC2Items.getItem("windMill"),
            'G',
            "glassReinforced");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.Gasturbine),
            "IAI",
            "WGW",
            "IAI",
            'I',
            "plateAluminium",
            'A',
            "circuitAdvanced",
            'W',
            IC2Items.getItem("windMill"),
            'G',
            "glassReinforced");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.Semifluidgenerator),
            "III",
            "IHI",
            "CGC",
            'I',
            "plateSteel",
            'H',
            "glassReinforced",
            'C',
            "circuitBasic",
            'G',
            IC2Items.getItem("generator"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.Semifluidgenerator),
            "AAA",
            "AHA",
            "CGC",
            'A',
            "plateAluminium",
            'H',
            "glassReinforced",
            'C',
            "circuitBasic",
            'G',
            IC2Items.getItem("generator"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.DieselGenerator),
            "III",
            "I I",
            "CGC",
            'I',
            "plateIron",
            'C',
            "circuitBasic",
            'G',
            IC2Items.getItem("generator"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.DieselGenerator),
            "AAA",
            "A A",
            "CGC",
            'A',
            "plateAluminium",
            'C',
            "circuitBasic",
            'G',
            IC2Items.getItem("generator"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.MagicalAbsorber),
            "CSC",
            "IBI",
            "CAC",
            'C',
            "circuitMaster",
            'S',
            "craftingSuperconductor",
            'B',
            Blocks.beacon,
            'A',
            ModBlocks.Magicenergeyconverter,
            'I',
            IC2Items.getItem("iridiumPlate"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.Magicenergeyconverter),
            "CTC",
            "PBP",
            "CLC",
            'C',
            "circuitAdvanced",
            'P',
            "ingotThaumium",
            'B',
            Blocks.beacon,
            'L',
            "battery10M",
            'T',
            IC2Items.getItem("teleporter"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.Dragoneggenergysiphoner),
            "CTC",
            "ISI",
            "CBC",
            'I',
            IC2Items.getItem("iridiumPlate"),
            'C',
            "circuitMaster",
            'B',
            "battery100M",
            'S',
            ModBlocks.Supercondensator,
            'T',
            IC2Items.getItem("teleporter"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.centrifuge),
            "SCS",
            "BEB",
            "SCS",
            'S',
            "plateSteel",
            'C',
            "circuitAdvanced",
            'B',
            "machineAdvanced",
            'E',
            IC2Items.getItem("extractor"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.centrifuge),
            "SCS",
            "BEB",
            "SCS",
            'S',
            "plateSteel",
            'C',
            "circuitAdvanced",
            'B',
            "machineAdvanced",
            'E',
            IC2Items.getItem("extractor"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.centrifuge),
            "SCS",
            "BEB",
            "SCS",
            'S',
            "plateAluminium",
            'C',
            "circuitAdvanced",
            'B',
            "machineAdvanced",
            'E',
            IC2Items.getItem("extractor"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.IndustrialElectrolyzer),
            "SXS",
            "CEC",
            "SMS",
            'S',
            "plateSteel",
            'C',
            "circuitAdvanced",
            'X',
            IC2Items.getItem("extractor"),
            'E',
            IC2Items.getItem("electrolyzer"),
            'M',
            IC2Items.getItem("magnetizer"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.IndustrialElectrolyzer),
            "SXS",
            "CEC",
            "SMS",
            'S',
            "plateAluminium",
            'C',
            "circuitAdvanced",
            'X',
            IC2Items.getItem("extractor"),
            'E',
            IC2Items.getItem("electrolyzer"),
            'M',
            IC2Items.getItem("magnetizer"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.BlastFurnace),
            "CHC",
            "HBH",
            "FHF",
            'H',
            ItemParts.getPartByName("cupronickelHeatingCoil"),
            'C',
            "circuitBasic",
            'B',
            "machineAdvanced",
            'F',
            IC2Items.getItem("inductionFurnace"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.Grinder),
            "ECP",
            "GGG",
            "CBC",
            'E',
            ModBlocks.IndustrialElectrolyzer,
            'P',
            IC2Items.getItem("pump"),
            'C',
            "circuitAdvanced",
            'B',
            "machineAdvanced",
            'G',
            "craftingGrinder");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.ImplosionCompressor),
            "ABA",
            "CPC",
            "ABA",
            'A',
            IC2Items.getItem("advancedAlloy"),
            'C',
            "circuitBasic",
            'B',
            "machineAdvanced",
            'P',
            IC2Items.getItem("compressor"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.VacuumFreezer),
            "SPS",
            "CGC",
            "SPS",
            'S',
            "plateSteel",
            'C',
            "circuitAdvanced",
            'G',
            "glassReinforced",
            'P',
            IC2Items.getItem("pump"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.VacuumFreezer),
            "SPS",
            "CGC",
            "SPS",
            'S',
            "plateAluminium",
            'C',
            "circuitAdvanced",
            'G',
            "glassReinforced",
            'P',
            IC2Items.getItem("pump"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.Distillationtower),
            "CMC",
            "PBP",
            "EME",
            'E',
            ModBlocks.IndustrialElectrolyzer,
            'M',
            "circuitMaster",
            'B',
            "machineElite",
            'C',
            ModBlocks.centrifuge,
            'P',
            IC2Items.getItem("pump"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.AlloyFurnace),
            "III",
            "F F",
            "III",
            'I',
            "plateIron",
            'F',
            IC2Items.getItem("ironFurnace"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.ChemicalReactor),
            "IMI",
            "CPC",
            "IEI",
            'I',
            "plateInvar",
            'C',
            "circuitAdvanced",
            'M',
            IC2Items.getItem("magnetizer"),
            'P',
            IC2Items.getItem("compressor"),
            'E',
            IC2Items.getItem("extractor"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.ChemicalReactor),
            "AMA",
            "CPC",
            "AEA",
            'A',
            "plateAluminium",
            'C',
            "circuitAdvanced",
            'M',
            IC2Items.getItem("magnetizer"),
            'P',
            IC2Items.getItem("compressor"),
            'E',
            IC2Items.getItem("extractor"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.RollingMachine),
            "PCP",
            "MBM",
            "PCP",
            'P',
            "craftingPiston",
            'C',
            "circuitAdvanced",
            'M',
            IC2Items.getItem("compressor"),
            'B',
            "machineBasic");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.ElectricCraftingTable),
            "PBP",
            "CTC",
            "PMP",
            'P',
            "plateElectrum",
            'B',
            "battery10k",
            'C',
            "circuitAdvanced",
            'T',
            "craftingTableWood",
            'M',
            "machineAdvanced");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.ChunkLoader),
            "SCS",
            "CMC",
            "SCS",
            'S',
            "plateSteel",
            'C',
            "circuitMaster",
            'M',
            ItemParts.getPartByName("advancedCircuitParts"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.Lesu),
            " L ",
            "CBC",
            " M ",
            'L',
            IC2Items.getItem("lvTransformer"),
            'C',
            "circuitAdvanced",
            'M',
            IC2Items.getItem("mvTransformer"),
            'B',
            ModBlocks.LesuStorage);

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.HighAdvancedMachineBlock),
            "CTC",
            "TBT",
            "CTC",
            'C',
            "plateChrome",
            'T',
            "plateTitanium",
            'B',
            "machineAdvanced");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.MachineCasing, 4, 0),
            "III",
            "CBC",
            "III",
            'I',
            "plateSteel",
            'C',
            "circuitBasic",
            'B',
            "machineBasic");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.MachineCasing, 4, 0),
            "III",
            "CBC",
            "III",
            'I',
            "plateAluminium",
            'C',
            "circuitBasic",
            'B',
            "machineBasic");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.MachineCasing, 4, 1),
            "SSS",
            "CBC",
            "SSS",
            'S',
            "plateSteel",
            'C',
            "circuitAdvanced",
            'B',
            "machineAdvanced");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.MachineCasing, 4, 2),
            "HHH",
            "CBC",
            "HHH",
            'H',
            "plateChromium",
            'C',
            "circuitElite",
            'B',
            "machineElite");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.quantumChest),
            "DCD",
            "ATA",
            "DQD",
            'D',
            ItemParts.getPartByName("dataOrb"),
            'C',
            ItemParts.getPartByName("computerMonitor"),
            'A',
            "machineElite",
            'Q',
            ModBlocks.digitalChest,
            'T',
            IC2Items.getItem("teleporter"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.PlasmaGenerator),
            "PPP",
            "PTP",
            "CGC",
            'P',
            "plateTungstensteel",
            'T',
            IC2Items.getItem("hvTransformer"),
            'G',
            IC2Items.getItem("generator"),
            'C',
            "circuitMaster");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.AlloySmelter),
            "ICI",
            "BFB",
            "IMI",
            'I',
            "plateInvar",
            'C',
            ItemParts.getPartByName("cupronickelHeatingCoil"),
            'B',
            "circuitBasic",
            'F',
            IC2Items.getItem("electroFurnace"),
            'M',
            ItemParts.getPartByName("conveyorModule"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.AssemblyMachine),
            "CTC",
            "PMP",
            "CPC",
            'C',
            "circuitBasic",
            'T',
            "craftingPiston",
            'P',
            "plateSteel",
            'M',
            ItemParts.getPartByName("conveyorModule"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.AssemblyMachine),
            "CTC",
            "PMP",
            "CPC",
            'C',
            "circuitBasic",
            'T',
            "craftingPiston",
            'P',
            "plateAluminium",
            'M',
            ItemParts.getPartByName("conveyorModule"));

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("dataControlCircuit", 4),
            "ASA",
            "SIS",
            "ASA",
            'A',
            "circuitAdvanced",
            'S',
            "circuitData",
            'I',
            IC2Items.getItem("iridiumPlate"));

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("conveyorModule"),
            "GGG",
            "PPP",
            "CBC",
            'G',
            "blockGlass",
            'P',
            "plateAluminium",
            'C',
            "circuitBasic",
            'B',
            "battery10k");

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("conveyorModule"),
            "GGG",
            "PPP",
            "CBC",
            'G',
            "blockGlass",
            'P',
            "plateIron",
            'C',
            "circuitBasic",
            'B',
            "battery10k");

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("conveyorModule"),
            "GGG",
            "PPP",
            "CBC",
            'G',
            "blockGlass",
            'P',
            "ingotAluminium",
            'C',
            "circuitBasic",
            'B',
            "battery10k");

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("conveyorModule"),
            "GGG",
            "PPP",
            "CBC",
            'G',
            "blockGlass",
            'P',
            "ingotIron",
            'C',
            "circuitBasic",
            'B',
            "battery10k");

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("diamondGrindingHead", 2),
            "DPD",
            "PGP",
            "DPD",
            'D',
            "dustDiamond",
            'P',
            "plateSteel",
            'G',
            IC2Items.getItem("industrialDiamond"));

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("tungstenGrindingHead", 2),
            "TST",
            "SBS",
            "TST",
            'T',
            "plateTungsten",
            'S',
            "plateSteel",
            'B',
            "blockSteel");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.cellThorium2),
            "TCT",
            'T',
            ModItems.cellThorium1,
            'C',
            "plateCopper");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.cellThorium4),
            " T ",
            "CCC",
            " T ",
            'T',
            ModItems.cellThorium2,
            'C',
            "plateCopper");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.cellPlutonium2),
            "TCT",
            'T',
            ModItems.cellPlutonium1,
            'C',
            "plateCopper");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.cellPlutonium4),
            " T ",
            "CCC",
            " T ",
            'T',
            ModItems.cellPlutonium2,
            'C',
            "plateCopper");

        RollingMachineRecipe.instance.addShapedOreRecipe(
            ItemParts.getPartByName("iridiumAlloyIngot"),
            "IPI",
            "PDP",
            "IPI",
            'I',
            "ingotIridium",
            'P',
            IC2Items.getItem("advancedAlloy"),
            'D',
            "dustDiamond");

        RollingMachineRecipe.instance.addShapedOreRecipe(
            ItemPlates.getPlateByName("magnalium", 3),
            "PPP",
            "DDD",
            "PPP",
            'P',
            "plateAluminium",
            'D',
            "dustMagnesium");

        RollingMachineRecipe.instance.addShapedOreRecipe(
            ItemParts.getPartByName("cupronickelHeatingCoil", 3),
            "NCN",
            "C C",
            "NCN",
            'N',
            "ingotNickel",
            'C',
            "ingotCopper");

        RollingMachineRecipe.instance.addShapedOreRecipe(
            ItemParts.getPartByName("nichromeHeatingCoil"),
            " N ",
            "NCN",
            " N ",
            'N',
            "ingotNickel",
            'C',
            "ingotChromium");

        RollingMachineRecipe.instance.addShapelessOreRecipe(
            ItemParts.getPartByName("kanthalHeatingCoil", 3),
            "ingotSteel",
            "ingotSteel",
            "ingotSteel",
            "ingotChromium",
            "ingotChromium",
            "ingotChromium",
            "ingotAluminium",
            "ingotAluminium",
            "ingotAluminium");

        if (Loader.isModLoaded("Railcraft")) {
            RailcraftCraftingManager.rollingMachine.getRecipeList()
                .add(
                    new ShapedOreRecipe(
                        ItemParts.getPartByName("iridiumAlloyIngot"),
                        "IPI",
                        "PDP",
                        "IPI",
                        'I',
                        "ingotIridium",
                        'P',
                        IC2Items.getItem("advancedAlloy"),
                        'D',
                        "dustDiamond"));

            mods.railcraft.api.crafting.RailcraftCraftingManager.rollingMachine.getRecipeList()
                .add(
                    new ShapedOreRecipe(
                        ItemPlates.getPlateByName("magnalium", 3),
                        "PPP",
                        "DDD",
                        "PPP",
                        'P',
                        "plateAluminium",
                        'D',
                        "dustMagnesium"));

            mods.railcraft.api.crafting.RailcraftCraftingManager.rollingMachine.getRecipeList()
                .add(
                    new ShapedOreRecipe(
                        ItemParts.getPartByName("cupronickelHeatingCoil", 3),
                        "NCN",
                        "C C",
                        "NCN",
                        'N',
                        "ingotNickel",
                        'C',
                        "ingotCopper"));

            mods.railcraft.api.crafting.RailcraftCraftingManager.rollingMachine.getRecipeList()
                .add(
                    new ShapedOreRecipe(
                        ItemParts.getPartByName("nichromeHeatingCoil"),
                        " N ",
                        "NCN",
                        " N ",
                        'N',
                        "ingotNickel",
                        'C',
                        "ingotChromium"));

            mods.railcraft.api.crafting.RailcraftCraftingManager.rollingMachine.getRecipeList()
                .add(
                    new ShapelessOreRecipe(
                        ItemParts.getPartByName("kanthalHeatingCoil", 3),
                        "ingotSteel",
                        "ingotSteel",
                        "ingotSteel",
                        "ingotChromium",
                        "ingotChromium",
                        "ingotChromium",
                        "ingotAluminium",
                        "ingotAluminium",
                        "ingotAluminium"));
        }

        // Assembling Machine
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemParts.getPartByName("processorCircuitBoard"),
                IC2Items.getItem("lapotronCrystal"),
                ItemParts.getPartByName("energyFlowCircuit"),
                3200,
                4));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemParts.getPartByName("processorCircuitBoard"),
                ItemParts.getPartByName("dataStorageCircuit"),
                ItemParts.getPartByName("dataControlCircuit"),
                3200,
                4));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                new ItemStack(Items.emerald, 8),
                IC2Items.getItem("advancedCircuit"),
                ItemParts.getPartByName("dataStorageCircuit", 4),
                3200,
                4,
                false));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemGems.getGemByName("olivine", 8),
                IC2Items.getItem("advancedCircuit"),
                ItemParts.getPartByName("dataStorageCircuit", 4),
                3200,
                8));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemDusts.getDustByName("emerald", 8),
                IC2Items.getItem("advancedCircuit"),
                ItemParts.getPartByName("dataStorageCircuit", 4),
                3200,
                4));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemDusts.getDustByName("olivine", 8),
                IC2Items.getItem("advancedCircuit"),
                ItemParts.getPartByName("dataStorageCircuit", 4),
                3200,
                4));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemPlates.getPlateByName("iron", 2),
                IC2Items.getItem("electronicCircuit"),
                ItemParts.getPartByName("machineParts", 4),
                800,
                16));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemPlates.getPlateByName("aluminium", 2),
                IC2Items.getItem("electronicCircuit"),
                ItemParts.getPartByName("machineParts", 3),
                800,
                16));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                new ItemStack(Items.dye, 1, 4),
                new ItemStack(Items.glowstone_dust),
                ItemParts.getPartByName("advancedCircuitParts", 2),
                800,
                2,
                false));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemDusts.getDustByName("lazurite"),
                new ItemStack(Items.glowstone_dust),
                ItemParts.getPartByName("advancedCircuitParts", 2),
                800,
                2,
                false));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemPlates.getPlateByName("aluminium", 8),
                ItemParts.getPartByName("machineParts"),
                BlockMachineFrame.getFrameByName("aluminium", 1),
                400,
                8));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemPlates.getPlateByName("bronze", 8),
                ItemParts.getPartByName("machineParts"),
                BlockMachineFrame.getFrameByName("bronze", 1),
                400,
                8));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemPlates.getPlateByName("brass", 8),
                ItemParts.getPartByName("machineParts"),
                BlockMachineFrame.getFrameByName("brass", 1),
                400,
                8));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemPlates.getPlateByName("steel", 8),
                ItemParts.getPartByName("machineParts"),
                BlockMachineFrame.getFrameByName("steel", 1),
                400,
                8));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemPlates.getPlateByName("titanium", 8),
                ItemParts.getPartByName("machineParts"),
                BlockMachineFrame.getFrameByName("titanium", 1),
                400,
                8));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemPlates.getPlateByName("iron"),
                ItemPlates.getPlateByName("electrum", 2),
                ItemParts.getPartByName("basicCircuitBoard", 2),
                800,
                1));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemPlates.getPlateByName("aluminium"),
                ItemPlates.getPlateByName("electrum", 2),
                ItemParts.getPartByName("basicCircuitBoard", 2),
                800,
                1));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemPlates.getPlateByName("silicon"),
                ItemPlates.getPlateByName("electrum", 4),
                ItemParts.getPartByName("advancedCircuitBoard", 2),
                1600,
                2));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                IC2Items.getItem("electronicCircuit"),
                ItemPlates.getPlateByName("electrum", 2),
                ItemParts.getPartByName("advancedCircuitBoard"),
                1600,
                2));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemPlates.getPlateByName("platinum"),
                IC2Items.getItem("advancedCircuit"),
                ItemParts.getPartByName("processorCircuitBoard"),
                3200,
                4));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemParts.getPartByName("dataControlCircuit"),
                ItemParts.getPartByName("dataStorageCircuit", 8),
                ItemParts.getPartByName("dataOrb", 4),
                12800,
                16));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                new ItemStack(ModItems.lithiumBatpack, 1, OreDictionary.WILDCARD_VALUE),
                null,
                new ItemStack(ModItems.lithiumBattery, 6),
                3200,
                4,
                false));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                any(IC2Items.getItem("batPack")),
                null,
                IC2Items.getItem("reBattery"),
                3200,
                4,
                false));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemParts.getPartByName("advancedCircuitBoard"),
                ItemParts.getPartByName("advancedCircuitParts", 2),
                IC2Items.getItem("advancedCircuit"),
                1600,
                2));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                new ItemStack(
                    IC2Items.getItem("windMill")
                        .getItem(),
                    2,
                    4),
                null,
                IC2Items.getItem("generator"),
                6400,
                8));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                IC2Items.getItem("generator"),
                new ItemStack(
                    IC2Items.getItem("carbonPlate")
                        .getItem(),
                    4),
                IC2Items.getItem("windMill"),
                6400,
                8));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                IC2Items.getItem("generator"),
                ItemPlates.getPlateByName("magnalium", 2),
                IC2Items.getItem("windMill"),
                6400,
                8));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                IC2Items.getItem("generator"),
                ItemPlates.getPlateByName("aluminium", 4),
                new ItemStack(
                    IC2Items.getItem("waterMill")
                        .getItem(),
                    2,
                    2),
                6400,
                8));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                new ItemStack(Items.stick),
                new ItemStack(Blocks.cobblestone),
                new ItemStack(Blocks.lever),
                400,
                1));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                new ItemStack(Items.stick),
                new ItemStack(Items.coal),
                new ItemStack(Blocks.torch, 4),
                400,
                1));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                new ItemStack(Items.stick),
                IC2Items.getItem("resin"),
                new ItemStack(Blocks.torch, 4),
                400,
                1));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                new ItemStack(Items.stick),
                new ItemStack(Items.redstone),
                new ItemStack(Blocks.redstone_torch),
                400,
                1));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                new ItemStack(Items.iron_ingot, 4),
                new ItemStack(Items.redstone),
                new ItemStack(Items.compass),
                400,
                4));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                new ItemStack(Items.gold_ingot, 4),
                new ItemStack(Items.redstone),
                new ItemStack(Items.clock),
                400,
                4));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(new ItemStack(Items.iron_ingot, 8), null, IC2Items.getItem("machine"), 400, 8));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                IC2Items.getItem("copperCableItem"),
                IC2Items.getItem("rubber"),
                IC2Items.getItem("insulatedCopperCableItem"),
                100,
                2));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                IC2Items.getItem("goldCableItem"),
                new ItemStack(
                    IC2Items.getItem("rubber")
                        .getItem(),
                    2),
                IC2Items.getItem("insulatedGoldCableItem"),
                100,
                2));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                IC2Items.getItem("ironCableItem"),
                new ItemStack(
                    IC2Items.getItem("rubber")
                        .getItem(),
                    3),
                IC2Items.getItem("insulatedIronCableItem"),
                100,
                2));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                ItemParts.getPartByName("basicCircuitBoard"),
                new ItemStack(
                    IC2Items.getItem("insulatedCopperCableItem")
                        .getItem(),
                    3),
                IC2Items.getItem("electronicCircuit"),
                1600,
                1));
        RecipeHandler.addRecipe(
            new AssemblingMachineRecipe(
                IC2Items.getItem("electronicCircuit"),
                IC2Items.getItem("insulatedCopperCableItem"),
                IC2Items.getItem("frequencyTransmitter"),
                800,
                1));

        // Smelting
        GameRegistry.addSmelting(ItemDusts.getDustByName("copper", 1), IC2Items.getItem("copperIngot"), 1F);
        GameRegistry.addSmelting(ItemDusts.getDustByName("tin", 1), IC2Items.getItem("tinIngot"), 1F);
        GameRegistry.addSmelting(ItemDusts.getDustByName("bronze", 1), IC2Items.getItem("bronzeIngot"), 1F);
        GameRegistry.addSmelting(ItemDusts.getDustByName("lead", 1), IC2Items.getItem("leadIngot"), 1F);
        GameRegistry.addSmelting(ItemDusts.getDustByName("silver", 1), IC2Items.getItem("silverIngot"), 1F);
        ItemStack sulfurDust = IC2Items.getItem("sulfurDust")
            .copy();
        sulfurDust.stackSize = 2;
        GameRegistry.addSmelting(OreUtil.getStackFromName("oreSulfur"), sulfurDust, 1F);
        GameRegistry.addSmelting(Items.slime_ball, IC2Items.getItem("resin"), 1F);

        // Saw mill
        RecipeHandler.addRecipe(
            new IndustrialSawmillRecipe(
                IC2Items.getItem("rubberWood"),
                null,
                new FluidStack(WATER, 1000),
                IC2Items.getItem("resin"),
                ItemDusts.getDustByName("sawDust", 16),
                null,
                200,
                30,
                true));
        RecipeHandler.addRecipe(
            new IndustrialSawmillRecipe(
                IC2Items.getItem("rubberWood"),
                IC2Items.getItem("waterCell"),
                null,
                IC2Items.getItem("resin"),
                ItemDusts.getDustByName("sawDust", 16),
                IC2Items.getItem("cell"),
                200,
                30,
                true));
        if (Loader.isModLoaded("ProjRed|Core")) {
            ItemStack boule = GameRegistry.findItemStack("ProjRed|Core", "projectred.core.part", 1);
            ItemStack silicon = boule.copy();
            silicon.stackSize = 16;
            Items.apple.setDamage(boule, 11);
            Items.apple.setDamage(silicon, 12);
            RecipeHandler.addRecipe(
                new IndustrialSawmillRecipe(
                    boule,
                    null,
                    new FluidStack(WATER, 1000),
                    silicon,
                    null,
                    null,
                    200,
                    30,
                    true));
            RecipeHandler.addRecipe(
                new IndustrialSawmillRecipe(
                    boule,
                    IC2Items.getItem("waterCell"),
                    null,
                    silicon,
                    null,
                    IC2Items.getItem("cell"),
                    200,
                    30,
                    true));
        }

        // UU
        if (ConfigTechReborn.UUrecipesIridiamOre) CraftingHelper
            .addShapedOreRecipe((IC2Items.getItem("iridiumOre")), "UUU", " U ", "UUU", 'U', ModItems.uuMatter);

        // Blast Furnace
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                new ItemStack(Blocks.iron_ore),
                ItemCells.getCellByName("calciumCarbonate"),
                new ItemStack(
                    IC2Items.getItem("advIronIngot")
                        .getItem(),
                    3,
                    3),
                IC2Items.getItem("cell"),
                100,
                128,
                1000));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                BlockOre.getOreByName("Pyrite"),
                ItemCells.getCellByName("calciumCarbonate"),
                new ItemStack(
                    IC2Items.getItem("advIronIngot")
                        .getItem(),
                    2,
                    3),
                IC2Items.getItem("cell"),
                100,
                128,
                1000));
        if (OreUtil.doesOreExistAndValid("oreNetherIron")) {
            RecipeHandler.addRecipe(
                new BlastFurnaceRecipe(
                    OreUtil.getStackFromName("oreNetherIron"),
                    ItemCells.getCellByName("calciumCarbonate"),
                    new ItemStack(
                        IC2Items.getItem("advIronIngot")
                            .getItem(),
                        5,
                        3),
                    IC2Items.getItem("cell"),
                    100,
                    128,
                    1000));
        }
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemCells.getCellByName("silicon", 2),
                null,
                ItemPlates.getPlateByName("silicon"),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    2),
                1000,
                128,
                1500));

        // CentrifugeRecipes
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                IC2Items.getItem("reactorDepletedUraniumSimple"),
                null,
                ItemDusts.getDustByName("thorium"),
                IC2Items.getItem("cell"),
                null,
                null,
                500,
                5));

        // Plantball/Bio Chaff
        if (!CompatManager.isIC2ClassicLoaded) {
            RecipeHandler.addRecipe(
                new CentrifugeRecipe(
                    new ItemStack(Blocks.grass, 16),
                    null,
                    new ItemStack(
                        IC2Items.getItem("biochaff")
                            .getItem(),
                        2),
                    new ItemStack(
                        IC2Items.getItem("plantBall")
                            .getItem(),
                        2),
                    new ItemStack(Items.clay_ball),
                    new ItemStack(Blocks.sand, 8),
                    2500,
                    5));
            RecipeHandler.addRecipe(
                new CentrifugeRecipe(
                    new ItemStack(Blocks.dirt, 16),
                    null,
                    IC2Items.getItem("biochaff"),
                    IC2Items.getItem("plantBall"),
                    new ItemStack(Items.clay_ball),
                    new ItemStack(Blocks.sand, 8),
                    2500,
                    5));
        }
        ItemStack lavaCell = IC2Items.getItem("lavaCell")
            .copy();
        lavaCell.stackSize = 16;
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                lavaCell,
                null,
                ItemDusts.getDustByName("tin", 18),
                ItemDusts.getDustByName("copper", 4),
                ItemDusts.getDustByName("electrum"),
                ItemDustsSmall.getSmallDustByName("tungsten"),
                15000,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("uranium", 4),
                new ItemStack(
                    IC2Items.getItem("fuelRod")
                        .getItem(),
                    4),
                new ItemStack(
                    IC2Items.getItem("reactorUraniumSimple")
                        .getItem(),
                    4,
                    1),
                ItemDustsSmall.getSmallDustByName("plutonium"),
                ItemDusts.getDustByName("thorium", 2),
                ItemDusts.getDustByName("tungsten"),
                10000,
                5));

        // Methane
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.mushroom_stew, 16),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.apple, 32),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.porkchop, 12),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.cooked_porkchop, 16),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.bread, 64),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.fish, 12),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.cooked_fished, 16),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.beef, 12),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.cooked_beef, 16),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Blocks.pumpkin, 16),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.speckled_melon, 1),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                new ItemStack(Items.gold_nugget, 6),
                null,
                null,
                10000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.spider_eye, 32),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.chicken, 12),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.cooked_chicken, 16),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.rotten_flesh, 16),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.melon, 64),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.cookie, 64),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.cake, 8),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.golden_carrot, 1),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                new ItemStack(Items.gold_nugget, 6),
                null,
                null,
                10000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.carrot, 16),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.baked_potato, 24),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.potato, 16),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.poisonous_potato, 12),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.nether_wart, 32),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(
                    IC2Items.getItem("terraWart")
                        .getItem(),
                    16),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Blocks.brown_mushroom_block, 12),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Blocks.red_mushroom_block, 12),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Blocks.brown_mushroom, 32),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Blocks.red_mushroom, 32),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane", 1),
                null,
                null,
                null,
                5000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.golden_apple, 1, 1),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    2),
                ItemCells.getCellByName("methane", 2),
                new ItemStack(Items.gold_ingot, 64),
                null,
                null,
                10000,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.golden_apple),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("methane"),
                new ItemStack(Items.gold_nugget, 6),
                null,
                null,
                10000,
                5,
                false));

        // Rubber Wood Yields
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(
                    IC2Items.getItem("rubberWood")
                        .getItem(),
                    16),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    5),
                new ItemStack(
                    IC2Items.getItem("resin")
                        .getItem(),
                    8),
                new ItemStack(
                    IC2Items.getItem("plantBall")
                        .getItem(),
                    6),
                ItemCells.getCellByName("methane", 1),
                ItemCells.getCellByName("carbon", 4),
                5000,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(
                    IC2Items.getItem("resin")
                        .getItem(),
                    4),
                null,
                new ItemStack(
                    IC2Items.getItem("rubber")
                        .getItem(),
                    14),
                IC2Items.getItem("biochaff"),
                IC2Items.getItem("plantBall"),
                null,
                1300,
                5,
                false));

        // Soul Sand Byproducts
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Blocks.soul_sand, 16),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("oil", 1),
                ItemDusts.getDustByName("saltpeter", 4),
                ItemDusts.getDustByName("coal", 1),
                new ItemStack(Blocks.sand, 10),
                2500,
                5, false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                OreUtil.getStackFromName("sandCracked"),
                null,
                ItemDusts.getDustByName("saltpeter", 8),
                new ItemStack(Blocks.sand, 10),
                null,
                null,
                2500,
                5));

        // Ice
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemCells.getCellByName("ice", 1),
                null,
                new ItemStack(Blocks.ice, 1),
                IC2Items.getItem("cell"),
                null,
                null,
                40,
                5));

        // Dust Byproducts
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.glowstone_dust, 16),
                IC2Items.getItem("cell"),
                new ItemStack(Items.redstone, 8),
                ItemDusts.getDustByName("gold", 8),
                ItemCells.getCellByName("helium", 1),
                null,
                25000,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.redstone, 10),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    4),
                ItemCells.getCellByName("silicon", 1),
                ItemDusts.getDustByName("pyrite", 5),
                ItemDusts.getDustByName("ruby", 1),
                ItemCells.getCellByName("mercury", 3),
                7000,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("endstone", 16),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    2),
                ItemCells.getCellByName("helium3", 1),
                ItemCells.getCellByName("helium"),
                ItemDustsSmall.getSmallDustByName("Tungsten", 1),
                new ItemStack(Blocks.sand, 12),
                4800,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("redrock", 4),
                null,
                ItemDusts.getDustByName("calcite", 2),
                ItemDusts.getDustByName("flint", 1),
                IC2Items.getItem("clayDust"),
                null,
                100,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                IC2Items.getItem("lapiDust"),
                null,
                ItemDusts.getDustByName("lazurite", 3),
                ItemDustsSmall.getSmallDustByName("sodalite", 2),
                ItemDustsSmall.getSmallDustByName("pyrite"),
                ItemDustsSmall.getSmallDustByName("calcite"),
                1500,
                5,
                false));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("netherrack", 16),
                null,
                new ItemStack(Items.redstone),
                new ItemStack(
                    IC2Items.getItem("sulfurDust")
                        .getItem(),
                    4,
                    13),
                IC2Items.getItem("coalDust"),
                new ItemStack(Items.gold_nugget),
                2400,
                5));

        // Deuterium/Tritium
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemCells.getCellByName("helium", 16),
                null,
                ItemCells.getCellByName("helium3"),
                IC2Items.getItem("cell"),
                null,
                null,
                10000,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemCells.getCellByName("deuterium", 4),
                null,
                ItemCells.getCellByName("tritium", 1),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    3),
                null,
                null,
                3000,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemCells.getCellByName("hydrogen", 4),
                null,
                ItemCells.getCellByName("deuterium", 1),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    3),
                null,
                null,
                3000,
                5));

        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemCells.getCellByName("calciumCarbonate"),
                null,
                ItemDusts.getDustByName("calcite"),
                IC2Items.getItem("cell"),
                null,
                null,
                40,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemCells.getCellByName("sulfur"),
                null,
                ItemDusts.getDustByName("sulfur"),
                IC2Items.getItem("cell"),
                null,
                null,
                40,
                5));

        // Lava Cell Byproducts
        ItemStack lavaCells = IC2Items.getItem("lavaCell")
            .copy();
        lavaCells.stackSize = 8;

        // IndustrialGrinderRecipes
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.coal_ore, 1),
                IC2Items.getItem("waterCell"),
                null,
                new ItemStack(Items.coal, 1),
                IC2Items.getItem("coalDust"),
                ItemDustsSmall.getSmallDustByName("thorium", 1),
                IC2Items.getItem("cell"),
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherCoal")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherCoal"),
                    IC2Items.getItem("waterCell"),
                    null,
                    new ItemStack(Items.coal, 2),
                    ItemDusts.getDustByName("coal", 2),
                    ItemDustsSmall.getSmallDustByName("thorium"),
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.iron_ore, 1),
                IC2Items.getItem("waterCell"),
                null,
                ItemDusts.getDustByName("iron", 2),
                ItemDustsSmall.getSmallDustByName("Nickel", 1),
                ItemDustsSmall.getSmallDustByName("Tin", 1),
                IC2Items.getItem("cell"),
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherIron")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherIron"),
                    IC2Items.getItem("waterCell"),
                    null,
                    ItemDusts.getDustByName("iron", 4),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    ItemDustsSmall.getSmallDustByName("Tin", 1),
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.gold_ore, 1),
                IC2Items.getItem("waterCell"),
                null,
                ItemDusts.getDustByName("gold", 2),
                ItemDustsSmall.getSmallDustByName("Copper", 1),
                ItemDustsSmall.getSmallDustByName("Nickel", 1),
                IC2Items.getItem("cell"),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.gold_ore, 1),
                ItemCells.getCellByName("sodiumPersulfate", 1),
                null,
                ItemDusts.getDustByName("gold", 2),
                ItemDusts.getDustByName("copper", 1),
                ItemDustsSmall.getSmallDustByName("Nickel", 1),
                IC2Items.getItem("cell"),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.gold_ore, 1),
                ItemCells.getCellByName("mercury", 1),
                null,
                ItemDusts.getDustByName("gold", 3),
                ItemDustsSmall.getSmallDustByName("Copper", 1),
                ItemDustsSmall.getSmallDustByName("Nickel", 1),
                IC2Items.getItem("cell"),
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherGold")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherGold"),
                    IC2Items.getItem("waterCell"),
                    null,
                    ItemDusts.getDustByName("gold", 4),
                    ItemDustsSmall.getSmallDustByName("Copper", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    IC2Items.getItem("cell"),
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherGold"),
                    ItemCells.getCellByName("mercury", 1),
                    null,
                    ItemDusts.getDustByName("gold", 5),
                    ItemDustsSmall.getSmallDustByName("Copper", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    IC2Items.getItem("cell"),
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherGold"),
                    ItemCells.getCellByName("sodiumPersulfate", 1),
                    null,
                    ItemDusts.getDustByName("gold", 4),
                    ItemDusts.getDustByName("copper", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.diamond_ore, 1),
                IC2Items.getItem("waterCell"),
                null,
                new ItemStack(Items.diamond, 1),
                ItemDustsSmall.getSmallDustByName("Diamond", 6),
                IC2Items.getItem("hydratedCoalDust"),
                IC2Items.getItem("cell"),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.diamond_ore, 1),
                null,
                new FluidStack(WATER, 1000),
                new ItemStack(Items.diamond, 1),
                ItemDustsSmall.getSmallDustByName("Diamond", 6),
                IC2Items.getItem("hydratedCoalDust"),
                null,
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherDiamond")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    new ItemStack(Blocks.diamond_ore, 1),
                    IC2Items.getItem("waterCell"),
                    null,
                    new ItemStack(Items.diamond, 2),
                    ItemDustsSmall.getSmallDustByName("Diamond", 6),
                    IC2Items.getItem("hydratedCoalDust"),
                    IC2Items.getItem("cell"),
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    new ItemStack(Blocks.diamond_ore, 1),
                    null,
                    new FluidStack(WATER, 1000),
                    new ItemStack(Items.diamond, 2),
                    ItemDustsSmall.getSmallDustByName("Diamond", 6),
                    IC2Items.getItem("hydratedCoalDust"),
                    null,
                    100,
                    128));
        }
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.emerald_ore, 1),
                IC2Items.getItem("waterCell"),
                null,
                new ItemStack(Items.emerald, 1),
                ItemDustsSmall.getSmallDustByName("Emerald", 6),
                ItemDustsSmall.getSmallDustByName("olivine", 2),
                IC2Items.getItem("cell"),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.emerald_ore, 1),
                null,
                new FluidStack(WATER, 1000),
                new ItemStack(Items.emerald, 1),
                ItemDustsSmall.getSmallDustByName("Emerald", 6),
                ItemDustsSmall.getSmallDustByName("olivine", 2),
                null,
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherEmerald")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    new ItemStack(Blocks.emerald_ore, 1),
                    IC2Items.getItem("waterCell"),
                    null,
                    new ItemStack(Items.emerald, 2),
                    ItemDustsSmall.getSmallDustByName("Emerald", 12),
                    ItemDustsSmall.getSmallDustByName("olivine", 2),
                    IC2Items.getItem("cell"),
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    new ItemStack(Blocks.emerald_ore, 1),
                    null,
                    new FluidStack(WATER, 1000),
                    new ItemStack(Items.emerald, 2),
                    ItemDustsSmall.getSmallDustByName("Emerald", 12),
                    ItemDustsSmall.getSmallDustByName("olivine", 2),
                    null,
                    100,
                    128));
        }
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.redstone_ore, 1),
                IC2Items.getItem("waterCell"),
                null,
                new ItemStack(Items.redstone, 10),
                ItemDustsSmall.getSmallDustByName("Glowstone", 2),
                null,
                IC2Items.getItem("cell"),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.redstone_ore, 1),
                null,
                new FluidStack(WATER, 1000),
                new ItemStack(Items.redstone, 10),
                ItemDustsSmall.getSmallDustByName("Glowstone", 2),
                null,
                null,
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherRedstone")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    new ItemStack(Blocks.redstone_ore, 1),
                    IC2Items.getItem("waterCell"),
                    null,
                    new ItemStack(Items.redstone, 15),
                    ItemDustsSmall.getSmallDustByName("Glowstone", 2),
                    null,
                    IC2Items.getItem("cell"),
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    new ItemStack(Blocks.redstone_ore, 1),
                    null,
                    new FluidStack(WATER, 1000),
                    new ItemStack(Items.redstone, 15),
                    ItemDustsSmall.getSmallDustByName("Glowstone", 2),
                    null,
                    null,
                    100,
                    128));
        }
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.lapis_ore),
                IC2Items.getItem("waterCell"),
                null,
                new ItemStack(Items.dye, 12, 4),
                ItemDusts.getDustByName("lazurite", 3),
                null,
                IC2Items.getItem("cell"),
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherLapis")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreDictionary.getOres("oreNetherLapis")
                        .get(0),
                    IC2Items.getItem("waterCell"),
                    null,
                    new ItemStack(Items.dye, 18, 4),
                    ItemDusts.getDustByName("lazurite", 3),
                    null,
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sodalite"),
                IC2Items.getItem("waterCell"),
                null,
                ItemDusts.getDustByName("sodalite", 12),
                ItemDusts.getDustByName("aluminium", 3),
                null,
                IC2Items.getItem("cell"),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.netherrack, 16),
                IC2Items.getItem("waterCell"),
                null,
                new ItemStack(Items.gold_nugget),
                ItemDusts.getDustByName("netherrack", 16),
                null,
                IC2Items.getItem("cell"),
                1600,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.netherrack, 8),
                ItemCells.getCellByName("mercury"),
                null,
                new ItemStack(Items.gold_nugget),
                ItemDusts.getDustByName("netherrack", 8),
                null,
                IC2Items.getItem("cell"),
                800,
                128));

        // Copper Ore
        if (OreUtil.doesOreExistAndValid("oreCopper")) {
            try {
                ItemStack oreStack = OreDictionary.getOres("oreCopper")
                    .get(0);
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        IC2Items.getItem("waterCell"),
                        null,
                        ItemDusts.getDustByName("copper", 2),
                        ItemDustsSmall.getSmallDustByName("Gold", 1),
                        ItemDustsSmall.getSmallDustByName("Nickel", 1),
                        IC2Items.getItem("cell"),
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        ItemCells.getCellByName("sodiumPersulfate", 1),
                        null,
                        ItemDusts.getDustByName("copper", 3),
                        ItemDustsSmall.getSmallDustByName("gold", 1),
                        ItemDustsSmall.getSmallDustByName("Nickel", 1),
                        IC2Items.getItem("cell"),
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        ItemCells.getCellByName("mercury", 1),
                        null,
                        ItemDusts.getDustByName("copper", 2),
                        ItemDusts.getDustByName("Gold", 1),
                        null,
                        IC2Items.getItem("cell"),
                        100,
                        128));
            } catch (Exception e) {
                Core.logHelper.info("Failed to Load Grinder Recipe for Copper Ore");
            }
        }
        if (OreUtil.doesOreExistAndValid("oreNetherCopper")) {
            ItemStack oreStack = OreDictionary.getOres("oreNetherCopper")
                .get(0);
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    oreStack,
                    IC2Items.getItem("waterCell"),
                    null,
                    ItemDusts.getDustByName("copper", 4),
                    ItemDustsSmall.getSmallDustByName("Gold", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    IC2Items.getItem("cell"),
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    oreStack,
                    ItemCells.getCellByName("sodiumPersulfate", 1),
                    null,
                    ItemDusts.getDustByName("copper", 5),
                    ItemDustsSmall.getSmallDustByName("gold", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    IC2Items.getItem("cell"),
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    oreStack,
                    ItemCells.getCellByName("mercury", 1),
                    null,
                    ItemDusts.getDustByName("copper", 4),
                    ItemDusts.getDustByName("Gold", 1),
                    null,
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }

        // Tin Ore
        if (OreUtil.doesOreExistAndValid("oreTin")) {
            try {
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreTin"),
                        IC2Items.getItem("waterCell"),
                        null,
                        ItemDusts.getDustByName("tin", 2),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDustsSmall.getSmallDustByName("Zinc", 1),
                        IC2Items.getItem("cell"),
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreTin"),
                        ItemCells.getCellByName("sodiumPersulfate", 1),
                        null,
                        ItemDusts.getDustByName("tin", 2),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDusts.getDustByName("zinc", 1),
                        IC2Items.getItem("cell"),
                        100,
                        128));
            } catch (Exception e) {
                Core.logHelper.info("Failed to Load Grinder Recipe for Tin Ore");
            }
        }
        if (OreUtil.doesOreExistAndValid("oreNetherTin")) {
            try {
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreNetherTin"),
                        IC2Items.getItem("waterCell"),
                        null,
                        ItemDusts.getDustByName("tin", 4),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDustsSmall.getSmallDustByName("Zinc", 1),
                        IC2Items.getItem("cell"),
                        100,
                        120));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreNetherTin"),
                        ItemCells.getCellByName("sodiumPersulfate", 1),
                        null,
                        ItemDusts.getDustByName("tin", 4),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDusts.getDustByName("zinc", 1),
                        IC2Items.getItem("cell"),
                        100,
                        120));
            } catch (Exception e) {
                Core.logHelper.info("Failed to Load Grinder Recipe for Tin Ore");
            }
        }

        // Nickel Ore
        if (OreUtil.doesOreExistAndValid("oreNickel")) {
            try {
                ItemStack oreStack = OreUtil.getStackFromName("oreNickel");
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        IC2Items.getItem("waterCell"),
                        null,
                        ItemDusts.getDustByName("nickel", 3),
                        ItemDustsSmall.getSmallDustByName("platinum"),
                        ItemDustsSmall.getSmallDustByName("copper"),
                        IC2Items.getItem("cell"),
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        ItemCells.getCellByName("mercury"),
                        null,
                        ItemDusts.getDustByName("nickel", 3),
                        ItemDusts.getDustByName("platinum"),
                        null,
                        IC2Items.getItem("cell"),
                        100,
                        128));
            } catch (Exception e) {
                Core.logHelper.info("Failed to Load Grinder Recipe for Nickel Ore");
            }
        }

        // Zinc Ore
        if (OreUtil.doesOreExistAndValid("oreZinc")) {
            try {
                ItemStack oreStack = OreDictionary.getOres("oreZinc")
                    .get(0);
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        IC2Items.getItem("waterCell"),
                        null,
                        ItemDusts.getDustByName("zinc", 2),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDustsSmall.getSmallDustByName("Tin", 1),
                        IC2Items.getItem("cell"),
                        100,
                        120));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        ItemCells.getCellByName("sodiumPersulfate", 1),
                        null,
                        ItemDusts.getDustByName("zinc", 2),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDusts.getDustByName("iron", 1),
                        IC2Items.getItem("cell"),
                        100,
                        120));
            } catch (Exception e) {
                Core.logHelper.info("Failed to Load Grinder Recipe for Zinc Ore");
            }
        }

        // Silver Ore
        if (OreUtil.doesOreExistAndValid("oreSilver")) {
            try {
                ItemStack oreStack = OreDictionary.getOres("oreSilver")
                    .get(0);
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        IC2Items.getItem("waterCell"),
                        null,
                        ItemDusts.getDustByName("silver", 2),
                        ItemDustsSmall.getSmallDustByName("Lead", 2),
                        null,
                        IC2Items.getItem("cell"),
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        ItemCells.getCellByName("mercury", 1),
                        null,
                        ItemDusts.getDustByName("silver", 3),
                        ItemDustsSmall.getSmallDustByName("Lead", 2),
                        null,
                        IC2Items.getItem("cell"),
                        100,
                        128));
            } catch (Exception e) {
                Core.logHelper.info("Failed to Load Grinder Recipe for Silver Ore");
            }
        }
        if (OreUtil.doesOreExistAndValid("oreNetherSilver")) {
            ItemStack oreStack = OreDictionary.getOres("oreNetherSilver")
                .get(0);
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    oreStack,
                    IC2Items.getItem("waterCell"),
                    null,
                    ItemDusts.getDustByName("silver", 4),
                    ItemDustsSmall.getSmallDustByName("Lead", 2),
                    null,
                    IC2Items.getItem("cell"),
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    oreStack,
                    ItemCells.getCellByName("mercury", 1),
                    null,
                    ItemDusts.getDustByName("silver", 5),
                    ItemDustsSmall.getSmallDustByName("Lead", 2),
                    null,
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }

        // Lead Ore
        if (OreUtil.doesOreExistAndValid("oreLead")) {
            try {
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreLead"),
                        IC2Items.getItem("waterCell"),
                        null,
                        ItemDusts.getDustByName("lead", 2),
                        ItemDustsSmall.getSmallDustByName("Silver", 1),
                        null,
                        IC2Items.getItem("cell"),
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreLead"),
                        ItemCells.getCellByName("mercury"),
                        null,
                        ItemDusts.getDustByName("lead", 2),
                        ItemDusts.getDustByName("silver", 1),
                        null,
                        IC2Items.getItem("cell"),
                        100,
                        128));
            } catch (Exception e) {
                Core.logHelper.info("Failed to Load Grinder Recipe for Lead Ore");
            }
        }
        if (OreUtil.doesOreExistAndValid("oreNetherLead")) {
            try {
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreNetherLead"),
                        IC2Items.getItem("waterCell"),
                        null,
                        ItemDusts.getDustByName("lead", 4),
                        ItemDustsSmall.getSmallDustByName("Silver", 1),
                        null,
                        IC2Items.getItem("cell"),
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreNetherLead"),
                        ItemCells.getCellByName("mercury"),
                        null,
                        ItemDusts.getDustByName("lead", 4),
                        ItemDusts.getDustByName("silver", 1),
                        null,
                        IC2Items.getItem("cell"),
                        100,
                        128));
            } catch (Exception e) {
                Core.logHelper.info("Failed to Load Grinder Recipe for Nether Lead Ore");
            }
        }

        // Uranium Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                IC2Items.getItem("uraniumOre"),
                null,
                new FluidStack(WATER, 1000),
                ItemDusts.getDustByName("uranium", 2),
                ItemDustsSmall.getSmallDustByName("plutonium", 2),
                null,
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                IC2Items.getItem("uraniumOre"),
                IC2Items.getItem("waterCell"),
                null,
                ItemDusts.getDustByName("uranium", 2),
                ItemDustsSmall.getSmallDustByName("plutonium", 2),
                null,
                IC2Items.getItem("cell"),
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherUranium")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherUranium"),
                    null,
                    new FluidStack(WATER, 1000),
                    ItemDusts.getDustByName("uranium", 4),
                    ItemDustsSmall.getSmallDustByName("plutonium", 2),
                    null,
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherUranium"),
                    IC2Items.getItem("waterCell"),
                    null,
                    ItemDusts.getDustByName("uranium", 4),
                    ItemDustsSmall.getSmallDustByName("plutonium", 2),
                    null,
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }

        // aluminium Ore
        if (OreUtil.doesOreExistAndValid("orealuminium")) {
            try {
                ItemStack oreStack = OreDictionary.getOres("orealuminium")
                    .get(0);
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        IC2Items.getItem("waterCell"),
                        null,
                        ItemDusts.getDustByName("aluminium", 2),
                        ItemDustsSmall.getSmallDustByName("Bauxite", 1),
                        ItemDustsSmall.getSmallDustByName("Bauxite", 1),
                        IC2Items.getItem("cell"),
                        100,
                        120));
            } catch (Exception e) {
                Core.logHelper.info("Failed to Load Grinder Recipe for Lead Ore");
            }
        }

        // Sulfur Ore
        if (OreUtil.doesOreExistAndValid("oreSulfur")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreSulfur"),
                    null,
                    new FluidStack(WATER, 1000),
                    new ItemStack(
                        IC2Items.getItem("sulfurDust")
                            .getItem(),
                        10,
                        13),
                    null,
                    null,
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreSulfur"),
                    IC2Items.getItem("waterCell"),
                    null,
                    new ItemStack(
                        IC2Items.getItem("sulfurDust")
                            .getItem(),
                        10,
                        13),
                    null,
                    null,
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }

        // Apatite Ore
        if (OreUtil.doesOreExistAndValid("oreApatite") & OreUtil.doesOreExistAndValid("gemApatite")) {
            try {
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreApatite"),
                        IC2Items.getItem("waterCell"),
                        null,
                        OreUtil.getStackFromName("gemApatite", 12),
                        ItemDusts.getDustByName("tricalciumPhosphate"),
                        null,
                        IC2Items.getItem("cell"),
                        100,
                        128));
            } catch (Exception e) {
                Core.logHelper.info("Failed to Load Grinder Recipe for Apatite Ore");
            }
        }

        // Saltpeter Ore
        if (OreUtil.doesOreExistAndValid("oreSaltpeter")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreSaltpeter"),
                    IC2Items.getItem("waterCell"),
                    null,
                    ItemDusts.getDustByName("saltpeter", 7),
                    null,
                    null,
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }

        // Sheldonite Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sheldonite"),
                IC2Items.getItem("waterCell"),
                null,
                ItemDusts.getDustByName("platinum", 2),
                ItemDusts.getDustByName("nickel"),
                ItemNuggets.getNuggetByName("iridium", 2),
                IC2Items.getItem("cell"),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sheldonite"),
                ItemCells.getCellByName("mercury"),
                null,
                ItemDusts.getDustByName("platinum", 3),
                ItemDusts.getDustByName("nickel"),
                ItemNuggets.getNuggetByName("iridium", 2),
                IC2Items.getItem("cell"),
                100,
                128));

        // Electrotine Ore
        if (OreUtil.doesOreExistAndValid("oreElectrotine")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreElectrotine"),
                    null,
                    new FluidStack(WATER, 1000),
                    OreUtil.getStackFromName("dustElectrotine", 12),
                    ItemDustsSmall.getSmallDustByName("diamond"),
                    null,
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreElectrotine"),
                    IC2Items.getItem("waterCell"),
                    null,
                    OreUtil.getStackFromName("dustElectrotine", 12),
                    ItemDustsSmall.getSmallDustByName("diamond"),
                    null,
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }
        if (OreUtil.doesOreExistAndValid("oreNetherElectrotine")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherElectrotine"),
                    null,
                    new FluidStack(WATER, 1000),
                    OreUtil.getStackFromName("dustElectrotine", 18),
                    ItemDustsSmall.getSmallDustByName("diamond", 2),
                    null,
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherElectrotine"),
                    IC2Items.getItem("waterCell"),
                    null,
                    OreUtil.getStackFromName("dustElectrotine", 18),
                    ItemDustsSmall.getSmallDustByName("diamond", 2),
                    null,
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }

        // Peridot Ore
        if (OreUtil.doesOreExistAndValid("orePeridot")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreDictionary.getOres("orePeridot")
                        .get(0),
                    IC2Items.getItem("waterCell"),
                    null,
                    ItemGems.getGemByName("peridot"),
                    ItemDustsSmall.getSmallDustByName("peridot", 6),
                    ItemDustsSmall.getSmallDustByName("sapphire", 2),
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }
        if (OreUtil.doesOreExistAndValid("oreNetherPeridot")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreDictionary.getOres("oreNetherPeridot")
                        .get(0),
                    IC2Items.getItem("waterCell"),
                    null,
                    ItemGems.getGemByName("peridot", 2),
                    ItemDustsSmall.getSmallDustByName("peridot", 12),
                    ItemDustsSmall.getSmallDustByName("sapphire", 2),
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }

        // Implosion Compressor

        RecipeHandler.addRecipe(
            new ImplosionCompressorRecipe(
                ItemParts.getPartByName("iridiumAlloyIngot"),
                new ItemStack(
                    IC2Items.getItem("industrialTnt")
                        .getItem(),
                    8),
                IC2Items.getItem("iridiumPlate"),
                ItemDusts.getDustByName("darkAshes", 4),
                20,
                32));
        RecipeHandler.addRecipe(
            new ImplosionCompressorRecipe(
                ItemDusts.getDustByName("diamond", 4),
                new ItemStack(
                    IC2Items.getItem("industrialTnt")
                        .getItem(),
                    32),
                new ItemStack(
                    IC2Items.getItem("industrialDiamond")
                        .getItem(),
                    3),
                ItemDusts.getDustByName("darkAshes", 16),
                20,
                32));
        RecipeHandler.addRecipe(
            new ImplosionCompressorRecipe(
                ItemDusts.getDustByName("emerald", 4),
                new ItemStack(
                    IC2Items.getItem("industrialTnt")
                        .getItem(),
                    24),
                new ItemStack(Items.emerald, 3),
                ItemDusts.getDustByName("darkAshes", 12),
                20,
                32));
        RecipeHandler.addRecipe(
            new ImplosionCompressorRecipe(
                ItemDusts.getDustByName("sapphire", 4),
                new ItemStack(
                    IC2Items.getItem("industrialTnt")
                        .getItem(),
                    24),
                ItemGems.getGemByName("sapphire", 3),
                ItemDusts.getDustByName("darkAshes", 12),
                20,
                32));
        RecipeHandler.addRecipe(
            new ImplosionCompressorRecipe(
                ItemDusts.getDustByName("ruby", 4),
                new ItemStack(
                    IC2Items.getItem("industrialTnt")
                        .getItem(),
                    24),
                ItemGems.getGemByName("ruby", 3),
                ItemDusts.getDustByName("darkAshes", 12),
                20,
                32));
        RecipeHandler.addRecipe(
            new ImplosionCompressorRecipe(
                ItemDusts.getDustByName("yellowGarnet", 4),
                new ItemStack(
                    IC2Items.getItem("industrialTnt")
                        .getItem(),
                    24),
                ItemGems.getGemByName("yellowGarnet", 3),
                ItemDusts.getDustByName("darkAshes", 8),
                20,
                32));
        RecipeHandler.addRecipe(
            new ImplosionCompressorRecipe(
                ItemDusts.getDustByName("redGarnet", 4),
                new ItemStack(
                    IC2Items.getItem("industrialTnt")
                        .getItem(),
                    24),
                ItemGems.getGemByName("redGarnet", 3),
                ItemDusts.getDustByName("darkAshes", 8),
                20,
                32));
        RecipeHandler.addRecipe(
            new ImplosionCompressorRecipe(
                ItemDusts.getDustByName("peridot", 4),
                new ItemStack(
                    IC2Items.getItem("industrialTnt")
                        .getItem(),
                    24),
                ItemGems.getGemByName("peridot", 3),
                ItemDusts.getDustByName("darkAshes", 12),
                20,
                32));
        RecipeHandler.addRecipe(
            new ImplosionCompressorRecipe(
                ItemDusts.getDustByName("olivine", 4),
                new ItemStack(
                    IC2Items.getItem("industrialTnt")
                        .getItem(),
                    24),
                ItemGems.getGemByName("olivine", 3),
                ItemDusts.getDustByName("darkAshes", 12),
                20,
                32));

        // Grinder

        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("galena"),
                null,
                new FluidStack(WATER, 1000),
                ItemDusts.getDustByName("galena", 2),
                IC2Items.getItem("sulfurDust"),
                null,
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("galena"),
                IC2Items.getItem("waterCell"),
                null,
                ItemDusts.getDustByName("galena", 2),
                IC2Items.getItem("sulfurDust"),
                null,
                IC2Items.getItem("cell"),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("galena"),
                null,
                new FluidStack(ModFluids.fluidMercury, 1000),
                ItemDusts.getDustByName("galena", 2),
                IC2Items.getItem("sulfurDust"),
                ItemDusts.getDustByName("silver"),
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("galena"),
                new ItemStack(ModItems.bucketMercury),
                null,
                ItemDusts.getDustByName("galena", 2),
                IC2Items.getItem("sulfurDust"),
                ItemDusts.getDustByName("silver"),
                new ItemStack(Items.bucket),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("galena"),
                ItemCells.getCellByName("mercury", 1),
                null,
                ItemDusts.getDustByName("galena", 2),
                IC2Items.getItem("sulfurDust"),
                ItemDusts.getDustByName("silver"),
                IC2Items.getItem("cell"),
                100,
                128));

        // Iridium Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("iridium"),
                null,
                new FluidStack(ModFluids.fluidMercury, 1000),
                new ItemStack(
                    IC2Items.getItem("iridiumOre")
                        .getItem(),
                    2),
                ItemDusts.getDustByName("platinum"),
                null,
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("iridium"),
                new ItemStack(ModItems.bucketMercury),
                null,
                new ItemStack(
                    IC2Items.getItem("iridiumOre")
                        .getItem(),
                    2),
                ItemDusts.getDustByName("platinum"),
                null,
                new ItemStack(Items.bucket),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("iridium"),
                ItemCells.getCellByName("mercury"),
                null,
                new ItemStack(
                    IC2Items.getItem("iridiumOre")
                        .getItem(),
                    2),
                ItemDusts.getDustByName("platinum"),
                null,
                IC2Items.getItem("cell"),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("iridium"),
                null,
                new FluidStack(WATER, 1000),
                new ItemStack(
                    IC2Items.getItem("iridiumOre")
                        .getItem(),
                    2),
                ItemDustsSmall.getSmallDustByName("platinum", 2),
                null,
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("iridium"),
                IC2Items.getItem("aterCell"),
                null,
                new ItemStack(
                    IC2Items.getItem("iridiumOre")
                        .getItem(),
                    2),
                ItemDustsSmall.getSmallDustByName("platinum", 2),
                null,
                IC2Items.getItem("cell"),
                100,
                128));

        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("ruby"),
                IC2Items.getItem("waterCell"),
                null,
                ItemGems.getGemByName("ruby", 1),
                ItemDustsSmall.getSmallDustByName("Ruby", 6),
                ItemDustsSmall.getSmallDustByName("redGarnet", 2),
                IC2Items.getItem("cell"),
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherRuby")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    BlockOre.getOreByName("ruby"),
                    IC2Items.getItem("waterCell"),
                    null,
                    ItemGems.getGemByName("ruby", 2),
                    ItemDustsSmall.getSmallDustByName("Ruby", 12),
                    ItemDustsSmall.getSmallDustByName("redGarnet", 2),
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }

        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sapphire"),
                IC2Items.getItem("waterCell"),
                null,
                ItemGems.getGemByName("sapphire", 1),
                ItemDustsSmall.getSmallDustByName("Sapphire", 6),
                ItemDustsSmall.getSmallDustByName("peridot", 2),
                IC2Items.getItem("cell"),
                100,
                128));
        if (OreUtil.doesOreExistAndValid("orenetherSapphire")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherSapphire"),
                    IC2Items.getItem("waterCell"),
                    null,
                    ItemGems.getGemByName("sapphire", 2),
                    ItemDustsSmall.getSmallDustByName("sapphire", 12),
                    ItemDustsSmall.getSmallDustByName("peridot", 2),
                    IC2Items.getItem("cell"),
                    100,
                    128));
        }

        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("bauxite"),
                IC2Items.getItem("waterCell"),
                null,
                ItemDusts.getDustByName("bauxite", 4),
                ItemDusts.getDustByName("aluminium"),
                null,
                IC2Items.getItem("cell"),
                100,
                128));

        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("cinnabar"),
                IC2Items.getItem("waterCell"),
                null,
                ItemDusts.getDustByName("cinnabar", 5),
                ItemDustsSmall.getSmallDustByName("redstone", 2),
                ItemDustsSmall.getSmallDustByName("glowstone", 1),
                IC2Items.getItem("cell"),
                100,
                128));

        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sphalerite"),
                IC2Items.getItem("waterCell"),
                null,
                ItemDusts.getDustByName("sphalerite", 5),
                ItemDusts.getDustByName("Zinc", 1),
                ItemDustsSmall.getSmallDustByName("YellowGarnet", 1),
                IC2Items.getItem("cell"),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sphalerite"),
                ItemCells.getCellByName("sodiumPersulfate", 1),
                null,
                ItemDusts.getDustByName("sphalerite", 5),
                ItemDusts.getDustByName("zinc", 3),
                ItemDustsSmall.getSmallDustByName("YellowGarnet", 1),
                IC2Items.getItem("cell"),
                100,
                128));

        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("tungsten"),
                IC2Items.getItem("waterCell"),
                null,
                ItemDusts.getDustByName("tungsten", 2),
                ItemDustsSmall.getSmallDustByName("iron", 3),
                ItemDustsSmall.getSmallDustByName("manganese", 3),
                IC2Items.getItem("cell"),
                100,
                128));

        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("olivine"),
                IC2Items.getItem("waterCell"),
                null,
                ItemGems.getGemByName("olivine", 1),
                ItemDustsSmall.getSmallDustByName("olivine", 6),
                ItemDustsSmall.getSmallDustByName("emerald", 2),
                IC2Items.getItem("cell"),
                100,
                128));
        ItemStack sulfurDust1 = IC2Items.getItem("sulfurDust")
            .copy();
        sulfurDust1.stackSize = 2;
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("pyrite"),
                null,
                new FluidStack(WATER, 1000),
                ItemDusts.getDustByName("pyrite", 5),
                sulfurDust1,
                null,
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("pyrite"),
                IC2Items.getItem("waterCell"),
                null,
                ItemDusts.getDustByName("pyrite", 5),
                sulfurDust1,
                null,
                IC2Items.getItem("cell"),
                100,
                128));

        // Chemical Reactor
        RecipeHandler.addRecipe(
            new ChemicalReactorRecipe(
                ItemCells.getCellByName("sodiumSulfide", 1),
                IC2Items.getItem("airCell"),
                ItemCells.getCellByName("sodiumPersulfate", 2),
                4000,
                32));
        RecipeHandler.addRecipe(
            new ChemicalReactorRecipe(
                ItemCells.getCellByName("nitrocarbon", 3),
                new ItemStack(
                    IC2Items.getItem("waterCell")
                        .getItem(),
                    3,
                    1),
                ItemCells.getCellByName("glyceryl", 6),
                1750,
                32));
        ItemStack waterCells = IC2Items.getItem("waterCell")
            .copy();
        waterCells.stackSize = 2;
        RecipeHandler.addRecipe(
            new ChemicalReactorRecipe(
                ItemCells.getCellByName("sulfur", 1),
                waterCells,
                ItemCells.getCellByName("sulfuricAcid", 3),
                1150,
                32));
        ItemStack waterCells2 = IC2Items.getItem("waterCell")
            .copy();
        waterCells2.stackSize = 5;
        RecipeHandler.addRecipe(
            new ChemicalReactorRecipe(
                ItemCells.getCellByName("hydrogen", 4),
                IC2Items.getItem("airCell"),
                waterCells2,
                10,
                30));
        RecipeHandler.addRecipe(
            new ChemicalReactorRecipe(
                ItemCells.getCellByName("nitrogen", 1),
                IC2Items.getItem("airCell"),
                ItemCells.getCellByName("nitrogenDioxide", 2),
                1250,
                32));

        // IndustrialElectrolyzer

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("pyrite", 2),
                null,
                ItemDusts.getDustByName("iron"),
                IC2Items.getItem("sulfurDust"),
                null,
                null,
                150,
                100));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                new ItemStack(Items.blaze_powder, 4),
                null,
                ItemDusts.getDustByName("darkAshes", 1),
                IC2Items.getItem("sulfurDust"),
                null,
                null,
                1240,
                5));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                new ItemStack(
                    IC2Items.getItem("electrolyzedWaterCell")
                        .getItem(),
                    6,
                    8),
                null,
                ItemCells.getCellByName("hydrogen", 4),
                IC2Items.getItem("airCell"),
                IC2Items.getItem("cell"),
                null,
                1000,
                30));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                IC2Items.getItem("waterCell"),
                null,
                IC2Items.getItem("electrolyzedWaterCell"),
                null,
                null,
                null,
                128,
                128));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                new ItemStack(Items.water_bucket),
                IC2Items.getItem("cell"),
                IC2Items.getItem("electrolyzedWaterCell"),
                new ItemStack(Items.bucket),
                null,
                null,
                128,
                128,
                false));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("cinnabar", 2),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("mercury"),
                IC2Items.getItem("sulfurDust"),
                null,
                null,
                100,
                128));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("enderPearl", 16),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    16),
                ItemCells.getCellByName("nitrogen", 5),
                ItemCells.getCellByName("beryllium"),
                ItemCells.getCellByName("potassium", 4),
                ItemCells.getCellByName("chlorine", 6),
                1300,
                50));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                new ItemStack(Items.sugar, 32),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    7),
                ItemCells.getCellByName("carbon", 2),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    5,
                    1),
                null,
                null,
                210,
                32,
                false));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("coal"),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    2),
                ItemCells.getCellByName("carbon", 2),
                null,
                null,
                null,
                40,
                50,
                false));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("ashes", 2),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("carbon"),
                null,
                null,
                null,
                25,
                50,
                false));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("charcoal"),
                IC2Items.getItem("cell"),
                ItemCells.getCellByName("carbon"),
                null,
                null,
                null,
                20,
                50,
                false));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("calcite", 10),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    7),
                ItemCells.getCellByName("calcium", 2),
                ItemCells.getCellByName("carbon", 2),
                new ItemStack(
                    IC2Items.getItem("airCell")
                        .getItem(),
                    3,
                    5),
                null,
                700,
                80));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("obsidian", 4),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    3),
                ItemDustsSmall.getSmallDustByName("magnesium", 2),
                ItemDustsSmall.getSmallDustByName("iron", 2),
                ItemCells.getCellByName("silicon"),
                new ItemStack(
                    IC2Items.getItem("airCell")
                        .getItem(),
                    2,
                    5),
                500,
                5));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("clay", 8),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    5),
                ItemCells.getCellByName("lithium"),
                ItemCells.getCellByName("silicon", 2),
                ItemDusts.getDustByName("aluminium", 2),
                ItemCells.getCellByName("sodium", 2),
                200,
                50));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("lazurite", 29),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    11),
                ItemDusts.getDustByName("aluminium", 3),
                ItemCells.getCellByName("silicon", 3),
                ItemCells.getCellByName("calcium", 4),
                ItemCells.getCellByName("sodium", 4),
                1475,
                100,
                false));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("bauxite", 12),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    8),
                ItemDusts.getDustByName("aluminium", 8),
                ItemDustsSmall.getSmallDustByName("titanium", 2),
                ItemCells.getCellByName("hydrogen", 5),
                new ItemStack(
                    IC2Items.getItem("airCell")
                        .getItem(),
                    3,
                    5),
                2000,
                128));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemCells.getCellByName("sulfuricAcid", 7),
                null,
                ItemCells.getCellByName("hydrogen", 2),
                ItemCells.getCellByName("sulfur"),
                new ItemStack(
                    IC2Items.getItem("airCell")
                        .getItem(),
                    2,
                    5),
                IC2Items.getItem("cell"),
                40,
                100));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("sodalite", 23),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    8),
                ItemCells.getCellByName("sodium", 4),
                ItemDusts.getDustByName("aluminium", 3),
                ItemCells.getCellByName("silicon", 3),
                ItemCells.getCellByName("chlorine"),
                1350,
                90,
                false));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("flint", 8),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    2),
                ItemCells.getCellByName("silicon"),
                IC2Items.getItem("airCell"),
                null,
                null,
                1000,
                5));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("ruby", 9),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    3),
                ItemDusts.getDustByName("aluminium", 2),
                new ItemStack(
                    IC2Items.getItem("airCell")
                        .getItem(),
                    3,
                    5),
                ItemDusts.getDustByName("chromium"),
                null,
                500,
                50));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("sapphire", 8),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    3),
                ItemDusts.getDustByName("aluminium", 2),
                new ItemStack(
                    IC2Items.getItem("airCell")
                        .getItem(),
                    3,
                    5),
                null,
                null,
                400,
                50));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemCells.getCellByName("nitrogenDioxide", 3),
                null,
                ItemCells.getCellByName("nitrogen", 1),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    1,
                    5),
                null,
                IC2Items.getItem("cell"),
                160,
                60));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("peridot", 8),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    3),
                ItemDusts.getDustByName("aluminium", 2),
                new ItemStack(
                    IC2Items.getItem("airCell")
                        .getItem(),
                    1,
                    5),
                null,
                null,
                400,
                50));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("emerald", 29),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    18,
                    0),
                ItemCells.getCellByName("beryllium", 3),
                ItemDusts.getDustByName("aluminium", 2),
                ItemCells.getCellByName("silicon", 6),
                new ItemStack(
                    IC2Items.getItem("airCell")
                        .getItem(),
                    9,
                    5),
                600,
                50));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                new ItemStack(
                    IC2Items.getItem("silicondioxideDust")
                        .getItem(),
                    3,
                    0),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    2,
                    0),
                ItemCells.getCellByName("silicon", 1),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    1,
                    5),
                null,
                null,
                60,
                60));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                new ItemStack(Items.dye, 3, 15),
                IC2Items.getItem("cell"),
                null,
                ItemCells.getCellByName("calcium", 1),
                null,
                null,
                24,
                106,
                false));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("olivine", 9),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    4,
                    0),
                ItemDusts.getDustByName("magnesium", 2),
                ItemDusts.getDustByName("iron", 2),
                ItemCells.getCellByName("silicon"),
                new ItemStack(
                    IC2Items.getItem("airCell")
                        .getItem(),
                    2,
                    5),
                600,
                60));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("saltpeter", 10),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    7),
                ItemCells.getCellByName("potassium", 2),
                ItemCells.getCellByName("nitrogen", 2),
                new ItemStack(
                    IC2Items.getItem("airCell")
                        .getItem(),
                    3,
                    5),
                null,
                50,
                110));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("pyrope", 20),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    9,
                    0),
                ItemDusts.getDustByName("aluminium", 2),
                ItemDusts.getDustByName("magnesium", 3),
                ItemCells.getCellByName("silicon", 3),
                new ItemStack(
                    IC2Items.getItem("airCell")
                        .getItem(),
                    6,
                    5),
                1790,
                50));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                new ItemStack(Blocks.sand, 16),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    2,
                    0),
                ItemCells.getCellByName("silicon", 1),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    1,
                    5),
                null,
                null,
                1000,
                25));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("almandine", 20),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    9,
                    0),
                ItemDusts.getDustByName("aluminium", 2),
                ItemDusts.getDustByName("iron", 3),
                ItemCells.getCellByName("silicon", 3),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    6,
                    5),
                1640,
                50));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("spessartine", 20),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    9,
                    0),
                ItemDusts.getDustByName("aluminium", 2),
                ItemDusts.getDustByName("manganese", 3),
                ItemCells.getCellByName("silicon", 3),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    6,
                    5),
                1810,
                50));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("andradite", 20),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    12,
                    0),
                ItemCells.getCellByName("calcium", 3),
                ItemDusts.getDustByName("iron", 2),
                ItemCells.getCellByName("silicon", 3),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    6,
                    5),
                1280,
                50));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("grossular", 20),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    12,
                    0),
                ItemCells.getCellByName("calcium", 3),
                ItemDusts.getDustByName("aluminium", 2),
                ItemCells.getCellByName("silicon", 3),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    6,
                    5),
                2050,
                50));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("Uvarovite", 20),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    12,
                    0),
                ItemCells.getCellByName("calcium", 3),
                ItemDusts.getDustByName("chromium", 2),
                ItemCells.getCellByName("silicon", 3),
                new ItemStack(
                    IC2Items.getItem("airCell")
                        .getItem(),
                    6,
                    5),
                2200,
                50));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("darkAshes"),
                new ItemStack(
                    IC2Items.getItem("cell")
                        .getItem(),
                    2,
                    0),
                ItemCells.getCellByName("carbon", 2),
                null,
                null,
                null,
                20,
                30,
                false));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemCells.getCellByName("methane", 5),
                null,
                ItemCells.getCellByName("hydrogen", 4),
                ItemCells.getCellByName("carbon"),
                null,
                null,
                150,
                50));

        if (OreUtil.doesOreExistAndValid("dustSalt")) {
            ItemStack salt = OreDictionary.getOres("dustSalt")
                .get(0);
            salt.stackSize = 2;
            RecipeHandler.addRecipe(
                new IndustrialElectrolyzerRecipe(
                    salt,
                    new ItemStack(
                        IC2Items.getItem("cell")
                            .getItem(),
                        2,
                        0),
                    ItemCells.getCellByName("sodium"),
                    ItemCells.getCellByName("chlorine"),
                    null,
                    null,
                    40,
                    60));
        }
    }

    static void addScrapboxDrops() {
        try {
            Field drops = Class.forName("ic2.core.item.ItemScrapbox$ScrapboxRecipeManager")
                .getDeclaredField("drops");
            drops.setAccessible(true);
            drops.set(Recipes.scrapboxDrops, new ArrayList<>());
            Core.logHelper.info("Removed Scrapbox Drops.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("scrap"), 200.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.wooden_hoe), 9.5f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.stick), 9.5f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.rotten_flesh), 9.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Blocks.dirt), 5.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.paper), 5.0f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("netherrack"), 4.0f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("flint"), 4.0f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("sawDust"), 3.8f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Blocks.grass), 3.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Blocks.gravel), 3.0f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("suBattery"), 2.7f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("charcoal"), 2.5f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Blocks.netherrack), 2.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.wooden_axe), 2.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.wooden_sword), 2.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.wooden_shovel), 2.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.wooden_pickaxe), 2.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.sign), 2.0f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("cell"), 2.0f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("insulatedCopperCableItem"), 2.0f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("saltpeter"), 2.0f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("lazurite"), 2.0f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("pyrite"), 2.0f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("calcite"), 2.0f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("sodalite"), 2.0f);
        Recipes.scrapboxDrops.addDrop(ItemParts.getPartByName("basicCircuitBoard"), 1.8f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("obsidianDust"), 1.5f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("sulfurDust"), 1.5f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("tinDust"), 1.2f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("copperDust"), 1.2f);
        Recipes.scrapboxDrops.addDrop(ItemParts.getPartByName("advancedCircuitParts"), 1.2f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Blocks.soul_sand), 1.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.bone), 1.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.leather), 1.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.feather), 1.0f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("filledTinCan"), 1.0f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("waterCell"), 1.0f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("ironDust"), 1.0f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("goldDust"), 1.0f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.redstone), 0.9f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.glowstone_dust), 0.8f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("rubber"), 0.8f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("coalDust"), 0.8f);
        Recipes.scrapboxDrops.addDrop(ItemParts.getPartByName("machineParts"), 0.8f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("plantBall"), 0.7f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.slime_ball), 0.6f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Blocks.pumpkin), 0.5f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.apple), 0.5f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.bread), 0.5f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("leadDust"), 0.5f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("silverDust"), 0.5f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("electrum"), 0.5f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("bauxite"), 0.5f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("aluminium"), 0.5f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("nickel"), 0.5f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("zinc"), 0.5f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("brass"), 0.5f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("steel"), 0.5f);
        Recipes.scrapboxDrops.addDrop(ItemGems.getGemByName("redGarnet"), 0.5f);
        Recipes.scrapboxDrops.addDrop(ItemGems.getGemByName("yellowGarnet"), 0.5f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.cooked_porkchop), 0.4f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.cooked_beef), 0.4f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.cooked_chicken), 0.4f);
        Recipes.scrapboxDrops.addDrop(IC2Items.getItem("insulatedGoldCableItem"), 0.4f);
        Recipes.scrapboxDrops.addDrop(ItemParts.getPartByName("advancedCircuitBoard"), 0.4f);
        Recipes.scrapboxDrops.addDrop(ItemCells.getCellByName("silicon"), 0.2f);
        Recipes.scrapboxDrops.addDrop(ItemParts.getPartByName("processorCircuitBoard"), 0.2f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.cake), 0.1f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.blaze_rod), 0.1f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.ender_pearl), 0.08f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.diamond), 0.05f);
        Recipes.scrapboxDrops.addDrop(new ItemStack(Items.emerald), 0.05f);
        Recipes.scrapboxDrops.addDrop(ItemGems.getGemByName("olivine"), 0.05f);
        Recipes.scrapboxDrops.addDrop(ItemGems.getGemByName("ruby"), 0.05f);
        Recipes.scrapboxDrops.addDrop(ItemGems.getGemByName("sapphire"), 0.05f);
        Recipes.scrapboxDrops.addDrop(ItemGems.getGemByName("peridot"), 0.05f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("platinum"), 0.03f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("tungsten"), 0.03f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("chromium"), 0.03f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("titanium"), 0.03f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("magnesium"), 0.03f);
        Recipes.scrapboxDrops.addDrop(ItemDusts.getDustByName("endstone"), 0.03f);
        Core.logHelper.info("Scrapbox Drops (re)added.");
    }

    static void removeIc2Recipes() {
        if (ConfigTechReborn.ExpensiveMacerator) {
            RecipeRemover.removeAnyRecipe(IC2Items.getItem("macerator"));
        }
        if (ConfigTechReborn.ExpensiveDrill) {
            RecipeRemover.removeAnyRecipe(IC2Items.getItem("miningDrill"));
        }
        if (ConfigTechReborn.ExpensiveDiamondDrill) {
            RecipeRemover.removeAnyRecipe(IC2Items.getItem("diamondDrill"));
        }
        if (ConfigTechReborn.ExpensiveSolar) {
            RecipeRemover.removeAnyRecipe(IC2Items.getItem("solarPanel"));
        }
        /*
         * if (ConfigTechReborn.ExpensiveWatermill) {
         * RecipeRemover.removeAnyRecipe(IC2Items.getItem("waterMill"));
         * }
         * if (ConfigTechReborn.ExpensiveWindmill) {
         * RecipeRemover.removeAnyRecipe(IC2Items.getItem("windMill"));
         * }
         */
        RecipeRemover.removeAnyRecipe(IC2Items.getItem("iridiumPlate"));
        RecipeRemover.removeAnyRecipe(IC2Items.getItem("nanoSaber"));
        RecipeRemover.removeAnyRecipe(IC2Items.getItem("miningLaser"));
        RecipeRemover.removeAnyRecipe(IC2Items.getItem("mixedMetalIngot"));

        Core.logHelper.info("IC2 Recipes Removed");
    }

    static void addShappedIc2Recipes() {

        if (ConfigTechReborn.ExpensiveMacerator) {
            CraftingHelper.addShapedOreRecipe(
                IC2Items.getItem("macerator"),
                "FDF",
                "DMD",
                "FCF",
                'F',
                "itemFlint",
                'D',
                "gemDiamond",
                'M',
                "machineBasic",
                'C',
                "circuitAdvanced");
            CraftingHelper.addShapedOreRecipe(
                IC2Items.getItem("macerator"),
                "FGF",
                "CMC",
                "FCF",
                'F',
                "itemFlint",
                'G',
                "craftingGrinder",
                'M',
                "machineBasic",
                'C',
                "circuitAdvanced");
        }

        if (ConfigTechReborn.ExpensiveDrill) CraftingHelper.addShapedOreRecipe(
            IC2Items.getItem("miningDrill"),
            " S ",
            "SCS",
            "SBS",
            'S',
            "plateSteel",
            'B',
            "battery10k",
            'C',
            "circuitBasic");

        if (ConfigTechReborn.ExpensiveDiamondDrill) CraftingHelper.addShapedOreRecipe(
            IC2Items.getItem("diamondDrill"),
            " D ",
            "DBD",
            "TCT",
            'D',
            "gemDiamond",
            'T',
            "plateTitanium",
            'B',
            any(IC2Items.getItem("miningDrill")),
            'C',
            "circuitAdvanced");

        if (ConfigTechReborn.ExpensiveSolar) CraftingHelper.addShapedOreRecipe(
            IC2Items.getItem("solarPanel"),
            "PPP",
            "SZS",
            "CGC",
            'P',
            "paneGlass",
            'S',
            "plateSilicon",
            'Z',
            IC2Items.getItem("carbonPlate"),
            'G',
            IC2Items.getItem("generator"),
            'C',
            "circuitBasic");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.lithiumBatpack, 1, OreDictionary.WILDCARD_VALUE),
            "BCB",
            "BPB",
            "B B",
            'B',
            new ItemStack(ModItems.lithiumBattery, 1, OreDictionary.WILDCARD_VALUE),
            'P',
            "plateAluminium",
            'C',
            "circuitAdvanced");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.lithiumBattery, 1, OreDictionary.WILDCARD_VALUE),
            " C ",
            "PFP",
            "PFP",
            'F',
            ItemCells.getCellByName("lithium"),
            'P',
            "plateAluminium",
            'C',
            IC2Items.getItem("insulatedGoldCableItem"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.lapotronpack, 1, OreDictionary.WILDCARD_VALUE),
            "FOF",
            "SPS",
            "FIF",
            'F',
            "circuitMaster",
            'O',
            new ItemStack(ModItems.lapotronicOrb),
            'S',
            ItemParts.getPartByName("superConductor"),
            'I',
            IC2Items.getItem("iridiumPlate"),
            'P',
            new ItemStack(ModItems.lapotronpack));

        CraftingHelper.addShapedOreRecipe(
            IC2Items.getItem("mfeUnit"),
            "CBC",
            "BMB",
            "CBC",
            'C',
            IC2Items.getItem("insulatedGoldCableItem"),
            'B',
            "battery1M",
            'M',
            "machineBasic");

        CraftingHelper.addShapedOreRecipe(
            IC2Items.getItem("generator"),
            "B",
            "M",
            "F",
            'B',
            "battery10k",
            'M',
            "machineBasic",
            'F',
            Blocks.furnace);

        CraftingHelper.addShapedOreRecipe(
            IC2Items.getItem("generator"),
            " B ",
            "PPP",
            " F ",
            'B',
            "battery10k",
            'P',
            "plateAluminium",
            'F',
            IC2Items.getItem("ironFurnace"));

        CraftingHelper.addShapedOreRecipe(
            IC2Items.getItem("generator"),
            " B ",
            "PPP",
            " F ",
            'B',
            "battery10k",
            'P',
            "plateInvar",
            'F',
            IC2Items.getItem("ironFurnace"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(
                IC2Items.getItem("industrialTnt")
                    .getItem(),
                5),
            "FFF",
            "TTT",
            "FFF",
            'F',
            "dustFlint",
            'T',
            Blocks.tnt);

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(
                IC2Items.getItem("luminator")
                    .getItem(),
                16),
            "ITI",
            "GCG",
            "GGG",
            'I',
            IC2Items.getItem("casingiron"),
            'T',
            IC2Items.getItem("casingtin"),
            'G',
            "blockGlass",
            'C',
            ItemCells.getCellByName("helium"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(
                IC2Items.getItem("luminator")
                    .getItem(),
                16),
            "ITI",
            "GCG",
            "GGG",
            'I',
            IC2Items.getItem("casingiron"),
            'T',
            IC2Items.getItem("casingtin"),
            'G',
            "blockGlass",
            'C',
            ItemCells.getCellByName("mercury"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(
                IC2Items.getItem("transformerUpgrade")
                    .getItem(),
                2),
            " L ",
            "CEC",
            'L',
            ModItems.coolantHe60k,
            'C',
            IC2Items.getItem("insulatedCopperCableItem"),
            'E',
            "circuitBasic");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(
                IC2Items.getItem("transformerUpgrade")
                    .getItem(),
                2),
            " L ",
            "CEC",
            'L',
            ModItems.coolantNaK60k,
            'C',
            IC2Items.getItem("insulatedCopperCableItem"),
            'E',
            "circuitBasic");

        CraftingHelper.addShapedOreRecipe(
            IC2Items.getItem("miningLaser"),
            "RHB",
            "TTC",
            " AA",
            'R',
            "gemRuby",
            'H',
            ModItems.coolantHe360k,
            'B',
            "battery1M",
            'T',
            "plateTitanium",
            'C',
            "circuitAdvanced",
            'A',
            IC2Items.getItem("advancedAlloy"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(
                IC2Items.getItem("suBattery")
                    .getItem(),
                8),
            "C",
            "R",
            "D",
            'C',
            IC2Items.getItem("insulatedCopperCableItem"),
            'R',
            "dustRedstone",
            'D',
            IC2Items.getItem("hydratedCoalDust"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(
                IC2Items.getItem("suBattery")
                    .getItem(),
                32),
            "C",
            "R",
            "M",
            'C',
            IC2Items.getItem("insulatedCopperCableItem"),
            'R',
            "dustRedstone",
            'M',
            ItemCells.getCellByName("mercury"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(
                IC2Items.getItem("suBattery")
                    .getItem(),
                32),
            "C",
            "L",
            "S",
            'C',
            IC2Items.getItem("insulatedCopperCableItem"),
            'L',
            "dustLead",
            'S',
            ItemCells.getCellByName("sulfur"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(
                IC2Items.getItem("reBattery")
                    .getItem(),
                2),
            " C ",
            "TLT",
            "TST",
            'C',
            IC2Items.getItem("insulatedTinCableItem"),
            'T',
            IC2Items.getItem("casingtin"),
            'L',
            "dustLead",
            'S',
            ItemCells.getCellByName("sulfur"));

        CraftingHelper.addShapedOreRecipe(
            IC2Items.getItem("lapotronCrystal"),
            "LCL",
            "LSL",
            "LCL",
            'L',
            "dustLapis",
            'C',
            "circuitAdvanced",
            'S',
            "gemSapphire");

        CraftingHelper.addShapedOreRecipe(
            IC2Items.getItem("lapotronCrystal"),
            "LCL",
            "LSL",
            "LCL",
            'L',
            "dustLazurite",
            'C',
            "circuitAdvanced",
            'S',
            "gemSapphire");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(
                IC2Items.getItem("energiumDust")
                    .getItem(),
                9,
                2),
            "XOX",
            "OXO",
            "XOX",
            'X',
            "dustRedstone",
            'O',
            "dustRuby");

        for (Pair<String, Integer> top : new Pair[] { Pair.of("Iron", 1), Pair.of("Nickel", 1), Pair.of("Invar", 2),
            Pair.of("Steel", 2), Pair.of("Titanium", 3), Pair.of("Tungsten", 3), Pair.of("Tungstensteel", 5) }) {
            for (String mid : new String[] { "Bronze", "Brass" }) {
                for (Pair<String, Integer> bot : new Pair[] { Pair.of("Tin", 0), Pair.of("Zinc", 0),
                    Pair.of("Aluminium", 1) }) {
                    CraftingHelper.addShapedOreRecipe(
                        new ItemStack(
                            IC2Items.getItem("mixedMetalIngot")
                                .getItem(),
                            top.getRight() + bot.getRight(),
                            4),
                        "T",
                        "M",
                        "B",
                        'T',
                        "plate" + top.getLeft(),
                        'M',
                        "plate" + mid,
                        'B',
                        "plate" + bot.getLeft());
                }
            }
        }

        Recipes.compressor
            .addRecipe(new RecipeInputOreDict("dustLazurite", 8), null, ItemParts.getPartByName("lazuriteChunk"));
        Recipes.compressor.addRecipe(new RecipeInputOreDict("dustWood", 8), null, ItemPlates.getPlateByName("wood"));
        try {
            Recipes.compressor.addRecipe(
                new RecipeInputItemStack(IC2Items.getItem("iridiumOre")),
                null,
                ItemIngots.getIngotByName("iridium"));
        } catch (Exception e) {}
        Recipes.compressor.addRecipe(new RecipeInputOreDict("dustOsmium"), null, ItemIngots.getIngotByName("osmium"));
        Recipes.compressor.addRecipe(new RecipeInputOreDict("dustThorium"), null, ItemIngots.getIngotByName("thorium"));
        Recipes.compressor
            .addRecipe(new RecipeInputOreDict("dustPlutonium"), null, ItemIngots.getIngotByName("plutonium"));

        Recipes.extractor.addRecipe(
            new RecipeInputOreDict("slimeball"),
            null,
            new ItemStack(
                IC2Items.getItem("rubber")
                    .getItem(),
                2));

        Core.logHelper.info("Added Expensive IC2 Recipes");
    }

    static void addTRMaceratorRecipes() {
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("oreBauxite"), null, ItemCrushedOre.getCrushedOreByName("Bauxite", 2));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("oreCinnabar"), null, ItemCrushedOre.getCrushedOreByName("Cinnabar", 3));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("oreGalena"), null, ItemCrushedOre.getCrushedOreByName("Galena", 2));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("oreIridium"), null, ItemCrushedOre.getCrushedOreByName("Iridium", 2));
        Recipes.macerator.addRecipe(
            new RecipeInputOreDict("oreSphalerite"),
            null,
            ItemCrushedOre.getCrushedOreByName("Sphalerite", 4));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("orePyrite"), null, ItemCrushedOre.getCrushedOreByName("pyrite", 5));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("oreSodalite"), null, ItemDusts.getDustByName("sodalite", 12));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("oreTungsten"), null, ItemCrushedOre.getCrushedOreByName("tungsten", 2));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("orePlatinum"), null, ItemCrushedOre.getCrushedOreByName("platinum", 2));
        Recipes.macerator.addRecipe(
            new RecipeInputOreDict("oreCoal"),
            null,
            new ItemStack(
                IC2Items.getItem("coalDust")
                    .getItem(),
                2,
                2));

        Recipes.macerator.addRecipe(new RecipeInputOreDict("pearlEnder"), null, ItemDusts.getDustByName("enderPearl"));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("pearlEnderEye"), null, ItemDusts.getDustByName("enderEye", 2));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("gemRuby"), null, ItemDusts.getDustByName("ruby"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("gemSapphire"), null, ItemDusts.getDustByName("sapphire"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("gemPeridot"), null, ItemDusts.getDustByName("peridot"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("gemEmerald"), null, ItemDusts.getDustByName("emerald"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("gemOlivine"), null, ItemDusts.getDustByName("olivine"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("charcoal"), null, ItemDusts.getDustByName("charcoal"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("gemRedGarnet"), null, ItemDusts.getDustByName("redGarnet"));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("gemYellowGarnet"), null, ItemDusts.getDustByName("yellowGarnet"));

        Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotUranium"), null, ItemDusts.getDustByName("uranium"));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("ingotAluminium"), null, ItemDusts.getDustByName("aluminium"));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("plateAluminium"), null, ItemDusts.getDustByName("aluminium"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotTitanium"), null, ItemDusts.getDustByName("titanium"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("plateTitanium"), null, ItemDusts.getDustByName("titanium"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotChromium"), null, ItemDusts.getDustByName("chromium"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("plateChromium"), null, ItemDusts.getDustByName("chromium"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotElectrum"), null, ItemDusts.getDustByName("electrum"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("plateElectrum"), null, ItemDusts.getDustByName("electrum"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotTungsten"), null, ItemDusts.getDustByName("tungsten"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("plateTungsten"), null, ItemDusts.getDustByName("tungsten"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotZinc"), null, ItemDusts.getDustByName("zinc"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("plateZinc"), null, ItemDusts.getDustByName("zinc"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotBrass"), null, ItemDusts.getDustByName("brass"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("plateBrass"), null, ItemDusts.getDustByName("brass"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("plateSteel"), null, ItemDusts.getDustByName("steel"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotPlatinum"), null, ItemDusts.getDustByName("platinum"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("platePlatinum"), null, ItemDusts.getDustByName("platinum"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotNickel"), null, ItemDusts.getDustByName("nickel"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("plateNickel"), null, ItemDusts.getDustByName("nickel"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotInvar"), null, ItemDusts.getDustByName("invar"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("plateInvar"), null, ItemDusts.getDustByName("invar"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotOsmium"), null, ItemDusts.getDustByName("osmium"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("plateOsmium"), null, ItemDusts.getDustByName("osmium"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotThorium"), null, ItemDusts.getDustByName("thorium"));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("ingotPlutonium"), null, ItemDusts.getDustByName("plutonium"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("plateSilver"), null, ItemDusts.getDustByName("silver"));

        Recipes.macerator.addRecipe(
            new RecipeInputItemStack(new ItemStack(Blocks.netherrack)),
            null,
            ItemDusts.getDustByName("netherrack"));
        Recipes.macerator.addRecipe(
            new RecipeInputItemStack(new ItemStack(Blocks.end_stone)),
            null,
            ItemDusts.getDustByName("endstone"));
        Recipes.macerator.addRecipe(
            new RecipeInputItemStack(new ItemStack(Blocks.enchanting_table)),
            null,
            ItemDusts.getDustByName("diamond", 2));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("blockMarble"), null, ItemDusts.getDustByName("marble"));
        Recipes.macerator.addRecipe(
            new RecipeInputItemStack(IC2Items.getItem("basaltBlock")),
            null,
            ItemDusts.getDustByName("basalt"));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("plankWood"), null, ItemDusts.getDustByName("sawDust"));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("itemFlint"), null, ItemDustsSmall.getSmallDustByName("flint", 2));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("stickWood"), null, ItemDustsSmall.getSmallDustByName("sawDust", 2));
        Recipes.macerator.addRecipe(
            new RecipeInputItemStack(new ItemStack(Blocks.redstone_torch)),
            null,
            ItemDustsSmall.getSmallDustByName("sawDust", 2));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("slabWood"), null, ItemDustsSmall.getSmallDustByName("sawDust", 2));
        Recipes.macerator
            .addRecipe(new RecipeInputOreDict("barsIron"), null, ItemDustsSmall.getSmallDustByName("iron", 3));
        Recipes.macerator.addRecipe(
            new RecipeInputItemStack(
                new ItemStack(
                    IC2Items.getItem("ironFence")
                        .getItem(),
                    2)),
            null,
            ItemDustsSmall.getSmallDustByName("iron", 3));
        Recipes.macerator.addRecipe(
            new RecipeInputItemStack(IC2Items.getItem("cell")),
            null,
            ItemDustsSmall.getSmallDustByName("tin", 4));

        if (Loader.isModLoaded("ExtrabiomesXL")) {
            Recipes.macerator.addRecipe(
                new RecipeInputItemStack(any(GameRegistry.findItemStack("ExtrabiomesXL", "terrain_blocks1", 1))),
                null,
                ItemDusts.getDustByName("redrock"));
        }
        if (Loader.isModLoaded("ProjRed|Exploration")) {
            Recipes.macerator.addRecipe(
                new RecipeInputItemStack(
                    new ItemStack(GameRegistry.findItem("ProjRed|Exploration", "projectred.exploration.stone"), 1, 1)),
                null,
                ItemDusts.getDustByName("marble"));
            Recipes.macerator.addRecipe(
                new RecipeInputItemStack(
                    new ItemStack(GameRegistry.findItem("ProjRed|Exploration", "projectred.exploration.stone"), 1, 2)),
                null,
                ItemDusts.getDustByName("basalt"));
            Recipes.macerator.addRecipe(
                new RecipeInputItemStack(
                    new ItemStack(GameRegistry.findItem("ProjRed|Exploration", "projectred.exploration.stone"), 1, 3)),
                null,
                ItemDusts.getDustByName("basalt"));
            Recipes.macerator.addRecipe(
                new RecipeInputItemStack(
                    new ItemStack(GameRegistry.findItem("ProjRed|Exploration", "projectred.exploration.stone"), 1, 4)),
                null,
                ItemDusts.getDustByName("basalt"));
        }
    }

    static void addTROreWashingRecipes() {
        // Ore Washing Plant
        NBTTagCompound liquidAmount = new NBTTagCompound();
        liquidAmount.setInteger("amount", 1000);
        Recipes.oreWashing.addRecipe(
            new RecipeInputOreDict("crushedBauxite"),
            liquidAmount,
            ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Bauxite", 1),
            ItemDustsSmall.getSmallDustByName("Grossular", 2),
            IC2Items.getItem("stoneDust"));
        Recipes.oreWashing.addRecipe(
            new RecipeInputOreDict("crushedCinnabar"),
            liquidAmount,
            ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Cinnabar", 1),
            ItemDustsSmall.getSmallDustByName("Redstone", 2),
            ItemDusts.getDustByName("Netherrack"));
        Recipes.oreWashing.addRecipe(
            new RecipeInputOreDict("crushedIridium"),
            liquidAmount,
            ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Iridium", 1),
            ItemDustsSmall.getSmallDustByName("Platinum", 2),
            IC2Items.getItem("stoneDust"));
        Recipes.oreWashing.addRecipe(
            new RecipeInputOreDict("crushedPlatinum"),
            liquidAmount,
            ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Platinum", 1),
            ItemDustsSmall.getSmallDustByName("Nickel", 2),
            IC2Items.getItem("stoneDust"));
        Recipes.oreWashing.addRecipe(
            new RecipeInputOreDict("crushedPyrite"),
            liquidAmount,
            ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Pyrite", 1),
            ItemDustsSmall.getSmallDustByName("Sulfur", 2),
            ItemDusts.getDustByName("Netherrack"));
        Recipes.oreWashing.addRecipe(
            new RecipeInputOreDict("crushedSphalerite"),
            liquidAmount,
            ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Sphalerite", 1),
            ItemDustsSmall.getSmallDustByName("YellowGarnet", 2),
            ItemDusts.getDustByName("Netherrack"));
        Recipes.oreWashing.addRecipe(
            new RecipeInputOreDict("crushedTungsten"),
            liquidAmount,
            ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Tungsten", 1),
            ItemDustsSmall.getSmallDustByName("Manganese", 2),
            IC2Items.getItem("stoneDust"));
        Recipes.oreWashing.addRecipe(
            new RecipeInputOreDict("crushedGalena"),
            liquidAmount,
            ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Galena", 1),
            ItemDustsSmall.getSmallDustByName("Sulfur", 2),
            IC2Items.getItem("stoneDust"));
    }

    static void addTRThermalCentrifugeRecipes() {
        // Thermal Centrifuge

        // Heat Values
        NBTTagCompound aluminiumHeat = new NBTTagCompound();
        aluminiumHeat.setInteger("minHeat", 520);
        // NBTTagCompound arditeHeat = new NBTTagCompound();
        // arditeHeat.setInteger("minHeat", 3000);
        NBTTagCompound bauxiteHeat = new NBTTagCompound();
        bauxiteHeat.setInteger("minHeat", 360);
        // NBTTagCompound cadmiumHeat = new NBTTagCompound();
        // cadmiumHeat.setInteger("minHeat", 1500);
        NBTTagCompound cinnabarHeat = new NBTTagCompound();
        cinnabarHeat.setInteger("minHeat", 2320);
        NBTTagCompound cobaltHeat = new NBTTagCompound();
        cobaltHeat.setInteger("minHeat", 1180);
        // NBTTagCompound darkIronHeat = new NBTTagCompound();
        // darkIronHeat.setInteger("minHeat", 2500);
        // NBTTagCompound indiumHeat = new NBTTagCompound();
        // indiumHeat.setInteger("minHeat", 2000);
        NBTTagCompound iridiumHeat = new NBTTagCompound();
        iridiumHeat.setInteger("minHeat", 3840);
        NBTTagCompound nickelHeat = new NBTTagCompound();
        nickelHeat.setInteger("minHeat", 1160);
        // NBTTagCompound osmiumHeat = new NBTTagCompound();
        // osmiumHeat.setInteger("minHeat", 2000);
        NBTTagCompound platinumHeat = new NBTTagCompound();
        platinumHeat.setInteger("minHeat", 3900);
        NBTTagCompound pyriteHeat = new NBTTagCompound();
        pyriteHeat.setInteger("minHeat", 800);
        NBTTagCompound sphaleriteHeat = new NBTTagCompound();
        sphaleriteHeat.setInteger("minHeat", 960);
        NBTTagCompound tetrahedriteHeat = new NBTTagCompound();
        tetrahedriteHeat.setInteger("minHeat", 1140);
        NBTTagCompound tungstenHeat = new NBTTagCompound();
        tungstenHeat.setInteger("minHeat", 3660);
        NBTTagCompound galenaHeat = new NBTTagCompound();
        galenaHeat.setInteger("minHeat", 2380);

        Recipes.centrifuge.addRecipe(
            new RecipeInputOreDict("crushedBauxite"),
            bauxiteHeat,
            ItemDustsSmall.getSmallDustByName("Titanium", 1),
            ItemDusts.getDustByName("bauxite", 1),
            IC2Items.getItem("stoneDust"));
        Recipes.centrifuge.addRecipe(
            new RecipeInputOreDict("crushedCinnabar"),
            cinnabarHeat,
            ItemDustsSmall.getSmallDustByName("Sulfur"),
            ItemDusts.getDustByName("cinnabar", 1),
            ItemDusts.getDustByName("Netherrack"));
        Recipes.centrifuge.addRecipe(
            new RecipeInputOreDict("crushedTungsten"),
            tungstenHeat,
            ItemDustsSmall.getSmallDustByName("Manganese", 1),
            ItemDusts.getDustByName("tungsten", 1),
            IC2Items.getItem("stoneDust"));
        Recipes.centrifuge.addRecipe(
            new RecipeInputOreDict("crushedGalena"),
            galenaHeat,
            ItemDustsSmall.getSmallDustByName("Silver", 1),
            ItemDusts.getDustByName("galena", 1),
            IC2Items.getItem("stoneDust"));

        Recipes.centrifuge.addRecipe(
            new RecipeInputOreDict("crushedPurifiedBauxite"),
            bauxiteHeat,
            ItemDustsSmall.getSmallDustByName("Titanium", 1),
            ItemDusts.getDustByName("bauxite", 1));
        Recipes.centrifuge.addRecipe(
            new RecipeInputOreDict("crushedPurifiedCinnabar"),
            cinnabarHeat,
            ItemDustsSmall.getSmallDustByName("Sulfur"),
            ItemDusts.getDustByName("cinnabar", 1));
        Recipes.centrifuge.addRecipe(
            new RecipeInputOreDict("crushedPurifiedTungsten"),
            tungstenHeat,
            ItemDustsSmall.getSmallDustByName("Manganese", 1),
            ItemDusts.getDustByName("tungsten", 1));
        Recipes.centrifuge.addRecipe(
            new RecipeInputOreDict("crushedPurifiedGalena"),
            galenaHeat,
            ItemDustsSmall.getSmallDustByName("Silver", 1),
            ItemDusts.getDustByName("galena", 1));
    }

    static void addMetalFormerRecipes() {
        // Metal Former
        NBTTagCompound mode = new NBTTagCompound();
        mode.setInteger("mode", 1);
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotBrass"), mode, ItemPlates.getPlateByName("brass"));
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotChromium"), mode, ItemPlates.getPlateByName("chromium"));
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotElectrum"), mode, ItemPlates.getPlateByName("electrum"));
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotInvar"), mode, ItemPlates.getPlateByName("invar"));
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotNickel"), mode, ItemPlates.getPlateByName("nickel"));
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotOsmium"), mode, ItemPlates.getPlateByName("osmium"));
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotPlatinum"), mode, ItemPlates.getPlateByName("platinum"));
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotSilver"), mode, ItemPlates.getPlateByName("silver"));
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotTitanium"), mode, ItemPlates.getPlateByName("titanium"));
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotTungsten"), mode, ItemPlates.getPlateByName("tungsten"));
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotTungstensteel"), mode, ItemPlates.getPlateByName("tungstensteel"));
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotZinc"), mode, ItemPlates.getPlateByName("zinc"));
        Recipes.metalformerRolling
            .addRecipe(new RecipeInputOreDict("ingotAluminium"), mode, ItemPlates.getPlateByName("aluminium"));
    }

    static void addCannerRecipes() {
        Recipes.cannerBottle.addRecipe(
            new RecipeInputItemStack(IC2Items.getItem("cell")),
            new RecipeInputOreDict("dustTungsten"),
            ItemCells.getCellByName("tungsten"));
        Recipes.cannerBottle.addRecipe(
            new RecipeInputItemStack(IC2Items.getItem("cell")),
            new RecipeInputOreDict("dustCalcite"),
            ItemCells.getCellByName("calciumCarbonate"));
        Recipes.cannerBottle.addRecipe(
            new RecipeInputItemStack(IC2Items.getItem("cell")),
            new RecipeInputOreDict("dustSulfur"),
            ItemCells.getCellByName("sulfur"));
        Recipes.cannerBottle.addRecipe(
            new RecipeInputItemStack(IC2Items.getItem("fuelRod")),
            new RecipeInputOreDict("ingotThorium"),
            new ItemStack(ModItems.cellThorium1));
        Recipes.cannerBottle.addRecipe(
            new RecipeInputItemStack(IC2Items.getItem("fuelRod")),
            new RecipeInputOreDict("ingotPlutonium"),
            new ItemStack(ModItems.cellPlutonium1));
    }

    static void addUUA() {
        final ItemStack n = null;
        NBTTagCompound x5000 = new NBTTagCompound();
        NBTTagCompound x25000 = new NBTTagCompound();
        NBTTagCompound x50000 = new NBTTagCompound();
        NBTTagCompound x75000 = new NBTTagCompound();
        NBTTagCompound x100000 = new NBTTagCompound();
        NBTTagCompound x125000 = new NBTTagCompound();
        NBTTagCompound x200000 = new NBTTagCompound();
        NBTTagCompound x500000 = new NBTTagCompound();
        NBTTagCompound x1000000 = new NBTTagCompound();
        NBTTagCompound x2000000 = new NBTTagCompound();
        x5000.setInteger("amplification", 5000);
        x25000.setInteger("amplification", 25000);
        x50000.setInteger("amplification", 50000);
        x75000.setInteger("amplification", 75000);
        x100000.setInteger("amplification", 100000);
        x125000.setInteger("amplification", 125000);
        x200000.setInteger("amplification", 200000);
        x500000.setInteger("amplification", 500000);
        x1000000.setInteger("amplification", 1000000);
        x2000000.setInteger("amplification", 2000000);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustElectrotine"), x5000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustRedstone"), x5000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustManganese"), x5000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustGlowstone"), x25000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustEnderPearl"), x50000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustTungsten"), x50000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustRuby"), x50000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustSapphire"), x50000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustPeridot"), x50000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustEmerald"), x50000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustOlivine"), x50000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustEnderEye"), x75000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustPlatinum"), x100000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustDiamond"), x125000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustOsmium"), x200000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustThorium"), x500000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustUranium"), x1000000, n);
        Recipes.matterAmplifier.addRecipe(new RecipeInputOreDict("dustPlutonium"), x2000000, n);
    }

}
