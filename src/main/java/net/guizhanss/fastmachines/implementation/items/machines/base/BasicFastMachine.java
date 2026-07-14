package net.guizhanss.fastmachines.implementation.items.machines.base;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;

/**
 * The basic machine level fast machine.
 */
public abstract class BasicFastMachine extends BaseFastMachine {

    protected BasicFastMachine(ItemGroup itemGroup, SlimefunItemStack itemStack, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, itemStack, recipeType, recipe, 1024, 8);
    }
}
