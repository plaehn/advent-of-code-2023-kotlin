package org.plaehn.adventofcode.day06

import org.plaehn.adventofcode.common.tokenize

class WaitForIt(private val races: List<Race>) {

    fun computeProductOfNumberOfWaysToBeatRecord(): Int {
        println(races)
        return 0
    }


    data class Race(
        val duration: Int,
        val recordDistance: Int
    )

    companion object {
        fun fromInput(lines: List<String>): WaitForIt {
            val durations = lines.first().split(":").last().tokenize().map { it.toInt() }
            val distances = lines.last().split(":").last().tokenize().map { it.toInt() }
            val races = durations.zip(distances).map { (duration, distance) -> Race(duration, distance) }

            return WaitForIt(races)
        }
    }
}
