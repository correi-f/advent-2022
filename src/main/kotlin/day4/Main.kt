package day4

import getResourceAsText

fun main() {
    val fileTextDemo = getResourceAsText("day4/input-demo.txt") ?: ""
    val elvesSectionsDemo = fileTextDemo.split("\r\n").map { line ->
        val firstElfSection = line.split(",")[0].split("-")
        val secondElfSection = line.split(",")[1].split("-")
        Pair(firstElfSection[0].toInt()..firstElfSection[1].toInt(), secondElfSection[0].toInt()..secondElfSection[1].toInt())
    }

    // Demo First Result
    println(elvesSectionsDemo.filter { pair ->
        pair.first.toList().containsAll(pair.second.toList()) || pair.second.toList().containsAll(pair.first.toList())
    }.size)

    // Demo Second Result
    println(elvesSectionsDemo.filter { pair ->
        pair.first.toList().any { pair.second.toList().contains(it) } || pair.second.toList().any { pair.first.toList().contains(it) }
    }.size)

    val fileText = getResourceAsText("day4/input.txt") ?: ""
    val elvesSections = fileText.split("\r\n").map { line ->
        val firstElfSection = line.split(",")[0].split("-")
        val secondElfSection = line.split(",")[1].split("-")
        Pair(firstElfSection[0].toInt()..firstElfSection[1].toInt(), secondElfSection[0].toInt()..secondElfSection[1].toInt())
    }

    // First Result
    println(elvesSections.filter { pair ->
        pair.first.toList().containsAll(pair.second.toList()) || pair.second.toList().containsAll(pair.first.toList())
    }.size)

    // Second Result
    println(elvesSections.filter { pair ->
        pair.first.toList().any { pair.second.toList().contains(it) } || pair.second.toList().any { pair.first.toList().contains(it) }
    }.size)
}