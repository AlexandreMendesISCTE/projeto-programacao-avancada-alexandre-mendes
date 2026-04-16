package pt.pa.jsonlib

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class JsonModelTest {
    @Test
    fun createsEmptyJsonObject() {
        val json = JsonObject()

        assertTrue(json.isEmpty())
        assertEquals(0, json.size)
    }

    @Test
    fun addsReplacesAndRemovesProperties() {
        val json = JsonObject()

        assertNull(json.setProperty("name", "ProJson"))
        assertEquals("ProJson", (json["name"] as JsonPrimitive).value)

        val replaced = json.setProperty("name", "Project")
        assertEquals("ProJson", (replaced as JsonPrimitive).value)
        assertEquals("Project", (json["name"] as JsonPrimitive).value)

        val removed = json.removeProperty("name")
        assertEquals("Project", (removed as JsonPrimitive).value)
        assertTrue(json.isEmpty())
    }

    @Test
    fun createsArrayAndAddsRemovesElements() {
        val array = JsonArray()

        array.add("a").add(null).add(2)

        assertEquals(3, array.size)
        assertEquals("a", (array[0] as JsonPrimitive).value)
        assertIs<JsonNull>(array[1])
        assertEquals(2, (array[2] as JsonPrimitive).value)

        val removed = array.removeAt(1)
        assertIs<JsonNull>(removed)
        assertEquals(2, array.size)
    }

    @Test
    fun preventsInvalidStates() {
        val json = JsonObject()

        assertFailsWith<IllegalArgumentException> {
            json.setProperty("", "invalid")
        }

        assertFailsWith<IllegalArgumentException> {
            json.setProperty("unsupported", listOf(1, 2, 3))
        }

        assertFailsWith<IllegalArgumentException> {
            JsonReference("   ")
        }

        assertFailsWith<IllegalArgumentException> {
            JsonArray().add(mapOf("x" to 1))
        }
    }

    @Test
    fun keepsInsertionOrderInObjectProperties() {
        val json = JsonObject()

        json.setProperty("first", 1)
        json.setProperty("second", 2)
        json.setProperty("third", 3)

        assertEquals(listOf("first", "second", "third"), json.propertyNames())
    }

    @Test
    fun supportsNestedJsonStructures() {
        val child = JsonObject().apply {
            setProperty("id", 10)
        }
        val array = JsonArray().add(child)
        val root = JsonObject()

        root.setProperty("items", array)

        val storedArray = root["items"] as JsonArray
        val storedChild = storedArray[0] as JsonObject

        assertEquals(10, (storedChild["id"] as JsonPrimitive).value)
    }
}
