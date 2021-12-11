import java.util.*

fun main() {
    val d = getData(10)
    var total = 0
    val stackScores = mutableListOf<Long>()
    d.forEach {
        val d10 = Day10()
        var processing = true
        it.forEach { c ->
            if (processing) {
                val (score, msg) = d10.next(c)
                if (score > 0) {
                    processing = false
                    total += score
                }
            }
        }
        if (processing) {
            stackScores.add(d10.getStackScore())
        }
    }

    stackScores.sort()

    println("Part 1 : ${total}")
    println("Part 2: ${stackScores.get(stackScores.size / 2)}")
}

class Day10() {
    val pairs = mapOf(
        '(' to ')',
        '<' to '>',
        '{' to '}',
        '[' to ']',
    )

    val score = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    val autoscore = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    val myStack = Stack<Char>()

    fun getStackScore() = myStack.toArray().reversed().fold(0L, { int, acc -> int * 5 + autoscore[pairs[acc]!!]!! })

    fun next(s: Char): Pair<Int, String> {
        if (s in pairs) {
            myStack.push(s)
        } else {
            var expected = pairs[myStack.pop()]!!
            if (s != expected) {
                return Pair(score.get(s)!!, "Expected $expected but found $s")
            }
        }
        return Pair(0, "")
    }
}
