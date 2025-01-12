package org.plaehn.adventofcode.day22

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines


class SandSlabsTest {

    @Test
    fun `Solve part 1 for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val sandSlabs = SandSlabs.fromInput(input)

        val result = sandSlabs.solvePart1()

        assertThat(result).isEqualTo(5)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val sandSlabs = SandSlabs.fromInput(input)

        val result = sandSlabs.solvePart1()

        assertThat(result).isEqualTo(-1)
    }
}