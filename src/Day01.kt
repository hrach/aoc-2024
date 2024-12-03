import kotlin.math.absoluteValue

fun main() {
	fun part1(input: List<String>): Int {
		val inputs = input.map { line -> line.split("   ").map { it.toInt() } }
		val first = inputs.map { it[0] }.sorted()
		val second = inputs.map { it[1] }.sorted()
		return first.zip(second).fold(0) { acc, pair -> acc + (pair.first - pair.second).absoluteValue }
	}

	fun part2(input: List<String>): Int {
		val inputs = input.map { line -> line.split("   ").map { it.toInt() } }
		val first = inputs.map { it[0] }.sorted()
		val second = inputs.map { it[1] }.sorted()
		return first.sumOf { num -> second.count { it == num }.times(num) }
	}

	val testInput = readInput("Day01_test")
	check(part1(testInput) == 11)
	check(part2(testInput) == 31)

	val input = readInput("Day01")
	part1(input).println()
	part2(input).println()
}
