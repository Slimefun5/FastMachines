package net.guizhanss.fastmachines.utils.items;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.items.ItemWrapper;
import net.guizhanss.fastmachines.utils.CompatUtils;
import net.guizhanss.fastmachines.utils.MaterialCompat;
import net.guizhanss.fastmachines.utils.MetaCompat;

/**
 * Item comparison / counting helpers.
 *
 * Modified from <a href="https://github.com/Sefiraat/Networks">Networks by Sefiraat</a>.
 *
 * Every post-1.8 meta comparison (PDC, custom model data, and the per-type refinements) is delegated
 * to the guarded {@code MetaCompat} helper and only invoked when
 * {@link CompatUtils#advancedItemMetaSupported()} is true, so those post-1.8 types/methods are never
 * resolved on legacy servers.
 */
public final class ItemExt {

    private ItemExt() {
    }

    public static boolean isSimilarTo(ItemWrapper self, ItemStack other) {
        return isSimilarTo(self, other, false);
    }

    public static boolean isSimilarTo(ItemWrapper self, ItemStack other, boolean checkLore) {
        // null check
        if (self == null || other == null) {
            return false;
        }

        ItemStack baseItem = self.getBaseItem();
        ItemMeta baseItemMeta = self.getBaseItemMeta();

        // bukkit item comparison
        if (FastMachines.getConfigService().getFmUseBukkitItemComparison().getValue()) {
            return baseItem.isSimilar(other);
        }

        // type check
        if (baseItem.getType() != other.getType()) {
            return false;
        }
        if (MaterialCompat.isAirMaterial(baseItem.getType()) || MaterialCompat.isAirMaterial(other.getType())) {
            return false;
        }

        // has meta check
        if (baseItemMeta == null || !other.hasItemMeta()) {
            return (baseItemMeta != null) == other.hasItemMeta();
        }

        ItemMeta thisMeta = baseItemMeta;
        ItemMeta otherMeta = other.getItemMeta();

        if (thisMeta.getClass() != otherMeta.getClass()) {
            return false;
        }

        // version-sensitive meta comparison (PDC / custom model data / per-type refinements) - guarded
        if (CompatUtils.advancedItemMetaSupported() && MetaCompat.metaDiffers(thisMeta, otherMeta)) {
            return false;
        }

        // has display name check
        if (thisMeta.hasDisplayName() != otherMeta.hasDisplayName()) {
            return false;
        }

        // enchantments check
        if (!thisMeta.getEnchants().equals(otherMeta.getEnchants())) {
            return false;
        }

        // item flags check
        if (!thisMeta.getItemFlags().equals(otherMeta.getItemFlags())) {
            return false;
        }

        // sf id check (distinction is covered with pdc and lore)
        if (Slimefun.instance() != null) {
            Optional<String> sfIdThis = Slimefun.getItemDataService().getItemData(thisMeta);
            Optional<String> sfIdOther = Slimefun.getItemDataService().getItemData(otherMeta);
            if (sfIdThis.isPresent() && sfIdOther.isPresent()) {
                return sfIdThis.get().equals(sfIdOther.get());
            }
        }

        // display name check
        if (thisMeta.hasDisplayName() && !thisMeta.getDisplayName().equals(otherMeta.getDisplayName())) {
            return false;
        }

        // lore check
        if (checkLore && !java.util.Objects.equals(thisMeta.getLore(), otherMeta.getLore())) {
            return false;
        }

        return true;
    }

    /**
     * Returns a sorted map where all similar items are merged into one wrapper with their total amount.
     */
    public static Map<ItemWrapper, Integer> countItems(Collection<ItemStack> items) {
        Map<ItemWrapper, Integer> result = new TreeMap<>();
        for (ItemStack item : items) {
            if (item == null || MaterialCompat.isAirMaterial(item.getType())) {
                continue;
            }
            ItemWrapper wrapper = ItemWrapper.of(item);
            result.merge(wrapper, item.getAmount(), Integer::sum);
        }
        return result;
    }
}
