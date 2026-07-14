package net.guizhanss.fastmachines.libs.guizhanlib.items

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack
import net.guizhanss.fastmachines.libs.guizhanlib.utils.ChatUtil
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * A tiny builder DSL for editing an [ItemStack]'s meta in place, ported from GuizhanLib-kt's `edit`
 * extension - see the package-level note in [net.guizhanss.fastmachines.libs.guizhanlib].
 */
class ItemStackEditor(private val stack: ItemStack) {

    fun name(name: String) {
        val meta = stack.itemMeta ?: return
        meta.setDisplayName(ChatUtil.color(name))
        stack.itemMeta = meta
    }

    fun lore(vararg lines: String) {
        val meta = stack.itemMeta ?: return
        meta.lore = lines.map(ChatUtil::color)
        stack.itemMeta = meta
    }

    fun amount(amount: Int) {
        stack.amount = amount
    }
}

fun Material.toItem(): ItemStack = ItemStack(this)

fun ItemStack.edit(block: ItemStackEditor.() -> Unit): ItemStack {
    ItemStackEditor(this).apply(block)
    return this
}

/** Edits a copy of this [SlimefunItemStack]'s underlying [ItemStack], leaving the registered item stack untouched. */
fun SlimefunItemStack.edit(block: ItemStackEditor.() -> Unit): ItemStack = item().clone().edit(block)
