package net.guizhanss.fastmachines.libs.guizhanlib.utils

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Java-8-safe, Kotlin port of GuizhanLib's `InventoryUtil` - see the package-level note in
 * [net.guizhanss.fastmachines.libs.guizhanlib].
 */
object InventoryUtil {

    fun push(p: Player, vararg itemStacks: ItemStack) {
        push(p, p.location, *itemStacks)
    }

    fun push(p: Player, loc: Location, vararg itemStacks: ItemStack) {
        val remaining = p.inventory.addItem(*itemStacks)
        for (item in remaining.values) {
            p.world.dropItem(loc, item.clone())
        }
    }
}
