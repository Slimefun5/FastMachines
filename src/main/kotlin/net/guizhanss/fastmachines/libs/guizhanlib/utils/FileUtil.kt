package net.guizhanss.fastmachines.libs.guizhanlib.utils

import java.io.File
import java.util.jar.JarEntry
import java.util.jar.JarFile

/**
 * Java-8-safe, Kotlin port of the small slice of GuizhanLib's `FileUtil` this addon uses - see the
 * package-level note in [net.guizhanss.fastmachines.libs.guizhanlib].
 */
object FileUtil {

    fun <T> listJarEntries(
        jarFile: File,
        filter: (String, JarEntry) -> Boolean,
        mapper: (String, JarEntry) -> T,
    ): List<T> {
        JarFile(jarFile).use { jar ->
            return jar.entries().asSequence()
                .filter { filter(it.name, it) }
                .map { mapper(it.name, it) }
                .toList()
        }
    }
}
