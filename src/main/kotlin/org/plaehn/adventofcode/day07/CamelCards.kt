package org.plaehn.adventofcode.day07

import org.plaehn.adventofcode.common.tokenize
import org.plaehn.adventofcode.day07.CamelCards.Card.JOKER
import org.plaehn.adventofcode.day07.CamelCards.Type.*

class CamelCards(val handsWithBids: List<HandWithBid>) {

    fun computeTotalWinnings(): Long =
        handsWithBids
            .sortedBy { it.hand }.also { println(it.joinToString("\n")) }
            .mapIndexed { index, handWithBid -> (index + 1) * handWithBid.bid }
            .sum()

    companion object {
        fun fromInput(lines: List<String>, withJokers: Boolean = false) =
            CamelCards(handsWithBids = lines.map { HandWithBid.fromInput(it, withJokers) })
    }

    data class HandWithBid(
        val hand: Hand,
        val bid: Long
    ) {
        companion object {
            fun fromInput(input: String, withJokers: Boolean): HandWithBid {
                val (hand, bid) = input.tokenize()
                return HandWithBid(
                    hand = Hand.fromInput(hand, withJokers),
                    bid = bid.toLong()
                )
            }
        }
    }

    data class Hand(
        val cards: List<Card>,
        val withJokers: Boolean
    ) : Comparable<Hand> {

        private val type: Type = determineType()

        private fun determineType(): Type {
            val cardWithCount: List<Pair<Card, Int>> =
                cards.groupingBy { it }.eachCount().entries.map { it.key to it.value }.sortedByDescending { it.second }
            return when {
                // 5
                cardWithCount.size == 1 -> FIVE_OF_A_KIND

                // 4, 1
                cardWithCount.size == 2 && cardWithCount[0].second == 4 -> {
                    if (cardWithCount[0].first == JOKER || cardWithCount[1].first == JOKER) {
                        FIVE_OF_A_KIND
                    } else {
                        FOUR_OF_A_KIND
                    }
                }

                // 3, 2
                cardWithCount.size == 2 && cardWithCount[0].second == 3 -> {
                    if (cardWithCount[0].first == JOKER || cardWithCount[1].first == JOKER) {
                        FIVE_OF_A_KIND
                    } else {
                        FULL_HOUSE
                    }
                }

                // 3, 1, 1
                cardWithCount.size == 3 && cardWithCount[0].second == 3 -> {
                    if (cardWithCount.any { it.first == JOKER }) {
                        FOUR_OF_A_KIND
                    } else {
                        THREE_OF_A_KIND
                    }
                }

                // 2, 2, 1
                cardWithCount.size == 3 -> {
                    if (cardWithCount[0].first == JOKER || cardWithCount[1].first == JOKER) {
                        FOUR_OF_A_KIND
                    } else if (cardWithCount[2].first == JOKER) {
                        FULL_HOUSE
                    } else {
                        TWO_PAIR
                    }
                }

                // 2, 1, 1, 1
                cardWithCount.size == 4 -> {
                    if (cardWithCount.any { it.first == JOKER }) {
                        THREE_OF_A_KIND
                    } else {
                        ONE_PAIR
                    }
                }

                // 1, 1, 1, 1, 1
                else -> {
                    if (cardWithCount.any { it.first == JOKER }) {
                        ONE_PAIR
                    } else {
                        HIGH_CARD
                    }
                }
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
            fun fromInput(input: String, withJokers: Boolean) =
                Hand(
                    cards = input.map { Card.fromChar(it, withJokers) },
                    withJokers = withJokers
                )
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
        JOKER('J'),
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
            private val chr2Card = entries.drop(1).associateBy { it.chr }

            fun fromChar(chr: Char, withJokers: Boolean): Card =
                if (withJokers && chr == 'J') {
                    JOKER
                } else {
                    chr2Card[chr] ?: throw IllegalArgumentException("Unknown card $chr")
                }
        }
    }
}