package net.guizhanss.fastmachines.core.recipes.choices;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.bukkit.inventory.ItemStack;

import net.guizhanss.fastmachines.core.items.ItemWrapper;
import net.guizhanss.fastmachines.utils.items.ItemExt;

/**
 * The {@link RecipeChoice} that matches multiple items.
 */
public final class MultipleChoice implements RecipeChoice {

    private final Map<ItemWrapper, Integer> choices;

    public MultipleChoice(Map<ItemWrapper, Integer> choices) {
        this.choices = choices;
    }

    @Override
    public Map<ItemWrapper, Integer> getChoices() {
        return choices;
    }

    @Override
    public boolean isValidItem(ItemStack item) {
        for (ItemWrapper wrapper : choices.keySet()) {
            if (ItemExt.isSimilarTo(wrapper, item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int maxCraftableAmount(Map<ItemWrapper, Integer> availableItems) {
        Map<ItemWrapper, Integer> items = new HashMap<>(availableItems);
        int totalCrafts = 0;

        while (true) {
            boolean matched = false;

            for (Map.Entry<ItemWrapper, Integer> entry : choices.entrySet()) {
                ItemWrapper item = entry.getKey();
                int amount = entry.getValue();
                int availableAmount = items.getOrDefault(item, 0);
                if (availableAmount >= amount) {
                    items.put(item, availableAmount - amount);
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                break;
            }

            totalCrafts++;
        }

        return totalCrafts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MultipleChoice)) {
            return false;
        }
        return Objects.equals(choices, ((MultipleChoice) o).choices);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(choices);
    }

    @Override
    public String toString() {
        return "MultipleChoice(choices=" + choices + ")";
    }
}
