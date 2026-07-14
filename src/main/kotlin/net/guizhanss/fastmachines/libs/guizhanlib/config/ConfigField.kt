package net.guizhanss.fastmachines.libs.guizhanlib.config

import io.github.thebusybiscuit.slimefun5.libraries.dough.config.Config
import org.bukkit.plugin.java.JavaPlugin

/**
 * A single, typed, hot-reloadable config value backed by a [Config] path.
 *
 * Java-8-safe, Kotlin port of the `ConfigField`/`addonConfig` DSL GuizhanLib-kt provided - reimplemented
 * against the fork's own dough [Config] rather than a compile dependency on upstream GuizhanLib (Java-16
 * bytecode, coupled to the pre-fork `slimefun4` API). See the package-level note in
 * [net.guizhanss.fastmachines.libs.guizhanlib].
 */
class ConfigField<T> internal constructor(
    private val config: Config,
    private val path: String,
    private val default: T,
    private val min: Int? = null,
    private val max: Int? = null,
) {

    var value: T = default
        private set

    internal fun reload() {
        var loaded: T = config.getOrSetDefault(path, default)

        if (min != null && max != null && loaded is Int) {
            if (loaded < min || loaded > max) {
                @Suppress("UNCHECKED_CAST")
                loaded = default
                config.setValue(path, loaded)
            }
        }

        value = loaded
    }
}

class AddonConfigBuilder internal constructor(private val config: Config) {

    private val fields = mutableListOf<ConfigField<*>>()

    fun boolean(path: String, default: Boolean): ConfigField<Boolean> =
        ConfigField(config, path, default).also { fields += it }

    fun string(path: String, default: String): ConfigField<String> =
        ConfigField(config, path, default).also { fields += it }

    fun int(path: String, default: Int, min: Int? = null, max: Int? = null): ConfigField<Int> =
        ConfigField(config, path, default, min, max).also { fields += it }

    internal fun reloadAll() {
        fields.forEach { it.reload() }
    }
}

class AddonConfig internal constructor(private val config: Config, private val builder: AddonConfigBuilder) {

    fun reload() {
        config.reload()
        builder.reloadAll()
        config.save()
    }
}

fun addonConfig(plugin: JavaPlugin, fileName: String, block: AddonConfigBuilder.() -> Unit): AddonConfig {
    val config = Config(plugin, fileName)
    val builder = AddonConfigBuilder(config)
    builder.block()
    return AddonConfig(config, builder)
}
