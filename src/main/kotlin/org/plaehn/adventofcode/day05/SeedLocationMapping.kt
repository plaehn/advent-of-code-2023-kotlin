package org.plaehn.adventofcode.day05

import org.plaehn.adventofcode.common.tokenize

class SeedLocationMapping(
    val seeds: List<Long>,
    val mappings: List<Mapping>
) {

    fun findLowestLocationNumber(): Long =
        seeds.minOfOrNull { it.applyMappings() } ?: throw IllegalStateException()

    private fun Long.applyMappings() =
        mappings.fold(this) { acc, mapping ->
            mapping.applyTo(acc)
        }

    companion object {
        fun fromInput(chunks: List<List<String>>): SeedLocationMapping {
            val seeds = chunks.first().first().split(":").last().tokenize().map { it.toLong() }
            val mappings = chunks.drop(1).map { Mapping.fromInput(it) }

            return SeedLocationMapping(
                seeds = seeds,
                mappings = mappings
            )
        }
    }

    data class Mapping(
        val ranges: List<Range>
    ) {
        fun applyTo(input: Long): Long =
            ranges.firstNotNullOfOrNull { range ->
                range.applyTo(input)
            } ?: input

        companion object {
            fun fromInput(chunk: List<String>) =
                Mapping(
                    ranges = chunk
                        .dropWhile { it.isBlank() || it.contains("map:") }
                        .map { Range.fromInput(it) }
                        .sortedBy { it.sourceRange.first }
                )
        }
    }

    data class Range(
        val destinationRangeStart: Long,
        val sourceRange: LongRange
    ) {

        fun applyTo(input: Long): Long? =
            if (input in sourceRange) {
                destinationRangeStart + (input - sourceRange.first)
            } else {
                null
            }

        companion object {
            fun fromInput(input: String): Range {
                val (destinationRangeStart, sourceRangeStart, rangeLength) = input.tokenize().map { it.toLong() }
                return Range(
                    destinationRangeStart = destinationRangeStart,
                    sourceRange = sourceRangeStart..sourceRangeStart + rangeLength
                )
            }
        }

    }
}
