package org.plaehn.adventofcode.day07

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

class CamelCardsTest {

    @Test
    fun `Compute total winnings for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val camelCards = CamelCards.fromInput(lines)

        val totalWinnings = camelCards.computeTotalWinnings()

        assertThat(totalWinnings).isEqualTo(6440L)
    }

    @Test
    fun `Compute total winnings for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val camelCards = CamelCards.fromInput(lines)

        val totalWinnings = camelCards.computeTotalWinnings()

        assertThat(totalWinnings).isEqualTo(246424613L)
    }

    @Test
    fun `Compute total winnings with jokers for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val camelCards = CamelCards.fromInput(lines, withJokers = true)

        val totalWinnings = camelCards.computeTotalWinnings()

        assertThat(totalWinnings).isEqualTo(5905L)
    }

    @Test
    fun `Compute total winnings with jokers for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val camelCards = CamelCards.fromInput(lines, withJokers = true)

        val totalWinnings = camelCards.computeTotalWinnings()

        assertThat(totalWinnings).isEqualTo(248256639)
    }
}