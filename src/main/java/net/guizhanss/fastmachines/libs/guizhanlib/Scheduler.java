package net.guizhanss.fastmachines.libs.guizhanlib;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * A tiny task scheduler. Java-8-safe port of GuizhanLib's {@code Scheduler}.
 *
 * @author Mooy1, ybw0014 (original), ported for the Slimefun5 fork
 */
public class Scheduler {

    private final Plugin plugin;

    public Scheduler(Plugin plugin) {
        this.plugin = plugin;
    }

    public void run(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

    public void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public void repeat(int intervalTicks, Runnable runnable) {
        repeat(intervalTicks, 1, runnable);
    }

    public void repeatAsync(int intervalTicks, Runnable runnable) {
        repeatAsync(intervalTicks, 1, runnable);
    }

    public void repeat(int intervalTicks, int delayTicks, Runnable runnable) {
        Bukkit.getScheduler().runTaskTimer(plugin, runnable, delayTicks, Math.max(1, intervalTicks));
    }

    public void repeatAsync(int intervalTicks, int delayTicks, Runnable runnable) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delayTicks, Math.max(1, intervalTicks));
    }
}
