package net.guizhanss.fastmachines.implementation.items.machines.slimefun;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

import net.guizhanss.fastmachines.core.items.attributes.NotACauldron;
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader;
import net.guizhanss.fastmachines.core.recipes.loaders.SlimefunMultiblockRecipeLoader;
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine;
import net.guizhanss.fastmachines.utils.MaterialCompat;

public class FastOreWasher extends BasicFastMachine implements NotACauldron {

    public FastOreWasher(ItemGroup itemGroup, SlimefunItemStack itemStack, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, itemStack, recipeType, recipe);
    }

    @Override
    public Material getCraftItemMaterial() {
        return MaterialCompat.safe(XMaterial.CAULDRON);
    }

    @Override
    public RecipeLoader getRecipeLoader() {
        return new SlimefunMultiblockRecipeLoader(this, SlimefunItems.ORE_WASHER.getItemId(), true);
    }
}
