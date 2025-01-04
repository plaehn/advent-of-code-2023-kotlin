package org.plaehn.adventofcode.day20

import org.plaehn.adventofcode.common.product

class PulsePropagation(private val modules: List<Module>) {

    private val name2Module = modules.associateBy { it.name }

    fun solvePart1(): Long {
        val pulseCount = mutableMapOf(false to 0L, true to 0L)
        val modulesState = ModulesState.fromModules(modules)

        repeat(1000) {
            println(modulesState)
            println(modulesState.hashCode())
            println()

            val queue = ArrayDeque<State>()
            queue.add(State("button", "broadcaster", false))
            while (queue.isNotEmpty()) {
                val (prevName, currName, pulse) = queue.removeFirst()

                println(prevName + " -" + (if (pulse) "high" else "low") + "-> " + currName)

                pulseCount[pulse] = 1 + pulseCount.getOrDefault(pulse, 0L)

                val curr = name2Module[currName] ?: continue

                when {
                    curr is FlipFlop && !pulse -> {
                        val newPulse = !modulesState.flipFlopStates.getValue(currName)
                        modulesState.flipFlopStates[currName] = newPulse
                        curr.outputs.forEach { output ->
                            queue.add(State(currName, output, newPulse))
                        }
                    }

                    curr is FlipFlop -> {}

                    curr is Conjunction -> {
                        modulesState.conjunctionStates.getValue(currName)[prevName] = pulse
                        val newPulse = !modulesState.conjunctionStates.getValue(currName).all { it.value }
                        curr.outputs.forEach { output ->
                            queue.add(State(currName, output, newPulse))
                        }
                    }

                    else -> curr.outputs.forEach { output ->
                        queue.add(State(currName, output, pulse))
                    }
                }
            }
        }
        println(pulseCount)
        return pulseCount.values.product()
    }

    data class State(
        val from: String,
        val to: String,
        val pulse: Boolean
    )

    data class ModulesState(
        val flipFlopStates: MutableMap<String, Boolean> = mutableMapOf(),
        val conjunctionStates: MutableMap<String, MutableMap<String, Boolean>> = mutableMapOf()
    ) {
        companion object {
            fun fromModules(modules: List<Module>): ModulesState =
                ModulesState().apply {
                    modules.forEach { module ->
                        when (module) {
                            is FlipFlop -> flipFlopStates[module.name] = false
                            is Conjunction -> conjunctionStates[module.name] =
                                module.inputs.associateWith { false }.toMutableMap()

                            else -> {}
                        }
                    }
                }
        }
    }

    sealed class Module {
        abstract val name: String
        abstract val outputs: List<String>
    }

    data class Button(
        override val name: String = "button",
        override val outputs: List<String> = listOf("broadcaster")
    ) : Module()

    data class Broadcaster(
        override val name: String = "broadcaster",
        override val outputs: List<String>
    ) : Module()

    data class FlipFlop(
        override val name: String,
        override val outputs: List<String>
    ) : Module()

    data class Conjunction(
        override val name: String,
        override val outputs: List<String>,
        val inputs: List<String>
    ) : Module()

    companion object {
        fun fromInput(input: List<String>) =
            PulsePropagation(input.toModules() + Button())

        private fun List<String>.toModules(): List<Module> {

            val lhs2Outputs = map { module ->
                val (lhs, rhs) = module.split(" -> ")
                val outputs = rhs.split(", ")
                lhs to outputs
            }
            val inputs =
                lhs2Outputs
                    .flatMap { (lhs, outputs) ->
                        outputs.map { output ->
                            val name = lhs.dropWhile { it in listOf('%', '&') }
                            name to output.dropWhile { it in listOf('%', '&') }
                        }
                    }.groupBy { it.second }.map { (key, value) ->
                        key to value.map { it.first }
                    }.toMap()

            return lhs2Outputs
                .map { (lhs, outputs) ->
                    val name = lhs.drop(1)
                    when (lhs.first()) {
                        '%' -> FlipFlop(name, outputs)
                        '&' -> Conjunction(name, outputs, inputs.getValue(name))
                        else -> Broadcaster(outputs = outputs)
                    }
                }
        }
    }
}