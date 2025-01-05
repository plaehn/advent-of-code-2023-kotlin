package org.plaehn.adventofcode.day21

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines


class StepCounterTest {

    @Test
    fun `Solve part 1 for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val stepCounter = StepCounter.fromInput(input)

        val result = stepCounter.solvePart1(stepCount = 6)

        assertThat(result).isEqualTo(16)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val stepCounter = StepCounter.fromInput(input)

        val result = stepCounter.solvePart1(stepCount = 64)

        assertThat(result).isEqualTo(3689)
    }

    @Test
    fun `Solve part 2 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val stepCounter = StepCounter.fromInput(input)

        val result = stepCounter.solvePart2(stepCount = 26501365)

        // too high: 610158187564402
        assertThat(result).isEqualTo(610158187564402)
    }
}