package org.plaehn.adventofcode.day01

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

class TrebuchetTest {

    @Test
    fun `Compute sum of all calibration values for part one for test input`() {
        val lines = this::class.java.readLines("test_input_part_1.txt")

        val trebuchet = Trebuchet(lines)

        val sum = trebuchet.computeSumOfCalibrationValues()

        assertThat(sum).isEqualTo(142)
    }

    @Test
    fun `Compute sum of all calibration values for part one for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val trebuchet = Trebuchet(lines)

        val sum = trebuchet.computeSumOfCalibrationValues()

        assertThat(sum).isEqualTo(53080)
    }

    @Test
    fun `Compute sum of all calibration values for part two for test input`() {
        val lines = this::class.java.readLines("test_input_part_2.txt")

        val trebuchet = Trebuchet(lines, acceptWordsForDigits = true)

        val sum = trebuchet.computeSumOfCalibrationValues()

        assertThat(sum).isEqualTo(281)
    }

    @Test
    fun `Compute sum of all calibration values for part two for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val trebuchet = Trebuchet(lines, acceptWordsForDigits = true)

        val sum = trebuchet.computeSumOfCalibrationValues()

        assertThat(sum).isEqualTo(53268)
    }
}