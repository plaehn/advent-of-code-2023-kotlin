package org.plaehn.adventofcode.day03

data class PartNumber(
    val partNumber: Long,
    val neighbors: Set<Char>
) {
    
    fun hasSymbolNeighbor() = neighbors.any { !it.isDigit() && it != '.' }
}