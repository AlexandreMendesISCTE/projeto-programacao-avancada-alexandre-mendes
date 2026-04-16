package pt.pa.jsonlib

fun JsonValue.accept(visitor: (JsonValue) -> Unit) {
    visitor(this)

    when (this) {
        is JsonObject -> entries().forEach { (_, child) -> child.accept(visitor) }
        is JsonArray -> toList().forEach { child -> child.accept(visitor) }
        else -> Unit
    }
}

fun JsonValue.filterNodes(predicate: (JsonValue) -> Boolean): List<JsonValue> {
    val nodes = mutableListOf<JsonValue>()
    accept { value ->
        if (predicate(value)) {
            nodes.add(value)
        }
    }
    return nodes
}

fun JsonValue.countNodes(predicate: (JsonValue) -> Boolean = { true }): Int =
    filterNodes(predicate).size
