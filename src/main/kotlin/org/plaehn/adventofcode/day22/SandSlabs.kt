package org.plaehn.adventofcode.day22

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.toLongs
import org.plaehn.adventofcode.day22.SandSlabs.Brick.Companion.EMPTY
import kotlin.math.max

class SandSlabs(private val bricks: Set<Brick>) {

    // Puzzle:
    // -------
    // max: (9, 9, 337)
    // min: (0, 0, 1)

    fun solvePart1(): Int {
        val tower = buildTower()
        printTower(tower)
        val fallenBricks = letThemFall(bricks)
        //return fallenBricks.count { brick ->
//            fallenBricks != letThemFall(fallenBricks - brick)
        //      }
        TODO()
    }

    private fun buildTower(): Array<Array<Array<Brick>>> {
        val maxX = bricks.maxOf { max(it.top.x, it.bottom.x) }.toInt()
        val maxY = bricks.maxOf { max(it.top.y, it.bottom.y) }.toInt()
        val maxZ = bricks.maxOf { max(it.top.z, it.bottom.z) }.toInt()
        val tower = Array(size = 1 + maxX) { Array(size = 1 + maxY) { Array(size = 1 + maxZ) { EMPTY } } }
        bricks.forEach { brick ->
            brick.forEach { coord ->
                tower[coord.x.toInt()][coord.y.toInt()][coord.z.toInt()] = brick
            }
        }
        return tower
    }
//
//     x
//    012
//    .G. 9
//    .G. 8
//    ... 7
//    FFF 6
//    ..E 5 z
//    D.. 4
//    CCC 3
//    BBB 2
//    .A. 1
//    --- 0

    private fun printTower(tower: Array<Array<Array<Brick>>>) {
        println()
        printFromXSide(tower)
        println()
        printFromYSide(tower)
    }

    private fun printFromXSide(tower: Array<Array<Array<Brick>>>) {
        println(" x ")
        println("012")
        (9 downTo 1).forEach { z ->
            (0..2).forEach { x ->
                val matchingYBricks = (0..2)
                    .map { y -> tower[x][y][z] }
                    .filter { brick -> brick != EMPTY }
                    .toSet()
                val chr = when {
                    matchingYBricks.size > 1 -> '?'
                    matchingYBricks.size == 1 -> 'A' + bricks.indexOf(matchingYBricks.first())
                    else -> '.'
                }
                print(chr)
            }
            print(" $z")
            if (z == 5) println(" z") else println()
        }
        println("--- 0")
    }

    private fun printFromYSide(tower: Array<Array<Array<Brick>>>) {
        println(" y ")
        println("012")
        (9 downTo 1).forEach { z ->
            (0..2).forEach { y ->
                val matchingXBricks = (0..2)
                    .map { x -> tower[x][y][z] }
                    .filter { brick -> brick != EMPTY }
                    .toSet()
                val chr = when {
                    matchingXBricks.size > 1 -> '?'
                    matchingXBricks.size == 1 -> 'A' + bricks.indexOf(matchingXBricks.first())
                    else -> '.'
                }
                print(chr)
            }
            print(" $z")
            if (z == 5) println(" z") else println()
        }
        println("--- 0")
    }

    private fun letThemFall(bricks: Set<Brick>): Set<Brick> {

        TODO()
    }

    data class Brick(
        val bottom: Coord,
        val top: Coord
    ) : Iterable<Coord> {

        private val direction: Direction

        enum class Direction {
            X, Y, Z
        }

        init {
            require(bottom.x <= top.x)
            require(bottom.y <= top.y)
            require(bottom.z <= top.z)
            direction = when {
                bottom.y == top.y && bottom.z == top.z -> Direction.X
                bottom.x == top.x && bottom.z == top.z -> Direction.Y
                bottom.x == top.x && bottom.y == top.y -> Direction.Z
                else -> throw IllegalStateException("Unknown direction")
            }
        }

        override fun iterator(): Iterator<Coord> =
            when (direction) {
                Direction.X -> (bottom.x..top.x).map { Coord(it, bottom.y, bottom.z) }
                Direction.Y -> (bottom.y..top.y).map { Coord(bottom.x, it, bottom.z) }
                Direction.Z -> (bottom.z..top.z).map { Coord(bottom.x, bottom.y, it) }
            }.iterator()

        companion object {
            val EMPTY = Brick(Coord(0, 0, 0), Coord(0, 0, 0))
        }
    }


    companion object {
        fun fromInput(input: List<String>) =
            SandSlabs(input.map {
                val (lhs, rhs) = it.split("~")
                Brick(
                    bottom = Coord.fromList(lhs.toLongs()),
                    top = Coord.fromList(rhs.toLongs())
                )
            }.toSet())
    }
}
