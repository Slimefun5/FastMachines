package net.guizhanss.fastmachines.core.recipes.loaders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.inventory.ItemStack;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.items.ItemWrapper;
import net.guizhanss.fastmachines.core.recipes.RandomRecipe;
import net.guizhanss.fastmachines.core.recipes.StandardRecipe;
import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice;
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe;
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine;
import net.guizhanss.fastmachines.utils.items.SlimefunItemExt;

/**
 * A {@link RecipeLoader} is responsible for loading recipes for a specific {@link BaseFastMachine}.
 */
public abstract class RecipeLoader {

    protected final BaseFastMachine machine;
    protected final boolean enableRandomRecipes;
    protected final List<RawRecipe> rawRecipes = new ArrayList<>();

    protected RecipeLoader(BaseFastMachine machine) {
        this(machine, false);
    }

    protected RecipeLoader(BaseFastMachine machine, boolean enableRandomRecipes) {
        this.machine = machine;
        this.enableRandomRecipes = enableRandomRecipes;
    }

    /**
     * Implementing classes should override this method to add recipes to {@code rawRecipes}.
     */
    public void beforeLoad() {
        // there is nothing here in the default loader
    }

    public void load() {
        FastMachines.debug("Pre-processing recipes for " + machine.getId() + "...");
        beforeLoad();
        FastMachines.debug("Loading recipes for " + machine.getId() + "...");
        if (enableRandomRecipes) {
            loadWithRandomRecipes();
        } else {
            loadRecipes();
        }
    }

    private void loadWithRandomRecipes() {
        sortRecipes();

        Map<String, List<RawRecipe>> groupedRecipes = new LinkedHashMap<>();
        for (RawRecipe rawRecipe : rawRecipes) {
            groupedRecipes.computeIfAbsent(inputKey(rawRecipe), k -> new ArrayList<>()).add(rawRecipe);
        }

        for (Map.Entry<String, List<RawRecipe>> entry : groupedRecipes.entrySet()) {
            try {
                FastMachines.debug("===============");
                FastMachines.debug("Processing recipes with input key: " + entry.getKey());

                RecipeChoice input = entry.getValue().get(0).getInputs().get(0);
                List<ItemStack> outputs = new ArrayList<>();
                for (RawRecipe recipe : entry.getValue()) {
                    for (ItemStack output : recipe.getOutput()) {
                        if (!SlimefunItemExt.isDisabled(output)) {
                            outputs.add(output);
                        }
                    }
                }

                // all disabled, no recipe
                if (outputs.isEmpty()) {
                    continue;
                }

                FastMachines.debug("  - Input: " + input);
                FastMachines.debug("  - Outputs: " + outputs);

                net.guizhanss.fastmachines.core.recipes.Recipe recipe;
                if (outputs.size() > 1) {
                    recipe = new RandomRecipe(input, outputs);
                } else {
                    recipe = new StandardRecipe(java.util.Collections.singletonList(input), outputs.get(0));
                }
                FastMachines.debug("  - Created recipe: " + recipe);
                machine.addRecipe(recipe);
            } catch (Exception e) {
                FastMachines.log(Level.SEVERE, e,
                    "An unexpected error has occurred while loading grouped recipes."
                        + "Please enable debug mode first and restart the server,"
                        + "report this issue with FULL debug log only.");
            }
        }
    }

    private void loadRecipes() {
        sortRecipes();

        for (int index = 0; index < rawRecipes.size(); index++) {
            RawRecipe rawRecipe = rawRecipes.get(index);
            try {
                FastMachines.debug("===============");
                FastMachines.debug("Processing raw recipe (" + (index + 1) + "/" + rawRecipes.size() + "): " + rawRecipe);

                if (rawRecipe.getOutput().size() > 1) {
                    FastMachines.debug("  - Unexpected multiple outputs, skipping");
                    continue;
                }

                ItemStack outputItem = rawRecipe.getOutput().get(0);

                // no need to load recipe if the output item is disabled
                if (SlimefunItemExt.isDisabled(outputItem)) {
                    FastMachines.debug("  - Output item is a disabled Slimefun item, skipping");
                    continue;
                }

                StandardRecipe recipe = new StandardRecipe(rawRecipe.getInputs(), outputItem);
                FastMachines.debug("  - Created recipe: " + recipe);
                machine.addRecipe(recipe);
            } catch (Exception e) {
                FastMachines.log(Level.SEVERE, e,
                    "An unexpected error has occurred while loading recipes."
                        + "Please enable debug mode first and restart the server,"
                        + "report this issue with FULL debug log only.");
            }
        }
    }

    private void sortRecipes() {
        rawRecipes.sort(Comparator.comparing(this::inputKey));
        FastMachines.debug("Sorted raw recipes:");
        for (int index = 0; index < rawRecipes.size(); index++) {
            FastMachines.debug("  (" + (index + 1) + "/" + rawRecipes.size() + "): " + rawRecipes.get(index));
        }
    }

    private String inputKey(RawRecipe rawRecipe) {
        List<String> sortedInputChoices = new ArrayList<>();
        for (RecipeChoice choice : rawRecipe.getInputs()) {
            List<Map.Entry<ItemWrapper, Integer>> entries = new ArrayList<>(choice.getChoices().entrySet());
            entries.sort(Comparator
                .comparing((Map.Entry<ItemWrapper, Integer> e) -> e.getKey())
                .thenComparing(Map.Entry::getValue));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < entries.size(); i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(entries.get(i).getKey()).append("x").append(entries.get(i).getValue());
            }
            sortedInputChoices.add(sb.toString());
        }
        sortedInputChoices.sort(Comparator.naturalOrder());
        return String.join("||", sortedInputChoices);
    }
}
