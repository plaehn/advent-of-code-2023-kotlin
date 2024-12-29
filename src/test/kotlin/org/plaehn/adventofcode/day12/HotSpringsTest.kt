package org.plaehn.adventofcode.day12

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines


class HotSpringsTest {

    @Test
    fun `Solve part 1 for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val hotSprings = HotSprings.fromInput(lines)

        val sum = hotSprings.solvePart1()

        assertThat(sum).isEqualTo(21)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val hotSprings = HotSprings.fromInput(lines)

        val sum = hotSprings.solvePart1()

        assertThat(sum).isEqualTo(7407)
    }

    @Test
    fun `Solve part 2 for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val hotSprings = HotSprings.fromInput(lines)

        val sum = hotSprings.solvePart2()

        assertThat(sum).isEqualTo(525152)
    }

    @Test
    fun `Solve part 2 for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val hotSprings = HotSprings.fromInput(lines)

        val sum = hotSprings.solvePart2()

        assertThat(sum).isEqualTo(30568243604962)
    }
}