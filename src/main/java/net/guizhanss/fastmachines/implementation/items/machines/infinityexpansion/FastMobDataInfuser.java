package net.guizhanss.fastmachines.implementation.items.machines.infinityexpansion;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.items.mobdata.MobDataInfuser;
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.loaders.InfinityExpansionRecipeLoader;
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader;
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine;
import net.guizhanss.fastmachines.utils.MaterialCompat;

public class FastMobDataInfuser extends BaseFastMachine {

    public FastMobDataInfuser(ItemGroup itemGroup, SlimefunItemStack itemStack, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, itemStack, recipeType, recipe, 200_000, 20_000);
    }

    @Override
    public Material getCraftItemMaterial() {
        return MaterialCompat.safe(XMaterial.LODESTONE);
    }

    @Override
    public RecipeLoader getRecipeLoader() {
        return new InfinityExpansionRecipeLoader(this, MobDataInfuser.class);
    }

    @Override
    public boolean registerPrecondition() {
        return FastMachines.getIntegrationService().isInfinityExpansionEnabled();
    }
}
