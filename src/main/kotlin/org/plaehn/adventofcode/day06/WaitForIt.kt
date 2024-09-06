package org.plaehn.adventofcode.day06

import org.plaehn.adventofcode.common.product
import org.plaehn.adventofcode.common.tokenize

class WaitForIt(private val races: List<Race>) {

    fun computeProductOfNumberOfWaysToWin(): Int =
        races
            .map { it.computeNumberOfWaysToWin() }
            .product()


    data class Race(
        val duration: Int,
        val recordDistance: Int
    ) {
        fun computeNumberOfWaysToWin(): Int =
            (1..<duration)
                .map { buttonDuration -> (duration - buttonDuration) * buttonDuration }
                .count { distance -> distance > recordDistance }
    }

    companion object {
        fun fromInput(lines: List<String>): WaitForIt {
            val durations = lines.first().split(":").last().tokenize().map { it.toInt() }
            val distances = lines.last().split(":").last().tokenize().map { it.toInt() }
            val races = durations.zip(distances).map { (duration, distance) -> Race(duration, distance) }

            return WaitForIt(races)
        }
    }
}
