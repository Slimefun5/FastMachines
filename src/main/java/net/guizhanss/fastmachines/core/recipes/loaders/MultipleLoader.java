package net.guizhanss.fastmachines.core.recipes.loaders;

import java.util.Arrays;
import java.util.List;

import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine;

/**
 * A {@link RecipeLoader} that loads recipes from multiple {@link RecipeLoader}s.
 */
public class MultipleLoader extends RecipeLoader {

    private final List<RecipeLoader> loaders;

    public MultipleLoader(BaseFastMachine machine, RecipeLoader... loaders) {
        super(machine);
        this.loaders = Arrays.asList(loaders);
    }

    @Override
    public void load() {
        for (RecipeLoader loader : loaders) {
            loader.load();
        }
    }
}
