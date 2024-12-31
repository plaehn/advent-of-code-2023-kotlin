package org.plaehn.adventofcode.day17

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Direction
import org.plaehn.adventofcode.common.Coord.Direction.RIGHT
import org.plaehn.adventofcode.common.Matrix
import java.util.*

class ClumsyCrucible(private val grid: Matrix<Char>) {

    fun solvePart1(): Int =
        findMinimalHeatLoss(
            start = Coord(0, 0),
            end = Coord(grid.width() - 1L, grid.height() - 1L),
            filter = { _, next -> next.sameDirectionCount < 3 },
            minimalSameDirectionCountAtEnd = 0
        )

    fun solvePart2(): Int =
        findMinimalHeatLoss(
            start = Coord(0, 0),
            end = Coord(grid.width() - 1L, grid.height() - 1L),
            filter = { current, next ->
                (next.direction == current.direction && current.sameDirectionCount < 9) ||
                    (next.direction != current.direction && current.sameDirectionCount >= 3)
            },
            minimalSameDirectionCountAtEnd = 4
        )

    private fun findMinimalHeatLoss(
        start: Coord,
        end: Coord,
        filter: (State, State) -> Boolean,
        minimalSameDirectionCountAtEnd: Int
    ): Int {
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

            if (state.position == end && state.sameDirectionCount >= minimalSameDirectionCountAtEnd) {
                return stateWrapper.totalHeatLoss
            }

            state
                .computeNextStates(filter)
                .filter { it !in shortestPathFound }
                .forEach { nextState ->
                    val totalHeatLoss = stateWrapper.totalHeatLoss + grid[nextState.position].digitToInt()
                    var nextStateWrapper: StateWrapper? = stateWrappers[nextState]
                    if (nextStateWrapper == null) {
                        nextStateWrapper = StateWrapper(nextState, totalHeatLoss, stateWrapper)
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

    private fun State.computeNextStates(filter: (State, State) -> Boolean): List<State> =
        listOf(
            State(position = position + direction, direction = direction, sameDirectionCount = sameDirectionCount + 1),
            State(position = position + direction.turnLeft(), direction = direction.turnLeft()),
            State(position = position + direction.turnRight(), direction = direction.turnRight())
        )
            .filter { grid.isInsideBounds(it.position) }
            .filter { filter(this, it) }

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
            ClumsyCrucible(Matrix.fromRows(input.map { row -> row.toCharArray().toList() }, '.'))
    }
}