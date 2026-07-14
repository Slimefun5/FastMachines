package net.guizhanss.fastmachines.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.Inventory;

import io.github.thebusybiscuit.slimefun5.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;

/**
 * Runtime version guards + reflective fallbacks so post-1.8 Bukkit API is never touched on servers
 * that lack it. Callers gate every reference to a post-1.8 class/method behind one of these so the
 * JVM never resolves the missing type/member on a legacy runtime.
 */
public final class CompatUtils {

    private CompatUtils() {
    }

    /** The vanilla {@code RecipeChoice}/{@code CookingRecipe} API + {@code ShapedRecipe.getChoiceMap()} are all 1.13+. */
    public static boolean recipeChoiceApiSupported() {
        return Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_13);
    }

    /**
     * Advanced item-meta comparison (PDC, custom model data, and the per-type meta refinements such as
     * {@code CompassMeta}/{@code SuspiciousStewMeta}/{@code Damageable}) touches types/methods that only
     * exist on 1.14+ (some, like {@code CompassMeta}, only 1.16+). Gated at 1.16 to cover the newest
     * referenced type; on older servers the base comparison is used instead.
     */
    public static boolean advancedItemMetaSupported() {
        return Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16);
    }

    /**
     * {@code Inventory#getLocation()} does not exist on 1.8.8; invoke it reflectively and return
     * {@code null} when absent so hopper-protection logic simply no-ops on legacy versions instead of
     * crashing.
     */
    public static Location inventoryLocation(Inventory inventory) {
        try {
            Object result = inventory.getClass().getMethod("getLocation").invoke(inventory);
            return result instanceof Location ? (Location) result : null;
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * {@code BlockPhysicsEvent#getSourceBlock()} was only added in 1.9; invoke it reflectively and fall
     * back to {@code getBlock()} on 1.8.8, where a direct call throws {@code NoSuchMethodError}.
     */
    public static Block physicsSourceBlock(BlockPhysicsEvent e) {
        try {
            Object result = BlockPhysicsEvent.class.getMethod("getSourceBlock").invoke(e);
            return result instanceof Block ? (Block) result : e.getBlock();
        } catch (Throwable t) {
            return e.getBlock();
        }
    }
}
