package org.plaehn.adventofcode.day03

import org.plaehn.adventofcode.common.Coord

data class AsteriskCoordWithPartNumbers(
    val coord: Coord,
    val adjacentPartNumbers: Set<PartNumber>
) {

    fun toGear() =
        Gear(
            coord = coord,
            adjacentPartNumbers = adjacentPartNumbers
        )
}
