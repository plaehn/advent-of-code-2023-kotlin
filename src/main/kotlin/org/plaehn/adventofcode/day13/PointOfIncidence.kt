package org.plaehn.adventofcode.day13

import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.chunkByBlankLines
import kotlin.math.min

class PointOfIncidence(private val patterns: List<Matrix<Char>>) {

    fun solvePart1(): Int =
        solve(smudgeCount = 0)

    private fun solve(smudgeCount: Int): Int {
        val rowSum = patterns.sumOf { computeMirrorIndex(it.rows(), smudgeCount) }
        val colSum = patterns.sumOf { computeMirrorIndex(it.columns(), smudgeCount) }
        return 100 * rowSum + colSum
    }

    private fun computeMirrorIndex(lines: List<List<Char>>, smudgeCount: Int) =
        (1..<lines.size).firstOrNull { index -> lines.isMirroredAlong(index, smudgeCount) } ?: 0

    private fun List<List<Char>>.isMirroredAlong(index: Int, smudgeCount: Int): Boolean {
        val above = subList(0, index)
        val below = subList(index, size)
        val overlap = min(above.size, below.size)
        val aboveStr = above.takeLast(overlap).joinToString("") { it.joinToString("") }
        val belowStr = below.take(overlap).reversed().joinToString("") { it.joinToString("") }
        val differenceCount = aboveStr.zip(belowStr).count { (lhs, rhs) -> lhs != rhs }
        return differenceCount == smudgeCount
    }

    fun solvePart2(): Int =
        solve(smudgeCount = 1)

    companion object {
        fun fromInput(input: String) =
            PointOfIncidence(
                input.chunkByBlankLines().map { chunk -> Matrix.fromRows(chunk.map { it.toCharArray().toList() }, '.') }
            )
    }
}



