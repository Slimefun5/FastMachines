package net.guizhanss.fastmachines.libs.guizhanlib.config;

import io.github.thebusybiscuit.slimefun5.libraries.dough.config.Config;

/**
 * A single, typed, hot-reloadable config value backed by a {@link Config} path.
 *
 * Java-8-safe port of the {@code ConfigField}/{@code addonConfig} DSL GuizhanLib-kt provided -
 * reimplemented against the fork's own dough {@link Config} rather than a compile dependency on
 * upstream GuizhanLib (Java-16 bytecode, coupled to Slimefun's pre-fork API).
 */
public class ConfigField<T> {

    private final Config config;
    private final String path;
    private final T defaultValue;
    private final Integer min;
    private final Integer max;

    private T value;

    ConfigField(Config config, String path, T defaultValue) {
        this(config, path, defaultValue, null, null);
    }

    ConfigField(Config config, String path, T defaultValue, Integer min, Integer max) {
        this.config = config;
        this.path = path;
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.value = defaultValue;
    }

    public T getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    void reload() {
        T loaded = config.getOrSetDefault(path, defaultValue);

        if (min != null && max != null && loaded instanceof Integer) {
            int loadedInt = (Integer) loaded;
            if (loadedInt < min || loadedInt > max) {
                loaded = defaultValue;
                config.setValue(path, loaded);
            }
        }

        value = loaded;
    }
}
