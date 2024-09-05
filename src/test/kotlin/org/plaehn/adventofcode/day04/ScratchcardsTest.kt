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

    @Test
    fun `Compute scratch cards for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val scratchcards = Scratchcards.fromInput(lines)

        val cardCount = scratchcards.countScratchCards()

        assertThat(cardCount).isEqualTo(30)
    }

    @Test
    fun `Compute scratch cards for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val scratchcards = Scratchcards.fromInput(lines)

        val cardCount = scratchcards.countScratchCards()

        assertThat(cardCount).isEqualTo(7258152)
    }
}