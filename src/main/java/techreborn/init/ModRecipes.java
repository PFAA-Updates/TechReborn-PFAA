package techreborn.init;

import static net.minecraftforge.fluids.FluidRegistry.WATER;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.commons.lang3.tuple.Pair;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import reborncore.common.util.CraftingHelper;
import reborncore.common.util.OreUtil;
import techreborn.Core;
import techreborn.api.reactor.FusionReactorRecipe;
import techreborn.api.reactor.FusionReactorRecipeHelper;
import techreborn.api.recipe.RecipeHandler;
import techreborn.api.recipe.machines.AlloySmelterRecipe;
import techreborn.api.recipe.machines.BlastFurnaceRecipe;
import techreborn.api.recipe.machines.CentrifugeRecipe;
import techreborn.api.recipe.machines.ChemicalReactorRecipe;
import techreborn.api.recipe.machines.GrinderRecipe;
import techreborn.api.recipe.machines.IndustrialElectrolyzerRecipe;
import techreborn.api.recipe.machines.IndustrialSawmillRecipe;
import techreborn.api.recipe.machines.VacuumFreezerRecipe;
import techreborn.blocks.BlockOre;
import techreborn.blocks.BlockStorage;
import techreborn.blocks.BlockStorage2;
import techreborn.config.ConfigTechReborn;
import techreborn.items.ItemCells;
import techreborn.items.ItemDusts;
import techreborn.items.ItemDustsSmall;
import techreborn.items.ItemGems;
import techreborn.items.ItemIngots;
import techreborn.items.ItemNuggets;
import techreborn.items.ItemParts;
import techreborn.items.ItemPlates;
import techreborn.utils.RecipeUtils;

public class ModRecipes {

    public static ConfigTechReborn config;

    public static void init() {
        addShapelessRecipes();
        addGeneralShapedRecipes();
        addMachineRecipes();

        addSmeltingRecipes();
        addUUrecipes();

        addAlloySmelterRecipes();
        addIndustrialCentrifugeRecipes();
        addChemicalReactorRecipes();
        addIndustrialElectrolyzerRecipes();

        addIndustrialSawmillRecipes();
        addBlastFurnaceRecipes();
        addIndustrialGrinderRecipes();
        addReactorRecipes();
    }

    static void addReactorRecipes() {

        FusionReactorRecipeHelper.registerRecipe(
            new FusionReactorRecipe(
                ItemCells.getCellByName("tritium"),
                ItemCells.getCellByName("deuterium"),
                ItemCells.getCellByName("heliumplasma"),
                40000000,
                4096,
                128));
        FusionReactorRecipeHelper.registerRecipe(
            new FusionReactorRecipe(
                ItemCells.getCellByName("helium3"),
                ItemCells.getCellByName("deuterium"),
                ItemCells.getCellByName("heliumplasma"),
                60000000,
                2048,
                128));
        FusionReactorRecipeHelper.registerRecipe(
            new FusionReactorRecipe(
                ItemCells.getCellByName("beryllium"),
                ItemCells.getCellByName("tungsten"),
                ItemDusts.getDustByName("platinum"),
                100000000,
                -32768,
                512));
    }

