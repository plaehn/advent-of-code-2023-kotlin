package org.plaehn.adventofcode.day12

import org.plaehn.adventofcode.common.chunked
import org.plaehn.adventofcode.common.tokenize
import kotlin.math.pow

class HotSprings(private val conditionRecords: List<ConditionRecord>) {

    fun solvePart1(): Int =
        conditionRecords.sumOf { it.countArrangements() }

    companion object {
        fun fromInput(lines: List<String>): HotSprings =
            HotSprings(lines.map { ConditionRecord.fromInput(it) })
    }

    data class ConditionRecord(
        val row: String,
        val sizes: List<Int>
    ) {
        fun countArrangements(): Int {
            val unknowns = row.count { it == '?' }
            val arrangements = (0..<2.0.pow(unknowns).toInt()).map { candidate ->
                var remainder = candidate
                row.map { chr ->
                    if (chr == '?') {
                        val newChr = if (remainder % 2 == 0) '#' else '.'
                        remainder = remainder shr 1
                        newChr
                    } else {
                        chr
                    }
                }
            }
            //println(arrangements.joinToString("\n") { it.joinToString("") })
            return arrangements.count { isValid(it) }
        }

        private fun isValid(arrangement: List<Char>): Boolean =
            sizes == arrangement
                .chunked { prev, curr -> prev != curr }
                .filter { it.contains('#') }
                .map { it.size }

        companion object {
            fun fromInput(input: String): ConditionRecord {
                val (row, sizes) = input.tokenize()
                return ConditionRecord(row, sizes.split(",").map { it.toInt() })
            }
        }
    }
}
