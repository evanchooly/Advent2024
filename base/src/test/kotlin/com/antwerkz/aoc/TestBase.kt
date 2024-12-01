package com.antwerkz.aoc

import java.io.File
import java.net.URI
import org.apache.hc.client5.http.fluent.Request
import org.testng.Assert
import org.testng.SkipException
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

abstract class TestBase {
    companion object {
        const val INPUT = "src/test/resources/input.txt"
        const val SAMPLE = "src/test/resources/sample.txt"
        const val SAMPLE2 = "src/test/resources/sample2.txt"
        val SESSION_ID: String by lazy {
            System.getenv("SESSION_ID")
                ?: File(System.getProperty("user.dir"), ".bashrc")
                    .readLines()
                    .filter { s -> s.contains("AOC_SESSION") }
                    .map { s -> s.substringAfter("=").trim() }
                    .firstOrNull()
                ?: throw IllegalArgumentException("SESSION_ID cannot be null")
        }
    }

    lateinit var sample: List<String>
    var sample2: List<String>? = null
    lateinit var data: List<String>

    val day: Int by lazy {
        var root = File(System.getProperty("user.dir")).absoluteFile
        while (!File(root, "pom.xml").exists()) {
            root = root.parentFile
        }
        val name = root.name
        name.substring(3).toInt()
    }

    @BeforeClass
    fun downloadInput() {
        val file = File(INPUT)
        if (!file.exists() || file.length() == 0L) {
            val url = URI("https://adventofcode.com/2024/day/${day}/input")
            Request.get(url)
                .userAgent(
                    "Mozilla/5.0 (compatible; aoc; +https://github.com/evanchooly/adventofcode2024)"
                )
                .addHeader("Cookie", "session=$SESSION_ID")
                .execute()
                .saveContent(file)
        }

        data = INPUT.read()
        sample = if (File(SAMPLE).exists()) SAMPLE.read() else listOf<String>()
        sample2 = if (File(SAMPLE2).exists()) SAMPLE2.read() else sample
    }

    @Test
    open fun part1() {
        samplePart1()
        println("Solution to day ${day} part 1:  ${solvePart1(data)}")
    }

    @Test
    open fun part2() {
        try {
            samplePart2()
            println("Solution to day ${day} part 2:  ${solvePart2(data)}")
        } catch (_: NotImplementedError) {
            throw SkipException("part 2 not implemented")
        }
    }

    open fun samplePart1() = Assert.assertEquals(solvePart1(sample), sampleSolutionPart1())

    open fun samplePart2() =
        Assert.assertEquals(solvePart2(sample2 ?: sample), sampleSolutionPart2())

    abstract fun sampleSolutionPart1(): Any

    abstract fun sampleSolutionPart2(): Any

    abstract fun solvePart1(input: List<String>): Any

    abstract fun solvePart2(input: List<String>): Any

    fun String.read(): List<String> {
        return File(this).readLines()
    }
}
