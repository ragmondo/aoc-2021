import java.io.File

fun main() {

    var prev = 0
    var ctr = -1

    getData(1).map { it.toInt() }.forEachIndexed { index, i ->
        if(i > prev) { ctr++}
        prev = i
    }
    println(ctr)
}

class Day1() {

}