package org.plaehn.adventofcode.common

data class Matrix<T>(
    private val matrix: List<MutableList<T>>,
    private val defaultValue: T
) {

    operator fun get(coord: Coord) = this[coord.y][coord.x]

    operator fun set(coord: Coord, value: T) {
        this[coord.y][coord.x] = value
    }

    operator fun get(rowNumber: Int): MutableList<T> = matrix[rowNumber]

    fun width() = matrix.first().size

    fun height() = matrix.size

    fun replaceAll(transform: (T) -> T) {
        matrix.forEach { row ->
            row.forEachIndexed { index, value ->
                row[index] = transform(value)
            }
        }
    }

    fun values() = matrix.flatten()

    fun rows(): List<MutableList<T>> = matrix.toList()

    fun columns() = transpose().rows()

    fun toMap(): Map<Coord, T> =
        sequence {
            for (y in 0 until height()) {
                for (x in 0 until width()) {
                    yield(Coord(x, y) to matrix[y][x])
                }
            }
        }.toMap()

    fun neighbors(coord: Coord, includeDiagonals: Boolean = false) =
        coord
            .neighbors(includeDiagonals)
            .filter { isInsideBounds(it) }

    fun isInsideBounds(coord: Coord) = coord.y in 0 until height() && coord.x in 0 until width()

    private fun transpose(): Matrix<T> {
        val transposed = MutableList(width()) { MutableList(height()) { defaultValue } }
        for (i in 0 until height()) {
            for (j in 0 until width()) {
                transposed[j][i] = matrix[i][j]
            }
        }
        return Matrix(transposed, defaultValue)
    }

    override fun toString() = toString(0)

    fun toString(pad: Int = 0) =
        matrix.joinToString(separator = "\n") { row ->
            row.joinToString(separator = "") {
                it.toString().padStart(pad)
            }
        }

    companion object {

        fun <T> fromMap(map: Map<Coord, T>, width: Int, height: Int, defaultValue: T): Matrix<T> {
            val matrix = Matrix(MutableList(height) { MutableList(width) { defaultValue } }, defaultValue)
            map.forEach { (coord, value) ->
                matrix[coord] = value
            }
            return matrix
        }

        fun <T> fromDimensions(width: Int, height: Int, defaultValue: T) =
            Matrix(MutableList(height) { MutableList(width) { defaultValue } }, defaultValue)

        fun <T> fromRows(rows: List<List<T>>, defaultValue: T) =
            Matrix(rows.map {
                it.toMutableList()
            }, defaultValue)
    }
}


