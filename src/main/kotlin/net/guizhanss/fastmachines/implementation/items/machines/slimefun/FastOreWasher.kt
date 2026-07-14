package net.guizhanss.fastmachines.implementation.items.machines.slimefun

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial
import net.guizhanss.fastmachines.utils.MaterialCompat
import net.guizhanss.fastmachines.core.items.attributes.NotACauldron
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.core.recipes.loaders.SlimefunMultiblockRecipeLoader
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class FastOreWasher(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BasicFastMachine(itemGroup, itemStack, recipeType, recipe), NotACauldron {

    override val craftItemMaterial: Material
        get() = MaterialCompat.safe(XMaterial.CAULDRON)

    override val recipeLoader: RecipeLoader
        get() = SlimefunMultiblockRecipeLoader(this, SlimefunItems.ORE_WASHER.itemId, true)
}
