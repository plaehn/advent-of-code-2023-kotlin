package org.plaehn.adventofcode.day04

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

class ScratchcardsTest {

    @Test
    fun `Compute points for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val scratchcards = Scratchcards.fromInput(lines)

        val points = scratchcards.computePoints()

        assertThat(points).isEqualTo(13)
    }

    @Test
    fun `Compute points for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val scratchcards = Scratchcards.fromInput(lines)

        val points = scratchcards.computePoints()

        assertThat(points).isEqualTo(24848)
    }
}