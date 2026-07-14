package net.guizhanss.fastmachines.core.services

import net.guizhanss.fastmachines.FastMachines

/**
 * Slimefun 4-era soft-dependencies (`SlimefunTranslation`, `SlimeFrame`, `InfinityExpansion2`) have not
 * been ported to this fork yet, so those integrations are gated/removed entirely rather than left as a
 * broken compile dependency - see [net.guizhanss.fastmachines.FastMachines]'s build notes. Only
 * `InfinityExpansion`, which already has a slimefun5-compatible release, is kept.
 */
class IntegrationService(private val plugin: FastMachines) {

    val infinityExpansionEnabled = isEnabled("InfinityExpansion")

    private fun isEnabled(pluginName: String): Boolean {
        return plugin.server.pluginManager.isPluginEnabled(pluginName)
    }
}
