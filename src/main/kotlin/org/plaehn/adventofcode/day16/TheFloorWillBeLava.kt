package org.plaehn.adventofcode.day16

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Direction
import org.plaehn.adventofcode.common.Coord.Direction.*
import org.plaehn.adventofcode.common.Matrix

class TheFloorWillBeLava(private val grid: Matrix<Char>) {

    private val moves: Map<Pair<Direction, Char>, Set<Direction>> = mapOf(
        UP to '-' to setOf(LEFT, RIGHT),
        DOWN to '-' to setOf(LEFT, RIGHT),
        LEFT to '|' to setOf(UP, DOWN),
        RIGHT to '|' to setOf(UP, DOWN),
        UP to '/' to setOf(RIGHT),
        DOWN to '/' to setOf(LEFT),
        LEFT to '/' to setOf(DOWN),
        RIGHT to '/' to setOf(UP),
        UP to '\\' to setOf(LEFT),
        DOWN to '\\' to setOf(RIGHT),
        LEFT to '\\' to setOf(UP),
        RIGHT to '\\' to setOf(DOWN)
    )

    fun solvePart1(): Int =
        beam(Front(Coord(0, 0), RIGHT)).size

    fun solvePart2(): Int =
        buildList {
            (0L..<grid.width()).forEach { x ->
                add(Front(Coord(x, 0), DOWN))
                add(Front(Coord(x, grid.height() - 1L), UP))
            }
            (0L..<grid.height()).forEach { y ->
                add(Front(Coord(0, y), RIGHT))
                add(Front(Coord(grid.width() - 1L, y), LEFT))
            }
        }.maxOf { front -> beam(front).size }

    private fun beam(start: Front): Set<Coord> {
        val seen = mutableSetOf<Front>()

        val queue = ArrayDeque<Front>()
        queue.add(start)

        while (queue.isNotEmpty()) {
            val front = queue.removeFirst()
            seen.add(front)

            moves.getOrDefault(front.direction to grid[front.position], setOf(front.direction))
                .map { newDirection -> Front(front.position + newDirection, newDirection) }
                .filter { newFront -> newFront !in seen }
                .filter { newFront -> grid.isInsideBounds(newFront.position) }
                .forEach { newFront -> queue.add(newFront) }
        }

        return seen.map { it.position }.toSet()
    }

    data class Front(
        val position: Coord,
        val direction: Direction
    )

    companion object {
        fun fromInput(input: List<String>) =
            TheFloorWillBeLava(Matrix.fromRows(input.map { it.toCharArray().toList() }, '#'))
    }
}

