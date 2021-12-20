import org.junit.jupiter.api.Assertions.assertEquals


fun main() {
    mainpt1()
    mainpt2()
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
        var hits = 0L
        var misses = 0L

    }
}

fun mainpt1() {

    var (polymer, chains) = Day14.DataReader().read(true)

    repeat(10) {
        val pairs = polymer.dropLast(1).mapIndexed { it, c ->
            c.toString() + polymer[it + 1]
        }
        val inserts = pairs.map { it -> chains[it]!! }

        polymer = ((polymer.toList().zip(inserts) { a, b -> listOf(a, b) }).flatten() + polymer.last()).joinToString(
            separator = ""
        )
//        println("${it+1} : [${polymer.length}] ") //  ${polymer}")
    }
    val groups = polymer.groupBy { it }.map{ it -> it.key to it.value.size}
    val sortedGroups = groups.sortedBy { it.second }
    val f = sortedGroups.first()
    val l = sortedGroups.last()
    println("$sortedGroups $f $l ${l.second - f.second}")
}



fun mainpt2() {
    var (polymer, chains) = Day14.DataReader().read()
    println(chains)
    println(polymer)

//    val cache = mapOf()

    val soln = recur(chains, polymer, 40)

    println(soln)
    val mx = soln.maxOf { it.value }
    val mn = soln.minOf { it.value }
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

    assertEquals(s.length , 2, "S needs to be length 2 : $s")

    if(Pair(s, levels) in Day14.cacheMap) {
        Day14.hits ++
        return Day14.cacheMap.get(Pair(s, levels))!!
    }
    Day14.misses ++
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