package net.guizhanss.fastmachines.libs.guizhanlib

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import kotlin.math.max

/**
 * A tiny task scheduler. Java-8-safe, Kotlin port of GuizhanLib's `Scheduler` - see the package-level
 * note in [net.guizhanss.fastmachines.libs.guizhanlib].
 *
 * @author Mooy1, ybw0014 (original), ported for the Slimefun5 fork
 */
class Scheduler(private val plugin: Plugin) {

    fun run(runnable: Runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable)
    }

    fun runAsync(runnable: Runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable)
    }

    fun repeat(intervalTicks: Int, runnable: Runnable) {
        repeat(intervalTicks, 1, runnable)
    }

    fun repeatAsync(intervalTicks: Int, runnable: Runnable) {
        repeatAsync(intervalTicks, 1, runnable)
    }

    fun repeat(intervalTicks: Int, delayTicks: Int, runnable: Runnable) {
        Bukkit.getScheduler().runTaskTimer(plugin, runnable, delayTicks.toLong(), max(1, intervalTicks).toLong())
    }

    fun repeatAsync(intervalTicks: Int, delayTicks: Int, runnable: Runnable) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delayTicks.toLong(), max(1, intervalTicks).toLong())
    }
}
