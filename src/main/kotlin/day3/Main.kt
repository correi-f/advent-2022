package day3

import getResourceAsText

fun main() {
    val fileTextDemo = getResourceAsText("day3/input-demo.txt") ?: ""
    val linesOfCompartmentDemo = fileTextDemo.split("\r\n").map { line ->
        val lineOfItem = line.map { char -> Item.valueOf(char.toString()) }
        Pair(lineOfItem.take(lineOfItem.size / 2), lineOfItem.takeLast(lineOfItem.size / 2))
    }

    val demoAlterations = findAlterations(linesOfCompartmentDemo)

    // Demo First result
    println(demoAlterations.sumOf { it.value })

    val demoAlterationsV2 = findAlterationsV2(linesOfCompartmentDemo)

    // Demo Second result
    println(demoAlterationsV2.sumOf { it.value })

    val fileText = getResourceAsText("day3/input.txt") ?: ""
    val linesOfCompartment = fileText.split("\r\n").map { line ->
        val lineOfItem = line.map { char -> Item.valueOf(char.toString()) }
        Pair(lineOfItem.take(lineOfItem.size / 2), lineOfItem.takeLast(lineOfItem.size / 2))
    }

    val alterations = findAlterations(linesOfCompartment)

    // First result
    println(alterations.sumOf { it.value })

    val alterationsV2 = findAlterationsV2(linesOfCompartment)

    // Second result
    println(alterationsV2.sumOf { it.value })
}

fun findAlterations(linesOfCompartment: List<Pair<List<Item>, List<Item>>>): List<Item> {
    val alterations = mutableListOf<Item>()
    linesOfCompartment.forEach { pair ->
        var notFound = true
        pair.first.forEach { item ->
            if (notFound && pair.second.contains(item)) {
                alterations.add(item)
                notFound = false
            }
        }
    }
    return alterations
}


fun findAlterationsV2(linesOfCompartment: List<Pair<List<Item>, List<Item>>>): List<Item> {
    val linesOfCompartmentV2 = linesOfCompartment.map { it.first + it.second }
    val alterations = mutableListOf<Item>()
    for (index in linesOfCompartmentV2.indices step 3) {
        var notFound = true
        linesOfCompartmentV2[index].forEach { item ->
            if (notFound && linesOfCompartmentV2[index + 1].contains(item) && linesOfCompartmentV2[index + 2].contains(item)) {
                alterations.add(item)
                notFound = false
            }
        }
    }
    return alterations
}

enum class Item(val value: Int) {
    a(1),
    b(2),
    c(3),
    d(4),
    e(5),
    f(6),
    g(7),
    h(8),
    i(9),
    j(10),
    k(11),
    l(12),
    m(13),
    n(14),
    o(15),
    p(16),
    q(17),
    r(18),
    s(19),
    t(20),
    u(21),
    v(22),
    w(23),
    x(24),
    y(25),
    z(26),
    A(27),
    B(28),
    C(29),
    D(30),
    E(31),
    F(32),
    G(33),
    H(34),
    I(35),
    J(36),
    K(37),
    L(38),
    M(39),
    N(40),
    O(41),
    P(42),
    Q(43),
    R(44),
    S(45),
    T(46),
    U(47),
    V(48),
    W(49),
    X(50),
    Y(51),
    Z(52);
}