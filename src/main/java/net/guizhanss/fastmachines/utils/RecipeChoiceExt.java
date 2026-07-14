package net.guizhanss.fastmachines.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.guizhanss.fastmachines.core.items.ItemWrapper;
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice;
import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice;

public final class RecipeChoiceExt {

    private RecipeChoiceExt() {
    }

    /**
     * Consolidates a list of {@link RecipeChoice} by merging {@link ExactChoice}s with the same item.
     * Other types of {@link RecipeChoice} remain unchanged.
     */
    public static List<RecipeChoice> consolidate(List<RecipeChoice> choices) {
        List<ExactChoice> exactChoices = new ArrayList<>();
        List<RecipeChoice> otherChoices = new ArrayList<>();

        for (RecipeChoice choice : choices) {
            if (choice instanceof ExactChoice) {
                exactChoices.add((ExactChoice) choice);
            } else {
                otherChoices.add(choice);
            }
        }

        // group by item, preserving encounter order
        Map<ItemWrapper, List<ExactChoice>> grouped = new LinkedHashMap<>();
        for (ExactChoice choice : exactChoices) {
            grouped.computeIfAbsent(choice.getItem(), k -> new ArrayList<>()).add(choice);
        }

        List<RecipeChoice> result = new ArrayList<>();
        for (Map.Entry<ItemWrapper, List<ExactChoice>> entry : grouped.entrySet()) {
            ItemWrapper item = entry.getKey();
            int totalAmount = 0;
            for (ExactChoice choice : entry.getValue()) {
                totalAmount += choice.getAmount();
            }
            int maxStackSize = item.getBaseItem().getMaxStackSize();

            if (totalAmount <= maxStackSize) {
                result.add(new ExactChoice(item, totalAmount));
            } else {
                int fullStacks = totalAmount / maxStackSize;
                int remainder = totalAmount % maxStackSize;
                for (int i = 0; i < fullStacks; i++) {
                    result.add(new ExactChoice(item, maxStackSize));
                }
                if (remainder > 0) {
                    result.add(new ExactChoice(item, remainder));
                }
            }
        }

        result.addAll(otherChoices);
        return result;
    }
}
