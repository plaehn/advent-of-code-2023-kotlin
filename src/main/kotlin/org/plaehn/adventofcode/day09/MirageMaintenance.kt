package org.plaehn.adventofcode.day09

import org.plaehn.adventofcode.common.tokenize

class MirageMaintenance(
    val histories: List<List<Int>>,
    val extrapolateBackwards: Boolean
) {

    fun computeSumOfExtrapolatedValues(): Int =
        histories.sumOf { history -> history.extrapolateNextValue() }

    private fun List<Int>.extrapolateNextValue(): Int =
        if (extrapolateBackwards) {
            extrapolateBackwardsNextValueFrom(computeDifferenceLists())
        } else {
            extrapolateNextValueFrom(computeDifferenceLists())
        }

    private fun List<Int>.computeDifferenceLists(): List<List<Int>> =
        mutableListOf(this).apply {
            while (!last().all { it == 0 }) {
                add(last().zipWithNext { prev, next -> next - prev })
            }
        }

    private fun extrapolateNextValueFrom(differenceLists: List<List<Int>>) =
        differenceLists
            .map { it.last() }
            .reduce { next, prev -> next + prev }

    private fun extrapolateBackwardsNextValueFrom(differenceLists: List<List<Int>>) =
        differenceLists
            .map { it.first() }
            .reduceRight { next, prev -> next - prev }

    companion object {
        fun fromInput(lines: List<String>, extrapolateBackwards: Boolean = false) =
            MirageMaintenance(
                histories = lines.map { line -> line.tokenize().map { it.toInt() } },
                extrapolateBackwards = extrapolateBackwards
            )
    }
}
