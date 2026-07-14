package net.guizhanss.fastmachines.libs.guizhanlib.config;

import java.util.ArrayList;
import java.util.List;

import io.github.thebusybiscuit.slimefun5.libraries.dough.config.Config;

public class AddonConfigBuilder {

    private final Config config;
    private final List<ConfigField<?>> fields = new ArrayList<>();

    AddonConfigBuilder(Config config) {
        this.config = config;
    }

    public ConfigField<Boolean> booleanField(String path, boolean defaultValue) {
        ConfigField<Boolean> field = new ConfigField<>(config, path, defaultValue);
        fields.add(field);
        return field;
    }

    public ConfigField<String> stringField(String path, String defaultValue) {
        ConfigField<String> field = new ConfigField<>(config, path, defaultValue);
        fields.add(field);
        return field;
    }

    public ConfigField<Integer> intField(String path, int defaultValue, Integer min, Integer max) {
        ConfigField<Integer> field = new ConfigField<>(config, path, defaultValue, min, max);
        fields.add(field);
        return field;
    }

    void reloadAll() {
        for (ConfigField<?> field : fields) {
            field.reload();
        }
    }
}
