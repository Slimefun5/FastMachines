package net.guizhanss.fastmachines.utils.items;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun5.utils.compatibility.PdcCompat;

import net.guizhanss.fastmachines.utils.constants.Keys;

public final class GuiItems {

    private GuiItems() {
    }

    /**
     * Adds the PDC flag to the {@link ItemStack} in place to indicate it is a display item.
     */
    public static ItemStack asDisplayItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PdcCompat.setByte(meta, Keys.DISPLAY_ITEM, (byte) ThreadLocalRandom.current().nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE + 1));
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Returns a copy of the {@link ItemStack} with the display item flag.
     */
    public static ItemStack toDisplayItem(ItemStack item) {
        return asDisplayItem(item.clone());
    }

    /**
     * Removes the PDC flag from the {@link ItemStack} in place.
     */
    public static ItemStack asNotDisplayItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PdcCompat.remove(meta, Keys.DISPLAY_ITEM);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Returns a copy of the {@link ItemStack} without the display item flag.
     */
    public static ItemStack removeDisplayItem(ItemStack item) {
        return asNotDisplayItem(item.clone());
    }

    public static boolean isDisplayItem(ItemStack item) {
        return item.hasItemMeta() && PdcCompat.hasByte(item.getItemMeta(), Keys.DISPLAY_ITEM);
    }
}
