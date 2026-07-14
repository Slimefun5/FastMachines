package net.guizhanss.fastmachines.implementation.items.machines.slimefun

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial
import net.guizhanss.fastmachines.utils.MaterialCompat
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.core.recipes.loaders.SlimefunMultiblockRecipeLoader
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class FastEnhancedCraftingTable(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BasicFastMachine(itemGroup, itemStack, recipeType, recipe) {

    override val craftItemMaterial: Material
        get() = MaterialCompat.safe(XMaterial.CRAFTING_TABLE)

    override val recipeLoader: RecipeLoader
        get() = SlimefunMultiblockRecipeLoader(this, SlimefunItems.ENHANCED_CRAFTING_TABLE.itemId)
}
