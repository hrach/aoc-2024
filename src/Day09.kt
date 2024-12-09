package Day9

import println
import readInput

sealed interface Entry {
	data class Data(val len: Int, val value: Int, var moved: Boolean = false) : Entry {
		fun split(len: Int): Triple<Data, Entry?, Entry?> =
			Triple(
				Data(len = len.coerceAtMost(this.len), value, moved = true),
				if (this.len < len) Space(len - this.len) else null,
				if (this.len > len) Data(this.len - len, value) else null,
			)
	}

	data class Space(val len: Int, var tried: Boolean = false) : Entry
}

fun parse(input: List<String>): List<Entry> = buildList {
	var i = 0
	input[0].forEachIndexed { index, s ->
		when {
			index.mod(2) == 0 -> add(Entry.Data(s.digitToInt(), i++))
			else -> {
				val len = s.digitToInt()
				if (len > 0) add(Entry.Space(len))
			}
		}
	}
}

fun compact(data: MutableList<Entry>) {
	while (true) {
		val spaceIndex = data.indexOfFirst { it is Entry.Space }
		if (spaceIndex == -1) break
		val space = data[spaceIndex] as Entry.Space
		val pop = data.removeLast()
		if (pop !is Entry.Data) continue
		val (a, b, c) = pop.split(space.len)
		data[spaceIndex] = a
		if (b != null) data.add(spaceIndex + 1, b)
		if (c != null) data.add(c)
	}
}

fun compact2(data: MutableList<Entry>) {
	while (true) {
		val popIndex = data.indexOfLast { it is Entry.Data && !it.moved }
		if (popIndex == -1) break
		val pop = data[popIndex] as Entry.Data

		val spaceIndex = data.indexOfFirst { it is Entry.Space && it.len >= pop.len }
		if (spaceIndex == -1 || popIndex <= spaceIndex) {
			pop.moved = true
			continue
		}
		val space = data[spaceIndex] as Entry.Space

		data[popIndex] = Entry.Space(pop.len)
		if (data.getOrNull(popIndex + 1) is Entry.Space) {
			data[popIndex] = Entry.Space(pop.len + (data[popIndex + 1] as Entry.Space).len)
			data.removeAt(popIndex + 1)
		}

		val (a, b, c) = pop.split(space.len)
		data[spaceIndex] = a
		if (b != null) data.add(spaceIndex + 1, b)
		if (c != null) data.add(c)
	}
}

fun sum(data: List<Entry>): Long {
	var sum = 0L
	var i = 0
	data.forEach { entry ->
		if (entry is Entry.Data) {
			repeat(entry.len) {
				sum += i * entry.value
				i++
			}
		} else if (entry is Entry.Space) {
			i += entry.len
		}
	}
	return sum
}

fun main() {
	fun part1(input: List<String>): Long {
		val data = parse(input).toMutableList()
		compact(data)
		return sum(data)
	}

	fun part2(input: List<String>): Long {
		val data = parse(input).toMutableList()
		compact2(data)
		return sum(data)
	}

	val testInput = readInput("Day09_test")
	check(part1(testInput) == 1928L)
	check(part2(testInput) == 2858L)

	val input = readInput("Day09")
	part1(input).println()
	part2(input).println()
}
