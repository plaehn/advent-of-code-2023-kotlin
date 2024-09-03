package org.plaehn.adventofcode.day03

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.product

data class AsteriskWithAdjacentPartNumbers(
    val coord: Coord,
    val adjacentPartNumbers: Set<PartNumber>
) {

    fun isGear() = adjacentPartNumbers.size == 2

    fun gearRatio(): Long =
        adjacentPartNumbers.map { it.partNumber }.product()
}