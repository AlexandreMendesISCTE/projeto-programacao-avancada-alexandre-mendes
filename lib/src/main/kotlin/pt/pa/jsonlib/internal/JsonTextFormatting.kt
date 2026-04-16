package pt.pa.jsonlib.internal

internal object JsonTextFormatting {
    fun escape(text: String): String = buildString {
        for (ch in text) {
            when (ch) {
                '"' -> append("\\\"")
                '\\' -> append("\\\\")
                '\b' -> append("\\b")
                '\u000C' -> append("\\f")
                '\n' -> append("\\n")
                '\r' -> append("\\r")
                '\t' -> append("\\t")
                else -> {
                    if (ch.code < 0x20) {
                        append("\\u")
                        append(ch.code.toString(16).padStart(4, '0'))
                    } else {
                        append(ch)
                    }
                }
            }
        }
    }

    fun requireFinite(number: Number) {
        when (number) {
            is Double -> require(number.isFinite()) { "JSON numbers must be finite" }
            is Float -> require(number.isFinite()) { "JSON numbers must be finite" }
        }
    }
}
