package net.guizhanss.fastmachines.utils.reflections;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import net.guizhanss.fastmachines.libs.guizhanlib.reflect.Reflect;

/**
 * Backward compatibility: shaped and shapeless recipes extend crafting recipe in 1.20+, so their
 * {@code getResult()} is reached reflectively rather than through a hard type reference.
 */
public final class RecipeReflect {

    private RecipeReflect() {
    }

    public static ItemStack resultItem(Recipe recipe) {
        ItemStack result = Reflect.invoke(recipe, "getResult");
        if (result == null) {
            throw new IllegalStateException("Unexpected result item from bukkit recipe.");
        }
        return result;
    }
}
