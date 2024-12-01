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

    private fun readInput(input: List<String>): Pair<List<Int>, List<Int>> {
        val pairs =
            input.map { line ->
                val ids = line.split("   ").map { it.toInt() }
                ids[0] to ids[1]
            }
        return pairs.map { it.first }.sorted() to pairs.map { it.second }.sorted()
    }
}
