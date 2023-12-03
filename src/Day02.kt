import kotlin.math.max

fun main() {
    data class GameSet(val red: Int, val blue: Int, val green: Int)
    data class Game(val id: Int, val sets: List<GameSet>)

    fun parseGames(input: List<String>): List<Game> {
        val games = mutableListOf<Game>()
        for (line in input) {
            val gameId = line.substringBefore(":").split(" ")[1].toInt()
            val sets = line.substringAfter(":").trim().split(";")
            val gameSets = sets.map { set -> set.trim() }.map { set ->
                val values = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
                set.split(",").map { it.trim() }.forEach {
                    val count = it.split(" ")[0]
                    val color = it.split(" ")[1]
                    values[color] = count.toInt()
                }

                GameSet(red = values["red"]!!, blue = values["blue"]!!, green = values["green"]!!)
            }

            games.add(Game(id = gameId, sets = gameSets))
        }

        return games
    }

    fun part1(input: List<String>): Int {
        // Determine which games would have been possible if the bag had been loaded with
        // only 12 red cubes, 13 green cubes, and 14 blue cubes
        val games = parseGames(input)

        return games.filter { game ->
            game.sets.map { set -> set.red <= 12 && set.green <= 13 && set.blue <= 14 }
                .reduce { acc, b -> acc && b }
        }.sumOf { game -> game.id }
    }

    fun part2(input: List<String>): Int {
        // For each game, find the minimum set of cubes that must have been present.
        // What is the sum of the power of these sets?
        val games = parseGames(input)
        return games.sumOf { game ->
            var minRed = 0
            var minBlue = 0
            var minGreen = 0

            game.sets.forEach { set ->
                minRed = max(minRed, set.red)
                minBlue = max(minBlue, set.blue)
                minGreen = max(minGreen, set.green)
            }

            minRed * minBlue * minGreen
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}