@Suppress("NAME_SHADOWING")
fun main() {
	fun check(data: List<String>, x: Int, y: Int, dx: Int, dy: Int, word: String): Boolean {
		var x = x
		var y = y
		word.forEach { c ->
			if (c != data.getOrNull(y)?.getOrNull(x)) return false
			x += dx
			y += dy
		}
		return true
	}

	fun count(data: List<String>, x: Int, y: Int): Int {
		return listOf(
			check(data, x, y, 1, 1, "XMAS"),
			check(data, x, y, 1, 0, "XMAS"),
			check(data, x, y, 1, -1, "XMAS"),
			check(data, x, y, 0, -1, "XMAS"),
			check(data, x, y, -1, -1, "XMAS"),
			check(data, x, y, -1, 0, "XMAS"),
			check(data, x, y, -1, 1, "XMAS"),
			check(data, x, y, 0, 1, "XMAS"),
		).count { it }
	}

	fun count2(data: List<String>, x: Int, y: Int): Int {
		return listOf(
			check(data, x, y, dx = 1, dy = 1, "MAS")
				&& check(data, x, y + 2, dx = 1, dy = -1, "MAS"),
			check(data, x, y, dx = 1, dy = -1, "MAS")
				&& check(data, x + 2, y, dx = -1, dy = -1, "MAS"),
			check(data, x, y, dx = -1, dy = 1, "MAS")
				&& check(data, x, y + 2, dx = -1, dy = -1, "MAS"),
			check(data, x, y, dx = 1, dy = 1, "MAS")
				&& check(data, x + 2, y, dx = -1, dy = 1, "MAS"),
		).count { it }
	}

	fun List<String>.calc(check: (List<String>, Int, Int) -> Int): Int {
		var sum = 0
		indices.forEach { y ->
			this[y].indices.forEach { x ->
				sum += check(this, x, y)
			}
		}
		return sum
	}

	fun part1(input: List<String>): Int =
		input.calc(::count)

	fun part2(input: List<String>): Int =
		input.calc(::count2)

	val testInput = readInput("Day04_test")
	check(part1(testInput) == 18)
	check(part2(testInput) == 9)

	val input = readInput("Day04")
	part1(input).println()
	part2(input).println()
}
