package org.plaehn.adventofcode.day05

import org.plaehn.adventofcode.common.tokenize

class SeedLocationMapping(
    val seeds: List<Long>,
    val inputMappings: List<Mapping>
) {

    fun findLowestLocationNumber(): Long =
        seeds.minOfOrNull { it.applyMappings(inputMappings) } ?: throw IllegalStateException()

    private fun Long.applyMappings(mappings: List<Mapping>) =
        mappings.fold(this) { acc, mapping ->
            mapping.applyTo(acc)
        }

    fun findLowestLocationNumberWithSeedPairs(): Long {
        val seedRanges = seeds.chunked(2) { (start, length) ->
            start..<start + length
        }

        val reversedMappings = inputMappings.reversed().map { it.reversed() }

        return (0..Long.MAX_VALUE).first { location ->
            val seed = location.applyMappings(reversedMappings)
            seedRanges.any { it.contains(seed) }
        }
    }


    companion object {
        fun fromInput(chunks: List<List<String>>): SeedLocationMapping {
            val seeds = chunks.first().first().split(":").last().tokenize().map { it.toLong() }
            val mappings = chunks.drop(1).map { Mapping.fromInput(it) }

            return SeedLocationMapping(
                seeds = seeds,
                inputMappings = mappings
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

        fun reversed(): Mapping =
            Mapping(ranges.map { it.reversed() })

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

        fun reversed() =
            Range(
                destinationRangeStart = sourceRange.first,
                sourceRange = destinationRangeStart..<destinationRangeStart + sourceRange.size()
            )

        private fun LongRange.size() = endInclusive - start

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