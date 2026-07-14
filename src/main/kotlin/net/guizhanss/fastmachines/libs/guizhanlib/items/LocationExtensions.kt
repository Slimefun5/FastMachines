package net.guizhanss.fastmachines.libs.guizhanlib.items

import io.github.thebusybiscuit.slimefun5.libraries.dough.blocks.BlockPosition
import org.bukkit.Location

/**
 * Java-8-safe, Kotlin port of GuizhanLib-kt's `position` extension - see the package-level note in
 * [net.guizhanss.fastmachines.libs.guizhanlib].
 */
val Location.position: BlockPosition
    get() = BlockPosition(this)
