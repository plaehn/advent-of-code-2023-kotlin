package org.plaehn.adventofcode.day05

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.chunkByBlankLines
import org.plaehn.adventofcode.common.slurp

class SeedLocationMappingTest {

    @Test
    fun `Find lowest location number for test input`() {
        val chunks = readInput("test_input.txt")

        val seedLocationMapping = SeedLocationMapping.fromInput(chunks)

        val lowestLocationNumber = seedLocationMapping.findLowestLocationNumber()

        assertThat(lowestLocationNumber).isEqualTo(35L)
    }

    @Test
    fun `Find lowest location number for puzzle input`() {
        val chunks = readInput("puzzle_input.txt")

        val seedLocationMapping = SeedLocationMapping.fromInput(chunks)

        val lowestLocationNumber = seedLocationMapping.findLowestLocationNumber()

        assertThat(lowestLocationNumber).isEqualTo(389056265L)
    }

    private fun readInput(resource: String): List<List<String>> =
        this::class.java
            .slurp(resource)
            .chunkByBlankLines()
}