package org.plaehn.adventofcode.day03

import org.plaehn.adventofcode.common.Coord

data class PartNumber(
    val partNumber: Long,
    val neighbors: Set<Coord>
)