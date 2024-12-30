package org.plaehn.adventofcode.day14

import org.plaehn.adventofcode.common.Coord.Companion.UP
import org.plaehn.adventofcode.common.Matrix

class ParabolicReflectorDish(private val dish: Matrix<Char>) {

    fun computeTotalLoad(): Long =
        dish.tiltNorth().computeLoad()

    private fun Matrix<Char>.tiltNorth(): Matrix<Char> =
        apply {
            toMap()
                .filter { (_, chr) -> chr == 'O' }
                .forEach { (coord, _) ->
                    var swapWith = coord
                    while (dish.isInsideBounds(swapWith + UP) && dish[swapWith + UP] == '.') {
                        swapWith += UP
                    }
                    dish.swap(coord, swapWith)
                }
        }

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



