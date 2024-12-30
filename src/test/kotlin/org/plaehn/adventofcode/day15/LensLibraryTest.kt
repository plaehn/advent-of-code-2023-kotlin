package org.plaehn.adventofcode.day15

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp


class LensLibraryTest {

    @Test
    fun `Solve part 1 for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val lensLibrary = LensLibrary.fromInput(input)

        val sum = lensLibrary.solvePart1()

        assertThat(sum).isEqualTo(1320)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val lensLibrary = LensLibrary.fromInput(input)

        val sum = lensLibrary.solvePart1()

        assertThat(sum).isEqualTo(508498)
    }

    @Test
    fun `Solve part 2 for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val lensLibrary = LensLibrary.fromInput(input)

        val sum = lensLibrary.solvePart2()

        assertThat(sum).isEqualTo(145)
    }

    @Test
    fun `Solve part 2 for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val lensLibrary = LensLibrary.fromInput(input)

        val sum = lensLibrary.solvePart2()

        assertThat(sum).isEqualTo(279116)
    }
}