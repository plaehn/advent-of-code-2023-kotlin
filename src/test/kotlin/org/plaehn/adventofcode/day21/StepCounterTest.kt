package org.plaehn.adventofcode.day21

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Disabled
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
    @Disabled
    fun `Solve part 2 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val stepCounter = StepCounter.fromInput(input)

        val result = stepCounter.solvePart2(stepCount = 26501365)

        // got correct answer by running this implementation on my puzzle input:
        // https://github.com/ericwburden/advent_of_code_2023
        assertThat(result).isEqualTo(610158187362102)
    }
}