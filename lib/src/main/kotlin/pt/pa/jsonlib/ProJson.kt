package pt.pa.jsonlib

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

class ProJson {
    fun toJson(value: Any?): JsonValue = serialize(value)

    private fun serialize(value: Any?): JsonValue = when (value) {
        null -> JsonNull
        is JsonValue -> value
        is String, is Number, is Boolean -> JsonPrimitive(value)
        is Map<*, *> -> serializeMap(value)
        is Iterable<*> -> serializeIterable(value)
        else -> {
            if (value.javaClass.isArray) {
                serializeArray(value)
            } else {
                serializeObject(value)
            }
        }
    }

    private fun serializeIterable(values: Iterable<*>): JsonArray = JsonArray().also { array ->
        values.forEach { value ->
            array.add(serialize(value))
        }
    }

    private fun serializeArray(values: Any): JsonArray = JsonArray().also { array ->
        val length = java.lang.reflect.Array.getLength(values)
        for (index in 0 until length) {
            array.add(serialize(java.lang.reflect.Array.get(values, index)))
        }
    }

    private fun serializeMap(values: Map<*, *>): JsonObject = JsonObject().also { obj ->
        values.forEach { (key, value) ->
            requireNotNull(key) { "JSON object keys cannot be null" }
            obj.setProperty(key.toString(), serialize(value))
        }
    }

    private fun serializeObject(value: Any): JsonObject = JsonObject().also { obj ->
        val typeName = value::class.simpleName ?: value::class.qualifiedName ?: "Unknown"
        obj.setProperty("\$type", typeName)
        orderedProperties(value::class).forEach { property ->
            property.isAccessible = true
            obj.setProperty(property.name, serialize(property.get(value)))
        }
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
