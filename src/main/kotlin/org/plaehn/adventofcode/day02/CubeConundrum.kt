package org.plaehn.adventofcode.day02

import org.plaehn.adventofcode.common.tokenize

class CubeConundrum(val games: List<Game>) {

    fun sumOfIdsOfPossibleGames(maxCubeCountPerColor: Map<Color, Int>): Int {
        println(games)
        return 0
    }

    companion object {
        fun fromInput(lines: List<String>): CubeConundrum {
            return CubeConundrum(lines.map { Game.fromInput(it) })
        }
    }

    data class Game(
        val id: Int,
        val rounds: List<Round>
    ) {
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
