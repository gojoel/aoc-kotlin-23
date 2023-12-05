import java.lang.StringBuilder

fun main() {

    fun buildLeft(array: Array<CharArray>, row: Int, start: Int): String {
        val partNumber = StringBuilder()
        var startPos = start
        while (startPos >= 0 && array[row][startPos].isDigit()) {
            partNumber.insert(0, array[row][startPos])
            array[row][startPos] = '.'
            startPos -= 1
        }

        return partNumber.toString()
    }

    fun buildRight(array: Array<CharArray>, row: Int, start: Int): String {
        val partNumber = StringBuilder()
        var startPos = start
        while (startPos < array[row].size && array[row][startPos].isDigit()) {
            partNumber.append(array[row][startPos])
            array[row][startPos] = '.'
            startPos += 1
        }

        return partNumber.toString()
    }

    fun buildLR(array: Array<CharArray>, row: Int, start: Int): String {
        if (array[row][start].isDigit()) {
            val partNumber = StringBuilder()
            partNumber.append(array[row][start])
            array[row][start] = '.'

            partNumber.insert(0, buildLeft(array, row, start - 1))
            partNumber.append(buildRight(array, row, start + 1))

            return partNumber.toString()
        }

        return ""
    }

    fun buildVertical(array: Array<CharArray>, row: Int, start: Int): List<String> {
        val partNumbers = mutableListOf<String>()
        if (array[row][start].isDigit()) {
            partNumbers.add(buildLR(array, row, start))
        } else {
            if (start - 1 >= 0) {
                partNumbers.add(buildLeft(array, row, start - 1))
            }

            if (start + 1 < array[row].size) {
                partNumbers.add(buildRight(array, row, start + 1))
            }
        }

        return partNumbers
    }

    fun partNumberInt(partNumber: String): Int {
        if (partNumber.isEmpty()) {
            return 0
        }

        return partNumber.toInt()
    }

    fun getPartNumbers(array: Array<CharArray>, symbol: Pair<Int, Int>): List<Int> {
        val partNumbers = mutableListOf<Int>()
        partNumbers.add(partNumberInt(buildLeft(array, symbol.first, symbol.second - 1)))
        partNumbers.add(partNumberInt(buildRight(array, symbol.first, symbol.second + 1)))

        val topRow = symbol.first - 1
        if (topRow >= 0) {
            buildVertical(
                array,
                topRow,
                symbol.second
            ).forEach { partNumbers.add(partNumberInt(it)) }
        }

        val bottomRow = symbol.first + 1
        if (bottomRow < array.size) {
            buildVertical(array, bottomRow, symbol.second).forEach {
                partNumbers.add(
                    partNumberInt(
                        it
                    )
                )
            }
        }

        return partNumbers
    }

    fun part1(input: List<String>): Int {
        val array = Array(input.size) { CharArray(input[0].length) }
        val symbols = mutableListOf<Pair<Int, Int>>()

        input.forEachIndexed { lineIdx, line ->
            line.forEachIndexed { charIdx, char ->
                array[lineIdx][charIdx] = char
                if (!char.isDigit() && char != '.') {
                    symbols.add(Pair(lineIdx, charIdx))
                }
            }
        }

        return symbols.map { getPartNumbers(array, it) }.flatten().sum()
    }

    fun part2(input: List<String>): Int {
        val array = Array(input.size) { CharArray(input[0].length) }
        val symbols = mutableListOf<Pair<Int, Int>>()

        input.forEachIndexed { lineIdx, line ->
            line.forEachIndexed { charIdx, char ->
                array[lineIdx][charIdx] = char
                if (!char.isDigit() && char != '.') {
                    symbols.add(Pair(lineIdx, charIdx))
                }
            }
        }

        return symbols.map { getPartNumbers(array, it) }.map { it.filter { num -> num != 0 } }
            .filter {
                it.size == 2
            }.sumOf { it.reduce { acc, i -> acc * i } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}