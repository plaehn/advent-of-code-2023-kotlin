package org.plaehn.adventofcode.day16

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Companion.DOWN
import org.plaehn.adventofcode.common.Coord.Companion.LEFT
import org.plaehn.adventofcode.common.Coord.Companion.RIGHT
import org.plaehn.adventofcode.common.Coord.Companion.UP
import org.plaehn.adventofcode.common.Matrix

class TheFloorWillBeLava(private val grid: Matrix<Char>) {

    fun solvePart1(): Int =
        beam(setOf(Front(Coord(0, 0), RIGHT))).size

    private fun beam(frontier: Set<Front>, seen: MutableSet<Front> = mutableSetOf()): Set<Coord> {
        val oldFrontier = frontier
            .filter { front -> grid.isInsideBounds(front.position) }
            .filter { front -> front !in seen }
            .toSet()
        if (oldFrontier.isEmpty()) return emptySet() else seen.addAll(oldFrontier)
        return oldFrontier
            .flatMap { front ->
                val nextFrontier = oldFrontier - front
                setOf(front.position) + when (grid.getOrDefault(front.position)) {
                    '.' -> beam(nextFrontier + go(front.position, front.direction), seen)
                    '/' -> {
                        val nextDirection = when (front.direction) {
                            UP, DOWN -> front.direction.turnRight()
                            LEFT, RIGHT -> front.direction.turnLeft()
                            else -> throw IllegalStateException()
                        }
                        beam(nextFrontier + go(front.position, nextDirection), seen)
                    }

                    '\\' -> {
                        val nextDirection = when (front.direction) {
                            UP, DOWN -> front.direction.turnLeft()
                            LEFT, RIGHT -> front.direction.turnRight()
                            else -> throw IllegalStateException()
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

                            else -> throw IllegalStateException()
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

                            else -> throw IllegalStateException()
                        }
                    }

                    '#' -> emptySet()

                    else -> throw IllegalStateException("Unknown element")
                }
            }.toSet()
    }

    private fun go(position: Coord, direction: Coord): Front =
        Front(position + direction, direction)

    private fun Coord.turnLeft(): Coord =
        when (this) {
            UP -> LEFT
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
            else -> throw IllegalStateException("Don't know how to turn left for $this")
        }

    private fun Coord.turnRight(): Coord =
        when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            else -> throw IllegalStateException("Don't know how to turn right for $this")
        }

    data class Front(
        val position: Coord,
        val direction: Coord
    )

    companion object {
        fun fromInput(input: List<String>) =
            TheFloorWillBeLava(Matrix.fromRows(input.map { it.toCharArray().toList() }, '#'))
    }
}

