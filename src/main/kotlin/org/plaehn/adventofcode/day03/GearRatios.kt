package org.plaehn.adventofcode.day03

import org.plaehn.adventofcode.common.Matrix


class GearRatios(val engineSchematic: Matrix<Char>) {

    fun computeSumOfAllPartNumbers(): Long {
        // TODO
        return 0
    }

    companion object {

        fun fromInput(input: List<String>) =
            GearRatios(Matrix.fromRows(input.map { line -> line.toList() }, '.'))
    }
}
