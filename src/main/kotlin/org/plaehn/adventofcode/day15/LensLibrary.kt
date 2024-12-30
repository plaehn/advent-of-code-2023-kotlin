package org.plaehn.adventofcode.day15

class LensLibrary(private val initSequence: List<String>) {

    fun solvePart1(): Int =
        initSequence.sumOf { it.hash() }

    private fun String.hash(): Int {
        var current = 0
        forEach { chr ->
            current += chr.code
            current *= 17
            current %= 256
        }
        return current
    }

    companion object {
        fun fromInput(input: String) =
            LensLibrary(input.split(",").map { it.trim() })
    }
}



