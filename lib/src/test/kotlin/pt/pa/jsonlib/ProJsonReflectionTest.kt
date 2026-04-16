package pt.pa.jsonlib

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

class ProJsonReflectionTest {
    data class DateDto(val day: Int, val month: Int, val year: Int)

    data class OrderedTask(
        val description: String,
        val priority: Int,
        val done: Boolean
    )

    @Test
    fun serializesDataClassWithType() {
        val json = ProJson().toJson(DateDto(31, 4, 2026)) as JsonObject

        assertEquals("DateDto", (json["\$type"] as JsonPrimitive).value)
        assertEquals(31, (json["day"] as JsonPrimitive).value)
        assertEquals(4, (json["month"] as JsonPrimitive).value)
        assertEquals(2026, (json["year"] as JsonPrimitive).value)
    }

    @Test
    fun serializesCollectionAsArray() {
        val json = ProJson().toJson(listOf("a", null, "b")) as JsonArray

        assertEquals(3, json.size)
        assertEquals("a", (json[0] as JsonPrimitive).value)
        assertIs<JsonNull>(json[1])
        assertEquals("b", (json[2] as JsonPrimitive).value)
    }

    @Test
    fun serializesMapWithoutType() {
        val json = ProJson().toJson(linkedMapOf("a" to 1, "b" to true)) as JsonObject

        assertNull(json["\$type"])
        assertEquals(1, (json["a"] as JsonPrimitive).value)
        assertEquals(true, (json["b"] as JsonPrimitive).value)
    }

    @Test
    fun keepsConstructorPropertyOrder() {
        val json = ProJson().toJson(OrderedTask("T1", 3, false)) as JsonObject

        assertEquals(
            listOf("\$type", "description", "priority", "done"),
            json.propertyNames()
        )
    }
}
