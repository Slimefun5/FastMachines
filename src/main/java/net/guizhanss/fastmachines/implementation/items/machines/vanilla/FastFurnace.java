package net.guizhanss.fastmachines.implementation.items.machines.vanilla;

import org.bukkit.Material;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader;
import net.guizhanss.fastmachines.core.recipes.loaders.VanillaRecipeLoader;
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine;
import net.guizhanss.fastmachines.utils.CompatUtils;
import net.guizhanss.fastmachines.utils.MaterialCompat;

public class FastFurnace extends BasicFastMachine {

    public FastFurnace(ItemGroup itemGroup, SlimefunItemStack itemStack, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, itemStack, recipeType, recipe);
    }

    @Override
    public Material getCraftItemMaterial() {
        return MaterialCompat.safe(XMaterial.FURNACE);
    }

    // CookingRecipe + the RecipeChoice API are 1.13+; the class literal below is only resolved when the
    // guard passes, so on older servers this machine registers with no vanilla recipes rather than crashing.
    @Override
    public RecipeLoader getRecipeLoader() {
        if (CompatUtils.recipeChoiceApiSupported()) {
            return new VanillaRecipeLoader<>(this, CookingRecipe.class);
        }
        return new RecipeLoader(this) {
        };
    }
}
