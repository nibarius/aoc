package test.aoc2019

import org.junit.Test

class BonusTest {


    @Test
    fun long() {
        val base = "https://echo.opera.com/?a="
        val first = "100".padStart(100 - base.length, '_')
        var long = base + first
        for (i in 2..15) {
            val len = "${i * 100}"
            long += len.padStart(100, '_')
        }
        println(long)
    }

}

