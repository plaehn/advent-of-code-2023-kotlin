package org.plaehn.adventofcode.day22

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.toLongs
import org.plaehn.adventofcode.day22.SandSlabs.Brick
import org.plaehn.adventofcode.day22.SandSlabs.Brick.Companion.EMPTY
import kotlin.math.max

typealias Tower = Array<Array<Array<Brick>>>

class SandSlabs(private val bricks: Set<Brick>) {

    private val sizeX = 1 + bricks.maxOf { max(it.top.x, it.bottom.x) }.toInt()
    private val sizeY = 1 + bricks.maxOf { max(it.top.y, it.bottom.y) }.toInt()
    private val sizeZ = 1 + bricks.maxOf { max(it.top.z, it.bottom.z) }.toInt()

    private val tower = bricks.toTower()
    private val fallenBricks = tower.run {
        letBricksFallAndCountThem(bricks)
        collectBricks()
    }

    fun solvePart1(): Int =
        fallenBricks
            .count { fallenBrick ->
                fallenBrick.forEach { coord -> tower[coord] = EMPTY }
                val canRemove = fallenBricks
                    .filter { it != fallenBrick }
                    .all { !tower.isFree(it.moveDown()) }
                fallenBrick.forEach { coord -> tower[coord] = fallenBrick }
                canRemove
            }

    fun solvePart2(): Int =
        fallenBricks.sumOf { fallenBrick ->
            val oneRemovedBricks = fallenBricks - setOf(fallenBrick)
            oneRemovedBricks.toTower().letBricksFallAndCountThem(oneRemovedBricks)
        }

    private fun Set<Brick>.toTower(): Tower {
        val tower = Array(sizeX) { Array(sizeY) { Array(sizeZ) { EMPTY } } }
        forEach { brick ->
            brick.forEach { coord -> tower[coord] = brick }
        }
        return tower
    }

    private fun Tower.letBricksFallAndCountThem(bricks: Set<Brick>): Int {
        var fallingBricksCount = 0
        bricks
            .sortedBy { it.bottom.z }
            .forEach { brick ->
                var movedBrick = brick
                while (true) {
                    val new = movedBrick.moveDown()
                    if (!isFree(new)) break
                    movedBrick = new
                }
                if (movedBrick != brick) {
                    fallingBricksCount++
                    brick.forEach { this[it] = EMPTY }
                    movedBrick.forEach { this[it] = movedBrick }
                }
            }
        return fallingBricksCount
    }

    private fun Tower.collectBricks(): Set<Brick> =
        buildSet {
            (0..<sizeX).forEach { x ->
                (0..<sizeY).forEach { y ->
                    (1..<sizeZ).forEach { z ->
                        add(this@collectBricks[x][y][z])
                    }
                }
            }
        }.filter { it != EMPTY }.toSet()

    private operator fun Tower.set(coord: Coord, brick: Brick) {
        this[coord.x.toInt()][coord.y.toInt()][coord.z.toInt()] = brick
    }

    private operator fun Tower.get(coord: Coord): Brick =
        this[coord.x.toInt()][coord.y.toInt()][coord.z.toInt()]

    private fun Tower.isFree(brick: Brick): Boolean =
        brick.bottom.z > 0 && brick.all { this[it].id in setOf(EMPTY.id, brick.id) }

    data class Brick(
        val bottom: Coord,
        val top: Coord,
        val id: Int
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

        fun moveDown() =
            Brick(
                bottom = bottom.copy(z = bottom.z - 1),
                top = top.copy(z = top.z - 1),
                id = id
            )

        override fun iterator(): Iterator<Coord> =
            when (direction) {
                Direction.X -> (bottom.x..top.x).map { Coord(it, bottom.y, bottom.z) }
                Direction.Y -> (bottom.y..top.y).map { Coord(bottom.x, it, bottom.z) }
                Direction.Z -> (bottom.z..top.z).map { Coord(bottom.x, bottom.y, it) }
            }.iterator()

        companion object {
            val EMPTY = Brick(Coord(0, 0, 0), Coord(0, 0, 0), -1)
        }
    }

    companion object {
        fun fromInput(input: List<String>) =
            SandSlabs(input.mapIndexed { index, line ->
                val (lhs, rhs) = line.split("~")
                Brick(
                    bottom = Coord.fromList(lhs.toLongs()),
                    top = Coord.fromList(rhs.toLongs()),
                    id = index
                )
            }.toSet())
    }
}
