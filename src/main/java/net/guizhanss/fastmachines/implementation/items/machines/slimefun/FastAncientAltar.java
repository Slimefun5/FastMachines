package net.guizhanss.fastmachines.implementation.items.machines.slimefun;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun5.implementation.items.altar.AncientAltar;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

import net.guizhanss.fastmachines.core.items.ItemWrapper;
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice;
import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice;
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader;
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe;
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine;
import net.guizhanss.fastmachines.utils.MaterialCompat;
import net.guizhanss.fastmachines.utils.items.ItemExt;

public class FastAncientAltar extends BasicFastMachine {

    public FastAncientAltar(ItemGroup itemGroup, SlimefunItemStack itemStack, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, itemStack, recipeType, recipe);
    }

    @Override
    public Material getCraftItemMaterial() {
        return MaterialCompat.safe(XMaterial.ENCHANTING_TABLE);
    }

    @Override
    public RecipeLoader getRecipeLoader() {
        return new RecipeLoader(this) {
            @Override
            public void beforeLoad() {
                SlimefunItem sfItem = SlimefunItem.getById(SlimefunItems.ANCIENT_ALTAR.getItemId());
                if (!(sfItem instanceof AncientAltar)) {
                    return;
                }
                AncientAltar altar = (AncientAltar) sfItem;

                for (io.github.thebusybiscuit.slimefun5.implementation.items.altar.AltarRecipe recipe : altar.getRecipes()) {
                    // explicitly ignore spawner recipes
                    if (recipe.getOutput().getType() == MaterialCompat.safe(XMaterial.SPAWNER)) {
                        continue;
                    }

                    List<ItemStack> input = new ArrayList<>(recipe.getInput());
                    input.add(recipe.getCatalyst());

                    List<RecipeChoice> choices = new ArrayList<>();
                    for (Map.Entry<ItemWrapper, Integer> entry : ItemExt.countItems(input).entrySet()) {
                        choices.add(new ExactChoice(entry.getKey(), entry.getValue()));
                    }

                    rawRecipes.add(new RawRecipe(choices, java.util.Collections.singletonList(recipe.getOutput())));
                }
            }
        };
    }
}
