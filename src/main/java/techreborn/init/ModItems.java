package techreborn.init;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IC2Items;
import reborncore.common.util.BucketHandler;
import techreborn.Core;
import techreborn.events.OreUnifier;
import techreborn.items.ItemCells;
import techreborn.items.ItemCrushedOre;
import techreborn.items.ItemDusts;
import techreborn.items.ItemDustsSmall;
import techreborn.items.ItemGems;
import techreborn.items.ItemIngots;
import techreborn.items.ItemLapotronicOrb;
import techreborn.items.ItemLithiumBattery;
import techreborn.items.ItemNuggets;
import techreborn.items.ItemParts;
import techreborn.items.ItemPlates;
import techreborn.items.ItemPurifiedCrushedOre;
import techreborn.items.ItemUUmatter;
import techreborn.items.armor.ItemLapotronPack;
import techreborn.items.armor.ItemLithiumBatpack;
import techreborn.items.component.ItemCoolantCell;
import techreborn.items.component.ItemFuelRod;
import techreborn.items.component.ItemReflector;
import techreborn.items.tools.ItemCloakingDevice;
import techreborn.items.tools.ItemFluidbucket;
import techreborn.items.tools.ItemOmniTool;
import techreborn.items.tools.ItemRockCutter;
import techreborn.powerSystem.PoweredItem;

public class ModItems {

    // This are deprected to stop people using them in the recipes.
    @Deprecated
    public static Item gems;
    @Deprecated
    public static Item ingots;
    @Deprecated
    public static Item nuggets;
    @Deprecated
    public static Item dusts;
    @Deprecated
    public static Item smallDusts;
    @Deprecated
    public static Item parts;
    @Deprecated
    public static Item cells;
    @Deprecated
    public static Item plate;
    @Deprecated
    public static Item crushedOre;
    @Deprecated
    public static Item purifiedCrushedOre;

    public static Item neutronReflector;
    public static Item coolantHe60k;
    public static Item coolantHe180k;
    public static Item coolantHe360k;
    public static Item coolantNaK60k;
    public static Item coolantNaK180k;
    public static Item coolantNaK360k;
    public static Item cellPlutonium1;
    public static Item cellPlutonium2;
    public static Item cellPlutonium4;
    public static Item cellThorium1;
    public static Item cellThorium2;
    public static Item cellThorium4;

    public static Item rockCutter;
    public static Item lithiumBatpack;
    public static Item lapotronpack;
    public static Item lithiumBattery;
    public static Item omniTool;
    public static Item lapotronicOrb;
    public static Item uuMatter;
    public static Item cloakingDevice;

    public static Item bucketBerylium;
    public static Item bucketcalcium;
    public static Item bucketcalciumcarbonate;
    public static Item bucketChlorite;
    public static Item bucketDeuterium;
    public static Item bucketGlyceryl;
    public static Item bucketHelium;
    public static Item bucketHelium3;
    public static Item bucketHeliumplasma;
    public static Item bucketHydrogen;
    public static Item bucketLithium;
    public static Item bucketMercury;
    public static Item bucketMethane;
    public static Item bucketNitrocoalfuel;
    public static Item bucketNitrofuel;
    public static Item bucketNitrogen;
    public static Item bucketNitrogendioxide;
    public static Item bucketPotassium;
    public static Item bucketSilicon;
    public static Item bucketSodium;
    public static Item bucketSodiumpersulfate;
    public static Item bucketTritium;
    public static Item bucketWolframium;

    // public static Item upgrades;

