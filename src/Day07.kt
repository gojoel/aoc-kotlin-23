fun main() {
    data class Hand(val hand: String, val updatedHand: String, val bid: Int)

    fun getHandType(type: String): Int {
        val cards = type.split("")
        val distinctCards = cards.distinct().filter { it.isNotEmpty() }

        return when (distinctCards.size) {
            1 -> 7 // five of a kind
            2 -> {
                if (cards.groupingBy { it }.eachCount()
                        .filter { it.value == 4 || it.value == 1 }.size == 2
                ) {
                    6 // four of a kind
                } else {
                    5 // full house
                }
            }

            3 -> {
                if (cards.groupingBy { it }.eachCount()
                        .filter { it.value == 3 || it.value == 1 }.size == 3
                ) {
                    4 // three of a kind
                } else {
                    3 // two pair
                }
            }

            4 -> 2 // one pair
            5 -> 1 // high card
            else -> 0
        }
    }

    fun getHandComparator(
    ): Comparator<Hand> {
        return Comparator { hand1, hand2 -> getHandType(hand1.updatedHand) - getHandType(hand2.updatedHand) }
    }

    fun getCardComparator(
        strengthMap: Map<Char, Int>
    ): Comparator<Hand> {
        return object : Comparator<Hand> {
            override fun compare(hand1: Hand, hand2: Hand): Int {
                hand1.hand.toCharArray().zip(hand2.hand.toCharArray()).forEach {
                    val diff = strengthMap[it.first]!! - strengthMap[it.second]!!
                    if (diff != 0) {
                        return diff
                    }
                }

                return -1
            }
        }
    }

    fun getSubstituteCard(hand: String, strengthMap: Map<Char, Int>): String {
        val cards = hand.split("").filter { it.isNotEmpty() }
        if (cards.all { it == "J" }) {
            return "J"
        }

        return cards.filter { it != "J" }.groupingBy { v -> v }.eachCount()
            .maxWith(compareBy({ it.value }, { strengthMap[it.key.single()] })).key
    }

    fun getTotalWinnings(
        input: List<String>,
        strengthMap: Map<Char, Int>,
        joker: Boolean = false
    ): Int {
        val bids = input.map { it.split(" ")[1].toInt() }
        val hands = input.map { it.split(" ")[0] }.zip(bids).map {
            Hand(
                it.first,
                if (joker) it.first.replace(
                    "J",
                    getSubstituteCard(it.first, strengthMap)
                ) else it.first,
                it.second
            )
        }

        return hands.sortedWith(getHandComparator().then(getCardComparator(strengthMap)))
            .withIndex().sumOf { (it.index + 1) * it.value.bid }
    }

    fun part1(input: List<String>): Int {
        val strengthMap =
            (1..13).zip(listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'))
                .associate { it.second to it.first }

        return getTotalWinnings(input, strengthMap)
    }

    fun part2(input: List<String>): Int {
        val strengthMap =
            (1..13).zip(listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A'))
                .associate { it.second to it.first }
        return getTotalWinnings(input, strengthMap, true)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}