package pt.pa.jsonlib.reflection

import pt.pa.jsonlib.JsonIgnore
import pt.pa.jsonlib.JsonProperty
import pt.pa.jsonlib.JsonString
import pt.pa.jsonlib.Reference
import pt.pa.jsonlib.plugins.JsonStringSerializer
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

internal data class ReflectedProperty(
    val name: String,
    val value: Any?,
    val useReference: Boolean
)

internal object ReflectionInspector {
    fun typeNameOf(instance: Any): String =
        instance::class.simpleName ?: instance::class.qualifiedName ?: "Unknown"

    fun stringSerializerClassOf(instance: Any): KClass<out JsonStringSerializer<*>>? =
        instance::class.findAnnotation<JsonString>()?.serializer

    fun readProperties(instance: Any): List<ReflectedProperty> =
        orderedProperties(instance::class).mapNotNull { property ->
            if (property.findAnnotation<JsonIgnore>() != null) {
                return@mapNotNull null
            }

            property.isAccessible = true
            val resolvedName = property.findAnnotation<JsonProperty>()?.name ?: property.name

            ReflectedProperty(
                name = resolvedName,
                value = property.get(instance),
                useReference = property.findAnnotation<Reference>() != null
            )
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
