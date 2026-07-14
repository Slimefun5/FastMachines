package net.guizhanss.fastmachines.implementation.listeners

import me.mrCookieSlime.Slimefun.api.BlockStorage
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.items.attributes.NotAHopper
import net.guizhanss.fastmachines.utils.CompatUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.inventory.InventoryPickupItemEvent
import org.bukkit.event.inventory.InventoryType

class HopperListener(plugin: FastMachines) : Listener {

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onPickupItem(e: InventoryPickupItemEvent) {
        if (e.inventory.type != InventoryType.HOPPER) return

        // Inventory#getLocation() does not exist on 1.8.8 - resolve reflectively (null-safe there).
        val loc = CompatUtils.inventoryLocation(e.inventory) ?: return

        val sfItem = BlockStorage.check(loc)
        if (sfItem is NotAHopper) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onMoveItemIntoHopper(e: InventoryMoveItemEvent) {
        if (e.destination.type != InventoryType.HOPPER) return

        val loc = CompatUtils.inventoryLocation(e.destination) ?: return

        if (BlockStorage.check(loc) is NotAHopper) {
            e.isCancelled = true
        }
    }
}
