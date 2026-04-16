package pt.pa.jsonlib

/** Mutable in-memory representation of a JSON object. */
class JsonObject : JsonValue {
    private val properties = LinkedHashMap<String, JsonValue>()

    val size: Int
        get() = properties.size

    /** Returns true when the object has no properties. */
    fun isEmpty(): Boolean = properties.isEmpty()

    /** Returns property names preserving insertion order. */
    fun propertyNames(): List<String> = properties.keys.toList()

    /** Adds or replaces a property and returns the previous value when present. */
    fun setProperty(name: String, value: Any?): JsonValue? {
        require(name.isNotBlank()) { "Property name cannot be blank" }
        return properties.put(name, JsonValueFactory.fromAny(value))
    }

    /** Returns the value of a property by name, or null when absent. */
    fun getProperty(name: String): JsonValue? = properties[name]

    operator fun get(name: String): JsonValue? = getProperty(name)

    /** Removes a property by name and returns its previous value when present. */
    fun removeProperty(name: String): JsonValue? {
        require(name.isNotBlank()) { "Property name cannot be blank" }
        return properties.remove(name)
    }

    internal fun entries(): Set<Map.Entry<String, JsonValue>> = properties.entries

    override fun toString(): String = JsonWriter.write(this)
}
