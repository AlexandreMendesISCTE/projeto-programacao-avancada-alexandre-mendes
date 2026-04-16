package pt.pa.jsonlib

import kotlin.reflect.KClass
import pt.pa.jsonlib.plugins.JsonStringSerializer

/**
 * Applies a custom serializer that converts instances of an annotated class to JSON strings.
 */
@Target(AnnotationTarget.CLASS)
annotation class JsonString(
    val serializer: KClass<out JsonStringSerializer<*>>
)
