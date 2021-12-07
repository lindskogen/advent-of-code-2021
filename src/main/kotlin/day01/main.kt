package day01

import java.io.File
import java.util.*
import kotlin.time.measureTimedValue

@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val nums = File("src/main/kotlin/day01/input").readLines().map(Integer::parseInt)
        solve(nums)
        solve2(nums)
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
    var count = 0

    for (i in 0..nums.size-3) {
        val currSum = nums[i] + nums[i + 1] + nums[i + 2]
        numbers[i] = currSum
        if (i > 0 && currSum > numbers[i-1]!!) {
            count++
        }
    }

    println(count)
    return count
}
