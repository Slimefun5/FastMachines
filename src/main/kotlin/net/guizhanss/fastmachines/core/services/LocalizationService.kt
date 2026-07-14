package net.guizhanss.fastmachines.core.services

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun5.utils.SlimefunUtils
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.libs.guizhanlib.localization.Localization
import net.guizhanss.fastmachines.libs.guizhanlib.utils.ChatUtil
import net.guizhanss.fastmachines.libs.guizhanlib.utils.FileUtil
import net.guizhanss.fastmachines.utils.toId
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack
import java.io.File
import java.text.MessageFormat

/**
 * Reimplemented against the fork's own [SlimefunItemStack]/[SlimefunUtils] rather than GuizhanLib's
 * `SlimefunLocalization`: that upstream class's item helpers return the pre-fork
 * `io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack`, a type this package no longer has.
 */
class LocalizationService(
    plugin: FastMachines,
    private val jarFile: File,
) : Localization(plugin) {

    var idPrefix: String = ""

    init {
        extractTranslations()
    }

    private fun extractTranslations() {
        val translationsFolder = File(plugin.dataFolder, FOLDER_NAME)
        if (!translationsFolder.exists()) {
            translationsFolder.mkdirs()
        }
        val translationFiles = FileUtil.listJarEntries(
            jarFile,
            { entryName, entry -> entryName.startsWith("$FOLDER_NAME/") && !entry.isDirectory && entryName.endsWith(".yml") },
            { entryName, _ -> entryName.replace("$FOLDER_NAME/", "") }
        )
        for (translationFile in translationFiles) {
            val filePath = FOLDER_NAME + File.separator + translationFile
            plugin.saveResource(filePath, true)
        }
    }

    fun getString(key: String, vararg args: Any?): String = MessageFormat.format(getString(key), *args)

    // items
    fun getItemName(itemId: String, vararg args: Any?) = getString("items.${itemId.toId()}.name", *args)
    fun getItemLore(itemId: String): List<String> = getStringList("items.${itemId.toId()}.lore")

    fun getItem(id: String, material: Material, vararg extraLore: String): SlimefunItemStack =
        getItem(id, ItemStack(material), *extraLore)

    fun getItem(id: String, texture: String, vararg extraLore: String): SlimefunItemStack =
        getItem(id, SlimefunUtils.getCustomHead(texture), *extraLore)

    fun getItem(id: String, itemStack: ItemStack, vararg extraLore: String): SlimefunItemStack {
        val lore = (getItemLore(id) + extraLore).toTypedArray()
        return SlimefunItemStack("$idPrefix$id", itemStack, getItemName(id), *lore)
    }

    fun sendMessage(sender: CommandSender, key: String, vararg args: Any) {
        ChatUtil.send(sender, MessageFormat.format(getString("messages.$key"), *args))
    }

    companion object {

        const val FOLDER_NAME = "lang"
    }
}
