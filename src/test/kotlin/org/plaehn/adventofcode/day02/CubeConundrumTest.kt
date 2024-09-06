package org.plaehn.adventofcode.day02

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines
import org.plaehn.adventofcode.day02.CubeConundrum.Color.*

class CubeConundrumTest {

    @Test
    fun `Compute sum of IDs of possible games for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val cubeConundrum = CubeConundrum.fromInput(lines)

        val maxCubeCountPerColor = mapOf(
            RED to 12,
            GREEN to 13,
            BLUE to 14
        )
        val sumOfIdsOfPossibleGames = cubeConundrum.sumOfIdsOfPossibleGames(maxCubeCountPerColor)

        assertThat(sumOfIdsOfPossibleGames).isEqualTo(8)
    }

    @Test
    fun `Compute sum of IDs of possible games for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val cubeConundrum = CubeConundrum.fromInput(lines)

        val maxCubeCountPerColor = mapOf(
            RED to 12,
            GREEN to 13,
            BLUE to 14
        )
        val sumOfIdsOfPossibleGames = cubeConundrum.sumOfIdsOfPossibleGames(maxCubeCountPerColor)

        assertThat(sumOfIdsOfPossibleGames).isEqualTo(2256)
    }

    @Test
    fun `Compute sum of power of cube sets for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val cubeConundrum = CubeConundrum.fromInput(lines)

        val sumOfPowerOfCubeSets = cubeConundrum.computeSumOfPowerOfCubeSets()

        assertThat(sumOfPowerOfCubeSets).isEqualTo(2286)
    }

    @Test
    fun `Compute sum of power of cube sets for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val cubeConundrum = CubeConundrum.fromInput(lines)

        val sumOfPowerOfCubeSets = cubeConundrum.computeSumOfPowerOfCubeSets()

        assertThat(sumOfPowerOfCubeSets).isEqualTo(74229)
    }
}