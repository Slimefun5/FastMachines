package net.guizhanss.fastmachines.utils.constants

import io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey
import net.guizhanss.fastmachines.FastMachines

object Keys {

    val MAIN_GROUP = fmKey("fast_machines")
    val MATERIALS = fmKey("materials")
    val MACHINES = fmKey("machines")
    val HIDDEN = fmKey("hidden")
    val DISPLAY_ITEM = fmKey("display_item")
}

internal fun fmKey(key: String) = NamespacedKey(FastMachines.instance, key)
