fun main() {
    Day3().part1()
}

class Day3() {

    fun part1() {
        val data = getData(3)
        val result = mutableListOf<Int>()
        for (i in 0..data[0].length - 1) {
            var r = data.map {
                it[i].toString().toInt()
            }.sum()
            result.add(r)
        }
        val gam = (result.map {
            if (it > (data.size / 2)) 1 else 0
        })
        val eps = gam.map {
            1 - it
        }

        println(binToInt(gam) * binToInt(eps))

        var seeking = true
        var bit = 0

        var localData = data

        while (seeking) {
            val localResult = mutableListOf<Int>()
            for (i in 0..localData[0].length - 1) {
                var r = localData.map {
                    it[i].toString().toInt()
                }.sum()
                localResult.add(r)
            }
            val localGam = (localResult.map {
                if (it * 2 >= localData.size) 1 else 0
            })
            localData = localData.filter {
                (it[bit].toString() == localGam[bit].toString())
            }
            if (localData.size == 1) {
                seeking = false
            }
            bit++
        }
        val oxy = localData.single().map { it.toString().toInt() }
        var oxyI = binToInt(oxy)
        println("Oxy is $oxyI")

        seeking = true
        bit = 0

        localData = data

        while (seeking) {
            val localResult = mutableListOf<Int>()
            for (i in 0..localData[0].length - 1) {
                var r = localData.map {
                    it[i].toString().toInt()
                }.sum()
                localResult.add(r)
            }
            val localGam = (localResult.map {
                if (it * 2 < localData.size) 1 else 0
            })
            localData = localData.filter {
                (it[bit].toString() == localGam[bit].toString())
            }
            if (localData.size == 1) {
                seeking = false
            }
            bit++
        }
        val co2 = localData.single().map { it.toString().toInt() }
        var co2I = binToInt(co2)
        println("Co2 is $co2I")

        println(co2I * oxyI)

    }


    fun binToInt(x: List<Int>) = x.reduce { sum, element -> sum * 2 + element }

}