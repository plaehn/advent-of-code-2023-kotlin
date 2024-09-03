package org.plaehn.adventofcode.day03


object RucksackReorganization {

    fun computeSumOfPrioritiesOfItemsToBeRearranged(rucksacks: List<String>) =
        rucksacks
            .map { it.toCompartments() }
            .map { it.mapToCharSets() }
            .map { it.findCommonItem() }
            .sumOf { it.computePriority() }

    private fun String.toCompartments() = chunked(length / 2)

    private fun List<String>.mapToCharSets() = map { it.toSet() }

    private fun List<Set<Char>>.findCommonItem() = reduce { acc, set -> acc.intersect(set) }.first()

    private fun Char.computePriority() = if (isUpperCase()) this - 'A' + 27 else this - 'a' + 1

    fun computeSumOfPrioritiesOfBadges(rucksacks: List<String>) =
        rucksacks
            .chunked(3)
            .map { it.mapToCharSets() }
            .map { it.findCommonItem() }
            .sumOf { it.computePriority() }
}
