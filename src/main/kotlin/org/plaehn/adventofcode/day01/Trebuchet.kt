package org.plaehn.adventofcode.day01

class Trebuchet(private val input: List<String>) {

    fun computeSumOfCalibrationValues(acceptWordsForDigits: Boolean = false): Int =
        input.sumOf { it.toCalibrationValue(acceptWordsForDigits) }

    private fun String.toCalibrationValue(acceptWordsForDigits: Boolean): Int {
        val digitStrings = constructDigitStrings(acceptWordsForDigits)
        return 10 * findFirstOccurrence(digitStrings).toIntDigit() + findLastOccurrence(digitStrings).toIntDigit()
    }

    private fun constructDigitStrings(acceptWordsForDigits: Boolean) =
        ('1'..'9').map { it.toString() }.toSet() +
            if (acceptWordsForDigits) {
                digitWords
            } else {
                emptySet()
            }

    private fun String.findFirstOccurrence(digitStrings: Set<String>) =
        findDigitStringStartingAt(indexOfAny(digitStrings), digitStrings)

    private fun String.findLastOccurrence(digitStrings: Set<String>) =
        findDigitStringStartingAt(lastIndexOfAny(digitStrings), digitStrings)

    private fun String.findDigitStringStartingAt(index: Int, digitStrings: Set<String>): String {
        val substringStartingWithDigitString = substring(index)
        return digitStrings.find { digitString ->
            substringStartingWithDigitString.startsWith(digitString)
        } ?: throw IllegalStateException()
    }

    private fun String.toIntDigit() =
        if (first().isDigit()) {
            first().code - '0'.code
        } else {
            1 + digitWords.indexOfFirst { startsWith(it) }
        }

    companion object {
        private val digitWords = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    }
}
