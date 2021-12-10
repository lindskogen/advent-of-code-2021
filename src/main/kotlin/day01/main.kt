package day01

import java.io.File
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue

@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val nums = File("src/main/kotlin/day01/input").readLines().map(Integer::parseInt)
        assertEquals(1581, solve(nums))
        assertEquals(1618, solve2(nums))
    }
    println(elapsed)
}

private fun solve(nums: List<Int>): Int {
    var prev: Int? = null
    var count = 0

    for (n in nums) {
        if (prev == null) {
            prev = n
        } else if (n > prev) {
            count++
        }
        prev = n
    }

    println(count)
    return count
}

private fun solve2(nums: List<Int>): Int {
    val numbers = mutableMapOf<Int, Int>()

    return nums.windowed(3).withIndex().count { (i, it) ->
        val currSum = it.sum()
        numbers[i] = currSum

        i > 0 && currSum > numbers[i-1]!!
    }
}
