@file:Suppress("unused")

package net.guizhanss.fastmachines.implementation.items

import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems
import io.github.thebusybiscuit.slimefun5.implementation.items.blocks.UnplaceableBlock
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.implementation.groups.FMItemGroups
import net.guizhanss.fastmachines.implementation.items.machines.infinityexpansion.FastInfinityWorkbench
import net.guizhanss.fastmachines.implementation.items.machines.infinityexpansion.FastMobDataInfuser
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastAncientAltar
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastArmorForge
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastComposter
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastCompressor
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastEnhancedCraftingTable
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastGrindStone
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastJuicer
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastMagicWorkbench
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastOreCrusher
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastOreWasher
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastPanningMachine
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastPressureChamber
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastSmeltery
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastTableSaw
import net.guizhanss.fastmachines.implementation.items.machines.vanilla.FastCraftingTable
import net.guizhanss.fastmachines.implementation.items.machines.vanilla.FastFurnace
import net.guizhanss.fastmachines.implementation.items.materials.StackedAncientPedestal
import net.guizhanss.fastmachines.libs.guizhanlib.slimefun.items.builder.ItemRegistry
import net.guizhanss.fastmachines.libs.guizhanlib.slimefun.items.builder.asMaterialType
import net.guizhanss.fastmachines.libs.guizhanlib.slimefun.items.builder.buildRecipe
import net.guizhanss.fastmachines.utils.MaterialCompat
import net.guizhanss.fastmachines.utils.items.builder.buildSlimefunItem
import net.guizhanss.fastmachines.utils.items.getSfItem

// Every Material reference here goes through XMaterial + MaterialCompat.safe so this object can be
// initialised on legacy servers (1.8-1.12) where constants like CONDUIT/RESPAWN_ANCHOR/GRINDSTONE
// simply do not exist (a raw Material.X would throw NoSuchFieldError at class-init).
//
// NOTE: FastMachines originally also shipped fast-machine variants for SlimeFrame's Foundry and
// InfinityExpansion2's Infinity Workbench/Mob Data Infuser. Neither SlimeFrame nor InfinityExpansion2
// have a slimefun5-compatible release yet (both are still coupled to the pre-fork slimefun4 API /
// Java 16), so those three items - and their recipe loaders/registrations - were dropped rather than
// left as a broken compile dependency. FLAG: re-add once those addons are ported to this fork.
object FMItems : ItemRegistry(FastMachines.instance, FastMachines.localization.idPrefix) {

    //<editor-fold desc="Materials" collapsed="true">
    val ETERNAL_FIRE by buildSlimefunItem<UnplaceableBlock> {
        material = MaterialCompat.safe(XMaterial.IRON_INGOT).asMaterialType()
        itemGroup = FMItemGroups.MATERIALS
        recipeType = RecipeType.MAGIC_WORKBENCH
        recipe = buildRecipe {
            +"   "
            +"FFF"
            +"CNC"
            'F' means XMaterial.FLINT_AND_STEEL
            'C' means SlimefunItems.IGNITION_CHAMBER
            'N' means XMaterial.NETHERRACK
        }
    }

