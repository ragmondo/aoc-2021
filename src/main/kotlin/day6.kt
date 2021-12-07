import java.math.BigInteger

fun main() {
    var startingFishList: List<Int> = getData(6).first().split(",").map{ it.toInt()}
    var fishMap = startingFishList.groupBy { it }.map { it.key to BigInteger.valueOf(it.value.size.toLong())}.toMap()

    repeat(256) {
        val newFishMap = mutableMapOf<Int,BigInteger>()

        fishMap.forEach  { t, u ->
            lifecycle(t).forEach {
                newFishMap.put(it, u.plus(newFishMap.getOrDefault(it,BigInteger.ZERO)))
            }
        }
        fishMap = newFishMap
    }
    val fishcount = fishMap.values.fold(BigInteger.ZERO, BigInteger::plus)
    println("[$fishcount] : $fishMap")

}

fun lifecycle(i: Int) = when {
        (i == 0) -> listOf(6,8)
        else -> listOf(i - 1)
    }
