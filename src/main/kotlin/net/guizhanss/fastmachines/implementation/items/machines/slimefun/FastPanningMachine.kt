package net.guizhanss.fastmachines.implementation.items.machines.slimefun

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial
import net.guizhanss.fastmachines.utils.MaterialCompat
import net.guizhanss.fastmachines.core.items.attributes.NotAHopper
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.core.recipes.loaders.SlimefunDisplayRecipeLoader
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class FastPanningMachine(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BasicFastMachine(itemGroup, itemStack, recipeType, recipe), NotAHopper {

    override val craftItemMaterial: Material
        get() = MaterialCompat.safe(XMaterial.BOWL)

    override val recipeLoader: RecipeLoader
        get() = SlimefunDisplayRecipeLoader(this, SlimefunItems.AUTOMATED_PANNING_MACHINE.itemId, true)
}
