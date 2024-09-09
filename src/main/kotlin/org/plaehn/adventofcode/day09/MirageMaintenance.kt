package org.plaehn.adventofcode.day09

import org.plaehn.adventofcode.common.tokenize

class MirageMaintenance(val histories: List<List<Int>>) {

    fun computeSumOfExtrapolatedValues(): Int =
        histories.sumOf { history -> history.extrapolateNextValue() }

    private fun List<Int>.extrapolateNextValue(): Int =
        extrapolateNextValueFrom(differenceLists = computeDifferenceLists())

    private fun List<Int>.computeDifferenceLists(): List<List<Int>> =
        mutableListOf(this).apply {
            while (!last().all { it == 0 }) {
                add(last().zipWithNext { prev, next -> next - prev })
            }
        }

    private fun extrapolateNextValueFrom(differenceLists: List<List<Int>>) =
        differenceLists.map { it.last() }.reduce { next, prev -> next + prev }

    companion object {
        fun fromInput(lines: List<String>) =
            MirageMaintenance(
                histories = lines.map { line -> line.tokenize().map { it.toInt() } }
            )
    }
}
