import kotlin.math.roundToInt

fun main() {
	fun parse(input: List<String>): Pair<Map<Int, List<Int>>, List<List<Int>>> {
		val rules = input
			.takeWhile { it.contains("|") }
			.map { it.split('|').map { it.toInt() } }
			.groupBy { it[1] }
			.mapValues { it.value.map { it.first() } }
		val pages = input.dropWhile { !it.contains(',') }
			.map { it.split(',').map { it.toInt() } }
		return rules to pages
	}

	fun isOk(pages: List<Int>, rules: Map<Int, List<Int>>): Boolean =
		pages.indices.all { i ->
			val num = pages[i]
			val numRules = rules[num]
			numRules?.all { beforeNum ->
				pages.indexOf(beforeNum) < i // -1~not found matches the condition as well
			} ?: true
		}

	fun createComparator(rules: Map<Int, List<Int>>): Comparator<Int> =
		Comparator { a, b ->
			val bBefore = rules[a]?.find { it == b } != null // b has to be before
			val aBefore = rules[b]?.find { it == a } != null // a has to be before
			if (bBefore) 1
			else if (aBefore) -1
			else 0
		}

	fun part1(input: List<String>): Long {
		val (rules, pages) = parse(input)
		return pages.sumOf {
			val isOk = isOk(it, rules)
			if (isOk) it[(it.size / 2f).roundToInt() - 1].toLong() else 0L
		}
	}

	fun part2(input: List<String>): Long {
		val (rules, pages) = parse(input)
		val comparator = createComparator(rules)
		return pages.sumOf {
			val isOk = isOk(it, rules)
			if (!isOk) {
				val fixed = it.sortedWith(comparator)
				fixed[(fixed.size / 2f).roundToInt() - 1].toLong()
			} else 0L
		}
	}

	val testInput = readInput("Day05_test")
	check(part1(testInput) == 143L)
	check(part2(testInput) == 123L)

	val input = readInput("Day05")
	part1(input).println()
	part2(input).println()
}
