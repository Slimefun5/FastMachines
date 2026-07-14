package net.guizhanss.fastmachines.core.recipes.loaders;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.infinitylib.machines.MachineRecipeType;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.items.ItemWrapper;
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice;
import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice;
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe;
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine;
import net.guizhanss.fastmachines.utils.items.ItemExt;

/**
 * A {@link RecipeLoader} that loads recipes from an InfinityExpansion item.
 * The item must have a static {@code MachineRecipeType} field {@code TYPE}.
 */
public class InfinityExpansionRecipeLoader extends RecipeLoader {

    private final Class<? extends SlimefunItem> clazz;

    public InfinityExpansionRecipeLoader(BaseFastMachine machine, Class<? extends SlimefunItem> clazz) {
        this(machine, clazz, false);
    }

    public InfinityExpansionRecipeLoader(BaseFastMachine machine, Class<? extends SlimefunItem> clazz, boolean enableRandomRecipes) {
        super(machine, enableRandomRecipes);
        this.clazz = clazz;
    }

    @Override
    public void beforeLoad() {
        MachineRecipeType type;
        try {
            Field field = clazz.getDeclaredField("TYPE");
            field.setAccessible(true);
            type = (MachineRecipeType) field.get(null);
        } catch (Exception e) {
            FastMachines.log(Level.SEVERE, e, "An error occurred while loading InfinityExpansion recipes.");
            return;
        }

        for (Map.Entry<ItemStack[], ItemStack> entry : type.recipes().entrySet()) {
            List<RecipeChoice> input = new ArrayList<>();
            for (Map.Entry<ItemWrapper, Integer> counted : ItemExt.countItems(Arrays.asList(entry.getKey())).entrySet()) {
                input.add(new ExactChoice(counted.getKey(), counted.getValue()));
            }
            rawRecipes.add(new RawRecipe(input, java.util.Collections.singletonList(entry.getValue())));
        }
    }
}
