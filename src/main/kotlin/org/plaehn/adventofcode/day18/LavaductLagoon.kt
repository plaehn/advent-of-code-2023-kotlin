package org.plaehn.adventofcode.day18

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Direction
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.tokenize

class LavaductLagoon(private val digPlan: List<Instruction>) {

    fun solvePart1(): Int =
        digPlan
            .fold(listOf(Coord(0, 0))) { path, instruction ->
                path + (1..instruction.amount).map { path.last() + instruction.direction.offset * it }
            }
            .toGrid()
            .digOutInterior()
            .toMap().count { (_, chr) -> chr == '#' }

    private fun List<Coord>.toGrid(): Pair<Coord, Matrix<Char>> {
        val minX = minOf { it.x }
        val maxX = maxOf { it.x }
        val minY = minOf { it.y }
        val maxY = maxOf { it.y }
        val width = (maxX - minX + 1).toInt()
        val height = (maxY - minY + 1).toInt()
        val offset = Coord(-minX, -minY)
        return offset to Matrix.fromRows(List(height) { List(width) { '.' } }, '.').apply {
            forEach { this[it + offset] = '#' }
        }
    }

    private fun Pair<Coord, Matrix<Char>>.digOutInterior(): Matrix<Char> {
        val (offset, grid) = this
        val queue = ArrayDeque<Coord>()
        queue.add(offset + Coord(1, 1))
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            grid[current] = '#'
            current.neighbors()
                .filter { grid[it] == '.' }
                .forEach { queue.add(it) }
        }
        return grid
    }

    data class Instruction(
        val direction: Direction,
        val amount: Int,
        val color: String
    )

    companion object {
        fun fromInput(input: List<String>) =
            LavaductLagoon(input.map {
                val (direction, amount, color) = it.tokenize()
                Instruction(
                    Direction.fromFirstLetter(direction.first()),
                    amount.toInt(),
                    color.drop(1).dropLast(1)
                )
            })
    }
}

