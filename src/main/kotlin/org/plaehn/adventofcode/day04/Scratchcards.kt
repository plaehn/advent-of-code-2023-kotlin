package org.plaehn.adventofcode.day04

import org.plaehn.adventofcode.common.toIntSet
import kotlin.math.pow


class Scratchcards(private val cards: List<Card>) {

    fun computePoints(): Int =
        cards.sumOf { it.computePoints() }

    companion object {
        fun fromInput(input: List<String>) =
            Scratchcards(input.map { Card.fromInput(it) })
    }

    data class Card(
        val winningNumbers: Set<Int>,
        val ownNumbers: Set<Int>
    ) {
        fun computePoints() =
            2.0.pow(countMatchingNumbers() - 1).toInt()

        private fun countMatchingNumbers() =
            ownNumbers.count { it in winningNumbers }

        companion object {
            fun fromInput(input: String): Card {
                val (winningNumbers, ownNumbers) = input.substringAfter(": ").split('|')
                return Card(
                    winningNumbers = winningNumbers.toIntSet(),
                    ownNumbers = ownNumbers.toIntSet()
                )
            }
        }
    }
}

