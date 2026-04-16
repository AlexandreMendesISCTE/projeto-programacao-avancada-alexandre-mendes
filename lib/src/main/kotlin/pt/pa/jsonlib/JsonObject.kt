package pt.pa.jsonlib

class JsonObject : JsonValue {
    private val properties = LinkedHashMap<String, JsonValue>()

    val size: Int
        get() = properties.size

    fun isEmpty(): Boolean = properties.isEmpty()

    fun propertyNames(): List<String> = properties.keys.toList()

    fun setProperty(name: String, value: Any?): JsonValue? {
        require(name.isNotBlank()) { "Property name cannot be blank" }
        return properties.put(name, JsonValueFactory.fromAny(value))
    }

    fun getProperty(name: String): JsonValue? = properties[name]

    operator fun get(name: String): JsonValue? = getProperty(name)

    fun removeProperty(name: String): JsonValue? {
        require(name.isNotBlank()) { "Property name cannot be blank" }
        return properties.remove(name)
    }

    internal fun entries(): Set<Map.Entry<String, JsonValue>> = properties.entries
}
