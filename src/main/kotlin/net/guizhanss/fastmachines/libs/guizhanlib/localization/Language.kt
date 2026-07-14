package net.guizhanss.fastmachines.libs.guizhanlib.localization

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * Holds the loaded [FileConfiguration] for a single language file, backfilling any keys missing from
 * the on-disk copy with the jar's bundled defaults.
 *
 * Java-8-safe, Kotlin port of GuizhanLib's `Language` - see the package-level note in
 * [net.guizhanss.fastmachines.libs.guizhanlib].
 */
class Language(
    val name: String,
    private val currentFile: File,
    defaultConfig: FileConfiguration,
) {

    val config: FileConfiguration = YamlConfiguration.loadConfiguration(currentFile).apply {
        setDefaults(defaultConfig)
        for (key in defaultConfig.getKeys(true)) {
            if (!contains(key)) {
                set(key, defaultConfig.get(key))
            }
        }
    }

    init {
        save()
    }

    fun save() {
        try {
            config.save(currentFile)
        } catch (ex: java.io.IOException) {
            ex.printStackTrace()
        }
    }
}
