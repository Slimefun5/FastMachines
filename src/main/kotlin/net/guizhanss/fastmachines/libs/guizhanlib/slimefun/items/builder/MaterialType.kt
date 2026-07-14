package net.guizhanss.fastmachines.libs.guizhanlib.slimefun.items.builder

import org.bukkit.Material

/**
 * A thin wrapper so [net.guizhanss.fastmachines.utils.items.builder.SlimefunItemBuilder] can accept a
 * `Material` without committing the DSL's `material` property to that exact type.
 *
 * Java-8-safe, Kotlin port of GuizhanLib-kt's item builder DSL - see the package-level note in
 * [net.guizhanss.fastmachines.libs.guizhanlib].
 */
class MaterialType internal constructor(private val material: Material) {
    fun convert(): Material = material
}

fun Material.asMaterialType(): MaterialType = MaterialType(this)
