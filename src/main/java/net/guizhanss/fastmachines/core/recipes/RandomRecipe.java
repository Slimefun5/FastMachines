package net.guizhanss.fastmachines.core.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice;
import net.guizhanss.fastmachines.utils.items.SlimefunItemExt;

/**
 * A random recipe accepts a single input and returns a random output from the list of outputs.
 */
public final class RandomRecipe implements Recipe {

    private final RecipeChoice input;
    private final List<ItemStack> outputs;
    private final List<RecipeChoice> inputs;

    public RandomRecipe(RecipeChoice input, List<ItemStack> outputs) {
        this.input = input;
        this.outputs = outputs;
        this.inputs = Collections.singletonList(input);
    }

    @Override
    public List<RecipeChoice> getInputs() {
        return inputs;
    }

    @Override
    public List<ItemStack> getOutputs() {
        return outputs;
    }

    @Override
    public ItemStack getOutput(World world) {
        List<ItemStack> enabled = new ArrayList<>();
        for (ItemStack output : outputs) {
            if (!SlimefunItemExt.isDisabledIn(output, world)) {
                enabled.add(output);
            }
        }
        return enabled.get(ThreadLocalRandom.current().nextInt(enabled.size()));
    }

    @Override
    public boolean isDisabledIn(World world) {
        for (ItemStack output : outputs) {
            if (!SlimefunItemExt.isDisabledIn(output, world)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RandomRecipe)) {
            return false;
        }
        RandomRecipe that = (RandomRecipe) o;
        return Objects.equals(input, that.input) && Objects.equals(outputs, that.outputs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, outputs);
    }

    @Override
    public String toString() {
        return "RandomRecipe(input=" + input + ", outputs=" + outputs + ")";
    }
}
