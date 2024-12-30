package org.plaehn.adventofcode.day14

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines


class ParabolicReflectorDishTest {

    @Test
    fun `Solve part 1 for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val parabolicReflectorDish = ParabolicReflectorDish.fromInput(input)

        val totalLoad = parabolicReflectorDish.solvePart1()

        assertThat(totalLoad).isEqualTo(136)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val parabolicReflectorDish = ParabolicReflectorDish.fromInput(input)

        val totalLoad = parabolicReflectorDish.solvePart1()

        assertThat(totalLoad).isEqualTo(106186)
    }

    @Test
    fun `Solve part 2 for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val parabolicReflectorDish = ParabolicReflectorDish.fromInput(input)

        val totalLoad = parabolicReflectorDish.solvePart2()

        assertThat(totalLoad).isEqualTo(64)
    }

    @Test
    fun `Solve part 2 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val parabolicReflectorDish = ParabolicReflectorDish.fromInput(input)

        val totalLoad = parabolicReflectorDish.solvePart2()

        assertThat(totalLoad).isEqualTo(106390)
    }
}