fun main() {

    fun winningWays(time: Long, distance: Long) = (1..time).count { (time - it) * it > distance }

    fun part1(input: List<String>): Int {
        val durations =
            input[0].substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val distances =
            input[1].substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

        return durations.zip(distances).map {
            winningWays(it.first.toLong(), it.second.toLong())
        }.reduce { acc, i -> i * acc }
    }

    fun part2(input: List<String>): Int {
        val duration = input[0].substringAfter(":").replace(" ", "").trim().toLong()
        val distance = input[1].substringAfter(":").replace(" ", "").trim().toLong()

        return winningWays(duration, distance)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    println(part2(testInput))
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}