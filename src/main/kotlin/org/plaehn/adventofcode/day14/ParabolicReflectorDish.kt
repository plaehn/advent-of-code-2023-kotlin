package org.plaehn.adventofcode.day14

import org.plaehn.adventofcode.common.Coord.Direction.UP
import org.plaehn.adventofcode.common.Matrix

class ParabolicReflectorDish(private val dish: Matrix<Char>) {

    fun solvePart1(): Long =
        dish.tiltNorth().computeLoad()

    fun solvePart2(): Long {
        val seen = mutableMapOf<Int, Int>()
        var result = dish
        (1..1_000_000_000).forEach { cycleNumber ->
            result = result.cycle()
            val state = result.hashCode()
            if (state in seen) {
                val cycleLength = cycleNumber - seen.getValue(state)
                val cyclesRemaining = (1_000_000_000 - cycleNumber) % cycleLength
                repeat(cyclesRemaining) {
                    result = result.cycle()
                }
                return result.computeLoad()
            } else {
                seen[state] = cycleNumber
            }
        }
        return result.computeLoad()
    }

    private fun Matrix<Char>.cycle(): Matrix<Char> =
        tiltNorth().tiltWest().tiltSouth().tiltEast()

    private fun Matrix<Char>.tiltNorth(): Matrix<Char> =
        apply {
            toMap()
                .filter { (_, chr) -> chr == 'O' }
                .forEach { (coord, _) ->
                    var swapWith = coord
                    while (isInsideBounds(swapWith + UP) && this[swapWith + UP] == '.') {
                        swapWith += UP
                    }
                    this.swap(coord, swapWith)
                }
        }

    private fun Matrix<Char>.tiltWest(): Matrix<Char> =
        flipHorizontally().transpose().tiltNorth().transpose().flipHorizontally()

    private fun Matrix<Char>.tiltSouth(): Matrix<Char> =
        flipHorizontally().tiltNorth().flipHorizontally()

    private fun Matrix<Char>.tiltEast(): Matrix<Char> =
        rotateLeft().tiltNorth().rotateRight()

    private fun Matrix<Char>.computeLoad(): Long =
        toMap()
            .filter { (_, chr) -> chr == 'O' }
            .map { (coord, _) -> dish.height() - coord.y }
            .sum()

    companion object {
        fun fromInput(input: List<String>) =
            ParabolicReflectorDish(
                Matrix.fromRows(input.map { it.toCharArray().toList() }, '.')
            )
    }
}



