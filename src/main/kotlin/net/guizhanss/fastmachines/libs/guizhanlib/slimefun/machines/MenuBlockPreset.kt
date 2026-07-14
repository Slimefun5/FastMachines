package net.guizhanss.fastmachines.libs.guizhanlib.slimefun.machines

import io.github.thebusybiscuit.slimefun5.implementation.Slimefun
import io.github.thebusybiscuit.slimefun5.libraries.dough.protection.Interaction
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * A [MenuBlockPreset] is a preset of a [MenuBlock].
 *
 * Java-8-safe, Kotlin port of GuizhanLib 0.9.0's `MenuBlockPreset` - see the class doc on [MenuBlock]
 * for why this is vendored rather than a compile dependency.
 *
 * @author Mooy1 (InfinityLib original)
 * @author ybw0014 (GuizhanLib port)
 */
internal class MenuBlockPreset(private val menuBlock: MenuBlock) : BlockMenuPreset(menuBlock.id, menuBlock.itemName) {

    init {
        menuBlock.setup(this)
    }

    override fun newInstance(menu: BlockMenu, b: Block) {
        menuBlock.onNewInstance(menu, b)
    }

    override fun getSlotsAccessedByItemTransport(menu: DirtyChestMenu, flow: ItemTransportFlow, item: ItemStack): IntArray {
        return menuBlock.getTransportSlots(menu, flow, item)
    }

    override fun init() {
        // nothing to do
    }

    override fun canOpen(b: Block, p: Player): Boolean {
        return Slimefun.getProtectionManager().hasPermission(p, b.location, Interaction.INTERACT_BLOCK) &&
            menuBlock.canUse(p, false)
    }

    override fun getSlotsAccessedByItemTransport(flow: ItemTransportFlow): IntArray {
        return IntArray(0)
    }
}
