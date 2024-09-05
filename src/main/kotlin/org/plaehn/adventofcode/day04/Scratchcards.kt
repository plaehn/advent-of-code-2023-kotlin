package org.plaehn.adventofcode.day04

import org.plaehn.adventofcode.common.toIntSet
import kotlin.math.pow


class Scratchcards(private val cards: List<Card>) {

    fun computePoints(): Int =
        cards.sumOf { it.computePoints() }

    fun countScratchCards(): Int =
        cards
            .map { it.cardNumber }
            .fold(cards) { acc, cardNumber ->
                val currentCard = acc[cardNumber - 1]
                val numbersOfCardsToCopy = (cardNumber + 1)..(cardNumber + currentCard.countMatchingNumbers())
                acc.map { card ->
                    if (card.cardNumber in numbersOfCardsToCopy) {
                        card.copy(numberOfCopies = card.numberOfCopies + currentCard.numberOfCopies)
                    } else {
                        card
                    }
                }
            }
            .sumOf { it.numberOfCopies }

    companion object {
        fun fromInput(input: List<String>) =
            Scratchcards(input.map { Card.fromInput(it) })
    }

    data class Card(
        val cardNumber: Int,
        val winningNumbers: Set<Int>,
        val ownNumbers: Set<Int>,
        val numberOfCopies: Int = 1
    ) {
        fun computePoints() =
            2.0.pow(countMatchingNumbers() - 1).toInt()

        fun countMatchingNumbers() =
            ownNumbers.count { it in winningNumbers }

        companion object {
            fun fromInput(input: String): Card {
                val (cardNumber, numbers) = input.split(":")
                val (winningNumbers, ownNumbers) = numbers.split('|')
                return Card(
                    cardNumber = cardNumber.removePrefix("Card ").trim().toInt(),
                    winningNumbers = winningNumbers.toIntSet(),
                    ownNumbers = ownNumbers.toIntSet()
                )
            }
        }
    }
}

