package org.plaehn.adventofcode.day06

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

class WaitForItTest {

    @Test
    fun `Compute product of number of ways to beat record for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val waitForIt = WaitForIt.fromInput(lines)

        val product = waitForIt.computeProductOfNumberOfWaysToBeatRecord()

        assertThat(product).isEqualTo(288)
    }

    @Test
    fun `Compute product of number of ways to beat record for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val waitForIt = WaitForIt.fromInput(lines)

        val product = waitForIt.computeProductOfNumberOfWaysToBeatRecord()

        assertThat(product).isEqualTo(-1)
    }
}

