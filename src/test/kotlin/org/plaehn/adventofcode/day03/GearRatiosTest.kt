package org.plaehn.adventofcode.day03

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class GearRatiosTest {

    @Test
    fun `Compute sum of all part numbers for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val gearRatios = GearRatios.fromInput(lines)

        val sumOfAllPartNumbers = gearRatios.computeSumOfAllPartNumbers()

        assertThat(sumOfAllPartNumbers).isEqualTo(4361L)
    }

    @Test
    fun `Compute sum of all part numbers for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val gearRatios = GearRatios.fromInput(lines)

        val sumOfAllPartNumbers = gearRatios.computeSumOfAllPartNumbers()

        assertThat(sumOfAllPartNumbers).isEqualTo(538046L)
    }

    @Test
    fun `Compute sum of all gear ratios for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val gearRatios = GearRatios.fromInput(lines)

        val sumOfAllGearRatios = gearRatios.computeSumOfGearRatios()

        assertThat(sumOfAllGearRatios).isEqualTo(467835L)
    }

    @Test
    fun `Compute sum of all gear ratios for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val gearRatios = GearRatios.fromInput(lines)

        val sumOfAllGearRatios = gearRatios.computeSumOfGearRatios()

        assertThat(sumOfAllGearRatios).isEqualTo(0L)
    }
}