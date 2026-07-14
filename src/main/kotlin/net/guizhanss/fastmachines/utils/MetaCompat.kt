@file:Suppress("deprecation")

package net.guizhanss.fastmachines.utils

import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.inventory.meta.BookMeta
import org.bukkit.inventory.meta.CompassMeta
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.inventory.meta.FireworkEffectMeta
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.inventory.meta.SuspiciousStewMeta
import org.bukkit.inventory.meta.TropicalFishBucketMeta

/**
 * Isolates every item-meta comparison that references a post-1.8 Bukkit CLASS or METHOD:
 * `PersistentDataContainer` (1.14), `ItemMeta.hasCustomModelData()/getCustomModelData()` (1.14),
 * `Damageable`/`TropicalFishBucketMeta` (1.13), `SuspiciousStewMeta` (1.14), `CompassMeta` (1.16),
 * `BookMeta.Generation` (1.10), etc.
 *
 * This is a SEPARATE class deliberately: on legacy servers it is never loaded (callers gate every
 * invocation behind [CompatUtils.advancedItemMetaSupported]), so the JVM never has to resolve any of
 * these missing types during verification/execution on 1.8-1.15. On those versions the coarser
 * base comparison in [net.guizhanss.fastmachines.utils.items.isSimilarTo] (type, display name, lore,
 * enchants, item flags, Slimefun id) is used instead.
 */
internal object MetaCompat {

    /** Returns true if the two metas differ in any of the version-sensitive fields. */
    fun metaDiffers(a: ItemMeta, b: ItemMeta): Boolean {
        // custom model data (1.14)
        if (!a.hasCustomModelData() || !b.hasCustomModelData()) {
            if (a.hasCustomModelData() != b.hasCustomModelData()) return true
        } else if (a.customModelData != b.customModelData) {
            return true
        }

        // persistent data container (1.14)
        if (a.persistentDataContainer != b.persistentDataContainer) return true

        // per-type meta refinements
        return deepMetaDiffers(a, b)
    }

    private fun deepMetaDiffers(a: ItemMeta, b: ItemMeta): Boolean {
        if (a is Damageable && b is Damageable) {
            if (a.damage != b.damage) return true
        }
        if (a is BannerMeta && b is BannerMeta) {
            if (a.patterns != b.patterns) return true
        }
        if (a is BookMeta && b is BookMeta) {
            if (a.pageCount != b.pageCount) return true
            if (a.author != b.author) return true
            if (a.title != b.title) return true
            if (a.generation != b.generation) return true
        }
        if (a is CompassMeta && b is CompassMeta) {
            if (a.isLodestoneTracked != b.isLodestoneTracked) return true
            if (a.lodestone != b.lodestone) return true
        }
        if (a is EnchantmentStorageMeta && b is EnchantmentStorageMeta) {
            if (a.hasStoredEnchants() != b.hasStoredEnchants()) return true
            if (a.storedEnchants != b.storedEnchants) return true
        }
        if (a is FireworkEffectMeta && b is FireworkEffectMeta) {
            if (a.effect != b.effect) return true
        }
        if (a is FireworkMeta && b is FireworkMeta) {
            if (a.power != b.power) return true
            if (a.effects != b.effects) return true
        }
        if (a is LeatherArmorMeta && b is LeatherArmorMeta) {
            if (a.color != b.color) return true
        }
        if (a is MapMeta && b is MapMeta) {
            if (a.hasMapView() != b.hasMapView()) return true
            if (a.hasLocationName() != b.hasLocationName()) return true
            if (a.hasColor() != b.hasColor()) return true
            if (a.mapView != b.mapView) return true
            if (a.locationName != b.locationName) return true
            if (a.color != b.color) return true
        }
        if (a is PotionMeta && b is PotionMeta) {
            if (a.basePotionData != b.basePotionData) return true
            if (a.hasCustomEffects() != b.hasCustomEffects()) return true
            if (a.hasColor() != b.hasColor()) return true
            if (a.color != b.color) return true
            if (a.customEffects != b.customEffects) return true
        }
        if (a is SkullMeta && b is SkullMeta) {
            if (a.hasOwner() != b.hasOwner()) return true
            if (a.owningPlayer != b.owningPlayer) return true
        }
        if (a is SuspiciousStewMeta && b is SuspiciousStewMeta) {
            if (a.customEffects != b.customEffects) return true
        }
        if (a is TropicalFishBucketMeta && b is TropicalFishBucketMeta) {
            if (a.hasVariant() != b.hasVariant()) return true
            if (a.pattern != b.pattern) return true
            if (a.bodyColor != b.bodyColor) return true
            if (a.patternColor != b.patternColor) return true
        }
        return false
    }
}
