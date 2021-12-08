package test.aoc2021

import aoc2021.Day8
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day8Test {
    private val exampleInput = """
        be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
        edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
        fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
        fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
        aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
        fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
        dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
        bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
        egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
        gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day8 = Day8(exampleInput)
        assertEquals(26, day8.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day8 = Day8(resourceAsList("2021/day8.txt"))
        assertEquals(521, day8.solvePart1())
    }


    @Test
    fun testPartTwoSimpleExample1() {
        val day8 = Day8(listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"))
        assertEquals(5353, day8.solvePart2())
    }

    @Test
    fun testPartTwoExample1() {
        val day8 = Day8(exampleInput)
        assertEquals(61229, day8.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day8 = Day8(resourceAsList("2021/day8.txt"))
        assertEquals(1016804, day8.solvePart2())
    }
}