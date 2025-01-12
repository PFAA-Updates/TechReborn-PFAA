package techreborn.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import reborncore.common.util.OreDrop;
import reborncore.common.util.OreDropSet;
import techreborn.client.TechRebornCreativeTabMisc;
import techreborn.config.ConfigTechReborn;
import techreborn.init.ModBlocks;
import techreborn.items.ItemDusts;
import techreborn.items.ItemGems;
import techreborn.utils.RecipeUtils;

public class BlockOre extends Block {

    @SuppressWarnings("deprecation")
    public static ItemStack getOreByName(String name, int count) {
        int meta = RecipeUtils.getArrayPos(types, name);
        if (meta == -1) throw new IllegalArgumentException("The ore " + name + " could not be found!");
        return new ItemStack(ModBlocks.ore, count, meta);
    }

    public static ItemStack getOreByName(String name) {
        return getOreByName(name, 1);
    }

    public static final String[] types = new String[] { "Galena", "Iridium", "Ruby", "Sapphire", "Bauxite", "Pyrite",
        "Cinnabar", "Sphalerite", "Tungsten", "Sheldonite", "Olivine", "Sodalite" };

    private IIcon[] textures;

    public BlockOre(Material material) {
        super(material);
        setBlockName("techreborn.ore");
        setCreativeTab(TechRebornCreativeTabMisc.instance);
        setHardness(2.0f);
        ModBlocks.blocksToCut.add(this);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        // Ruby
        if (metadata == RecipeUtils.getArrayPos(types, "Ruby")) {
            OreDrop ruby = new OreDrop(
                ItemGems.getGemByName("ruby"),
                ConfigTechReborn.FortuneSecondaryOreMultiplierPerLevel);
            OreDrop redGarnet = new OreDrop(ItemGems.getGemByName("redGarnet"), 0.02);
            OreDropSet set = new OreDropSet(ruby, redGarnet);
            return set.drop(fortune, world.rand);
        }

        // Sapphire
        if (metadata == RecipeUtils.getArrayPos(types, "Sapphire")) {
            OreDrop sapphire = new OreDrop(
                ItemGems.getGemByName("sapphire"),
                ConfigTechReborn.FortuneSecondaryOreMultiplierPerLevel);
            OreDrop peridot = new OreDrop(ItemGems.getGemByName("peridot"), 0.03);
            OreDropSet set = new OreDropSet(sapphire, peridot);
            return set.drop(fortune, world.rand);
        }

        // Pyrite
        if (metadata == RecipeUtils.getArrayPos(types, "Pyrite")) {
            OreDrop pyriteDust = new OreDrop(
                ItemDusts.getDustByName("pyrite"),
                ConfigTechReborn.FortuneSecondaryOreMultiplierPerLevel);
            OreDropSet set = new OreDropSet(pyriteDust);
            return set.drop(fortune, world.rand);
        }

        // Sodolite
        if (metadata == RecipeUtils.getArrayPos(types, "Sodalite")) {
            OreDrop sodalite = new OreDrop(
                ItemDusts.getDustByName("sodalite", 6),
                ConfigTechReborn.FortuneSecondaryOreMultiplierPerLevel);
            OreDrop aluminum = new OreDrop(ItemDusts.getDustByName("aluminium"), 0.50);
            OreDropSet set = new OreDropSet(sodalite, aluminum);
            return set.drop(fortune, world.rand);
        }

        // Cinnabar
        if (metadata == RecipeUtils.getArrayPos(types, "Cinnabar")) {
            OreDrop cinnabar = new OreDrop(
                ItemDusts.getDustByName("cinnabar"),
                ConfigTechReborn.FortuneSecondaryOreMultiplierPerLevel);
            OreDrop redstone = new OreDrop(new ItemStack(Items.redstone), 0.25);
            OreDropSet set = new OreDropSet(cinnabar, redstone);
            return set.drop(fortune, world.rand);
        }

        // Sphalerite 1, 1/8 yellow garnet
        if (metadata == RecipeUtils.getArrayPos(types, "Sphalerite")) {
            OreDrop sphalerite = new OreDrop(
                ItemDusts.getDustByName("sphalerite"),
                ConfigTechReborn.FortuneSecondaryOreMultiplierPerLevel);
            OreDrop yellowGarnet = new OreDrop(ItemGems.getGemByName("yellowGarnet"), 0.125);
            OreDropSet set = new OreDropSet(sphalerite, yellowGarnet);
            return set.drop(fortune, world.rand);
        }

        ArrayList<ItemStack> block = new ArrayList<ItemStack>();
        block.add(new ItemStack(Item.getItemFromBlock(this), 1, metadata));
        return block;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
        for (int meta = 0; meta < types.length; meta++) {
            list.add(new ItemStack(item, 1, meta));
        }
    }

    @Override
    public int damageDropped(int metaData) {
        if (metaData == 2) {
            return 0;
        } else if (metaData == 3) {
            return 1;
        } else if (metaData == 5) {
            return 60;
        }
        return metaData;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[types.length];

        for (int i = 0; i < types.length; i++) {
            textures[i] = iconRegister.registerIcon("techreborn:" + "ore/ore" + types[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metaData) {
        metaData = MathHelper.clamp_int(metaData, 0, types.length - 1);

        if (ForgeDirection.getOrientation(side) == ForgeDirection.UP
            || ForgeDirection.getOrientation(side) == ForgeDirection.DOWN) {
            return textures[metaData];
        } else {
            return textures[metaData];
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        return new ItemStack(ModBlocks.ore, 1, world.getBlockMetadata(x, y, z));
    }
}
