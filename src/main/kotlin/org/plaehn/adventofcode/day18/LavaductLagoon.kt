package org.plaehn.adventofcode.day18

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Direction
import org.plaehn.adventofcode.common.Coord.Direction.*
import org.plaehn.adventofcode.common.tokenize
import kotlin.math.abs

class LavaductLagoon(private val digPlan: List<Instruction>) {

    // Pick's theorem, cf. https://en.wikipedia.org/wiki/Pick%27s_theorem
    //
    // A = i + b/2 - 1
    // rearranged to
    // i + b = A + b/2 + 1
    // which we compute below
    //
    // A: area
    // i: number of points inside polygon (computed with shoelace algorithm)
    // b: number of points on boundary (perimeter)

    fun solvePart1(): Long =
        digPlan.run { toPolygon().shoelaceArea() + perimeter() / 2 + 1 }

    fun solvePart2(): Long =
        digPlan
            .correctDigPlan()
            .run { toPolygon().shoelaceArea() + perimeter() / 2 + 1 }

    private fun List<Instruction>.correctDigPlan() =
        map { instruction ->
            instruction.copy(
                direction = when (instruction.color.takeLast(1)) {
                    "0" -> RIGHT
                    "1" -> DOWN
                    "2" -> LEFT
                    "3" -> UP
                    else -> throw IllegalArgumentException("Unknown direction")
                },
                amount = instruction.color.take(5).toInt(16)
            )
        }

    private fun List<Instruction>.toPolygon() =
        fold(listOf(Coord(0, 0))) { path, instruction ->
            path + (path.last() + instruction.direction.offset * instruction.amount)
        }

    private fun List<Instruction>.perimeter() = sumOf { it.amount }

    // Cf. https://en.wikipedia.org/wiki/Shoelace_formula
    private fun List<Coord>.shoelaceArea(): Long {
        val n = size
        var a = 0.0
        for (i in 0 until n - 1) {
            a += this[i].x * this[i + 1].y - this[i + 1].x * this[i].y
        }
        return (abs(a + this[n - 1].x * this[0].y - this[0].x * this[n - 1].y) / 2.0).toLong()
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
                    color.drop(2).dropLast(1)
                )
            })
    }
}

