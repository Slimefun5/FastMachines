package net.guizhanss.fastmachines.libs.guizhanlib.slimefun.items.builder

import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial
import net.guizhanss.fastmachines.utils.MaterialCompat
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * A small DSL for describing a 3x3 shaped recipe as three 3-character rows (`+"..."`) plus a mapping
 * from each character to an ingredient (`'X' means ...`). A blank space always means "no ingredient".
 *
 * Java-8-safe, Kotlin port of GuizhanLib-kt's `buildRecipe` DSL - see the package-level note in
 * [net.guizhanss.fastmachines.libs.guizhanlib].
 */
class RecipeBuilder {

    private val rows = mutableListOf<String>()
    private val ingredients = mutableMapOf<Char, ItemStack?>()

    operator fun String.unaryPlus() {
        require(length == 3) { "Each recipe row must be exactly 3 characters long, was \"$this\"" }
        rows += this
    }

    infix fun Char.means(material: Material?) {
        ingredients[this] = material?.let { ItemStack(it) }
    }

    /** Version-safe ingredient: the [XMaterial] is resolved to a [Material] that exists on this server. */
    infix fun Char.means(material: XMaterial?) {
        ingredients[this] = material?.let { ItemStack(MaterialCompat.safe(it)) }
    }

    infix fun Char.means(item: SlimefunItemStack?) {
        ingredients[this] = item?.item()
    }

    infix fun Char.means(item: ItemStack?) {
        ingredients[this] = item
    }

    internal fun build(): Array<ItemStack?> {
        require(rows.size == 3) { "A recipe must have exactly 3 rows, had ${rows.size}" }
        val shape = rows.joinToString("")
        return Array(9) { i -> ingredients[shape[i]] }
    }
}

fun buildRecipe(block: RecipeBuilder.() -> Unit): Array<ItemStack?> {
    val builder = RecipeBuilder()
    builder.block()
    return builder.build()
}
