import java.lang.Math.abs

fun main() {
    val d = getData(7).first().split(",").map { it.toInt() }
    println((0 .. d.maxOf { it }).minOf { trial -> costPt1(trial, d) })
    println((0 .. d.maxOf { it }).minOf { trial -> costPt2(trial, d) })
    println((0 .. d.maxOf { it }).minOf { trial -> costPt2b(trial, d) })
}

fun triangle(x: Int)= (1 .. x).sum()

fun costPt1(target: Int, crabs: List<Int>)= crabs.map { abs(target - it) }.sum()
fun costPt2(target: Int, crabs: List<Int>)= crabs.map { triangle(abs(target - it)) }.sum()
fun costPt2b(target: Int, crabs: List<Int>)= crabs.map {
    val x = abs(target-it)
    (x* (x+1))/2
}.sum()
