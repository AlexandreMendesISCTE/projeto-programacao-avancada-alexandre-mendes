package pt.pa.jsonlib

data class JsonPrimitive(val value: Any) : JsonValue {
    init {
        require(value is String || value is Number || value is Boolean) {
            "JsonPrimitive only supports String, Number, and Boolean values"
        }
    }
}
