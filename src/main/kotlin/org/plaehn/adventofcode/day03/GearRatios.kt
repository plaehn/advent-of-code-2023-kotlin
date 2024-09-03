package org.plaehn.adventofcode.day03

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.product


class GearRatios(private val engineSchematic: Matrix<Char>) {

    fun computeSumOfAllPartNumbers(): Long =
        engineSchematic
            .extractPartNumbers()
            .filter { it.hasSymbolNeighbor() }
            .sumOf { it.partNumber }

    private fun Matrix<Char>.extractPartNumbers() =
        rows()
            .mapIndexed { rowIndex, row ->
                val rowAsString = row.joinToString("")
                rowAsString.extractPartNumbers(rowIndex)
            }
            .flatten()

    private fun String.extractPartNumbers(rowIndex: Int): List<PartNumber> =
        digitRegex
            .findAll(this)
            .map { matchResult -> matchResult.toPartNumber(rowIndex) }
            .toList()

    private fun MatchResult.toPartNumber(rowIndex: Int) =
        PartNumber(
            partNumber = value.toLong(),
            neighbors = collectNeighbors(rowIndex, range)
        )

    private fun collectNeighbors(rowIndex: Int, range: IntRange): Set<Coord> =
        range
            .flatMap { columnIndex ->
                engineSchematic
                    .neighbors(coord = Coord(x = columnIndex, y = rowIndex), includeDiagonals = true)
            }
            .toSet()

    private fun PartNumber.hasSymbolNeighbor() =
        neighbors.any { neighbor ->
            val neighborChar = engineSchematic[neighbor]
            !neighborChar.isDigit() && neighborChar != '.'
        }

    fun computeSumOfGearRatios(): Long {
        val potentialGears2AdjacentPartNumbers: Map<Coord, List<PartNumber>> = engineSchematic
            .extractPartNumbers()
            .flatMap { partNumber ->
                partNumber.neighbors
                    .filter { engineSchematic[it] == '*' }
                    .map { coord ->
                        coord to partNumber
                    }
            }
            .groupBy { it.first }
            .mapValues { it.value.map { pair -> pair.second } }

        return potentialGears2AdjacentPartNumbers
            .filter { (_, partNumbers) -> partNumbers.size == 2 }
            .map { (_, partNumbers) ->
                partNumbers.map { it.partNumber }.product()
            }
            .sum()
    }

    companion object {

        private val digitRegex = "\\d+".toRegex()

        fun fromInput(input: List<String>) =
            GearRatios(Matrix.fromRows(input.map { line -> line.toList() }, '.'))
    }
}
