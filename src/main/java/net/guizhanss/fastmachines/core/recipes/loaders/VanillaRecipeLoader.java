package net.guizhanss.fastmachines.core.recipes.loaders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe;
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine;
import net.guizhanss.fastmachines.utils.RecipeChoiceExt;
import net.guizhanss.fastmachines.utils.items.RecipeExt;
import net.guizhanss.fastmachines.utils.reflections.RecipeReflect;

/**
 * A {@link RecipeLoader} that loads recipes from the vanilla registry.
 * <p>
 * The vanilla {@link RecipeChoice} API is 1.13+, so this loader (and the whole class) is only ever
 * referenced from callers gated behind the version check; on legacy servers it is never loaded.
 */
public class VanillaRecipeLoader<T extends Recipe> extends RecipeLoader {

    private final Class<T> recipeClass;

    public VanillaRecipeLoader(BaseFastMachine machine, Class<T> recipeClass) {
        this(machine, recipeClass, false);
    }

    public VanillaRecipeLoader(BaseFastMachine machine, Class<T> recipeClass, boolean enableRandomRecipes) {
        super(machine, enableRandomRecipes);
        this.recipeClass = recipeClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void beforeLoad() {
        Iterator<Recipe> iterator = Bukkit.getServer().recipeIterator();
        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if (recipeClass.isInstance(recipe)) {
                registerRecipe((T) recipe);
            }
        }
    }

    private void registerRecipe(T recipe) {
        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shaped = (ShapedRecipe) recipe;
            // each character in the shape may map to a different choice,
            // so count the amount of each bukkit recipe choice
            Map<RecipeChoice, Integer> ingredientMap = new LinkedHashMap<>();
            StringBuilder shapeBuilder = new StringBuilder();
            for (String row : shaped.getShape()) {
                shapeBuilder.append(row);
            }
            for (char ingredientChar : shapeBuilder.toString().toCharArray()) {
                if (Character.isWhitespace(ingredientChar)) {
                    continue;
                }
                RecipeChoice choice = shaped.getChoiceMap().get(ingredientChar);
                if (choice == null) {
                    continue;
                }
                ingredientMap.merge(choice, 1, Integer::sum);
            }

            List<net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice> ingredients = new ArrayList<>();
            for (Map.Entry<RecipeChoice, Integer> entry : ingredientMap.entrySet()) {
                ingredients.add(RecipeExt.asFMRecipeChoice(entry.getKey(), entry.getValue()));
            }

            rawRecipes.add(new RawRecipe(ingredients, java.util.Collections.singletonList(RecipeReflect.resultItem(recipe))));
        } else if (recipe instanceof ShapelessRecipe) {
            ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
            List<net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice> ingredients = new ArrayList<>();
            for (RecipeChoice choice : shapeless.getChoiceList()) {
                ingredients.add(RecipeExt.asFMRecipeChoice(choice));
            }
            rawRecipes.add(new RawRecipe(RecipeChoiceExt.consolidate(ingredients),
                java.util.Collections.singletonList(RecipeReflect.resultItem(recipe))));
        } else if (recipe instanceof CookingRecipe) {
            CookingRecipe<?> cooking = (CookingRecipe<?>) recipe;
            rawRecipes.add(new RawRecipe(
                java.util.Collections.singletonList(RecipeExt.asFMRecipeChoice(cooking.getInputChoice())),
                java.util.Collections.singletonList(cooking.getResult())));
        }
    }
}
