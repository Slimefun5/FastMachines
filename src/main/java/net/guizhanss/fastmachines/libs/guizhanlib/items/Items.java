package net.guizhanss.fastmachines.libs.guizhanlib.items;

import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.libraries.dough.blocks.BlockPosition;

/**
 * Java-8-safe ports of GuizhanLib-kt's {@link ItemStack}/{@link SlimefunItem}/{@link Location}
 * extensions.
 */
public final class Items {

    private Items() {
    }

    public static ItemStack toItem(Material material) {
        return new ItemStack(material);
    }

    public static ItemStack edit(ItemStack stack, Consumer<ItemStackEditor> block) {
        block.accept(new ItemStackEditor(stack));
        return stack;
    }

    /** Edits a copy of this {@link SlimefunItemStack}'s underlying item, leaving the registered stack untouched. */
    public static ItemStack edit(SlimefunItemStack stack, Consumer<ItemStackEditor> block) {
        return edit(stack.item().clone(), block);
    }

    public static boolean isSlimefunItem(ItemStack item) {
        return SlimefunItem.getByItem(item) != null;
    }

    public static SlimefunItem getSlimefunItem(ItemStack item) {
        SlimefunItem sfItem = SlimefunItem.getByItem(item);
        if (sfItem == null) {
            throw new IllegalStateException("This ItemStack is not a registered Slimefun item");
        }
        return sfItem;
    }

    public static BlockPosition position(Location location) {
        return new BlockPosition(location);
    }
}
