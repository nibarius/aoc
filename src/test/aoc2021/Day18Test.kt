package test.aoc2021

import aoc2021.Day18
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day18Test {
    private val exampleInput = """
        [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
        [[[5,[2,8]],4],[5,[[9,9],0]]]
        [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
        [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
        [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
        [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
        [[[[5,4],[7,7]],8],[[8,3],8]]
        [[9,3],[[9,9],[6,[4,9]]]]
        [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
        [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
    """.trimIndent().split("\n")

    @Test
    fun testExplode1() {
        val day18 = Day18(listOf())
        val str = day18.parse("[[[[[9,8],1],2],3],4]")
        day18.explode(str)
        assertEquals("[[[[0,9],2],3],4]", day18.stringify(str))
    }

    @Test
    fun testExplode2() {
        val day18 = Day18(listOf())
        val str = day18.parse("[7,[6,[5,[4,[3,2]]]]]")
        day18.explode(str)
        assertEquals("[7,[6,[5,[7,0]]]]", day18.stringify(str))
    }

    @Test
    fun testExplode3() {
        val day18 = Day18(listOf())
        val str = day18.parse("[[6,[5,[4,[3,2]]]],1]")
        day18.explode(str)
        assertEquals("[[6,[5,[7,0]]],3]", day18.stringify(str))
    }

    @Test
    fun testExplode4() {
        val day18 = Day18(listOf())
        val str = day18.parse("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")
        day18.explode(str)
        assertEquals("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", day18.stringify(str))
    }

    @Test
    fun testExplode5() {
        val day18 = Day18(listOf())
        val str = day18.parse("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")
        day18.explode(str)
        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", day18.stringify(str))
    }

    @Test
    fun testSplit() {
        val day18 = Day18(listOf())
        val str = day18.parse("[[[[0,7],4],[?,[0,=]]],[1,1]]") // ? is 15 while = is 13
        day18.split(str)
        assertEquals("[[[[0,7],4],[[7,8],[0,=]]],[1,1]]", day18.stringify(str))
    }

    @Test
    fun testHomework1() {
        val input = """
            [1,1]
            [2,2]
            [3,3]
            [4,4]
        """.trimIndent().split("\n")
        val day18 = Day18(input)
        val ret = day18.doHomework()
        assertEquals("[[[[1,1],[2,2]],[3,3]],[4,4]]", day18.stringify(ret))
    }

    @Test
    fun testHomework2() {
        val input = """
            [1,1]
            [2,2]
            [3,3]
            [4,4]
            [5,5]
        """.trimIndent().split("\n")
        val day18 = Day18(input)
        val ret = day18.doHomework()
        assertEquals("[[[[3,0],[5,3]],[4,4]],[5,5]]", day18.stringify(ret))
    }

    @Test
    fun testHomework3() {
        val input = """
            [1,1]
            [2,2]
            [3,3]
            [4,4]
            [5,5]
            [6,6]
        """.trimIndent().split("\n")
        val day18 = Day18(input)
        val ret = day18.doHomework()
        assertEquals("[[[[5,0],[7,4]],[5,5]],[6,6]]", day18.stringify(ret))
    }

    @Test
    fun testHomework4() {
        val input = """
            [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
            [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
            [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
            [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
            [7,[5,[[3,8],[1,4]]]]
            [[2,[2,2]],[8,[8,1]]]
            [2,9]
            [1,[[[9,3],9],[[9,0],[0,7]]]]
            [[[5,[7,4]],7],1]
            [[[[4,2],2],6],[8,7]]
        """.trimIndent().split("\n")
        val day18 = Day18(input)
        val ret = day18.doHomework()
        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", day18.stringify(ret))
    }

    @Test
    fun testHomework5() {
        val input = """
            [[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]
            [[2,[2,2]],[8,[8,1]]]
        """.trimIndent().split("\n")
        val day18 = Day18(input)
        val ret = day18.doHomework()
        assertEquals("[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]", day18.stringify(ret))
    }

    @Test
    fun testMagnitude() {
        val day18 = Day18(listOf("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"))
        assertEquals(3488, day18.solvePart1())
    }

    @Test
    fun testPartOneExample1() {
        val day18 = Day18(exampleInput)
        assertEquals(4140, day18.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day18 = Day18(resourceAsList("2021/day18.txt"))
        assertEquals(4033, day18.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day18 = Day18(exampleInput)
        assertEquals(3993, day18.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day18 = Day18(resourceAsList("2021/day18.txt"))
        assertEquals(4864, day18.solvePart2())
    }
}