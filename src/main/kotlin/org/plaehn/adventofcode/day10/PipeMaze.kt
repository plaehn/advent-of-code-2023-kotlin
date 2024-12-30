package org.plaehn.adventofcode.day10

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Direction
import org.plaehn.adventofcode.common.Coord.Direction.*
import org.plaehn.adventofcode.common.Matrix
import java.util.*

class PipeMaze(private val grid: Matrix<Char>) {

    private val start = grid.toMap().entries.find { it.value == 'S' }!!.key
    private val path = findPath()

    private fun findPath(
        preMove: (Coord, Direction, Direction) -> (Unit) = { _, _, _ -> }
    ): Set<Coord> {
        val pipe = mutableSetOf(start)
        var current = start
            .neighbors()
            .filter { grid.isInsideBounds(it) }
            .first {
                val d = Direction.fromOffset(it - start)
                (grid[it] to d in movements)
            }
        var direction = Direction.fromOffset(current - start)
        while (current != start) {
            pipe += current
            movements[grid[current] to direction]?.let { nextDirection ->
                preMove(current, direction, nextDirection)
                direction = nextDirection
                current += direction
            } ?: error("Invalid movement detected: $current, $direction")
        }
        return pipe
    }

    fun countSteps(): Int =
        path.size / 2

    fun countEnclosedTiles(): Int {
        path.removePipeSymbolsNotPartOfPath()

        val emptyCorner = listOf(
            Coord(0, 0),
            Coord(0L, grid.height() - 1L),
            Coord(grid.width() - 1L, 0),
            Coord(grid.width() - 1L, grid.height() - 1L)
        ).first { grid[it] == '.' }

        findPath { current, direction, nextDirection ->
            floodFill(current + markingDirection.getValue(direction))
            if (grid[current] in setOf('7', 'L', 'J', 'F')) {
                floodFill(current + markingDirection.getValue(nextDirection))
            }
        }
        val lookFor = if (grid[emptyCorner] == 'O') '.' else 'O'
        return grid.toMap().count { (_, tile) -> tile == lookFor }
    }

    private fun Set<Coord>.removePipeSymbolsNotPartOfPath() {
        grid.toMap().keys.forEach { coord ->
            if (coord !in this) {
                grid[coord] = '.'
            }
        }
    }

    private fun floodFill(coord: Coord) {
        if (!grid.isInsideBounds(coord)) return
        val queue = ArrayDeque<Coord>().apply { add(coord) }
        while (queue.isNotEmpty()) {
            val next = queue.removeFirst()
            if (grid.isInsideBounds(next) && grid[next] == '.') {
                grid[next] = 'O'
                queue.addAll(next.neighbors())
            }
        }
    }

    companion object {
        private val markingDirection = mapOf(UP to LEFT, RIGHT to UP, DOWN to RIGHT, LEFT to DOWN)

        private val movements: Map<Pair<Char, Direction>, Direction> =
            mapOf(
                ('|' to DOWN) to DOWN,
                ('|' to UP) to UP,
                ('-' to RIGHT) to RIGHT,
                ('-' to LEFT) to LEFT,
                ('L' to LEFT) to UP,
                ('L' to DOWN) to RIGHT,
                ('J' to DOWN) to LEFT,
                ('J' to RIGHT) to UP,
                ('7' to RIGHT) to DOWN,
                ('7' to UP) to LEFT,
                ('F' to LEFT) to DOWN,
                ('F' to UP) to RIGHT
            )

        fun fromInput(lines: List<String>) =
            PipeMaze(Matrix.fromRows(lines.map { it.toCharArray().toList() }, '.'))
    }
}