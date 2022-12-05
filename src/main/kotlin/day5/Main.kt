package day5

import getResourceAsText

fun main() {
    val fileTextDemo = getResourceAsText("day5/input-demo.txt") ?: ""
    val demoLines = fileTextDemo.split("\r\n")
    val demoStacks = retrieveStacks(demoLines)
    val demoActions = retrieveActions(demoLines, demoStacks)
    demoActions.forEach(::move)
    // Demo First result
    demoStacks.forEach { print(it.crate.last()) }
    println()

    val demoStacksV2 = retrieveStacks(demoLines)
    val demoActionsV2 = retrieveActions(demoLines, demoStacksV2)
    demoActionsV2.forEach(::moveV2)
    // Demo Second result
    demoStacksV2.forEach { print(it.crate.last()) }
    println()

    val fileText = getResourceAsText("day5/input.txt") ?: ""
    val lines = fileText.split("\r\n")
    val stacks = retrieveStacks(lines)
    val actions = retrieveActions(lines, stacks)
    actions.forEach(::move)
    // First result
    stacks.forEach { print(it.crate.last()) }
    println()

    val stacksV2 = retrieveStacks(lines)
    val actionsV2 = retrieveActions(lines, stacksV2)
    actionsV2.forEach(::moveV2)
    // Second result
    stacksV2.forEach { print(it.crate.last()) }
}

internal fun move(action: Action) {
    repeat((1..action.move).count()) {
        action.destinationStack.crate.add(action.originStack.crate.removeLast())
    }
}

internal fun moveV2(action: Action) {
    val tmpList = mutableListOf<Char>()
    repeat((1..action.move).count()) {
        tmpList.add(action.originStack.crate.removeLast())
    }
    action.destinationStack.crate.addAll(tmpList.reversed())
}

internal fun retrieveStacks(lines: List<String>) : MutableList<Stack> =
    (1..lines.first { it.isNotBlank() && !it.contains("move") && !it.contains("[")}.split(" ").last().toInt()).map {
        Stack(it, mutableListOf())
    }.toMutableList()

internal fun retrieveActions(lines: List<String>, stacks: List<Stack>) : MutableList<Action> {
    val actions = mutableListOf<Action>()
    lines.forEach { line ->
        if (line.contains("move")) {
            val actionString = line.replace("move ", "").replace(" from ", "-").replace(" to ", "-").split("-").map { it.toInt() }
            actions.add(Action(actionString[0], stacks.first { it.id == actionString[1] }, stacks.first { it.id == actionString[2] }))
        } else if (line.contains("[")) {
            for (index in line.indices step 4) {
                if (line[index] == '[') {
                    stacks[index/4].crate.add(line[index+1])
                }
            }
        }
    }
    stacks.forEach { it.crate.reverse() }
    return actions
}


data class Stack(
    val id: Int,
    val crate: MutableList<Char>
)

data class Action(
    val move: Int,
    val originStack: Stack,
    val destinationStack: Stack
)