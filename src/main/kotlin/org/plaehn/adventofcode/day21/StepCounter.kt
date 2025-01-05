package org.plaehn.adventofcode.day21

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix

class StepCounter(private val grid: Matrix<Char>) {

    fun solvePart1(steps: Int): Int {
        val start = grid.findAll('S').first()
        grid[start] = '.'

        val reachable = mutableSetOf<Coord>()

        val queue = ArrayDeque<State>()
        queue.add(State(start, 0))
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            if (current.position in reachable) continue
            if (current.distance > steps) continue
            if (current.distance % 2 == 0) reachable.add(current.position)

            grid.neighbors(current.position)
                .filter { grid[it] != '#' }
                .forEach { queue.add(State(it, current.distance + 1)) }
        }

        return reachable.size
    }

    data class State(
        val position: Coord,
        val distance: Int
    )

    companion object {
        fun fromInput(input: List<String>) =
            StepCounter(Matrix.fromRows(input.map { it.toCharArray().toList() }, '.'))
    }
}