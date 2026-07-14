package net.guizhanss.fastmachines.implementation.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;

import me.mrCookieSlime.Slimefun.api.BlockStorage;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.items.attributes.NotAHopper;
import net.guizhanss.fastmachines.utils.CompatUtils;

public class HopperListener implements Listener {

    public HopperListener(FastMachines plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPickupItem(InventoryPickupItemEvent e) {
        if (e.getInventory().getType() != InventoryType.HOPPER) {
            return;
        }

        // Inventory#getLocation() does not exist on 1.8.8 - resolve reflectively (null-safe there).
        Location loc = CompatUtils.inventoryLocation(e.getInventory());
        if (loc == null) {
            return;
        }

        if (BlockStorage.check(loc) instanceof NotAHopper) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMoveItemIntoHopper(InventoryMoveItemEvent e) {
        if (e.getDestination().getType() != InventoryType.HOPPER) {
            return;
        }

        Location loc = CompatUtils.inventoryLocation(e.getDestination());
        if (loc == null) {
            return;
        }

        if (BlockStorage.check(loc) instanceof NotAHopper) {
            e.setCancelled(true);
        }
    }
}
