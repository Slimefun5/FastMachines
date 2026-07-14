package net.guizhanss.fastmachines.core.recipes.loaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.core.multiblocks.MultiBlockMachine;

import net.guizhanss.fastmachines.core.items.ItemWrapper;
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice;
import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice;
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe;
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine;
import net.guizhanss.fastmachines.utils.items.ItemExt;

/**
 * A {@link RecipeLoader} that loads recipes from a {@link MultiBlockMachine}.
 */
public class SlimefunMultiblockRecipeLoader extends RecipeLoader {

    private final String id;

    public SlimefunMultiblockRecipeLoader(BaseFastMachine machine, String id) {
        this(machine, id, false);
    }

    public SlimefunMultiblockRecipeLoader(BaseFastMachine machine, String id, boolean enableRandomRecipes) {
        super(machine, enableRandomRecipes);
        this.id = id;
    }

    @Override
    public void beforeLoad() {
        SlimefunItem sfItem = SlimefunItem.getById(id);
        if (!(sfItem instanceof MultiBlockMachine)) {
            throw new IllegalArgumentException("The item " + id + " is not MultiBlockMachine.");
        }

        List<ItemStack[]> recipes = ((MultiBlockMachine) sfItem).getRecipes();
        if (recipes.size() % 2 != 0) {
            throw new IllegalArgumentException("The multiblock machine " + id + " has invalid recipe list.");
        }

        for (int i = 0; i < recipes.size(); i += 2) {
            List<ItemStack> nonNullInputs = new ArrayList<>();
            for (ItemStack item : recipes.get(i)) {
                if (item != null) {
                    nonNullInputs.add(item);
                }
            }

            List<RecipeChoice> input = new ArrayList<>();
            for (Map.Entry<ItemWrapper, Integer> entry : ItemExt.countItems(nonNullInputs).entrySet()) {
                input.add(new ExactChoice(entry.getKey(), entry.getValue()));
            }

            List<ItemStack> output = new ArrayList<>(Arrays.asList(recipes.get(i + 1)));

            rawRecipes.add(new RawRecipe(input, output));
        }
    }
}
