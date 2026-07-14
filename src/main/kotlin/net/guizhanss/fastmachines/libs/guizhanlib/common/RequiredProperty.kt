package net.guizhanss.fastmachines.libs.guizhanlib.common

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A property delegate for DSL builders: reads throw until the property has been set (unless a
 * [default] is given), and an optional [setter] transform is applied to every assigned value.
 *
 * Java-8-safe, Kotlin port of the `RequiredProperty` delegate GuizhanLib-kt's item builder DSL used -
 * see the package-level note in [net.guizhanss.fastmachines.libs.guizhanlib].
 */
class RequiredProperty<T>(
    default: T? = null,
    private val setter: ((T) -> T)? = null,
) : ReadWriteProperty<Any?, T> {

    private var value: T? = default

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: error("Property '${property.name}' has not been set")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = setter?.invoke(value) ?: value
    }
}
