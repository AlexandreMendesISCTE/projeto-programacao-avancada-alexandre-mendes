package pt.pa.jsonlib.plugins

/**
 * Contract for custom serializers that convert objects into JSON string values.
 */
interface JsonStringSerializer<T : Any> {
    /** Converts [value] into a string representation to be written in JSON. */
    fun serialize(value: T): String
}
