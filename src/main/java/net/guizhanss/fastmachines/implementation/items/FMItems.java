package net.guizhanss.fastmachines.implementation.items;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun5.implementation.items.blocks.UnplaceableBlock;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.implementation.groups.FMItemGroups;
import net.guizhanss.fastmachines.implementation.items.machines.infinityexpansion.FastInfinityWorkbench;
import net.guizhanss.fastmachines.implementation.items.machines.infinityexpansion.FastMobDataInfuser;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastAncientAltar;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastArmorForge;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastComposter;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastCompressor;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastEnhancedCraftingTable;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastGrindStone;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastJuicer;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastMagicWorkbench;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastOreCrusher;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastOreWasher;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastPanningMachine;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastPressureChamber;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastSmeltery;
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastTableSaw;
import net.guizhanss.fastmachines.implementation.items.machines.vanilla.FastCraftingTable;
import net.guizhanss.fastmachines.implementation.items.machines.vanilla.FastFurnace;
import net.guizhanss.fastmachines.implementation.items.materials.StackedAncientPedestal;
import net.guizhanss.fastmachines.libs.guizhanlib.slimefun.items.builder.RecipeBuilder;
import net.guizhanss.fastmachines.utils.MaterialCompat;
import net.guizhanss.fastmachines.utils.items.SlimefunItemExt;
import net.guizhanss.fastmachines.utils.items.builder.SlimefunItemBuilder;

/**
 * Every Material reference here goes through XMaterial + MaterialCompat.safe so this class can be
 * initialised on legacy servers (1.8-1.12) where constants like CONDUIT/RESPAWN_ANCHOR/GRINDSTONE
 * simply do not exist (a raw Material.X would throw NoSuchFieldError at class-init).
 * <p>
 * NOTE: FastMachines originally also shipped fast-machine variants for SlimeFrame's Foundry and
 * InfinityExpansion2's Infinity Workbench/Mob Data Infuser. Neither SlimeFrame nor InfinityExpansion2
 * have a slimefun5-compatible release yet, so those three items - and their recipe loaders /
 * registrations - were dropped rather than left as a broken compile dependency. FLAG: re-add once
 * those addons are ported to this fork.
 */
public final class FMItems {

    // Materials
    public static SlimefunItemStack ETERNAL_FIRE;
    public static SlimefunItemStack FAST_CORE;
    public static SlimefunItemStack STACKED_ANCIENT_PEDESTAL;

    // Machines - Vanilla
    public static SlimefunItemStack FAST_CRAFTING_TABLE;
    public static SlimefunItemStack FAST_FURNACE;

    // Machines - Slimefun
    public static SlimefunItemStack FAST_ENHANCED_CRAFTING_TABLE;
    public static SlimefunItemStack FAST_GRIND_STONE;
    public static SlimefunItemStack FAST_ARMOR_FORGE;
    public static SlimefunItemStack FAST_ORE_CRUSHER;
    public static SlimefunItemStack FAST_COMPRESSOR;
    public static SlimefunItemStack FAST_SMELTERY;
    public static SlimefunItemStack FAST_PRESSURE_CHAMBER;
    public static SlimefunItemStack FAST_MAGIC_WORKBENCH;
    public static SlimefunItemStack FAST_ORE_WASHER;
    public static SlimefunItemStack FAST_TABLE_SAW;
    public static SlimefunItemStack FAST_COMPOSTER;
    public static SlimefunItemStack FAST_PANNING_MACHINE;
    public static SlimefunItemStack FAST_JUICER;
    public static SlimefunItemStack FAST_ANCIENT_ALTAR;

    // Machines - InfinityExpansion
    public static SlimefunItemStack FAST_INFINITY_WORKBENCH;
    public static SlimefunItemStack FAST_MOB_DATA_INFUSER;

    private FMItems() {
    }

    public static void setup() {
        final String prefix = FastMachines.getLocalization().getIdPrefix();
        final boolean useEnergy = FastMachines.getConfigService().getFmUseEnergy().getValue();

        // ---- Materials ----
        ETERNAL_FIRE = new SlimefunItemBuilder(prefix)
            .id("ETERNAL_FIRE")
            .material(MaterialCompat.safe(XMaterial.IRON_INGOT))
            .itemGroup(FMItemGroups.MATERIALS)
            .guideType("resources")
            .recipeType(RecipeType.MAGIC_WORKBENCH)
            .recipe(RecipeBuilder.create()
                .row("   ").row("FFF").row("CNC")
                .where('F', XMaterial.FLINT_AND_STEEL)
                .where('C', SlimefunItems.IGNITION_CHAMBER)
                .where('N', XMaterial.NETHERRACK)
                .build())
            .build(UnplaceableBlock::new);

        FAST_CORE = new SlimefunItemBuilder(prefix)
            .id("FAST_CORE")
            .material(MaterialCompat.safe(XMaterial.CONDUIT))
            .itemGroup(FMItemGroups.MATERIALS)
            .guideType("resources")
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("   ").row(" C ").row(" R ")
                .where('C', SlimefunItems.SMALL_CAPACITOR)
                .where('R', SlimefunItems.REDSTONE_ALLOY)
                .build())
            .build(UnplaceableBlock::new);

