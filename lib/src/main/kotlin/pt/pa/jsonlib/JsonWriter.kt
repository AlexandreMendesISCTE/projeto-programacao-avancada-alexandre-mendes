package pt.pa.jsonlib

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

    private fun escape(text: String): String = buildString {
        for (ch in text) {
            when (ch) {
                '"' -> append("\\\"")
                '\\' -> append("\\\\")
                '\b' -> append("\\b")
                '\u000C' -> append("\\f")
                '\n' -> append("\\n")
                '\r' -> append("\\r")
                '\t' -> append("\\t")
                else -> {
                    if (ch.code < 0x20) {
                        append("\\u")
                        append(ch.code.toString(16).padStart(4, '0'))
                    } else {
                        append(ch)
                    }
                }
            }
        }
    }

    private fun requireFinite(number: Number) {
        when (number) {
            is Double -> require(number.isFinite()) { "JSON numbers must be finite" }
            is Float -> require(number.isFinite()) { "JSON numbers must be finite" }
        }
    }
}
