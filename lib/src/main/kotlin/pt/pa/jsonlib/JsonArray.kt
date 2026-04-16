package pt.pa.jsonlib

class JsonArray : JsonValue {
    private val elements = mutableListOf<JsonValue>()

    val size: Int
        get() = elements.size

    fun isEmpty(): Boolean = elements.isEmpty()

    fun add(value: Any?): JsonArray {
        elements.add(JsonValueFactory.fromAny(value))
        return this
    }

    operator fun get(index: Int): JsonValue = elements[index]

    fun removeAt(index: Int): JsonValue = elements.removeAt(index)

    fun toList(): List<JsonValue> = elements.toList()
}
