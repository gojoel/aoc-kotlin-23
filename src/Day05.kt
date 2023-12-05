import kotlin.math.abs
import kotlin.math.min

fun main() {

    data class CategoryInfo(val destination: Long, val source: Long, val range: Long) {
        fun inRange(srcNum: Long): Boolean {
            return srcNum >= source && srcNum <= (source + range) - 1
        }

        fun destinationNumber(srcNum: Long): Long {
            return destination + abs((srcNum - source))
        }
    }

    fun buildInfoList(input: List<String>, startIndex: Int): List<CategoryInfo> {
        val list = mutableListOf<CategoryInfo>()
        var index = startIndex + 1
        while (index < input.size && input[index].isNotEmpty()) {
            val line = input[index]
            val dst = line.split(" ")[0].trim().toLong()
            val src = line.split(" ")[1].trim().toLong()
            val range = line.split(" ")[2].trim().toLong()

            list.add(CategoryInfo(dst, src, range))
            index++
        }

        return list
    }

    fun getDestination(categoryInfoList: List<CategoryInfo>, sourceNum: Long): Long {
        val destination =  categoryInfoList.filter { info -> info.inRange(sourceNum) }.let {
            if (it.isEmpty()) {
                sourceNum
            } else {
                it.first().destinationNumber(sourceNum)
            }
        }

        return destination
    }

    fun part1(input: List<String>): Long {
        val seeds = input[input.indexOfFirst { it.startsWith("seeds") }].split(":")[1].split(" ")
            .filter { it.isNotEmpty() }.map { it.toLong() }
        val seedSoil = buildInfoList(input, input.indexOfFirst { it.startsWith("seed-to-soil") })
        val soilFertilizer =
            buildInfoList(input, input.indexOfFirst { it.startsWith("soil-to-fertilizer") })
        val fertilizerWater =
            buildInfoList(input, input.indexOfFirst { it.startsWith("fertilizer-to-water") })
        val waterLight =
            buildInfoList(input, input.indexOfFirst { it.startsWith("water-to-light") })
        val lightTemp =
            buildInfoList(input, input.indexOfFirst { it.startsWith("light-to-temperature") })
        val tempHumidity =
            buildInfoList(input, input.indexOfFirst { it.startsWith("temperature-to-humidity") })
        val humidityLocation =
            buildInfoList(input, input.indexOfFirst { it.startsWith("humidity-to-location") })

        return seeds.asSequence().map { seed -> getDestination(seedSoil, seed) }
            .map { soil -> getDestination(soilFertilizer, soil) }
            .map { fertilizer -> getDestination(fertilizerWater, fertilizer) }
            .map { water -> getDestination(waterLight, water) }
            .map { light -> getDestination(lightTemp, light) }
            .map { temp -> getDestination(tempHumidity, temp) }
            .map { humidity -> getDestination(humidityLocation, humidity) }.toList()
            .min()
    }

    fun getSeedPairs(seeds: List<Long>): List<Pair<Long, Long>> {
        val pairs = mutableListOf<Pair<Long, Long>>()
        var index = 0
        while (index < seeds.size) {
            pairs.add(Pair(seeds[index], seeds[index + 1]))
            index += 2
        }

        return pairs
    }

    fun part2(input: List<String>): Long {
        val seeds = input[input.indexOfFirst { it.startsWith("seeds") }].split(":")[1].split(" ")
            .filter { it.isNotEmpty() }.map { it.toLong() }
        val seedPairs = getSeedPairs(seeds)

        val seedSoil = buildInfoList(input, input.indexOfFirst { it.startsWith("seed-to-soil") })
        val soilFertilizer =
            buildInfoList(input, input.indexOfFirst { it.startsWith("soil-to-fertilizer") })
        val fertilizerWater =
            buildInfoList(input, input.indexOfFirst { it.startsWith("fertilizer-to-water") })
        val waterLight =
            buildInfoList(input, input.indexOfFirst { it.startsWith("water-to-light") })
        val lightTemp =
            buildInfoList(input, input.indexOfFirst { it.startsWith("light-to-temperature") })
        val tempHumidity =
            buildInfoList(input, input.indexOfFirst { it.startsWith("temperature-to-humidity") })
        val humidityLocation =
            buildInfoList(input, input.indexOfFirst { it.startsWith("humidity-to-location") })


        var currentMin = 0L
        for (pair in seedPairs) {
            for (i in pair.first..< pair.first + pair.second) {
                val location = listOf(i).asSequence().map { seed -> getDestination(seedSoil, seed) }
                    .map { soil -> getDestination(soilFertilizer, soil) }
                    .map { fertilizer -> getDestination(fertilizerWater, fertilizer) }
                    .map { water -> getDestination(waterLight, water) }
                    .map { light -> getDestination(lightTemp, light) }
                    .map { temp -> getDestination(tempHumidity, temp) }
                    .map { humidity -> getDestination(humidityLocation, humidity) }
                    .first()

                currentMin = if (currentMin == 0L) {
                    location
                } else {
                    min(currentMin, location)
                }
            }
        }

        return currentMin
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}