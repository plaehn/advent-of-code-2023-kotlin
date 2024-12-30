package org.plaehn.adventofcode.day16

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Direction
import org.plaehn.adventofcode.common.Coord.Direction.*
import org.plaehn.adventofcode.common.Matrix

class TheFloorWillBeLava(private val grid: Matrix<Char>) {

    fun solvePart1(): Int =
        beam(setOf(Front(Coord(0, 0), RIGHT))).size

    // TODO use memoization for both parts
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
        }.maxOf { front -> beam(setOf(front)).size }

    private fun beam(frontier: Set<Front>, seen: MutableSet<Front> = mutableSetOf()): Set<Coord> =
        frontier
            .filter { front -> grid.isInsideBounds(front.position) }
            .filter { front -> front !in seen }
            .also { seen.addAll(it) }
            .flatMap { front -> beam(front, frontier - front, seen) }
            .toSet()

    private fun beam(front: Front, nextFrontier: Set<Front>, seen: MutableSet<Front>): Set<Coord> =
        setOf(front.position) + when (grid.getOrDefault(front.position)) {
            '.' -> beam(nextFrontier + go(front.position, front.direction), seen)
            '/' -> {
                val nextDirection = when (front.direction) {
                    UP, DOWN -> front.direction.turnRight()
                    LEFT, RIGHT -> front.direction.turnLeft()
                }
                beam(nextFrontier + go(front.position, nextDirection), seen)
            }

            '\\' -> {
                val nextDirection = when (front.direction) {
                    UP, DOWN -> front.direction.turnLeft()
                    LEFT, RIGHT -> front.direction.turnRight()
                }
                beam(nextFrontier + go(front.position, nextDirection), seen)
            }

            '|' -> {
                when (front.direction) {
                    UP, DOWN -> beam(nextFrontier + go(front.position, front.direction), seen)
                    LEFT, RIGHT -> beam(
                        nextFrontier
                            + go(front.position, front.direction.turnLeft())
                            + go(front.position, front.direction.turnRight()),
                        seen
                    )
                }
            }

            '-' -> {
                when (front.direction) {
                    LEFT, RIGHT -> beam(nextFrontier + go(front.position, front.direction), seen)
                    UP, DOWN -> beam(
                        nextFrontier
                            + go(front.position, front.direction.turnLeft())
                            + go(front.position, front.direction.turnRight()),
                        seen
                    )
                }
            }

            '#' -> emptySet()

            else -> throw IllegalStateException("Unknown element")
        }

    private fun go(position: Coord, direction: Direction): Front =
        Front(position + direction, direction)

    data class Front(
        val position: Coord,
        val direction: Direction
    )

    companion object {
        fun fromInput(input: List<String>) =
            TheFloorWillBeLava(Matrix.fromRows(input.map { it.toCharArray().toList() }, '#'))
    }
}

