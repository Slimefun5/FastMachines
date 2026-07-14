package net.guizhanss.fastmachines.utils.constants;

import io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey;

import net.guizhanss.fastmachines.FastMachines;

public final class Keys {

    public static final NamespacedKey MAIN_GROUP = fmKey("fast_machines");
    public static final NamespacedKey MATERIALS = fmKey("materials");
    public static final NamespacedKey MACHINES = fmKey("machines");
    public static final NamespacedKey HIDDEN = fmKey("hidden");
    public static final NamespacedKey DISPLAY_ITEM = fmKey("display_item");

    private Keys() {
    }

    private static NamespacedKey fmKey(String key) {
        return new NamespacedKey(FastMachines.getInstance(), key);
    }
}
