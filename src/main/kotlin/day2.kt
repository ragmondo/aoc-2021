fun main() {
    Day2().part1()
    Day2().part2()
}


class Day2 {

    fun part1() {
        val dd = getData(2).groupBy(keySelector = {it.split(" ")[0]}, valueTransform = { it.split(" ")[1].toInt() })
        val forward = dd.get("forward")!!.sum()
        val down = dd.get("down")!!.sum()
        val up = dd.get("up")!!.sum()
        println((forward*(down-up)))
    }

    fun part2() {
        var hor = 0
        var depth = 0
        var aim = 0

        getData(2).forEach {
            val (cmd, amountS) = it.split(" ")
            val s = amountS.toInt()
            when {
                cmd == "forward" -> {
                    hor = hor + s
                    depth = depth + s * aim
                }
                cmd == "down" -> {
                    aim = aim + s
                }
                cmd == "up" -> {
                    aim = aim - s
                }
            }
        }
        println(hor * depth)

    }

}

