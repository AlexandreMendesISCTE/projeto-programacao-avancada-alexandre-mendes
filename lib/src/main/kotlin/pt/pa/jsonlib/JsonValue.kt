package pt.pa.jsonlib

/** Base type for all in-memory JSON nodes. */
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
