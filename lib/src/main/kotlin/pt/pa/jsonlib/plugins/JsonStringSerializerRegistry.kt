package pt.pa.jsonlib.plugins

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

internal class JsonStringSerializerRegistry {
    private val cache = ConcurrentHashMap<KClass<*>, JsonStringSerializer<Any>>()

    @Suppress("UNCHECKED_CAST")
    fun serialize(value: Any, serializerClass: KClass<out JsonStringSerializer<*>>): String {
        val serializer = cache.getOrPut(serializerClass) {
            createSerializer(serializerClass)
        }

        return try {
            serializer.serialize(value)
        } catch (error: ClassCastException) {
            throw IllegalArgumentException(
                "Serializer ${serializerClass.qualifiedName} is incompatible with value type ${value::class.qualifiedName}",
                error
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun createSerializer(serializerClass: KClass<out JsonStringSerializer<*>>): JsonStringSerializer<Any> {
        val instance = try {
            serializerClass.createInstance()
        } catch (error: Exception) {
            throw IllegalArgumentException(
                "Serializer ${serializerClass.qualifiedName} must have a public no-arg constructor",
                error
            )
        }

        return instance as? JsonStringSerializer<Any>
            ?: throw IllegalArgumentException(
                "Serializer ${serializerClass.qualifiedName} must implement JsonStringSerializer"
            )
    }
}
