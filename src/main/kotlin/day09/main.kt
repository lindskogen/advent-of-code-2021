package day09

import util.*
import java.io.File
import kotlin.test.assertEquals
import kotlin.time.measureTimedValue

data class Grid<E>(private val list: List<List<E>>) {

    fun height(): Int = list.size
    fun width(): Int = list[0].size

    fun get(p: Pair<Int, Int>): E =
        list[p.second][p.first]

    fun getOrNull(p: Pair<Int, Int>): E? =
        list.getOrNull(p.second)?.getOrNull(p.first)

}

fun Pair<Int, Int>.neighbors(): List<Pair<Int, Int>> {
    val (x, y) = this
    return listOf(x to y - 1, x to y + 1, x - 1 to y, x + 1 to y)
}

@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val numbers = File("src/main/kotlin/day09/input").readLines().map { line ->
            line.toCharArray().map { it - '0' }
        }
        val g = Grid(numbers)
        val (r1, r2) = solve(g)
        println(r1)
        assertEquals(518, r1)

        println(r2)
        assertEquals(949905, r2)

    }
    println(elapsed)
}

private fun solve(grid: Grid<Int>): Pair<Int, Int> {
    var riskSum = 0
    val basins = mutableListOf<Pair<Int, Int>>()
    for (y in 0 until grid.height()) {
        for (x in 0 until grid.width()) {
            val here = x to y
            val my = grid.get(here)
            if (here.neighbors().all { my < (grid.getOrNull(it) ?: Int.MAX_VALUE) }) {
                basins.add(here)
                riskSum += my + 1
            }
        }
    }

    val basinSizes = basins.map { b ->
        countNeighbors(grid, b).size
    }

    val topBasins = basinSizes.sortedDescending().take(3).product()
    return riskSum to topBasins
}

fun countNeighbors(
    grid: Grid<Int>,
    b: Pair<Int, Int>,
    points: MutableSet<Pair<Int, Int>> = mutableSetOf()
): Set<Pair<Int, Int>> {
    val myValue = grid.getOrNull(b) ?: 9

    if (myValue < 9 && points.add(b)) {
        b.neighbors().forEach {
            countNeighbors(grid, it, points)
        }
    }

    return points
}





