package net.guizhanss.fastmachines.implementation.items.machines.vanilla;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

import net.guizhanss.fastmachines.core.recipes.loaders.MultipleLoader;
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader;
import net.guizhanss.fastmachines.core.recipes.loaders.VanillaRecipeLoader;
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine;
import net.guizhanss.fastmachines.utils.CompatUtils;
import net.guizhanss.fastmachines.utils.MaterialCompat;

public class FastCraftingTable extends BasicFastMachine {

    public FastCraftingTable(ItemGroup itemGroup, SlimefunItemStack itemStack, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, itemStack, recipeType, recipe);
    }

    @Override
    public Material getCraftItemMaterial() {
        return MaterialCompat.safe(XMaterial.CRAFTING_TABLE);
    }

    // VanillaRecipeLoader uses the RecipeChoice API (ShapedRecipe.getChoiceMap etc.), which is 1.13+;
    // on older servers this machine registers with no vanilla recipes rather than crashing.
    @Override
    public RecipeLoader getRecipeLoader() {
        if (CompatUtils.recipeChoiceApiSupported()) {
            return new MultipleLoader(
                this,
                new VanillaRecipeLoader<>(this, ShapedRecipe.class),
                new VanillaRecipeLoader<>(this, ShapelessRecipe.class)
            );
        }
        return new RecipeLoader(this) {
        };
    }
}
