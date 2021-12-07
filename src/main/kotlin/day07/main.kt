package day07

import java.io.File
import java.lang.Integer.max
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue

@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val nums = File("src/main/kotlin/day07/input").readLines().single().split(",").map(Integer::parseInt)
        val r1 = solve(nums, false)
        assertEquals(344605, r1)
        val r2 = solve(nums, true)
        assertEquals(93699985, r2)

    }
    println(elapsed)
}

private fun solve(nums: List<Int>, part2: Boolean): Int {
    val r = (0..nums.maxOrNull()!!).minOf { p ->
        nums.sumOf {
            if (part2) {
                (1..abs(it - p)).sum()
            } else {
                abs(it - p)
            }
        }
    }

    return r
}
