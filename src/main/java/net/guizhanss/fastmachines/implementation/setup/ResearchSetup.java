package net.guizhanss.fastmachines.implementation.setup;

import io.github.thebusybiscuit.slimefun5.api.researches.Research;

import net.guizhanss.fastmachines.implementation.items.FMItems;
import net.guizhanss.fastmachines.utils.constants.Keys;

public final class ResearchSetup {

    public static Research materials;
    public static Research machines;

    private ResearchSetup() {
    }

    public static void setup() {
        materials = new Research(Keys.MATERIALS, 1145141, "Fast Machine Materials", 4);
        materials.addItems(
            FMItems.ETERNAL_FIRE.item(),
            FMItems.FAST_CORE.item(),
            FMItems.STACKED_ANCIENT_PEDESTAL.item()
        );
        materials.register();

        machines = new Research(Keys.MACHINES, 1145142, "Fast Machines", 40);
        machines.addItems(
            // vanilla
            FMItems.FAST_CRAFTING_TABLE.item(),
            FMItems.FAST_FURNACE.item(),
            // slimefun
            FMItems.FAST_ENHANCED_CRAFTING_TABLE.item(),
            FMItems.FAST_GRIND_STONE.item(),
            FMItems.FAST_ARMOR_FORGE.item(),
            FMItems.FAST_ORE_CRUSHER.item(),
            FMItems.FAST_COMPRESSOR.item(),
            FMItems.FAST_SMELTERY.item(),
            FMItems.FAST_PRESSURE_CHAMBER.item(),
            FMItems.FAST_MAGIC_WORKBENCH.item(),
            FMItems.FAST_ORE_WASHER.item(),
            FMItems.FAST_TABLE_SAW.item(),
            FMItems.FAST_COMPOSTER.item(),
            FMItems.FAST_PANNING_MACHINE.item(),
            FMItems.FAST_JUICER.item(),
            FMItems.FAST_ANCIENT_ALTAR.item(),
            // infinity expansion
            FMItems.FAST_INFINITY_WORKBENCH.item(),
            FMItems.FAST_MOB_DATA_INFUSER.item()
            // NOTE: SlimeFrame + InfinityExpansion2 machines dropped (soft-deps not yet ported) - see FMItems.
        );
        machines.register();
    }
}
