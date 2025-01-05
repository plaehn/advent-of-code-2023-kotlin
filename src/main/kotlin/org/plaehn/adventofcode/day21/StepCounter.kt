package org.plaehn.adventofcode.day21

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix

class StepCounter(private val grid: Matrix<Char>) {

    fun solvePart1(stepCount: Int): Int =
        countSteps(stepCount).values.count { it % 2 == 0 }

    private fun countSteps(stepCount: Int): Map<Coord, Int> {
        val start = grid.findAll('S').first()
        grid[start] = '.'

        val reachable = mutableMapOf<Coord, Int>()

        val queue = ArrayDeque<Pair<Coord, Int>>()
        queue.add(start to 0)
        while (queue.isNotEmpty()) {
            val (position, distance) = queue.removeFirst()

            if (position in reachable) continue
            if (distance > stepCount) continue
            reachable[position] = distance

            grid.neighbors(position)
                .filter { grid[it] != '#' }
                .filter { it !in reachable }
                .forEach { queue.add(it to distance + 1) }
        }
        return reachable
    }

    // Cf. https://github.com/villuna/aoc23/wiki/A-Geometric-solution-to-advent-of-code-2023,-day-21
    fun solvePart2(stepCount: Int): Long {
        check(grid.width() == grid.height())

        val steps = countSteps(stepCount = grid.width())
        val evenCorners = steps.count { it.value % 2 == 0 && it.value > 65 }.toLong()
        val oddCorners = steps.count { it.value % 2 == 1 && it.value > 65 }.toLong()
        val evenBlock = steps.values.count { it % 2 == 0 }.toLong()
        val oddBlock = steps.values.count { it % 2 == 1 }.toLong()
        val n: Long = ((stepCount.toLong() - (grid.width() / 2)) / grid.width())
        check(n == 202300L)

        val even: Long = n * n
        val odd: Long = (n + 1) * (n + 1)
        return (odd * oddBlock) + (even * evenBlock) - ((n + 1) * oddCorners) + (n * evenCorners)
    }

    companion object {
        fun fromInput(input: List<String>) =
            StepCounter(Matrix.fromRows(input.map { it.toCharArray().toList() }, '.'))
    }
}