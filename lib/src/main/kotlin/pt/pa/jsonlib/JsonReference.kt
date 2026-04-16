package pt.pa.jsonlib

/** JSON reference node using a reference identifier. */
data class JsonReference(val refId: String) : JsonValue {
    init {
        require(refId.isNotBlank()) { "Reference id cannot be blank" }
    }

    override fun toString(): String = JsonWriter.write(this)
}
