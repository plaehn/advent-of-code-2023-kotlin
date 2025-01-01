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
        return 0
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
        val filter: (Int, Int) -> Boolean,
        val amount: Int,
        val sendToWorkflow: String
    ) {
        fun applyTo(rating: Map<Char, Int>): String? =
            if (filter(rating.getOrDefault(category, 0), amount)) {
                sendToWorkflow
            } else {
                null
            }

        companion object {
            fun fromInput(input: String): Rule =
                when {
                    input.contains('<') -> input.split('<', ':').run {
                        val (category, amount, sendToWorkflow) = this
                        Rule(category.first(), { lhs, rhs -> lhs < rhs }, amount.toInt(), sendToWorkflow)
                    }

                    input.contains('>') -> input.split('>', ':').run {
                        val (category, amount, sendToWorkflow) = this
                        Rule(category.first(), { lhs, rhs -> lhs > rhs }, amount.toInt(), sendToWorkflow)
                    }

                    else -> Rule('X', { _, _ -> true }, 0, input)
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