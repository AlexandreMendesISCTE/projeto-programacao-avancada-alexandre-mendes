package pt.pa.jsonlib

import pt.pa.jsonlib.internal.ReferenceRegistry
import pt.pa.jsonlib.plugins.JsonStringSerializerRegistry
import pt.pa.jsonlib.reflection.ReflectionInspector

/**
 * Entry point for converting Kotlin values into the in-memory JSON model.
 *
 * The conversion supports primitives, collections, maps, regular classes,
 * annotations for customization, and optional reference handling.
 */
class ProJson {
    /** Converts any supported value to a [JsonValue] tree. */
    fun toJson(value: Any?): JsonValue = serialize(
        value = value,
        context = SerializationContext(),
        mode = SerializationMode.NORMAL
    )

    private fun serialize(value: Any?, context: SerializationContext, mode: SerializationMode): JsonValue = when (value) {
        null -> JsonNull
        is JsonValue -> value
        is String, is Number, is Boolean -> JsonPrimitive(value)
        is Map<*, *> -> serializeMap(value, context, mode)
        is Iterable<*> -> serializeIterable(value, context, mode)
        else -> {
            if (value.javaClass.isArray) {
                serializeArray(value, context, mode)
            } else {
                serializeObject(value, context, mode)
            }
        }
    }

    private fun serializeIterable(
        values: Iterable<*>,
        context: SerializationContext,
        mode: SerializationMode
    ): JsonArray = JsonArray().also { array ->
        values.forEach { value ->
            array.add(serialize(value, context, mode))
        }
    }

    private fun serializeArray(
        values: Any,
        context: SerializationContext,
        mode: SerializationMode
    ): JsonArray = JsonArray().also { array ->
        val length = java.lang.reflect.Array.getLength(values)
        for (index in 0 until length) {
            array.add(serialize(java.lang.reflect.Array.get(values, index), context, mode))
        }
    }

    private fun serializeMap(
        values: Map<*, *>,
        context: SerializationContext,
        mode: SerializationMode
    ): JsonObject = JsonObject().also { obj ->
        values.forEach { (key, value) ->
            requireNotNull(key) { "JSON object keys cannot be null" }
            obj.setProperty(key.toString(), serialize(value, context, mode))
        }
    }

    private fun serializeObject(
        value: Any,
        context: SerializationContext,
        mode: SerializationMode
    ): JsonValue {
        ReflectionInspector.stringSerializerClassOf(value)?.let { serializerClass ->
            return JsonPrimitive(context.plugins.serialize(value, serializerClass))
        }

        val entry = context.references.entryFor(value)

        if (mode == SerializationMode.REFERENCE) {
            entry.referenced = true
        }

        if (entry.inProgress) {
            entry.referenced = true
            return JsonReference(entry.id)
        }

        if (entry.serialized && (mode == SerializationMode.REFERENCE || entry.referenced)) {
            return JsonReference(entry.id)
        }

        entry.inProgress = true

        val obj = JsonObject()
        obj.setProperty("\$type", ReflectionInspector.typeNameOf(value))

        if (shouldIncludeId(value, entry.referenced)) {
            obj.setProperty("\$id", entry.id)
        }

        ReflectionInspector.readProperties(value).forEach { property ->
            val propertyMode = if (property.useReference) {
                markReferenceTargets(property.value, context.references)
                SerializationMode.REFERENCE
            } else {
                SerializationMode.NORMAL
            }

            obj.setProperty(
                property.name,
                serialize(property.value, context, propertyMode)
            )
        }

        entry.inProgress = false
        entry.serialized = true
        return obj
    }

    private fun markReferenceTargets(value: Any?, registry: ReferenceRegistry) {
        when (value) {
            null -> Unit
            is String, is Number, is Boolean, is JsonValue -> Unit
            is Map<*, *> -> value.values.forEach { markReferenceTargets(it, registry) }
            is Iterable<*> -> value.forEach { markReferenceTargets(it, registry) }
            else -> {
                if (value.javaClass.isArray) {
                    val length = java.lang.reflect.Array.getLength(value)
                    for (index in 0 until length) {
                        markReferenceTargets(java.lang.reflect.Array.get(value, index), registry)
                    }
                } else {
                    registry.markReferenced(value)
                }
            }
        }
    }

    private fun shouldIncludeId(value: Any, isReferenced: Boolean): Boolean {
        if (isReferenced) {
            return true
        }
        return !value::class.isData
    }

    private enum class SerializationMode {
        NORMAL,
        REFERENCE
    }

    private data class SerializationContext(
        val references: ReferenceRegistry = ReferenceRegistry(),
        val plugins: JsonStringSerializerRegistry = JsonStringSerializerRegistry()
    )
}
