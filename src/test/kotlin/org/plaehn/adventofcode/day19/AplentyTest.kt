package org.plaehn.adventofcode.day19

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp


class AplentyTest {

    @Test
    fun `Solve part 1 for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val aplenty = Aplenty.fromInput(input)

        val result = aplenty.solvePart1()

        assertThat(result).isEqualTo(19114)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val aplenty = Aplenty.fromInput(input)

        val result = aplenty.solvePart1()

        assertThat(result).isEqualTo(362930)
    }

    @Test
    fun `Solve part 2 for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val aplenty = Aplenty.fromInput(input)

        val result = aplenty.solvePart2()

        assertThat(result).isEqualTo(167409079868000)
    }

    @Test
    fun `Solve part 2 for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val aplenty = Aplenty.fromInput(input)

        val result = aplenty.solvePart2()

        assertThat(result).isEqualTo(-1)
    }
}