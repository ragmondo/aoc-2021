import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day8Test {
    @Test
    fun ensureAllNumbersMapped() {
        val sample = "abcefg cf acdeg acdfg cbdf abdfg abdefg acf abcdefg abcdfg | abcdfg abcdefg acf abdefg abdfg cbdf acdfg acdeg cf abcefg"
        val (parseMap, answer) = (Day8.parse(sample))
        assertEquals(answer,listOf(9,8,7,6,5,4,3,2,1,0))
    }
}