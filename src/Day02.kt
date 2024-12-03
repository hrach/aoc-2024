import kotlin.math.absoluteValue

fun main() {
	fun part1(input: List<String>): Int =
		input
			.map { it.split(' ').map { it.toInt() } }
			.map { it.zipWithNext { a, b -> a - b } }
			.count {
				val sign = it.first() > 0
				it.all { it.absoluteValue in 1..3 } && it.all { sign == (it > 0) }
			}

	fun part2(input: List<String>): Int =
		input
			.map { it.split(' ').map { it.toInt() } }
			.count { line ->
				(0..line.size).any {
					val l = (line.take(it) + line.drop(it + 1)).zipWithNext { a, b -> a - b }
					val sign = l.first() > 0
					l.all {
						it.absoluteValue in 1..3 && sign == (it > 0)
					}
				}
			}

	val testInput = readInput("Day02_test")
	check(part1(testInput) == 2)
	check(part2(testInput) == 4)

	val input = readInput("Day02")
	part1(input).println()
	part2(input).println()
}
