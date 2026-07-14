package net.guizhanss.fastmachines.core.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun5.utils.itemstack.ItemStackWrapper;

import net.guizhanss.fastmachines.libs.guizhanlib.items.Items;
import net.guizhanss.fastmachines.utils.items.ItemExt;

/**
 * A wrapper for {@link ItemStack} that only focuses on the item type and meta.
 */
public final class ItemWrapper implements Comparable<ItemWrapper> {

    private final ItemStack baseItem;
    private final ItemMeta baseItemMeta;
    private final int itemHash;

    private ItemWrapper(ItemStack baseItem) {
        this.baseItem = baseItem;
        this.baseItemMeta = baseItem.hasItemMeta() ? baseItem.getItemMeta() : null;
        this.itemHash = calcHash(baseItem);
    }

    /**
     * Creates a new {@link ItemWrapper} with the given {@link ItemStack}.
     */
    public static ItemWrapper of(ItemStack item) {
        ItemStack baseItem;
        if (item instanceof ItemStackWrapper) {
            // Slimefun's ItemStackWrapper, construct a new ItemStack
            baseItem = new ItemStack(item.getType());
            if (item.hasItemMeta()) {
                baseItem.setItemMeta(item.getItemMeta());
            }
        } else {
            baseItem = item.clone();
            baseItem.setAmount(1);
        }
        return new ItemWrapper(baseItem);
    }

    /**
     * Creates a new {@link ItemWrapper} with the given {@link Material}.
     */
    public static ItemWrapper of(Material material) {
        return new ItemWrapper(new ItemStack(material));
    }

    private static int calcHash(ItemStack item) {
        int result = item.getType().hashCode();
        result = 31 * result + (item.hasItemMeta() ? item.getItemMeta().hashCode() : 0);
        return result;
    }

    public ItemStack getBaseItem() {
        return baseItem;
    }

    public ItemMeta getBaseItemMeta() {
        return baseItemMeta;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ItemWrapper && ItemExt.isSimilarTo(this, ((ItemWrapper) other).baseItem);
    }

    @Override
    public int hashCode() {
        return itemHash;
    }

    @Override
    public int compareTo(ItemWrapper other) {
        // quick compare hash code, items with different hash code are likely different
        if (itemHash != other.itemHash) {
            return itemHash - other.itemHash;
        }

        // same hash, compare item
        if (!ItemExt.isSimilarTo(this, other.baseItem)) {
            return baseItem.hashCode() - other.baseItem.hashCode();
        }

        // should be same
        return 0;
    }

    @Override
    public String toString() {
        if (Items.isSlimefunItem(baseItem)) {
            return "ItemWrapper(slimefunId=" + Items.getSlimefunItem(baseItem).getId() + ")";
        }
        return "ItemWrapper(type=" + baseItem.getType()
            + (baseItemMeta != null ? ", meta=" + baseItemMeta : "") + ")";
    }
}
