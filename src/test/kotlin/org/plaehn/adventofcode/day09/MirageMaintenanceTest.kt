package org.plaehn.adventofcode.day09

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

class MirageMaintenanceTest {

    @Test
    fun `Sum extrapolated values for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val mirageMaintenance = MirageMaintenance.fromInput(lines)

        val sumOfExtrapolatedValues = mirageMaintenance.computeSumOfExtrapolatedValues()

        assertThat(sumOfExtrapolatedValues).isEqualTo(114)
    }

    @Test
    fun `Sum extrapolated values for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val mirageMaintenance = MirageMaintenance.fromInput(lines)

        val sumOfExtrapolatedValues = mirageMaintenance.computeSumOfExtrapolatedValues()

        assertThat(sumOfExtrapolatedValues).isEqualTo(2105961943)
    }
}