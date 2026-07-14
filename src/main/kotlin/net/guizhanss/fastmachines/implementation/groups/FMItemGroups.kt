package net.guizhanss.fastmachines.implementation.groups

import io.github.thebusybiscuit.slimefun5.api.items.groups.NestedItemGroup
import io.github.thebusybiscuit.slimefun5.api.items.groups.SubItemGroup
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.items.groups.HiddenItemGroup
import net.guizhanss.fastmachines.libs.guizhanlib.items.edit
import net.guizhanss.fastmachines.libs.guizhanlib.items.toItem
import net.guizhanss.fastmachines.utils.constants.HeadTexture
import net.guizhanss.fastmachines.utils.constants.Keys
import org.bukkit.Material

object FMItemGroups {

    val MAIN = NestedItemGroup(
        Keys.MAIN_GROUP,
        FastMachines.localization.getItem(
            "FAST_MACHINES",
            HeadTexture.MAIN.texture
        ).item()
    )

    val MATERIALS = SubItemGroup(
        Keys.MATERIALS,
        MAIN,
        FastMachines.localization.getItem(
            "MATERIALS",
            Material.DIAMOND
        ).item()
    )

    val MACHINES = SubItemGroup(
        Keys.MACHINES,
        MAIN,
        FastMachines.localization.getItem(
            "MACHINES",
            HeadTexture.MAIN.texture
        ).item()
    )

    val HIDDEN = HiddenItemGroup(
        Keys.HIDDEN,
        Material.BARRIER.toItem().edit { name("FM Invalid items") }
    )
}