        STACKED_ANCIENT_PEDESTAL = new SlimefunItemBuilder(prefix)
            .id("STACKED_ANCIENT_PEDESTAL")
            .material(MaterialCompat.safe(XMaterial.DISPENSER))
            .itemGroup(FMItemGroups.MATERIALS)
            .guideType("resources")
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("A A").row("   ").row("A A")
                .where('A', SlimefunItems.ANCIENT_PEDESTAL)
                .build())
            .build(StackedAncientPedestal::new);

        // ---- Machines - Vanilla ----
        FAST_CRAFTING_TABLE = new SlimefunItemBuilder(prefix)
            .id("FAST_CRAFTING_TABLE")
            .material(MaterialCompat.safe(XMaterial.CRAFTING_TABLE))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row(" c ").row(" C ").row(" o ")
                .where('c', XMaterial.CHEST)
                .where('C', XMaterial.CRAFTING_TABLE)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastCraftingTable::new);

        FAST_FURNACE = new SlimefunItemBuilder(prefix)
            .id("FAST_FURNACE")
            .material(MaterialCompat.safe(XMaterial.FURNACE))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row(" c ").row(" F ").row(" o ")
                .where('c', XMaterial.CHEST)
                .where('F', XMaterial.FURNACE)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastFurnace::new);

        // ---- Machines - Slimefun ----
        FAST_ENHANCED_CRAFTING_TABLE = new SlimefunItemBuilder(prefix)
            .id("FAST_ENHANCED_CRAFTING_TABLE")
            .material(MaterialCompat.safe(XMaterial.CARTOGRAPHY_TABLE))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("   ").row(" C ").row("ODo")
                .where('C', XMaterial.CRAFTING_TABLE)
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('D', XMaterial.DISPENSER)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastEnhancedCraftingTable::new);

        FAST_GRIND_STONE = new SlimefunItemBuilder(prefix)
            .id("FAST_GRIND_STONE")
            .material(MaterialCompat.safe(XMaterial.GRINDSTONE))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("   ").row(" F ").row("ODo")
                .where('F', XMaterial.OAK_FENCE)
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('D', XMaterial.DISPENSER)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastGrindStone::new);

        FAST_ARMOR_FORGE = new SlimefunItemBuilder(prefix)
            .id("FAST_ARMOR_FORGE")
            .material(MaterialCompat.safe(XMaterial.IRON_BLOCK))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("   ").row(" A ").row("ODo")
                .where('A', XMaterial.ANVIL)
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('D', XMaterial.DISPENSER)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastArmorForge::new);

        FAST_ORE_CRUSHER = new SlimefunItemBuilder(prefix)
            .id("FAST_ORE_CRUSHER")
            .material(MaterialCompat.safe(XMaterial.DROPPER))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("   ").row("OFo").row("BDB")
                .where('F', XMaterial.NETHER_BRICK_FENCE)
                .where('B', XMaterial.IRON_BARS)
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('D', XMaterial.DISPENSER)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastOreCrusher::new);

        FAST_COMPRESSOR = new SlimefunItemBuilder(prefix)
            .id("FAST_COMPRESSOR")
            .material(MaterialCompat.safe(XMaterial.PISTON))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("   ").row("OFo").row("PDP")
                .where('F', XMaterial.NETHER_BRICK_FENCE)
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('P', XMaterial.PISTON)
                .where('D', XMaterial.DISPENSER)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastCompressor::new);

        FAST_SMELTERY = new SlimefunItemBuilder(prefix)
            .id("FAST_SMELTERY")
            .material(MaterialCompat.safe(XMaterial.BLAST_FURNACE))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row(" F ").row("BDB").row("Ofo")
                .where('F', XMaterial.NETHER_BRICK_FENCE)
                .where('B', XMaterial.NETHER_BRICKS)
                .where('D', XMaterial.DISPENSER)
                .where('f', ETERNAL_FIRE)
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastSmeltery::new);

        FAST_PRESSURE_CHAMBER = new SlimefunItemBuilder(prefix)
            .id("FAST_PRESSURE_CHAMBER")
            .material(MaterialCompat.safe(XMaterial.SMOKER))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("ODo").row("PGP").row("PCP")
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('D', XMaterial.DISPENSER)
                .where('o', useEnergy ? FAST_CORE : null)
                .where('P', XMaterial.PISTON)
                .where('G', XMaterial.GLASS)
                .where('C', XMaterial.CAULDRON)
                .build())
            .build(FastPressureChamber::new);

        FAST_MAGIC_WORKBENCH = new SlimefunItemBuilder(prefix)
            .id("FAST_MAGIC_WORKBENCH")
            .material(MaterialCompat.safe(XMaterial.BOOKSHELF))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.MAGIC_WORKBENCH)
            .recipe(RecipeBuilder.create()
                .row("   ").row("O o").row("BCD")
                .where('B', XMaterial.BOOKSHELF)
                .where('C', XMaterial.CRAFTING_TABLE)
                .where('D', XMaterial.DISPENSER)
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastMagicWorkbench::new);

        FAST_ORE_WASHER = new SlimefunItemBuilder(prefix)
            .id("FAST_ORE_WASHER")
            .material(MaterialCompat.safe(XMaterial.CAULDRON))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row(" G ").row(" F ").row("ODo")
                .where('G', XMaterial.GLASS)
                .where('F', XMaterial.OAK_FENCE)
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('D', XMaterial.DISPENSER)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastOreWasher::new);

        FAST_TABLE_SAW = new SlimefunItemBuilder(prefix)
            .id("FAST_TABLE_SAW")
            .material(MaterialCompat.safe(XMaterial.STONECUTTER))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("   ").row("SCS").row("OIo")
                .where('S', XMaterial.SMOOTH_STONE_SLAB)
                .where('C', XMaterial.STONECUTTER)
                .where('I', XMaterial.IRON_BLOCK)
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastTableSaw::new);

        FAST_COMPOSTER = new SlimefunItemBuilder(prefix)
            .id("FAST_COMPOSTER")
            .material(MaterialCompat.safe(XMaterial.COMPOSTER))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("SOS").row("SoS").row("SCS")
                .where('S', XMaterial.OAK_SLAB)
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('o', useEnergy ? FAST_CORE : null)
                .where('C', XMaterial.CAULDRON)
                .build())
            .build(FastComposter::new);

        FAST_PANNING_MACHINE = new SlimefunItemBuilder(prefix)
            .id("FAST_PANNING_MACHINE")
            .material(MaterialCompat.safe(XMaterial.HOPPER))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("   ").row(" T ").row("OCo")
                .where('T', XMaterial.OAK_TRAPDOOR)
                .where('C', XMaterial.CAULDRON)
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastPanningMachine::new);

        FAST_JUICER = new SlimefunItemBuilder(prefix)
            .id("FAST_JUICER")
            .material(MaterialCompat.safe(XMaterial.BREWING_STAND))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row(" G ").row(" F ").row("ODo")
                .where('G', XMaterial.GLASS)
                .where('F', XMaterial.NETHER_BRICK_FENCE)
                .where('D', XMaterial.DISPENSER)
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('o', useEnergy ? FAST_CORE : null)
                .build())
            .build(FastJuicer::new);

        FAST_ANCIENT_ALTAR = new SlimefunItemBuilder(prefix)
            .id("FAST_ANCIENT_ALTAR")
            .material(MaterialCompat.safe(XMaterial.ENCHANTING_TABLE))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.MAGIC_WORKBENCH)
            .recipe(RecipeBuilder.create()
                .row("   ").row(" o ").row("PAP")
                .where('o', useEnergy ? FAST_CORE : null)
                .where('P', STACKED_ANCIENT_PEDESTAL)
                .where('A', SlimefunItems.ANCIENT_ALTAR)
                .build())
            .build(FastAncientAltar::new);

        // ---- Machines - InfinityExpansion ----
        SlimefunItem infinityForge = SlimefunItemExt.getSfItem("INFINITY_FORGE");
        FAST_INFINITY_WORKBENCH = new SlimefunItemBuilder(prefix)
            .id("FAST_INFINITY_WORKBENCH")
            .material(MaterialCompat.safe(XMaterial.RESPAWN_ANCHOR))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("   ").row("   ").row("Omo")
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('o', useEnergy ? FAST_CORE : null)
                .where('m', infinityForge != null ? infinityForge.getItem() : (ItemStack) null)
                .build())
            .build(FastInfinityWorkbench::new);

        SlimefunItem dataInfuser = SlimefunItemExt.getSfItem("DATA_INFUSER");
        FAST_MOB_DATA_INFUSER = new SlimefunItemBuilder(prefix)
            .id("FAST_MOB_DATA_INFUSER")
            .material(MaterialCompat.safe(XMaterial.LODESTONE))
            .itemGroup(FMItemGroups.MACHINES)
            .recipeType(RecipeType.ENHANCED_CRAFTING_TABLE)
            .recipe(RecipeBuilder.create()
                .row("   ").row("   ").row("Omo")
                .where('O', SlimefunItems.OUTPUT_CHEST)
                .where('o', useEnergy ? FAST_CORE : null)
                .where('m', dataInfuser != null ? dataInfuser.getItem() : (ItemStack) null)
                .build())
            .build(FastMobDataInfuser::new);
    }
}
