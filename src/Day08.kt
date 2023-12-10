
fun main() {

    fun getNodes(input: List<String>): Map<String, Pair<String, String>> {
        return input.drop(2).associate {
            val key = it.substringBefore("=").trim()
            val value = it.substringAfter("=").replace("[()]".toRegex(), "").split(",")
            Pair(key,  Pair(value[0].trim(), value[1].trim()))
        }
    }

    fun part1(input: List<String>): Int {
        val instructions  = input.first().split("").filter { it.isNotEmpty() }
        val nodeMap = getNodes(input)

        var currNode = "AAA"
        var steps = 0
        var index = 0

        while (true) {
            if (index == instructions.size) {
                index = 0
            }

            val pair = nodeMap[currNode]!!
            val instruction = instructions[index]
            val node = if (instruction == "L") pair.first else pair.second
            currNode = node

            steps += 1
            index += 1

            if (node == "ZZZ") break
        }

        return steps
    }

    fun part2(input: List<String>): Int {
        val instructions  = input.first().split("").filter { it.isNotEmpty() }
        val nodeMap = getNodes(input)
        val startingNodes = nodeMap.keys.filter { it.last() == 'A' }

        var currNodes = startingNodes.toMutableList()
        var index = 0
        var sum = 0

        while (currNodes.distinct().size != 1 || currNodes[0] != "Z") {
            if (index == instructions.size) {
                index = 0
            }

            val instruction = instructions[index]
            currNodes = if (instruction == "L") {
                currNodes.map { nodeMap[it]!!.first }.toMutableList()
            } else {
                currNodes.map { nodeMap[it]!!.second }.toMutableList()
            }

            index += 1
            sum += 1
        }


        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 6)
    check(part2(testInput) == 6)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}