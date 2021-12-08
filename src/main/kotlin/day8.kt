fun main() {
    var sum = 0
    var ctr = 0
    getData(8).forEach {
        val (mapping, result) = Day8.parse(it)
        val output = result.map {
            it.toString()!!
        }.joinToString(separator = "").toInt()

        sum = sum + output
        ctr += result.count { it in listOf(1, 4, 7, 8) }
    }
    println(ctr)
    println(sum)
}


class Day8() {
    companion object {
        fun parse(it: String): Pair<MutableMap<String, Int>, List<Int?>> {
            val s = it.split(" ").map {
                it.toSortedSet().joinToString(separator = "")
            }.joinToString(separator = " ")

            val retMap = mutableMapOf<String, Int>()

            val (inp, outp) = s.split("|")
            val i = inp.split(" ")
            val o = outp.split(" ").drop(1)

            // Find the 8
            val map8 = i.filter { it.length == 7 }.single()
            retMap.put(map8, 8)

            // Find the 1
            val map1 = i.filter { it.length == 2 }.single()
            retMap.put(map1, 1)

            // Find the 4
            val map4 = i.filter { it4 -> it4.length == 4 && map1.all { it in it4 } }.single()
            retMap.put(map4, 4)

            // Find the 9
            val map9 = i.filter { it9 -> it9.length == 6 && map4.all { it in it9 } }.single()
            retMap.put(map9, 9)

            // Find the 0
            val map0 = i.filter { it0 -> it0.length == 6 && it0 != map9 && map1.all { it in it0 } }.single()
            retMap.put(map0, 0)

            // Find the 6
            val map6 = i.filter { it6 -> it6.length == 6 && it6 != map0 && it6 != map9 }.single()
            retMap.put(map6, 6)

            // Find the 7
            val map7 = i.filter { it7 -> it7.length == 3 }.single()
            retMap.put(map7, 7)

            // Find the 3
            val map3 = i.filter { it3 -> it3.length == 5 && map1.all { it in it3 } }.single()
            retMap.put(map3, 3)

            // get elements of 4 that aren't in 1 to help identify the 5
            val ele41 = map4.filter { it !in map1 }
            val map5 = i.filter { it5 -> it5.length == 5 && ele41.all { it in it5 } }.single()
            retMap.put(map5, 5)

            // Find the 2
            val map2 = i.filter { it2 -> it2.length == 5 && it2 != map3 && it2 != map5 }.single()
            retMap.put(map2, 2)

            val retOutput = o.map {
                retMap.get(it)
            }

            return Pair(retMap, retOutput)
        }
    }
}