    val FAST_CORE by buildSlimefunItem<UnplaceableBlock> {
        material = MaterialCompat.safe(XMaterial.CONDUIT).asMaterialType()
        itemGroup = FMItemGroups.MATERIALS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +" C "
            +" R "
            'C' means SlimefunItems.SMALL_CAPACITOR
            'R' means SlimefunItems.REDSTONE_ALLOY
        }
    }

    val STACKED_ANCIENT_PEDESTAL by buildSlimefunItem<StackedAncientPedestal> {
        material = MaterialCompat.safe(XMaterial.DISPENSER).asMaterialType()
        itemGroup = FMItemGroups.MATERIALS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"A A"
            +"   "
            +"A A"
            'A' means SlimefunItems.ANCIENT_PEDESTAL
        }
    }
    //</editor-fold>

    //<editor-fold desc="Machines - Vanilla" collapsed="true">
    val FAST_CRAFTING_TABLE by buildSlimefunItem<FastCraftingTable> {
        material = MaterialCompat.safe(XMaterial.CRAFTING_TABLE).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +" c "
            +" C "
            +" o "
            'c' means XMaterial.CHEST
            'C' means XMaterial.CRAFTING_TABLE
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_FURNACE by buildSlimefunItem<FastFurnace> {
        material = MaterialCompat.safe(XMaterial.FURNACE).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +" c "
            +" F "
            +" o "
            'c' means XMaterial.CHEST
            'F' means XMaterial.FURNACE
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }
    //</editor-fold>

    //<editor-fold desc="Machines - Slimefun" collapsed="true">
    val FAST_ENHANCED_CRAFTING_TABLE by buildSlimefunItem<FastEnhancedCraftingTable> {
        material = MaterialCompat.safe(XMaterial.CARTOGRAPHY_TABLE).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +" C "
            +"ODo"
            'C' means XMaterial.CRAFTING_TABLE
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means XMaterial.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_GRIND_STONE by buildSlimefunItem<FastGrindStone> {
        material = MaterialCompat.safe(XMaterial.GRINDSTONE).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +" F "
            +"ODo"
            'F' means XMaterial.OAK_FENCE
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means XMaterial.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_ARMOR_FORGE by buildSlimefunItem<FastArmorForge> {
        material = MaterialCompat.safe(XMaterial.IRON_BLOCK).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +" A "
            +"ODo"
            'A' means XMaterial.ANVIL
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means XMaterial.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_ORE_CRUSHER by buildSlimefunItem<FastOreCrusher> {
        material = MaterialCompat.safe(XMaterial.DROPPER).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"OFo"
            +"BDB"
            'F' means XMaterial.NETHER_BRICK_FENCE
            'B' means XMaterial.IRON_BARS
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means XMaterial.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_COMPRESSOR by buildSlimefunItem<FastCompressor> {
        material = MaterialCompat.safe(XMaterial.PISTON).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"OFo"
            +"PDP"
            'F' means XMaterial.NETHER_BRICK_FENCE
            'O' means SlimefunItems.OUTPUT_CHEST
            'P' means XMaterial.PISTON
            'D' means XMaterial.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_SMELTERY by buildSlimefunItem<FastSmeltery> {
        material = MaterialCompat.safe(XMaterial.BLAST_FURNACE).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +" F "
            +"BDB"
            +"Ofo"
            'F' means XMaterial.NETHER_BRICK_FENCE
            'B' means XMaterial.NETHER_BRICKS
            'D' means XMaterial.DISPENSER
            'f' means ETERNAL_FIRE
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_PRESSURE_CHAMBER by buildSlimefunItem<FastPressureChamber> {
        material = MaterialCompat.safe(XMaterial.SMOKER).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"ODo"
            +"PGP"
            +"PCP"
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means XMaterial.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
            'P' means XMaterial.PISTON
            'G' means XMaterial.GLASS
            'C' means XMaterial.CAULDRON
        }
    }

    val FAST_MAGIC_WORKBENCH by buildSlimefunItem<FastMagicWorkbench> {
        material = MaterialCompat.safe(XMaterial.BOOKSHELF).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.MAGIC_WORKBENCH
        recipe = buildRecipe {
            +"   "
            +"O o"
            +"BCD"
            'B' means XMaterial.BOOKSHELF
            'C' means XMaterial.CRAFTING_TABLE
            'D' means XMaterial.DISPENSER
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_ORE_WASHER by buildSlimefunItem<FastOreWasher> {
        material = MaterialCompat.safe(XMaterial.CAULDRON).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +" G "
            +" F "
            +"ODo"
            'G' means XMaterial.GLASS
            'F' means XMaterial.OAK_FENCE
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means XMaterial.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_TABLE_SAW by buildSlimefunItem<FastTableSaw> {
        material = MaterialCompat.safe(XMaterial.STONECUTTER).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"SCS"
            +"OIo"
            'S' means XMaterial.SMOOTH_STONE_SLAB
            'C' means XMaterial.STONECUTTER
            'I' means XMaterial.IRON_BLOCK
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_COMPOSTER by buildSlimefunItem<FastComposter> {
        material = MaterialCompat.safe(XMaterial.COMPOSTER).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"SOS"
            +"SoS"
            +"SCS"
            'S' means XMaterial.OAK_SLAB
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
            'C' means XMaterial.CAULDRON
        }
    }

    val FAST_PANNING_MACHINE by buildSlimefunItem<FastPanningMachine> {
        material = MaterialCompat.safe(XMaterial.HOPPER).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +" T "
            +"OCo"
            'T' means XMaterial.OAK_TRAPDOOR
            'C' means XMaterial.CAULDRON
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_JUICER by buildSlimefunItem<FastJuicer> {
        material = MaterialCompat.safe(XMaterial.BREWING_STAND).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +" G "
            +" F "
            +"ODo"
            'G' means XMaterial.GLASS
            'F' means XMaterial.NETHER_BRICK_FENCE
            'D' means XMaterial.DISPENSER
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_ANCIENT_ALTAR by buildSlimefunItem<FastAncientAltar> {
        material = MaterialCompat.safe(XMaterial.ENCHANTING_TABLE).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.MAGIC_WORKBENCH
        recipe = buildRecipe {
            +"   "
            +" o "
            +"PAP"
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
            'P' means STACKED_ANCIENT_PEDESTAL
            'A' means SlimefunItems.ANCIENT_ALTAR
        }
    }
    //</editor-fold>

    //<editor-fold desc="Machines - InfinityExpansion" collapsed="true">
    val FAST_INFINITY_WORKBENCH by buildSlimefunItem<FastInfinityWorkbench> {
        material = MaterialCompat.safe(XMaterial.RESPAWN_ANCHOR).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"   "
            +"Omo"
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
            'm' means "INFINITY_FORGE".getSfItem()?.item
        }
    }

    val FAST_MOB_DATA_INFUSER by buildSlimefunItem<FastMobDataInfuser> {
        material = MaterialCompat.safe(XMaterial.LODESTONE).asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"   "
            +"Omo"
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
            'm' means "DATA_INFUSER".getSfItem()?.item
        }
    }
    //</editor-fold>
}
