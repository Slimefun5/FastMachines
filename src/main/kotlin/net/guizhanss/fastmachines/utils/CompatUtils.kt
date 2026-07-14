package net.guizhanss.fastmachines.utils

import io.github.thebusybiscuit.slimefun5.api.MinecraftVersion
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun
import org.bukkit.Location
import org.bukkit.inventory.Inventory

/**
 * Runtime version guards + reflective fallbacks so post-1.8 Bukkit API is never touched on servers
 * that lack it. Callers gate every reference to a post-1.8 class/method behind one of these so the
 * JVM never resolves the missing type/member on a legacy runtime.
 */
object CompatUtils {

    /** The vanilla `RecipeChoice`/`CookingRecipe` API + `ShapedRecipe.getChoiceMap()` are all 1.13+. */
    fun recipeChoiceApiSupported(): Boolean =
        Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_13)

    /**
     * Advanced item-meta comparison (PDC, custom model data, and the per-type meta refinements such as
     * `CompassMeta`/`SuspiciousStewMeta`/`Damageable`) touches types/methods that only exist on 1.14+
     * (some, like `CompassMeta`, only 1.16+). Gated at 1.16 to cover the newest referenced type; on
     * older servers the base comparison (type/name/lore/enchants/Slimefun-id) is used instead.
     */
    fun advancedItemMetaSupported(): Boolean =
        Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)

    /**
     * `Inventory#getLocation()` does not exist on 1.8.8; invoke it reflectively and return `null` when
     * absent so hopper-protection logic simply no-ops on legacy versions instead of crashing.
     */
    fun inventoryLocation(inventory: Inventory): Location? {
        return try {
            inventory.javaClass.getMethod("getLocation").invoke(inventory) as? Location
        } catch (e: Throwable) {
            null
        }
    }
}
