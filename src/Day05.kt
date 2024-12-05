fun main() {
	fun parse(input: List<String>): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
		val rules = input.takeWhile { it.contains("|") }
			.map { it.substringAfter('|').toInt() to it.substringBefore('|').toInt() }
		val pages = input.dropWhile { !it.contains(',') }
			.map { it.split(',').map { it.toInt() } }
		return rules to pages
	}

	fun createComparator(rules: List<Pair<Int, Int>>): Comparator<Int> =
		Comparator { a, b ->
			val aBefore = rules.find { it.first == b && it.second == a } != null // a has to be before
			val bBefore = rules.find { it.first == a && it.second == b } != null // b has to be before
			if (bBefore) 1
			else if (aBefore) -1
			else 0
		}

	fun sums(input: List<String>): Pair<Int, Int> {
		val (rules, pages) = parse(input)
		var (sumValid, sumInvalid) = 0 to 0
		val comparator = createComparator(rules)
		pages.forEach { page ->
			val sorted = page.sortedWith(comparator)
			if (sorted == page) sumValid += page[page.size / 2]
			else sumInvalid += sorted[sorted.size / 2]
		}
		return sumValid to sumInvalid
	}

	fun part1(input: List<String>): Int = sums(input).first
	fun part2(input: List<String>): Int = sums(input).second

	val testInput = readInput("Day05_test")
	check(part1(testInput) == 143)
	check(part2(testInput) == 123)

	val input = readInput("Day05")
	part1(input).println()
	part2(input).println()
}
