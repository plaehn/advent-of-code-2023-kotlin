package org.plaehn.adventofcode.day17

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines


class ClumsyCrucibleTest {

    @Test
    fun `Solve part 1 for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val clumsyCrucible = ClumsyCrucible.fromInput(input)

        val heatLoss = clumsyCrucible.solvePart1()

        assertThat(heatLoss).isEqualTo(102)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val clumsyCrucible = ClumsyCrucible.fromInput(input)

        val heatLoss = clumsyCrucible.solvePart1()

        assertThat(heatLoss).isEqualTo(916)
    }

    @Test
    fun `Solve part 2 for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val clumsyCrucible = ClumsyCrucible.fromInput(input)

        val heatLoss = clumsyCrucible.solvePart2()

        assertThat(heatLoss).isEqualTo(94)
    }

    @Test
    fun `Solve part 2 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val clumsyCrucible = ClumsyCrucible.fromInput(input)

        val heatLoss = clumsyCrucible.solvePart2()
        assertThat(heatLoss).isEqualTo(1067)
    }
}