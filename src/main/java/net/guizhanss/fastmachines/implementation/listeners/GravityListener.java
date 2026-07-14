package net.guizhanss.fastmachines.implementation.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;

import me.mrCookieSlime.Slimefun.api.BlockStorage;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.items.attributes.NotAnAnvil;
import net.guizhanss.fastmachines.utils.CompatUtils;

public class GravityListener implements Listener {

    public GravityListener(FastMachines plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onAnvilFall(BlockPhysicsEvent e) {
        // getSourceBlock() is 1.9+; resolve version-safely (falls back to getBlock() on 1.8.8).
        Block source = CompatUtils.physicsSourceBlock(e);
        if (!isAnvil(source.getType())) {
            return;
        }

        SlimefunItem sfItem = BlockStorage.check(source);
        if (sfItem instanceof NotAnAnvil) {
            e.setCancelled(true);
        }
    }

    // `org.bukkit.Tag` (Tag.ANVIL) was only added in 1.13; a name-based check is equivalent and works
    // on every supported version (1.8+). The three anvil damage states all end in "ANVIL".
    private boolean isAnvil(Material material) {
        String name = material.name();
        return name.equals("ANVIL") || name.endsWith("_ANVIL");
    }
}
