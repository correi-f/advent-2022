package day8

import getResourceAsText
import kotlin.math.max

fun main() {
    val fileTextDemo = getResourceAsText("day8/input-demo.txt") ?: ""
    val demoGrid = Grid(mutableMapOf())
    mapGrid(demoGrid, fileTextDemo.split("\r\n"))
    // Demo First result
    println(analyzeTrees(demoGrid))
    // Demo Second result
    println(analyzeTreesV2(demoGrid))

    val fileText = getResourceAsText("day8/input.txt") ?: ""
    val grid = Grid(mutableMapOf())
    mapGrid(grid, fileText.split("\r\n"))
    // First result
    println(analyzeTrees(grid))
    // Second result
    println(analyzeTreesV2(grid))
}

fun analyzeTrees(grid: Grid): Int {
    var visibleTrees = 0
    (0 until grid.map.size).forEach { y ->
        (0 until grid.map[y]!!.size).forEach { x ->
            val treeValue = grid.map[y]!![x].value
            visibleTrees += when {
                !grid.map[y]!!.subList(0, x).any { it.value >= treeValue } ||
                !grid.map[y]!!.subList(x+1, grid.map[y]!!.size).any { it.value >= treeValue } ||
                !(0 until y).any { grid.map[it]!![x].value >= treeValue } ||
                !(y+1 until grid.map.size).any { grid.map[it]!![x].value >= treeValue } -> 1
                else -> 0
            }
        }
    }
    return visibleTrees
}

fun analyzeTreesV2(grid: Grid): Int {
    var highestScenic = 0
    (0 until grid.map.size).forEach { y ->
        (0 until grid.map[y]!!.size).forEach { x ->
            val treeValue = grid.map[y]!![x].value
            val visibleTreesScenic: Int = seeVisibleTree(grid.map[y]!!.subList(0, x).reversed(), treeValue) *
                    seeVisibleTree(grid.map[y]!!.subList(x+1, grid.map[y]!!.size), treeValue) *
                    seeVisibleTree((0 until y).map { grid.map[it]!![x] }.reversed(), treeValue) *
                    seeVisibleTree((y+1 until grid.map.size).map { grid.map[it]!![x] }, treeValue)
            highestScenic = max(highestScenic, visibleTreesScenic)
        }
    }
    return highestScenic
}

private fun seeVisibleTree(trees: List<Tree>, treeValue: Int): Int {
    var visibleTree = 0
    var previousTreeValue = -1
    for (index in trees.indices) {
        visibleTree++
        if (trees[index].value >= treeValue) break
        if (trees[index].value < previousTreeValue) continue
        previousTreeValue = trees[index].value
    }
    return visibleTree
}

private fun mapGrid(grid: Grid, lines: List<String>) {
    lines.forEachIndexed { index, line ->
        (0 until line.indices.count()).forEach { indexLine ->
            if (indexLine == 0) grid.map[index] = mutableListOf()
            grid.map[index]!!.add(Tree(line[indexLine].digitToInt()))
        }
    }
}

data class Grid(val map: MutableMap<Int, MutableList<Tree>> = mutableMapOf())

data class Tree(val value: Int)