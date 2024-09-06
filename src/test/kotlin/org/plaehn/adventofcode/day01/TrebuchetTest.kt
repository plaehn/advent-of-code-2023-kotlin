package org.plaehn.adventofcode.day01

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

class TrebuchetTest {

    @Test
    fun `Compute sum of all calibration values for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val trebuchet = Trebuchet(lines)

        val sum = trebuchet.computeSumOfCalibrationValues()

        assertThat(sum).isEqualTo(142)
    }

    @Test
    fun `Compute sum of all calibration values for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val trebuchet = Trebuchet(lines)

        val sum = trebuchet.computeSumOfCalibrationValues()

        assertThat(sum).isEqualTo(53080)
    }
}