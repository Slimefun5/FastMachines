package net.guizhanss.fastmachines.libs.guizhanlib.slimefun.machines

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockBreakHandler
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockPlaceHandler
import me.mrCookieSlime.Slimefun.api.BlockStorage
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow
import org.bukkit.block.Block
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.ItemStack

/**
 * A [MenuBlock] is a [SlimefunItem] with a [BlockMenuPreset].
 *
 * Java-8-safe, Kotlin port of GuizhanLib 0.9.0's `MenuBlock` (itself "Modified from InfinityLib"):
 * the upstream jar is class-file version 60 (Java 16) and cannot be read by a Java-8 javac as a
 * compile dependency, so this vendored copy is used instead. Only the API package was migrated
 * (slimefun4 -> slimefun5); the logic is unchanged from upstream.
 *
 * Members that [MenuBlockPreset] (a sibling class, not a subclass) needs to call are `internal`
 * rather than `protected`: unlike Java, Kotlin's `protected` is subclass-only and does not also grant
 * same-package access.
 *
 * @author Mooy1 (InfinityLib original)
 * @author ybw0014 (GuizhanLib port)
 */
abstract class MenuBlock(
    itemGroup: ItemGroup,
    item: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : SlimefunItem(itemGroup, item, recipeType, recipe) {

    init {
        addItemHandler(
            object : BlockBreakHandler(false, false) {
                override fun onPlayerBreak(e: BlockBreakEvent, itemStack: ItemStack, list: MutableList<ItemStack>) {
                    val menu = BlockStorage.getInventory(e.block) ?: return
                    onBreak(e, menu)
                }
            },
            object : BlockPlaceHandler(false) {
                override fun onPlayerPlace(e: BlockPlaceEvent) {
                    onPlace(e, e.blockPlaced)
                }
            }
        )
    }

    override fun postRegister() {
        MenuBlockPreset(this)
    }

    internal abstract fun setup(preset: BlockMenuPreset)

    internal fun getTransportSlots(menu: DirtyChestMenu, flow: ItemTransportFlow, item: ItemStack): IntArray {
        return when (flow) {
            ItemTransportFlow.INSERT -> getInputSlots(menu, item)
            ItemTransportFlow.WITHDRAW -> getOutputSlots()
            else -> IntArray(0)
        }
    }

    internal open fun getInputSlots(menu: DirtyChestMenu, item: ItemStack): IntArray = getInputSlots()

    internal abstract fun getInputSlots(): IntArray

    internal abstract fun getOutputSlots(): IntArray

    internal open fun onNewInstance(menu: BlockMenu, b: Block) {}

    internal open fun onBreak(e: BlockBreakEvent, menu: BlockMenu) {
        val l = menu.location
        menu.dropItems(l, *getInputSlots())
        menu.dropItems(l, *getOutputSlots())
    }

    internal open fun onPlace(e: BlockPlaceEvent, b: Block) {}
}
