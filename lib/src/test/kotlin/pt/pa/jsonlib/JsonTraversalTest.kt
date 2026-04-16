package pt.pa.jsonlib

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JsonTraversalTest {
    @Test
    fun visitsAllNodesInTree() {
        val root = JsonObject().apply {
            setProperty("name", "root")
            setProperty(
                "items",
                JsonArray()
                    .add(JsonObject().apply { setProperty("id", 1) })
                    .add(JsonObject().apply { setProperty("id", 2) })
            )
        }

        val visited = mutableListOf<JsonValue>()
        root.accept { visited.add(it) }

        assertEquals(7, visited.size)
        assertTrue(visited.first() is JsonObject)
    }

    @Test
    fun countsNodesWithPredicate() {
        val root = JsonObject().apply {
            setProperty("ref", JsonReference("x"))
            setProperty("ok", true)
        }

        assertEquals(3, root.countNodes())
        assertEquals(1, root.countNodes { it is JsonReference })
    }

    @Test
    fun filtersReferencesFromTree() {
        val root = JsonObject().apply {
            setProperty("dep1", JsonReference("a"))
            setProperty("dep2", JsonReference("b"))
            setProperty("name", "task")
        }

        val refs = root.filterNodes { it is JsonReference }

        assertEquals(2, refs.size)
        assertEquals("a", (refs[0] as JsonReference).refId)
        assertEquals("b", (refs[1] as JsonReference).refId)
    }
}
