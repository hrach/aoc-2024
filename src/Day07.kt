package Day7

import println
import readInput

fun options1(operators: List<Long>): Sequence<Long> = sequence {
	if (operators.size == 1) {
		yield(operators.first())
		return@sequence
	}
	yieldAll(options1(operators.take(operators.size - 1)).map { it + operators[operators.lastIndex] })
	yieldAll(options1(operators.take(operators.size - 1)).map { it * operators[operators.lastIndex] })
}

fun options2(operators: List<Long>): Sequence<Long> = sequence {
	if (operators.size == 1) {
		yield(operators.first())
		return@sequence
	}
	yieldAll(options2(operators.take(operators.size - 1)).map { it + operators[operators.lastIndex] })
	yieldAll(options2(operators.take(operators.size - 1)).map { it * operators[operators.lastIndex] })
	yieldAll(options2(operators.take(operators.size - 1)).map { (it.toString() + operators[operators.lastIndex]).toLong() })
}

fun calc(input: List<String>, options: (operators: List<Long>) -> Sequence<Long>): Long =
	input.sumOf { line ->
		val sum = line.substringBefore(':').toLong()
		val operators = line.substringAfter(':').trim().split(' ').map { it.toLong() }
		val doable = options(operators).any { it == sum }
		if (doable) { sum } else { 0 }
	}

fun main() {
	fun part1(input: List<String>): Long = calc(input, ::options1)
	fun part2(input: List<String>): Long = calc(input, ::options2)

	val testInput = readInput("Day07_test")
	check(part1(testInput) == 3749L)
	check(part2(testInput) == 11387L)

	val input = readInput("Day07")
	part1(input).println()
	part2(input).println()
}
