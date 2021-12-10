package day10

import util.*
import java.io.File
import java.lang.UnsupportedOperationException
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue


@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val numbers = File("src/main/kotlin/day10/input").readLines()
        val r1 = solve(numbers)
        println(r1)
        assertEquals(413733, r1)
        val r2 = solve2(numbers)
        println(r2)
        assertEquals(3354640192, r2)
    }
    println(elapsed)
}

private fun solve(lines: List<String>): Int =
    lines.sumOf { l ->
        val stack = ArrayDeque<Char>()
        for (c in l.toCharArray()) {
            when (c) {
                '[', '(', '{', '<' -> stack.addFirst(c)
                ']', ')', '}', '>' -> {
                    if (stack.first() == matchingParens(c)) {
                        stack.removeFirst()
                    } else {
                        return@sumOf parensPoints(c)
                    }
                }
            }
        }

        return@sumOf 0
    }

private fun solve2(lines: List<String>): Long {
    val sortedLines = lines.flatMap { l ->
        val stack = ArrayDeque<Char>()
        for (c in l.toCharArray()) {
            when (c) {
                '[', '(', '{', '<' -> stack.addFirst(c)
                ']', ')', '}', '>' -> {
                    if (stack.first() == matchingParens(c)) {
                        stack.removeFirst()
                    } else {
                        return@flatMap emptyList()
                    }
                }
            }
        }

        listOf(stack.fold(0L) { acc, it ->
            acc * 5 + autocompletePoints(matchingParens(it))
        })
    }.sorted()

    return sortedLines.let { it[it.size / 2] }
}

fun matchingParens(c: Char) = when (c) {
    '[' -> ']'
    '(' -> ')'
    '{' -> '}'
    '<' -> '>'
    ']' -> '['
    ')' -> '('
    '}' -> '{'
    '>' -> '<'
    else -> throw UnsupportedOperationException("No support for $c")
}

fun parensPoints(c: Char) = when (c) {
    ']' -> 57
    ')' -> 3
    '}' -> 1197
    '>' -> 25137
    else -> throw UnsupportedOperationException("No support for $c")
}

fun autocompletePoints(c: Char) = when (c) {
    ')' -> 1
    ']' -> 2
    '}' -> 3
    '>' -> 4
    else -> throw UnsupportedOperationException("No support for $c")
}
