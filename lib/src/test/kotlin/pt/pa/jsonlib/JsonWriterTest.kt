package pt.pa.jsonlib

import kotlin.test.Test
import kotlin.test.assertEquals

class JsonWriterTest {
    @Test
    fun rendersEscapedStrings() {
        val value = JsonObject().apply {
            setProperty("text", "line1\n\"quoted\"\\path")
        }

        assertEquals("{\"text\":\"line1\\n\\\"quoted\\\"\\\\path\"}", value.toString())
    }

    @Test
    fun rendersMixedArray() {
        val array = JsonArray().add("a").add(null).add(true).add(2)

        assertEquals("[\"a\",null,true,2]", array.toString())
    }

    @Test
    fun rendersNestedObjectsAndArrays() {
        val child = JsonObject().apply {
            setProperty("id", 10)
        }
        val root = JsonObject().apply {
            setProperty("name", "root")
            setProperty("items", JsonArray().add(child))
        }

        assertEquals("{\"name\":\"root\",\"items\":[{\"id\":10}]}", root.toString())
    }

    @Test
    fun rendersReferenceObjects() {
        val root = JsonObject().apply {
            setProperty("dep", JsonReference("abc-123"))
        }

        assertEquals("{\"dep\":{\"\$ref\":\"abc-123\"}}", root.toString())
    }
}
