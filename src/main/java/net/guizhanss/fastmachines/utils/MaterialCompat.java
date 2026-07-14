package net.guizhanss.fastmachines.utils;

import org.bukkit.Material;

import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

/**
 * Version-safety helpers for {@link Material}.
 *
 * {@code Material.CONDUIT}, {@code RESPAWN_ANCHOR}, {@code GRINDSTONE}, {@code BLAST_FURNACE}, ... are
 * enum constants that simply do not exist on legacy servers (1.8-1.12); referencing them directly
 * throws {@code NoSuchFieldError} at class-init time. Every such reference is routed through
 * {@link #safe}, which resolves the {@link XMaterial} to whatever {@link Material} the running server
 * actually has (via XSeries' legacy mapping), falling back to {@link Material#STONE} for blocks that
 * have no legacy equivalent at all.
 */
public final class MaterialCompat {

    private MaterialCompat() {
    }

    public static Material safe(XMaterial material) {
        Material parsed = material.parseMaterial();
        return parsed != null ? parsed : Material.STONE;
    }

    /**
     * {@code Material.isAir()} is a 1.13+ method (absent on 1.8-1.12, where there's also only a single
     * {@code AIR} material instead of {@code AIR}/{@code CAVE_AIR}/{@code VOID_AIR}); a name-based check
     * works unchanged on every version without needing reflection.
     */
    public static boolean isAirMaterial(Material material) {
        if (material == null) {
            return true;
        }
        String name = material.name();
        return name.equals("AIR") || name.endsWith("_AIR");
    }
}
