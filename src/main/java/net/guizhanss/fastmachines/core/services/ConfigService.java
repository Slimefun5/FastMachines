package net.guizhanss.fastmachines.core.services;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.libs.guizhanlib.config.AddonConfig;
import net.guizhanss.fastmachines.libs.guizhanlib.config.ConfigField;

public class ConfigService {

    private ConfigField<Boolean> autoUpdate;
    private ConfigField<Boolean> debug;
    private ConfigField<String> lang;
    private ConfigField<Boolean> enableResearches;

    // fast machines options
    private ConfigField<Integer> fmTickRate;
    private ConfigField<Boolean> fmUseEnergy;
    private ConfigField<Boolean> fmRequireSfResearch;
    private ConfigField<Boolean> fmUseBukkitItemComparison;

    private final AddonConfig config;

    public ConfigService(FastMachines plugin) {
        this.config = AddonConfig.of(plugin, "config.yml", b -> {
            autoUpdate = b.booleanField("auto-update", true);
            debug = b.booleanField("debug", false);
            lang = b.stringField("lang", FastMachines.DEFAULT_LANG);
            enableResearches = b.booleanField("enable-researches", false);
            fmTickRate = b.intField("fast-machines.tick-rate", 10, 5, 600);
            fmUseEnergy = b.booleanField("fast-machines.use-energy", true);
            fmRequireSfResearch = b.booleanField("fast-machines.require-sf-research", false);
            fmUseBukkitItemComparison = b.booleanField("fast-machines.use-bukkit-items", false);
        });
        reload();
    }

    public void reload() {
        config.reload();
    }

    public ConfigField<Boolean> getAutoUpdate() {
        return autoUpdate;
    }

    public ConfigField<Boolean> getDebug() {
        return debug;
    }

    public ConfigField<String> getLang() {
        return lang;
    }

    public ConfigField<Boolean> getEnableResearches() {
        return enableResearches;
    }

    public ConfigField<Integer> getFmTickRate() {
        return fmTickRate;
    }

    public ConfigField<Boolean> getFmUseEnergy() {
        return fmUseEnergy;
    }

    public ConfigField<Boolean> getFmRequireSfResearch() {
        return fmRequireSfResearch;
    }

    public ConfigField<Boolean> getFmUseBukkitItemComparison() {
        return fmUseBukkitItemComparison;
    }
}
