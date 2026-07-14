package net.guizhanss.fastmachines.implementation.items.materials

import io.github.thebusybiscuit.slimefun5.api.events.PlayerRightClickEvent
import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun5.core.handlers.ItemUseHandler
import io.github.thebusybiscuit.slimefun5.core.services.sounds.SoundEffect
import io.github.thebusybiscuit.slimefun5.implementation.SlimefunItems
import io.github.thebusybiscuit.slimefun5.implementation.items.blocks.UnplaceableBlock
import io.github.thebusybiscuit.slimefun5.libraries.dough.items.ItemUtils
import net.guizhanss.fastmachines.libs.guizhanlib.items.edit
import net.guizhanss.fastmachines.libs.guizhanlib.utils.InventoryUtil
import net.guizhanss.fastmachines.utils.items.isDisabledIn
import org.bukkit.inventory.ItemStack
import javax.annotation.Nonnull

class StackedAncientPedestal(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : UnplaceableBlock(itemGroup, itemStack, recipeType, recipe) {

    @Nonnull
    override fun getItemHandler(): ItemUseHandler {
        return ItemUseHandler { e: PlayerRightClickEvent ->
            e.cancel()
            val p = e.player
            val pedestal = SlimefunItems.ANCIENT_PEDESTAL
            if (pedestal.item().isDisabledIn(p.world)) {
                return@ItemUseHandler
            }
            ItemUtils.consumeItem(e.item, true)
            InventoryUtil.push(p, pedestal.edit { amount(4) })
            SoundEffect.ANCIENT_ALTAR_START_SOUND.playFor(p)
        }
    }
}
