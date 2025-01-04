package org.plaehn.adventofcode.day20

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines


class PulsePropagationTest {

    @Test
    fun `Solve part 1 for test input 1`() {
        val input = this::class.java.readLines("test_input_1.txt")

        val pulsePropagation = PulsePropagation.fromInput(input)

        val result = pulsePropagation.solvePart1()

        assertThat(result).isEqualTo(32000000)
    }

    @Test
    fun `Solve part 1 for test input 2`() {
        val input = this::class.java.readLines("test_input_2.txt")

        val pulsePropagation = PulsePropagation.fromInput(input)

        val result = pulsePropagation.solvePart1()

        assertThat(result).isEqualTo(11687500)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val pulsePropagation = PulsePropagation.fromInput(input)

        val result = pulsePropagation.solvePart1()

        assertThat(result).isEqualTo(912199500)
    }
}