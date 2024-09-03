package org.plaehn.adventofcode.day03

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.product

data class Gear(
    val coord: Coord,
    val adjacentPartNumbers: Set<PartNumber>
) {

    init {
        require(adjacentPartNumbers.size == 2)
    }

    fun gearRatio(): Long =
        adjacentPartNumbers.map { it.partNumber }.product()
}