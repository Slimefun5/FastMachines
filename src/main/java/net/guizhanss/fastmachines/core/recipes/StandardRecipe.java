package net.guizhanss.fastmachines.core.recipes;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice;
import net.guizhanss.fastmachines.utils.items.SlimefunItemExt;

/**
 * The standard recipe that has fixed inputs and outputs.
 */
public final class StandardRecipe implements Recipe {

    private final List<RecipeChoice> inputs;
    private final ItemStack output;
    private final List<ItemStack> outputs;

    public StandardRecipe(List<RecipeChoice> inputs, ItemStack output) {
        this.inputs = inputs;
        this.output = output;
        this.outputs = Collections.singletonList(output);
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
        return output;
    }

    @Override
    public boolean isDisabledIn(World world) {
        return SlimefunItemExt.isDisabledIn(output, world);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StandardRecipe)) {
            return false;
        }
        StandardRecipe that = (StandardRecipe) o;
        return Objects.equals(inputs, that.inputs) && Objects.equals(output, that.output);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputs, output);
    }

    @Override
    public String toString() {
        return "StandardRecipe(inputs=" + inputs + ", output=" + output + ")";
    }
}
