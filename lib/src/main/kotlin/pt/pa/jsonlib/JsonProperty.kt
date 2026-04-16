package pt.pa.jsonlib

/** Overrides the JSON property name used for a class field. */
@Target(AnnotationTarget.PROPERTY)
annotation class JsonProperty(val name: String)
