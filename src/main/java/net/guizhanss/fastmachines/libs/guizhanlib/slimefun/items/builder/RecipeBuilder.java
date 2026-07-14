package net.guizhanss.fastmachines.libs.guizhanlib.slimefun.items.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

import net.guizhanss.fastmachines.utils.MaterialCompat;

/**
 * A small builder for describing a 3x3 shaped recipe as three 3-character rows ({@link #row}) plus a
 * mapping from each character to an ingredient ({@link #where}). A blank space always means
 * "no ingredient".
 *
 * Java-8-safe port of GuizhanLib-kt's {@code buildRecipe} DSL.
 */
public class RecipeBuilder {

    private final List<String> rows = new ArrayList<>();
    private final Map<Character, ItemStack> ingredients = new HashMap<>();

    public static RecipeBuilder create() {
        return new RecipeBuilder();
    }

    public RecipeBuilder row(String row) {
        if (row.length() != 3) {
            throw new IllegalArgumentException("Each recipe row must be exactly 3 characters long, was \"" + row + "\"");
        }
        rows.add(row);
        return this;
    }

    /** Version-safe ingredient: the {@link XMaterial} is resolved to a {@link Material} that exists on this server. */
    public RecipeBuilder where(char c, XMaterial material) {
        ingredients.put(c, material == null ? null : new ItemStack(MaterialCompat.safe(material)));
        return this;
    }

    public RecipeBuilder where(char c, Material material) {
        ingredients.put(c, material == null ? null : new ItemStack(material));
        return this;
    }

    public RecipeBuilder where(char c, SlimefunItemStack item) {
        ingredients.put(c, item == null ? null : item.item());
        return this;
    }

    public RecipeBuilder where(char c, ItemStack item) {
        ingredients.put(c, item);
        return this;
    }

    public ItemStack[] build() {
        if (rows.size() != 3) {
            throw new IllegalArgumentException("A recipe must have exactly 3 rows, had " + rows.size());
        }
        String shape = rows.get(0) + rows.get(1) + rows.get(2);
        ItemStack[] recipe = new ItemStack[9];
        for (int i = 0; i < 9; i++) {
            recipe[i] = ingredients.get(shape.charAt(i));
        }
        return recipe;
    }
}
