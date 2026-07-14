package net.guizhanss.fastmachines.utils.items

import io.github.thebusybiscuit.slimefun5.implementation.Slimefun
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.utils.CompatUtils
import net.guizhanss.fastmachines.utils.MetaCompat
import net.guizhanss.fastmachines.utils.isAirMaterial
import org.bukkit.inventory.ItemStack

/**
 * Strictly compares a [ItemWrapper] to an [ItemStack].
 *
 * Modified from [Networks by Sefiraat](https://github.com/Sefiraat/Networks/blob/master/src/main/java/io/github/sefiraat/networks/utils/StackUtils.java).
 *
 * Every post-1.8 meta comparison (PDC, custom model data, and the per-type `CompassMeta`/`Damageable`/
 * ... refinements) is delegated to the guarded [MetaCompat] helper and only invoked when
 * [CompatUtils.advancedItemMetaSupported] is true, so those post-1.8 types/methods are never resolved
 * on legacy servers. On 1.8-1.15 the base comparison below (type, display name, lore, enchants, item
 * flags, Slimefun id) is used.
 */
fun ItemWrapper?.isSimilarTo(other: ItemStack?, checkLore: Boolean = false): Boolean {
    // null check
    if (this == null || other == null) return false

    // bukkit item comparison
    if (FastMachines.configService.fmUseBukkitItemComparison.value) {
        return baseItem.isSimilar(other)
    }

    // type check
    if (baseItem.type != other.type) return false
    if (isAirMaterial(baseItem.type) || isAirMaterial(other.type)) return false

    // has meta check
    if (baseItemMeta == null || !other.hasItemMeta()) {
        return (baseItemMeta != null) == other.hasItemMeta()
    }

    val thisMeta = baseItemMeta
    val otherMeta = other.itemMeta!!

    if (thisMeta.javaClass != otherMeta.javaClass) return false

    // version-sensitive meta comparison (PDC / custom model data / per-type refinements) - guarded
    if (CompatUtils.advancedItemMetaSupported() && MetaCompat.metaDiffers(thisMeta, otherMeta)) {
        return false
    }

    // has display name check
    if (thisMeta.hasDisplayName() != otherMeta.hasDisplayName()) return false

    // enchantments check
    if (thisMeta.enchants != otherMeta.enchants) return false

    // item flags check
    if (thisMeta.itemFlags != otherMeta.itemFlags) return false

    // sf id check (distinction is covered with pdc and lore)
    if (Slimefun.instance() != null) {
        val sfIdThis = Slimefun.getItemDataService().getItemData(thisMeta)
        val sfIdOther = Slimefun.getItemDataService().getItemData(otherMeta)
        if (sfIdThis.isPresent && sfIdOther.isPresent) {
            return sfIdThis.get() == sfIdOther.get()
        }
    }

    // display name check
    if (thisMeta.hasDisplayName() && (thisMeta.displayName != otherMeta.displayName)) {
        return false
    }

    // lore check
    if (checkLore && thisMeta.lore != otherMeta.lore) return false

    return true
}

/**
 * Returns a list of [ItemWrapper]s that all similar items are merged into one wrapper.
 */
fun Collection<ItemStack?>.countItems(): Map<ItemWrapper, Int> {
    val result = mutableMapOf<ItemWrapper, Int>()

    for (item in this) {
        if (item == null || isAirMaterial(item.type)) continue

        val wrapper = ItemWrapper.of(item)
        result[wrapper] = (result[wrapper] ?: 0) + item.amount
    }

    return result.toSortedMap()
}
