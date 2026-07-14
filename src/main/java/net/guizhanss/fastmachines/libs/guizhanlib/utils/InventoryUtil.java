package net.guizhanss.fastmachines.libs.guizhanlib.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Java-8-safe port of GuizhanLib's {@code InventoryUtil}.
 */
public final class InventoryUtil {

    private InventoryUtil() {
    }

    public static void push(Player p, ItemStack... itemStacks) {
        push(p, p.getLocation(), itemStacks);
    }

    public static void push(Player p, Location loc, ItemStack... itemStacks) {
        for (ItemStack item : p.getInventory().addItem(itemStacks).values()) {
            p.getWorld().dropItem(loc, item.clone());
        }
    }
}
