import java.lang.Integer.max
import java.lang.StringBuilder

fun main() {

    fun buildLeft(line: String, start: Int): Int {
        val partNumber = StringBuilder()
        var startPos = start
        while (startPos >= 0 && line[startPos].isDigit()) {
            partNumber.insert(0, line[startPos])
            startPos -= 1
        }

        if (partNumber.isEmpty()) {
            return -1
        }

        return partNumber.toString().toInt()
    }

    fun buildRight(line: String, start: Int): Int {
        val partNumber = StringBuilder()
        var startPos = start
        while (startPos < line.length && line[startPos].isDigit()) {
            partNumber.append(line[startPos])
            startPos += 1
        }

        if (partNumber.isEmpty()) {
            return -1
        }

        return partNumber.toString().toInt()
    }

    fun buildLR(line: String, startPos: Int): Int {
        if (line[startPos].isDigit()) {
            val partNumber = StringBuilder()
            partNumber.append(line[startPos])

            val left = buildLeft(line, startPos - 1)
            val right = buildRight(line, startPos + 1)
            if (left > -1) {
                partNumber.insert(0, left.toString())
            }

            if (right > -1) {
                partNumber.append(right.toString())
            }

            return partNumber.toString().toInt()
        }

        return  -1
    }

    fun buildVertical(line: String, startPos: Int): Int {
        return if (line[startPos].isDigit()) {
            max(buildLR(line, startPos), 0)
        } else {
            var sum = 0
            if (startPos - 1 >= 0 ) {
                sum += max(buildLeft(line, startPos - 1), 0)
            }

            if (startPos + 1 < line.length ) {
                sum += max(buildRight(line, startPos + 1), 0)
            }

            sum
        }
    }


    fun part1(input: List<String>): Int {

        val symbols = mutableListOf<Pair<Int, Int>>()

        input.forEachIndexed { lineIdx, line ->
            line.forEachIndexed { charIdx, char ->
                if (!char.isDigit() && char != '.') {
                    symbols.add(Pair(lineIdx, charIdx))
                }
            }
        }

        var sum = 0
        symbols.forEach { symbol ->
            sum += max(buildLeft(input[symbol.first], symbol.second - 1), 0)
            sum += max(buildRight(input[symbol.first], symbol.second + 1), 0)

            val topRow = symbol.first - 1
            if (topRow >= 0) {
                sum += buildVertical(input[topRow], symbol.second)
            }

            val bottomRow = symbol.first + 1
            if (bottomRow < input.size) {
                sum += buildVertical(input[bottomRow], symbol.second)
            }
        }

        return sum
    }



    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println(part1(testInput))
    check(part1(testInput) == 4361)

//    val testInput2 = readInput("Day03_test_2")
//    println(part1(testInput2))
//    check(part1(testInput2) == 10279)

    val input = readInput("Day03")
    part1(input).println()
//    part2(input).println()
}

// submitted
// 513986
// 522014
