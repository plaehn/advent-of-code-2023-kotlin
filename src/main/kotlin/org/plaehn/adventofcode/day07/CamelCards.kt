package org.plaehn.adventofcode.day07

import org.plaehn.adventofcode.common.tokenize

class CamelCards(val handsWithBids: List<HandWithBid>) {

    fun computeTotalWinnings(): Long {
        println(handsWithBids.joinToString("\n"))
        return 0L
    }

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
    ) {
        companion object {
            fun fromInput(input: String) =
                Hand(cards = input.map { Card.fromChar(it) })
        }
    }

    enum class Card(private val chr: Char) {
        ACE('A'),
        KING('K'),
        QUEEN('Q'),
        JACK('J'),
        TEN('T'),
        NINE('9'),
        EIGHT('8'),
        SEVEN('7'),
        SIX('6'),
        FIVE('5'),
        FOUR('4'),
        THREE('3'),
        TWO('2');

        companion object {
            private val chr2Card = entries.associateBy { it.chr }

            fun fromChar(chr: Char): Card =
                chr2Card[chr] ?: throw IllegalArgumentException("Unknown card $chr")
        }
    }
}