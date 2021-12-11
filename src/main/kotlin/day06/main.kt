package day06

import java.io.File
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue

@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val nums = File("src/main/kotlin/day06/input").readLines().single().split(",").map(Integer::parseInt)
        val r1 = solve(nums, 80)
        assertEquals(395627, r1)
         val r2 = solve(nums, 256)
         assertEquals(1767323539209, r2)
    }
    println(elapsed)
}

private fun solve(nums: List<Int>, iterations: Int): Long {
    val fishes = (0..8).map { i -> nums.count { it == i }.toLong() }.toMutableList()
    repeat(iterations) { _ ->
        val fishesToAdd = fishes[0]

        for (i in 0..7) {
            fishes[i] = fishes[i+1]
        }
        fishes[6] += fishesToAdd
        fishes[8] = fishesToAdd
    }

    val count = fishes.sum()

    println(count)
    return count
}
