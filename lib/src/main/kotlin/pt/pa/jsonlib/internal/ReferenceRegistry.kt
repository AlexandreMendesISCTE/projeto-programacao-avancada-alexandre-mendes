package pt.pa.jsonlib.internal

import java.util.IdentityHashMap

internal data class ReferenceEntry(
    val id: String,
    var serialized: Boolean = false,
    var inProgress: Boolean = false,
    var referenced: Boolean = false
)

internal class ReferenceRegistry {
    private val entries = IdentityHashMap<Any, ReferenceEntry>()
    private var nextId = 1

    fun entryFor(value: Any): ReferenceEntry {
        val existing = entries[value]
        if (existing != null) {
            return existing
        }

        val created = ReferenceEntry(id = "id-${nextId++}")
        entries[value] = created
        return created
    }

    fun markReferenced(value: Any) {
        entryFor(value).referenced = true
    }
}