    static void addGeneralShapedRecipes() {

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.Supercondensator),
            "EOE",
            "SAS",
            "EOE",
            'E',
            ItemParts.getPartByName("energyFlowCircuit"),
            'O',
            new ItemStack(ModItems.lapotronicOrb, 1, OreDictionary.WILDCARD_VALUE),
            'S',
            ItemParts.getPartByName("superconductor"),
            'A',
            ModBlocks.HighAdvancedMachineBlock);

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("diamondSawBlade"),
            "DSD",
            "S S",
            "DSD",
            'S',
            "plateSteel",
            'D',
            "dustDiamond");

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
            ItemParts.getPartByName("destructoPack"),
            "CIC",
            "IBI",
            "CIC",
            'C',
            "circuitAdvanced",
            'I',
            "plateIron",
            'B',
            new ItemStack(Items.lava_bucket));

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("destructoPack"),
            "CIC",
            "IBI",
            "CIC",
            'C',
            "circuitAdvanced",
            'I',
            "plateAluminium",
            'B',
            new ItemStack(Items.lava_bucket));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.rockCutter),
            "DT ",
            "DT ",
            "DCB",
            'D',
            "dustDiamond",
            'T',
            "plateTitanium",
            'C',
            "circuitBasic",
            'B',
            "battery10k");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.coolantHe60k),
            " T ",
            "TCT",
            " T ",
            'T',
            "ingotTin",
            'C',
            ItemCells.getCellByName("helium", 1, false));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.coolantHe180k),
            "TTT",
            "CCC",
            "TTT",
            'T',
            "ingotTin",
            'C',
            new ItemStack(ModItems.coolantHe60k));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.coolantHe360k),
            "THT",
            "TCT",
            "THT",
            'T',
            "ingotTin",
            'C',
            "plateCopper",
            'H',
            new ItemStack(ModItems.coolantHe180k));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.coolantNaK180k),
            "TTT",
            "CCC",
            "TTT",
            'T',
            "ingotTin",
            'C',
            new ItemStack(ModItems.coolantNaK60k));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModItems.coolantNaK360k),
            "THT",
            "TCT",
            "THT",
            'T',
            "ingotTin",
            'C',
            "ingotCopper",
            'H',
            new ItemStack(ModItems.coolantNaK180k));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.Aesu),
            "LLL",
            "LCL",
            "LLL",
            'L',
            new ItemStack(ModItems.lapotronicOrb, 1, OreDictionary.WILDCARD_VALUE),
            'C',
            new ItemStack(ModBlocks.ComputerCube));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.FusionControlComputer),
            "CCC",
            "PTP",
            "CCC",
            'P',
            new ItemStack(ModBlocks.ComputerCube),
            'T',
            new ItemStack(ModBlocks.FusionCoil),
            'C',
            ItemParts.getPartByName("energyFlowCircuit"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.LightningRod),
            "CAC",
            "ASA",
            "CAC",
            'A',
            "machineElite",
            'S',
            new ItemStack(ModBlocks.Supercondensator),
            'C',
            "circuitMaster");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.FusionCoil),
            "CSC",
            "NAN",
            "CRC",
            'A',
            "machineElite",
            'N',
            ItemParts.getPartByName("nichromeHeatingCoil"),
            'C',
            ItemParts.getPartByName("energyFlowCircuit"),
            'S',
            ItemParts.getPartByName("superConductor"),
            'R',
            new ItemStack(ModItems.neutronReflector));

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("dataOrb"),
            "SSS",
            "SCS",
            "SSS",
            'S',
            ItemParts.getPartByName("dataStorageCircuit"),
            'C',
            ItemParts.getPartByName("dataControlCircuit"));

        CraftingHelper.addShapedOreRecipe(
            ItemParts.getPartByName("computerMonitor"),
            "AGA",
            "RCB",
            "AYA",
            'A',
            "plateAluminum",
            'G',
            "dyeLime",
            'R',
            "dyeRed",
            'C',
            "paneGlassColorless",
            'B',
            "dyeBlue",
            'Y',
            "dustGlowstone");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.chargeBench),
            "ABA",
            "CDC",
            "AEA",
            'A',
            "circuitMaster",
            'B',
            ModBlocks.ComputerCube,
            'C',
            "chestWood",
            'D',
            new ItemStack(ModItems.lapotronicOrb, 1, OreDictionary.WILDCARD_VALUE),
            'E',
            "machineAdvanced");

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.playerDetector),
            " D ",
            "ACA",
            " D ",
            'D',
            "circuitData",
            'A',
            "circuitAdvanced",
            'C',
            ModBlocks.ComputerCube);

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.quantumTank),
            "EPE",
            "PCP",
            "EPE",
            'P',
            "platePlatinum",
            'E',
            "circuitMaster",
            'C',
            ModBlocks.quantumChest);

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.digitalChest),
            "PPP",
            "PDP",
            "PCP",
            'P',
            "plateAluminum",
            'D',
            ItemParts.getPartByName("dataOrb"),
            'C',
            ItemParts.getPartByName("computerMonitor"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.digitalChest),
            "PPP",
            "PDP",
            "PCP",
            'P',
            "plateSteel",
            'D',
            ItemParts.getPartByName("dataOrb"),
            'C',
            ItemParts.getPartByName("computerMonitor"));

        CraftingHelper.addShapedOreRecipe(
            new ItemStack(ModBlocks.LesuStorage),
            "LLL",
            "LCL",
            "LLL",
            'L',
            "chunkLazurite",
            'C',
            "circuitBasic");

        ItemStack holyPlanks = ItemPlates.getPlateByName("wood");
        holyPlanks.addEnchantment(Enchantment.smite, 10);
        holyPlanks.setStackDisplayName("The holy Planks of Sengir");
        CraftingHelper
            .addShapedOreRecipe(holyPlanks, "SSS", "SES", "SSS", 'S', "itemNetherStar", 'E', Blocks.dragon_egg);

        Core.logHelper.info("Shapped Recipes Added");
    }

    public static String capitalizeFirstLetter(String original) {
        if (original.length() == 0) return original;
        return original.substring(0, 1)
            .toUpperCase() + original.substring(1);
    }

    static void addShapelessRecipes() {

        // Storage Blocks
        List<Pair<String, String>> blocks = new ArrayList<>();
        for (String name : BlockStorage.types) {
            blocks.add(Pair.of(name, "ingot" + capitalizeFirstLetter(name)));
        }
        blocks.add(Pair.of("ruby", "gemRuby"));
        blocks.add(Pair.of("sapphire", "gemSapphire"));
        blocks.add(Pair.of("peridot", "gemPeridot"));
        blocks.add(Pair.of("yellowGarnet", "gemYellowGarnet"));
        blocks.add(Pair.of("redGarnet", "gemRedGarnet"));
        blocks.add(Pair.of("olivine", "gemOlivine"));
        for (Pair<String, String> p : blocks) {
            String x = p.getValue();
            CraftingHelper
                .addShapelessOreRecipe(BlockStorage.getStorageBlockByName(p.getKey()), x, x, x, x, x, x, x, x, x);
        }
        CraftingHelper.addShapelessOreRecipe(
            BlockStorage2.getStorageBlockByName("iridium_reinforced_tungstensteel", 1),
            BlockStorage2.getStorageBlockByName("iridium_reinforced_stone", 1),
            "plateTungstensteel");
        CraftingHelper.addShapelessOreRecipe(
            BlockStorage2.getStorageBlockByName("iridium_reinforced_tungstensteel", 1),
            "blockTungstensteel",
            "ingotIridium");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("iridium"),
            "nuggetIridium",
            "nuggetIridium",
            "nuggetIridium",
            "nuggetIridium",
            "nuggetIridium",
            "nuggetIridium",
            "nuggetIridium",
            "nuggetIridium",
            "nuggetIridium");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("silver"),
            "nuggetSilver",
            "nuggetSilver",
            "nuggetSilver",
            "nuggetSilver",
            "nuggetSilver",
            "nuggetSilver",
            "nuggetSilver",
            "nuggetSilver",
            "nuggetSilver");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("aluminium"),
            "nuggetAluminium",
            "nuggetAluminium",
            "nuggetAluminium",
            "nuggetAluminium",
            "nuggetAluminium",
            "nuggetAluminium",
            "nuggetAluminium",
            "nuggetAluminium",
            "nuggetAluminium");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("titanium"),
            "nuggetTitanium",
            "nuggetTitanium",
            "nuggetTitanium",
            "nuggetTitanium",
            "nuggetTitanium",
            "nuggetTitanium",
            "nuggetTitanium",
            "nuggetTitanium",
            "nuggetTitanium");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("chromium"),
            "nuggetChromium",
            "nuggetChromium",
            "nuggetChromium",
            "nuggetChromium",
            "nuggetChromium",
            "nuggetChromium",
            "nuggetChromium",
            "nuggetChromium",
            "nuggetChromium");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("electrum"),
            "nuggetElectrum",
            "nuggetElectrum",
            "nuggetElectrum",
            "nuggetElectrum",
            "nuggetElectrum",
            "nuggetElectrum",
            "nuggetElectrum",
            "nuggetElectrum",
            "nuggetElectrum");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("tungsten"),
            "nuggetTungsten",
            "nuggetTungsten",
            "nuggetTungsten",
            "nuggetTungsten",
            "nuggetTungsten",
            "nuggetTungsten",
            "nuggetTungsten",
            "nuggetTungsten",
            "nuggetTungsten");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("lead"),
            "nuggetLead",
            "nuggetLead",
            "nuggetLead",
            "nuggetLead",
            "nuggetLead",
            "nuggetLead",
            "nuggetLead",
            "nuggetLead",
            "nuggetLead");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("zinc"),
            "nuggetZinc",
            "nuggetZinc",
            "nuggetZinc",
            "nuggetZinc",
            "nuggetZinc",
            "nuggetZinc",
            "nuggetZinc",
            "nuggetZinc",
            "nuggetZinc");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("brass"),
            "nuggetBrass",
            "nuggetBrass",
            "nuggetBrass",
            "nuggetBrass",
            "nuggetBrass",
            "nuggetBrass",
            "nuggetBrass",
            "nuggetBrass",
            "nuggetBrass");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("steel"),
            "nuggetSteel",
            "nuggetSteel",
            "nuggetSteel",
            "nuggetSteel",
            "nuggetSteel",
            "nuggetSteel",
            "nuggetSteel",
            "nuggetSteel",
            "nuggetSteel");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("platinum"),
            "nuggetPlatinum",
            "nuggetPlatinum",
            "nuggetPlatinum",
            "nuggetPlatinum",
            "nuggetPlatinum",
            "nuggetPlatinum",
            "nuggetPlatinum",
            "nuggetPlatinum",
            "nuggetPlatinum");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("nickel"),
            "nuggetNickel",
            "nuggetNickel",
            "nuggetNickel",
            "nuggetNickel",
            "nuggetNickel",
            "nuggetNickel",
            "nuggetNickel",
            "nuggetNickel",
            "nuggetNickel");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("invar"),
            "nuggetInvar",
            "nuggetInvar",
            "nuggetInvar",
            "nuggetInvar",
            "nuggetInvar",
            "nuggetInvar",
            "nuggetInvar",
            "nuggetInvar",
            "nuggetInvar");
        CraftingHelper.addShapelessOreRecipe(
            ItemIngots.getIngotByName("osmium"),
            "nuggetOsmium",
            "nuggetOsmium",
            "nuggetOsmium",
            "nuggetOsmium",
            "nuggetOsmium",
            "nuggetOsmium",
            "nuggetOsmium",
            "nuggetOsmium",
            "nuggetOsmium");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("iridium", 9), "blockIridium");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("silver", 9), "blockSilver");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("aluminium", 9), "blockAluminium");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("titanium", 9), "blockTitanium");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("chromium", 9), "blockChromium");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("electrum", 9), "blockElectrum");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("tungsten", 9), "blockTungsten");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("lead", 9), "blockLead");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("zinc", 9), "blockZinc");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("brass", 9), "blockBrass");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("steel", 9), "blockSteel");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("platinum", 9), "blockPlatinum");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("nickel", 9), "blockNickel");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("invar", 9), "blockInvar");
        CraftingHelper.addShapelessOreRecipe(ItemIngots.getIngotByName("osmium", 9), "blockOsmium");
        CraftingHelper.addShapelessOreRecipe(ItemGems.getGemByName("ruby", 9), "blockRuby");
        CraftingHelper.addShapelessOreRecipe(ItemGems.getGemByName("sapphire", 9), "blockSapphire");
        CraftingHelper.addShapelessOreRecipe(ItemGems.getGemByName("peridot", 9), "blockPeridot");
        CraftingHelper.addShapelessOreRecipe(ItemGems.getGemByName("olivine", 9), "blockOlivine");
        CraftingHelper.addShapelessOreRecipe(ItemGems.getGemByName("redGarnet", 9), "blockRedGarnet");
        CraftingHelper.addShapelessOreRecipe(ItemGems.getGemByName("yellowGarnet", 9), "blockYellowGarnet");
        CraftingHelper.addShapelessOreRecipe(ItemDusts.getDustByName("electrum", 2), "dustSilver", "dustGold");
        CraftingHelper.addShapelessOreRecipe(
            ItemDusts.getDustByName("electrum"),
            "dustSmallSilver",
            "dustSmallSilver",
            "dustSmallGold",
            "dustSmallGold");
        CraftingHelper.addShapelessOreRecipe(
            ItemDusts.getDustByName("brass", 4),
            "dustCopper",
            "dustCopper",
            "dustCopper",
            "dustZinc");
        CraftingHelper.addShapelessOreRecipe(
            ItemDusts.getDustByName("brass"),
            "dustSmallCopper",
            "dustSmallCopper",
            "dustSmallCopper",
            "dustSmallZinc");
        CraftingHelper.addShapelessOreRecipe(ItemDusts.getDustByName("invar", 3), "dustIron", "dustIron", "dustNickel");
        CraftingHelper.addShapelessOreRecipe(
            ItemDustsSmall.getSmallDustByName("invar", 3),
            "dustSmallIron",
            "dustSmallIron",
            "dustSmallNickel");
        CraftingHelper.addShapelessOreRecipe(
            ItemDustsSmall.getSmallDustByName("bronze", 2),
            "dustSmallCopper",
            "dustSmallCopper",
            "dustSmallTin");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("iridium", 9), "ingotIridium");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("silver", 9), "ingotSilver");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("aluminium", 9), "ingotAluminium");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("titanium", 9), "ingotTitanium");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("chromium", 9), "ingotChromium");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("electrum", 9), "ingotElectrum");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("tungsten", 9), "ingotTungsten");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("lead", 9), "ingotLead");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("zinc", 9), "ingotZinc");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("brass", 9), "ingotBrass");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("steel", 9), "ingotSteel");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("platinum", 9), "ingotPlatinum");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("nickel", 9), "ingotNickel");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("invar", 9), "ingotInvar");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("osmium", 9), "ingotOsmium");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("iron", 9), "ingotIron");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("copper", 9), "ingotCopper");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("tin", 9), "ingotTin");
        CraftingHelper.addShapelessOreRecipe(ItemNuggets.getNuggetByName("bronze", 9), "ingotBronze");

        for (String name : ItemDustsSmall.types) {
            GameRegistry.addShapelessRecipe(ItemDustsSmall.getSmallDustByName(name, 4), ItemDusts.getDustByName(name));
            GameRegistry.addShapelessRecipe(
                ItemDusts.getDustByName(name, 1),
                ItemDustsSmall.getSmallDustByName(name),
                ItemDustsSmall.getSmallDustByName(name),
                ItemDustsSmall.getSmallDustByName(name),
                ItemDustsSmall.getSmallDustByName(name));
        }

        Core.logHelper.info("Shapless Recipes Added");
    }

    static void addMachineRecipes() {
        RecipeHandler.addRecipe(
            new VacuumFreezerRecipe(
                ItemIngots.getIngotByName("hotTungstensteel"),
                ItemIngots.getIngotByName("tungstensteel"),
                450,
                128));
        RecipeHandler.addRecipe(
            new VacuumFreezerRecipe(
                ItemCells.getCellByName("heliumPlasma"),
                ItemCells.getCellByName("helium"),
                100,
                128));
        RecipeHandler.addRecipe(
            new VacuumFreezerRecipe(
                new ItemStack(ModItems.coolantHe60k, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(ModItems.coolantHe60k),
                270,
                128));
        RecipeHandler.addRecipe(
            new VacuumFreezerRecipe(
                new ItemStack(ModItems.coolantHe180k, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(ModItems.coolantHe180k),
                780,
                128));
        RecipeHandler.addRecipe(
            new VacuumFreezerRecipe(
                new ItemStack(ModItems.coolantHe360k, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(ModItems.coolantHe360k),
                1500,
                128));
        RecipeHandler.addRecipe(
            new VacuumFreezerRecipe(
                new ItemStack(ModItems.coolantNaK60k, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(ModItems.coolantNaK60k),
                190,
                128));
        RecipeHandler.addRecipe(
            new VacuumFreezerRecipe(
                new ItemStack(ModItems.coolantNaK180k, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(ModItems.coolantNaK180k),
                580,
                128));
        RecipeHandler.addRecipe(
            new VacuumFreezerRecipe(
                new ItemStack(ModItems.coolantNaK360k, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(ModItems.coolantNaK360k),
                1150,
                128));
    }

    static void addSmeltingRecipes() {
        GameRegistry.addSmelting(BlockOre.getOreByName("sheldonite"), ItemIngots.getIngotByName("platinum"), 0);
        GameRegistry.addSmelting(OreUtil.getStackFromName("oreSaltpeter"), ItemDusts.getDustByName("saltpeter", 3), 0);
        GameRegistry.addSmelting(BlockOre.getOreByName("tungsten"), ItemDusts.getDustByName("tungsten"), 0);
        GameRegistry.addSmelting(BlockOre.getOreByName("pyrite"), new ItemStack(Items.iron_ingot), 0);

        GameRegistry.addSmelting(ItemDusts.getDustByName("iron", 1), new ItemStack(Items.iron_ingot), 0);
        GameRegistry.addSmelting(ItemDusts.getDustByName("gold", 1), new ItemStack(Items.gold_ingot), 0);
        GameRegistry.addSmelting(ItemDusts.getDustByName("silver"), ItemIngots.getIngotByName("silver"), 0);
        GameRegistry.addSmelting(ItemDusts.getDustByName("electrum"), ItemIngots.getIngotByName("electrum"), 0);
        GameRegistry.addSmelting(ItemDusts.getDustByName("lead"), ItemIngots.getIngotByName("lead"), 0);
        GameRegistry.addSmelting(ItemDusts.getDustByName("zinc"), ItemIngots.getIngotByName("zinc"), 0);
        GameRegistry.addSmelting(ItemDusts.getDustByName("brass"), ItemIngots.getIngotByName("brass"), 0);
        GameRegistry.addSmelting(ItemDusts.getDustByName("platinum"), ItemIngots.getIngotByName("platinum"), 0);
        GameRegistry.addSmelting(ItemDusts.getDustByName("nickel"), ItemIngots.getIngotByName("nickel"), 0);
        GameRegistry.addSmelting(ItemDusts.getDustByName("invar"), ItemIngots.getIngotByName("invar"), 0);
        if (OreUtil.doesOreExistAndValid("ingotUranium"))
            GameRegistry.addSmelting(ItemDusts.getDustByName("uranium"), OreUtil.getStackFromName("ingotUranium"), 0);

        GameRegistry
            .addSmelting(ItemPlates.getPlateByName("tungstensteel"), ItemIngots.getIngotByName("tungstensteel"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("silver"), ItemIngots.getIngotByName("silver"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("aluminium"), ItemIngots.getIngotByName("aluminium"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("titanium"), ItemIngots.getIngotByName("titanium"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("electrum"), ItemIngots.getIngotByName("electrum"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("tungsten"), ItemIngots.getIngotByName("tungsten"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("lead"), ItemIngots.getIngotByName("lead"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("zinc"), ItemIngots.getIngotByName("zinc"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("brass"), ItemIngots.getIngotByName("brass"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("steel"), ItemIngots.getIngotByName("steel"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("platinum"), ItemIngots.getIngotByName("platinum"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("nickel"), ItemIngots.getIngotByName("nickel"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("invar"), ItemIngots.getIngotByName("invar"), 0);
        GameRegistry.addSmelting(ItemPlates.getPlateByName("osmium"), ItemIngots.getIngotByName("osmium"), 0);

        Core.logHelper.info("Smelting Recipes Added");
    }

    static void addAlloySmelterRecipes() {
        RecipeHandler.addRecipe(
            new AlloySmelterRecipe(
                new ItemStack(Items.gold_ingot, 1),
                ItemIngots.getIngotByName("silver", 1),
                ItemIngots.getIngotByName("electrum", 2),
                100,
                16));
        RecipeHandler.addRecipe(
            new AlloySmelterRecipe(
                ItemIngots.getIngotByName("copper", 3),
                ItemIngots.getIngotByName("zinc", 1),
                ItemIngots.getIngotByName("brass", 4),
                200,
                16));
        RecipeHandler.addRecipe(
            new AlloySmelterRecipe(
                new ItemStack(Items.iron_ingot, 2),
                ItemIngots.getIngotByName("nickel", 1),
                ItemIngots.getIngotByName("invar", 3),
                150,
                16));
    }

    static void addIndustrialSawmillRecipes() {
        boolean ic2Enabled = Loader.isModLoaded("IC2");
        // Credits to KingLemming
        // (https://github.com/CoFH/ThermalExpansion/blob/1.7.10/src/main/java/cofh/thermalexpansion/util/crafting/SawmillManager.java#L171-L217)
        Container tempContainer = new Container() {

            @Override
            public boolean canInteractWith(EntityPlayer p) {
                return false;
            }
        };
        InventoryCrafting tempCrafting = new InventoryCrafting(tempContainer, 3, 3);
        for (int i = 0; i < 9; i++) tempCrafting.setInventorySlotContents(i, null);
        List<ItemStack> logs = OreDictionary.getOres("logWood");
        for (int i = 0; i < logs.size(); i++) {
            ItemStack logEntry = logs.get(i);
            if (logEntry.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                for (int j = 0; j < 16; j++) {
                    ItemStack log = logEntry.copy();
                    log.setItemDamage(j);
                    tempCrafting.setInventorySlotContents(0, log);
                    ItemStack resultEntry = RecipeUtils.findMatchingRecipeSimple(tempCrafting, null);
                    if (resultEntry != null) {
                        ItemStack result = resultEntry.copy();
                        result.stackSize *= 1.5;
                        RecipeHandler.addRecipe(
                            new IndustrialSawmillRecipe(
                                log,
                                null,
                                new FluidStack(WATER, 1000),
                                result,
                                ItemDusts.getDustByName("sawDust"),
                                null,
                                200,
                                32,
                                false));
                        RecipeHandler.addRecipe(
                            new IndustrialSawmillRecipe(
                                log,
                                new ItemStack(Items.water_bucket),
                                null,
                                result,
                                ItemDusts.getDustByName("sawDust"),
                                new ItemStack(Items.bucket),
                                200,
                                32,
                                false));
                        if (ic2Enabled) {
                            RecipeHandler.addRecipe(
                                new IndustrialSawmillRecipe(
                                    log,
                                    ic2.api.item.IC2Items.getItem("waterCell"),
                                    null,
                                    result,
                                    ItemDusts.getDustByName("sawDust"),
                                    ic2.api.item.IC2Items.getItem("cell"),
                                    200,
                                    32,
                                    false));
                        }
                    }
                }
            } else {
                ItemStack log = logEntry.copy();
                tempCrafting.setInventorySlotContents(0, log);
                ItemStack resultEntry = RecipeUtils.findMatchingRecipeSimple(tempCrafting, null);
                if (resultEntry != null) {
                    ItemStack result = resultEntry.copy();
                    result.stackSize *= 1.5;
                    RecipeHandler.addRecipe(
                        new IndustrialSawmillRecipe(
                            log,
                            null,
                            new FluidStack(WATER, 1000),
                            result,
                            ItemDusts.getDustByName("sawDust"),
                            null,
                            200,
                            32,
                            false));
                    RecipeHandler.addRecipe(
                        new IndustrialSawmillRecipe(
                            log,
                            new ItemStack(Items.water_bucket),
                            null,
                            result,
                            ItemDusts.getDustByName("sawDust"),
                            new ItemStack(Items.bucket),
                            200,
                            32,
                            false));
                    if (ic2Enabled) {
                        RecipeHandler.addRecipe(
                            new IndustrialSawmillRecipe(
                                log,
                                ic2.api.item.IC2Items.getItem("waterCell"),
                                null,
                                result,
                                ItemDusts.getDustByName("sawDust"),
                                ic2.api.item.IC2Items.getItem("cell"),
                                200,
                                32,
                                false));
                    }
                }
            }
        }
    }

    static void addBlastFurnaceRecipes() {
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemDusts.getDustByName("titanium"),
                null,
                ItemIngots.getIngotByName("titanium"),
                null,
                1000,
                128,
                1500));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemDustsSmall.getSmallDustByName("titanium", 4),
                null,
                ItemIngots.getIngotByName("titanium"),
                null,
                1000,
                128,
                1500));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemDusts.getDustByName("aluminium"),
                null,
                ItemIngots.getIngotByName("aluminium"),
                null,
                200,
                128,
                1700));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemDustsSmall.getSmallDustByName("aluminium", 4),
                null,
                ItemIngots.getIngotByName("aluminium"),
                null,
                200,
                128,
                1700));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemDusts.getDustByName("tungsten"),
                null,
                ItemIngots.getIngotByName("tungsten"),
                null,
                2000,
                128,
                2500));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemDustsSmall.getSmallDustByName("tungsten", 4),
                null,
                ItemIngots.getIngotByName("tungsten"),
                null,
                2000,
                128,
                2500));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemDusts.getDustByName("chromium"),
                null,
                ItemIngots.getIngotByName("chromium"),
                null,
                800,
                128,
                1700));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemDustsSmall.getSmallDustByName("chromium", 4),
                null,
                ItemIngots.getIngotByName("chromium"),
                null,
                800,
                128,
                1700));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemDusts.getDustByName("steel"),
                null,
                ItemIngots.getIngotByName("steel"),
                null,
                100,
                128,
                1000));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemDustsSmall.getSmallDustByName("steel", 4),
                null,
                ItemIngots.getIngotByName("steel"),
                null,
                100,
                128,
                1000));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemDusts.getDustByName("galena", 2),
                null,
                ItemIngots.getIngotByName("silver"),
                ItemIngots.getIngotByName("lead"),
                20,
                128,
                1500));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemDustsSmall.getSmallDustByName("galena", 8),
                null,
                ItemIngots.getIngotByName("silver"),
                ItemIngots.getIngotByName("lead"),
                20,
                128,
                1500));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                new ItemStack(Items.iron_ingot),
                ItemDusts.getDustByName("coal", 2),
                ItemIngots.getIngotByName("steel"),
                ItemDusts.getDustByName("darkAshes", 2),
                500,
                128,
                1000));
        RecipeHandler.addRecipe(
            new BlastFurnaceRecipe(
                ItemIngots.getIngotByName("tungsten"),
                ItemIngots.getIngotByName("steel"),
                ItemIngots.getIngotByName("hotTungstensteel"),
                ItemDusts.getDustByName("darkAshes", 4),
                2000,
                128,
                3000));

        if (Loader.isModLoaded("Railcraft")) {
            for (ItemStack s : OreDictionary.getOres("dustAluminium"))
                mods.railcraft.api.crafting.RailcraftCraftingManager.blastFurnace
                    .addRecipe(s, true, false, 1700, ItemIngots.getIngotByName("aluminium"));
            for (ItemStack s : OreDictionary.getOres("dustSteel"))
                mods.railcraft.api.crafting.RailcraftCraftingManager.blastFurnace
                    .addRecipe(s, true, false, 100, ItemIngots.getIngotByName("steel"));
        }
    }

    static void addUUrecipes() {

        if (ConfigTechReborn.UUrecipesWood) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.log, 8), " U ", "   ", "   ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesStone) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.stone, 16), "   ", " U ", "   ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesSnowBlock) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.snow, 16), "U U", "   ", "   ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesGrass) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.grass, 16), "   ", "U  ", "U  ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesObsidian) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.obsidian, 12), "U U", "U U", "   ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesGlass) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.glass, 32), " U ", "U U", " U ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesWater) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.water, 1), "   ", " U ", " U ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesLava) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.lava, 1), " U ", " U ", " U ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesCocoa) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.dye, 32, 3), "UU ", "  U", "UU ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesGlowstoneBlock) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.glowstone, 8), " U ", "U U", "UUU", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesCactus) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.cactus, 48), " U ", "UUU", "U U", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesSugarCane) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.reeds, 48), "U U", "U U", "U U", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesVine) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.vine, 24), "U  ", "U  ", "U  ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesSnowBall) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.snowball, 16), "   ", "   ", "UUU", 'U', ModItems.uuMatter);

        CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.clay_ball, 48), "UU ", "U  ", "UU ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipeslilypad) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.waterlily, 64), "U U", " U ", " U ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesGunpowder) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.gunpowder, 15), "UUU", "U  ", "UUU", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesBone) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.bone, 32), "U  ", "UU ", "U  ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesFeather) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.feather, 32), " U ", " U ", "U U", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesInk) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.dye, 48), " UU", " UU", " U ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesEnderPearl) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.ender_pearl, 1), "UUU", "U U", " U ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesCoal) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.coal, 5), "  U", "U  ", "  U", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesIronOre) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.iron_ore, 2), "U U", " U ", "U U", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesGoldOre) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.gold_ore, 2), " U ", "UUU", " U ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesRedStone) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.redstone, 24), "   ", " U ", "UUU", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesLapis) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.dye, 9, 4), " U ", " U ", " UU", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesEmeraldOre) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Blocks.emerald_ore, 1), "UU ", "U U", " UU", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesEmerald) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.emerald, 2), "UUU", "UUU", " U ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesDiamond) CraftingHelper
            .addShapedOreRecipe(new ItemStack(Items.diamond, 1), "UUU", "UUU", "UUU", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesTinDust) CraftingHelper
            .addShapedOreRecipe(new ItemStack(ModItems.dusts, 10, 77), "   ", "U U", "  U", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesCopperDust) CraftingHelper
            .addShapedOreRecipe(new ItemStack(ModItems.dusts, 10, 21), "  U", "U U", "   ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesLeadDust) CraftingHelper
            .addShapedOreRecipe(new ItemStack(ModItems.dusts, 14, 42), "UUU", "UUU", "U  ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesPlatinumDust) CraftingHelper
            .addShapedOreRecipe(new ItemStack(ModItems.dusts, 1, 58), "  U", "UUU", "UUU", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesTungstenDust) CraftingHelper
            .addShapedOreRecipe(new ItemStack(ModItems.dusts, 1, 79), "U  ", "UUU", "UUU", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesTitaniumDust) CraftingHelper
            .addShapedOreRecipe(new ItemStack(ModItems.dusts, 2, 78), "UUU", " U ", " U ", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.UUrecipesAluminumDust) CraftingHelper
            .addShapedOreRecipe(new ItemStack(ModItems.dusts, 16, 2), " U ", " U ", "UUU", 'U', ModItems.uuMatter);

        if (ConfigTechReborn.HideUuRecipes) hideUUrecipes();

    }

    static void hideUUrecipes() {
        // TODO
    }

    static void addIndustrialCentrifugeRecipes() {

        // Mycelium Byproducts
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Blocks.mycelium, 8),
                null,
                new ItemStack(Blocks.brown_mushroom, 2),
                new ItemStack(Blocks.red_mushroom, 2),
                new ItemStack(Items.clay_ball, 1),
                new ItemStack(Blocks.sand, 4),
                1650,
                5));

        // Blaze Powder Byproducts
        if (!Loader.isModLoaded("IC2")) {
            RecipeHandler.addRecipe(
                new CentrifugeRecipe(
                    new ItemStack(Items.blaze_powder, 4),
                    null,
                    ItemDusts.getDustByName("darkAshes", 1),
                    ItemDusts.getDustByName("sulfur", 1),
                    null,
                    null,
                    1240,
                    5));
        }

        // Magma Cream Products
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.magma_cream, 1),
                null,
                new ItemStack(Items.blaze_powder, 1),
                new ItemStack(Items.slime_ball, 1),
                null,
                null,
                500,
                5));

        // Dust Byproducts
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("copper", 3),
                null,
                ItemDustsSmall.getSmallDustByName("gold"),
                ItemDustsSmall.getSmallDustByName("nickel"),
                null,
                null,
                2400,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("gold", 3),
                null,
                ItemDustsSmall.getSmallDustByName("copper"),
                ItemDustsSmall.getSmallDustByName("nickel"),
                null,
                null,
                2400,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("iron", 2),
                null,
                ItemDustsSmall.getSmallDustByName("tin"),
                ItemDustsSmall.getSmallDustByName("nickel"),
                null,
                null,
                1500,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("silver", 2),
                null,
                ItemDustsSmall.getSmallDustByName("lead"),
                null,
                null,
                null,
                2400,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("tin", 2),
                null,
                ItemDustsSmall.getSmallDustByName("zinc"),
                ItemDustsSmall.getSmallDustByName("iron"),
                null,
                null,
                2100,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("lead", 3),
                null,
                ItemDustsSmall.getSmallDustByName("silver"),
                null,
                null,
                null,
                2400,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("zinc"),
                null,
                ItemDustsSmall.getSmallDustByName("tin"),
                null,
                null,
                null,
                1050,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("nickel", 3),
                null,
                ItemDustsSmall.getSmallDustByName("iron"),
                ItemDustsSmall.getSmallDustByName("gold"),
                ItemDustsSmall.getSmallDustByName("copper"),
                null,
                3450,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("bronze", 4),
                null,
                ItemDusts.getDustByName("tin", 1),
                ItemDusts.getDustByName("copper", 3),
                null,
                null,
                2420,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("bronze"),
                null,
                ItemDustsSmall.getSmallDustByName("copper", 6),
                ItemDustsSmall.getSmallDustByName("tin", 2),
                null,
                null,
                1500,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("brass"),
                null,
                ItemDustsSmall.getSmallDustByName("copper", 3),
                ItemDustsSmall.getSmallDustByName("zinc"),
                null,
                null,
                1500,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("electrum", 2),
                null,
                ItemDusts.getDustByName("silver", 1),
                ItemDusts.getDustByName("gold", 1),
                null,
                null,
                2400,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("electrum"),
                null,
                ItemDustsSmall.getSmallDustByName("gold", 2),
                ItemDustsSmall.getSmallDustByName("silver", 2),
                null,
                null,
                975,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("invar", 3),
                null,
                ItemDusts.getDustByName("iron", 2),
                ItemDusts.getDustByName("nickel", 1),
                null,
                null,
                1000,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("marble", 8),
                null,
                ItemDusts.getDustByName("magnesium", 1),
                ItemDusts.getDustByName("calcite", 7),
                null,
                null,
                1055,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("basalt", 16),
                null,
                ItemDusts.getDustByName("olivine", 1),
                ItemDusts.getDustByName("calcite", 3),
                ItemDusts.getDustByName("flint", 8),
                ItemDusts.getDustByName("darkAshes", 4),
                2040,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("yellowGarnet", 16),
                null,
                ItemDusts.getDustByName("andradite", 5),
                ItemDusts.getDustByName("grossular", 8),
                ItemDusts.getDustByName("uvarovite", 3),
                null,
                3500,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("redGarnet", 16),
                null,
                ItemDusts.getDustByName("pyrope", 3),
                ItemDusts.getDustByName("almandine", 5),
                ItemDusts.getDustByName("spessartine", 8),
                null,
                3000,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("platinum"),
                null,
                ItemNuggets.getNuggetByName("iridium"),
                ItemDustsSmall.getSmallDustByName("nickel"),
                null,
                null,
                3000,
                5));
        if (!Loader.isModLoaded("ThermalExpansion")) {
            RecipeHandler.addRecipe(
                new CentrifugeRecipe(
                    ItemDusts.getDustByName("darkAshes", 2),
                    null,
                    ItemDusts.getDustByName("ashes", 2),
                    null,
                    null,
                    null,
                    240,
                    5));
        }
        if (!Loader.isModLoaded("IC2")) {
            RecipeHandler.addRecipe(
                new CentrifugeRecipe(
                    ItemDusts.getDustByName("netherrack", 16),
                    null,
                    new ItemStack(Items.redstone, 1),
                    ItemDusts.getDustByName("sulfur", 4),
                    ItemDusts.getDustByName("coal", 1),
                    new ItemStack(Items.gold_nugget, 1),
                    2400,
                    5));
        }
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                ItemDusts.getDustByName("enderEye", 2),
                null,
                ItemDusts.getDustByName("enderPearl", 1),
                new ItemStack(Items.blaze_powder, 1),
                null,
                null,
                1850,
                5));
        RecipeHandler.addRecipe(
            new CentrifugeRecipe(
                new ItemStack(Items.dye, 4, 4),
                null,
                ItemDusts.getDustByName("lazurite", 3),
                ItemDustsSmall.getSmallDustByName("pyrite"),
                ItemDustsSmall.getSmallDustByName("calcite"),
                ItemDustsSmall.getSmallDustByName("sodalite", 2),
                1500,
                5,
                false));

    }

    static void addIndustrialGrinderRecipes() {
        // Ruby Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("ruby"),
                null,
                new FluidStack(WATER, 1000),
                ItemGems.getGemByName("ruby", 1),
                ItemDustsSmall.getSmallDustByName("Ruby", 6),
                ItemDustsSmall.getSmallDustByName("redGarnet", 2),
                null,
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherRuby")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreDictionary.getOres("oreNetherRuby")
                        .get(0),
                    null,
                    new FluidStack(WATER, 1000),
                    ItemGems.getGemByName("ruby", 2),
                    ItemDustsSmall.getSmallDustByName("Ruby", 12),
                    ItemDustsSmall.getSmallDustByName("redGarnet", 2),
                    null,
                    100,
                    128));
        }

        // Sapphire Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sapphire"),
                null,
                new FluidStack(WATER, 1000),
                ItemGems.getGemByName("sapphire", 1),
                ItemDustsSmall.getSmallDustByName("Sapphire", 6),
                ItemDustsSmall.getSmallDustByName("peridot", 2),
                null,
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherSapphire")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreDictionary.getOres("oreNetherSapphire")
                        .get(0),
                    null,
                    new FluidStack(WATER, 1000),
                    ItemGems.getGemByName("sapphire", 2),
                    ItemDustsSmall.getSmallDustByName("Sapphire", 12),
                    ItemDustsSmall.getSmallDustByName("peridot", 2),
                    null,
                    100,
                    128));
        }

        // Peridot Ore
        if (OreUtil.doesOreExistAndValid("orePeridot")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreDictionary.getOres("orePeridot")
                        .get(0),
                    null,
                    new FluidStack(WATER, 1000),
                    ItemGems.getGemByName("peridot"),
                    ItemDustsSmall.getSmallDustByName("peridot", 6),
                    ItemDustsSmall.getSmallDustByName("sapphire", 2),
                    null,
                    100,
                    128));
        }
        if (OreUtil.doesOreExistAndValid("oreNetherPeridot")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreDictionary.getOres("oreNetherPeridot")
                        .get(0),
                    null,
                    new FluidStack(WATER, 1000),
                    ItemGems.getGemByName("peridot", 2),
                    ItemDustsSmall.getSmallDustByName("peridot", 12),
                    ItemDustsSmall.getSmallDustByName("sapphire", 2),
                    null,
                    100,
                    128));
        }

        // Olivine Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("olivine"),
                null,
                new FluidStack(WATER, 1000),
                ItemGems.getGemByName("olivine"),
                ItemDustsSmall.getSmallDustByName("olivine", 6),
                ItemDustsSmall.getSmallDustByName("emerald", 2),
                null,
                100,
                128));

        // Lapis Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.lapis_ore),
                null,
                new FluidStack(WATER, 1000),
                new ItemStack(Items.dye, 12, 4),
                ItemDusts.getDustByName("lazurite", 3),
                null,
                null,
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherLapis")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreDictionary.getOres("oreNetherLapis")
                        .get(0),
                    null,
                    new FluidStack(WATER, 1000),
                    new ItemStack(Items.dye, 18, 4),
                    ItemDusts.getDustByName("lazurite", 3),
                    null,
                    null,
                    100,
                    128));
        }

        // Sheldonite (Platinum) Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sheldonite"),
                null,
                new FluidStack(WATER, 1000),
                ItemDusts.getDustByName("platinum", 2),
                ItemDusts.getDustByName("nickel"),
                ItemNuggets.getNuggetByName("iridium", 2),
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sheldonite"),
                null,
                new FluidStack(ModFluids.fluidMercury, 1000),
                ItemDusts.getDustByName("platinum", 3),
                ItemDusts.getDustByName("nickel"),
                ItemNuggets.getNuggetByName("iridium", 2),
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sheldonite"),
                new ItemStack(ModItems.bucketMercury),
                null,
                ItemDusts.getDustByName("platinum", 3),
                ItemDusts.getDustByName("nickel"),
                ItemNuggets.getNuggetByName("iridium", 2),
                new ItemStack(Items.bucket),
                100,
                128));

        // Sodalite Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sodalite"),
                null,
                new FluidStack(WATER, 1000),
                ItemDusts.getDustByName("sodalite", 12),
                ItemDusts.getDustByName("aluminium", 3),
                null,
                null,
                100,
                128));

        // Netherrack
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.netherrack, 16),
                null,
                new FluidStack(WATER, 1000),
                new ItemStack(Items.gold_nugget),
                ItemDusts.getDustByName("netherrack", 16),
                null,
                null,
                1600,
                128,
                false));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.netherrack, 8),
                null,
                new FluidStack(ModFluids.fluidMercury, 1000),
                new ItemStack(Items.gold_nugget),
                ItemDusts.getDustByName("netherrack", 8),
                null,
                null,
                800,
                128,
                false));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.netherrack, 8),
                new ItemStack(ModItems.bucketMercury),
                null,
                new ItemStack(Items.gold_nugget),
                ItemDusts.getDustByName("netherrack", 8),
                null,
                new ItemStack(Items.bucket),
                800,
                128,
                false));

        // Saltpeter Ore
        if (OreUtil.doesOreExistAndValid("oreSaltpeter")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreSaltpeter"),
                    null,
                    new FluidStack(WATER, 1000),
                    ItemDusts.getDustByName("saltpeter", 7),
                    null,
                    null,
                    null,
                    100,
                    128));
        }

        // Cinnabar Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("cinnabar"),
                null,
                new FluidStack(WATER, 1000),
                ItemDusts.getDustByName("cinnabar", 5),
                ItemDustsSmall.getSmallDustByName("redstone", 2),
                ItemDustsSmall.getSmallDustByName("glowstone", 1),
                null,
                100,
                128));

        // Sphalerite Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sphalerite"),
                null,
                new FluidStack(WATER, 1000),
                ItemDusts.getDustByName("sphalerite", 5),
                ItemDusts.getDustByName("Zinc", 1),
                ItemDustsSmall.getSmallDustByName("YellowGarnet", 1),
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sphalerite"),
                null,
                new FluidStack(ModFluids.fluidSodiumpersulfate, 1000),
                ItemDusts.getDustByName("sphalerite", 5),
                ItemDusts.getDustByName("zinc", 3),
                ItemDustsSmall.getSmallDustByName("YellowGarnet", 1),
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("sphalerite"),
                new ItemStack(ModItems.bucketSodiumpersulfate),
                null,
                ItemDusts.getDustByName("sphalerite", 5),
                ItemDusts.getDustByName("zinc", 3),
                ItemDustsSmall.getSmallDustByName("YellowGarnet", 1),
                new ItemStack(Items.bucket),
                100,
                128));

        // Bauxite Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("bauxite"),
                null,
                new FluidStack(WATER, 1000),
                ItemDusts.getDustByName("bauxite", 4),
                ItemDusts.getDustByName("aluminium"),
                null,
                null,
                100,
                128));

        // Tungsten Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                BlockOre.getOreByName("tungsten"),
                null,
                new FluidStack(WATER, 1000),
                ItemDusts.getDustByName("tungsten", 2),
                ItemDustsSmall.getSmallDustByName("iron", 3),
                ItemDustsSmall.getSmallDustByName("manganese", 3),
                null,
                100,
                128));

        // Lead Ore
        if (OreUtil.doesOreExistAndValid("oreLead")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreLead"),
                    null,
                    new FluidStack(WATER, 1000),
                    ItemDusts.getDustByName("lead", 2),
                    ItemDustsSmall.getSmallDustByName("silver"),
                    null,
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreLead"),
                    null,
                    new FluidStack(ModFluids.fluidMercury, 1000),
                    ItemDusts.getDustByName("lead", 2),
                    ItemDusts.getDustByName("silver"),
                    null,
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreLead"),
                    new ItemStack(ModItems.bucketMercury),
                    null,
                    ItemDusts.getDustByName("lead", 2),
                    ItemDusts.getDustByName("silver"),
                    null,
                    new ItemStack(Items.bucket),
                    100,
                    128));
        }
        if (OreUtil.doesOreExistAndValid("oreNetherLead")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherLead"),
                    null,
                    new FluidStack(WATER, 1000),
                    ItemDusts.getDustByName("lead", 4),
                    ItemDustsSmall.getSmallDustByName("silver"),
                    null,
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherLead"),
                    null,
                    new FluidStack(ModFluids.fluidMercury, 1000),
                    ItemDusts.getDustByName("lead", 4),
                    ItemDusts.getDustByName("silver"),
                    null,
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherLead"),
                    new ItemStack(ModItems.bucketMercury),
                    null,
                    ItemDusts.getDustByName("lead", 4),
                    ItemDusts.getDustByName("silver"),
                    null,
                    new ItemStack(Items.bucket),
                    100,
                    128));
        }

        // Coal Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.coal_ore, 1),
                null,
                new FluidStack(WATER, 1000),
                new ItemStack(Items.coal, 1),
                ItemDusts.getDustByName("coal"),
                ItemDustsSmall.getSmallDustByName("thorium"),
                null,
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherCoal")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherCoal"),
                    null,
                    new FluidStack(WATER, 1000),
                    new ItemStack(Items.coal, 2),
                    ItemDusts.getDustByName("coal", 2),
                    ItemDustsSmall.getSmallDustByName("thorium"),
                    null,
                    100,
                    128));
        }

        // Iron Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.iron_ore, 1),
                null,
                new FluidStack(WATER, 1000),
                ItemDusts.getDustByName("iron", 2),
                ItemDustsSmall.getSmallDustByName("Nickel", 1),
                ItemDustsSmall.getSmallDustByName("Tin", 1),
                null,
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherIron")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherIron"),
                    null,
                    new FluidStack(WATER, 1000),
                    ItemDusts.getDustByName("iron", 4),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    ItemDustsSmall.getSmallDustByName("Tin", 1),
                    null,
                    100,
                    128));
        }

        // Gold Ore
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.gold_ore, 1),
                null,
                new FluidStack(WATER, 1000),
                ItemDusts.getDustByName("gold", 2),
                ItemDustsSmall.getSmallDustByName("Copper", 1),
                ItemDustsSmall.getSmallDustByName("Nickel", 1),
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.gold_ore, 1),
                null,
                new FluidStack(ModFluids.fluidSodiumpersulfate, 1000),
                ItemDusts.getDustByName("gold", 2),
                ItemDusts.getDustByName("copper", 1),
                ItemDustsSmall.getSmallDustByName("Nickel", 1),
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.gold_ore, 1),
                new ItemStack(ModItems.bucketSodiumpersulfate),
                null,
                ItemDusts.getDustByName("gold", 2),
                ItemDusts.getDustByName("copper", 1),
                ItemDustsSmall.getSmallDustByName("Nickel", 1),
                new ItemStack(Items.bucket),
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.gold_ore, 1),
                null,
                new FluidStack(ModFluids.fluidMercury, 1000),
                ItemDusts.getDustByName("gold", 3),
                ItemDustsSmall.getSmallDustByName("Copper", 1),
                ItemDustsSmall.getSmallDustByName("Nickel", 1),
                null,
                100,
                128));
        RecipeHandler.addRecipe(
            new GrinderRecipe(
                new ItemStack(Blocks.gold_ore, 1),
                new ItemStack(ModItems.bucketMercury),
                null,
                ItemDusts.getDustByName("gold", 3),
                ItemDustsSmall.getSmallDustByName("Copper", 1),
                ItemDustsSmall.getSmallDustByName("Nickel", 1),
                new ItemStack(Items.bucket),
                100,
                128));
        if (OreUtil.doesOreExistAndValid("oreNetherGold")) {
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherGold"),
                    null,
                    new FluidStack(WATER, 1000),
                    ItemDusts.getDustByName("gold", 4),
                    ItemDustsSmall.getSmallDustByName("Copper", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherGold"),
                    null,
                    new FluidStack(ModFluids.fluidMercury, 1000),
                    ItemDusts.getDustByName("gold", 5),
                    ItemDustsSmall.getSmallDustByName("Copper", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherGold"),
                    new ItemStack(ModItems.bucketMercury),
                    null,
                    ItemDusts.getDustByName("gold", 5),
                    ItemDustsSmall.getSmallDustByName("Copper", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    new ItemStack(Items.bucket),
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherGold"),
                    null,
                    new FluidStack(ModFluids.fluidSodiumpersulfate, 1000),
                    ItemDusts.getDustByName("gold", 4),
                    ItemDusts.getDustByName("copper", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    OreUtil.getStackFromName("oreNetherGold"),
                    new ItemStack(ModItems.bucketSodiumpersulfate),
                    null,
                    ItemDusts.getDustByName("gold", 4),
                    ItemDusts.getDustByName("copper", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    new ItemStack(Items.bucket),
                    100,
                    128));
        }

        // Copper Ore
        if (OreUtil.doesOreExistAndValid("oreCopper")) {
            try {
                ItemStack oreStack = OreDictionary.getOres("oreCopper")
                    .get(0);
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        null,
                        new FluidStack(WATER, 1000),
                        ItemDusts.getDustByName("copper", 2),
                        ItemDustsSmall.getSmallDustByName("Gold", 1),
                        ItemDustsSmall.getSmallDustByName("Nickel", 1),
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        null,
                        new FluidStack(ModFluids.fluidSodiumpersulfate, 1000),
                        ItemDusts.getDustByName("copper", 3),
                        ItemDustsSmall.getSmallDustByName("gold", 1),
                        ItemDustsSmall.getSmallDustByName("Nickel", 1),
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        new ItemStack(ModItems.bucketSodiumpersulfate),
                        null,
                        ItemDusts.getDustByName("copper", 3),
                        ItemDustsSmall.getSmallDustByName("gold", 1),
                        ItemDustsSmall.getSmallDustByName("Nickel", 1),
                        new ItemStack(Items.bucket),
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        null,
                        new FluidStack(ModFluids.fluidMercury, 1000),
                        ItemDusts.getDustByName("copper", 2),
                        ItemDusts.getDustByName("Gold", 1),
                        null,
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        new ItemStack(ModItems.bucketMercury),
                        null,
                        ItemDusts.getDustByName("copper", 2),
                        ItemDusts.getDustByName("Gold", 1),
                        null,
                        new ItemStack(Items.bucket),
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
                    null,
                    new FluidStack(WATER, 1000),
                    ItemDusts.getDustByName("copper", 4),
                    ItemDustsSmall.getSmallDustByName("Gold", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    oreStack,
                    null,
                    new FluidStack(ModFluids.fluidSodiumpersulfate, 1000),
                    ItemDusts.getDustByName("copper", 5),
                    ItemDusts.getDustByName("gold", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    oreStack,
                    new ItemStack(ModItems.bucketSodiumpersulfate),
                    null,
                    ItemDusts.getDustByName("copper", 5),
                    ItemDusts.getDustByName("gold", 1),
                    ItemDustsSmall.getSmallDustByName("Nickel", 1),
                    new ItemStack(Items.bucket),
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    oreStack,
                    null,
                    new FluidStack(ModFluids.fluidMercury, 1000),
                    ItemDusts.getDustByName("copper", 4),
                    ItemDusts.getDustByName("Gold", 1),
                    null,
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    oreStack,
                    new ItemStack(ModItems.bucketMercury),
                    null,
                    ItemDusts.getDustByName("copper", 4),
                    ItemDusts.getDustByName("Gold", 1),
                    null,
                    new ItemStack(Items.bucket),
                    100,
                    128));
        }

        // Tin Ore
        if (OreUtil.doesOreExistAndValid("oreTin")) {
            try {
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreTin"),
                        null,
                        new FluidStack(WATER, 1000),
                        ItemDusts.getDustByName("tin", 2),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDustsSmall.getSmallDustByName("Zinc", 1),
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreTin"),
                        null,
                        new FluidStack(ModFluids.fluidSodiumpersulfate, 1000),
                        ItemDusts.getDustByName("tin", 2),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDusts.getDustByName("zinc", 1),
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreTin"),
                        new ItemStack(ModItems.bucketSodiumpersulfate),
                        null,
                        ItemDusts.getDustByName("tin", 2),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDusts.getDustByName("zinc", 1),
                        new ItemStack(Items.bucket),
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
                        null,
                        new FluidStack(WATER, 1000),
                        ItemDusts.getDustByName("tin", 4),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDustsSmall.getSmallDustByName("Zinc", 1),
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreNetherTin"),
                        null,
                        new FluidStack(ModFluids.fluidSodiumpersulfate, 1000),
                        ItemDusts.getDustByName("tin", 4),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDusts.getDustByName("zinc", 1),
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreNetherTin"),
                        new ItemStack(ModItems.bucketSodiumpersulfate),
                        null,
                        ItemDusts.getDustByName("tin", 4),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDusts.getDustByName("zinc", 1),
                        new ItemStack(Items.bucket),
                        100,
                        128));
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
                        null,
                        new FluidStack(WATER, 1000),
                        ItemDusts.getDustByName("nickel", 3),
                        ItemDustsSmall.getSmallDustByName("platinum"),
                        ItemDustsSmall.getSmallDustByName("copper"),
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        null,
                        new FluidStack(ModFluids.fluidMercury, 1000),
                        ItemDusts.getDustByName("nickel", 3),
                        ItemDusts.getDustByName("platinum"),
                        null,
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        new ItemStack(ModItems.bucketMercury),
                        null,
                        ItemDusts.getDustByName("nickel", 3),
                        ItemDusts.getDustByName("platinum"),
                        null,
                        new ItemStack(Items.bucket),
                        100,
                        128));
            } catch (Exception e) {
                Core.logHelper.info("Failed to Load Grinder Recipe for Nickel Ore");
            }
        }

        // Zinc Ore
        if (OreUtil.doesOreExistAndValid("oreZinc")) {
            try {
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreZinc"),
                        null,
                        new FluidStack(WATER, 1000),
                        ItemDusts.getDustByName("zinc", 2),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDustsSmall.getSmallDustByName("Tin", 1),
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreZinc"),
                        null,
                        new FluidStack(ModFluids.fluidSodiumpersulfate, 1000),
                        ItemDusts.getDustByName("zinc", 2),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDusts.getDustByName("iron", 1),
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreZinc"),
                        new ItemStack(ModItems.bucketSodiumpersulfate),
                        null,
                        ItemDusts.getDustByName("zinc", 2),
                        ItemDustsSmall.getSmallDustByName("Iron", 1),
                        ItemDusts.getDustByName("iron", 1),
                        new ItemStack(Items.bucket),
                        100,
                        128));
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
                        null,
                        new FluidStack(WATER, 1000),
                        ItemDusts.getDustByName("silver", 2),
                        ItemDustsSmall.getSmallDustByName("Lead", 2),
                        null,
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        null,
                        new FluidStack(ModFluids.fluidMercury, 1000),
                        ItemDusts.getDustByName("silver", 3),
                        ItemDustsSmall.getSmallDustByName("Lead", 2),
                        null,
                        null,
                        100,
                        128));
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        oreStack,
                        new ItemStack(ModItems.bucketMercury),
                        null,
                        ItemDusts.getDustByName("silver", 3),
                        ItemDustsSmall.getSmallDustByName("Lead", 2),
                        null,
                        new ItemStack(Items.bucket),
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
                    null,
                    new FluidStack(WATER, 1000),
                    ItemDusts.getDustByName("silver", 4),
                    ItemDustsSmall.getSmallDustByName("Lead", 2),
                    null,
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    oreStack,
                    null,
                    new FluidStack(ModFluids.fluidMercury, 1000),
                    ItemDusts.getDustByName("silver", 5),
                    ItemDustsSmall.getSmallDustByName("Lead", 2),
                    null,
                    null,
                    100,
                    128));
            RecipeHandler.addRecipe(
                new GrinderRecipe(
                    oreStack,
                    new ItemStack(ModItems.bucketMercury),
                    null,
                    ItemDusts.getDustByName("silver", 5),
                    ItemDustsSmall.getSmallDustByName("Lead", 2),
                    null,
                    new ItemStack(Items.bucket),
                    100,
                    128));
        }

        // Apatite Ore
        if (OreUtil.doesOreExistAndValid("oreApatite") && OreUtil.doesOreExistAndValid("gemApatite")) {
            try {
                RecipeHandler.addRecipe(
                    new GrinderRecipe(
                        OreUtil.getStackFromName("oreApatite"),
                        null,
                        new FluidStack(WATER, 1000),
                        OreUtil.getStackFromName("gemApatite", 12),
                        ItemDusts.getDustByName("tricalciumPhosphate"),
                        null,
                        null,
                        100,
                        128));
            } catch (Exception e) {
                Core.logHelper.info("Failed to Load Grinder Recipe for Apatite Ore");
            }
        }
    }

    static void addChemicalReactorRecipes() {
        RecipeHandler.addRecipe(
            new ChemicalReactorRecipe(
                ItemCells.getCellByName("calcium", 1),
                ItemCells.getCellByName("carbon", 1),
                ItemCells.getCellByName("calciumCarbonate", 2),
                250,
                32));
        RecipeHandler.addRecipe(
            new ChemicalReactorRecipe(
                ItemCells.getCellByName("nitrogen", 1),
                ItemCells.getCellByName("carbon", 1),
                ItemCells.getCellByName("nitrocarbon", 2),
                1500,
                32));
        RecipeHandler.addRecipe(
            new ChemicalReactorRecipe(
                ItemCells.getCellByName("carbon", 1),
                ItemCells.getCellByName("hydrogen", 4),
                ItemCells.getCellByName("methane", 5),
                3500,
                32));
        RecipeHandler.addRecipe(
            new ChemicalReactorRecipe(
                ItemCells.getCellByName("sulfur", 1),
                ItemCells.getCellByName("sodium", 1),
                ItemCells.getCellByName("sodiumSulfide", 2),
                100,
                32));
        RecipeHandler.addRecipe(
            new ChemicalReactorRecipe(
                ItemCells.getCellByName("glyceryl", 1),
                ItemCells.getCellByName("diesel", 4),
                ItemCells.getCellByName("nitroDiesel", 5),
                250,
                32));
    }

    static void addIndustrialElectrolyzerRecipes() {

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("sphalerite", 2),
                null,
                ItemDusts.getDustByName("zinc"),
                ItemDusts.getDustByName("sulfur"),
                null,
                null,
                150,
                100));

        RecipeHandler.addRecipe(
            new IndustrialElectrolyzerRecipe(
                ItemDusts.getDustByName("galena", 2),
                null,
                ItemDustsSmall.getSmallDustByName("silver", 3),
                ItemDustsSmall.getSmallDustByName("lead", 3),
                ItemDustsSmall.getSmallDustByName("sulfur", 2),
                null,
                1000,
                120));

    }

}
