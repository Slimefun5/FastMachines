package net.guizhanss.fastmachines.implementation.items.machines.slimefun

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems
import io.github.thebusybiscuit.slimefun5.implementation.items.altar.AncientAltar
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial
import net.guizhanss.fastmachines.utils.MaterialCompat
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine
import net.guizhanss.fastmachines.utils.items.countItems
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class FastAncientAltar(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BasicFastMachine(itemGroup, itemStack, recipeType, recipe) {

    override val craftItemMaterial: Material
        get() = MaterialCompat.safe(XMaterial.ENCHANTING_TABLE)

    override val recipeLoader: RecipeLoader
        get() = object : RecipeLoader(this) {
            override fun beforeLoad() {
                val altar = SlimefunItem.getById(SlimefunItems.ANCIENT_ALTAR.itemId) as? AncientAltar ?: return

                for (recipe in altar.recipes) {
                    // explicitly ignore spawner recipes
                    if (recipe.output.type == MaterialCompat.safe(XMaterial.SPAWNER)) continue

                    val input = recipe.input.toMutableList()
                    input.add(recipe.catalyst)
                    val rawRecipe = RawRecipe(
                        input.countItems().map { (item, amount) -> ExactChoice(item, amount) },
                        listOf(recipe.output)
                    )
                    rawRecipes.add(rawRecipe)
                }
            }
        }
}
