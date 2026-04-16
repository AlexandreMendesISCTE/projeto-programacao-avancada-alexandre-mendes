package pt.pa.jsonlib

import pt.pa.jsonlib.internal.JsonTextFormatting.escape
import pt.pa.jsonlib.internal.JsonTextFormatting.requireFinite

object JsonWriter {
    fun write(value: JsonValue): String = when (value) {
        is JsonObject -> writeObject(value)
        is JsonArray -> writeArray(value)
        is JsonPrimitive -> writePrimitive(value.value)
        is JsonReference -> "{\"\$ref\":\"${escape(value.refId)}\"}"
        JsonNull -> "null"
    }

    private fun writeObject(obj: JsonObject): String {
        val body = obj.entries().joinToString(",") { (key, value) ->
            "\"${escape(key)}\":${write(value)}"
        }
        return "{$body}"
    }

    private fun writeArray(array: JsonArray): String {
        val body = array.toList().joinToString(",") { write(it) }
        return "[$body]"
    }

    private fun writePrimitive(value: Any): String = when (value) {
        is String -> "\"${escape(value)}\""
        is Number -> {
            requireFinite(value)
            value.toString()
        }
        is Boolean -> value.toString()
        else -> throw IllegalArgumentException("Unsupported primitive value: ${value::class.qualifiedName}")
    }

}
