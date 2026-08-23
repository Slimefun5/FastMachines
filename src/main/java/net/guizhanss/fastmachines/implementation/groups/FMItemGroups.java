package net.guizhanss.fastmachines.implementation.groups;

import org.bukkit.Material;

import io.github.thebusybiscuit.slimefun5.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.groups.SubItemGroup;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.items.groups.HiddenItemGroup;
import net.guizhanss.fastmachines.libs.guizhanlib.items.Items;
import net.guizhanss.fastmachines.utils.constants.HeadTexture;
import net.guizhanss.fastmachines.utils.constants.Keys;

public final class FMItemGroups {

    public static NestedItemGroup MAIN;
    public static SubItemGroup MATERIALS;
    public static SubItemGroup MACHINES;
    public static HiddenItemGroup HIDDEN;

    private FMItemGroups() {
    }

    public static void setup() {
        MAIN = new NestedItemGroup(
            Keys.MAIN_GROUP,
            FastMachines.getLocalization().getItemGroupItem("FAST_MACHINES", HeadTexture.MAIN.getTexture())
        );

        MATERIALS = new SubItemGroup(
            Keys.MATERIALS,
            MAIN,
            FastMachines.getLocalization().getItemGroupItem("MATERIALS", Material.DIAMOND)
        );

        MACHINES = new SubItemGroup(
            Keys.MACHINES,
            MAIN,
            FastMachines.getLocalization().getItemGroupItem("MACHINES", HeadTexture.MAIN.getTexture())
        );

        HIDDEN = new HiddenItemGroup(
            Keys.HIDDEN,
            Items.edit(Items.toItem(Material.BARRIER), e -> e.name("FM Invalid items"))
        );
    }
}
