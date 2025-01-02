package org.plaehn.adventofcode.day19

import org.plaehn.adventofcode.common.chunkByBlankLines
import org.plaehn.adventofcode.common.product
import org.plaehn.adventofcode.common.toInts
import org.plaehn.adventofcode.day19.Aplenty.Workflow.Companion.ACCEPT
import org.plaehn.adventofcode.day19.Aplenty.Workflow.Companion.REJECT

class Aplenty(workflows: List<Workflow>, private val ratings: List<Map<Char, Int>>) {

    private val name2Workflow = workflows.associateBy { it.name } + mapOf("A" to ACCEPT, "R" to REJECT)
    private val acceptedRanges = computeAcceptedRanges()

    fun solvePart1(): Int =
        ratings
            .filter { rating -> acceptedRanges.any { it.accept(rating) } }
            .sumOf { rating -> rating.values.sum() }

    fun solvePart2(): Long =
        acceptedRanges.sumOf { ranges -> ranges.countCombinations() }

    private fun computeAcceptedRanges(): MutableSet<Ranges> {
        val acceptedRanges = mutableSetOf<Ranges>()

        val queue = ArrayDeque<State>()
        queue.add(State("in"))

        while (queue.isNotEmpty()) {
            val state = queue.removeFirst()
            val rule = name2Workflow.getValue(state.workflowName).rules[state.ruleIndex]

            when (rule.operation) {
                'A' -> {
                    acceptedRanges.add(state.ranges)
                }

                'R' -> {}

                '<', '>' -> {
                    val (acceptedRange, notAcceptedRange) = state.ranges.split(rule)
                    queue.add(
                        State(
                            workflowName = rule.sendToWorkflow,
                            ruleIndex = 0,
                            ranges = state.ranges.replace(rule.category, acceptedRange)
                        )
                    )
                    queue.add(
                        State(
                            workflowName = state.workflowName,
                            ruleIndex = state.ruleIndex + 1,
                            ranges = state.ranges.replace(rule.category, notAcceptedRange)
                        )
                    )
                }

                else -> queue.add(
                    State(
                        workflowName = rule.sendToWorkflow,
                        ranges = state.ranges
                    )
                )
            }
        }
        return acceptedRanges
    }

    data class State(
        val workflowName: String,
        val ruleIndex: Int = 0,
        val ranges: Ranges = Ranges()
    )

    data class Ranges(
        val cat2Ranges: Map<Char, IntRange> = mapOf(
            'x' to 1..4000,
            'm' to 1..4000,
            'a' to 1..4000,
            's' to 1..4000
        )
    ) {
        fun split(rule: Rule): Pair<IntRange, IntRange> =
            with(rule) {
                cat2Ranges.getValue(category).run {
                    if (operation == '<') {
                        first()..<amount to amount..last()
                    } else {
                        (amount + 1)..last() to first()..amount
                    }
                }
            }

        fun replace(category: Char, newRange: IntRange) =
            Ranges(cat2Ranges.map { (cat, range) ->
                cat to if (cat == category) newRange else range
            }.toMap())

        fun countCombinations(): Long =
            cat2Ranges.values.map { range -> range.last - range.first + 1L }.product()

        fun accept(rating: Map<Char, Int>): Boolean =
            rating.all { (category, amount) ->
                amount in cat2Ranges.getValue(category)
            }
    }

    data class Workflow(
        val name: String,
        val rules: List<Rule>
    ) {
        companion object {
            val ACCEPT = Workflow("A", listOf(Rule('A', 'A', 0, "A")))
            val REJECT = Workflow("R", listOf(Rule('R', 'R', 0, "R")))

            fun fromInput(input: String) =
                Workflow(
                    name = input.takeWhile { it != '{' },
                    rules = input.dropWhile { it != '{' }.drop(1).dropLast(1).split(",").map { Rule.fromInput(it) })
        }
    }

    data class Rule(
        val category: Char,
        val operation: Char,
        val amount: Int,
        val sendToWorkflow: String
    ) {

        companion object {
            fun fromInput(input: String): Rule =
                when {
                    input.contains('<') -> input.split('<', ':').run {
                        val (category, amount, sendToWorkflow) = this
                        Rule(category.first(), '<', amount.toInt(), sendToWorkflow)
                    }

                    input.contains('>') -> input.split('>', ':').run {
                        val (category, amount, sendToWorkflow) = this
                        Rule(category.first(), '>', amount.toInt(), sendToWorkflow)
                    }

                    else -> Rule(input.first(), input.first(), 0, input)
                }
        }
    }

    companion object {
        fun fromInput(input: String): Aplenty =
            input.chunkByBlankLines().run {
                val (workflows, ratings) = this
                Aplenty(
                    workflows.map { Workflow.fromInput(it) },
                    ratings.map {
                        val (x, m, a, s) = it.toInts()
                        mapOf('x' to x, 'm' to m, 'a' to a, 's' to s)
                    }
                )
            }
    }
}