package net.guizhanss.fastmachines.libs.guizhanlib.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.guizhanss.fastmachines.libs.guizhanlib.utils.ChatUtil;

/**
 * A tiny builder for editing an {@link ItemStack}'s meta in place, ported from GuizhanLib-kt's
 * {@code edit} extension.
 */
public class ItemStackEditor {

    private final ItemStack stack;

    public ItemStackEditor(ItemStack stack) {
        this.stack = stack;
    }

    public void name(String name) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) {
            return;
        }
        meta.setDisplayName(ChatUtil.color(name));
        stack.setItemMeta(meta);
    }

    public void lore(String... lines) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) {
            return;
        }
        List<String> lore = new ArrayList<>(lines.length);
        for (String line : lines) {
            lore.add(ChatUtil.color(line));
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
    }

    public void amount(int amount) {
        stack.setAmount(amount);
    }
}
