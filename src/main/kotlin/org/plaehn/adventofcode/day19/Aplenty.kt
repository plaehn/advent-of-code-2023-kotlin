package org.plaehn.adventofcode.day19

import org.plaehn.adventofcode.common.chunkByBlankLines
import org.plaehn.adventofcode.common.toInts

class Aplenty(workflows: List<Workflow>, private val ratings: List<Map<Char, Int>>) {

    private val name2Workflow = workflows.associateBy { it.name }

    fun solvePart1(): Int =
        ratings
            .filter { rating -> workflowsAccept(rating) }
            .sumOf { rating -> rating.values.sum() }

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

        val acceptedRanges = mutableSetOf<Map<Char, List<IntRange>>>()

        val queue = ArrayDeque<State>()
        queue.add(State(name2Workflow.getValue("in")))

        while (queue.isNotEmpty()) {
            val state = queue.removeFirst()
            state.workflow.rules.forEach { rule ->
                when (rule.category) {
                    '>' -> {

                        TODO()
                    }

                    '<' -> TODO()
                    else -> TODO()
                }
            }
        }

        return 0
    }

    data class State(
        val workflow: Workflow,
        val ranges: Map<Char, List<IntRange>> = mapOf(
            'x' to listOf(1..4000),
            'm' to listOf(1..4000),
            'a' to listOf(1..4000),
            's' to listOf(1..4000)
        )
    )

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