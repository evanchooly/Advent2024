package com.antwerkz.aoc

import kotlin.math.abs

class SolutionTest : TestBase() {
    override fun sampleSolutionPart1() = 11

    override fun sampleSolutionPart2() = 31

    override fun solvePart1(input: List<String>): Int {
        val (left, right) = readInput(input)

        return left.zip(right).sumOf { pair -> abs(pair.first - pair.second) }
    }

    override fun solvePart2(input: List<String>): Int {
        val (left, right) = readInput(input)

        return left.sumOf { id -> id * right.count { i -> i == id } }
    }

    private fun readInput(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        input
            .map { line -> line.replace(Regex(" +"), " ").split(" ") }
            .map { values -> listOf(values[0].toInt(), values[1].toInt()) }
            .forEach { list ->
                left += list[0]
                right += list[1]
            }
        left.sort()
        right.sort()
        return Pair(left, right)
    }
}