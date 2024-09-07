package org.plaehn.adventofcode.day08

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

class HauntedWastelandTest {

    @Test
    fun `Count steps for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val hauntedWasteland = HauntedWasteland.fromInput(lines)

        val steps = hauntedWasteland.countSteps()

        assertThat(steps).isEqualTo(2)
    }

    @Test
    fun `Count steps for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val hauntedWasteland = HauntedWasteland.fromInput(lines)

        val steps = hauntedWasteland.countSteps()

        assertThat(steps).isEqualTo(16271)
    }
}