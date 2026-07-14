package net.guizhanss.fastmachines.implementation.items.machines.vanilla

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.core.recipes.loaders.VanillaRecipeLoader
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine
import net.guizhanss.fastmachines.utils.CompatUtils
import net.guizhanss.fastmachines.utils.MaterialCompat
import org.bukkit.Material
import org.bukkit.inventory.CookingRecipe
import org.bukkit.inventory.ItemStack

class FastFurnace(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BasicFastMachine(itemGroup, itemStack, recipeType, recipe) {

    override val craftItemMaterial: Material
        get() = MaterialCompat.safe(XMaterial.FURNACE)

    // CookingRecipe + the RecipeChoice API are 1.13+; the class literal below is only resolved when the
    // guard passes, so on older servers this machine registers with no vanilla recipes rather than crashing.
    override val recipeLoader: RecipeLoader
        get() = if (CompatUtils.recipeChoiceApiSupported()) {
            VanillaRecipeLoader(this, CookingRecipe::class.java)
        } else {
            object : RecipeLoader(this) {}
        }
}
