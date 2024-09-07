package org.plaehn.adventofcode.day07

import org.plaehn.adventofcode.common.tokenize
import org.plaehn.adventofcode.day07.CamelCards.Type.*

class CamelCards(val handsWithBids: List<HandWithBid>) {

    fun computeTotalWinnings(): Long =
        handsWithBids
            .sortedBy { it.hand }
            .mapIndexed { index, handWithBid -> (index + 1) * handWithBid.bid }
            .sum()

    companion object {
        fun fromInput(lines: List<String>) =
            CamelCards(handsWithBids = lines.map { HandWithBid.fromInput(it) })
    }

    data class HandWithBid(
        val hand: Hand,
        val bid: Long
    ) {
        companion object {
            fun fromInput(input: String): HandWithBid {
                val (hand, bid) = input.tokenize()
                return HandWithBid(
                    hand = Hand.fromInput(hand),
                    bid = bid.toLong()
                )
            }
        }
    }

    data class Hand(
        val cards: List<Card>
    ) : Comparable<Hand> {

        private val type: Type = determineType()

        private fun determineType(): Type {
            val sameCardCounts = cards.groupingBy { it }.eachCount().values.sortedDescending()
            return when {
                sameCardCounts.size == 1 -> FIVE_OF_A_KIND
                sameCardCounts.size == 2 && sameCardCounts[0] == 4 -> FOUR_OF_A_KIND
                sameCardCounts.size == 2 && sameCardCounts[0] == 3 -> FULL_HOUSE
                sameCardCounts.size == 3 && sameCardCounts[0] == 3 -> THREE_OF_A_KIND
                sameCardCounts.size == 3 -> TWO_PAIR
                sameCardCounts.size == 4 -> ONE_PAIR
                else -> HIGH_CARD
            }
        }

        override fun toString() =
            "Hand(cards=" + cards.joinToString("") { it.chr.toString() } + ", type=$type)"

        override fun compareTo(other: Hand): Int =
            compareBy<Hand> { it.type }
                .thenBy { it.cards.compareTo(other.cards) }
                .compare(this, other)

        private fun List<Card>.compareTo(otherCards: List<Card>): Int =
            zip(otherCards)
                .map { it.first.compareTo(it.second) }
                .firstOrNull { it != 0 } ?: 0

        companion object {
            fun fromInput(input: String) =
                Hand(cards = input.map { Card.fromChar(it) })
        }
    }

    enum class Type {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND
    }

    enum class Card(val chr: Char) {
        TWO('2'),
        THREE('3'),
        FOUR('4'),
        FIVE('5'),
        SIX('6'),
        SEVEN('7'),
        EIGHT('8'),
        NINE('9'),
        TEN('T'),
        JACK('J'),
        QUEEN('Q'),
        KING('K'),
        ACE('A');

        companion object {
            private val chr2Card = entries.associateBy { it.chr }

            fun fromChar(chr: Char): Card =
                chr2Card[chr] ?: throw IllegalArgumentException("Unknown card $chr")
        }
    }
}