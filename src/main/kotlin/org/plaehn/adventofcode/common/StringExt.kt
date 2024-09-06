package org.plaehn.adventofcode.common

fun String.tokenize(): List<String> = this.split("\\s+".toRegex()).filter { it.isNotBlank() }

fun String.toIntSet(): Set<Int> = this.tokenize().map { it.trim().toInt() }.toSet()

fun String.chunkByBlankLines(): List<List<String>> =
    this.lines().chunked { _, current ->
        current.isBlank()
    }
