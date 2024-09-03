package org.plaehn.adventofcode.day03

import org.plaehn.adventofcode.common.Matrix


class GearRatios(private val engineSchematic: Matrix<Char>) {

    fun computeSumOfAllPartNumbers(): Long {
        val partNumbers = buildList {
            engineSchematic.rows().forEachIndexed { rowIndex, row ->
                digitRegex
                    .findAll(row.joinToString(""))
                    .forEach { matchResult ->
                        add(
                            PartNumber(
                                partNumber = matchResult.value.toInt(),
                                row = rowIndex,
                                columnRange = matchResult.range
                            )
                        )
                    }
            }
        }
        println(partNumbers)
        return 0
    }

    data class PartNumber(
        val partNumber: Int,
        val row: Int,
        val columnRange: IntRange
    )

    companion object {

        private val digitRegex = "\\d+".toRegex()

        fun fromInput(input: List<String>) =
            GearRatios(Matrix.fromRows(input.map { line -> line.toList() }, '.'))
    }
}
