package org.plaehn.adventofcode.day18

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines


class LavaductLagoonTest {

    @Test
    fun `Solve part 1 for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val lavaductLagoon = LavaductLagoon.fromInput(input)

        val cubicMeter = lavaductLagoon.solvePart1()

        assertThat(cubicMeter).isEqualTo(62)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val lavaductLagoon = LavaductLagoon.fromInput(input)

        val cubicMeter = lavaductLagoon.solvePart1()

        assertThat(cubicMeter).isEqualTo(49578)
    }

}