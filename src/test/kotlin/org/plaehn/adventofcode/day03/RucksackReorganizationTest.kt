package org.plaehn.adventofcode.day03

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class RucksackReorganizationTest {

    @Test
    fun `Compute sum of priorities of items to be rearranged for test input`() {
        val rucksacks = this::class.java.readLines("test_input.txt")

        val sum = RucksackReorganization.computeSumOfPrioritiesOfItemsToBeRearranged(rucksacks)

        assertThat(sum).isEqualTo(157)
    }

    @Test
    fun `Compute sum of priorities of items to be rearranged for puzzle input`() {
        val rucksacks = this::class.java.readLines("puzzle_input.txt")

        val sum = RucksackReorganization.computeSumOfPrioritiesOfItemsToBeRearranged(rucksacks)

        assertThat(sum).isEqualTo(8252)
    }

    @Test
    fun `Compute sum of priorities of badges for test input`() {
        val rucksacks = this::class.java.readLines("test_input.txt")

        val sum = RucksackReorganization.computeSumOfPrioritiesOfBadges(rucksacks)

        assertThat(sum).isEqualTo(70)
    }

    @Test
    fun `Compute sum of priorities of badges for puzzle input`() {
        val rucksacks = this::class.java.readLines("puzzle_input.txt")

        val sum = RucksackReorganization.computeSumOfPrioritiesOfBadges(rucksacks)

        assertThat(sum).isEqualTo(2828)
    }
}