package net.guizhanss.fastmachines.libs.guizhanlib.utils

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

/**
 * Java-8-safe, Kotlin port of GuizhanLib's `ChatUtil`. Vendored (not a compile dependency) because
 * upstream GuizhanLib is Java-16 bytecode and coupled to the pre-fork `slimefun4` API - see the
 * package-level note in [net.guizhanss.fastmachines.libs.guizhanlib].
 */
@Suppress("DEPRECATION")
object ChatUtil {

    fun color(str: String): String = ChatColor.translateAlternateColorCodes('&', str)

    fun color(strList: List<String>): List<String> = strList.map(::color)

    fun send(sender: CommandSender, message: String, vararg args: Any?) {
        sender.sendMessage(color(if (args.isEmpty()) message else java.text.MessageFormat.format(message, *args)))
    }
}
