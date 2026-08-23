package net.guizhanss.fastmachines.core.services;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun5.utils.SlimefunUtils;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.libs.guizhanlib.localization.Localization;
import net.guizhanss.fastmachines.libs.guizhanlib.utils.ChatUtil;
import net.guizhanss.fastmachines.libs.guizhanlib.utils.FileUtil;

/**
 * Reimplemented against the fork's own {@link SlimefunItemStack}/{@link SlimefunUtils} rather than
 * GuizhanLib's {@code SlimefunLocalization}: that upstream class's item helpers return the
 * pre-fork package's {@code SlimefunItemStack}, a type this fork's renamed API no longer has.
 */
public class LocalizationService extends Localization {

    public static final String FOLDER_NAME = "lang";

    private final File jarFile;
    private String idPrefix = "";

    public LocalizationService(FastMachines plugin, File jarFile) {
        super(plugin);
        this.jarFile = jarFile;
        extractTranslations();
    }

    public String getIdPrefix() {
        return idPrefix;
    }

    public void setIdPrefix(String idPrefix) {
        this.idPrefix = idPrefix;
    }

    private void extractTranslations() {
        File translationsFolder = new File(plugin.getDataFolder(), FOLDER_NAME);
        if (!translationsFolder.exists()) {
            translationsFolder.mkdirs();
        }
        List<String> translationFiles;
        try {
            translationFiles = FileUtil.listJarEntries(
                jarFile,
                (entryName, entry) -> entryName.startsWith(FOLDER_NAME + "/") && !entry.isDirectory() && entryName.endsWith(".yml"),
                (entryName, entry) -> entryName.replace(FOLDER_NAME + "/", "")
            );
        } catch (java.io.IOException e) {
            return;
        }
        for (String translationFile : translationFiles) {
            String filePath = FOLDER_NAME + File.separator + translationFile;
            plugin.saveResource(filePath, true);
        }
    }

    public String getString(String key, Object... args) {
        return MessageFormat.format(getString(key), args);
    }

    public String getItemName(String itemId, Object... args) {
        return getString("items." + itemId + ".name", args);
    }

    public List<String> getItemLore(String itemId) {
        return getStringList("items." + itemId + ".lore");
    }

    public SlimefunItemStack getItem(String id, Material material, String... extraLore) {
        return getItem(id, new ItemStack(material), extraLore);
    }

    public SlimefunItemStack getItem(String id, String texture, String... extraLore) {
        return getItem(id, SlimefunUtils.getCustomHead(texture), extraLore);
    }

    /**
     * An item-group icon: pure decoration, never a registered item.
     *
     * @implNote Deliberately NOT a {@code SlimefunItemStack}: that type always overwrites the display name
     *           with the raw id, so the category rendered as "FM_FAST_MACHINES" in the guide.
     */
    public ItemStack getItemGroupItem(String id, ItemStack itemStack) {
        return CustomItemStack.create(itemStack, getItemName(id), getItemLore(id).toArray(new String[0]));
    }

    public ItemStack getItemGroupItem(String id, Material material) {
        return getItemGroupItem(id, new ItemStack(material));
    }

    public ItemStack getItemGroupItem(String id, String texture) {
        return getItemGroupItem(id, SlimefunUtils.getCustomHead(texture));
    }

    public SlimefunItemStack getItem(String id, ItemStack itemStack, String... extraLore) {
        List<String> lore = new ArrayList<>(getItemLore(id));
        lore.addAll(Arrays.asList(extraLore));
        return new SlimefunItemStack(idPrefix + id, itemStack, getItemName(id), lore.toArray(new String[0]));
    }

    public void sendMessage(CommandSender sender, String key, Object... args) {
        ChatUtil.send(sender, MessageFormat.format(getString("messages." + key), args));
    }
}
