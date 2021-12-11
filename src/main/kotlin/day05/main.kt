package day05

import util.toPair
import java.io.File
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue

fun parseRect(input: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val (p1, p2) = input.split(" -> ").map { it.split(",").map(Integer::parseInt) }

    return (p1.toPair()) to (p2.toPair())
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

private fun solve(rects: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>, countDiagonals: Boolean): Int {

    return rects.flatMap { (from, to) ->
        val (x1, y1) = from
        val (x2, y2) = to

        if (x1 == x2) {
            val (start, end) = listOf(y1, y2).sorted()
            (start..end).map { y -> x1 to y }
        } else if (y1 == y2) {
            val (start, end) = listOf(x1, x2).sorted()
            (start..end).map { x -> x to y1 }
        } else if (countDiagonals) {
            val dx = if (x1 < x2) 1 else -1
            val dy = if (y1 < y2) 1 else -1

            (0..dx * (x2 - x1)).map { t ->
                (x1 + dx * t) to (y1 + dy * t)
            }
        } else {
            listOf()
        }
    }.groupingBy { it }.eachCount().count { it.value > 1 }
}
