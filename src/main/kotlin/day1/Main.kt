package day1

import getResourceAsText

fun main() {
    val fileText = getResourceAsText("day1/input-demo.txt") ?: ""
    val elvesBagsDemo = retrieveElvesBag(fileText)
    // Demo first response
    println(elvesBagsDemo.maxOf { it.aliments.sum() })

    // Demo second response
    println(elvesBagsDemo.map { it.aliments.sum() }.sorted().takeLast(3).sum())

    val fileText1 = getResourceAsText("day1/input.txt") ?: ""
    val elvesBags1 = retrieveElvesBag(fileText1)
    // First response
    println(elvesBags1.maxOf { it.aliments.sum() })

    // Second response
    println(elvesBags1.map { it.aliments.sum() }.sorted().takeLast(3).sum())
}

fun retrieveElvesBag(fileText: String): List<Bag> {
    val elvesBags = mutableListOf<Bag>()
    var aliments = mutableListOf<Int>()
    val splitFileText = fileText.split("\r\n")
    splitFileText.forEachIndexed { index, line ->
        if (line.isNotBlank()) {
            aliments.add(line.toInt())
            if (index == (splitFileText.size - 1)) elvesBags.add(Bag(aliments))
        } else {
            elvesBags.add(Bag(aliments))
            aliments = mutableListOf()
        }
    }
    return elvesBags
}

data class Bag(
    val aliments: List<Int>
)