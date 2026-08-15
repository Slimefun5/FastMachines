package net.guizhanss.fastmachines.libs.guizhanlib.slimefun.machines;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockPlaceHandler;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;

/**
 * A {@link MenuBlock} is a {@link SlimefunItem} with a {@link BlockMenuPreset}.
 *
 * Java-8-safe port of GuizhanLib 0.9.0's {@code MenuBlock} (itself "Modified from InfinityLib"):
 * the upstream jar is class-file version 60 (Java 16) and cannot be read by a Java-8 javac as a
 * compile dependency, so this vendored copy is used instead. Only the API package name was
 * updated for the fork; the logic is unchanged from upstream.
 *
 * @author Mooy1 (InfinityLib original)
 * @author ybw0014 (GuizhanLib port)
 */
public abstract class MenuBlock extends SlimefunItem {

    protected MenuBlock(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        addItemHandler(
            new BlockBreakHandler(false, false) {
                @Override
                public void onPlayerBreak(BlockBreakEvent e, ItemStack itemStack, List<ItemStack> list) {
                    BlockMenu menu = BlockStorage.getInventory(e.getBlock());
                    if (menu == null) {
                        return;
                    }
                    onBreak(e, menu);
                }
            },
            new BlockPlaceHandler(false) {
                @Override
                public void onPlayerPlace(BlockPlaceEvent e) {
                    onPlace(e, e.getBlockPlaced());
                }
            }
        );
    }

    @Override
    public void postRegister() {
        new MenuBlockPreset(this);
    }

    protected abstract void setup(BlockMenuPreset preset);

    int[] getTransportSlots(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        switch (flow) {
            case INSERT:
                return getInputSlots(menu, item);
            case WITHDRAW:
                return getOutputSlots();
            default:
                return new int[0];
        }
    }

    protected int[] getInputSlots(DirtyChestMenu menu, ItemStack item) {
        return getInputSlots();
    }

    protected abstract int[] getInputSlots();

    protected abstract int[] getOutputSlots();

    protected void onNewInstance(BlockMenu menu, Block b) {
        // no-op by default
    }

    protected void onBreak(BlockBreakEvent e, BlockMenu menu) {
        org.bukkit.Location l = menu.getLocation();
        menu.dropItems(l, getInputSlots());
        menu.dropItems(l, getOutputSlots());
    }

    protected void onPlace(BlockPlaceEvent e, Block b) {
        // no-op by default
    }
}
