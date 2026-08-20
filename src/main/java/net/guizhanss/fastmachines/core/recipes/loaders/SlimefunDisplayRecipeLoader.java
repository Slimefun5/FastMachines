package net.guizhanss.fastmachines.core.recipes.loaders;

import java.util.Collections;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.core.attributes.RecipeDisplayItem;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.items.ItemWrapper;
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice;
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe;
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine;

/**
 * A {@link RecipeLoader} that loads recipes from display recipes.
 */
public class SlimefunDisplayRecipeLoader extends RecipeLoader {

    private final String id;

    public SlimefunDisplayRecipeLoader(BaseFastMachine machine, String id) {
        this(machine, id, false);
    }

    public SlimefunDisplayRecipeLoader(BaseFastMachine machine, String id, boolean enableRandomRecipes) {
        super(machine, enableRandomRecipes);
        this.id = id;
    }

    @Override
    public void beforeLoad() {
        SlimefunItem sfItem = SlimefunItem.getById(id);
        if (!(sfItem instanceof RecipeDisplayItem)) {
            throw new IllegalArgumentException("The item " + id + " is not RecipeDisplayItem.");
        }

        List<ItemStack> recipes;
        try {
            recipes = ((RecipeDisplayItem) sfItem).getDisplayRecipes();
        } catch (Throwable ex) {
            // Legacy servers can fail building display recipes (e.g. GoldPan on 1.8.8 uses a null Material); skip rather than crash.
            FastMachines.debug("Skipping display recipes for " + id + ": " + ex);
            return;
        }
        if (recipes.size() % 2 != 0) {
            throw new IllegalArgumentException("The item " + id + " has invalid display recipe list.");
        }

        for (int i = 0; i < recipes.size(); i += 2) {
            List<net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice> input =
                Collections.singletonList(new ExactChoice(ItemWrapper.of(recipes.get(i))));
            List<ItemStack> output = Collections.singletonList(recipes.get(i + 1));

            rawRecipes.add(new RawRecipe(input, output));
        }
    }
}
