package org.plaehn.adventofcode.common

fun <T> Sequence<T>.cycle() = sequence { while (true) yieldAll(this@cycle) }
