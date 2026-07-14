package net.guizhanss.fastmachines.libs.guizhanlib.slimefun.items.builder

import org.bukkit.plugin.java.JavaPlugin

/**
 * Base class for an addon's item registry object: holds the owning [plugin] and the [prefix]
 * prepended to every Slimefun item id built through [buildSlimefunItem].
 *
 * Java-8-safe, Kotlin port of GuizhanLib-kt's item builder DSL - see the package-level note in
 * [net.guizhanss.fastmachines.libs.guizhanlib].
 */
abstract class ItemRegistry(val plugin: JavaPlugin, val prefix: String)
