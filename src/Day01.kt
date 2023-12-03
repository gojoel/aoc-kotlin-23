import kotlin.text.StringBuilder

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0

        for (line in input) {
            val filtered = line.filter { it.isDigit() }
            val value = "${filtered.first()}${filtered.last()}"
            sum += value.toInt()
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val digitMap = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )

        val digitSet = hashSetOf("o", "t", "f", "s", "e", "n")

        var sum = 0

        for (line in input) {
            val digitLine = StringBuilder("")
            var digitBuilder = StringBuilder("")

            for (char in line) {
                if (char.isDigit()) {
                    digitLine.append(char)
                    digitBuilder = StringBuilder("")
                } else {
                    digitBuilder.append(char)
                    if (digitMap.containsKey(digitBuilder.toString())) {
                        digitLine.append(digitMap[digitBuilder.toString()])
                        digitBuilder = StringBuilder()
                        continue
                    }

                    var isValid = false
                    for (key in digitMap.keys) {
                        if (key.startsWith(digitBuilder.toString())) {
                            isValid = true
                            break
                        }
                    }

                    if (!isValid) {
                        digitBuilder = StringBuilder("")
                        if (digitSet.contains(char.toString())) {
                            digitBuilder.append(char)
                        }
                    }
                }
            }

            if (digitLine.isNotEmpty()) {
                val value = "${digitLine.first()}${digitLine.last()}"
                sum += value.toInt()
            }
        }

        return sum;
    }

    // test if implementation meets criteria from the description, like:
    val testP1Input = readInput("Day01_p1_test")
    check(part1(testP1Input) == 142)

    val testP2Input = readInput("Day01_p2_test")
    check(part2(testP2Input) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
