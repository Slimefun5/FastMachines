package net.guizhanss.fastmachines.implementation.listeners;

import org.bukkit.event.Listener;

import net.guizhanss.fastmachines.FastMachines;

public class CauldronListener implements Listener {

    public CauldronListener(FastMachines plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
