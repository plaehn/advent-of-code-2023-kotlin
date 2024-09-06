package org.plaehn.adventofcode.day06

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

class WaitForItTest {

    @Test
    fun `Compute product of number of ways to win for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val waitForIt = WaitForIt.fromInput(lines)

        val product = waitForIt.computeProductOfNumberOfWaysToWin()

        assertThat(product).isEqualTo(288)
    }

    @Test
    fun `Compute product of number of ways to win for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val waitForIt = WaitForIt.fromInput(lines)

        val product = waitForIt.computeProductOfNumberOfWaysToWin()

        assertThat(product).isEqualTo(227850)
    }
}

