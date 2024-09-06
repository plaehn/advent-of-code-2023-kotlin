package org.plaehn.adventofcode.day06

import org.plaehn.adventofcode.common.product
import org.plaehn.adventofcode.common.tokenize

class WaitForIt(private val races: List<Race>) {

    fun computeProductOfNumberOfWaysToWin(): Long =
        races
            .map { it.computeNumberOfWaysToWin() }
            .product()


    data class Race(
        val duration: Long,
        val recordDistance: Long
    ) {
        fun computeNumberOfWaysToWin(): Long =
            (1..<duration)
                .map { buttonDuration -> (duration - buttonDuration) * buttonDuration }
                .count { distance -> distance > recordDistance }
                .toLong()
    }

    companion object {
        fun fromInput(lines: List<String>, singleRace: Boolean = false): WaitForIt {
            val durations = lines.first().split(":").last().tokenize().map { it.toLong() }
            val distances = lines.last().split(":").last().tokenize().map { it.toLong() }

            val races = if (singleRace) {
                val singleDuration = durations.joinToString("") { it.toString() }.toLong()
                val singleDistance = distances.joinToString("") { it.toString() }.toLong()
                listOf(Race(singleDuration, singleDistance))
            } else {
                durations.zip(distances).map { (duration, distance) -> Race(duration, distance) }
            }

            return WaitForIt(races)
        }
    }
}
