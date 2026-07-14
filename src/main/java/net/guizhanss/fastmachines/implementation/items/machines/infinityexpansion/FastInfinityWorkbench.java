package net.guizhanss.fastmachines.implementation.items.machines.infinityexpansion;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.items.blocks.InfinityWorkbench;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.loaders.InfinityExpansionRecipeLoader;
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader;
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine;
import net.guizhanss.fastmachines.utils.MaterialCompat;

public class FastInfinityWorkbench extends BaseFastMachine {

    public FastInfinityWorkbench(ItemGroup itemGroup, SlimefunItemStack itemStack, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, itemStack, recipeType, recipe, 100_000_000, 10_000_000);
    }

    @Override
    public Material getCraftItemMaterial() {
        return MaterialCompat.safe(XMaterial.RESPAWN_ANCHOR);
    }

    @Override
    public RecipeLoader getRecipeLoader() {
        return new InfinityExpansionRecipeLoader(this, InfinityWorkbench.class);
    }

    @Override
    public boolean registerPrecondition() {
        return FastMachines.getIntegrationService().isInfinityExpansionEnabled();
    }
}
