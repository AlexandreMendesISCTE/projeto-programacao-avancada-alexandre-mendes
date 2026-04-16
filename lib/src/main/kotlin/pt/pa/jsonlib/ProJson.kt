package pt.pa.jsonlib

import pt.pa.jsonlib.reflection.ReflectionInspector

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
        obj.setProperty("\$type", ReflectionInspector.typeNameOf(value))
        ReflectionInspector.readProperties(value).forEach { property ->
            obj.setProperty(property.name, serialize(property.value))
        }
    }
}
