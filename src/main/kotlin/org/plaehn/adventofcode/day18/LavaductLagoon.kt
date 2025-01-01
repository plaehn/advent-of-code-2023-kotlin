package org.plaehn.adventofcode.day18

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Direction
import org.plaehn.adventofcode.common.tokenize
import kotlin.math.abs

class LavaductLagoon(private val digPlan: List<Instruction>) {

    fun solvePart1(): Int {
        val polygon = digPlan
            .fold(listOf(Coord(0, 0))) { path, instruction ->
                path + (path.last() + instruction.direction.offset * instruction.amount)
            }
        val perimeter = digPlan.sumOf { it.amount }
        // https://en.wikipedia.org/wiki/Pick%27s_theorem
        // A = i + b/2 - 1
        // rearranged to
        // i + b = A + b/2 + 1
        // A: area
        // i: number of points inside polygon (computed with shoelace algorithm)
        // b: number of points on boundary (perimeter)
        return shoelaceArea(polygon).toInt() + perimeter / 2 + 1

    }

    // Cf. https://en.wikipedia.org/wiki/Shoelace_formula
    private fun shoelaceArea(v: List<Coord>): Double {
        val n = v.size
        var a = 0.0
        for (i in 0 until n - 1) {
            a += v[i].x * v[i + 1].y - v[i + 1].x * v[i].y
        }
        return abs(a + v[n - 1].x * v[0].y - v[0].x * v[n - 1].y) / 2.0
    }

    data class Instruction(
        val direction: Direction,
        val amount: Int,
        val color: String
    )

    companion object {
        fun fromInput(input: List<String>) =
            LavaductLagoon(input.map {
                val (direction, amount, color) = it.tokenize()
                Instruction(
                    Direction.fromFirstLetter(direction.first()),
                    amount.toInt(),
                    color.drop(1).dropLast(1)
                )
            })
    }
}

