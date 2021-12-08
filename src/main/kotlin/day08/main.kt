package day08

import java.io.File
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue

@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val pairs = File("src/main/kotlin/day08/input").readLines().map { line ->
            val (inputs, outputs) = line.split(" | ")
                .map { it.split(" ").map { s -> s.toCharArray().sorted().joinToString(separator = "") } }
            inputs to outputs
        }
        val r1 = solve1(pairs)
        assertEquals(278, r1)
        println(r1)

        val r2 = solve2(pairs)
        println(r2)
        assertEquals(986179, r2)

    }
    println(elapsed)
}

private fun solve1(pairs: List<Pair<List<String>, List<String>>>): Int =
    pairs.sumOf { (inputs, outputs) ->
        outputs.count { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }
    }

fun solve2(pairs: List<Pair<List<String>, List<String>>>): Int =
    pairs.sumOf { (inputs, outputs) ->
        val iset = inputs.toMutableSet()
        val nbrMap = mutableMapOf<String, Int>()

        val one = findAndRemoveFromSet(iset) { it.length == 2 }.also {
            nbrMap[it] = 1
        }

        val seven = findAndRemoveFromSet(iset) { it.length == 3 }.also {
            nbrMap[it] = 7
        }

        val four = findAndRemoveFromSet(iset) { it.length == 4 }.also {
            nbrMap[it] = 4
        }

        val eight = findAndRemoveFromSet(iset) { it.length == 7 }.also {
            nbrMap[it] = 8
        }

        val three = findAndRemoveFromSet(iset) { it.toSet().containsAll(one.toSet()) && it.length == 5 }.also {
            nbrMap[it] = 3
        }

        val nine = findAndRemoveFromSet(iset) { it.length == 6 && it.toSet().containsAll(four.toSet()) }.also {
            nbrMap[it] = 9
        }

        val zero = findAndRemoveFromSet(iset) { it.length == 6 && it.toSet().containsAll(seven.toSet()) }.also {
            nbrMap[it] = 0
        }

        val six = findAndRemoveFromSet(iset) { it.length == 6 }.also {
            nbrMap[it] = 6
        }

        val nineMissingSegment = eight.toSet().minus(nine.toSet()).single()

        val two = findAndRemoveFromSet(iset) { it.contains(nineMissingSegment) }.also {
            nbrMap[it] = 2
        }

        val five = iset.single().also {
            nbrMap[it] = 5
        }

        outputs.joinToString(separator = "") { nbrMap[it].toString() }.toInt()
    }

private fun findAndRemoveFromSet(
    iset: MutableSet<String>,
    predicate: (String) -> Boolean
): String =
    iset.find(predicate)!!.also { iset.remove(it) }




