import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day14KtTest {

    @Test
    fun KnownIterations() {

        var (polymer, chains) = Day14.DataReader().read(true)

        val iterations = listOf(
            "NNCB",
            "NCNBCHB",
            "NBCCNBBBCBHCB",
            "NBBBCNCCNBBNBNBBCHBHHBCHB",
            "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB"
        )

        iterations.forEachIndexed { index, s ->
            println("Checking for ${index} iterations with ${s}")
            var (polymer, chains) = Day14.DataReader().read(true)
            val result = synthesize(chains, polymer, index)
            checkResult(s, result)
        }
    }

    @Test
    fun iteration_40() {
        var (polymer, chains) = Day14.DataReader().read(true)
        val r41 = synthesize(chains, polymer, 40)
        assertEquals(r41.get('B'), 2192039569602)
        assertEquals(r41.get('H'), 3849876073)
    }

    fun checkResult(expected: String, actual: Map<Char, Long>) {
        actual.forEach {
            val expected_n = expected.count { c ->
                it.key == c
            }.toLong()
            val actual_n = actual.get(it.key)
            println("Comparing for $it.key : $actual_n vs $expected_n")
            assertEquals(expected_n, actual_n, "Mismatch for ${it.key} Expected: $expected_n , Actual: $actual_n")
        }
    }
}