package day2

import getResourceAsText
import java.lang.IllegalArgumentException

fun main() {
    val fileTextDemo = getResourceAsText("day2/input-demo.txt") ?: ""
    val roundsDemo = fileTextDemo.split("\r\n").map {
        Pair(RPS.toRPS(it[0]), RPS.toRPS(it[2]))
    }
    // Demo first result
    println(roundsDemo.sumOf(::calculateHumanResultGame))

    // Demo first result
    println(roundsDemo.sumOf(::calculateHumanResultGameV2))

    val fileText = getResourceAsText("day2/input.txt") ?: ""
    val rounds = fileText.split("\r\n").map {
        Pair(RPS.toRPS(it[0]), RPS.toRPS(it[2]))
    }
    // First result
    println(rounds.sumOf(::calculateHumanResultGame))

    // Demo first result
    println(rounds.sumOf(::calculateHumanResultGameV2))
}

fun calculateHumanResultGame(inputGame: Pair<RPS, RPS>): Int {
    return when (inputGame.first) {
        RPS.ROCK -> when (inputGame.second) {
            RPS.ROCK -> 3 + 1
            RPS.SCISSOR -> 3
            else -> 6 + 2
        }
        RPS.PAPER -> when (inputGame.second) {
            RPS.ROCK -> 1
            RPS.PAPER -> 3 + 2
            else -> 6 + 3
        }
        else -> when (inputGame.second) {
            RPS.ROCK -> 6 + 1
            RPS.PAPER -> 2
            else -> 3 + 3
        }
    }
}

fun calculateHumanResultGameV2(inputGame: Pair<RPS, RPS>): Int {
    return when (inputGame.first) {
        RPS.ROCK -> when (inputGame.second) {
            RPS.ROCK -> 3
            RPS.SCISSOR -> 6 + 2
            else -> 3 + 1
        }
        RPS.PAPER -> when (inputGame.second) {
            RPS.ROCK -> 1
            RPS.PAPER -> 3 + 2
            else -> 6 + 3
        }
        else -> when (inputGame.second) {
            RPS.ROCK -> 2
            RPS.PAPER -> 3 + 3
            else -> 6 + 1
        }
    }
}


enum class RPS {
    ROCK,
    PAPER,
    SCISSOR;

    companion object {
        fun toRPS(character :Char): RPS {
            return when (character) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSOR
                else -> throw IllegalArgumentException()
            }
        }
    }
}