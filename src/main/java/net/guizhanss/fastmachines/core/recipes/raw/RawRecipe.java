package net.guizhanss.fastmachines.core.recipes.raw;

import java.util.List;
import java.util.Objects;

import org.bukkit.inventory.ItemStack;

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice;

public final class RawRecipe {

    private final List<RecipeChoice> inputs;
    private final List<ItemStack> output;

    public RawRecipe(List<RecipeChoice> inputs, List<ItemStack> output) {
        this.inputs = inputs;
        this.output = output;
    }

    public List<RecipeChoice> getInputs() {
        return inputs;
    }

    public List<ItemStack> getOutput() {
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawRecipe)) {
            return false;
        }
        RawRecipe that = (RawRecipe) o;
        return Objects.equals(inputs, that.inputs) && Objects.equals(output, that.output);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputs, output);
    }

    @Override
    public String toString() {
        return "RawRecipe(inputs=" + inputs + ", output=" + output + ")";
    }
}
