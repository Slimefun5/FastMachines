package net.guizhanss.fastmachines.core.recipes.choices;

import java.util.Map;

import org.bukkit.inventory.ItemStack;

import net.guizhanss.fastmachines.core.items.ItemWrapper;

/**
 * A recipe ingredient.
 */
public interface RecipeChoice {

    /**
     * The choices, with the item {@link ItemWrapper} and its amount.
     */
    Map<ItemWrapper, Integer> getChoices();

    /**
     * Only checks if the item is valid, without checking the amount.
     */
    boolean isValidItem(ItemStack item);

    /**
     * Calculates the maximum craftable amount based on the given available items.
     */
    int maxCraftableAmount(Map<ItemWrapper, Integer> availableItems);
}
