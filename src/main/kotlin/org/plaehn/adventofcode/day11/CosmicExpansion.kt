package org.plaehn.adventofcode.day11

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.combinations

class CosmicExpansion(private val universe: Matrix<Char>, expansion: Int) {

    private val rowOffsetMap = universe.rows().computeOffsetMap(expansion)
    private val colOffsetMap = universe.columns().computeOffsetMap(expansion)

    private fun List<List<Char>>.computeOffsetMap(expansion: Int): Map<Int, Long> {
        var sum = 0L
        return map { row -> if (row.all { it == '.' }) expansion - 1 else 0 }
            .mapIndexed { index, offset ->
                sum += offset
                val foo = index to sum
                foo
            }.toMap()
    }

    fun sumOfShortestPaths(): Long =
        universe
            .findGalaxyPairs()
            .expand()
            .sumOf { (lhs, rhs) -> lhs.manhattanDistanceTo(rhs) }

    private fun Matrix<Char>.findGalaxyPairs(): List<Pair<Coord, Coord>> =
        toMap()
            .filter { (_, chr) -> chr == '#' }
            .map { it.key }
            .toSet()
            .combinations(ofSize = 2)
            .map { it.first() to it.last() }

    private fun List<Pair<Coord, Coord>>.expand() =
        map { (lhs, rhs) -> lhs.expand() to rhs.expand() }

    private fun Coord.expand() =
        Coord(
            x + colOffsetMap.getOrDefault(x.toInt(), 0),
            y + rowOffsetMap.getOrDefault(y.toInt(), 0)
        )

    companion object {
        fun fromInput(lines: List<String>, expansion: Int = 2) =
            CosmicExpansion(
                universe = Matrix.fromRows(lines.map { it.toCharArray().toList() }, '.'),
                expansion = expansion
            )
    }
}
