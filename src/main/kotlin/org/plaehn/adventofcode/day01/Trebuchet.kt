package org.plaehn.adventofcode.day01

class Trebuchet(private val input: List<String>) {

    fun computeSumOfCalibrationValues(): Int =
        input.sumOf { it.toCalibrationValue() }

    private fun String.toCalibrationValue(): Int {
        val digits = filter { it.isDigit() }.map { it.code - '0'.code }
        return digits.first() * 10 + digits.last()
    }
}
