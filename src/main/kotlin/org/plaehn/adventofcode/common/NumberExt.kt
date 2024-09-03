package org.plaehn.adventofcode.common

fun Iterable<Int>.product(): Int = reduce(Int::times)

fun Iterable<Long>.product(): Long = reduce(Long::times)

fun <T> Iterable<T>.productOf(selector: (T) -> Long): Long {
    var product: Long = 1.toLong()
    for (element in this) {
        product *= selector(element)
    }
    return product
}
