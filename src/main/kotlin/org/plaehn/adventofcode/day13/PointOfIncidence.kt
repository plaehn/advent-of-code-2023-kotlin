package org.plaehn.adventofcode.day13

import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.chunkByBlankLines
import kotlin.math.min

class PointOfIncidence(private val patterns: List<Matrix<Char>>) {

    fun solvePart1(): Int {
        val rowSum = patterns.sumOf { computeMirrorIndex(it.rows()) }
        val colSum = patterns.sumOf { computeMirrorIndex(it.columns()) }
        return 100 * rowSum + colSum
    }

    private fun computeMirrorIndex(lines: List<List<Char>>) =
        (1..<lines.size).firstOrNull { index -> lines.isMirroredAlong(index) } ?: 0

    private fun List<List<Char>>.isMirroredAlong(index: Int): Boolean {
        val above = subList(0, index)
        val below = subList(index, size)
        val overlap = min(above.size, below.size)
        return above.takeLast(overlap) == below.take(overlap).reversed()
    }

    fun solvePart2(): Long {
        return 0
    }

    companion object {
        fun fromInput(input: String) =
            PointOfIncidence(
                input.chunkByBlankLines().map { chunk -> Matrix.fromRows(chunk.map { it.toCharArray().toList() }, '.') }
            )
    }
}



