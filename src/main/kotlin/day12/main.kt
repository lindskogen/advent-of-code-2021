package day12

import java.io.File
import kotlin.time.measureTimedValue

@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val input = File("src/main/kotlin/day12/input").readLines().flatMap {
            val (f, t) = it.split("-").filterNot(String::isEmpty)
            listOf(f to t, t to f)
        }

        val paths = input.toMutableSet()
        paths.removeIf { it.second == "start" }
        val r1 = recurse(paths, mutableListOf(), "start").size
        println(r1)
        val r2 = recurse2(paths, mutableListOf(), "start", null, 0).size
        println(r2)
    }
    println(elapsed)
}


fun recurse(paths: Set<Pair<String, String>>, path: MutableList<String>, here: String): List<List<String>> {
    val p2 = paths.toMutableSet()
    val myPath = path.toMutableList()
    myPath.add(here)
    if (here == "end") {
        return listOf(myPath)
    }
    if (isSmallCave(here)) {
        p2.removeIf { it.second == here }
    }
    return p2.filter { it.first == here }.flatMap {
        recurse(p2, myPath, it.second)
    }
}

fun isSmallCave(str: String): Boolean =
    str != "end" && str != "start" && str[0].isLowerCase()


fun recurse2(paths: Set<Pair<String, String>>, path: MutableList<String>, here: String, smallCaveVisitedTwice: String?, visitedCount: Int): Set<List<String>> {
    val p2 = paths.toMutableSet()
    val myPath = path.toMutableList()
    var visitedCount = visitedCount
    myPath.add(here)
    if (here == "end") {
        return setOf(myPath)
    }
    if (here == smallCaveVisitedTwice) {
        visitedCount += 1
        if (visitedCount > 1) {
            p2.removeIf { it.second == here }
        }
    } else if (isSmallCave(here)) {
        p2.removeIf { it.second == here }
    }

    return p2.filter { it.first == here }.flatMap {
        if (smallCaveVisitedTwice == null && isSmallCave(it.second)) {
            listOf(
                recurse2(p2, myPath, it.second, null, 0),
                recurse2(p2, myPath, it.second, it.second, 0),
            ).flatten()
        } else {
            recurse2(p2, myPath, it.second, smallCaveVisitedTwice, visitedCount)
        }
    }.toSet()
}
