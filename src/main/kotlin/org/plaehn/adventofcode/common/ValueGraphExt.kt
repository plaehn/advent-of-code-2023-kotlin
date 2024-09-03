package org.plaehn.adventofcode.common

import com.google.common.graph.ValueGraph

fun <N : Any> ValueGraph<N, Int>.shortestPath(start: N, end: N): List<N> =
    shortestPaths(start) { it == end }.first()

fun <N : Any> ValueGraph<N, Int>.shortestPaths(start: N, endNodePredicate: (N) -> Boolean): List<List<N>> {
    val shortestPathTree = computeShortestPathTree(start)

    fun pathTo(start: N, end: N): List<N> {
        if (shortestPathTree[end] == null) return listOf(end)
        return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
    }

    return nodes()
        .filter(endNodePredicate)
        .map { pathTo(start, it) }
        .filter { it.first() == start }
}

// Cf. https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
private fun <N : Any> ValueGraph<N, Int>.computeShortestPathTree(start: N): Map<N, N?> {
    val visited: MutableSet<N> = mutableSetOf()

    val delta = nodes().associateWith { Int.MAX_VALUE }.toMutableMap()
    delta[start] = 0

    val previous: MutableMap<N, N?> = nodes().associateWith { null }.toMutableMap()

    while (visited != nodes()) {
        val node = delta
            .filter { !visited.contains(it.key) }
            .minByOrNull { it.value }!!
            .key

        successors(node).minus(visited).forEach { neighbor ->
            val newPath = delta.getValue(node) + edgeValue(node, neighbor!!).get()

            if (newPath < delta.getValue(neighbor)) {
                delta[neighbor] = newPath
                previous[neighbor] = node
            }
        }

        visited.add(node)
    }

    return previous.toMap()
}
