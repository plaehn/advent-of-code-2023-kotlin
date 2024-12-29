package org.plaehn.adventofcode.day12

import org.plaehn.adventofcode.common.tokenize

class HotSprings(private val conditionRecords: List<ConditionRecord>) {

    fun solvePart1(): Long =
        conditionRecords.sumOf { it.countArrangements() }

    companion object {
        fun fromInput(lines: List<String>): HotSprings =
            HotSprings(lines.map { ConditionRecord.fromInput(it) })
    }

    data class ConditionRecord(
        val row: String,
        val brokenGroupLengths: List<Int>
    ) {

        fun countArrangements(): Long {
            // cache[i][j]: if not -1 then equals number of arrangements
            // for row.substring(i) and sizes.subList(j to end)
            val cache = Array(row.length) { LongArray(brokenGroupLengths.size + 1) { -1 } }

            // from inclusive, to exclusive
            fun brokenGroupPossible(from: Int, to: Int) =
                when {
                    // not enough springs remaining
                    to > row.length -> false

                    // all in range must not be marked as working
                    to == row.length -> (from until to).all { row[it] != '.' }

                    // all in range must not be marked as working
                    // and the following spring must not be marked as broken
                    else -> (from until to).all { row[it] != '.' } && row[to] != '#'
                }

            fun compute(i: Int, j: Int): Long {
                if (i == row.length) return if (j == brokenGroupLengths.size) 1 else 0

                if (cache[i][j] != -1L) return cache[i][j]

                fun computeWorking(): Long =
                    compute(i + 1, j)

                fun computeBroken(): Long {
                    if (j == brokenGroupLengths.size) return 0

                    // index of the end of the group, exclusive
                    val endGroupIdx = i + brokenGroupLengths[j]

                    if (!brokenGroupPossible(i, endGroupIdx)) return 0

                    if (endGroupIdx == row.length) return if (j == brokenGroupLengths.size - 1) 1 else 0

                    // set i to position after end of this group, including the working spring
                    // that ends this group, and increment j
                    return compute(endGroupIdx + 1, j + 1)
                }

                return when (val c = row[i]) {
                    '.' -> computeWorking()
                    '#' -> computeBroken()
                    '?' -> computeWorking() + computeBroken()
                    else -> throw IllegalStateException("Illegal char: $c")
                }.also { cache[i][j] = it }
            }

            return compute(0, 0)
        }

        companion object {
            fun fromInput(input: String): ConditionRecord {
                val (row, sizes) = input.tokenize()
                return ConditionRecord(row, sizes.split(",").map { it.toInt() })
            }
        }
    }
}
