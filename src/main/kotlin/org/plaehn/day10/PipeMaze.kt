package org.plaehn.day10

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.day10.PipeMaze.TileType.GROUND
import org.plaehn.day10.PipeMaze.TileType.STARTING_POSITION
import java.util.*

class PipeMaze(private val grid: Matrix<TileType>) {

    fun countSteps(): Int {
        val startCoord = grid.toMap().entries.find { it.value == STARTING_POSITION }!!.key
        val seenCoords = mutableSetOf<Coord>()
        val coords2Distance = mutableMapOf<Coord, Int>()

        val front: Queue<Coord> = LinkedList()
        front.add(startCoord)
        coords2Distance[startCoord] = 0
        while (front.isNotEmpty()) {

            val current = front.poll()

            if (current !in seenCoords) {
                seenCoords.add(current)
                grid.neighbors(current)
                    .filter { neighbor ->
                        grid[current].connectsTo(other = grid[neighbor], offset = neighbor - current)
                    }
                    .filter { neighbor -> neighbor !in seenCoords }
                    .forEach { connectingNeighbor ->
                        front.add(connectingNeighbor)
                        coords2Distance[connectingNeighbor] = 1 + coords2Distance.getOrDefault(current, 0)
                    }
            }
        }

        return coords2Distance.maxOf { it.value }
    }

    companion object {
        fun fromInput(lines: List<String>) =
            PipeMaze(Matrix.fromRows(lines.map { line -> line.map { TileType.fromChar(it) } }, GROUND))
    }

    enum class TileType(
        val chr: Char,
        private val connectingOffsets: List<Coord>
    ) {
        VERTICAL_PIPE('|', listOf(Coord(0, 1), Coord(0, -1))),
        HORIZONTAL_PIPE('-', listOf(Coord(-1, 0), Coord(1, 0))),
        NORTH_EAST_BEND('L', listOf(Coord(0, -1), Coord(1, 0))),
        NORTH_WEST_BEND('J', listOf(Coord(-1, 0), Coord(0, -1))),
        SOUTH_WEST_BEND('7', listOf(Coord(-1, 0), Coord(0, 1))),
        SOUTH_EAST_BEND('F', listOf(Coord(1, 0), Coord(0, 1))),
        GROUND('.', listOf()),
        STARTING_POSITION('S', listOf(Coord(0, -1), Coord(0, 1), Coord(-1, 0), Coord(1, 0)));

        fun connectsTo(other: TileType, offset: Coord) =
            connectingOffsets.contains(offset) && other.connectingOffsets.contains(Coord(-1, -1) * offset)

        companion object {
            private val chr2TileType = entries.associateBy { it.chr }

            fun fromChar(chr: Char): TileType =
                chr2TileType[chr] ?: throw IllegalArgumentException("No such tile type: $chr")
        }
    }
}