package org.plaehn.adventofcode.common

fun String.tokenize(): List<String> = this.split("\\s+".toRegex()).filter { it.isNotBlank() }

fun String.toIntSet(): Set<Int> = this.tokenize().map { it.trim().toInt() }.toSet()

fun String.toInts(): List<Int> =
    "-?\\d+".toRegex().findAll(this).map { it.value.toInt() }.toList()

fun String.chunkByBlankLines(): List<List<String>> =
    this.lines()
        .chunked { _, current -> current.isBlank() }
        .map { chunk -> chunk.filter { line -> line.isNotBlank() } }
