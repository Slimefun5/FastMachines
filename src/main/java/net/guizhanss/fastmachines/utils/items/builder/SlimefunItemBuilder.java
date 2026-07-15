package net.guizhanss.fastmachines.utils.items.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;

import net.guizhanss.fastmachines.FastMachines;

/**
 * A small builder for constructing a {@link SlimefunItem}, adapted for FastMachines.
 *
 * Replaces the upstream Kotlin property-delegate DSL: the item is constructed directly through a
 * typed {@link ItemFactory} rather than reflectively, so no {@code kotlin-reflect} is involved.
 */
public class SlimefunItemBuilder {

    /** Constructs a {@link SlimefunItem} from the four standard Slimefun constructor arguments. */
    public interface ItemFactory<T extends SlimefunItem> {
        T create(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe);
    }

    private final String prefix;
    private String id;
    private Material material;
    private int amount = 1;
    private ItemGroup itemGroup;
    private RecipeType recipeType;
    private ItemStack[] recipe = new ItemStack[9];

    public SlimefunItemBuilder(String prefix) {
        this.prefix = prefix;
    }

    public SlimefunItemBuilder id(String id) {
        this.id = id.toUpperCase();
        return this;
    }

    public SlimefunItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public SlimefunItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public SlimefunItemBuilder itemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }

    public SlimefunItemBuilder recipeType(RecipeType recipeType) {
        this.recipeType = recipeType;
        return this;
    }

    public SlimefunItemBuilder recipe(ItemStack[] recipe) {
        this.recipe = recipe;
        return this;
    }

    public <T extends SlimefunItem> SlimefunItemStack build(ItemFactory<T> factory) {
        // Name and lore are supplied per-language by the core ItemTranslationService from
        // languages/<lang>/items.yml, so the item is constructed name-less (2-arg constructor).
        SlimefunItemStack sfis = new SlimefunItemStack(prefix + id, material);
        sfis.setAmount(amount);

        T item = factory.create(itemGroup, sfis, recipeType, recipe);
        item.register(FastMachines.getAddon());
        return sfis;
    }
}
