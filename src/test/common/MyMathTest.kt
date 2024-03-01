import org.junit.Assert
import org.junit.Test

class MyMathTest {

    @Test
    fun testCRT2020Day13PartTwoExample1() {
        val input = listOf(Pair(7, 0), Pair(13, 12), Pair(59, 55), Pair(31, 25), Pair(19, 12))
        Assert.assertEquals(1068781, MyMath.chineseRemainder(input))
    }

    @Test
    fun testCRT2020Day13PartTwoExample2() {
        val input = listOf(Pair(17, 0), Pair(13, 11), Pair(19, 16))
        Assert.assertEquals(3417, MyMath.chineseRemainder(input))
    }

    @Test
    fun testCRT2020Day13PartTwoExample3() {
        val input = listOf(Pair(67, 0), Pair(7, 6), Pair(59, 57), Pair(61, 58))
        Assert.assertEquals(754018, MyMath.chineseRemainder(input))
    }

    @Test
    fun testCRT2020Day13PartTwoExample4() {
        val input = listOf(Pair(67, 0), Pair(7, 5), Pair(59, 56), Pair(61, 57))
        Assert.assertEquals(779210, MyMath.chineseRemainder(input))
    }

    @Test
    fun testCRT2020Day13PartTwoExample5() {
        val input = listOf(Pair(67, 0), Pair(7, 6), Pair(59, 56), Pair(61, 57))
        Assert.assertEquals(1261476, MyMath.chineseRemainder(input))
    }

    @Test
    fun testCRT2020Day13PartTwoExample6() {
        val input = listOf(Pair(1789, 0), Pair(37, 36), Pair(47, 45), Pair(1889, 1886))
        Assert.assertEquals(1202161486, MyMath.chineseRemainder(input))
    }

    @Test
    fun testLCM() {
        Assert.assertEquals(2, MyMath.lcm(setOf(2)))
        Assert.assertEquals(16, MyMath.lcm(setOf(2, 4, 8, 16)))
        Assert.assertEquals(36, MyMath.lcm(setOf(12, 18)))
        Assert.assertEquals(80, MyMath.lcm(setOf(16, 20)))
        Assert.assertEquals(2520, MyMath.lcm((1..10).toList()))
    }

    // Also used by 2023 day 24 part 2
    @Test
    fun testGaussianElimination() {
        val a1 = MyMath.gaussianElimination(listOf(
            listOf(4, 3, 11).map { it.toBigInteger() }.toTypedArray(),
            listOf(1, -3, -1).map { it.toBigInteger() }.toTypedArray(),
        ))
        Assert.assertEquals(listOf(2L, 1L), a1)

        val a2 = MyMath.gaussianElimination(listOf(
            listOf(1, 2 ,3 , -7).map { it.toBigInteger() }.toTypedArray(),
            listOf(2, -3, -5, 9).map { it.toBigInteger() }.toTypedArray(),
            listOf(-6, -8, 1, -22).map { it.toBigInteger() }.toTypedArray(),
        ))
        Assert.assertEquals(listOf(-1L, 3L, -4L), a2)

        val a3 = MyMath.gaussianElimination(listOf(
            listOf(3, 2, -4, 3).map { it.toBigInteger() }.toTypedArray(),
            listOf(2, 3, 3, 15).map { it.toBigInteger() }.toTypedArray(),
            listOf(5, -3, 1, 14).map { it.toBigInteger() }.toTypedArray()
        ))
        Assert.assertEquals(listOf(3L, 1L, 2L), a3)
    }
}