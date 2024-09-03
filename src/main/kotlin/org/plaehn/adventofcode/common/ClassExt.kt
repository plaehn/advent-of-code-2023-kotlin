package org.plaehn.adventofcode.common

fun <T> Class<T>.readLines(resource: String) =
    this.getResource(resource)
        ?.readText()
        ?.lines()
        ?.filter { it.isNotBlank() }
        ?: throw IllegalArgumentException("Resource $resource not found")

fun <T> Class<T>.slurp(resource: String) =
    this.getResource(resource)
        ?.readText()
        ?.trim()
        ?: throw IllegalArgumentException("Resource $resource not found")
