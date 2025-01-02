package org.plaehn.adventofcode.day19

import org.plaehn.adventofcode.common.chunkByBlankLines
import org.plaehn.adventofcode.common.product
import org.plaehn.adventofcode.common.toInts

class Aplenty(workflows: List<Workflow>, private val ratings: List<Map<Char, Int>>) {

    private val name2Workflow = workflows.associateBy { it.name }

    fun solvePart1(): Int =
        ratings
            .filter { rating -> workflowsAccept(rating) }
            .sumOf { rating -> rating.values.sum() }

    // TODO reformulate based on Ranges
    private fun workflowsAccept(rating: Map<Char, Int>): Boolean {
        var name = "in"
        while (name !in listOf("A", "R")) {
            name = name2Workflow.getValue(name).applyTo(rating)
        }
        return name == "A"
    }

    private fun Workflow.applyTo(rating: Map<Char, Int>): String =
        rules.firstNotNullOf { rule -> rule.applyTo(rating) }

    fun solvePart2(): Long {
        // workflows and rules form a tree (no workflow is used in more than one rule as RHS (apart from A and R))
        // every non-literal rule partitions the solution space along one dimension
        // tree has A and R as leaves; the latter can be discarded
        // A leaves make up the result

        val acceptedRanges = mutableSetOf<Ranges>()

        val queue = ArrayDeque<State>()
        queue.add(State(name2Workflow.getValue("in")))

        // TODO clean up this mess
        while (queue.isNotEmpty()) {
            val state = queue.removeFirst()
            val rule = state.workflow.rules[state.ruleIndex]
            when (rule.operation) {
                '>' -> {
                    when (rule.sendToWorkflow) {
                        "A" -> {
                            acceptedRanges.add(state.ranges.splitLarger(rule.category, rule.amount))
                            queue.add(
                                state.copy(
                                    ruleIndex = state.ruleIndex + 1,
                                    ranges = state.ranges.splitNotLarger(rule.category, rule.amount)
                                )
                            )
                        }

                        "R" -> {
                            queue.add(
                                state.copy(
                                    ruleIndex = state.ruleIndex + 1,
                                    ranges = state.ranges.splitNotLarger(rule.category, rule.amount)
                                )
                            )
                        }

                        else -> {
                            queue.add(
                                State(
                                    workflow = name2Workflow.getValue(rule.sendToWorkflow),
                                    ruleIndex = 0,
                                    ranges = state.ranges.splitLarger(rule.category, rule.amount)
                                )
                            )
                            queue.add(
                                state.copy(
                                    ruleIndex = state.ruleIndex + 1,
                                    ranges = state.ranges.splitNotLarger(rule.category, rule.amount)
                                )
                            )
                        }
                    }
                }

                '<' -> {
                    when (rule.sendToWorkflow) {
                        "A" -> {
                            acceptedRanges.add(state.ranges.splitSmaller(rule.category, rule.amount))
                            queue.add(
                                state.copy(
                                    ruleIndex = state.ruleIndex + 1,
                                    ranges = state.ranges.splitNotSmaller(rule.category, rule.amount)
                                )
                            )
                        }

                        "R" -> {
                            queue.add(
                                state.copy(
                                    ruleIndex = state.ruleIndex + 1,
                                    ranges = state.ranges.splitNotSmaller(rule.category, rule.amount)
                                )
                            )
                        }

                        else -> {
                            queue.add(
                                State(
                                    workflow = name2Workflow.getValue(rule.sendToWorkflow),
                                    ruleIndex = 0,
                                    ranges = state.ranges.splitSmaller(rule.category, rule.amount)
                                )
                            )
                            queue.add(
                                state.copy(
                                    ruleIndex = state.ruleIndex + 1,
                                    ranges = state.ranges.splitNotSmaller(rule.category, rule.amount)
                                )
                            )
                        }
                    }
                }

                'X' -> queue.add(
                    state.copy(
                        workflow = name2Workflow.getValue(rule.sendToWorkflow),
                        ruleIndex = 0
                    )
                )

                else -> if (rule.sendToWorkflow == "A") {
                    acceptedRanges.add(state.ranges)
                } else if (rule.sendToWorkflow != "R") {
                    queue.add(
                        state.copy(
                            workflow = name2Workflow.getValue(rule.sendToWorkflow),
                            ruleIndex = 0
                        )
                    )
                }

            }
        }

        return acceptedRanges.sumOf { ranges -> ranges.countCombinations() }
    }

    data class State(
        val workflow: Workflow,
        val ruleIndex: Int = 0,
        val ranges: Ranges = Ranges()
    )

    data class Ranges(
        val cat2Ranges: Map<Char, List<IntRange>> = mapOf(
            'x' to listOf(1..4000), // TODO do we need a list or will a single range suffice?
            'm' to listOf(1..4000),
            'a' to listOf(1..4000),
            's' to listOf(1..4000)
        )
    ) {
        fun splitLarger(category: Char, amount: Int): Ranges {
            val splitRanges = cat2Ranges.getValue(category).map { range ->
                if (amount in range) {
                    (amount + 1)..range.last
                } else {
                    range
                }
            }

            return Ranges(cat2Ranges.map { (cat, ranges) ->
                cat to if (cat == category) splitRanges else ranges
            }.toMap())
        }

        fun splitNotLarger(category: Char, amount: Int): Ranges {
            val splitRanges = cat2Ranges.getValue(category).map { range ->
                if (amount in range) {
                    range.first..amount
                } else {
                    range
                }
            }

            return Ranges(cat2Ranges.map { (cat, ranges) ->
                cat to if (cat == category) splitRanges else ranges
            }.toMap())
        }

        fun splitSmaller(category: Char, amount: Int): Ranges {
            val splitRanges = cat2Ranges.getValue(category).map { range ->
                if (amount in range) {
                    range.first..<amount
                } else {
                    range
                }
            }

            return Ranges(cat2Ranges.map { (cat, ranges) ->
                cat to if (cat == category) splitRanges else ranges
            }.toMap())
        }

        fun splitNotSmaller(category: Char, amount: Int): Ranges {
            val splitRanges = cat2Ranges.getValue(category).map { range ->
                if (amount in range) {
                    amount..range.last
                } else {
                    range
                }
            }

            return Ranges(cat2Ranges.map { (cat, ranges) ->
                cat to if (cat == category) splitRanges else ranges
            }.toMap())
        }

        fun countCombinations(): Long =
            cat2Ranges.values.map { ranges -> ranges.sumOf { range -> range.last - range.first + 1L } }.product()
    }

    data class Workflow(
        val name: String,
        val rules: List<Rule>
    ) {
        companion object {
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
        fun applyTo(rating: Map<Char, Int>): String? =
            when (operation) {
                '<' -> if (rating.getValue(category) < amount) sendToWorkflow else null
                '>' -> if (rating.getValue(category) > amount) sendToWorkflow else null
                else -> sendToWorkflow
            }


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

                    else -> Rule('X', 'x', 0, input)
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