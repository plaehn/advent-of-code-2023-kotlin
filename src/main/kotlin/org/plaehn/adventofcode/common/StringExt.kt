package org.plaehn.adventofcode.common

fun String.tokenize(): List<String> = this.split("\\s+".toRegex()).filter { it.isNotBlank() }

fun String.toIntSet(): Set<Int> = this.tokenize().map { it.trim().toInt() }.toSet()