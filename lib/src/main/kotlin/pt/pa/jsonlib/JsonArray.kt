package pt.pa.jsonlib

/** Mutable in-memory representation of a JSON array. */
class JsonArray : JsonValue {
    private val elements = mutableListOf<JsonValue>()

    val size: Int
        get() = elements.size

    /** Returns true when the array has no elements. */
    fun isEmpty(): Boolean = elements.isEmpty()

    /** Appends a value to the array and returns this array for fluent usage. */
    fun add(value: Any?): JsonArray {
        elements.add(JsonValueFactory.fromAny(value))
        return this
    }

    operator fun get(index: Int): JsonValue = elements[index]

    /** Removes and returns the element at the given index. */
    fun removeAt(index: Int): JsonValue = elements.removeAt(index)

    fun toList(): List<JsonValue> = elements.toList()

    override fun toString(): String = JsonWriter.write(this)
}
