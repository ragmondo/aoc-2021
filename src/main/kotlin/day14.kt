import Day14.Companion.cacheMap
import org.junit.jupiter.api.Assertions.assertEquals

fun mainpt1() {

    var (polymer, chains) = Day14.DataReader().read(true)

    repeat(40) {
        val pairs = polymer.dropLast(1).mapIndexed { it, c ->
            c.toString() + polymer[it + 1]
        }
        val inserts = pairs.map { it -> chains[it]!! }

        polymer = ((polymer.toList().zip(inserts) { a, b -> listOf(a, b) }).flatten() + polymer.last()).joinToString(
            separator = ""
        )
        println("${it+1} : [${polymer.length}] ") //  ${polymer}")
    }
    val groups = polymer.groupBy { it }.map{ it -> it.key to it.value.size}
    val sortedGroups = groups.sortedBy { it.second }
    val f = sortedGroups.first()
    val l = sortedGroups.last()
    println("$sortedGroups $f $l ${l.second - f.second}")
}

class Day14 {
    class DataReader() {
        fun read(sample: Boolean = false): Pair<String, Map<String, Char>> {
            val d = getData(14, sample)
            var polymer = d.first()
            val chains = d.takeLast(d.size - 2).map { it -> it.split(" -> ", limit = 2) }.map { it[0] to it[1].toCharArray()[0] }.toMap()
            return Pair(polymer, chains)
        }
    }
    companion object {
        var cacheMap = mutableMapOf<Pair<String, Int>, Map<Char, Long>>()
    }
}

fun main() {
    mainpt2()
}

fun mainpt2() {
    val d = getData(14 )
    var polymer = d.first()
    val chains = d.takeLast(d.size - 2).map { it -> it.split(" -> ", limit = 2) }.map { "${it[0]}:0" to it[1].toCharArray()[0] }.toMap()
    println(chains)
    println(polymer)

//    val cache = mapOf()

    val soln = recur(chains, polymer, 41)

    println(soln)
    val mx = soln.maxOf { it.value }
    val mn = soln.maxOf { it.value }
    println(mx-mn)


}

class Memory()  {
    companion object {
        val m = Memory()
        fun mem() = m
    }
}


fun recur(chains: Map<String, Char>, s: String, levels: Int): Map<Char, Long> {
    if (s.length > 2) {
        // Divide into pairs and process those.

        val pairs = s.mapIndexed { index, c ->
            if(index < s.length - 1) {
                "" + c + s[index + 1]
            }
            else {null}
        }.filterNotNull()

        val mainresult = pairs.map { polypair ->
            recur(chains, polypair, levels).map {
                it.key to (it.value - (if (it.key == polypair[1]) 1 else 0))
            }.toMap()
        }

        var allkeys = mainresult.map { it.keys }.flatten().toSet().map{ it to 0L}.toMap().toMutableMap()

        mainresult.forEach { mr ->
            mr.forEach { it ->
                allkeys.put(it.key, it.value + allkeys.getOrDefault(it.key, 0L))
            }
        }

        val lastChar = pairs.last()[1]

        allkeys.put(lastChar, 1 + allkeys.getOrDefault(lastChar, 0L))

        allkeys.forEach {
        return allkeys
        }
    }


//        if (s.length == 3) {
//            TODO("Just put middle in as intermediate")
//        }
//        if(s.length == 4) {
//            // call three times
//            val lhs = recur(chains, "" + s[0] + s[1], levels - 1).map {
//                if (it.key == s[1]) {
//                    it.key to it.value - 1
//                } else {
//                    it.key to it.value
//                }
//            }.toMap()
//            val mid = recur(chains, "" + s[1] + s[2], levels - 1).map {
//                if (it.key == s[2]) {
//                    it.key to it.value - 1
//                } else {
//                    it.key to it.value
//                }
//            }.toMap()
//
//            val rhs = recur(chains, "" + s[2] + s[3], levels - 1)
//
//            var sum = (lhs.keys + rhs.keys + mid.keys).toSet().map {
//                it to lhs.getOrDefault(it, 0) + rhs.getOrDefault(it, 0) + mid.getOrDefault(it, 0)
//            }.toMap()
//            return sum
//        }
//    }

    assertEquals(s.length , 2, "S needs to be length 4 or shorter : $s")

    if(Pair(s, levels) in Day14.cacheMap) {
        return Day14.cacheMap.get(Pair(s, levels))!!
    }
    if (levels == 0) {
        if ( s[0] == s[1]) {
            return mapOf(s[0].toChar() to 2)
        }
        else {
            return s.toCharArray().map { it to 1L }.toMap()
        }
    }

    val intermediate = chains.get(s) ?: throw RuntimeException("Looking for $s in polymer list and can't find")
    val lhs = recur(chains, s[0] + intermediate.toString(), levels - 1)
    val rhs = recur(chains, intermediate.toString() + s[1], levels - 1)
    var ret = (lhs.keys + rhs.keys).toSet().map {
        it to lhs.getOrDefault(it,0) + rhs.getOrDefault(it, 0)
    }.toMap()

    var modified = ret.map {
        if(it.key == intermediate) {
            it.key to it.value - 1
        }
        else {
            it.key to it.value
        }
    }.toMap()

    Day14.cacheMap.put(Pair(s, levels), modified)

    return modified
}