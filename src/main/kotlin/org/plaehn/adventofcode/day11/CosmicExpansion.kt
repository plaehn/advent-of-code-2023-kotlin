package org.plaehn.adventofcode.day11

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.combinations

class CosmicExpansion(private val universe: Matrix<Char>) {

    fun sumOfShortestPaths(): Int =
        universe
            .expand()
            .findGalaxyPairs()
            .sumOf { (lhs, rhs) -> lhs.manhattanDistanceTo(rhs) }

    private fun Matrix<Char>.expand(): Matrix<Char> =
        expandRows().transpose().expandRows().transpose()

    private fun Matrix<Char>.expandRows() =
        Matrix.fromRows(
            rows().flatMap { row -> if (row.all { it == '.' }) listOf(row, row) else listOf(row) },
            '.'
        )

    private fun Matrix<Char>.findGalaxyPairs(): List<Pair<Coord, Coord>> =
        toMap()
            .filter { (_, chr) -> chr == '#' }
            .map { it.key }
            .toSet()
            .combinations(ofSize = 2)
            .map { it.first() to it.last() }

    companion object {
        fun fromInput(lines: List<String>) =
            CosmicExpansion(Matrix.fromRows(lines.map { it.toCharArray().toList() }, '.'))
    }
}
