package org.plaehn.adventofcode.day11

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines


class CosmicExpansionTest {

    @Test
    fun `Compute sum of shortest paths for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val cosmicExpansion = CosmicExpansion.fromInput(lines)

        val sum = cosmicExpansion.sumOfShortestPaths()

        assertThat(sum).isEqualTo(374)
    }

    @Test
    fun `Compute sum of shortest paths for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val cosmicExpansion = CosmicExpansion.fromInput(lines)

        val sum = cosmicExpansion.sumOfShortestPaths()

        assertThat(sum).isEqualTo(9543156)
    }
}