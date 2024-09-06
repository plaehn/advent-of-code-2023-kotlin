package org.plaehn.adventofcode.day01

class Trebuchet(
    private val input: List<String>,
    acceptWordsForDigits: Boolean = false
) {

    private val digitStrings = constructDigitStrings(acceptWordsForDigits)

    private fun constructDigitStrings(acceptWordsForDigits: Boolean) =
        ('1'..'9').map { it.toString() }.toSet() +
            if (acceptWordsForDigits) {
                digitWords
            } else {
                emptySet()
            }

    fun computeSumOfCalibrationValues(): Int =
        input.sumOf { it.toCalibrationValue() }

    private fun String.toCalibrationValue(): Int =
        10 * findFirstOccurrence().toIntDigit() + findLastOccurrence().toIntDigit()

    private fun String.findFirstOccurrence() =
        findDigitStringStartingAt(indexOfAny(digitStrings))

    private fun String.findLastOccurrence() =
        findDigitStringStartingAt(lastIndexOfAny(digitStrings))

    private fun String.findDigitStringStartingAt(index: Int): String {
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
