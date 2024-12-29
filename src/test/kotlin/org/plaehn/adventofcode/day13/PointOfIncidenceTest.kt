package org.plaehn.adventofcode.day13

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp


class PointOfIncidenceTest {

    @Test
    fun `Solve part 1 for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val pointOfIncidence = PointOfIncidence.fromInput(input)

        val sum = pointOfIncidence.solvePart1()

        assertThat(sum).isEqualTo(405)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val pointOfIncidence = PointOfIncidence.fromInput(input)

        val sum = pointOfIncidence.solvePart1()

        assertThat(sum).isEqualTo(34911)
    }
}