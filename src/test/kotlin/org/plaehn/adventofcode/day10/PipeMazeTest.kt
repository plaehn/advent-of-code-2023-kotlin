package org.plaehn.adventofcode.day10

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines
import org.plaehn.day10.PipeMaze

class PipeMazeTest {

    @Test
    fun `Count steps for simple test input`() {
        val lines = this::class.java.readLines("simple_test_input.txt")

        val pipeMaze = PipeMaze.fromInput(lines)

        val numberOfSteps = pipeMaze.countSteps()

        assertThat(numberOfSteps).isEqualTo(4)
    }

    @Test
    fun `Count steps for complex test input`() {
        val lines = this::class.java.readLines("complex_test_input.txt")

        val pipeMaze = PipeMaze.fromInput(lines)

        val numberOfSteps = pipeMaze.countSteps()

        assertThat(numberOfSteps).isEqualTo(8)
    }

    @Test
    fun `Count steps for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val pipeMaze = PipeMaze.fromInput(lines)

        val numberOfSteps = pipeMaze.countSteps()

        assertThat(numberOfSteps).isEqualTo(7093)
    }
}