package net.guizhanss.fastmachines;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun5.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun5.libraries.dough.updater.BlobBuildUpdater;

import org.bstats.bukkit.Metrics;

import net.guizhanss.fastmachines.core.services.ConfigService;
import net.guizhanss.fastmachines.core.services.IntegrationService;
import net.guizhanss.fastmachines.core.services.LocalizationService;
import net.guizhanss.fastmachines.implementation.groups.FMItemGroups;
import net.guizhanss.fastmachines.implementation.items.FMItems;
import net.guizhanss.fastmachines.implementation.listeners.CauldronListener;
import net.guizhanss.fastmachines.implementation.listeners.GravityListener;
import net.guizhanss.fastmachines.implementation.listeners.HopperListener;
import net.guizhanss.fastmachines.implementation.listeners.PlayerProfileListener;
import net.guizhanss.fastmachines.implementation.listeners.SlimefunRegistryListener;
import net.guizhanss.fastmachines.implementation.setup.ResearchSetup;
import net.guizhanss.fastmachines.implementation.tasks.FastMachineTickingTask;
import net.guizhanss.fastmachines.libs.guizhanlib.Scheduler;

/**
 * Main plugin class.
 *
 * Unlike the upstream build this does NOT extend GuizhanLib's {@code AbstractAddon}; it implements the
 * fork's own {@link SlimefunAddon} directly (same approach as the other Java addons in this fork). No
 * Kotlin runtime is involved: the whole plugin is plain Java-8 bytecode, so it loads on every server
 * from 1.8.8 to 26.x.
 */
public class FastMachines extends JavaPlugin implements SlimefunAddon {

    private static final String GITHUB_USER = "ybw0014";
    private static final String GITHUB_REPO = "FastMachines";
    public static final String DEFAULT_LANG = "en-US";

    private static FastMachines instance;
    private static ConfigService configService;
    private static LocalizationService localization;
    private static IntegrationService integrationService;
    private static Scheduler scheduler;

    @Override
    public void onEnable() {
        instance = this;
        scheduler = new Scheduler(this);

        // config
        configService = new ConfigService(this);
        debug("Debug mode is enabled.");

        // localization
        log(Level.INFO, "Loading language...");
        String lang = configService.getLang().getValue();
        localization = new LocalizationService(this, getFile());
        localization.setIdPrefix("FM_");
        localization.addLanguage(lang);
        if (!lang.equals(DEFAULT_LANG)) {
            localization.addLanguage(DEFAULT_LANG);
        }
        log(Level.INFO, "Loaded language " + lang + ".");

        // integrations
        integrationService = new IntegrationService(this);

        // item groups setup
        FMItemGroups.setup();

        // item setup
        FMItems.setup();

        // register per-language item translations (languages/<lang>/items.yml)
        Slimefun.getItemTranslationService().registerTranslations(this);

        // researches setup
        if (configService.getEnableResearches().getValue()) {
            ResearchSetup.setup();
        }

        // listeners & tasks
        setupListeners();
        setupTasks();

        // Metrics setup
        setupMetrics();

        // auto-update
        if (configService.getAutoUpdate().getValue()) {
            autoUpdate();
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/" + GITHUB_USER + "/" + GITHUB_REPO + "/issues";
    }

    private void autoUpdate() {
        String version = getDescription().getVersion();
        if (version.startsWith("Dev")) {
            new BlobBuildUpdater(this, getFile(), GITHUB_REPO).start();
        }
    }

    private void setupListeners() {
        new CauldronListener(this);
        new GravityListener(this);
        new HopperListener(this);
        new PlayerProfileListener(this);
        new SlimefunRegistryListener(this);
    }

    private void setupTasks() {
        new FastMachineTickingTask();
    }

    private void setupMetrics() {
        // Consolidated metrics: only start our own bStats if the server opted out (metrics.disable-addon-metrics = false).
        if (Slimefun.getCfg().contains("metrics.disable-addon-metrics") && !Slimefun.getCfg().getBoolean("metrics.disable-addon-metrics")) {
            new Metrics(this, 20046);
        }
    }

    public static FastMachines getInstance() {
        return instance;
    }

    public static ConfigService getConfigService() {
        return configService;
    }

    public static LocalizationService getLocalization() {
        return localization;
    }

    public static IntegrationService getIntegrationService() {
        return integrationService;
    }

    public static SlimefunAddon getAddon() {
        return instance;
    }

    public static Scheduler scheduler() {
        return scheduler;
    }

    public static void log(Level level, String message) {
        instance.getLogger().log(level, message);
    }

    public static void log(Level level, Throwable ex, String message) {
        instance.getLogger().log(level, message, ex);
    }

    public static void debug(String message) {
        if (configService == null || !configService.getDebug().getValue()) {
            return;
        }
        log(Level.INFO, "[DEBUG] " + message);
    }
}
