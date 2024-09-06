package org.plaehn.adventofcode.day02

import org.plaehn.adventofcode.common.product
import org.plaehn.adventofcode.common.tokenize

class CubeConundrum(private val games: List<Game>) {

    fun sumOfIdsOfPossibleGames(maxCubeCountPerColor: Map<Color, Int>): Int =
        games
            .filter { it.isPossible(maxCubeCountPerColor) }
            .sumOf { it.id }

    fun computeSumOfPowerOfCubeSets(): Int =
        games
            .sumOf { it.computePowerOfCubeSets() }

    companion object {
        fun fromInput(lines: List<String>) =
            CubeConundrum(lines.map { Game.fromInput(it) })
    }

    data class Game(
        val id: Int,
        val rounds: List<Round>
    ) {
        fun isPossible(maxCubeCountPerColor: Map<Color, Int>) =
            rounds.all { it.isPossible(maxCubeCountPerColor) }

        fun computePowerOfCubeSets(): Int =
            Color.entries
                .map { getMaxCountForColor(it) }
                .product()

        private fun getMaxCountForColor(color: Color) =
            rounds.maxOf { it.shownCubeCountPerColor.getOrDefault(color, 0) }

        companion object {
            fun fromInput(input: String): Game {
                val (gameId, rounds) = input.split(":")
                return Game(
                    id = gameId.filter { it.isDigit() }.toInt(),
                    rounds = rounds.split(";").map { Round.fromInput(it) }
                )
            }
        }
    }

    data class Round(
        val shownCubeCountPerColor: Map<Color, Int>
    ) {
        fun isPossible(maxCubeCountPerColor: Map<Color, Int>) =
            shownCubeCountPerColor.all { (color, count) ->
                count <= maxCubeCountPerColor.getOrDefault(color, 0)
            }

        companion object {
            fun fromInput(input: String) =
                Round(shownCubeCountPerColor = input
                    .split(",")
                    .associate {
                        val (count, color) = it.tokenize()
                        Color.valueOf(color.trim().uppercase()) to count.trim().toInt()
                    }
                )
        }
    }

    enum class Color {
        RED, GREEN, BLUE
    }
}
