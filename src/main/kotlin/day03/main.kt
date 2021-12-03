package day03

import java.io.File
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue


@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val nums = File("src/main/kotlin/day03/input").readLines()
        val r1 = solve(nums)
        assertEquals(1540244, r1)
        println(r1)

        val r2 = solve2(nums)
        assertEquals(4203981, r2)
        println(r2)
    }
    println(elapsed)
}

private fun solve(lines: List<String>): Int {
    val result = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val invertedResult = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

    for (i in 0..11) {
        var numOnes = 0
        var numZeros = 0
        for (l in lines) {
            if (l[i] == '1') {
                numOnes += 1
            } else {
                numZeros += 1
            }
        }
        if (numZeros > numOnes) {
            result[i] = 0
            invertedResult[i] = 1
        } else {
            result[i] = 1
            invertedResult[i] = 0
        }
    }
    val binaryNum = Integer.parseInt(result.joinToString(separator = ""), 2)
    val invertedNum = Integer.parseInt(invertedResult.joinToString(separator = ""), 2)

    return binaryNum * invertedNum
}


private fun solve2(lines: List<String>): Int {
    val o2 = findMostCommon(lines) { ones, zeros ->  ones >= zeros }
    val co2 = findMostCommon(lines) { ones, zeros -> ones < zeros }

    return o2 * co2
}

private fun findMostCommon(lines: List<String>, keepOnes: (numOnes: Int, numZeros: Int) -> Boolean): Int {
    val numBits = lines[0].length
    val linesSet = lines.toMutableSet()

    for (i in 0 until numBits) {
        var numOnes = 0
        var numZeros = 0
        for (l in linesSet) {
            if (l[i] == '1') {
                numOnes += 1
            } else {
                numZeros += 1
            }
        }
        if (keepOnes(numOnes, numZeros)) {
            linesSet.removeIf { it[i] == '0' }
        } else {
            linesSet.removeIf { it[i] == '1' }
        }

        if (linesSet.size == 1) {
            return Integer.parseInt(linesSet.single(), 2)
        }
    }
    throw IllegalStateException("Missed end condition")
}
