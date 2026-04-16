package pt.pa.jsonlib

/** Singleton JSON null value. */
data object JsonNull : JsonValue {
	override fun toString(): String = JsonWriter.write(this)
}
