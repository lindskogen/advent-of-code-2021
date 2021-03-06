package day11

import java.io.File
import kotlin.time.measureTimedValue


@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val numbers = File("src/main/kotlin/day11/input").readLines().map { it.split("").filterNot(String::isEmpty).map(Integer::parseInt) }

        val (r1, r2) = solve(numbers, roundsPart1 = 100, maxRounds = 600)
        println(r1)
        println(r2)
    }
    println(elapsed)
}

fun Pair<Int, Int>.diagonalNeighbors(): List<Pair<Int, Int>> {
    val (x, y) = this
    return listOf(
        x to y - 1, x to y + 1, x - 1 to y, x + 1 to y,
        x-1 to y -1, x - 1 to y +1, x+1 to y-1, x +1 to y+1
    )
}


private fun solve(lines: List<List<Int>>, roundsPart1: Int, maxRounds: Int): Pair<Int, Int> {
    val lines = lines.map { l -> l.toMutableList() }.toMutableList()
    var totalFlashes = 0
    var part1Flashes = 0

    repeat(maxRounds) { round ->
        val flashedOctopuses = mutableSetOf<Pair<Int, Int>>()
        val flashStack = ArrayDeque<Pair<Int, Int>>()

        for (y in lines.indices) {
            for (x in lines.indices) {
                lines[y][x] += 1
                if (lines[y][x] > 9) {
                    flashStack.add(x to y)
                }
            }
        }

        while (flashStack.isNotEmpty()) {
            val (fx, fy) = flashStack.removeFirst()
            flashedOctopuses.add(fx to fy)
            lines[fy][fx] = 0
            totalFlashes += 1
            (fx to fy).diagonalNeighbors().filter { (0 until lines[0].size).contains(it.first) && (0 until lines.size).contains(it.second) }.forEach { n ->
                if (!flashedOctopuses.contains(n)) {
                    lines[n.second][n.first] += 1
                    if (lines[n.second][n.first] > 9) {
                        if (!flashStack.contains(n)) {
                            flashStack.add(n)
                        }
                    }
                }
            }
        }
        if (round+1 == roundsPart1) {
            part1Flashes = totalFlashes
        }
        if (flashedOctopuses.size == 100) {
            return part1Flashes to round+1
        }
    }

    throw IllegalStateException("No all-flash found before reaching max rounds")
}
