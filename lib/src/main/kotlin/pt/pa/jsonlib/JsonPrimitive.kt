package pt.pa.jsonlib

/** JSON primitive value wrapper for string, number, and boolean values. */
data class JsonPrimitive(val value: Any) : JsonValue {
    init {
        require(value is String || value is Number || value is Boolean) {
            "JsonPrimitive only supports String, Number, and Boolean values"
        }
    }
}
