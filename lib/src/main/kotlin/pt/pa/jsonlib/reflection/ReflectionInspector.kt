package pt.pa.jsonlib.reflection

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

internal data class ReflectedProperty(val name: String, val value: Any?)

internal object ReflectionInspector {
    fun typeNameOf(instance: Any): String =
        instance::class.simpleName ?: instance::class.qualifiedName ?: "Unknown"

    fun readProperties(instance: Any): List<ReflectedProperty> =
        orderedProperties(instance::class).map { property ->
            property.isAccessible = true
            ReflectedProperty(property.name, property.get(instance))
        }

    private fun orderedProperties(kClass: KClass<out Any>): List<KProperty1<Any, *>> {
        val properties = kClass.memberProperties.filterIsInstance<KProperty1<Any, *>>()
        val byName = properties.associateBy { it.name }
        val ctorNames = kClass.primaryConstructor?.parameters?.mapNotNull { it.name }.orEmpty()
        val ordered = mutableListOf<KProperty1<Any, *>>()

        ctorNames.forEach { name ->
            byName[name]?.let { ordered.add(it) }
        }

        properties
            .filterNot { it.name in ctorNames }
            .sortedBy { it.name }
            .forEach { ordered.add(it) }

        return ordered
    }
}
