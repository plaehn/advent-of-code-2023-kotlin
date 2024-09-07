package org.plaehn.adventofcode.day07

import org.plaehn.adventofcode.common.tokenize
import org.plaehn.adventofcode.day07.CamelCards.Card.JOKER
import org.plaehn.adventofcode.day07.CamelCards.Type.*

class CamelCards(val handsWithBids: List<HandWithBid>) {

    fun computeTotalWinnings(): Long =
        handsWithBids
            .sortedBy { it.hand }
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
        val cards: List<Card>
    ) : Comparable<Hand> {

        private val jokerCount = cards.count { it == JOKER }
        private val type = determineType()

        private fun determineType(): Type =
            with(cards.toCardBuckets().adaptForJokers()) {
                when {
                    size == 1 -> FIVE_OF_A_KIND
                    size == 2 && first().count == 4 -> FOUR_OF_A_KIND
                    size == 2 && first().count == 3 -> FULL_HOUSE
                    size == 3 && first().count == 3 -> THREE_OF_A_KIND
                    size == 3 -> TWO_PAIR
                    size == 4 -> ONE_PAIR
                    else -> HIGH_CARD
                }
            }

        private fun List<Card>.toCardBuckets() =
            groupingBy { it }
                .eachCount()
                .map { (card, count) -> CardBucket(card, count) }
                .sortedByDescending { it.count }

        private fun List<CardBucket>.adaptForJokers() =
            if (jokerCount == 0 || jokerCount == 5) {
                this
            } else {
                addJokerCountToBiggestBucket()
            }

        private fun List<CardBucket>.addJokerCountToBiggestBucket() =
            filter { it.card != JOKER }
                .mapIndexed { index, cardBucket ->
                    if (index == 0) {
                        cardBucket.copy(count = cardBucket.count + jokerCount)
                    } else {
                        cardBucket
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
                Hand(cards = input.map { Card.fromChar(it, withJokers) })
        }
    }

    data class CardBucket(val card: Card, val count: Int)

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