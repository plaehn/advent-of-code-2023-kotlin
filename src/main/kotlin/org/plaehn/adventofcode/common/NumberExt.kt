package org.plaehn.adventofcode.common


fun Iterable<Int>.product(): Int = reduce(Int::times)

fun Iterable<Long>.product(): Long = reduce(Long::times)
