fun main() {
    var startingFishList: List<Int> = getData(6).first().split(",").map { it.toInt() }
    var fishMap = startingFishList.groupBy { it }.map { it.key to it.value.size.toLong() }.toMap()

    repeat(256) {
        val newFishMap = mutableMapOf<Int, Long>()

        // TODO: Surely a better way to do this
        fishMap.forEach { t, u ->
            lifecycle(t).forEach {
                newFishMap.put(it, u + newFishMap.getOrDefault(it, 0))
            }
        }
        fishMap = newFishMap
    }
    println("[${fishMap.values.sum()}] : $fishMap")
}

fun lifecycle(i: Int) = when {
    (i == 0) -> listOf(6, 8)
    else -> listOf(i - 1)
}
