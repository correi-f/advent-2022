package day9

import getResourceAsText

fun main() {
    val fileTextDemo = getResourceAsText("day9/input-demo.txt") ?: ""
    val demoActions = fileTextDemo.split("\r\n").map { line ->
        val lineSplat = line.split(" ")
        Action(Direction.of(lineSplat[0]), lineSplat[1].toInt())
    }
    val demoTailPositionsVisited = retrieveTailsPositionsVisited(demoActions)
    println(demoTailPositionsVisited.size)

    val fileText = getResourceAsText("day9/input.txt") ?: ""
    val actions = fileText.split("\r\n").map { line ->
        val lineSplat = line.split(" ")
        Action(Direction.of(lineSplat[0]), lineSplat[1].toInt())
    }
    val tailPositionsVisited = retrieveTailsPositionsVisited(actions)
    println(tailPositionsVisited.size)
}

private fun retrieveTailsPositionsVisited(demoActions: List<Action>): List<Position> {
    val tailPositionsVisited = mutableListOf(Position())
    val headPosition = Position()
    val tailPosition = Position()
    demoActions.forEach { action ->
        repeat(action.step) {
            when (action.direction) {
                Direction.DOWN -> {
                    headPosition.coordinate = Pair(headPosition.coordinate.first, headPosition.coordinate.second - 1)
                }
                Direction.LEFT -> {
                    headPosition.coordinate = Pair(headPosition.coordinate.first - 1, headPosition.coordinate.second)
                }
                Direction.RIGHT -> {
                    headPosition.coordinate = Pair(headPosition.coordinate.first + 1, headPosition.coordinate.second)
                }
                else -> {
                    headPosition.coordinate = Pair(headPosition.coordinate.first, headPosition.coordinate.second + 1)
                }
            }
            moveTail(headPosition, tailPosition, tailPositionsVisited)
        }
    }
    return tailPositionsVisited.distinct()
}

fun moveTail(headPosition: Position, tailPosition: Position, tailPositionsVisited: MutableList<Position>) {
    if (tailPosition.coordinate == headPosition.coordinate) return
    val tx = tailPosition.coordinate.first
    val hx = headPosition.coordinate.first
    val ty = tailPosition.coordinate.second
    val hy = headPosition.coordinate.second
    if (ty == hy) {
        if (tx < hx && (hx - tx) > 1 ) {
            tailPosition.coordinate = Pair(tailPosition.coordinate.first + 1, tailPosition.coordinate.second)
            tailPositionsVisited.add(Position(Pair(tailPosition.coordinate.first, tailPosition.coordinate.second)))
        } else if (tx > hx && (tx - hx) > 1 ){
            tailPosition.coordinate = Pair(tailPosition.coordinate.first - 1, tailPosition.coordinate.second)
            tailPositionsVisited.add(Position(Pair(tailPosition.coordinate.first, tailPosition.coordinate.second)))
        }
    } else {
        if (ty < hy && (hy - ty) > 1) {
            if (tx < hx) {
                tailPosition.coordinate = Pair(tailPosition.coordinate.first + 1, tailPosition.coordinate.second + 1)
                tailPositionsVisited.add(Position(Pair(tailPosition.coordinate.first, tailPosition.coordinate.second)))
            } else if (tx > hx){
                tailPosition.coordinate = Pair(tailPosition.coordinate.first - 1, tailPosition.coordinate.second + 1)
                tailPositionsVisited.add(Position(Pair(tailPosition.coordinate.first, tailPosition.coordinate.second)))
            } else {
                tailPosition.coordinate = Pair(tailPosition.coordinate.first, tailPosition.coordinate.second + 1)
                tailPositionsVisited.add(Position(Pair(tailPosition.coordinate.first, tailPosition.coordinate.second)))
            }
        } else if (ty > hy && (ty - hy) > 1) {
            if (tx < hx) {
                tailPosition.coordinate = Pair(tailPosition.coordinate.first + 1, tailPosition.coordinate.second - 1)
                tailPositionsVisited.add(Position(Pair(tailPosition.coordinate.first, tailPosition.coordinate.second)))
            } else if (tx > hx){
                tailPosition.coordinate = Pair(tailPosition.coordinate.first - 1, tailPosition.coordinate.second - 1)
                tailPositionsVisited.add(Position(Pair(tailPosition.coordinate.first, tailPosition.coordinate.second)))
            } else {
                tailPosition.coordinate = Pair(tailPosition.coordinate.first, tailPosition.coordinate.second - 1)
                tailPositionsVisited.add(Position(Pair(tailPosition.coordinate.first, tailPosition.coordinate.second)))
            }
        } else if (tx > hx && (tx - hx) > 1) {
            if (ty < hy) {
                tailPosition.coordinate = Pair(tailPosition.coordinate.first - 1, tailPosition.coordinate.second + 1)
                tailPositionsVisited.add(Position(Pair(tailPosition.coordinate.first, tailPosition.coordinate.second)))
            } else {
                tailPosition.coordinate = Pair(tailPosition.coordinate.first - 1, tailPosition.coordinate.second - 1)
                tailPositionsVisited.add(Position(Pair(tailPosition.coordinate.first, tailPosition.coordinate.second)))
            }
        } else if (tx < hx && (hx - tx) > 1) {
            if (ty < hy) {
                tailPosition.coordinate = Pair(tailPosition.coordinate.first + 1, tailPosition.coordinate.second + 1)
                tailPositionsVisited.add(Position(Pair(tailPosition.coordinate.first, tailPosition.coordinate.second)))
            } else {
                tailPosition.coordinate = Pair(tailPosition.coordinate.first + 1, tailPosition.coordinate.second - 1)
                tailPositionsVisited.add(Position(Pair(tailPosition.coordinate.first, tailPosition.coordinate.second)))
            }
        }
    }
}

data class Position(var coordinate: Pair<Int, Int> = Pair(0, 0))

data class Action(val direction: Direction, val step: Int)

enum class Direction {
    UP,
    RIGHT,
    LEFT,
    DOWN;

    companion object {
        fun of(value: String): Direction {
            return when (value) {
                "U" -> UP
                "D" -> DOWN
                "L" -> LEFT
                "R" -> RIGHT
                else -> throw IllegalStateException()
            }
        }
    }
}
