package net.guizhanss.fastmachines.implementation.listeners

import me.mrCookieSlime.Slimefun.api.BlockStorage
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.items.attributes.NotAnAnvil
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPhysicsEvent

class GravityListener(plugin: FastMachines) : Listener {

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onAnvilFall(e: BlockPhysicsEvent) {
        if (!isAnvil(e.sourceBlock.type)) return

        val sfItem = BlockStorage.check(e.sourceBlock) ?: return
        if (sfItem is NotAnAnvil) {
            e.isCancelled = true
        }
    }

    // `org.bukkit.Tag` (Tag.ANVIL) was only added in 1.13; a name-based check is equivalent and works
    // on every supported version (1.8+). The three anvil damage states all end in "ANVIL".
    private fun isAnvil(material: Material): Boolean {
        val name = material.name
        return name == "ANVIL" || name.endsWith("_ANVIL")
    }
}
