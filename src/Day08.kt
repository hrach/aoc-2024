package Day8

import println
import readInput

data class Point(val x: Int, val y: Int)

fun parse(input: List<String>): List<Pair<Point, Char>> = buildList {
	input.forEachIndexed { y, s ->
		s.forEachIndexed { x, c ->
			if (c != '.') add(Point(x, y) to c)
		}
	}
}

fun List<Point>.combinations(): Sequence<Pair<Point, Point>> = sequence {
	for (i in this@combinations.indices) for (j in i..<size) {
		if (i == j) continue
		yield(this@combinations[i] to this@combinations[j])
	}
}

fun extrapolate(a: Point, b: Point, max: Int, repeated: Boolean): List<Point> = buildList {
	val dx = a.x - b.x
	val dy = a.y - b.y
	var ax = a.x
	var ay = a.y
	do {
		ax += dx
		ay += dy
		add(Point(ax, ay))
		if (!repeated) break
	} while (ax in 0..max && ay in 0..max)
	var bx = b.x
	var by = b.y
	do {
		bx -= dx
		by -= dy
		add(Point(bx, by))
		if (!repeated) break
	} while (bx in 0..max && by in 0..max)
}.filter {
	it.x in 0..max && it.y in 0..max
}

fun calc(input: List<String>, block: (Pair<Point, Point>, antiNodes: MutableSet<Point>) -> Unit): Int {
	val max = input.size - 1
	val antennas = parse(input)
	val unique = antennas.groupBy { it.second }.map { it.value.map { it.first } }
	val antiNodes = mutableSetOf<Point>()
	unique.forEach { letters ->
		val combinations = letters.combinations()
		combinations.forEach { combination ->
			block(combination, antiNodes)
		}
	}
	return antiNodes.size
}

fun main() {
	fun part1(input: List<String>): Int =
		calc(input) { (a, b), antiNodes ->
			antiNodes.addAll(extrapolate(a, b, max = input.lastIndex, repeated = false))
		}

	fun part2(input: List<String>): Int =
		calc(input) { (a, b), antiNodes ->
			antiNodes.add(a)
			antiNodes.add(b)
			antiNodes.addAll(extrapolate(a, b, max = input.lastIndex, repeated = true))
		}

	val testInput = readInput("Day08_test")
	check(part1(testInput) == 14)
	check(part2(testInput) == 34)

	val input = readInput("Day08")
	part1(input).println()
	part2(input).println()
}
