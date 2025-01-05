package org.plaehn.adventofcode.common

data class Matrix<T>(
    private val matrix: List<MutableList<T>>,
    private val defaultValue: T
) {

    operator fun get(coord: Coord) = this[coord.y.toInt()][coord.x.toInt()]

    fun getOrDefault(coord: Coord) =
        if (isInsideBounds(coord)) this[coord.y.toInt()][coord.x.toInt()] else defaultValue

    operator fun set(coord: Coord, value: T) {
        this[coord.y.toInt()][coord.x.toInt()] = value
    }

    operator fun get(rowNumber: Int): MutableList<T> = matrix[rowNumber]

    fun values() = matrix.flatten()

    fun rows(): List<MutableList<T>> = matrix.toList()

    fun columns() = transpose().rows()

    fun toMap(): Map<Coord, T> =
        sequence {
            for (y in 0 until height()) {
                for (x in 0 until width()) {
                    yield(Coord(x.toLong(), y.toLong()) to matrix[y][x])
                }
            }
        }.toMap()

    fun findAll(target: T): Set<Coord> =
        toMap().filter { (_, chr) ->
            chr == target
        }.keys
    
    fun neighbors(coord: Coord, includeDiagonals: Boolean = false) =
        coord
            .neighbors(includeDiagonals)
            .filter { isInsideBounds(it) }

    fun isInsideBounds(coord: Coord) = coord.y in 0 until height() && coord.x in 0 until width()

    fun width() = matrix.first().size

    fun height() = matrix.size

    fun transpose(): Matrix<T> {
        val transposed = MutableList(width()) { MutableList(height()) { defaultValue } }
        for (i in 0 until height()) {
            for (j in 0 until width()) {
                transposed[j][i] = matrix[i][j]
            }
        }
        return Matrix(transposed, defaultValue)
    }

    fun flipHorizontally(): Matrix<T> =
        fromRows(rows().reversed(), defaultValue)

    fun rotateLeft(): Matrix<T> =
        Matrix(columns().reversed(), defaultValue)

    fun rotateRight(): Matrix<T> =
        Matrix(transpose().rows().map { it.reversed().toMutableList() }.toMutableList(), defaultValue)

    fun swap(coord: Coord, other: Coord) {
        val tmp = this[other]
        this[other] = this[coord]
        this[coord] = tmp
    }

    override fun toString() =
        matrix
            .joinToString(separator = "\n") { row ->
                row.joinToString(separator = "") {
                    it.toString()
                }
            }

    companion object {

        fun <T> fromRows(rows: List<List<T>>, defaultValue: T) =
            Matrix(rows.map { it.toMutableList() }, defaultValue)
    }
}


