package net.guizhanss.fastmachines.libs.guizhanlib.localization;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.guizhanss.fastmachines.libs.guizhanlib.utils.ChatUtil;

/**
 * A multi-language, YAML-file-backed localization service. Colors every returned string/list via
 * {@link ChatUtil}. Languages are checked in the order they were added via {@link #addLanguage};
 * the first one containing a non-empty value for a path wins.
 *
 * Java-8-safe port of GuizhanLib's {@code Localization}/{@code MinecraftLocalization}.
 */
public class Localization {

    protected final JavaPlugin plugin;
    private final String folderName;
    private final List<String> languages = new ArrayList<>();
    private final Map<String, Language> langMap = new HashMap<>();
    private final File langFolder;

    public Localization(JavaPlugin plugin) {
        this(plugin, "lang");
    }

    public Localization(JavaPlugin plugin, String folderName) {
        this.plugin = plugin;
        this.folderName = folderName;
        this.langFolder = new File(plugin.getDataFolder(), folderName);
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        if (!langFolder.exists()) {
            langFolder.mkdirs();
        }
    }

    public void addLanguage(String langFilename) {
        File langFile = new File(langFolder, langFilename + ".yml");
        String resourcePath = folderName + "/" + langFilename + ".yml";

        if (!langFile.exists()) {
            try {
                plugin.saveResource(resourcePath, false);
            } catch (IllegalArgumentException ex) {
                plugin.getLogger().log(Level.SEVERE, "The default language file {0} does not exist in jar file!", resourcePath);
                return;
            }
        }

        languages.add(langFilename);

        InputStream resource = plugin.getResource(resourcePath);
        if (resource == null) {
            return;
        }
        FileConfiguration defaultConfig;
        try (InputStreamReader reader = new InputStreamReader(resource, StandardCharsets.UTF_8)) {
            defaultConfig = YamlConfiguration.loadConfiguration(reader);
        } catch (java.io.IOException e) {
            return;
        }
        langMap.put(langFilename, new Language(langFilename, langFile, defaultConfig));
    }

    public String getString(String path) {
        for (String lang : languages) {
            Language language = langMap.get(lang);
            if (language == null) {
                continue;
            }
            String value = language.getConfig().getString(path);
            if (value != null) {
                return ChatUtil.color(value);
            }
        }
        return "";
    }

    public List<String> getStringList(String path) {
        for (String lang : languages) {
            Language language = langMap.get(lang);
            if (language == null) {
                continue;
            }
            List<String> value = language.getConfig().getStringList(path);
            if (value != null && !value.isEmpty()) {
                return ChatUtil.color(value);
            }
        }
        return new ArrayList<>();
    }
}
