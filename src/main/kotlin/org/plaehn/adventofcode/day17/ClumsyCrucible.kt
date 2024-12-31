package org.plaehn.adventofcode.day17

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Direction
import org.plaehn.adventofcode.common.Coord.Direction.RIGHT
import org.plaehn.adventofcode.common.Matrix
import java.util.*

class ClumsyCrucible(private val grid: Matrix<Int>) {

    fun solvePart1(): Int =
        findMinimalHeatLoss(Coord(0, 0), Coord(grid.width() - 1L, grid.height() - 1L))

    private fun findMinimalHeatLoss(start: Coord, end: Coord): Int {
        val stateWrappers = mutableMapOf<State, StateWrapper>()
        val queue = PriorityQueue<StateWrapper>()
        val shortestPathFound = mutableSetOf<State>()

        val startState = State(position = start, direction = RIGHT)
        val startStateWrapper = StateWrapper(startState)

        queue.add(startStateWrapper)
        stateWrappers[startState] = startStateWrapper

        while (queue.isNotEmpty()) {
            val stateWrapper = queue.poll()
            val state = stateWrapper.state
            shortestPathFound.add(state)

            if (state.position == end) return stateWrapper.totalHeatLoss

            state
                .computeNextStates()
                .filter { it !in shortestPathFound }
                .forEach { nextState ->
                    val totalHeatLoss = stateWrapper.totalHeatLoss + grid[nextState.position]
                    var nextStateWrapper: StateWrapper? = stateWrappers[nextState]
                    if (nextStateWrapper == null) {
                        nextStateWrapper = StateWrapper(state, totalHeatLoss, stateWrapper)
                        stateWrappers[nextState] = nextStateWrapper
                        queue.add(nextStateWrapper)
                    } else if (totalHeatLoss < nextStateWrapper.totalHeatLoss) {
                        nextStateWrapper.totalHeatLoss = totalHeatLoss
                        nextStateWrapper.predecessor = stateWrapper
                        queue.remove(nextStateWrapper)
                        queue.add(nextStateWrapper)
                    }
                }
        }

        return Integer.MAX_VALUE
    }

    private fun State.computeNextStates(): List<State> =
        listOf(
            State(position = position + direction, direction = direction, sameDirectionCount = sameDirectionCount + 1),
            State(position = position + direction.turnLeft(), direction = direction.turnLeft()),
            State(position = position + direction.turnRight(), direction = direction.turnRight())
        )
            .filter { grid.isInsideBounds(it.position) }
            .filter { it.sameDirectionCount <= 3 }

    data class State(
        val position: Coord,
        val direction: Direction,
        val sameDirectionCount: Int = 0
    )

    data class StateWrapper(
        val state: State,
        var totalHeatLoss: Int = 0,
        var predecessor: StateWrapper? = null
    ) : Comparable<StateWrapper> {

        override fun compareTo(other: StateWrapper): Int =
            totalHeatLoss.compareTo(other.totalHeatLoss)
    }

    companion object {
        fun fromInput(input: List<String>) =
            ClumsyCrucible(Matrix.fromRows(input.map { row -> row.map { it.digitToInt() } }, -1))
    }
}


