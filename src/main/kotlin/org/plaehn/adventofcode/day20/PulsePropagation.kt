package org.plaehn.adventofcode.day20

import org.plaehn.adventofcode.common.leastCommonMultiple
import org.plaehn.adventofcode.common.product

class PulsePropagation(private val modules: List<Module>) {

    private val name2Module = modules.associateBy { it.name }

    fun solvePart1(): Long {
        val sink = CountHighLowPulses()
        val modulesState = ModulesState.fromModules(modules)

        repeat(1000) {
            pressButton(modulesState, sink)
        }

        return sink.pulseCount.values.product()
    }

    fun solvePart2(): Long {
        val rxInput = modules.find { it.outputs == listOf("rx") }!!
        check(rxInput is Conjunction)
        val conjunctionInputs = rxInput.inputs.toSet()
        val conjunctionInput2ButtonCount = conjunctionInputs.associateWith { 0L }.toMutableMap()

        val sink = RecordHighPulseFor(conjunctionInputs)
        val modulesState = ModulesState.fromModules(modules)

        var buttonCount = 0L
        while (conjunctionInput2ButtonCount.any { it.value == 0L }) {
            buttonCount++

            pressButton(modulesState, sink)

            sink.receivedHighPulse.forEach {
                if (conjunctionInput2ButtonCount[it] == 0L) {
                    conjunctionInput2ButtonCount[it] = buttonCount
                }
            }
            sink.receivedHighPulse.clear()
        }

        return conjunctionInput2ButtonCount.values.reduce(Long::leastCommonMultiple)
    }

    interface StateSink {
        fun receive(state: State)
    }

    class CountHighLowPulses : StateSink {
        val pulseCount = mutableMapOf(false to 0L, true to 0L)

        override fun receive(state: State) {
            pulseCount[state.pulse] = pulseCount[state.pulse]!! + 1
        }
    }

    class RecordHighPulseFor(private val sources: Set<String>) : StateSink {
        val receivedHighPulse = mutableSetOf<String>()

        override fun receive(state: State) {
            if (state.from in sources && state.pulse) receivedHighPulse.add(state.from)
        }
    }

    private fun pressButton(
        modulesState: ModulesState,
        stateSink: StateSink
    ) {
        val queue = ArrayDeque<State>()
        queue.add(State("button", "broadcaster", false))

        while (queue.isNotEmpty()) {
            val state = queue.removeFirst()
            stateSink.receive(state)

            val (prevName, currName, pulse) = state

            val curr = name2Module[currName] ?: continue
            val newPulse = when {
                curr is FlipFlop && pulse -> continue

                curr is FlipFlop -> {
                    val newPulse = !modulesState.flipFlopStates.getValue(currName)
                    modulesState.flipFlopStates[currName] = newPulse
                    newPulse
                }

                curr is Conjunction -> {
                    modulesState.conjunctionStates.getValue(currName)[prevName] = pulse
                    val newPulse = !modulesState.conjunctionStates.getValue(currName).all { it.value }
                    newPulse
                }

                else -> pulse
            }
            curr.outputs.forEach { output ->
                queue.add(State(currName, output, newPulse))
            }
        }
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
                            val outputName = output.dropWhile { it in listOf('%', '&') }
                            name to outputName
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