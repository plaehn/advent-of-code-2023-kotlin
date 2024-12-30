package org.plaehn.adventofcode.day16

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines


class TheFloorWillBeLavaTest {

    @Test
    fun `Solve part 1 for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val theFloorWillBeLava = TheFloorWillBeLava.fromInput(input)

        val count = theFloorWillBeLava.solvePart1()

        assertThat(count).isEqualTo(46)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val theFloorWillBeLava = TheFloorWillBeLava.fromInput(input)

        val count = theFloorWillBeLava.solvePart1()

        assertThat(count).isEqualTo(6994)
    }

    @Test
    fun `Solve part 2 for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val theFloorWillBeLava = TheFloorWillBeLava.fromInput(input)

        val count = theFloorWillBeLava.solvePart2()

        assertThat(count).isEqualTo(51)
    }

    // TODO takes 2 min 37 sec
    @Test
    @Disabled
    fun `Solve part 2 for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val theFloorWillBeLava = TheFloorWillBeLava.fromInput(input)

        val count = theFloorWillBeLava.solvePart2()

        assertThat(count).isEqualTo(7488)
    }
}