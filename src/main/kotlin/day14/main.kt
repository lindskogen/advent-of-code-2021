package day14

import util.toPair
import java.io.File
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue

class Counter<K> {
    private val map = mutableMapOf<K, Long>()

    constructor()

    constructor(initialValue: Collection<K>) {
        initialValue.groupingBy { it }.eachCount().mapValuesTo(map) { it.value.toLong() }
    }

    operator fun get(p: K): Long {
        return map.getOrDefault(p, 0L)
    }

    fun getMap() = map.toMap()

    operator fun set(p: K, value: Long) {
        return map.set(p, value)
    }
}

fun parseMatches(str: String): Map<String, Char> {
    return str.split("\n").filterNot(String::isEmpty).map { line -> line.split(" -> ").toPair() }
        .associate { it.first to it.second.first() }
}

@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val (input, matchInputs) = File("src/main/kotlin/day14/input").readText().split("\n\n")
        val matches = parseMatches(matchInputs)
        val r1 = solve(input, matches, 10)
        assertEquals(3411, r1)
        println(r1)
        val r2 = solve(input, matches, 40)

        assertEquals(7477815755570L, r2)
        println(r2)

    }
    println(elapsed)
}

fun solve(input: String, matches: Map<String, Char>, iterations: Int): Long {
    val pairs = Counter(input.windowed(2).toList())
    val charCounts = Counter(input.toList())

    repeat(iterations) {
        pairs.getMap().forEach { (key, count) ->
            val p = matches[key]
            if (p != null) {
                val (p1, p2) = key.toList()

                pairs[key] -= count
                pairs["$p1$p"] += count
                pairs["$p$p2"] += count
                charCounts[p] += count
            }
        }
    }

    val map = charCounts.getMap()

    return map.maxOf { it.value } - map.minOf { it.value }
}

