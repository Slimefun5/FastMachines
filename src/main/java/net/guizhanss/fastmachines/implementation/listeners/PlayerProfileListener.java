package net.guizhanss.fastmachines.implementation.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.thebusybiscuit.slimefun5.api.player.PlayerProfile;

import net.guizhanss.fastmachines.FastMachines;

public class PlayerProfileListener implements Listener {

    public PlayerProfileListener(FastMachines plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        // Load the player profile when the player joins, so that we can use it later
        PlayerProfile.get(e.getPlayer(), profile -> {
        });
    }
}
