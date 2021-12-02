package day02

import java.io.File
import java.lang.Integer.parseInt
import kotlin.time.measureTimedValue

enum class Direction {
    Forward, Up, Down;

    companion object {
        fun parse(input: String): Direction = when (input) {
            "forward" -> Forward
            "up" -> Up
            "down" -> Down
            else -> throw IllegalArgumentException("'$input' is an invalid Direction")
        }

    }
}

class Command(val dir: Direction, val num: Int) {
    companion object {
        fun parse(input: String): Command {
            val splits = input.split(" ")
            return Command(Direction.parse(splits[0]), parseInt(splits[1]))
        }
    }
}

@kotlin.time.ExperimentalTime
fun main(args: Array<String>) {
    val (value, elapsed) = measureTimedValue {
        val commands = File("src/main/kotlin/day02/input").readLines().map(Command::parse)
        println(solve(commands))
        println(solve2(commands))
    }
    println(elapsed)
}

private fun solve(commands: List<Command>): Int {
    var x = 0
    var y = 0

    for (c in commands) {
        when (c.dir) {
            Direction.Forward -> {
                x += c.num
            }
            Direction.Down -> {
                y += c.num
            }
            Direction.Up -> {
                y -= c.num
            }
        }
    }

    return x * y
}

private fun solve2(commands: List<Command>): Int {
    var x = 0
    var y = 0
    var aim = 0

    for (c in commands) {
        when (c.dir) {
            Direction.Forward -> {
                x += c.num
                y += aim * c.num
            }
            Direction.Down -> {
                aim += c.num
            }
            Direction.Up -> {
                aim -= c.num
            }
        }
    }

    return x * y
}
