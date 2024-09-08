package org.plaehn.adventofcode.day08

import org.plaehn.adventofcode.common.cycle
import org.plaehn.adventofcode.common.leastCommonMultiple

class HauntedWasteland(
    val directions: List<Int>,
    val nodes: List<Node>
) {

    private val position2NextPositions = nodes.associate { it.position to it.nextPositions }

    fun countSteps(start: String): Long {
        directions.asSequence().cycle().fold(start to 0L) { (position, stepCount), direction ->
            val newPosition = position2NextPositions[position]!![direction]
            val newStepCount = stepCount + 1

            if (newPosition.endsWith('Z')) return newStepCount

            newPosition to newStepCount
        }
        throw IllegalStateException()
    }

    fun countGhostSteps(): Long =
        nodes
            .filter { it.position.endsWith('A') }
            .map { startNode -> countSteps(startNode.position) }
            .reduce { prev, current -> prev.leastCommonMultiple(current) }

    companion object {
        fun fromInput(lines: List<String>) =
            HauntedWasteland(
                directions = lines.first().map { if (it == 'L') 0 else 1 },
                nodes = lines.drop(1).map { Node.fromInput(it) }
            )
    }

    data class Node(
        val position: String,
        val nextPositions: List<String>
    ) {
        companion object {

            private val inputMatchingRegex = "([A-Z0-9]{3}) = \\(([A-Z0-9]{3}),\\s+([A-Z0-9]{3})\\)".toRegex()

            fun fromInput(input: String): Node {
                val (position, left, right) = inputMatchingRegex.matchEntire(input)!!.groupValues.drop(1)
                return Node(
                    position = position,
                    nextPositions = listOf(left, right)
                )
            }
        }
    }
}
