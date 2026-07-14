package net.guizhanss.fastmachines

import io.github.thebusybiscuit.slimefun5.api.SlimefunAddon
import io.github.thebusybiscuit.slimefun5.libraries.dough.updater.BlobBuildUpdater
import net.guizhanss.fastmachines.core.FMRegistry
import net.guizhanss.fastmachines.core.services.ConfigService
import net.guizhanss.fastmachines.core.services.IntegrationService
import net.guizhanss.fastmachines.core.services.LocalizationService
import net.guizhanss.fastmachines.implementation.groups.FMItemGroups
import net.guizhanss.fastmachines.implementation.items.FMItems
import net.guizhanss.fastmachines.implementation.listeners.CauldronListener
import net.guizhanss.fastmachines.implementation.listeners.GravityListener
import net.guizhanss.fastmachines.implementation.listeners.HopperListener
import net.guizhanss.fastmachines.implementation.listeners.PlayerProfileListener
import net.guizhanss.fastmachines.implementation.listeners.SlimefunRegistryListener
import net.guizhanss.fastmachines.implementation.setup.ResearchSetup
import net.guizhanss.fastmachines.implementation.tasks.FastMachineTickingTask
import net.guizhanss.fastmachines.libs.guizhanlib.Scheduler
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

/**
 * Main plugin class.
 *
 * Unlike the upstream build this does NOT extend GuizhanLib's `AbstractAddon` (which `implements` the
 * pre-fork `io.github.thebusybiscuit.slimefun4.api.SlimefunAddon` and ships as Java-16 bytecode). It
 * implements the fork's own [SlimefunAddon] directly (same approach as SMG/GeneticChickengineering),
 * and the Kotlin stdlib is now shaded into the jar rather than fetched at runtime via Paper's library
 * loader (which is 1.16.5+ and absent on 1.8.8) - so the `load()`/Libby machinery is gone entirely.
 *
 * [SlimefunAddon] is implemented by a separate [addon] object rather than by this class directly:
 * Bukkit's `PluginBase.getName()` is `final`, so a Kotlin class extending [JavaPlugin] cannot also
 * satisfy `SlimefunAddon`'s conflicting `default getName()`/`getLogger()` (Kotlin, unlike Java, forces
 * an explicit override of the diamond, which a final super method forbids).
 */
class FastMachines : JavaPlugin() {

    override fun onEnable() {
        instance = this
        scheduler = Scheduler(this)
        addon = object : SlimefunAddon {
            override fun getJavaPlugin(): JavaPlugin = this@FastMachines
            override fun getBugTrackerURL(): String = "https://github.com/$GITHUB_USER/$GITHUB_REPO/issues"
        }

        FMRegistry

        // config
        configService = ConfigService(this)
        debug("Debug mode is enabled.")

        // localization
        log(Level.INFO, "Loading language...")
        val lang = configService.lang.value
        localization = LocalizationService(this, file)
        localization.idPrefix = "FM_"
        localization.addLanguage(lang)
        if (lang != DEFAULT_LANG) {
            localization.addLanguage(DEFAULT_LANG)
        }
        log(Level.INFO, "Loaded language $lang.")

        // integrations
        integrationService = IntegrationService(this)

        // item groups setup
        FMItemGroups

        // item setup
        FMItems

        // researches setup
        if (configService.enableResearches.value) {
            ResearchSetup
        }

        // listeners & tasks
        setupListeners()
        setupTasks()

        // Metrics setup
        setupMetrics()

        // auto-update
        if (configService.autoUpdate.value) {
            autoUpdate()
        }
    }

    override fun onDisable() {
        Bukkit.getScheduler().cancelTasks(this)
    }

    private fun autoUpdate() {
        val version = description.version
        if (version.startsWith("Dev")) {
            BlobBuildUpdater(this, file, GITHUB_REPO).start()
        }
    }

    private fun setupListeners() {
        CauldronListener(this)
        GravityListener(this)
        HopperListener(this)
        PlayerProfileListener(this)
        SlimefunRegistryListener(this)
    }

    private fun setupTasks() {
        FastMachineTickingTask()
    }

    private fun setupMetrics() {
        Metrics(this, 20046)
    }

    companion object {

        private const val GITHUB_USER = "ybw0014"
        private const val GITHUB_REPO = "FastMachines"
        const val DEFAULT_LANG = "en-US"

        lateinit var instance: FastMachines
            private set
        lateinit var configService: ConfigService
            private set
        lateinit var localization: LocalizationService
            private set
        lateinit var integrationService: IntegrationService
            private set
        lateinit var addon: SlimefunAddon
            private set
        private lateinit var scheduler: Scheduler

        fun scheduler() = scheduler

        fun log(level: Level, message: String) {
            instance.logger.log(level, message)
        }

        fun log(level: Level, ex: Throwable, message: String) {
            instance.logger.log(level, message, ex)
        }

        fun debug(message: String) {
            if (!Companion::configService.isInitialized || !configService.debug.value) return
            log(Level.INFO, "[DEBUG] $message")
        }
    }
}
