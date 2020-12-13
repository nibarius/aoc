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
}