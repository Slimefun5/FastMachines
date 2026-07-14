package net.guizhanss.fastmachines.core.recipes;

import java.util.List;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice;

/**
 * A shapeless recipe.
 */
public interface Recipe {

    /**
     * The recipe inputs.
     */
    List<RecipeChoice> getInputs();

    /**
     * The recipe outputs.
     */
    List<ItemStack> getOutputs();

    /**
     * Gets the output {@link ItemStack} in the given {@link World}. Called when crafting the recipe.
     */
    ItemStack getOutput(World world);

    /**
     * Quickly checks if all the outputs are fully disabled in the given {@link World}.
     */
    boolean isDisabledIn(World world);
}
