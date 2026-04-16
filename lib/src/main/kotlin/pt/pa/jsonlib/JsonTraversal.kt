package pt.pa.jsonlib

/** Traverses the JSON tree in pre-order and invokes [visitor] for each node. */
fun JsonValue.accept(visitor: (JsonValue) -> Unit) {
    visitor(this)

    when (this) {
        is JsonObject -> entries().forEach { (_, child) -> child.accept(visitor) }
        is JsonArray -> toList().forEach { child -> child.accept(visitor) }
        else -> Unit
    }
}

/** Returns all nodes that satisfy [predicate]. */
fun JsonValue.filterNodes(predicate: (JsonValue) -> Boolean): List<JsonValue> {
    val nodes = mutableListOf<JsonValue>()
    accept { value ->
        if (predicate(value)) {
            nodes.add(value)
        }
    }
    return nodes
}

/** Counts nodes that satisfy [predicate]. */
fun JsonValue.countNodes(predicate: (JsonValue) -> Boolean = { true }): Int =
    filterNodes(predicate).size

/** Collects all [JsonReference] nodes in the tree. */
fun JsonValue.findReferences(): List<JsonReference> =
    filterNodes { it is JsonReference }.map { it as JsonReference }

/**
 * Creates a transformed copy of the tree by applying [transform] to primitive nodes.
 */
fun JsonValue.mapPrimitives(transform: (JsonPrimitive) -> JsonValue): JsonValue = when (this) {
    is JsonPrimitive -> transform(this)
    is JsonObject -> JsonObject().also { mapped ->
        entries().forEach { (key, value) ->
            mapped.setProperty(key, value.mapPrimitives(transform))
        }
    }
    is JsonArray -> JsonArray().also { mapped ->
        toList().forEach { value ->
            mapped.add(value.mapPrimitives(transform))
        }
    }
    is JsonReference -> this
    JsonNull -> JsonNull
}
