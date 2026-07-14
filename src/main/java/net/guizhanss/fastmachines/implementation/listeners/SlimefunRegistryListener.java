package net.guizhanss.fastmachines.implementation.listeners;

import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import io.github.thebusybiscuit.slimefun5.api.events.SlimefunItemRegistryFinalizedEvent;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.FMRegistry;
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine;

public class SlimefunRegistryListener implements Listener {

    public SlimefunRegistryListener(FastMachines plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRegistryLoaded(SlimefunItemRegistryFinalizedEvent e) {
        for (BaseFastMachine machine : FMRegistry.ENABLED_FAST_MACHINES) {
            FastMachines.debug("Registering recipes for " + machine.getClass().getSimpleName());
            try {
                machine.getRecipeLoader().load();
            } catch (Exception ex) {
                FastMachines.log(Level.SEVERE, ex,
                    "An error has occurred while registering recipes for " + machine.getClass().getSimpleName());
            }
        }
    }
}
