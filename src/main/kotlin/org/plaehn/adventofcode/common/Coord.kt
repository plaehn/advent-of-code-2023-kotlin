package org.plaehn.adventofcode.common

import com.google.common.collect.Sets

data class Coord(val x: Int, val y: Int, val z: Int = 0) {

    override fun toString() = "($x,$y,$z)"

    operator fun plus(summand: Coord) = Coord(x + summand.x, y + summand.y, z + summand.z)

    operator fun minus(subtrahend: Coord) = Coord(x - subtrahend.x, y - subtrahend.y, z - subtrahend.z)

    operator fun times(factor: Coord): Coord = Coord(x * factor.x, y * factor.y, z * factor.z)

    fun neighbors(includeDiagonals: Boolean = false, dimensions: Int = 2) =
        neighborOffsets(includeDiagonals, dimensions)
            .map { this + it }

    private fun neighborOffsets(includeDiagonals: Boolean, dimensions: Int) =
        Sets.cartesianProduct(List(dimensions) { (-1..1).toSet() })
            .map { fromList(it) }
            .filter { !it.isCenter() }
            .filter { offset -> includeDiagonals || 1 == listOf(offset.x, offset.y, offset.z).count { it != 0 } }

    private fun isCenter() = x == 0 && y == 0 && z == 0

    companion object {
        fun fromList(input: List<Int>) =
            Coord(x = input[0], y = input[1], z = input.getOrElse(2) { 0 })
    }
}