package pt.pa.jsonlib

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ReferenceSerializationTest {
    data class DateValue(val day: Int, val month: Int, val year: Int)

    class Task(
        val description: String,
        val deadline: DateValue?,
        @Reference val dependencies: List<Task>
    )

    @Test
    fun serializesReferencesUsingIdAndRef() {
        val t1 = Task("T1", DateValue(30, 2, 2026), emptyList())
        val t2 = Task("T2", DateValue(31, 4, 2026), emptyList())
        val t3 = Task("T3", null, listOf(t1, t2))

        val json = ProJson().toJson(listOf(t1, t2, t3)) as JsonArray

        val j1 = json[0] as JsonObject
        val j2 = json[1] as JsonObject
        val j3 = json[2] as JsonObject

        val id1 = (j1["\$id"] as JsonPrimitive).value as String
        val id2 = (j2["\$id"] as JsonPrimitive).value as String

        val dependencies = j3["dependencies"] as JsonArray
        assertEquals(id1, (dependencies[0] as JsonReference).refId)
        assertEquals(id2, (dependencies[1] as JsonReference).refId)
    }

    @Test
    fun doesNotInjectIdIntoValueObjectsByDefault() {
        val t1 = Task("T1", DateValue(30, 2, 2026), emptyList())
        val json = ProJson().toJson(t1) as JsonObject

        val deadline = json["deadline"] as JsonObject

        assertNotNull(json["\$id"])
        assertNull(deadline["\$id"])
    }

    @Test
    fun keepsSharedObjectsInlineWhenReferenceIsNotRequested() {
        class Shared(val value: Int)
        class Holder(val first: Shared, val second: Shared)

        val shared = Shared(10)
        val json = ProJson().toJson(Holder(shared, shared)) as JsonObject

        assertIs<JsonObject>(json["first"])
        assertIs<JsonObject>(json["second"])
    }
}
