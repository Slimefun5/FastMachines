package net.guizhanss.fastmachines.core.services;

import net.guizhanss.fastmachines.FastMachines;

/**
 * Slimefun 4-era soft-dependencies ({@code SlimefunTranslation}, {@code SlimeFrame},
 * {@code InfinityExpansion2}) have not been ported to this fork yet, so those integrations are
 * gated/removed entirely rather than left as a broken compile dependency. Only
 * {@code InfinityExpansion}, which already has a slimefun5-compatible release, is kept.
 */
public class IntegrationService {

    private final FastMachines plugin;
    private final boolean infinityExpansionEnabled;

    public IntegrationService(FastMachines plugin) {
        this.plugin = plugin;
        this.infinityExpansionEnabled = isEnabled("InfinityExpansion");
    }

    public boolean isInfinityExpansionEnabled() {
        return infinityExpansionEnabled;
    }

    private boolean isEnabled(String pluginName) {
        return plugin.getServer().getPluginManager().isPluginEnabled(pluginName);
    }
}
