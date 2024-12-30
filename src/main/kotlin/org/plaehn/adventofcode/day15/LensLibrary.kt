package org.plaehn.adventofcode.day15

class LensLibrary(private val initSequence: List<Step>) {

    fun solvePart1(): Int =
        initSequence.sumOf { it.toString().hash() }

    fun solvePart2(): Int =
        initSequence
            .fold(mutableMapOf<Int, MutableList<Lens>>()) { boxes, step -> step.applyTo(boxes) }
            .computeFocusingPower()

    private fun Step.applyTo(
        boxes: MutableMap<Int, MutableList<Lens>>
    ): MutableMap<Int, MutableList<Lens>> {
        val boxNumber = label.hash()
        val box = boxes.getOrPut(boxNumber) { mutableListOf() }
        when (operation) {
            '-' -> box.removeIf { it.label == label }

            '=' -> box.indexOfFirst { it.label == label }.run {
                val lens = Lens(label, focalLength!!)
                if (this > -1) box[this] = lens else box.add(lens)
            }

            else -> throw IllegalStateException("Illegal step $operation")
        }
        return boxes
    }

    private fun Map<Int, List<Lens>>.computeFocusingPower() =
        map { (boxNumber, lenses) ->
            lenses.mapIndexed { lensNumber, lens ->
                (boxNumber + 1) * (lensNumber + 1) * lens.focalLength
            }.sum()
        }.sum()

    private fun String.hash(): Int =
        fold(0) { hash, chr -> ((hash + chr.code) * 17) % 256 }

    data class Lens(
        val label: String,
        val focalLength: Int
    )

    data class Step(
        val label: String,
        val operation: Char,
        val focalLength: Int? = null
    ) {

        override fun toString(): String =
            label + operation + focalLength?.toString().orEmpty()

        companion object {
            private val regex = "([a-z]+)([=\\-])([0-9]?)".toRegex()

            fun fromInput(input: String): Step {
                val groups = regex.matchEntire(input)?.groupValues
                    ?: throw IllegalArgumentException("Failed to parse: $input")
                return Step(
                    label = groups[1],
                    operation = groups[2].first(),
                    focalLength = if (groups[3].isEmpty()) null else groups[3].toInt()
                )
            }
        }
    }

    companion object {
        fun fromInput(input: String) =
            LensLibrary(input.split(",").map { Step.fromInput(it) })
    }
}