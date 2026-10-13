package net.guizhanss.fastmachines.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.libs.guizhanlib.utils.ChatUtil;

/**
 * Resolves this addon's chat text for one viewer, from {@code languages/<lang>/messages.yml}.
 * <p>
 * This addon already had a language pack under {@code lang/}, but a pack is chosen once in
 * {@code config.yml} and so reads the same for everybody on the server. These keys resolve per
 * viewer instead, and the pack stays authoritative wherever an operator edited it.
 */
public final class Messages {

    private Messages() {}

    /**
     * Maps a {@code messages.yml} key to the language-pack key it took over from.
     *
     * @implNote Only keys that existed in the pack appear here; anything absent is new to the
     *           per-viewer file and has no operator wording to respect.
     */
    private static final Map<String, String> PACK_KEYS = packKeys(
            "machine.not-enough-energy", "messages.not-enough-energy",
            "machine.not-enough-materials", "messages.not-enough-materials",
            "machine.profile-not-loaded", "messages.profile-not-loaded",
            "machine.no-research", "messages.no-research");

    @Nonnull
    private static Map<String, String> packKeys(@Nonnull String... pairs) {
        Map<String, String> keys = new HashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            keys.put(pairs[i], pairs[i + 1]);
        }
        return Collections.unmodifiableMap(keys);
    }

    /**
     * This addon's chat text in the viewer's own language.
     *
     * @implNote An operator who edited the line in their own language pack keeps their wording
     *           verbatim: the pack is copied into the data folder and documented as editable, so a
     *           translation may only stand in for a line nobody replaced. The comparison is made
     *           against the SHIPPED copy of the pack the operator actually chose, so selecting a
     *           non-English pack is not mistaken for editing every line of it.
     */
    @Nonnull
    public static String get(@Nullable Player viewer, @Nonnull String key, @Nonnull String english) {
        String packKey = PACK_KEYS.get(key);

        if (packKey != null && FastMachines.getLocalization().isCustomised(packKey)) {
            return FastMachines.getLocalization().getString(packKey);
        }

        return Slimefun.getAddonMessageService().getMessage(viewer, FastMachines.getInstance(), key, english);
    }

    public static void send(@Nonnull CommandSender recipient, @Nonnull ChatColor colour,
                            @Nonnull String key, @Nonnull String english) {
        Player viewer = recipient instanceof Player ? (Player) recipient : null;
        ChatUtil.send(recipient, colour + get(viewer, key, english));
    }
}
