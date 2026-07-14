package net.guizhanss.fastmachines.libs.guizhanlib.config;

import java.util.function.Consumer;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun5.libraries.dough.config.Config;

public class AddonConfig {

    private final Config config;
    private final AddonConfigBuilder builder;

    private AddonConfig(Config config, AddonConfigBuilder builder) {
        this.config = config;
        this.builder = builder;
    }

    public void reload() {
        config.reload();
        builder.reloadAll();
        config.save();
    }

    public static AddonConfig of(JavaPlugin plugin, String fileName, Consumer<AddonConfigBuilder> block) {
        Config config = new Config(plugin, fileName);
        AddonConfigBuilder builder = new AddonConfigBuilder(config);
        block.accept(builder);
        return new AddonConfig(config, builder);
    }
}
