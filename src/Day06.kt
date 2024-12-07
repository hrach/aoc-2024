package Day6

import println
import readInput

data class Point(val x: Int, val y: Int)
enum class Direction { N, E, S, W }

fun Point.next(dir: Direction) = when (dir) {
	Direction.N -> this.copy(y = this.y - 1)
	Direction.E -> this.copy(x = this.x + 1)
	Direction.S -> this.copy(y = this.y + 1)
	Direction.W -> this.copy(x = this.x - 1)
}

fun Direction.rotate(): Direction = when (this) {
	Direction.N -> Direction.E
	Direction.E -> Direction.S
	Direction.S -> Direction.W
	Direction.W -> Direction.N
}

fun parse(input: List<String>): Map {
	val walls = mutableSetOf<Point>()
	var guard: Point? = null
	input.forEachIndexed { y, s ->
		s.forEachIndexed { x, c ->
			when (c) {
				'#' -> walls.add(Point(x, y))
				'^' -> guard = Point(x, y)
			}
		}
	}
	return Map(
		max = input.size - 1,
		walls = walls,
		guard = guard!!,
		guardDir = Direction.N
	)
}

data class Map(
	val max: Int,
	val walls: Set<Point>,
	var guard: Point,
	var guardDir: Direction,
) {
	private fun Point.isEmpty(): Boolean = this !in walls

	fun move(): Boolean {
		val newPos = guard.next(guardDir)
		return if (!newPos.isEmpty()) {
			guardDir = guardDir.rotate()
//			guard = guard.next(guardDir)
//			guard.isEmpty()
			true
		} else {
			guard = newPos
			true
		}
	}

	inline fun run(block: (Point, Direction) -> Boolean) {
		do {
			if (!move()) break
			if (!block(guard, guardDir)) break
		} while (guard.x in 0..max && guard.y in 0..max)
	}
}

fun main() {
	fun part1(input: List<String>): Int {
		val map = parse(input)
		val visited = mutableSetOf(map.guard)
		map.run { point, _ ->
			visited += point
			true
		}
		return visited.size - 1 // guard is already outside the map
	}

	fun part2(input: List<String>): Int {
		val map = parse(input)
		var cycles = 0
		for (x in 0..map.max) for (y in 0..map.max) {
			val toAdd = Point(x, y)
			if (toAdd in map.walls || toAdd == map.guard) continue
			val newMap = map.copy(walls = map.walls + toAdd)
			val visited = mutableSetOf(newMap.guard to newMap.guardDir)
			newMap.run { point, dir ->
				if ((point to dir) in visited) {
					cycles += 1
					false
				} else {
					visited += point to dir
					true
				}
			}
		}
		return cycles
	}

	val testInput = readInput("Day06_test")
	check(part1(testInput) == 41)
	check(part2(testInput) == 6)

	val input = readInput("Day06")
	part1(input).println()
	part2(input).println()
}
