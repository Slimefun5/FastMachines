package net.guizhanss.fastmachines.utils;

import java.util.Objects;

import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

/**
 * Isolates every item-meta comparison that references a post-1.8 Bukkit CLASS or METHOD:
 * {@code PersistentDataContainer} (1.14), {@code ItemMeta.hasCustomModelData()} (1.14),
 * {@code Damageable}/{@code TropicalFishBucketMeta} (1.13), {@code SuspiciousStewMeta} (1.14),
 * {@code CompassMeta} (1.16), etc.
 *
 * This is a SEPARATE class deliberately: on legacy servers it is never loaded (callers gate every
 * invocation behind {@link CompatUtils#advancedItemMetaSupported()}), so the JVM never has to resolve
 * any of these missing types during verification/execution on 1.8-1.15.
 */
@SuppressWarnings("deprecation")
public final class MetaCompat {

    private MetaCompat() {
    }

    /** Returns true if the two metas differ in any of the version-sensitive fields. */
    public static boolean metaDiffers(ItemMeta a, ItemMeta b) {
        // custom model data (1.14)
        if (!a.hasCustomModelData() || !b.hasCustomModelData()) {
            if (a.hasCustomModelData() != b.hasCustomModelData()) {
                return true;
            }
        } else if (a.getCustomModelData() != b.getCustomModelData()) {
            return true;
        }

        // persistent data container (1.14)
        if (!a.getPersistentDataContainer().equals(b.getPersistentDataContainer())) {
            return true;
        }

        // per-type meta refinements
        return deepMetaDiffers(a, b);
    }

    private static boolean deepMetaDiffers(ItemMeta a, ItemMeta b) {
        if (a instanceof Damageable && b instanceof Damageable) {
            if (((Damageable) a).getDamage() != ((Damageable) b).getDamage()) {
                return true;
            }
        }
        if (a instanceof BannerMeta && b instanceof BannerMeta) {
            if (!((BannerMeta) a).getPatterns().equals(((BannerMeta) b).getPatterns())) {
                return true;
            }
        }
        if (a instanceof BookMeta && b instanceof BookMeta) {
            BookMeta ba = (BookMeta) a;
            BookMeta bb = (BookMeta) b;
            if (ba.getPageCount() != bb.getPageCount()) {
                return true;
            }
            if (!Objects.equals(ba.getAuthor(), bb.getAuthor())) {
                return true;
            }
            if (!Objects.equals(ba.getTitle(), bb.getTitle())) {
                return true;
            }
            if (ba.getGeneration() != bb.getGeneration()) {
                return true;
            }
        }
        if (a instanceof CompassMeta && b instanceof CompassMeta) {
            CompassMeta ca = (CompassMeta) a;
            CompassMeta cb = (CompassMeta) b;
            if (ca.isLodestoneTracked() != cb.isLodestoneTracked()) {
                return true;
            }
            if (!Objects.equals(ca.getLodestone(), cb.getLodestone())) {
                return true;
            }
        }
        if (a instanceof EnchantmentStorageMeta && b instanceof EnchantmentStorageMeta) {
            EnchantmentStorageMeta ea = (EnchantmentStorageMeta) a;
            EnchantmentStorageMeta eb = (EnchantmentStorageMeta) b;
            if (ea.hasStoredEnchants() != eb.hasStoredEnchants()) {
                return true;
            }
            if (!ea.getStoredEnchants().equals(eb.getStoredEnchants())) {
                return true;
            }
        }
        if (a instanceof FireworkEffectMeta && b instanceof FireworkEffectMeta) {
            if (!Objects.equals(((FireworkEffectMeta) a).getEffect(), ((FireworkEffectMeta) b).getEffect())) {
                return true;
            }
        }
        if (a instanceof FireworkMeta && b instanceof FireworkMeta) {
            FireworkMeta fa = (FireworkMeta) a;
            FireworkMeta fb = (FireworkMeta) b;
            if (fa.getPower() != fb.getPower()) {
                return true;
            }
            if (!fa.getEffects().equals(fb.getEffects())) {
                return true;
            }
        }
        if (a instanceof LeatherArmorMeta && b instanceof LeatherArmorMeta) {
            if (!Objects.equals(((LeatherArmorMeta) a).getColor(), ((LeatherArmorMeta) b).getColor())) {
                return true;
            }
        }
        if (a instanceof MapMeta && b instanceof MapMeta) {
            MapMeta ma = (MapMeta) a;
            MapMeta mb = (MapMeta) b;
            if (ma.hasMapView() != mb.hasMapView()) {
                return true;
            }
            if (ma.hasLocationName() != mb.hasLocationName()) {
                return true;
            }
            if (ma.hasColor() != mb.hasColor()) {
                return true;
            }
            if (!Objects.equals(ma.getMapView(), mb.getMapView())) {
                return true;
            }
            if (!Objects.equals(ma.getLocationName(), mb.getLocationName())) {
                return true;
            }
            if (!Objects.equals(ma.getColor(), mb.getColor())) {
                return true;
            }
        }
        if (a instanceof PotionMeta && b instanceof PotionMeta) {
            PotionMeta pa = (PotionMeta) a;
            PotionMeta pb = (PotionMeta) b;
            if (!Objects.equals(pa.getBasePotionData(), pb.getBasePotionData())) {
                return true;
            }
            if (pa.hasCustomEffects() != pb.hasCustomEffects()) {
                return true;
            }
            if (pa.hasColor() != pb.hasColor()) {
                return true;
            }
            if (!Objects.equals(pa.getColor(), pb.getColor())) {
                return true;
            }
            if (!pa.getCustomEffects().equals(pb.getCustomEffects())) {
                return true;
            }
        }
        if (a instanceof SkullMeta && b instanceof SkullMeta) {
            SkullMeta sa = (SkullMeta) a;
            SkullMeta sb = (SkullMeta) b;
            if (sa.hasOwner() != sb.hasOwner()) {
                return true;
            }
            if (!Objects.equals(sa.getOwningPlayer(), sb.getOwningPlayer())) {
                return true;
            }
        }
        if (a instanceof SuspiciousStewMeta && b instanceof SuspiciousStewMeta) {
            if (!((SuspiciousStewMeta) a).getCustomEffects().equals(((SuspiciousStewMeta) b).getCustomEffects())) {
                return true;
            }
        }
        if (a instanceof TropicalFishBucketMeta && b instanceof TropicalFishBucketMeta) {
            TropicalFishBucketMeta ta = (TropicalFishBucketMeta) a;
            TropicalFishBucketMeta tb = (TropicalFishBucketMeta) b;
            if (ta.hasVariant() != tb.hasVariant()) {
                return true;
            }
            if (ta.getPattern() != tb.getPattern()) {
                return true;
            }
            if (ta.getBodyColor() != tb.getBodyColor()) {
                return true;
            }
            if (ta.getPatternColor() != tb.getPatternColor()) {
                return true;
            }
        }
        return false;
    }
}
