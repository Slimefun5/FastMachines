package net.guizhanss.fastmachines.core.recipes.choices;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.bukkit.inventory.ItemStack;

import net.guizhanss.fastmachines.core.items.ItemWrapper;
import net.guizhanss.fastmachines.utils.items.ItemExt;

/**
 * The {@link RecipeChoice} that matches exactly one item.
 */
public final class ExactChoice implements RecipeChoice {

    private final ItemWrapper item;
    private final int amount;
    private final Map<ItemWrapper, Integer> choices;

    public ExactChoice(ItemWrapper item) {
        this(item, 1);
    }

    public ExactChoice(ItemWrapper item, int amount) {
        this.item = item;
        this.amount = amount;
        this.choices = Collections.singletonMap(item, amount);
    }

    public ItemWrapper getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public Map<ItemWrapper, Integer> getChoices() {
        return choices;
    }

    @Override
    public boolean isValidItem(ItemStack item) {
        return ItemExt.isSimilarTo(this.item, item);
    }

    @Override
    public int maxCraftableAmount(Map<ItemWrapper, Integer> availableItems) {
        int available = availableItems.getOrDefault(item, 0);
        return available / amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExactChoice)) {
            return false;
        }
        ExactChoice that = (ExactChoice) o;
        return amount == that.amount && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, amount);
    }

    @Override
    public String toString() {
        return "ExactChoice(item=" + item + ", amount=" + amount + ")";
    }
}
