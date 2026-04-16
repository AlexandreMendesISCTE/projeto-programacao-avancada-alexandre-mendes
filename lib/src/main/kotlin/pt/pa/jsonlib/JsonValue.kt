package pt.pa.jsonlib

sealed interface JsonValue

internal object JsonValueFactory {
    fun fromAny(value: Any?): JsonValue = when (value) {
        null -> JsonNull
        is JsonValue -> value
        is String, is Number, is Boolean -> JsonPrimitive(value)
        else -> throw IllegalArgumentException(
            "Unsupported JSON value type: ${value::class.qualifiedName}"
        )
    }
}
