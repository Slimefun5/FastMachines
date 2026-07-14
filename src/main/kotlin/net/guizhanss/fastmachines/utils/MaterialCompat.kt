package net.guizhanss.fastmachines.utils

import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial
import org.bukkit.Material

/**
 * Version-safety helpers for [Material].
 *
 * `Material.CONDUIT`, `RESPAWN_ANCHOR`, `GRINDSTONE`, `BLAST_FURNACE`, ... are enum constants that
 * simply do not exist on legacy servers (1.8-1.12); referencing them directly throws
 * `NoSuchFieldError` at class-init time. Every such reference is routed through [safe], which resolves
 * the [XMaterial] to whatever [Material] the running server actually has (via XSeries' legacy mapping),
 * falling back to [Material.STONE] for blocks that have no legacy equivalent at all. This keeps
 * FastMachines loadable + enableable on 1.8 (items/recipes just use substitute materials there).
 */
object MaterialCompat {

    @JvmStatic
    fun safe(material: XMaterial): Material = material.parseMaterial() ?: Material.STONE
}

/**
 * `Material.isAir()` is a 1.13+ method (absent on 1.8-1.12, where there's also only a single `AIR`
 * material instead of `AIR`/`CAVE_AIR`/`VOID_AIR`); a name-based check works unchanged on every
 * version without needing reflection. Matches the pattern used by the other ported addons in this
 * fork (e.g. Supreme's/FluffyMachines's `CompatUtils.isAir`).
 */
fun isAirMaterial(material: Material?): Boolean {
    if (material == null) return true
    val name = material.name
    return name == "AIR" || name.endsWith("_AIR")
}
