package org.plaehn.adventofcode.day09

import org.plaehn.adventofcode.common.tokenize

class MirageMaintenance(val histories: List<List<Int>>) {

    fun computeSumOfExtrapolatedValues(): Int {
        println(histories)
        return 0
    }

    companion object {
        fun fromInput(lines: List<String>) =
            MirageMaintenance(
                histories = lines.map { line -> line.tokenize().map { it.toInt() } }
            )
    }
}