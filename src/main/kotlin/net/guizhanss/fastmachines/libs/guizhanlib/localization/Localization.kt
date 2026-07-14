package net.guizhanss.fastmachines.libs.guizhanlib.localization

import net.guizhanss.fastmachines.libs.guizhanlib.utils.ChatUtil
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.logging.Level

/**
 * A multi-language, YAML-file-backed localization service. Colors every returned string/list via
 * [ChatUtil]. Languages are checked in the order they were added via [addLanguage]; the first one
 * containing a non-empty value for a path wins.
 *
 * Java-8-safe, Kotlin port of GuizhanLib's `Localization`/`MinecraftLocalization` - see the
 * package-level note in [net.guizhanss.fastmachines.libs.guizhanlib].
 */
open class Localization(
    protected val plugin: JavaPlugin,
    private val folderName: String = "lang",
) {

    private val languages = mutableListOf<String>()
    private val langMap = mutableMapOf<String, Language>()
    private val langFolder = File(plugin.dataFolder, folderName)

    init {
        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdirs()
        }
        if (!langFolder.exists()) {
            langFolder.mkdirs()
        }
    }

    fun addLanguage(langFilename: String) {
        val langFile = File(langFolder, "$langFilename.yml")
        val resourcePath = "$folderName/$langFilename.yml"

        if (!langFile.exists()) {
            try {
                plugin.saveResource(resourcePath, false)
            } catch (ex: IllegalArgumentException) {
                plugin.logger.log(Level.SEVERE, "The default language file {0} does not exist in jar file!", resourcePath)
                return
            }
        }

        languages.add(langFilename)

        val resource = plugin.getResource(resourcePath) ?: return
        val defaultConfig = InputStreamReader(resource, StandardCharsets.UTF_8).use {
            YamlConfiguration.loadConfiguration(it)
        }
        langMap[langFilename] = Language(langFilename, langFile, defaultConfig)
    }

    open fun getString(path: String): String {
        for (lang in languages) {
            val value = langMap[lang]?.config?.getString(path)
            if (value != null) return ChatUtil.color(value)
        }
        return ""
    }

    open fun getStringList(path: String): List<String> {
        for (lang in languages) {
            val value = langMap[lang]?.config?.getStringList(path)
            if (!value.isNullOrEmpty()) return ChatUtil.color(value)
        }
        return emptyList()
    }
}
