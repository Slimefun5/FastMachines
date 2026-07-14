package net.guizhanss.fastmachines.utils.items;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;

import net.guizhanss.fastmachines.libs.guizhanlib.items.Items;

public final class SlimefunItemExt {

    private SlimefunItemExt() {
    }

    public static SlimefunItem getSfItem(String id) {
        return SlimefunItem.getById(id.toUpperCase());
    }

    public static boolean isDisabled(ItemStack item) {
        if (!Items.isSlimefunItem(item)) {
            return false;
        }
        return Items.getSlimefunItem(item).isDisabled();
    }

    public static boolean isDisabledIn(ItemStack item, World world) {
        if (!Items.isSlimefunItem(item)) {
            return false;
        }
        return Items.getSlimefunItem(item).isDisabledIn(world);
    }
}
