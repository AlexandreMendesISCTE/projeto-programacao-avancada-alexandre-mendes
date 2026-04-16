package pt.pa.jsonlib

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class AnnotationCustomizationTest {
    class Task(
        @JsonProperty("desc") val description: String,
        @JsonIgnore val deadline: String?,
        @JsonProperty("deps") @Reference val dependencies: List<Task>
    )

    @Test
    fun appliesJsonPropertyAndJsonIgnore() {
        val task = Task("T1", "2026-01-01", emptyList())

        val json = ProJson().toJson(task) as JsonObject

        assertEquals("T1", (json["desc"] as JsonPrimitive).value)
        assertNull(json["description"])
        assertNull(json["deadline"])
        assertNotNull(json["deps"])
    }

    @Test
    fun appliesReferenceOnAnnotatedProperty() {
        val t1 = Task("T1", null, emptyList())
        val t2 = Task("T2", null, listOf(t1))

        val json = ProJson().toJson(listOf(t1, t2)) as JsonArray
        val first = json[0] as JsonObject
        val second = json[1] as JsonObject

        val firstId = (first["\$id"] as JsonPrimitive).value as String
        val deps = second["deps"] as JsonArray

        assertEquals(firstId, (deps[0] as JsonReference).refId)
    }
}
