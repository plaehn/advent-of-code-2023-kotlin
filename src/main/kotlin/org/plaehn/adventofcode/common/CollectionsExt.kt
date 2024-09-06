package org.plaehn.adventofcode.common

// Cf. https://youtrack.jetbrains.com/issue/KT-41648
fun <T> Iterable<T>.chunked(predicate: (T, T) -> Boolean): List<List<T>> {
    val underlyingIterable = this
    return sequence {
        val buffer = mutableListOf<T>()
        var last: T? = null
        for (current in underlyingIterable) {
            val shouldSplit = last?.let { predicate(it, current) } ?: false
            if (shouldSplit) {
                yield(buffer.toList())
                buffer.clear()
            }
            buffer.add(current)
            last = current
        }
        if (buffer.isNotEmpty()) {
            yield(buffer)
        }
    }.toList()
}