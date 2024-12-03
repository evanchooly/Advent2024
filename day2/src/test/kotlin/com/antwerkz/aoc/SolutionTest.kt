package com.antwerkz.aoc

import kotlin.collections.all
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sign
import org.testng.Assert.assertFalse
import org.testng.Assert.assertTrue
import org.testng.annotations.Test

class SolutionTest : TestBase() {
    override fun sampleSolutionPart1() = 2

    override fun sampleSolutionPart2() = 4

    override fun solvePart1(input: List<String>) =
        input.count { safe(it.split(" ").map { it.toInt() }) }

    override fun solvePart2(input: List<String>) =
        input.count { dampen(it.split(" ").map { it.toInt() }) }

    @Test
    fun samples() {
        assertFalse(dampen(listOf(1, 2, 7, 8, 9))) // Unsafe regardless of which level is removed.
        assertFalse(dampen(listOf(80, 82, 79, 81, 84, 85, 88, 92)))
        assertFalse(dampen(listOf(32, 31, 34, 36, 33, 35, 39)))
        assertTrue(dampen(listOf(2, 4, 6, 9, 11, 14, 18))) // Safe by removing the last level, 18.
        assertTrue(dampen(listOf(7, 6, 4, 2, 1))) // Safe without removing any level.
        assertFalse(dampen(listOf(9, 7, 6, 2, 1))) // Unsafe regardless of which level is removed.
        assertTrue(dampen(listOf(1, 3, 2, 4, 5))) // Safe by removing the second level, 3.
        assertTrue(dampen(listOf(8, 6, 4, 4, 1))) // Safe by removing the third level, 4.
        assertTrue(dampen(listOf(1, 3, 6, 7, 9))) // Safe without removing any level.
    }

    private fun dampen(input: List<Int>): Boolean {
        return errors(input) <= 1
    }

    private fun errors(data: List<Int>, recurse: Boolean = true): Int {
        val diffs = diffs(data)
        var signs = diffs.groupBy { sign(1.0 * it) }
        val minBy = signs.values.minBy { it.size }
        val dirChange = if (signs.size > 1) minBy.size else 0
        if (recurse && dirChange > 0) {
            return 1 +
                min(
                    errors(
                        data.filterIndexed { index, i -> index != diffs.indexOf(minBy.first()) },
                        false
                    ),
                    errors(
                        data.filterIndexed { index, i ->
                            index != diffs.indexOf(minBy.first()) + 1
                        },
                        false
                    )
                )
        }
        val badGaps = diffs.count { abs(it) !in 1..3 }
        if (recurse && badGaps > 0) {
            return 1 +
                min(
                    errors(
                        data.filterIndexed { index, i ->
                            index != diffs.indexOfFirst { abs(it) !in 1..3 }
                        },
                        false
                    ),
                    errors(
                        data.filterIndexed { index, i ->
                            index != diffs.indexOfFirst { abs(it) !in 1..3 } + 1
                        },
                        false
                    )
                )
        }
        return dirChange + badGaps
    }

    private fun safe(data: List<Int>): Boolean {
        val gaps = diffs(data)
        var oneDirection = gaps.map { sign(1.0 * it) }.distinct().size == 1

        return oneDirection && validGaps(gaps)
    }

    private fun validGaps(gaps: List<Int>): Boolean = gaps.all { abs(it) in 1..3 }

    private fun diffs(data: List<Int>) = data.windowed(2).map { it[1] - it[0] }
}
