package org.plaehn.adventofcode.day03

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix


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

    fun computeSumOfGearRatios(): Long =
        engineSchematic
            .findGears()
            .sumOf { it.gearRatio() }

    private fun Matrix<Char>.findGears() =
        extractPartNumbers()
            .flatMap { partNumber ->
                partNumber.neighbors
                    .filter { this@GearRatios.engineSchematic[it] == '*' }
                    .map { coord -> coord to partNumber }
            }
            .groupBy { it.first }
            .filter { (_, partNumbers) -> partNumbers.size == 2 }
            .map { (coord, partNumbers) ->
                Gear(
                    coord = coord,
                    adjacentPartNumbers = partNumbers.map { it.second }.toSet()
                )
            }

    companion object {

        private val digitRegex = "\\d+".toRegex()

        fun fromInput(input: List<String>) =
            GearRatios(Matrix.fromRows(input.map { line -> line.toList() }, '.'))
    }
}
