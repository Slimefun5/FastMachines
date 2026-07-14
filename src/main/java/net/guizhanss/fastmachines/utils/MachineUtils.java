package net.guizhanss.fastmachines.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.items.ItemWrapper;
import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice;
import net.guizhanss.fastmachines.utils.items.ItemExt;

public final class MachineUtils {

    private MachineUtils() {
    }

    /** Gets all the {@link ItemStack}s in the given slots. */
    public static List<ItemStack> getItems(BlockMenu menu, int... slots) {
        List<ItemStack> result = new ArrayList<>();
        for (int slot : slots) {
            ItemStack item = menu.getItemInSlot(slot);
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }

    /** Counts the number of each item in the given slots. */
    public static Map<ItemWrapper, Integer> countItems(BlockMenu menu, int... slots) {
        return ItemExt.countItems(getItems(menu, slots));
    }

    /** Consumes the given amount of items based on the {@link RecipeChoice}. */
    public static void consumeChoice(BlockMenu menu, RecipeChoice choice, int amount, int... slots) {
        FastMachines.debug("Consuming choice " + choice + " with amount of " + amount);
        int remainingAmount = amount;

        // Iterate over each possible choice item until the requirement is fully met
        choiceLoop:
        for (Map.Entry<ItemWrapper, Integer> entry : choice.getChoices().entrySet()) {
            ItemWrapper choiceItem = entry.getKey();
            int choiceAmount = entry.getValue();
            int totalRequiredAmount = choiceAmount * remainingAmount;

            for (int slot : slots) {
                if (totalRequiredAmount <= 0) {
                    continue choiceLoop;
                }

                ItemStack itemInSlot = menu.getItemInSlot(slot);
                if (itemInSlot == null) {
                    continue;
                }
                if (!ItemExt.isSimilarTo(choiceItem, itemInSlot)) {
                    continue;
                }

                int availableAmount = itemInSlot.getAmount();
                int consumeNow = Math.min(availableAmount, totalRequiredAmount);

                menu.consumeItem(slot, consumeNow);
                totalRequiredAmount -= consumeNow;
            }

            // Update remaining amount of recipes to fulfill
            remainingAmount = totalRequiredAmount / choiceAmount;
        }
    }
}
