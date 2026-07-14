package net.guizhanss.fastmachines.core.items.groups;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey;

public class HiddenItemGroup extends ItemGroup {

    public HiddenItemGroup(NamespacedKey key, ItemStack item) {
        this(key, item, 3);
    }

    public HiddenItemGroup(NamespacedKey key, ItemStack item, int tier) {
        super(key, item, tier);
    }

    @Override
    public boolean isAccessible(Player p) {
        return false;
    }

    @Override
    public boolean isVisible(Player p) {
        return false;
    }
}
