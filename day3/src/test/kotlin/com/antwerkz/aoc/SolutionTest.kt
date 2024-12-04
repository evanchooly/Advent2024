package com.antwerkz.aoc

class SolutionTest : TestBase() {
    override fun sampleSolutionPart1() = 161L

    override fun sampleSolutionPart2() = 48L

    override fun solvePart1(input: List<String>): Long {
        var regex = Regex("""(mul\(\d+,\d+\))""")
        return input
            .flatMap { regex.findAll(it).map { it.value } }
            .map { it.substringAfter('(').substringBefore(')').split(',') }
            .sumOf { it[0].toLong() * it[1].toLong() }
    }

    override fun solvePart2(input: List<String>): Long {
        var regex = Regex("""(mul\(\d+,\d+\)|do\(\)|don't\(\))""")
        var include = true
        return input
            .flatMap { regex.findAll(it).map { it.value } }
            .mapNotNull { entry ->
                when (entry) {
                    "do()" -> {
                        include = true
                        null
                    }
                    "don't()" -> {
                        include = false
                        null
                    }
                    else -> if (include) entry else null
                }
            }
            .map { it.substringAfter('(').substringBefore(')').split(',') }
            .sumOf { it[0].toLong() * it[1].toLong() }
    }
}
