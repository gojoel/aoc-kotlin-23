fun main() {

    data class Card(val id: Int, val winningNumbers: List<Int>, val numbers: List<Int>) {
        fun matches(): Int {
            return numbers.intersect(winningNumbers).size
        }

        fun value(): Int {
            val matches = numbers.intersect(winningNumbers)
            return matches.fold(0) { value, _ -> if (value == 0) 1 else value * 2}
        }
    }

    fun parseNumbers(numbers: String): List<Int> {
        return numbers.split(" ").filter { it.isNotEmpty() }.map {
            it.toInt()
        }
    }

    fun parseCards(input: List<String>): List<Card> {
        val cards = mutableListOf<Card>()

        for (line in input) {
            val split = line.split(":")
            val id = split[0].split(" ").filter { it.isNotEmpty() }[1].toInt()
            val numSplit = split[1].split("|")
            val winningNumbers = parseNumbers(numSplit[0])
            val numbers = parseNumbers(numSplit[1])

            cards.add(Card(id, winningNumbers, numbers))
        }

        return cards
    }

    fun part1(input: List<String>): Int {
        val cards = parseCards(input)
        return cards.sumOf { it.value() }
    }

    fun part2(input: List<String>): Int {
        val cards = parseCards(input).toMutableList()
        val cardMap = mutableMapOf<Int, Card>()
        cards.forEach { cardMap[it.id] = it }

        var count = cards.size

        while (cards.isNotEmpty()) {
            val copies = mutableListOf<Card>()
            cards.forEach {card ->
                for (i in 1..card.matches()) {
                    copies.add(cardMap[card.id + i]!!)
                    count++
                }
            }

            cards.clear()
            cards.addAll(copies)
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}