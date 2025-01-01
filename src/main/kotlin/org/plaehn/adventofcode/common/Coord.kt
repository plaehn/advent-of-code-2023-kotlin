package org.plaehn.adventofcode.common

import com.google.common.collect.Sets
import kotlin.math.absoluteValue

data class Coord(val x: Long, val y: Long, val z: Long = 0) {

    override fun toString() = "($x,$y,$z)"

    operator fun plus(summand: Direction) = plus(summand.offset)
    operator fun plus(summand: Coord) = Coord(x + summand.x, y + summand.y, z + summand.z)

    operator fun minus(subtrahend: Direction) = minus(subtrahend.offset)
    operator fun minus(subtrahend: Coord) = Coord(x - subtrahend.x, y - subtrahend.y, z - subtrahend.z)

    operator fun times(factor: Direction): Coord = times(factor.offset)
    operator fun times(factor: Coord): Coord = Coord(x * factor.x, y * factor.y, z * factor.z)
    operator fun times(factor: Int): Coord = Coord(x * factor, y * factor, z * factor)

    fun manhattanDistanceTo(other: Coord) =
        (x - other.x).absoluteValue + (y - other.y).absoluteValue + (z - other.z).absoluteValue

    fun neighbors(includeDiagonals: Boolean = false, dimensions: Int = 2) =
        neighborOffsets(includeDiagonals, dimensions)
            .map { this + it }

    private fun neighborOffsets(includeDiagonals: Boolean, dimensions: Int) =
        Sets.cartesianProduct(List(dimensions) { (-1L..1L).toSet() })
            .map { fromList(it) }
            .filter { !it.isCenter() }
            .filter { offset -> includeDiagonals || 1 == listOf(offset.x, offset.y, offset.z).count { it != 0L } }

    private fun isCenter() = x == 0L && y == 0L && z == 0L

    enum class Direction(val offset: Coord) {
        UP(Coord(0, -1)),
        DOWN(Coord(0, 1)),
        LEFT(Coord(-1, 0)),
        RIGHT(Coord(1, 0));

        fun turnLeft(): Direction =
            when (this) {
                UP -> LEFT
                LEFT -> DOWN
                DOWN -> RIGHT
                RIGHT -> UP
            }

        fun turnRight(): Direction =
            when (this) {
                UP -> RIGHT
                RIGHT -> DOWN
                DOWN -> LEFT
                LEFT -> UP
            }

        companion object {
            fun fromFirstLetter(letter: Char): Direction =
                entries.firstOrNull { it.name.first() == letter }
                    ?: throw IllegalArgumentException("Unknown first letter: $letter")

            fun fromOffset(offset: Coord): Direction =
                entries.firstOrNull { it.offset == offset }
                    ?: throw IllegalArgumentException("Unknown offset: $offset")
        }
    }


    companion object {
        fun fromList(input: List<Long>) =
            Coord(x = input[0], y = input[1], z = input.getOrElse(2) { 0 })
    }
}