    public static void init() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
        InvocationTargetException, NoSuchMethodException, SecurityException {
        gems = new ItemGems();
        GameRegistry.registerItem(gems, "gem");
        ingots = new ItemIngots();
        GameRegistry.registerItem(ingots, "ingot");
        dusts = new ItemDusts();
        GameRegistry.registerItem(dusts, "dust");
        smallDusts = new ItemDustsSmall();
        GameRegistry.registerItem(smallDusts, "smallDust");
        plate = new ItemPlates();
        GameRegistry.registerItem(plate, "plates");
        nuggets = new ItemNuggets();
        GameRegistry.registerItem(nuggets, "nuggets");
        crushedOre = new ItemCrushedOre();
        GameRegistry.registerItem(crushedOre, "crushedore");
        purifiedCrushedOre = new ItemPurifiedCrushedOre();
        GameRegistry.registerItem(purifiedCrushedOre, "purifiedCrushedOre");
        parts = new ItemParts();
        GameRegistry.registerItem(parts, "part");
        cells = new ItemCells();
        GameRegistry.registerItem(cells, "cell");
        if (Loader.isModLoaded("IC2")) {
            ItemStack ecell = IC2Items.getItem("cell")
                .copy();
            for (int i = 0; i < ItemCells.types.length; i++) {
                if (FluidRegistry.getFluid(ItemCells.types[i].toLowerCase()) != null) {
                    FluidContainerRegistry.registerFluidContainer(
                        FluidRegistry.getFluid(ItemCells.types[i].toLowerCase()),
                        ItemCells.getCellByName(ItemCells.types[i]),
                        ecell);
                }
            }
        } else {
            for (int i = 0; i < ItemCells.types.length; i++) {
                if (FluidRegistry.getFluid(ItemCells.types[i].toLowerCase()) != null) {
                    FluidContainerRegistry.registerFluidContainer(
                        FluidRegistry.getFluid(ItemCells.types[i].toLowerCase()),
                        ItemCells.getCellByName(ItemCells.types[i]));
                }
            }
        }
        neutronReflector = new ItemReflector(0);
        GameRegistry.registerItem(neutronReflector, "neutronReflector");
        coolantHe60k = new ItemCoolantCell(60000, "He60k");
        GameRegistry.registerItem(coolantHe60k, "coolantHe60k");
        coolantHe180k = new ItemCoolantCell(180000, "He180k");
        GameRegistry.registerItem(coolantHe180k, "coolantHe180k");
        coolantHe360k = new ItemCoolantCell(360000, "He360k");
        GameRegistry.registerItem(coolantHe360k, "coolantHe360k");
        coolantNaK60k = new ItemCoolantCell(60000, "NaK60k");
        GameRegistry.registerItem(coolantNaK60k, "coolantNaK60k");
        coolantNaK180k = new ItemCoolantCell(180000, "NaK180k");
        GameRegistry.registerItem(coolantNaK180k, "coolantNaK180k");
        coolantNaK360k = new ItemCoolantCell(360000, "NaK360k");
        GameRegistry.registerItem(coolantNaK360k, "coolantNaK360k");
        cellPlutonium1 = new ItemFuelRod(
            "cellPlutonium1",
            1,
            20000,
            2,
            2,
            2,
            IC2Items.getItem("reactorDepletedUraniumSimple"));
        GameRegistry.registerItem(cellPlutonium1, "cellPlutonium1");
        cellPlutonium2 = new ItemFuelRod(
            "cellPlutonium2",
            2,
            20000,
            2,
            2,
            2,
            IC2Items.getItem("reactorDepletedUraniumDual"));
        GameRegistry.registerItem(cellPlutonium2, "cellPlutonium2");
        cellPlutonium4 = new ItemFuelRod(
            "cellPlutonium4",
            4,
            20000,
            2,
            2,
            2,
            IC2Items.getItem("reactorDepletedUraniumQuad"));
        GameRegistry.registerItem(cellPlutonium4, "cellPlutonium4");
        cellThorium1 = new ItemFuelRod("cellThorium1", 1, 25000, 0.2f, 2, 0.5f, null);
        GameRegistry.registerItem(cellThorium1, "cellThorium1");
        cellThorium2 = new ItemFuelRod("cellThorium2", 2, 25000, 0.2f, 2, 0.5f, null);
        GameRegistry.registerItem(cellThorium2, "cellThorium2");
        cellThorium4 = new ItemFuelRod("cellThorium4", 4, 25000, 0.2f, 2, 0.5f, null);
        GameRegistry.registerItem(cellThorium4, "cellThorium4");

        rockCutter = PoweredItem.createItem(ItemRockCutter.class);
        GameRegistry.registerItem(rockCutter, "rockCutter");
        lithiumBatpack = PoweredItem.createItem(ItemLithiumBatpack.class);
        GameRegistry.registerItem(lithiumBatpack, "lithiumBatpack");
        lapotronpack = PoweredItem.createItem(ItemLapotronPack.class);
        GameRegistry.registerItem(lapotronpack, "lapotronPack");
        lithiumBattery = PoweredItem.createItem(ItemLithiumBattery.class);
        GameRegistry.registerItem(lithiumBattery, "lithiumBattery");
        lapotronicOrb = PoweredItem.createItem(ItemLapotronicOrb.class);
        GameRegistry.registerItem(lapotronicOrb, "lapotronicOrb");
        omniTool = PoweredItem.createItem(ItemOmniTool.class);
        GameRegistry.registerItem(omniTool, "omniTool");
        uuMatter = new ItemUUmatter();
        GameRegistry.registerItem(uuMatter, "uumatter");

        // upgrades = new ItemUpgrade();
        // GameRegistry.registerItem(upgrades, "upgrades");

        cloakingDevice = PoweredItem.createItem(ItemCloakingDevice.class);
        GameRegistry.registerItem(cloakingDevice, "cloakingdevice");

        // buckets
        bucketBerylium = new ItemFluidbucket(ModFluids.BlockFluidBerylium);
        bucketBerylium.setUnlocalizedName("bucketberyllium")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketBerylium, "bucketberyllium");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("beryllium", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketBerylium),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidBerylium, bucketBerylium);

        bucketcalcium = new ItemFluidbucket(ModFluids.BlockFluidCalcium);
        bucketcalcium.setUnlocalizedName("bucketcalcium")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketcalcium, "bucketcalcium");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("calcium", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketcalcium),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidCalcium, bucketcalcium);

        bucketcalciumcarbonate = new ItemFluidbucket(ModFluids.BlockFluidCalciumCarbonate);
        bucketcalciumcarbonate.setUnlocalizedName("bucketcalciumcarbonate")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketcalciumcarbonate, "bucketcalciumcarbonate");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("calciumcarbonate", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketcalciumcarbonate),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidCalciumCarbonate, bucketcalciumcarbonate);

        bucketChlorite = new ItemFluidbucket(ModFluids.BlockFluidChlorite);
        bucketChlorite.setUnlocalizedName("bucketchlorine")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketChlorite, "bucketchlorine");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("chlorine", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketcalciumcarbonate),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidChlorite, bucketChlorite);

        bucketDeuterium = new ItemFluidbucket(ModFluids.BlockFluidDeuterium);
        bucketDeuterium.setUnlocalizedName("bucketdeuterium")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketDeuterium, "bucketdeuterium");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("deuterium", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketDeuterium),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidDeuterium, bucketDeuterium);

        bucketGlyceryl = new ItemFluidbucket(ModFluids.BlockFluidGlyceryl);
        bucketGlyceryl.setUnlocalizedName("bucketglyceryl")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketGlyceryl, "bucketglyceryl");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("glyceryl", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketGlyceryl),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidGlyceryl, bucketGlyceryl);

        bucketHelium = new ItemFluidbucket(ModFluids.BlockFluidHelium);
        bucketHelium.setUnlocalizedName("buckethelium")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketHelium, "buckethelium");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("helium", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketHelium),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidHelium, bucketHelium);

        bucketHelium3 = new ItemFluidbucket(ModFluids.BlockFluidHelium3);
        bucketHelium3.setUnlocalizedName("buckethelium3")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketHelium3, "buckethelium3");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("helium3", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketHelium3),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidHelium3, bucketHelium3);

        bucketHeliumplasma = new ItemFluidbucket(ModFluids.BlockFluidHeliumplasma);
        bucketHeliumplasma.setUnlocalizedName("bucketheliumplasma")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketHeliumplasma, "bucketheliumplasma");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("heliumplasma", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketHeliumplasma),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidHeliumplasma, bucketHeliumplasma);

        bucketHydrogen = new ItemFluidbucket(ModFluids.BlockFluidHydrogen);
        bucketHydrogen.setUnlocalizedName("buckethydrogen")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketHydrogen, "buckethydrogen");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("hydrogen", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketHydrogen),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidHydrogen, bucketHydrogen);

        bucketLithium = new ItemFluidbucket(ModFluids.BlockFluidLithium);
        bucketLithium.setUnlocalizedName("bucketlithium")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketLithium, "bucketlithium");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("lithium", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketLithium),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidLithium, bucketLithium);

        bucketMercury = new ItemFluidbucket(ModFluids.BlockFluidMercury);
        bucketMercury.setUnlocalizedName("bucketmercury")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketMercury, "bucketmercury");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("mercury", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketMercury),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidMercury, bucketMercury);

        bucketMethane = new ItemFluidbucket(ModFluids.BlockFluidMethane);
        bucketMethane.setUnlocalizedName("bucketmethane")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketMethane, "bucketmethane");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("methane", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketMethane),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidMethane, bucketMethane);

        bucketNitrocoalfuel = new ItemFluidbucket(ModFluids.BlockFluidNitrocoalfuel);
        bucketNitrocoalfuel.setUnlocalizedName("bucketnitrocoalfuel")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketNitrocoalfuel, "bucketnitrocoalfuel");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("nitrocoalfuel", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketNitrocoalfuel),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidNitrocoalfuel, bucketNitrocoalfuel);

        bucketNitrofuel = new ItemFluidbucket(ModFluids.BlockFluidNitrofuel);
        bucketNitrofuel.setUnlocalizedName("bucketnitrofuel")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketNitrofuel, "bucketnitrodiesel");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("nitrodiesel", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketNitrofuel),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidNitrofuel, bucketNitrofuel);

        bucketNitrogen = new ItemFluidbucket(ModFluids.BlockFluidNitrogen);
        bucketNitrogen.setUnlocalizedName("bucketnitrogen")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketNitrogen, "bucketnitrogen");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("nitrogen", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketNitrogen),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidNitrogen, bucketNitrogen);

        bucketNitrogendioxide = new ItemFluidbucket(ModFluids.BlockFluidNitrogendioxide);
        bucketNitrogendioxide.setUnlocalizedName("bucketnitrogendioxide")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketNitrogendioxide, "bucketnitrogendioxide");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("nitrogendioxide", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketNitrogendioxide),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidNitrogendioxide, bucketNitrogendioxide);

        bucketPotassium = new ItemFluidbucket(ModFluids.BlockFluidPotassium);
        bucketPotassium.setUnlocalizedName("bucketpotassium")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketPotassium, "bucketpotassium");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("potassium", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketPotassium),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidPotassium, bucketPotassium);

        bucketSilicon = new ItemFluidbucket(ModFluids.BlockFluidSilicon);
        bucketSilicon.setUnlocalizedName("bucketsilicon")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketSilicon, "bucketsilicon");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("silicon", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketSilicon),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidSilicon, bucketSilicon);

        bucketSodium = new ItemFluidbucket(ModFluids.BlockFluidSodium);
        bucketSodium.setUnlocalizedName("bucketsodium")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketSodium, "bucketsodium");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("sodium", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketSodium),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidSodium, bucketSodium);

        bucketSodiumpersulfate = new ItemFluidbucket(ModFluids.BlockFluidSodiumpersulfate);
        bucketSodiumpersulfate.setUnlocalizedName("bucketsodiumpersulfate")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketSodiumpersulfate, "bucketsodiumpersulfate");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("sodiumpersulfate", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketSodiumpersulfate),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidSodiumpersulfate, bucketSodiumpersulfate);

        bucketTritium = new ItemFluidbucket(ModFluids.BlockFluidTritium);
        bucketTritium.setUnlocalizedName("buckettritium")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketTritium, "buckettritium");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("tritium", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketTritium),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidTritium, bucketTritium);

        bucketWolframium = new ItemFluidbucket(ModFluids.BlockFluidWolframium);
        bucketWolframium.setUnlocalizedName("buckettungsten")
            .setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketWolframium, "buckettungsten");
        FluidContainerRegistry.registerFluidContainer(
            FluidRegistry.getFluidStack("tungsten", FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucketWolframium),
            new ItemStack(Items.bucket));
        BucketHandler.INSTANCE.buckets.put(ModFluids.BlockFluidWolframium, bucketWolframium);

        MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

        Core.logHelper.info("TechReborns Items Loaded");

        registerOreDict();
    }

    public static void registerOreDict() {

        OreUnifier.registerOre("gemRuby", ItemGems.getGemByName("ruby"));
        OreUnifier.registerOre("gemSapphire", ItemGems.getGemByName("sapphire"));
        OreUnifier.registerOre("gemPeridot", ItemGems.getGemByName("peridot"));
        OreUnifier.registerOre("gemRedGarnet", ItemGems.getGemByName("redGarnet"));
        OreUnifier.registerOre("gemYellowGarnet", ItemGems.getGemByName("yellowGarnet"));
        OreUnifier.registerOre("gemOlivine", ItemGems.getGemByName("olivine"));

        // Dusts
        OreUnifier.registerOre("dustAlmandine", ItemDusts.getDustByName("almandine"));
        OreUnifier.registerOre("dustAluminum", ItemDusts.getDustByName("aluminium"));
        OreUnifier.registerOre("dustAluminium", ItemDusts.getDustByName("aluminium"));
        OreUnifier.registerOre("dustAndradite", ItemDusts.getDustByName("andradite"));
        OreUnifier.registerOre("dustAsh", ItemDusts.getDustByName("ashes"));
        OreUnifier.registerOre("dustBasalt", ItemDusts.getDustByName("basalt"));
        OreUnifier.registerOre("dustBauxite", ItemDusts.getDustByName("bauxite"));
        OreUnifier.registerOre("dustBrass", ItemDusts.getDustByName("brass"));
        OreUnifier.registerOre("dustBronze", ItemDusts.getDustByName("bronze"));
        OreUnifier.registerOre("dustCalcite", ItemDusts.getDustByName("calcite"));
        OreUnifier.registerOre("dustCharcoal", ItemDusts.getDustByName("charcoal"));
        OreUnifier.registerOre("dustChromium", ItemDusts.getDustByName("chromium"));
        OreUnifier.registerOre("dustCinnabar", ItemDusts.getDustByName("cinnabar"));
        OreUnifier.registerOre("dustClay", ItemDusts.getDustByName("clay"));
        OreUnifier.registerOre("dustCoal", ItemDusts.getDustByName("coal"));
        OreUnifier.registerOre("dustCopper", ItemDusts.getDustByName("copper"));
        OreUnifier.registerOre("dustDarkAsh", ItemDusts.getDustByName("darkAshes"));
        OreUnifier.registerOre("dustDiamond", ItemDusts.getDustByName("diamond"));
        OreUnifier.registerOre("dustElectrum", ItemDusts.getDustByName("electrum"));
        OreUnifier.registerOre("dustEmerald", ItemDusts.getDustByName("emerald"));
        OreUnifier.registerOre("dustEnderEye", ItemDusts.getDustByName("enderEye"));
        OreUnifier.registerOre("dustEnderPearl", ItemDusts.getDustByName("enderPearl"));
        OreUnifier.registerOre("dustEndstone", ItemDusts.getDustByName("endstone"));
        OreUnifier.registerOre("dustFlint", ItemDusts.getDustByName("flint"));
        OreUnifier.registerOre("dustWheat", ItemDusts.getDustByName("flour"));
        OreUnifier.registerOre("flour", ItemDusts.getDustByName("flour"));
        OreUnifier.registerOre("dustGalena", ItemDusts.getDustByName("galena"));
        OreUnifier.registerOre("dustGold", ItemDusts.getDustByName("gold"));
        OreUnifier.registerOre("dustGrossular", ItemDusts.getDustByName("grossular"));
        OreUnifier.registerOre("dustInvar", ItemDusts.getDustByName("invar"));
        OreUnifier.registerOre("dustIron", ItemDusts.getDustByName("iron"));
        OreUnifier.registerOre("dustLazurite", ItemDusts.getDustByName("lazurite"));
        OreUnifier.registerOre("dustLead", ItemDusts.getDustByName("lead"));
        OreUnifier.registerOre("dustMagnesium", ItemDusts.getDustByName("magnesium"));
        OreUnifier.registerOre("dustManganese", ItemDusts.getDustByName("manganese"));
        OreUnifier.registerOre("dustMarble", ItemDusts.getDustByName("marble"));
        OreUnifier.registerOre("dustNetherrack", ItemDusts.getDustByName("netherrack"));
        OreUnifier.registerOre("dustNickel", ItemDusts.getDustByName("nickel"));
        OreUnifier.registerOre("dustObsidian", ItemDusts.getDustByName("obsidian"));
        OreUnifier.registerOre("dustOsmium", ItemDusts.getDustByName("osmium"));
        OreUnifier.registerOre("dustPeridot", ItemDusts.getDustByName("peridot"));
        OreUnifier.registerOre("dustTricalciumPhosphate", ItemDusts.getDustByName("tricalciumPhosphate"));
        OreUnifier.registerOre("dustPlatinum", ItemDusts.getDustByName("platinum"));
        OreUnifier.registerOre("dustPyrite", ItemDusts.getDustByName("pyrite"));
        OreUnifier.registerOre("dustPyrope", ItemDusts.getDustByName("pyrope"));
        OreUnifier.registerOre("dustRedGarnet", ItemDusts.getDustByName("redGarnet"));
        OreUnifier.registerOre("dustRedrock", ItemDusts.getDustByName("redrock"));
        OreUnifier.registerOre("dustRuby", ItemDusts.getDustByName("ruby"));
        OreUnifier.registerOre("dustSaltpeter", ItemDusts.getDustByName("saltpeter"));
        OreUnifier.registerOre("dustSapphire", ItemDusts.getDustByName("sapphire"));
        OreUnifier.registerOre("dustSilver", ItemDusts.getDustByName("silver"));
        OreUnifier.registerOre("dustSodalite", ItemDusts.getDustByName("sodalite"));
        OreUnifier.registerOre("dustSpessartine", ItemDusts.getDustByName("spessartine"));
        OreUnifier.registerOre("dustSphalerite", ItemDusts.getDustByName("sphalerite"));
        OreUnifier.registerOre("dustSteel", ItemDusts.getDustByName("steel"));
        OreUnifier.registerOre("dustSulfur", ItemDusts.getDustByName("sulfur"));
        OreUnifier.registerOre("dustCopper", ItemDusts.getDustByName("copper"));
        OreUnifier.registerOre("dustTin", ItemDusts.getDustByName("tin"));
        OreUnifier.registerOre("dustTitanium", ItemDusts.getDustByName("titanium"));
        OreUnifier.registerOre("dustTungsten", ItemDusts.getDustByName("tungsten"));
        OreUnifier.registerOre("dustUvarovite", ItemDusts.getDustByName("uvarovite"));
        OreUnifier.registerOre("dustYellowGarnet", ItemDusts.getDustByName("yellowGarnet"));
        OreUnifier.registerOre("dustZinc", ItemDusts.getDustByName("zinc"));
        OreUnifier.registerOre("dustOlivine", ItemDusts.getDustByName("olivine"));
        OreUnifier.registerOre("pulpWood", ItemDusts.getDustByName("sawDust"));
        OreUnifier.registerOre("dustWood", ItemDusts.getDustByName("sawDust"));
        OreUnifier.registerOre("dustPlutonium", ItemDusts.getDustByName("plutonium"));
        OreUnifier.registerOre("dustThorium", ItemDusts.getDustByName("thorium"));
        OreUnifier.registerOre("dustUranium", ItemDusts.getDustByName("uranium"));

        // Small Dusts
        OreUnifier.registerOre("dustSmallAndradite", ItemDustsSmall.getSmallDustByName("andradite"));
        OreUnifier.registerOre("dustSmallAlmandine", ItemDustsSmall.getSmallDustByName("Almandine"));
        OreUnifier.registerOre("dustSmallAluminum", ItemDustsSmall.getSmallDustByName("Aluminium"));
        OreUnifier.registerOre("dustSmallAluminium", ItemDustsSmall.getSmallDustByName("Aluminium"));
        OreUnifier.registerOre("dustSmallAsh", ItemDustsSmall.getSmallDustByName("Ashes"));
        OreUnifier.registerOre("dustSmallBasalt", ItemDustsSmall.getSmallDustByName("Basalt"));
        OreUnifier.registerOre("dustSmallBauxite", ItemDustsSmall.getSmallDustByName("Bauxite"));
        OreUnifier.registerOre("dustSmallBrass", ItemDustsSmall.getSmallDustByName("Brass"));
        OreUnifier.registerOre("dustSmallBronze", ItemDustsSmall.getSmallDustByName("Bronze"));
        OreUnifier.registerOre("dustSmallCalcite", ItemDustsSmall.getSmallDustByName("Calcite"));
        OreUnifier.registerOre("dustSmallCharcoal", ItemDustsSmall.getSmallDustByName("Charcoal"));
        OreUnifier.registerOre("dustSmallChromium", ItemDustsSmall.getSmallDustByName("Chromium"));
        OreUnifier.registerOre("dustSmallCinnabar", ItemDustsSmall.getSmallDustByName("Cinnabar"));
        OreUnifier.registerOre("dustSmallClay", ItemDustsSmall.getSmallDustByName("Clay"));
        OreUnifier.registerOre("dustSmallCoal", ItemDustsSmall.getSmallDustByName("Coal"));
        OreUnifier.registerOre("dustSmallCopper", ItemDustsSmall.getSmallDustByName("Copper"));
        OreUnifier.registerOre("dustSmallDarkAsh", ItemDustsSmall.getSmallDustByName("DarkAshes"));
        OreUnifier.registerOre("dustSmallDiamond", ItemDustsSmall.getSmallDustByName("Diamond"));
        OreUnifier.registerOre("dustSmallElectrum", ItemDustsSmall.getSmallDustByName("Electrum"));
        OreUnifier.registerOre("dustSmallEmerald", ItemDustsSmall.getSmallDustByName("Emerald"));
        OreUnifier.registerOre("dustSmallEnderEye", ItemDustsSmall.getSmallDustByName("EnderEye"));
        OreUnifier.registerOre("dustSmallEnderPearl", ItemDustsSmall.getSmallDustByName("EnderPearl"));
        OreUnifier.registerOre("dustSmallEndstone", ItemDustsSmall.getSmallDustByName("Endstone"));
        OreUnifier.registerOre("dustSmallFlint", ItemDustsSmall.getSmallDustByName("Flint"));
        OreUnifier.registerOre("dustSmallGalena", ItemDustsSmall.getSmallDustByName("Galena"));
        OreUnifier.registerOre("dustSmallGlowstone", ItemDustsSmall.getSmallDustByName("Glowstone"));
        OreUnifier.registerOre("dustSmallGold", ItemDustsSmall.getSmallDustByName("Gold"));
        OreUnifier.registerOre("dustSmallGrossular", ItemDustsSmall.getSmallDustByName("Grossular"));
        OreUnifier.registerOre("dustSmallGunpowder", ItemDustsSmall.getSmallDustByName("Gunpowder"));
        OreUnifier.registerOre("dustSmallInvar", ItemDustsSmall.getSmallDustByName("Invar"));
        OreUnifier.registerOre("dustSmallIron", ItemDustsSmall.getSmallDustByName("Iron"));
        OreUnifier.registerOre("dustSmallLazurite", ItemDustsSmall.getSmallDustByName("Lazurite"));
        OreUnifier.registerOre("dustSmallLead", ItemDustsSmall.getSmallDustByName("Lead"));
        OreUnifier.registerOre("dustSmallMagnesium", ItemDustsSmall.getSmallDustByName("Magnesium"));
        OreUnifier.registerOre("dustSmallManganese", ItemDustsSmall.getSmallDustByName("Manganese"));
        OreUnifier.registerOre("dustSmallMarble", ItemDustsSmall.getSmallDustByName("Marble"));
        OreUnifier.registerOre("dustSmallNetherrack", ItemDustsSmall.getSmallDustByName("Netherrack"));
        OreUnifier.registerOre("dustSmallNickel", ItemDustsSmall.getSmallDustByName("Nickel"));
        OreUnifier.registerOre("dustSmallObsidian", ItemDustsSmall.getSmallDustByName("Obsidian"));
        OreUnifier.registerOre("dustSmallOsmium", ItemDustsSmall.getSmallDustByName("Osmium"));
        OreUnifier.registerOre("dustSmallPeridot", ItemDustsSmall.getSmallDustByName("Peridot"));
        OreUnifier
            .registerOre("dustSmallTricalciumPhosphate", ItemDustsSmall.getSmallDustByName("TricalciumPhosphate"));
        OreUnifier.registerOre("dustSmallPlatinum", ItemDustsSmall.getSmallDustByName("Platinum"));
        OreUnifier.registerOre("dustSmallPlutonium", ItemDustsSmall.getSmallDustByName("plutonium"));
        OreUnifier.registerOre("dustSmallPyrite", ItemDustsSmall.getSmallDustByName("Pyrite"));
        OreUnifier.registerOre("dustSmallPyrope", ItemDustsSmall.getSmallDustByName("Pyrope"));
        OreUnifier.registerOre("dustSmallRedGarnet", ItemDustsSmall.getSmallDustByName("RedGarnet"));
        OreUnifier.registerOre("dustSmallRedrock", ItemDustsSmall.getSmallDustByName("Redrock"));
        OreUnifier.registerOre("dustSmallRedstone", ItemDustsSmall.getSmallDustByName("Redstone"));
        OreUnifier.registerOre("dustSmallRuby", ItemDustsSmall.getSmallDustByName("Ruby"));
        OreUnifier.registerOre("dustSmallSaltpeter", ItemDustsSmall.getSmallDustByName("Saltpeter"));
        OreUnifier.registerOre("dustSmallSapphire", ItemDustsSmall.getSmallDustByName("Sapphire"));
        OreUnifier.registerOre("pulpSmallWood", ItemDustsSmall.getSmallDustByName("sawDust"));
        OreUnifier.registerOre("dustSmallWood", ItemDustsSmall.getSmallDustByName("sawDust"));
        OreUnifier.registerOre("dustSmallSilver", ItemDustsSmall.getSmallDustByName("Silver"));
        OreUnifier.registerOre("dustSmallSodalite", ItemDustsSmall.getSmallDustByName("Sodalite"));
        OreUnifier.registerOre("dustSmallSpessartine", ItemDustsSmall.getSmallDustByName("Spessartine"));
        OreUnifier.registerOre("dustSmallSphalerite", ItemDustsSmall.getSmallDustByName("Sphalerite"));
        OreUnifier.registerOre("dustSmallSteel", ItemDustsSmall.getSmallDustByName("Steel"));
        OreUnifier.registerOre("dustSmallSulfur", ItemDustsSmall.getSmallDustByName("Sulfur"));
        OreUnifier.registerOre("dustSmallCopper", ItemDustsSmall.getSmallDustByName("Copper"));
        OreUnifier.registerOre("dustSmallTin", ItemDustsSmall.getSmallDustByName("Tin"));
        OreUnifier.registerOre("dustSmallTitanium", ItemDustsSmall.getSmallDustByName("Titanium"));
        OreUnifier.registerOre("dustSmallThorium", ItemDustsSmall.getSmallDustByName("thorium"));
        OreUnifier.registerOre("dustSmallTungsten", ItemDustsSmall.getSmallDustByName("Tungsten"));
        OreUnifier.registerOre("dustSmallUranium", ItemDustsSmall.getSmallDustByName("uranium"));
        OreUnifier.registerOre("dustSmallUvarovite", ItemDustsSmall.getSmallDustByName("Uvarovite"));
        OreUnifier.registerOre("dustSmallYellowGarnet", ItemDustsSmall.getSmallDustByName("YellowGarnet"));
        OreUnifier.registerOre("dustSmallOlivine", ItemDustsSmall.getSmallDustByName("Olivine"));
        OreUnifier.registerOre("dustSmallZinc", ItemDustsSmall.getSmallDustByName("Zinc"));

        // Ingots
        OreUnifier.registerOre("ingotAluminum", ItemIngots.getIngotByName("aluminium"));
        OreUnifier.registerOre("ingotAluminium", ItemIngots.getIngotByName("aluminium"));
        OreUnifier.registerOre("ingotBrass", ItemIngots.getIngotByName("brass"));
        OreUnifier.registerOre("ingotChromium", ItemIngots.getIngotByName("chromium"));
        OreUnifier.registerOre("ingotCopper", ItemIngots.getIngotByName("copper"));
        OreUnifier.registerOre("ingotElectrum", ItemIngots.getIngotByName("electrum"));
        OreUnifier.registerOre("ingotInvar", ItemIngots.getIngotByName("invar"));
        OreUnifier.registerOre("ingotIridium", ItemIngots.getIngotByName("iridium"));
        OreUnifier.registerOre("ingotLead", ItemIngots.getIngotByName("lead"));
        OreUnifier.registerOre("ingotNickel", ItemIngots.getIngotByName("nickel"));
        OreUnifier.registerOre("ingotOsmium", ItemIngots.getIngotByName("osmium"));
        OreUnifier.registerOre("ingotPlatinum", ItemIngots.getIngotByName("platinum"));
        OreUnifier.registerOre("ingotPlutonium", ItemIngots.getIngotByName("plutonium"));
        OreUnifier.registerOre("ingotSilver", ItemIngots.getIngotByName("silver"));
        OreUnifier.registerOre("ingotSteel", ItemIngots.getIngotByName("steel"));
        OreUnifier.registerOre("ingotThorium", ItemIngots.getIngotByName("thorium"));
        OreUnifier.registerOre("ingotTitanium", ItemIngots.getIngotByName("titanium"));
        OreUnifier.registerOre("ingotTungsten", ItemIngots.getIngotByName("tungsten"));
        OreUnifier.registerOre("ingotTungstensteel", ItemIngots.getIngotByName("tungstensteel"));
        OreUnifier.registerOre("ingotHotTungstenSteel", ItemIngots.getIngotByName("hotTungstensteel"));
        OreUnifier.registerOre("ingotZinc", ItemIngots.getIngotByName("zinc"));

        // Nuggets
        OreUnifier.registerOre("nuggetAluminum", ItemNuggets.getNuggetByName("aluminium"));
        OreUnifier.registerOre("nuggetAluminium", ItemNuggets.getNuggetByName("aluminium"));
        OreUnifier.registerOre("nuggetBrass", ItemNuggets.getNuggetByName("brass"));
        OreUnifier.registerOre("nuggetBronze", ItemNuggets.getNuggetByName("bronze"));
        OreUnifier.registerOre("nuggetChromium", ItemNuggets.getNuggetByName("chromium"));
        OreUnifier.registerOre("nuggetCopper", ItemNuggets.getNuggetByName("copper"));
        OreUnifier.registerOre("nuggetElectrum", ItemNuggets.getNuggetByName("electrum"));
        OreUnifier.registerOre("nuggetInvar", ItemNuggets.getNuggetByName("invar"));
        OreUnifier.registerOre("nuggetIridium", ItemNuggets.getNuggetByName("iridium"));
        OreUnifier.registerOre("nuggetIron", ItemNuggets.getNuggetByName("iron"));
        OreUnifier.registerOre("nuggetLead", ItemNuggets.getNuggetByName("lead"));
        OreUnifier.registerOre("nuggetNickel", ItemNuggets.getNuggetByName("nickel"));
        OreUnifier.registerOre("nuggetOsmium", ItemNuggets.getNuggetByName("osmium"));
        OreUnifier.registerOre("nuggetPlatinum", ItemNuggets.getNuggetByName("platinum"));
        OreUnifier.registerOre("nuggetSilver", ItemNuggets.getNuggetByName("silver"));
        OreUnifier.registerOre("nuggetSteel", ItemNuggets.getNuggetByName("steel"));
        OreUnifier.registerOre("nuggetTin", ItemNuggets.getNuggetByName("tin"));
        OreUnifier.registerOre("nuggetTitanium", ItemNuggets.getNuggetByName("titanium"));
        OreUnifier.registerOre("nuggetTungsten", ItemNuggets.getNuggetByName("tungsten"));
        OreUnifier.registerOre("nuggetZinc", ItemNuggets.getNuggetByName("zinc"));

        // Plates
        OreUnifier.registerOre("plateAluminum", ItemPlates.getPlateByName("aluminium"));
        OreUnifier.registerOre("plateAluminium", ItemPlates.getPlateByName("aluminium"));
        OreUnifier.registerOre("plateBrass", ItemPlates.getPlateByName("brass"));
        OreUnifier.registerOre("plateBronze", ItemPlates.getPlateByName("bronze"));
        OreUnifier.registerOre("plateChromium", ItemPlates.getPlateByName("chromium"));
        OreUnifier.registerOre("plateCopper", ItemPlates.getPlateByName("copper"));
        OreUnifier.registerOre("plateElectrum", ItemPlates.getPlateByName("electrum"));
        OreUnifier.registerOre("plateGold", ItemPlates.getPlateByName("gold"));
        OreUnifier.registerOre("plateInvar", ItemPlates.getPlateByName("invar"));
        OreUnifier.registerOre("plateIron", ItemPlates.getPlateByName("iron"));
        OreUnifier.registerOre("plateLead", ItemPlates.getPlateByName("lead"));
        OreUnifier.registerOre("plateMagnalium", ItemPlates.getPlateByName("magnalium"));
        OreUnifier.registerOre("plateNickel", ItemPlates.getPlateByName("nickel"));
        OreUnifier.registerOre("plateOsmium", ItemPlates.getPlateByName("osmium"));
        OreUnifier.registerOre("platePlatinum", ItemPlates.getPlateByName("platinum"));
        OreUnifier.registerOre("plateSilicon", ItemPlates.getPlateByName("silicon"));
        OreUnifier.registerOre("plateSilver", ItemPlates.getPlateByName("silver"));
        OreUnifier.registerOre("plateSteel", ItemPlates.getPlateByName("steel"));
        OreUnifier.registerOre("plateTin", ItemPlates.getPlateByName("tin"));
        OreUnifier.registerOre("plateTitanium", ItemPlates.getPlateByName("titanium"));
        OreUnifier.registerOre("plateTungsten", ItemPlates.getPlateByName("tungsten"));
        OreUnifier.registerOre("plateTungstensteel", ItemPlates.getPlateByName("tungstensteel"));
        OreUnifier.registerOre("plateWood", ItemPlates.getPlateByName("wood"));
        OreUnifier.registerOre("plankWood", ItemPlates.getPlateByName("wood"));
        OreUnifier.registerOre("plateZinc", ItemPlates.getPlateByName("zinc"));

        // Crushed Ore
        OreUnifier.registerOre("crushedBauxite", ItemCrushedOre.getCrushedOreByName("Bauxite"));
        OreUnifier.registerOre("crushedCinnabar", ItemCrushedOre.getCrushedOreByName("Cinnabar"));
        OreUnifier.registerOre("crushedGalena", ItemCrushedOre.getCrushedOreByName("Galena"));
        OreUnifier.registerOre("crushedIridium", ItemCrushedOre.getCrushedOreByName("Iridium"));
        OreUnifier.registerOre("crushedPlatinum", ItemCrushedOre.getCrushedOreByName("Platinum"));
        OreUnifier.registerOre("crushedPyrite", ItemCrushedOre.getCrushedOreByName("Pyrite"));
        OreUnifier.registerOre("crushedSphalerite", ItemCrushedOre.getCrushedOreByName("Sphalerite"));
        OreUnifier.registerOre("crushedTungsten", ItemCrushedOre.getCrushedOreByName("Tungsten"));

        // Purified Crushed Ore
        OreUnifier.registerOre("crushedPurifiedBauxite", ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Bauxite"));
        OreUnifier
            .registerOre("crushedPurifiedCinnabar", ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Cinnabar"));
        OreUnifier.registerOre("crushedPurifiedGalena", ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Galena"));
        OreUnifier.registerOre("crushedPurifiedIridium", ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Iridium"));
        OreUnifier
            .registerOre("crushedPurifiedPlatinum", ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Platinum"));
        OreUnifier.registerOre("crushedPurifiedPyrite", ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Pyrite"));
        OreUnifier
            .registerOre("crushedPurifiedSphalerite", ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Sphalerite"));
        OreUnifier
            .registerOre("crushedPurifiedTungsten", ItemPurifiedCrushedOre.getPurifiedCrushedOreByName("Tungsten"));

        OreUnifier.registerOre("craftingGrinder", ItemParts.getPartByName("diamondGrindingHead"));
        OreUnifier.registerOre("craftingGrinder", ItemParts.getPartByName("tungstenGrindingHead"));
        OreUnifier.registerOre("circuitMaster", ItemParts.getPartByName("energyFlowCircuit"));
        OreUnifier.registerOre("circuitElite", ItemParts.getPartByName("dataControlCircuit"));
        OreUnifier.registerOre("circuitData", ItemParts.getPartByName("dataStorageCircuit"));
        OreUnifier.registerOre("craftingSuperconductor", ItemParts.getPartByName("superconductor"));
        OreDictionary.registerOre("chunkLazurite", ItemParts.getPartByName("lazuriteChunk"));
        for (ItemStack stack : OreDictionary.getOres("blockLapis")) {
            OreDictionary.registerOre("chunkLazurite", stack);
        }
        OreDictionary
            .registerOre("battery100k", new ItemStack(ModItems.lithiumBattery, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary
            .registerOre("battery600k", new ItemStack(ModItems.lithiumBatpack, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary
            .registerOre("battery100M", new ItemStack(ModItems.lapotronicOrb, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("battery1G", new ItemStack(ModItems.lapotronpack, 1, OreDictionary.WILDCARD_VALUE));
        for (ItemStack i : OreDictionary.getOres("pulpWood")) {
            OreDictionary.registerOre("dustWood", i);
        }
    }

}
