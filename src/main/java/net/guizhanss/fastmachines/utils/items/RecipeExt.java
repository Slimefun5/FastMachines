package net.guizhanss.fastmachines.utils.items;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import net.guizhanss.fastmachines.core.items.ItemWrapper;
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice;
import net.guizhanss.fastmachines.core.recipes.choices.MultipleChoice;

/**
 * Converts a Bukkit {@link RecipeChoice} (1.13+ API) into this addon's own recipe choices.
 * Only ever reached when the RecipeChoice API is supported, so the 1.13+ types here are never
 * resolved on legacy servers.
 */
public final class RecipeExt {

    private RecipeExt() {
    }

    public static net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice asFMRecipeChoice(RecipeChoice choice) {
        return asFMRecipeChoice(choice, 1);
    }

    public static net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice asFMRecipeChoice(RecipeChoice choice, int amount) {
        if (choice instanceof RecipeChoice.MaterialChoice) {
            List<Material> choices = ((RecipeChoice.MaterialChoice) choice).getChoices();
            if (choices.size() == 1) {
                return new ExactChoice(ItemWrapper.of(choices.get(0)), amount);
            }
            Map<ItemWrapper, Integer> map = new LinkedHashMap<>();
            for (Material material : choices) {
                map.put(ItemWrapper.of(material), amount);
            }
            return new MultipleChoice(map);
        }

        if (choice instanceof RecipeChoice.ExactChoice) {
            List<ItemStack> choices = ((RecipeChoice.ExactChoice) choice).getChoices();
            if (choices.size() == 1) {
                return new ExactChoice(ItemWrapper.of(choices.get(0)), amount);
            }
            Map<ItemWrapper, Integer> map = new LinkedHashMap<>();
            for (ItemStack item : choices) {
                map.put(ItemWrapper.of(item), amount);
            }
            return new MultipleChoice(map);
        }

        throw new IllegalArgumentException("Unknown RecipeChoice type: " + choice);
    }
}
