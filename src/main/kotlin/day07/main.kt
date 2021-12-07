package day07

import java.io.File
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue

@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val nums = File("src/main/kotlin/day07/input").readLines().single().split(",").map(Integer::parseInt)
        val r1 = solve1(nums)
        assertEquals(344605, r1)
        println(r1)
        val r2 = solve2(nums)
        assertEquals(93699985, r2)
        println(r2)

    }
    println(elapsed)
}

private fun solve1(nums: List<Int>): Int {
    val i = nums.sorted()[nums.size / 2]
    return nums.sumOf { abs(it - i)  }
}


private fun solve2(nums: List<Int>): Int {
    val i = nums.sum() / nums.size
    return nums.sumOf { trig(abs(it - i)) }
}

private fun trig(a: Int) = a * (a + 1) / 2
