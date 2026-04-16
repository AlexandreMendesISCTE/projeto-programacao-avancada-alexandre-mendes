package pt.pa.jsonlib

data class JsonReference(val refId: String) : JsonValue {
    init {
        require(refId.isNotBlank()) { "Reference id cannot be blank" }
    }
}
