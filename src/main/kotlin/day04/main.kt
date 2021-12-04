package day04

import java.io.File
import java.util.regex.Pattern
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue

typealias Board = List<List<Int>>

private fun parseBoard(input: String): Board {
    val regex = Pattern.compile("\\s+")
    return input.lines().map {
        it.split(regex).filterNot(String::isEmpty).map(Integer::parseInt)
    }
}


@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val input = File("src/main/kotlin/day04/input").readLines().joinToString(separator = "\n")
        val groups = input.split("\n\n")
        val numbers = groups.first().split(',').map(Integer::parseInt)
        val boards = groups.drop(1).map { parseBoard(it) }
        val (r1, r2) = solve(numbers, boards)
        assertEquals(50008, r1)
        println(r1)
        assertEquals(17408, r2)
        println(r2)

    }
    println(elapsed)
}

private fun solve(numbers: List<Int>, boards: List<Board>): Pair<Int, Int> {
    val calledNumbers = mutableSetOf<Int>()
    val wonBoards = mutableSetOf<Int>()
    var firstBoardScore: Int? = null
    var lastBoardScore: Int? = null

    for (n in numbers) {
        calledNumbers.add(n)

        for ((i, b) in boards.withIndex()) {

            if (!wonBoards.contains(i)) {
                if (checkIfBoardHasBingo(calledNumbers, b)) {
                    wonBoards.add(i)

                    val boardScore = n * sumBoard(calledNumbers, b)

                    firstBoardScore = firstBoardScore ?: boardScore
                    lastBoardScore = boardScore
                }
            }
        }
    }

    if (firstBoardScore == null || lastBoardScore == null) {
        throw IllegalStateException("No bingo for these numbers & boards")
    }

    return Pair(firstBoardScore, lastBoardScore)
}

fun checkIfBoardHasBingo(calledNumbers: Set<Int>, board: Board): Boolean {
    for (row in board) {
        if (calledNumbers.containsAll(row)) {
            return true
        }
    }
    for (i in board[0].indices) {
        if (board.all { row -> calledNumbers.contains(row[i]) }) {
            return true
        }
    }
    return false
}

fun sumBoard(calledNumbers: Set<Int>, board: Board): Int {
    return board.sumOf { row -> row.filter { !calledNumbers.contains(it) }.sum() }
}
