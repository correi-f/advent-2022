package day6

import getResourceAsText

fun main() {

    val demo1 = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
    val demo2 = "bvwbjplbgvbhsrlpgdmjqwftvncz"
    val demo3 = "nppdvjthqldpwncqszvftbrmjlhg"
    val demo4 = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"
    val demo5 = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"

    println(findMarkerPosition(demo1))
    println(findMarkerPosition(demo2))
    println(findMarkerPosition(demo3))
    println(findMarkerPosition(demo4))
    println(findMarkerPosition(demo5))

    println(findMarkerPositionV2(demo1))
    println(findMarkerPositionV2(demo2))
    println(findMarkerPositionV2(demo3))
    println(findMarkerPositionV2(demo4))
    println(findMarkerPositionV2(demo5))

    val fileText = getResourceAsText("day6/input.txt") ?: ""
    // First result
    println(findMarkerPosition(fileText))

    //Second result
    println(findMarkerPositionV2(fileText))

}

fun findMarkerPosition(sequence: String) : Int {
    val maxSize = sequence.length
    for (index in sequence.indices) {
        if (index + 4 >= maxSize) return -1
        if (sequence.subSequence(index, index + 4).toList().distinct().size == 4) return index + 4
    }
    return -1
}

fun findMarkerPositionV2(sequence: String) : Int {
    val maxSize = sequence.length
    for (index in sequence.indices) {
        if (index + 14 >= maxSize) return -1
        if (sequence.subSequence(index, index + 14).toList().distinct().size == 14) return index + 14
    }
    return -1
}