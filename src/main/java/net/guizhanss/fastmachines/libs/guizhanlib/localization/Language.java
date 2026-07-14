package net.guizhanss.fastmachines.libs.guizhanlib.localization;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Holds the loaded {@link FileConfiguration} for a single language file, backfilling any keys missing
 * from the on-disk copy with the jar's bundled defaults.
 *
 * Java-8-safe port of GuizhanLib's {@code Language}.
 */
public class Language {

    private final String name;
    private final File currentFile;
    private final FileConfiguration config;

    public Language(String name, File currentFile, FileConfiguration defaultConfig) {
        this.name = name;
        this.currentFile = currentFile;
        this.config = YamlConfiguration.loadConfiguration(currentFile);
        config.setDefaults(defaultConfig);
        for (String key : defaultConfig.getKeys(true)) {
            if (!config.contains(key)) {
                config.set(key, defaultConfig.get(key));
            }
        }
        save();
    }

    public String getName() {
        return name;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(currentFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
