package pt.pa.jsonlib

import pt.pa.jsonlib.plugins.JsonStringSerializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class JsonStringPluginTest {
    @JsonString(DateAsText::class)
    data class DateValue(val day: Int, val month: Int, val year: Int)

    class DateAsText : JsonStringSerializer<DateValue> {
        override fun serialize(value: DateValue): String {
            return "%02d/%02d/%04d".format(value.day, value.month, value.year)
        }
    }

    @JsonString(BrokenSerializer::class)
    data class BrokenDate(val day: Int)

    class BrokenSerializer(private val pattern: String) : JsonStringSerializer<BrokenDate> {
        override fun serialize(value: BrokenDate): String = "$pattern${value.day}"
    }

    data class Holder(val dates: List<DateValue>)

    @Test
    fun serializesAnnotatedClassAsString() {
        val json = ProJson().toJson(listOf(DateValue(30, 2, 2026), DateValue(31, 4, 2026))) as JsonArray

        assertEquals("30/02/2026", (json[0] as JsonPrimitive).value)
        assertEquals("31/04/2026", (json[1] as JsonPrimitive).value)
    }

    @Test
    fun serializesAnnotatedClassInsideNestedObjects() {
        val json = ProJson().toJson(Holder(listOf(DateValue(1, 1, 2026)))) as JsonObject
        val dates = json["dates"] as JsonArray

        assertEquals("01/01/2026", (dates[0] as JsonPrimitive).value)
    }

    @Test
    fun failsFastWhenSerializerCannotBeInstantiated() {
        assertFailsWith<IllegalArgumentException> {
            ProJson().toJson(BrokenDate(5))
        }
    }
}
