fun main() {

    fun extrapolateValue(sequence: List<Int>, previous: Boolean = false): Int {
        var currentSequence = sequence.toMutableList()
        var nextSequence = mutableListOf<Int>()
        val placeHolders = mutableListOf(if (previous) currentSequence.first() else currentSequence.last())

        while (currentSequence.distinct().size != 1 || currentSequence.first() != 0) {
            for ((index, num) in currentSequence.withIndex()) {
                if (index + 1 < currentSequence.size) {
                    nextSequence.add(currentSequence[index + 1] - num)
                }
            }

            placeHolders.add(if (previous) nextSequence.first() else nextSequence.last())
            currentSequence = nextSequence.toMutableList()
            nextSequence = mutableListOf()
        }

        return if (previous) {
            placeHolders.reversed().reduce { acc, i -> i - acc }
        } else {
            placeHolders.sum()
        }
    }

    fun part1(input: List<String>): Int {
        return input.map { it.split(" ").filter { it.isNotEmpty() }.map { value -> value.toInt() } }
            .sumOf {
                extrapolateValue(it)
            }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(" ").filter { it.isNotEmpty() }.map { value -> value.toInt() } }
            .sumOf {
                extrapolateValue(it, previous = true)
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()

}