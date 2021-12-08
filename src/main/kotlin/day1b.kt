
fun sum(t: Triple<Int,Int,Int>) = t.first + t.second + t.third

fun main() {
    val myBigArray = getData(1).map { it -> it.toInt() }.toTypedArray()
    var prev = Triple(999,999,999)
    var ctr = 0
    for (i in 2 .. myBigArray.size -1 ) {
        val cur = Triple(myBigArray[i-2], myBigArray[i-1], myBigArray[i])
        val x= sum(cur) > sum(prev)
        if(x) {
            ctr ++
        }
        prev = cur
    }
    println(ctr)
}

class Day1B() {
}