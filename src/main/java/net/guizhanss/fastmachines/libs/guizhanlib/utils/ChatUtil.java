package net.guizhanss.fastmachines.libs.guizhanlib.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Java-8-safe port of GuizhanLib's {@code ChatUtil}. Vendored (not a compile dependency) because
 * upstream GuizhanLib is Java-16 bytecode and coupled to the pre-fork {@code slimefun4} API.
 */
@SuppressWarnings("deprecation")
public final class ChatUtil {

    private ChatUtil() {
    }

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static List<String> color(List<String> strList) {
        List<String> result = new ArrayList<>(strList.size());
        for (String s : strList) {
            result.add(color(s));
        }
        return result;
    }

    public static void send(CommandSender sender, String message, Object... args) {
        sender.sendMessage(color(args.length == 0 ? message : MessageFormat.format(message, args)));
    }
}
