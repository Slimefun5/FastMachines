package net.guizhanss.fastmachines.libs.guizhanlib.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

/**
 * Finds a constructor of this class whose parameter types loosely match the given arguments (by
 * runtime type, so `null` arguments match any parameter type).
 *
 * Java-8-safe, Kotlin port of the reflection helper GuizhanLib-kt's item builder DSL used - see the
 * package-level note in [net.guizhanss.fastmachines.libs.guizhanlib].
 */
fun <T : Any> KClass<T>.getConstructor(vararg args: Any?): KFunction<T>? {
    return constructors.firstOrNull { ctor ->
        val params = ctor.parameters
        params.size == args.size && params.indices.all { i ->
            val arg = args[i]
            val classifier = params[i].type.classifier as? KClass<*>
            arg == null || classifier == null || classifier.isInstance(arg)
        }
    }
}

/**
 * Invokes a method of the given [name] on this object via reflection, matching by name and argument
 * count only. Used to reach methods that only exist on some Bukkit API versions/implementations
 * (e.g. a shared `getResult()` that different `Recipe` subclasses only gained in newer API versions)
 * without a hard compile-time reference to a specific type.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T> Any.invoke(name: String, vararg args: Any?): T? {
    val method = this::class.java.methods.firstOrNull { it.name == name && it.parameterCount == args.size }
        ?: return null
    return method.invoke(this, *args) as? T
}
