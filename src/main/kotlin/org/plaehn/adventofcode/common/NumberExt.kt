package org.plaehn.adventofcode.common


fun Iterable<Long>.product(): Long = reduce(Long::times)
