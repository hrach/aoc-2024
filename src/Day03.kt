fun main() {
	fun part1(input: List<String>): Int {
		val regexp = """mul\((\d+),(\d+)\)""".toRegex()
		return regexp.findAll(input.joinToString()).sumOf {
			it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt()
		}
	}

	fun part2(input: List<String>): Int {
		val regexp = """mul\((\d+),(\d+)\)|don't\(\)|do\(\)""".toRegex()
		val commands = regexp.findAll(input.joinToString())
		var sum = 0
		var allow = true
		commands.forEach {
			if (it.value.startsWith("mul") && allow) {
				sum += it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt()
			} else if (it.value.startsWith("don")) {
				allow = false
			} else if (it.value.startsWith("do")) {
				allow = true
			}
		}
		return sum
	}

	val testInput1 = readInput("Day03_test")
	val testInput2 = readInput("Day03_test2")
	check(part1(testInput1) == 161)
	check(part2(testInput2) == 48)

	val input = readInput("Day03")
	part1(input).println()
	part2(input).println()
}
