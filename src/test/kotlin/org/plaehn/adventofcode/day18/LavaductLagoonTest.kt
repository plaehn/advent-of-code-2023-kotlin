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

    @Test
    fun `Solve part 2 for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val lavaductLagoon = LavaductLagoon.fromInput(input)

        val cubicMeter = lavaductLagoon.solvePart2()

        assertThat(cubicMeter).isEqualTo(952408144115)
    }

    @Test
    fun `Solve part 2 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val lavaductLagoon = LavaductLagoon.fromInput(input)

        val cubicMeter = lavaductLagoon.solvePart2()

        assertThat(cubicMeter).isEqualTo(52885384955882)
    }

}