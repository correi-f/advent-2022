package day7

import getResourceAsText

private const val MAX_SIZE_FS = 40000000L

fun main() {
    val fileTextDemo = getResourceAsText("day7/input-demo.txt") ?: ""
    val demoRootDirectory = Directory("/", mutableListOf(), mutableListOf(), null)
    val demoLines = fileTextDemo.split("\r\n")
    retrieveRootStructure(demoLines, demoRootDirectory)
    // Demo First result
    println(findHugeSize(demoRootDirectory, 0))

    // Demo second result
    println(findMinDirectorySizeToRemove(demoRootDirectory, demoRootDirectory.size, demoRootDirectory.size))

    val fileText = getResourceAsText("day7/input.txt") ?: ""
    val rootDirectory = Directory("/", mutableListOf(), mutableListOf(), null)
    val lines = fileText.split("\r\n")
    retrieveRootStructure(lines, rootDirectory)
    // First result
    println(findHugeSize(rootDirectory, 0))

    // Second result
    println(findMinDirectorySizeToRemove(rootDirectory, rootDirectory.size, rootDirectory.size))
}

fun findMinDirectorySizeToRemove(directory: Directory, rootSize: Long, minDirectorySizeToDelete: Long): Long {
    return if (directory.directories.isEmpty()) {
        if (directory.size < minDirectorySizeToDelete && rootSize - directory.size <= MAX_SIZE_FS) directory.size else minDirectorySizeToDelete
    } else {
        directory.directories.minOf {
            findMinDirectorySizeToRemove(
                it,
                rootSize,
                if (directory.size < minDirectorySizeToDelete && rootSize - directory.size <= MAX_SIZE_FS) directory.size else minDirectorySizeToDelete
            )
        }
    }
}

fun findHugeSize(directory: Directory, sum: Long): Long {
    return sum + (if (directory.size <= 100000) directory.size else 0) + directory.directories.sumOf { findHugeSize(it, sum) }
}

fun retrieveRootStructure(prompt: List<String>, rootDirectory: Directory) {
    var pointer = rootDirectory
    var listing = false
    prompt.forEachIndexed { index, line ->
        if (index != 0) {
            when {
                line.startsWith("$") -> {
                    if (line.startsWith("$ cd")) {
                        listing = false
                        val directoryName = line.subSequence(5, line.length).toString()
                        pointer = if (directoryName == "..") {
                            pointer.previous!!
                        } else move(directoryName, pointer)
                    }
                    else if (line.startsWith("$ ls")) listing = true
                    else throw IllegalStateException()
                }
                listing -> {
                    if (line.startsWith("dir")) {
                        pointer.directories.add(Directory(line.subSequence(4, line.length).toString(), mutableListOf(), mutableListOf(), pointer))
                    } else {
                        pointer.files.add(File(line.split(" ")[1], line.split(" ")[0].toInt()))
                    }
                }
                else -> throw IllegalStateException()
            }
        }
    }
    calculateSize(rootDirectory, 0)
}

fun calculateSize(directory: Directory, sum: Long): Long {
    val directorySum = directory.files.sumOf { it.size }
    directory.size = directorySum + directory.directories.sumOf { calculateSize(it, sum) }
    return directory.size
}

fun move(directoryName: String, pointer: Directory): Directory {
    return pointer.directories.first { it.name == directoryName }
}

data class Directory(val name: String, val directories: MutableList<Directory>, val files: MutableList<File>, val previous: Directory?, var size: Long = 0)

data class File(val name: String, val size: Int)