package net.guizhanss.fastmachines.implementation.items.machines.slimefun;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

import net.guizhanss.fastmachines.core.items.attributes.NotAHopper;
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader;
import net.guizhanss.fastmachines.core.recipes.loaders.SlimefunDisplayRecipeLoader;
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine;
import net.guizhanss.fastmachines.utils.MaterialCompat;

public class FastPanningMachine extends BasicFastMachine implements NotAHopper {

    public FastPanningMachine(ItemGroup itemGroup, SlimefunItemStack itemStack, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, itemStack, recipeType, recipe);
    }

    @Override
    public Material getCraftItemMaterial() {
        return MaterialCompat.safe(XMaterial.BOWL);
    }

    @Override
    public RecipeLoader getRecipeLoader() {
        return new SlimefunDisplayRecipeLoader(this, SlimefunItems.AUTOMATED_PANNING_MACHINE.getItemId(), true);
    }
}
