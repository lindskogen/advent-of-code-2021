package day05

import java.io.File
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue

fun parseRect(input: String): Pair<Pair<Int,Int>, Pair<Int, Int>> {
    val lists = input.split(" -> ").map { it.split(",").map(Integer::parseInt) }

    return Pair(Pair(lists[0][0], lists[0][1]), Pair(lists[1][0], lists[1][1]))
}

@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val rects = File("src/main/kotlin/day05/input").readLines().map { parseRect(it) }
        val r1 = solve(rects, countDiagonals = false)
        assertEquals(7644, r1)
        println(r1)
        val r2 = solve(rects, countDiagonals = true)
        assertEquals(18627, r2)
        println(r2)

    }
    println(elapsed)
}

private fun solve(rects: List<Pair<Pair<Int,Int>, Pair<Int, Int>>>, countDiagonals: Boolean): Int {
    val map = mutableMapOf<Pair<Int, Int>, Int>()

    for ((from, to) in rects) {
        val (x1, x2) = sortFromTo(from.first, to.first)
        val (y1, y2) = sortFromTo(from.second, to.second)
        if (x1 == x2 || y1 == y2) {
            for (x in x1..x2) {
                for (y in y1..y2) {
                    map.compute(Pair(x, y)) { _, v -> (v ?: 0) + 1 }
                }
            }
        } else if (countDiagonals) {
            val xDir = if (from.first < to.first) { 1 } else { -1 }
            val yDir = if (from.second < to.second) { 1 } else { -1 }


            for (n in 0 .. abs(from.first - to.first)) {
                val x = from.first + (xDir*n)
                val y = from.second + (yDir*n)

                map.compute(Pair(x, y)) { _, v -> (v ?: 0) + 1 }
            }

        }
    }

    return map.count { (k, v) -> v > 1 }
}

private fun sortFromTo(from: Int, to: Int) =
    if (from > to) {
        Pair(to, from)
    } else {
         Pair(from, to)
    }

fun printMap(map: Map<Pair<Int, Int>, Int>, from: Int, to: Int) {
    for (x in from..to) {
        for (y in from..to) {
            print(map[Pair(y, x)] ?: '.')
        }
        println()
    }
}
