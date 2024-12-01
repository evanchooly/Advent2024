package com.antwerkz.aoc

class Day1Solution : TestBase() {
    val digits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    override fun day() = 1

    override fun sampleSolutionPart1() = 11

    override fun sampleSolutionPart2() = 31

    override fun solvePart1(input: List<String>): Int {
        val (left, right) = readInput(input)

        return left.zip(right).map { pair -> Math.abs(pair.first - pair.second) }.sum()
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

    private fun replaceDigits(it: String, start: Int = 0): String {
        var string = it
        if (start < string.length) {
            var match = digits.filter { string.substring(start).startsWith(it) }.firstOrNull()
            string =
                match?.let {
                    replaceDigits(
                        string.replaceFirst(
                            it,
                            "%-${it.length}s".format("${digits.indexOf(it) + 1}")
                        ),
                        start + it.length
                    )
                } ?: replaceDigits(string, start + 1)
        }
        return string
    }
}
