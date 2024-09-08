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

        val steps = hauntedWasteland.countSteps(start = "AAA")

        assertThat(steps).isEqualTo(2L)
    }

    @Test
    fun `Count steps for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val hauntedWasteland = HauntedWasteland.fromInput(lines)

        val steps = hauntedWasteland.countSteps(start = "AAA")

        assertThat(steps).isEqualTo(16271L)
    }

    @Test
    fun `Count ghost steps for test input`() {
        val lines = this::class.java.readLines("test_input_part_2.txt")

        val hauntedWasteland = HauntedWasteland.fromInput(lines)

        val steps = hauntedWasteland.countGhostSteps()

        assertThat(steps).isEqualTo(6L)
    }

    @Test
    fun `Count ghost steps for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val hauntedWasteland = HauntedWasteland.fromInput(lines)

        val steps = hauntedWasteland.countGhostSteps()

        assertThat(steps).isEqualTo(14265111103729)
    }
}