package net.guizhanss.fastmachines.libs.guizhanlib.items

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem
import org.bukkit.inventory.ItemStack

/**
 * Java-8-safe, Kotlin port of GuizhanLib-kt's `isSlimefunItem`/`getSlimefunItem` extensions - see the
 * package-level note in [net.guizhanss.fastmachines.libs.guizhanlib].
 */
fun ItemStack.isSlimefunItem(): Boolean = SlimefunItem.getByItem(this) != null

fun ItemStack.getSlimefunItem(): SlimefunItem =
    SlimefunItem.getByItem(this) ?: error("This ItemStack is not a registered Slimefun item")
