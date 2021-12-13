package day13

import util.toPair
import java.io.File
import kotlin.time.measureTimedValue

fun parseInput(input: String): List<Pair<Int, Int>> {
    return input.split("\n").map { it.split(",").map(Integer::parseInt).toPair() }
}

fun parseFolds(folds: String): List<Pair<Char, Int>> {
    return folds.split("\n").filterNot(String::isEmpty).map { line -> line.removePrefix("fold along ").split("=").toPair().let { it.first.first() to it.second.toInt() } }
}

@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val (input, foldInputs) = File("src/main/kotlin/day13/input").readText().split("\n\n")

        val points = parseInput(input)
        val folds = parseFolds(foldInputs)

        val r1 = solve(points, folds.take(1), print = false)
        println(r1)

        solve(points, folds, print = true)

    }
    println(elapsed)
}

fun foldPosAround(point: Int, pos: Int): Int =
    2 * pos - point

fun solve(_points: List<Pair<Int, Int>>, folds: List<Pair<Char, Int>>, print: Boolean): Int {
    val points = _points.toMutableSet()

    folds.forEach { (dir, pos) ->
        if (dir == 'y') {
            // fold up
            val movedPoints = points.filter { it.second > pos }.toSet()
            points.removeAll(movedPoints)
            points.addAll(movedPoints.map {
                it.first to foldPosAround(it.second, pos)
            })
        } else if (dir == 'x') {
            // fold left
            val movedPoints = points.filter { it.first > pos }.toSet()
            points.removeAll(movedPoints)
            points.addAll(movedPoints.map {
                foldPosAround(it.first, pos) to it.second
            })
        } else {
            throw IllegalArgumentException("unsupported dir $dir")
        }
    }

    if (print) {
        printPoints(points)
    }

    return points.size
}

fun printPoints(points: Set<Pair<Int, Int>>) {
    for (y in 0..points.maxOf { it.second }) {
        for (x in 0..points.maxOf { it.first }) {
            if (points.contains(x to y)) {
                print("█")
            } else {
                print(" ")
            }
        }
        println()
    }
}

