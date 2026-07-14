package net.guizhanss.fastmachines.implementation.items.machines.vanilla

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial
import net.guizhanss.fastmachines.core.recipes.loaders.MultipleLoader
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.core.recipes.loaders.VanillaRecipeLoader
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine
import net.guizhanss.fastmachines.utils.CompatUtils
import net.guizhanss.fastmachines.utils.MaterialCompat
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

class FastCraftingTable(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BasicFastMachine(itemGroup, itemStack, recipeType, recipe) {

    override val craftItemMaterial: Material
        get() = MaterialCompat.safe(XMaterial.CRAFTING_TABLE)

    // VanillaRecipeLoader uses the RecipeChoice API (ShapedRecipe.getChoiceMap etc.), which is 1.13+;
    // on older servers this machine registers with no vanilla recipes rather than crashing.
    override val recipeLoader: RecipeLoader
        get() = if (CompatUtils.recipeChoiceApiSupported()) {
            MultipleLoader(
                this,
                VanillaRecipeLoader(this, ShapedRecipe::class.java),
                VanillaRecipeLoader(this, ShapelessRecipe::class.java),
            )
        } else {
            object : RecipeLoader(this) {}
        }
}
