package org.plaehn.adventofcode.day05

import org.plaehn.adventofcode.common.tokenize

class SeedLocationMapping(
    val seeds: List<Int>,
    val mappings: List<Mapping>
) {

    fun findLowestLocationNumber(): Int {
        return 0
    }

    companion object {
        fun fromInput(chunks: List<List<String>>): SeedLocationMapping {
            val seeds = chunks.first().first().split(":").last().tokenize().map { it.toInt() }
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
        companion object {
            fun fromInput(chunk: List<String>) =
                Mapping(
                    ranges = chunk.dropWhile { it.isBlank() || it.contains("map:") }.map { Range.fromInput(it) }
                )
        }
    }

    data class Range(
        val destinationRangeStart: Int,
        val sourceRangeStart: Int,
        val rangeLength: Int
    ) {
        companion object {
            fun fromInput(input: String): Range {
                val (destinationRangeStart, sourceRangeStart, rangeLength) = input.tokenize()
                return Range(
                    destinationRangeStart = destinationRangeStart.toInt(),
                    sourceRangeStart = sourceRangeStart.toInt(),
                    rangeLength = rangeLength.toInt()
                )
            }
        }
    }
}
