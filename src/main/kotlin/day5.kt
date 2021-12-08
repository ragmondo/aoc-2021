import Day5.Companion.generatePoints

fun main() {

    val vents = getData(5).map { it -> VentReader(it) }
    run {
        val d5a = Day5()
        vents.forEach { v ->
            generatePoints(v.start, v.finish, false).forEach { d5a.addPoint(it) }
        }
        println(d5a.countAtLeast(2))
    }

    run {
        val d5b = Day5()
        vents.forEach { v ->
            generatePoints(v.start, v.finish, true).forEach { d5b.addPoint(it) }
        }
        println(d5b.countAtLeast(2))
    }
}

fun parse(s: String) = s.replace(" ", "").toInt()

data class VentReader(val s: String) {
    var start: Point
    var finish: Point

    init {
        val coords = s.split("->")
        val (x1, y1) = coords[0].split(",").map { parse(it) }
        val (x2, y2) = coords[1].split(",").map { parse(it) }
        start = Point(x1, y1)
        finish = Point(x2, y2)
    }
}

data class Point(val x: Int, val y: Int)

class Day5() {

    companion object {

        fun min(a: Int, b: Int) = if (a < b) a else b
        fun max(a: Int, b: Int) = if (a > b) a else b

        fun generatePoints(p1: Point, p2: Point, diags: Boolean = false): List<Point> {
            if (p1.x == p2.x) {
                return (min(p1.y, p2.y)..max(p1.y, p2.y)).map {
                    Point(p1.x, it)
                }
            } else if (p1.y == p2.y) {
                return (min(p1.x, p2.x).. max(p1.x, p2.x)).map {
                    Point(it, p1.y)
                }
            } else if (diags) {
                val (incr, start) = if ((p1.x > p2.x && p1.y > p2.y) || (p1.x < p2.x && p1.y < p2.y)) {
                    Pair(1, min(p1.y, p2.y))
                } else {
                    Pair(-1, max(p1.y, p2.y))
                }
                return (min(p1.x, p2.x)..max(p1.x, p2.x)).mapIndexed { index, i ->
                    Point(i, start + (index * incr))
                }
            } else {
                return listOf()
            }
        }
    }

    fun debug() {
        val maxy = data.maxOf { it.key }
        val maxx = data.maxOf { it.value.maxOf { it.key } }

        for (y in 0..maxy) {
            for (x in 0..maxx) {
                print(data.get(x)?.get(y) ?: ".")
            }
            println()
        }
    }

    var data = mutableMapOf<Int, MutableMap<Int, Int>>()

    fun addPoint(p: Point) {
        var row = data.getOrPut(p.x, {mutableMapOf<Int,Int>()})
        row[p.y] = row.getOrDefault(p.y,0) + 1
    }

    fun countAtLeast(x: Int) = data.map { it.value.filter { it.value >= x }.count() }.sum()

}

